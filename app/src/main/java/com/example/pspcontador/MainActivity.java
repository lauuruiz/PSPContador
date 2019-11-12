package com.example.pspcontador;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private TextView tvContador;
    private Button btAsyntask, btThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        initEvents();
    }

    private void initComponents(){
        tvContador = findViewById(R.id.tvContador);
        btAsyntask = findViewById(R.id.btAsyntask);
        btThread = findViewById(R.id.btThread);
    }

    private void initEvents(){
        btAsyntask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyntaskContador().execute();
            }
        });

        btThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvContador.setText("20");
                new ThreadContador().start();
            }
        });
    }

    public class AsyntaskContador extends AsyncTask<Void, Integer, Void> {
        int progreso;

        @Override
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this, "Asyntask onPreExecute", Toast.LENGTH_SHORT).show();
            progreso = 20;
            tvContador.setText(Integer.toString(progreso));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while (progreso >= 0){
                Log.v("AsyntaskContador", ""+progreso);
                publishProgress(progreso);
                SystemClock.sleep(1000);
                progreso--;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progresos) {
            tvContador.setText(progresos[0].toString());
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(MainActivity.this, "Asyntask onPostExecute", Toast.LENGTH_SHORT).show();
            btAsyntask.setClickable(true);
            tvContador.setText("");
        }
    }

    public class ThreadContador extends Thread{
        int progreso = 20;

        @Override
        public void run() {
            while(progreso > 0){
                Log.v("ThreadContador", ""+progreso);
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                progreso--;
                tvContador.post(new Runnable() {
                    @Override
                    public void run() {
                        tvContador.setText(Integer.toString(progreso));
                    }
                });



            }
        }


    }
}
