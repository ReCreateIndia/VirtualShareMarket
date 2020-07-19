package recreate.india.vsm.Main_Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import recreate.india.vsm.Main_Activities.R;

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
                finally{
                    if (firebaseUser != null) {
                        startActivity(new Intent(SplashScreen.this,MainActivity.class));
                        finish();
                    }
                    else{
                        startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                        finish();
                    }
                }
            }
        };
        t.start();
    }
}