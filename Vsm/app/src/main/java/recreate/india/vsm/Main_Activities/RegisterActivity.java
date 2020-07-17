package recreate.india.vsm.Main_Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import recreate.india.vsm.Main_Activities.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText user_name;
    private EditText pass_word;
    private EditText confirm_password;
    private FirebaseAuth firebaseAuth;
    private Button regis_ter;
    FirebaseFirestore ff;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ff=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        user_name=findViewById(R.id.username);
        pass_word=findViewById(R.id.password);
        confirm_password=findViewById(R.id.confirmPassword);
        regis_ter=findViewById(R.id.register1);
        regis_ter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((pass_word.getText().toString())!=(confirm_password.getText().toString())){
                    firebaseAuth.createUserWithEmailAndPassword(user_name.getText().toString()+"@gmail.com",pass_word.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                firebaseUser=firebaseAuth.getCurrentUser();
                                Map<String,Object>map=new HashMap<>();
                                map.put("Email",firebaseUser.getEmail());
                                map.put("PhoneNumber","phone Number");
                                map.put("ImageUrl","imageURL");
                                ff.collection("Users").document(firebaseUser.getUid())
                                        .collection("PersonalInformation")
                                        .document("personalInformation").set(map);
                                Map<String,Object> credits=new HashMap<>();
                                credits.put("credits","100");
                                ff.collection("Users").document(firebaseUser.getUid()).collection("Credits")
                                        .document("Credits").set(credits);

                                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                            }
                            else{
                                Toast.makeText(RegisterActivity.this,"error",Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
                else{
                    Toast.makeText(RegisterActivity.this,"error",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}