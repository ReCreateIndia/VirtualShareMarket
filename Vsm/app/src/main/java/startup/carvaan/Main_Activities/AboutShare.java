package startup.carvaan.Main_Activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
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
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import hb.xvideoplayer.MxVideoPlayer;
import hb.xvideoplayer.MxVideoPlayerWidget;
import startup.carvaan.Constructor.PostModal;
import startup.carvaan.Constructor.credits;
import startup.carvaan.Dialog_Fragments.CommentDialog;
import startup.carvaan.Dialog_Fragments.Dialog_buy;
import startup.carvaan.Dialog_Fragments.Dialog_sell;

import startup.carvaan.Dialog_Fragments.LikeDialog;
import startup.carvaan.Dialog_Fragments.Like_Comment;
import startup.carvaan.Main_Activities.R;


public class AboutShare extends AppCompatActivity  {
    private BottomSheetBehavior bottomSheetBehavior;
    private TextView textView;
    private int a;
    private  FirebaseFirestore ff;
    private ImageView stonksimage;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirestoreRecyclerAdapter adapter;
    private LineChart stonkschart;
    private List<PostModal> tech_list;
    private RecyclerView shareRecyclerView;
    private BottomNavigationView bottomNavigationView;
    credits credits=new credits();
    String shareid;
    private TextView coins;
    public static JCVideoPlayerStandard current_vv;

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

//                postViewHolder.companyname.setText(postModal.getName());

//                if(Integer.parseInt(postModal.getType())==1){
//                    //postViewHolder.userpostimage.setVisibility(View.VISIBLE);
//                }
                //else
                if(true)//Integer.parseInt(postModal.getType())==2)
                {
                    postViewHolder.postimage.setVisibility(View.GONE);
                    postViewHolder.videoview.setVisibility(View.VISIBLE);

                    postViewHolder.videoview.setUp("https://firebasestorage.googleapis.com/v0/b/vitual-share-market.appspot.com/o/Nature%20Beautiful%20short%20video%20720p%20HD.mp4?alt=media&token=3ad4713e-73b1-4814-8173-bc0f2f4f03d7",JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,"POST");
                    Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/vitual-share-market.appspot.com/o/40390415410_4933f04732_b_660_280220071938_180820090351.jpg?alt=media&token=16db16e6-bac6-40c9-ac7c-dd82ae74367c").into(postViewHolder.videoview.thumbImageView);
                    //postViewHolder.videoview.thumbImageView.setImageURI(Uri.parse("https://firebasestorage.googleapis.com/v0/b/vitual-share-market.appspot.com/o/40390415410_4933f04732_b_660_280220071938_180820090351.jpg?alt=media&token=16db16e6-bac6-40c9-ac7c-dd82ae74367c"));

                }
                postViewHolder.companyname.setText(postModal.getName());
                String s=firebaseUser.getEmail();
                postViewHolder.username.setText(s.substring(0,s.length()-10));
                postViewHolder.noofcomments.setText(postModal.getNo_comments());
                //postViewHolder.nooflikes.setText(postModal.getNo_likes());
                postViewHolder.description.setText(postModal.getDescription());
                postViewHolder.comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommentDialog commentDialog  = new CommentDialog();
                        Bundle bundle = new Bundle();
                        bundle.putString("shareid",shareid);
                        commentDialog.setArguments(bundle);
                        commentDialog.show(getSupportFragmentManager(),"Comments");
                    }
                });
                postViewHolder.nooflikes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LikeDialog likeDialog  = new LikeDialog();
                        Bundle bundle = new Bundle();
                        bundle.putString("shareid",shareid);
                        likeDialog.setArguments(bundle);
                        likeDialog.show(getSupportFragmentManager(),"Likes");
                    }
                });
                if (Integer.parseInt(postModal.getType())==1){
                    postViewHolder.postimage.setVisibility(View.VISIBLE);
                }
                if (Integer.parseInt(postModal.getType())==2){
                    postViewHolder.videoview.setVisibility(View.VISIBLE);
                }
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

        View bottomsheet = findViewById(R.id.bottomsheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);
        bottomSheetBehavior.setHideable(false);
        stonksimage = bottomsheet.findViewById(R.id.stonksimage);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState==BottomSheetBehavior.STATE_EXPANDED) {
                    stonksimage.setRotation(180f);
                    bottomNavigationView.setVisibility(View.GONE);
                }
                else {
                    stonksimage.setRotation(0f);
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        stonkschart = bottomsheet.findViewById(R.id.stonkschart);
        ArrayList<Entry> yvalues = new ArrayList<>();
        yvalues.add(new Entry(1,25));
        yvalues.add(new Entry(2,50));
        yvalues.add(new Entry(3,75));
        yvalues.add(new Entry(4,60));
        yvalues.add(new Entry(5,35));
        yvalues.add(new Entry(6,80));
        yvalues.add(new Entry(7,40));
        LineDataSet set1 = new LineDataSet(yvalues,"Share Prices");
        set1.setColor(Color.GREEN);
        set1.setLineWidth(3f);
        set1.setFillAlpha(110);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        stonkschart.setData(data);
        stonkschart.getAxisRight().setEnabled(false);
        String[] values = new String[] {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        XAxis xAxis = stonkschart.getXAxis();
        xAxis.setValueFormatter(new myformatter(values));
        xAxis.setGranularity(1);

    }
    public class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView companyname,username,description,nooflikes,noofcomments,comment;
        ImageView postimage,likeicon;
       String type;
       TextView name, comment_text, like_text, showcomments, showlikes;
       boolean isliked = false;
       boolean iscommented = false;
       LinearLayout write_comment_layout;
       EditText write_comment;
       ImageButton post_comment;
       JCVideoPlayerStandard videoview;
       ImageView userpostimage, like_icon, comment_icon;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            companyname=itemView.findViewById(R.id.companyName);
            username=itemView.findViewById(R.id.username);

            description=itemView.findViewById(R.id.description);
            nooflikes=itemView.findViewById(R.id.number_of_likes);
            noofcomments=itemView.findViewById(R.id.number_of_comments);
            comment=itemView.findViewById(R.id.comments);
            likeicon=itemView.findViewById(R.id.like_icon);
            postimage=itemView.findViewById(R.id.userPostImage);

            write_comment_layout = itemView.findViewById(R.id.write_comment_layout);
            write_comment = itemView.findViewById(R.id.write_comment_edittext);
            post_comment = itemView.findViewById(R.id.post_comment);
            like_icon = itemView.findViewById(R.id.like_icon);
            videoview = itemView.findViewById(R.id.userPostVideo);

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

    @Override
    public void onBackPressed() {
        adapter.notifyDataSetChanged();
        super.onBackPressed();
    }
    public class myformatter extends ValueFormatter implements IAxisValueFormatter {
        private String[] myvalues;
        public myformatter(String[] myvalues)
        {
            this.myvalues = myvalues;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return myvalues[(int)value];
        }
    }
}