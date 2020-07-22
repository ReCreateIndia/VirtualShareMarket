package recreate.india.vsm.Main_Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import recreate.india.vsm.Constructor.PostModal;
import recreate.india.vsm.Constructor.credits;
import recreate.india.vsm.Dialog_Fragments.Dialog_buy;
import recreate.india.vsm.Dialog_Fragments.Dialog_sell;

import recreate.india.vsm.Dialog_Fragments.Like_Comment;
import recreate.india.vsm.Main_Activities.R;


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
    private TextView coins;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_share);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayOptions(android.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        View view= getSupportActionBar().getCustomView();
        coins=view.findViewById(R.id.coins);
        ff=FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        ff.collection("Users").document(firebaseUser.getUid()).collection("Credits")
                .document("Credits").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                DocumentSnapshot snapshot=documentSnapshot;

                credits.setCredits(snapshot.getString("credits"));
                coins.setText(credits.getCredits());
            }
        });
        shareRecyclerView=findViewById(R.id.shareRecyclerView);
        final Bundle bundle = getIntent().getExtras();
        shareid = bundle.getString("shareid");
        bottomNavigationView=findViewById(R.id.aboutsharebottomnavview);
        bottomNavigationView.setOnNavigationItemSelectedListener(navlistner);
        ff = FirebaseFirestore.getInstance();
        tech_list=new ArrayList<>();

        Query query = ff.collection("Shares").document(shareid).collection("Bloging");
        FirestoreRecyclerOptions<PostModal> options = new FirestoreRecyclerOptions.Builder<PostModal>().setQuery(query, PostModal.class).build();
        adapter = new FirestoreRecyclerAdapter<PostModal, PostViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final PostViewHolder postViewHolder, int i, @NonNull final PostModal postModal) {
                if(Integer.parseInt(postModal.getType())==2){
                    postViewHolder.userpostimage.setVisibility(View.VISIBLE);
                }
                postViewHolder.name.setText(postModal.getName());
                String s=firebaseUser.getEmail();
              //  postViewHolder.username.setText(postModal.getName());

                /*

                Set appropriate values for PostViewHolder.isliked and PostViewHolder.iscommented
                If the likes_map contains the current user then set PostViewHolder.isliked = true.
                If the comments_map contains the current user then set PostViewHolder.iscommented = true

                */
                if(postViewHolder.isliked)
                {
                    postViewHolder.like_icon.setImageResource(R.drawable.ic_baseline_thumb_up_24);
                    postViewHolder.like_text.setTextColor(getResources().getColor(R.color.like_comment));
                }
                if(postViewHolder.iscommented)
                {
                    postViewHolder.comment_text.setTextColor(getResources().getColor(R.color.like_comment));
                    postViewHolder.comment_icon.setImageResource(R.drawable.comment_green);
                }
                View.OnClickListener likelistener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!postViewHolder.isliked)
                        {
                            postViewHolder.like_icon.setImageResource(R.drawable.ic_baseline_thumb_up_24);
                            postViewHolder.like_text.setTextColor(getResources().getColor(R.color.like_comment));
                            postModal.getLikes().put(firebaseUser.getUid(),"liked");
                            //User has liked the post. Save it inside the backend.
                        }
                        else
                        {
                            postViewHolder.like_icon.setImageResource(R.drawable.like_black);
                            postViewHolder.like_text.setTextColor(getResources().getColor(R.color.black));
                            postModal.getLikes().remove(firebaseUser.getUid());

                            //User has disliked the post. Remove the like from the backend.
                        }
                    }
                };
                View.OnClickListener comment_listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        postViewHolder.write_comment_layout.setVisibility(View.VISIBLE);
                    }
                };
                postViewHolder.post_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!postViewHolder.iscommented)
                        {
                            postViewHolder.iscommented=true;
                            postViewHolder.comment_text.setTextColor(getResources().getColor(R.color.like_comment));
                            postViewHolder.comment_icon.setImageResource(R.drawable.comment_green);

                            //User has written a comment and wants to post it.
                            //Save the comment into the backend here.
                            //Comment is written inside the edittext write comment.
                        }
                    }
                });
                postViewHolder.like_icon.setOnClickListener(likelistener);
                postViewHolder.like_text.setOnClickListener(likelistener);
                postViewHolder.comment_icon.setOnClickListener(comment_listener);
                postViewHolder.comment_text.setOnClickListener(comment_listener);
                postViewHolder.showlikes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Like_Comment lcdialog = new Like_Comment();
                        Bundle bundle1 = new Bundle();
                        bundle1.putInt("choice",10);
                        bundle1.putSerializable("map",postModal.getComments());
                        lcdialog.setArguments(bundle1);
                        lcdialog.show(getSupportFragmentManager(),"Likes_Dialog");
                    }
                });
                postViewHolder.showcomments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Like_Comment lcdialog = new Like_Comment();
                        Bundle bundle1 = new Bundle();
                        bundle1.putInt("choice",20);
                        bundle1.putSerializable("map",postModal.getComments());
                        lcdialog.setArguments(bundle1);
                        lcdialog.show(getSupportFragmentManager(),"Comments_Dialog");

                    }
                });
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
       TextView name, comment_text, like_text, showcomments, showlikes;
       boolean isliked = false;
       boolean iscommented = false;
       TextView username;
       LinearLayout write_comment_layout;
       EditText write_comment;
       ImageButton post_comment;
       ImageView userpostimage, like_icon, comment_icon;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.CompanyName);
            userpostimage=itemView.findViewById(R.id.userPostImage);
            username=itemView.findViewById(R.id.username);
            showcomments = itemView.findViewById(R.id.showcomments);
            showlikes = itemView.findViewById(R.id.showlikes);
            write_comment_layout = itemView.findViewById(R.id.write_comment_layout);
            write_comment = itemView.findViewById(R.id.write_comment_edittext);
            post_comment = itemView.findViewById(R.id.post_comment);
            like_icon = itemView.findViewById(R.id.like_icon);
            comment_icon = itemView.findViewById(R.id.comment_icon);
            like_text = itemView.findViewById(R.id.like_text);
            comment_text = itemView.findViewById(R.id.comment_text);

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
                    ff.collection("Users").document(firebaseUser.getUid()).
                            collection("Shares").document(shareid).get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot snapshot=task.getResult();
                                    if(snapshot.getString("holding")==null||Integer.parseInt(snapshot.getString("holding"))==0){
                                        Toast.makeText(AboutShare.this,"You do not have any holdings of this share",Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Dialog_sell dialog_sell = new Dialog_sell();
                                        Bundle bundle1 = new Bundle();
                                        bundle1.putString("shareid",shareid);
                                        dialog_sell.setArguments(bundle1);
                                        dialog_sell.show(getSupportFragmentManager(),"Dialog_Sell");
                                    }
                                }
                            });
                    break;

            }
            return false;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.coin_menu,menu);
        return true;
    }

}