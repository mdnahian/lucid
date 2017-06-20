from firebase import firebase
import json

firebase = firebase.FirebaseApplication('https://lucid-78ac3.firebaseio.com/')


def build_user(user_id, user):
    global firebase
    firebase.put(user_id, user)


def get_user(user_id):
    global firebase
    return json.dumps(firebase.get(user_id))