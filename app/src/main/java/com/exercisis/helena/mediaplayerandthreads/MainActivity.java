package com.exercisis.helena.mediaplayerandthreads;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnRunnable;
    Button btnThread;

    Context mContext;

    HIlo1Runnable mHIlo1Runnable;
    Thread hilo1;

    Hilo2Thread hilo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        btnRunnable = (Button) findViewById(R.id.runnableBtn);
        btnRunnable.setOnClickListener((v)->{
            Toast.makeText(this, "CLicado Botón Runnabla", Toast.LENGTH_SHORT).show();

            // Si el hilo1 es nulo
            // O si el hilo 2 es diferente de Está vivo?
            // Creo una instancia
            if (hilo1 == null || !hilo1.isAlive()){
                mHIlo1Runnable = new HIlo1Runnable(mContext);
                hilo1 = new Thread(mHIlo1Runnable);
                hilo1.setDaemon(true); // cuando se cierre la app,
                // aunque haya un hilo abierto se cerraraan cuando se cierre la app
                // los hilos se cierran cuando se cierra la app
                hilo1.start();
                Toast.makeText(this, "Hilo1 iniciado", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(mContext, "Hilo 1 se detendrá", Toast.LENGTH_SHORT).show();
                // pongo la variable stopMe a true
                mHIlo1Runnable.termina();
            }
        });

        btnThread = (Button) findViewById(R.id.threadBtn);
        btnThread.setOnClickListener((v)->{
            Toast.makeText(this, "CLicado Botón Runnabla", Toast.LENGTH_SHORT).show();

            if(hilo2 == null || !hilo2.isAlive()){
                hilo2 = new Hilo2Thread(mContext);
                hilo2.setDaemon(true);
                hilo2.start();
            }
            else {
                Toast.makeText(mContext, "Hilo 2 se detendrá", Toast.LENGTH_SHORT).show();
                hilo2.termina();
            }


        });
    } // Fin onCreate

    @Override
    protected void onResume() {
        // si el hilo runnable es diferente de null, lo reanudamos
        if (mHIlo1Runnable != null){
            mHIlo1Runnable.reanuda();
        }

        if (hilo2 != null){
            hilo2.reanuda();
        }
        super.onResume();
    }


    @Override
    protected void onPause() {

        if (mHIlo1Runnable != null){
            mHIlo1Runnable.pause();
        }

        if (hilo2 != null){
            hilo2.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mHIlo1Runnable != null){
            mHIlo1Runnable.termina();
        }

        if (hilo2 != null){
            hilo2.termina();
        }
        super.onDestroy();
    }
}
