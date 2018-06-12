package com.example.android.deutsch;


import android.content.Context;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.deutsch.R;
import com.example.android.deutsch.Word;

import org.w3c.dom.Text;

import java.util.ArrayList;



public class WordAdapter extends ArrayAdapter<Word> {

    //resource id for the background color for this list of words
    private int mColorId;

    public WordAdapter(Context context, ArrayList<Word> word, int color){
        super(context, 0, word);
        mColorId = color;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position);

        TextView miwokWord = (TextView) listItemView.findViewById(R.id.miwok_words);
        //get the translation depend on currunt word position
        miwokWord.setText(currentWord.getMiwokTranslation());


        TextView englishWord = (TextView) listItemView.findViewById(R.id.english_words);
        englishWord.setText(currentWord.getDefaultTranslation());

        ImageView image = (ImageView) listItemView.findViewById(R.id.image);

        //check if an image is provided for this word or not
        if ( currentWord.hasImage() ) {
            image.setVisibility(View.VISIBLE);
            image.setImageResource(currentWord.getImage());
        }else {
            image.setVisibility(View.GONE);
        }


        // Set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.text_container);
        // find the color that the resource ID refers to
        int color = ContextCompat.getColor(getContext(), mColorId);
        //set the background
        textContainer.setBackgroundColor(color);


        return listItemView;
    }
}
