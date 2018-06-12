package com.example.android.deutsch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.android.deutsch.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView coffee = (TextView) findViewById(R.id.numbers);
        coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openList(NumbersActivity.class);
            }
        });

        TextView courtCounter = (TextView) findViewById(R.id.family);
        courtCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openList(FamilyActivity.class);
            }
        });

        TextView practicalSet = (TextView) findViewById(R.id.colors);

        practicalSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openList(ColorsActivity.class);
            }
        });

        TextView miwokApp = (TextView) findViewById(R.id.phrases);

        miwokApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openList(PhrasesActivity.class);
            }
        });
    }


    private void openList(java.lang.Class<?> cls) {
        Intent i = new Intent(MainActivity.this, cls);
        startActivity(i);
    }
}
