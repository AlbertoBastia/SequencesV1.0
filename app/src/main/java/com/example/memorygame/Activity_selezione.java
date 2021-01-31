package com.example.memorygame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_selezione extends AppCompatActivity {
    Button btn3x3,btn4x4,btn5x5,btn6x6,btn7x7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selezione);
        Intent i= getIntent();
        if(i.hasExtra("AllertDialog")) {
            int punteggio= i.getIntExtra("Punteggio",0);
            new AlertDialog.Builder(Activity_selezione.this).setTitle("HAI PERSO").setMessage("HAI TOTALIZZATO " + Integer.toString(punteggio) + " PUNTI").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        }
        //getIntent(); prende l'intent passato
        btn3x3=findViewById(R.id.btn_3x3);
        btn3x3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApriActivity(3);
            }
        });
        btn4x4=findViewById(R.id.btn_4x4);
        btn4x4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApriActivity(4);
            }
        });
        btn5x5=findViewById(R.id.btn_5x5);
        btn5x5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApriActivity(5);
            }
        });
        btn6x6=findViewById(R.id.btn_6x6);
        btn6x6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApriActivity(6);
            }
        });
        btn7x7=findViewById(R.id.btn_7x7);
        btn7x7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApriActivity(7);
            }
        });
    }

    public void ApriActivity(int aux){
        Intent intent_gioco=new Intent(Activity_selezione.this,Activity_gioco.class);
        intent_gioco.putExtra("tabella",aux);
        startActivity(intent_gioco);



    }

}
