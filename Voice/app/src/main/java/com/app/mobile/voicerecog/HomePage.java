package com.app.mobile.voicerecog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Locale;

public class HomePage extends AppCompatActivity {

    TextView rText;
    TextView wCount, charCount;
    SpeechRecognizer recSpeechRecog;
    Intent recSpeechIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        rText = findViewById(R.id.recText);


        rText.setMovementMethod(new ScrollingMovementMethod());

        recSpeechRecog = SpeechRecognizer.createSpeechRecognizer(this);

        //create a new intent
        recSpeechIntent =new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recSpeechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        //get the by-default language used in device
        recSpeechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        recSpeechRecog.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            //On speech record it will store the character in textView space
            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> details = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                if(details != null)
                    rText.setText(details.get(0));
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });



        //On click of rec what different action should be taken and what it will do.
        findViewById(R.id.recButton).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction())
                {
                    //when finger is removed from the rec button
                    case MotionEvent.ACTION_UP:
                        recSpeechRecog.stopListening();
                        rText.setText("You will see the input here");
                        break;

                    //when finger is placed on the rec button
                    case MotionEvent.ACTION_DOWN:
                        rText.setText("");
                        rText.setHint("Listening your voice");
                        recSpeechRecog.startListening(recSpeechIntent);
                        break;
                }
                return  false;
            }
        });

        count();
    }


    private void count(){
        wCount = (TextView)findViewById(R.id.wordCount);
        charCount = (TextView)findViewById(R.id.charCountText);
        charCount.setText("Characters:0");
        wCount.setText("Words:0");

        rText.addTextChangedListener(textCount);
    }

    //Textwatcher method to find the no of character and word
    TextWatcher textCount = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
           // charCount.setText("Characters :" +String.valueOf(charSequence.length()));

            //call the charcount function to count no of character
            charCount.setText("Characters :" +charcterCount(charSequence.toString()));

            //call the wordcount function to count no of word
            wCount.setText("Words :" +wordCount(charSequence.toString()));
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    //count the no of character in the speech
    public static long charcterCount(String worCount){
        long character = 0;
        for (int i = 0; i< worCount.length(); i++){
            if(worCount.charAt(i) != ' ')
                character++;
        }
        return character;
    }

    //count the no of words  in the speech
    public static long wordCount(String lcount){
        long words = 0;
        int i = 0;
        boolean preSpace = true;
        while(i < lcount.length()){
            char ch=lcount.charAt(i++);
            boolean currSpace = Character.isWhitespace(ch);
            if (preSpace && !currSpace){
                words++;
            }
            preSpace = currSpace;

        }
        return words;
    }


    /*private void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)){
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package :" +getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }*/

}





