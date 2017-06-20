package com.mdislam.lucid;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

/**
 * Created by mdnah on 6/19/2017.
 */

public class ProfileActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final Profile profile = (Profile) getIntent().getSerializableExtra("profile");

        VideoView videoView = (VideoView) findViewById(R.id.video);
        videoView.setZOrderOnTop(true);
        ImageView image = (ImageView) findViewById(R.id.image);
        Picasso.with(this).load(profile.getImage()).into(image);
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(profile.getFullname());
        TextView email = (TextView) findViewById(R.id.email);
        email.setText(profile.getEmail());
        TextView phone = (TextView) findViewById(R.id.phone);
        phone.setText(profile.getPhone());
        TextView location = (TextView) findViewById(R.id.city_state);
        location.setText(profile.getLocation());
        TextView current = (TextView) findViewById(R.id.current_work);
        current.setText(profile.getCurrent());
        TextView bio = (TextView) findViewById(R.id.bio);
        bio.setText(profile.getBio());
        TextView linkedinBtn = (TextView) findViewById(R.id.linkedInBtn);
        linkedinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(profile.getLinkedin()));
                startActivity(browserIntent);
            }
        });


        videoView.setVideoURI(Uri.parse(profile.getVideo()));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        videoView.start();

    }



}
