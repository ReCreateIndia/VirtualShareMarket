package startup.carvaan.Main_Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ImageView app_logo;
    TextView app_motto, app_name;
    Animation top, bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        app_logo = findViewById(R.id.applogo);
        app_motto = findViewById(R.id.appmotto);
        app_name = findViewById(R.id.appname);
        top = AnimationUtils.loadAnimation(this, R.anim.top);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom);
        app_logo.setAnimation(top);
        app_name.setAnimation(bottom);
        app_motto.setAnimation(bottom);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gotonextpage();
            }
        }, 1000);
//         Thread t= new Thread(){
//            public void run(){
//                try {
//                    sleep(2000);
//                }catch (Exception e){
//
//                }
//                finally{
//                    if (firebaseUser != null) {
//                        startActivity(new Intent(SplashScreen.this,MainActivity.class));
//                        finish();
//                    }
//                    else{
//                        startActivity(new Intent(SplashScreen.this,LoginActivity.class));
//                        finish();
//                    }
//                }
//            }
//        };
//        t.start();
    }

    private void gotonextpage() {
        if (firebaseUser != null) {
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(SplashScreen.this, LoginActivity.class));
            finish();
        }
    }
}