package cz.devconf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jridky on 10.12.16.
 */

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        final boolean error = getIntent().getBooleanExtra("null",false);
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    super.run();
                    sleep(1500);
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    Intent i = new Intent(Splash.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        timerThread.start();
    }
}
