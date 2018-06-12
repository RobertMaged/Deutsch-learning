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

import com.example.android.deutsch.R;
import com.example.android.deutsch.Word;
import com.example.android.deutsch.WordAdapter;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

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

        words.add(new Word("Where are you going?", "Wohin gehst du?", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "Wie heißt du?", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "Ich heiße...", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "Wie fühlst du dich?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I’m feeling good.", "Ich fühle gut.", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "Kommst du?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming.", "Ja, ich komme.", R.raw.phrase_yes_im_coming));
        words.add(new Word("I’m coming...", "Ich komme...", R.raw.phrase_im_coming));
        words.add(new Word("Let’s go.", "Lass uns gehen.", R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "Komm her.", R.raw.phrase_come_here));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_phrases);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word forAudio = words.get(i);

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if ( result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaplayer = MediaPlayer.create(PhrasesActivity.this, forAudio.getAudio());

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
