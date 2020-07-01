package recreate.india.vsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread t=new Thread(){
            public void run(){
                try {
                    sleep(2000);
                }catch (Exception e){

                }finally {
                            startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                }
            }
        };
        t.start();
    }
}