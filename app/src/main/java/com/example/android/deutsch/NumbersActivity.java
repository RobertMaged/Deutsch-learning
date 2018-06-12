package com.example.android.deutsch;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.android.deutsch.R;
import com.example.android.deutsch.Word;
import com.example.android.deutsch.WordAdapter;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    //toggle between play and ear icon
    ImageView animation;

    private MediaPlayer mMediaplayer;

    //on audio complete listener
    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            //stop playback and release the media resources
            releaseMediaPlayer();
        }
    };

    private AudioManager mAudioManager;

    //audio Focus
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            switch (i){
                //case lost audio focus for a short time
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT: { mMediaplayer.pause(); mMediaplayer.seekTo(0); break; }
                //media not stopped but played in lower volume
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK: { mMediaplayer.pause(); mMediaplayer.seekTo(0); break; }
                //app has the focus and can complete audio playing
                case AudioManager.AUDIOFOCUS_GAIN: { mMediaplayer.start(); break; }
                //app lose the focus
                case AudioManager.AUDIOFOCUS_LOSS: { releaseMediaPlayer(); break; }
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word);

        //setup audio manager to request the focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

       //create the list of words
        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("one", "Eins", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "Zwei", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "Drei", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "Vier", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "Fünf", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "Sechs", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "Sieben", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "Acht", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "Neun", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "Zehn", R.drawable.number_ten, R.raw.number_ten));


        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //get the word object at the given position the user clicked on
                Word forAudio = words.get(i);

                releaseMediaPlayer();

                //request audio focus to play audio
                //app request short time focus
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if ( result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //prepare the audio file
                    mMediaplayer = MediaPlayer.create(NumbersActivity.this, forAudio.getAudio());
                    //set the play icon to ear icon when play audio
                    animation = (ImageView) view.findViewById(R.id.pause_play);
                    animation.setImageResource(R.drawable.baseline_hearing_white_18);

                    //play audio
                    mMediaplayer.start();
                    //detect when audio file finished
                    mMediaplayer.setOnCompletionListener(mOnCompletionListener);
                }
            }
        });

    }

    @Override
    protected void onStop(){
        super.onStop();
        //release resources when activity stopped
        releaseMediaPlayer();
    }


    private void releaseMediaPlayer(){

        //if mMediaplayer is not null so it plays audio file at the moment
        if( mMediaplayer != null) {
            //get the play icon back
            animation.setImageResource(R.drawable.ic_play_arrow);
            //release audio file
            mMediaplayer.release();
            mMediaplayer = null;
            //app do not need focus any more so abandon it
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}
