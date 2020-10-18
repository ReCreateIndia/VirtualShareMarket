package startup.carvaan.Main_Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText user_name;
    private EditText pass_word;
    private TextInputLayout confirmLayout;
    private FirebaseAuth firebaseAuth;
    private Button regis_ter;
    private TextView movetologin;
    private EditText confirmPassword;
    FirebaseFirestore ff;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        confirmPassword = findViewById(R.id.confirmPassword);
//        confirmLayout = findViewById(R.id.confirmLayout);
        ff = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user_name = findViewById(R.id.username);
        pass_word = findViewById(R.id.password);
        regis_ter = findViewById(R.id.register1);
        movetologin = findViewById(R.id.gotologin);
        regis_ter.setEnabled(false);
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password = pass_word.getText().toString();
                if (editable.length() > 0 && password.length() > 0) {
                    if (!confirmPassword.getText().toString().equals(password)) {
                        // give an error that password and confirm password not match
                        confirmLayout.setErrorEnabled(true);
                        confirmLayout.setError("You need to enter a name");
                    } else {
                        confirmLayout.setErrorEnabled(false);
                        confirmLayout.setError(null);
                        regis_ter.setEnabled(true);
                    }

                }
            }
        });

        regis_ter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.createUserWithEmailAndPassword(user_name.getText().toString() + "@gmail.com", pass_word.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        firebaseUser = firebaseAuth.getCurrentUser();
                        Map<String, Object> map = new HashMap<>();
                        map.put("Email", firebaseUser.getEmail());
                        map.put("PhoneNumber", "phone Number");
                        map.put("ImageUrl", "imageURL");
                        ff.collection("Users").document(firebaseUser.getUid())
                                .collection("PersonalInformation")
                                .document("personalInformation").set(map);
                        Map<String, Object> credits = new HashMap<>();
                        credits.put("credits", "100");
                        ff.collection("Users").document(firebaseUser.getUid()).collection("Credits")
                                .document("Credits").set(credits);

                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = e.getMessage();
                        Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        movetologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

    }

}