package com.example.memorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button btnStart; //dichiarazione bottone
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String aux="Punteggio.txt";
        TextView txtvW=findViewById(R.id.txtVwPunteggio);

        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        int defaultValue = getResources().getInteger(0);
        int highScore = sharedPref.getInt("punteggio", defaultValue);
        txtvW.setText("RECORD"+highScore);
        btnStart=findViewById(R.id.BtnStart); //assegnazione valore alla variabile
        btnStart.setOnClickListener(new View.OnClickListener() { //creazione on click listener
            @Override
            public void onClick(View v) {
                Intent intent_selezione =new Intent(MainActivity.this,Activity_selezione.class); //creazione intent e assegnamento activity
                startActivity(intent_selezione); //passaggio all'attiviti
            }
        });

    }
}