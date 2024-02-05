package com.example.texttospeech;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button buttonSpeak;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        buttonSpeak = findViewById(R.id.buttonSpeak);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.getDefault());
                } else {
                    Toast.makeText(MainActivity.this, "Error al inicializar Text-to-Speech", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToRead = editText.getText().toString();
                if (!textToRead.isEmpty()) {
                    speakText(textToRead);
                } else {
                    Toast.makeText(MainActivity.this, "Introduce texto antes de pulsar Hablar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void speakText(String text) {
        HashMap<String, String> params = new HashMap<>();
        params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "utteranceId");

        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, params);
        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                // Acciones al inicio de la reproducción
            }

            @Override
            public void onDone(String utteranceId) {
                // Acciones al finalizar la reproducción
            }

            @Override
            public void onError(String utteranceId) {
                // Acciones en caso de error
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
