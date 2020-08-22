package startup.carvaan.Main_Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import startup.carvaan.Main_Activities.R;


public class LoginActivity extends AppCompatActivity {
    private EditText user_name;
    private EditText pass_word;
    private FirebaseAuth firebaseAuth;
    private Button lo_gin;
    private ImageView googlesign;
    private TextView movetoregister;
    public static GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        movetoregister=findViewById(R.id.gotoregister);

        firebaseAuth=FirebaseAuth.getInstance();
        user_name=findViewById(R.id.username);
        pass_word=findViewById(R.id.password);
        lo_gin=findViewById(R.id.loginbutton);
        googlesign = findViewById(R.id.googlelogin);
        lo_gin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signInWithEmailAndPassword(user_name.getText().toString()+"@gmail.com",pass_word.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
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

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this,googleSignInOptions);
        googlesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gsignin();
            }
        });

    }
    private void gsignin()
    {
        Intent gintent = googleSignInClient.getSignInIntent();
        startActivityForResult(gintent,12345);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==12345)
        {
            final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this,"","Please Wait");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(LoginActivity.this,"LOGIN SUCCESSFUL",Toast.LENGTH_LONG).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            progressDialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this,"TASK UNSUCCESSFUL",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
            catch (ApiException e)
            {
                //Toast.makeText(LoginActivity.this,"LOGIN UNSUCCESSFUL API EXCEPTION",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                Log.i("api",e.getMessage());
            }
        }
    }
}