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

public class FamilyActivity extends AppCompatActivity {

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

        words.add(new Word("Father", "Vater", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("Mother", "Mutter" ,R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("Son", "Sohn", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("Daughter", "Tochter", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("Older brother", "Älter Sohn", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("Younger brother", "Jünger Sohn", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("Older sister", "Älter Tochter", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("Younger sister", "Jünger Tochter", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word("Grandmother", "Großmutter \"Oma\"" ,R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("Grandfather", "Großvater", R.drawable.family_grandfather, R.raw.family_grandfather));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_family);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word forAudio = words.get(i);

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if ( result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaplayer = MediaPlayer.create(FamilyActivity.this, forAudio.getAudio());

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
