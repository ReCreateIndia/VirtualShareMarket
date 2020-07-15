package recreate.india.vsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.MessagePattern;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import recreate.india.vsm.Dialog_Fragments.Dialog_buy;
import recreate.india.vsm.Dialog_Fragments.Dialog_sell;

public class AboutShare extends AppCompatActivity  {
    private TextView textView;
    private int a;
    private  FirebaseFirestore ff;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirestoreRecyclerAdapter adapter;
    private List<PostModal> tech_list;
    private RecyclerView shareRecyclerView;
    private BottomNavigationView bottomNavigationView;
    credits credits=new credits();
    String shareid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_share);
        ff=FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        ff.collection("Users").document(firebaseUser.getUid()).collection("Credits")
                .document("Credits").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();

                credits.setCredits(documentSnapshot.getString("credits"));
            }
        });
        shareRecyclerView=findViewById(R.id.shareRecyclerView);
        Bundle bundle = getIntent().getExtras();
        shareid = bundle.getString("shareid");
        bottomNavigationView=findViewById(R.id.aboutsharebottomnavview);
        bottomNavigationView.setOnNavigationItemSelectedListener(navlistner);
        ff = FirebaseFirestore.getInstance();
        tech_list=new ArrayList<>();

        Query query = ff.collection("Shares").document(shareid).collection("Bloging");
        FirestoreRecyclerOptions<PostModal> options = new FirestoreRecyclerOptions.Builder<PostModal>().setQuery(query, PostModal.class).build();
        adapter = new FirestoreRecyclerAdapter<PostModal, PostViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i, @NonNull PostModal postModal) {
                if(Integer.parseInt(postModal.getType())==1){
                    postViewHolder.userposttext.setVisibility(View.VISIBLE);
                }
                if(Integer.parseInt(postModal.getType())==2){
                    postViewHolder.userpostimage.setVisibility(View.VISIBLE);
                }
                postViewHolder.name.setText(postModal.getName());
            }

            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oneforall, parent, false);
                return new PostViewHolder(view);
            }

        };
        shareRecyclerView.setLayoutManager(new LinearLayoutManager(AboutShare.this));
        shareRecyclerView.setAdapter(adapter);
    }
    public class PostViewHolder extends RecyclerView.ViewHolder {
       String type;
       TextView name;
       TextView userposttext;
       ImageView userpostimage;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.CompanyName);
            userpostimage=itemView.findViewById(R.id.userPostImage);
            userposttext=itemView.findViewById(R.id.userPostText);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navlistner=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.sharebuy:
                    Dialog_buy dialog_buy = new Dialog_buy();
                    Bundle bundle = new Bundle();
                    bundle.putString("shareid",shareid);
                    dialog_buy.setArguments(bundle);
                    dialog_buy.show(getSupportFragmentManager(),"Dialog_Buy");
                    break;
                case R.id.sharesell:
                    Dialog_sell dialog_sell = new Dialog_sell();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("shareid",shareid);
                    dialog_sell.show(getSupportFragmentManager(),"Dialog_Sell");
                    break;
            }
            return false;
        }
    };

}