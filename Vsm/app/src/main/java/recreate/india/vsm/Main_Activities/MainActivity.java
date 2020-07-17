package recreate.india.vsm.Main_Activities;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import recreate.india.vsm.Constructor.credits;
import recreate.india.vsm.Main_Fragments.AllShareFragment;
import recreate.india.vsm.Main_Fragments.EarnMoneyFragment;
import recreate.india.vsm.Main_Fragments.MyShareFragment;
import recreate.india.vsm.Main_Activities.R;


public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DrawerLayout drawerLayout;
    FirebaseFirestore ff;
    GestureDetector gestureDetector;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private BottomNavigationView bottomNavigationView;
    private ActionBar actionBar;
    TextView coins;
    credits credits=new credits();
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Karvaan");
        getSupportActionBar().setDisplayOptions(android.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        View view= getSupportActionBar().getCustomView();
        coins=view.findViewById(R.id.coins);
        ff=FirebaseFirestore.getInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AllShareFragment()).commit();
        bottomNavigationView = findViewById(R.id.aboutsharebottomnavview);
        NavigationView navigationView = (NavigationView) findViewById(R.id.n1);
        drawerLayout = findViewById(R.id.drawerlayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
                ff.collection("Users").document(firebaseUser.getUid()).collection("Credits")
                .document("Credits").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot=task.getResult();

                credits.setCredits(documentSnapshot.getString("credits"));
                coins.setText(credits.getCredits());
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()) {
                case R.id.AllShares:
                    selectedFragment = new AllShareFragment();
                    break;
                case R.id.MyShares:
                    selectedFragment = new MyShareFragment();
                    break;
                case R.id.EarnCredits:
                    selectedFragment = new EarnMoneyFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.coin_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
