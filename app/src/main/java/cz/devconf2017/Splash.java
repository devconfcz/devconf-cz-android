package cz.devconf2017;

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

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    super.run();
                    sleep(1500);
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    Intent i = new Intent(Splash.this, MainActivity.class);
                    if(getIntent().getExtras() != null) {
                        i.putExtras(getIntent().getExtras());
                    }
                    startActivity(i);
                    finish();
                }
            }
        };
        timerThread.start();
    }
}
