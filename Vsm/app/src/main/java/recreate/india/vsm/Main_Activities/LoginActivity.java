package recreate.india.vsm.Main_Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import recreate.india.vsm.Main_Activities.R;


public class LoginActivity extends AppCompatActivity {
    private EditText user_name;
    private EditText pass_word;
    private FirebaseAuth firebaseAuth;
    private Button lo_gin;
    private TextView movetoregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        movetoregister=findViewById(R.id.gotoregister);
        firebaseAuth=FirebaseAuth.getInstance();
        user_name=findViewById(R.id.username);
        pass_word=findViewById(R.id.password);
        lo_gin=findViewById(R.id.loginbutton);
        lo_gin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signInWithEmailAndPassword(user_name.getText().toString()+"@gmail.com",pass_word.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        }
                        else{
                            Toast.makeText(LoginActivity.this,"error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

        });
        movetoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });




    }
}