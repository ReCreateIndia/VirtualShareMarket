package recreate.india.vsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        Thread t=new Thread(){
            public void run(){
                try {
                    sleep(2000);
                }catch (Exception e){

                }
            }
        };
        t.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseUser != null) {
                startActivity(new Intent(SplashScreen.this,MainActivity.class));
        }
    }
}