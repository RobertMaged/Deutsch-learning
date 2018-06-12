package com.example.android.deutsch;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    ImageView animation;

    private MediaPlayer mMediaplayer;

    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    private AudioManager mAudioManager;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            switch (i){
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT: { mMediaplayer.pause(); mMediaplayer.seekTo(0); break; }
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK: { mMediaplayer.pause(); mMediaplayer.seekTo(0); break; }
                case AudioManager.AUDIOFOCUS_GAIN: { mMediaplayer.start(); break; }
                case AudioManager.AUDIOFOCUS_LOSS: { releaseMediaPlayer(); break; }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("red", "Rot",R.drawable.color_red,R.raw.color_red));
        words.add(new Word("green", "Grün",R.drawable.color_green,R.raw.color_green));
        words.add(new Word("brown", "Braun",R.drawable.color_brown,R.raw.color_brown));
        words.add(new Word("gray", "Grau",R.drawable.color_gray,R.raw.color_gray));
        words.add(new Word("black", "Schwarz",R.drawable.color_black,R.raw.color_black));
        words.add(new Word("white", "Weiß",R.drawable.color_white,R.raw.color_white));
        words.add(new Word("dusty yellow", "Staubig Gelb",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new Word("mustard yellow", "Senf Gelb",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_colors);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word forAudio = words.get(i);

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if ( result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaplayer = MediaPlayer.create(ColorsActivity.this, forAudio.getAudio());

                    animation = (ImageView) view.findViewById(R.id.pause_play);
                    animation.setImageResource(R.drawable.baseline_hearing_white_18);

                    mMediaplayer.start();

                    mMediaplayer.setOnCompletionListener(mOnCompletionListener);
                }
            }
        });

    }

    @Override
    protected void onStop(){
        super.onStop();
        releaseMediaPlayer();
    }


    private void releaseMediaPlayer(){

        if( mMediaplayer != null) {

            animation.setImageResource(R.drawable.ic_play_arrow);

            mMediaplayer.release();
            mMediaplayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}
