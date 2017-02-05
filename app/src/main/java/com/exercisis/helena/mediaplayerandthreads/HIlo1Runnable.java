package com.exercisis.helena.mediaplayerandthreads;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by helena on 4/02/17.
 */

public class HIlo1Runnable implements Runnable{

    //Variable tipo Contexto
    // Para recoger el contexto de la aplicación
    private Context context;

    //Creación de un objeto MediaPLayer
    private MediaPlayer mMediaPlayer;

    // Variable para parar el hilo
    private boolean stopMe;

    // Variable para pausar el hilo
    private boolean pausaMe;

    private Object pauseLock;

    // Constructor
    public HIlo1Runnable(Context context){
        this.context = context;
        stopMe = false;
        pauseLock = new Object();
    }

    @Override
    public void run() {
        // reproducimos el sonido 3 veces
        for (int i =0; i<=3; i++ ){
            // si está parado
            if (stopMe){
                // Si es verdadro que salga del for
                break;
            }

            // Si el Mplayer es diferente a null, es decir si está vivo el objeto
            // hago un release que es para liberar los recursos
            if(mMediaPlayer != null){
                mMediaPlayer.release();
                mMediaPlayer = MediaPlayer.create(context, R.raw.goodnight);

                //ahora le decimos que empiece a reproducirlo
                mMediaPlayer.start();

                // que tarde 3 segundos cada vez que hay una interacion
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    return;
                }

                // hago una sección Synchronized con el pause lock
                synchronized (pauseLock){
                    // mientras pauseMe sea verdadero  (mietras esté pausado)-> pauseLock.wait
                    // mientras esté pausado estará esperando a que suceda algo
                    while (pausaMe){
                        try {
                            pauseLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }

    }

    // Para que cuando salga de la actividad no siga
    // sonando
    public void pause(){
        synchronized (pauseLock){
            pausaMe = true;
        }
    }

    public void reanuda(){
        synchronized (pauseLock){
            pausaMe = false;
            // objeto que notifica los cambios
            pauseLock.notifyAll();
        }


    }

    public void termina(){
        // la ponemos a true porque se va a parar el hilo
        stopMe = true;

    }

}
