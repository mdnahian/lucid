from flask import Flask, url_for
from flask_socketio import SocketIO, emit
from PIL import Image
import string
import random
import io
import face_recognition
import glob
import os
import lucid


app = Flask(__name__, static_url_path='')
app.config['SECRET_KEY'] = 'lucid'
socketio = SocketIO(app)


BASE_URL = ''


def recognize(image):
    for img in glob.glob('static/snaps/*.jpg'):
        snap = face_recognition.load_image_file(img)
        face_encoding = face_recognition.face_encodings(snap)[0]

        unknown_picture = face_recognition.load_image_file(image)
        unknown_face_encoding = face_recognition.face_encodings(unknown_picture)[0]

        results = face_recognition.compare_faces([face_encoding], unknown_face_encoding)

        if results[0]:
            return os.path.basename(img)
    return None


@app.route('/')
def test():
    return 'running...'


@socketio.on('image_sent')
def image_sent(raw):
    file_name = ''.join(random.SystemRandom().choice(string.ascii_uppercase + string.digits) for _ in range(0, 6))\
                + '.jpg'
    file_path = 'static/tmp/' + file_name
    img = Image.open(io.BytesIO(raw))
    img.save(file_path)

    user_id = recognize(file_path)

    if user_id is not None:
        user = lucid.get_user(user_id)
        emit('response', user)
    else:
        print 'user is not recognized'



@app.route('/<path:path>')
def image_file(path):
    return url_for('static', filename=path)


if __name__ == '__main__':
    socketio.run(app, host='0.0.0.0', port=int(80), debug=True)