package com.example.android.deutsch;


public class Word {

    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private final int hImage = -1;
    private int mImage = hImage;
    private int mAudio;

    public Word(String defaultTranslation, String miwokTranslation, int image, int audio){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImage = image;
        mAudio = audio;
    }

    public Word(String defaultTranslation, String miwokTranslation, int audio){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudio = audio;
    }

    public Word(String defaultTranslation, String miwokTranslation){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
    }


    public String getDefaultTranslation(){ return mDefaultTranslation; }

    public String getMiwokTranslation(){ return mMiwokTranslation; }

    public boolean hasImage(){ return mImage != hImage ; }

    public int getImage(){ return mImage; }

    public int getAudio(){ return mAudio; }
}
