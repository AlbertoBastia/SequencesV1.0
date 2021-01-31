package com.example.memorygame;

import android.app.AlertDialog;
import android.app.AppComponentFactory;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.Transliterator;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.Touch;
import android.text.style.BackgroundColorSpan;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.Semaphore;

import androidx.annotation.ContentView;
import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_gioco extends AppCompatActivity {

    private int griglia;
    private LinearLayout schermo ;
    private TextView txtPunteggio;
    private int punteggio = 0,lung=3;
    private int[] lOrdine=new int[100];
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioco);
        Intent i= getIntent(); //prende l'intent passato
        griglia= i.getIntExtra("tabella",0);



        //genero le variabili necessarie
        int aux=1;//variabile ausiliaria per grandezza bottoni

        Button btnAux ;

        //prelevo la grandezza dello schermo
        Display dis=getWindowManager().getDefaultDisplay();
        Point pt= new Point();
        dis.getSize(pt);



        //Genero dei layoutParams

        RelativeLayout.LayoutParams parContainer =new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,pt.y/(griglia+aux));
        parContainer.addRule(RelativeLayout.BELOW);

        //schermo intero(container verticale
        schermo =new LinearLayout(this);
        schermo.setOrientation(LinearLayout.VERTICAL);

        //creo i parametri per il bottone
        RelativeLayout.LayoutParams parBtn= new RelativeLayout.LayoutParams(pt.x/griglia, pt.y/(griglia+aux));


        //container Orizzontale
        LinearLayout lnContainer;

        //testo per il punteggio
        txtPunteggio= new TextView(this);
        //txtPunteggio.setText("PUNTEGGIO: 0");
        txtPunteggio.setTextSize(20);
        //int aux2=50;
        //txtPunteggio.setId(aux);

        addContentView(schermo, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));

        //aggiungo la prima riga (punteggio)
        lnContainer= new LinearLayout(this);
        lnContainer.addView(txtPunteggio);
        schermo.addView(lnContainer);

        for(int k=0;k<griglia; k++) {
            lnContainer= new LinearLayout(this);

            for (int j = 1; j <= griglia; j++) {
                //genero un nuovo bottone
                btnAux = new Button(Activity_gioco.this);

                //Modifico le sue caratteristiche
                btnAux.setBackgroundColor(Color.rgb(k*(255/griglia),j*(255/griglia),255-j));
                btnAux.setText(Integer.toString(j+(k*griglia)));
                btnAux.setLayoutParams(parBtn);
                btnAux.setId(j+(k*griglia));


                lnContainer.addView(btnAux);

                //Toast.makeText(this,Integer.toString(lstVw.get(j+(k*griglia)).getId()),Toast.LENGTH_LONG).show();
            }
            schermo.addView(lnContainer,parContainer);
        }
        Gioco();
        Sequence();
       }

    public void Gioco() {
           int pos = 0;
           int[] lOrdine=new int[100];


           for (int i=1;i<=griglia*griglia;i++) {
               Button b=findViewById(i);
               b.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       
                       if(b.getId()!=GetPosOrdine(pos)){
                           //qualcoda per dire che ha perso
                           //Toast.makeText(Activity_gioco.this,Integer.toString(b.getId())+Integer.toString(GetPosOrdine(pos))+"hai perso",Toast.LENGTH_SHORT).show();
                           //Toast.makeText(Activity_gioco.this,"HAI PERSO",Toast.LENGTH_SHORT).show();
                           //lettura
                           SharedPreferences sharedPref = Activity_gioco.this.getPreferences(Context.MODE_PRIVATE);
                           int defaultValue = getResources().getInteger(0);
                           int highScore = sharedPref.getInt("punteggio", defaultValue);
                           if(punteggio>highScore) {
                               //scrittura
                               SharedPreferences.Editor editor = sharedPref.edit();
                               editor.putInt("punteggio", punteggio);
                               editor.apply();
                           }
                           Intent intent_selezione =new Intent(Activity_gioco.this,Activity_selezione.class); //creazione intent e assegnamento activity
                           intent_selezione.putExtra("AllertDialog",true);
                           intent_selezione.putExtra("Punteggio",punteggio);
                           startActivity(intent_selezione);

                       }
                       else if(getPos()!= getLung()) {
                           punteggio+=griglia;
                           txtPunteggio.setText("Punteggio: " + punteggio);
                           AumentaPos(1);
                       }
                       if(getPos() == getLung()){
                           AumentaPos(0);
                           Sequence();
                       }

                       //Toast.makeText(Activity_gioco.this,Integer.toString(b.getId()),Toast.LENGTH_SHORT).show();
                  }
               });
           }


        }

        //illumina la sequenza
        private void Sequence(){
            boolean pausa = false;

            String str="";
            for(int i=0;i<1+lung;i++) {
                lOrdine[i] = (int) (System.currentTimeMillis() % (griglia * griglia)) + 1;
                str+= Integer.toString(lOrdine[i]);
                Long strt=System.currentTimeMillis();
                Long strt2=System.currentTimeMillis();
                while(System.currentTimeMillis()-strt!=strt2*strt2%13);
                if(i!=lung){
                    str+=",";
                }
            };
            txtPunteggio.setText(str);
            lung++;

        }


        private void AumentaPos(int aux){
            if(aux==0){
                pos=0;
            }
            else pos++;
            //Toast.makeText(Activity_gioco.this,"pos:"+Integer.toString(pos),Toast.LENGTH_SHORT).show();
        }

        private int GetPosOrdine(int pos){
            //Toast.makeText(Activity_gioco.this,"Ordine in pos:"+Integer.toString(lOrdine[getPos()])+";"+Integer.toString(getPos()),Toast.LENGTH_SHORT).show();
            return lOrdine[getPos()];
        }

        private int getPos(){
            return pos;
        }

        private int getLung(){
            return lung;
        }
}
