package recreate.india.vsm.Main_Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

import recreate.india.vsm.Constructor.sharemodel;
import recreate.india.vsm.Main_Activities.AboutShare;
import recreate.india.vsm.Main_Activities.R;



public class AllShareFragment extends Fragment implements GestureDetector.OnGestureListener{

    private RecyclerView tech_recycler_view, funded_recycler_view, third_recycler_view;
    private List<sharemodel> tech_list, funded_list, third_list;
    private FirebaseFirestore ff;
    private LinearLayout techll, fundll, thirdll;
    private ImageView techl, techr, fundl, fundr, thirdl, thirdr;
    private Button gotoshare;
    private FirestoreRecyclerAdapter adapter,adapter2;

    public AllShareFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_share, container, false);
        techll = view.findViewById(R.id.techlinearlayout);
        fundll = view.findViewById(R.id.fundedlinearlayout);
        thirdll = view.findViewById(R.id.thirdlinearlayout);
        techl = view.findViewById(R.id.techleft);
        techr = view.findViewById(R.id.techright);
        fundl = view.findViewById(R.id.fundedleft);
        fundr = view.findViewById(R.id.fundedright);
        thirdl = view.findViewById(R.id.thirdleft);
        thirdr = view.findViewById(R.id.thirdright);
        techll.setVisibility(View.VISIBLE);
        fundll.setVisibility(View.GONE);
        thirdll.setVisibility(View.GONE);
        tech_recycler_view=view.findViewById(R.id.techrecyclerview);
        funded_recycler_view=view.findViewById(R.id.fundedrecyclerview);
        techl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                techll.setVisibility(View.GONE);
                fundll.setVisibility(View.GONE);
                thirdll.setVisibility(View.VISIBLE);
            }
        });
        techr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                techll.setVisibility(View.GONE);
                fundll.setVisibility(View.VISIBLE);
                thirdll.setVisibility(View.GONE);
            }
        });
        fundl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                techll.setVisibility(View.VISIBLE);
                fundll.setVisibility(View.GONE);
                thirdll.setVisibility(View.GONE);
            }
        });
        fundr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                techll.setVisibility(View.GONE);
                fundll.setVisibility(View.GONE);
                thirdll.setVisibility(View.VISIBLE);
            }
        });
        thirdl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                techll.setVisibility(View.GONE);
                fundll.setVisibility(View.VISIBLE);
                thirdll.setVisibility(View.GONE);
            }
        });
        thirdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                techll.setVisibility(View.VISIBLE);
                fundll.setVisibility(View.GONE);
                thirdll.setVisibility(View.GONE);
            }
        });
        ff = FirebaseFirestore.getInstance();
        funded_recycler_view=view.findViewById(R.id.fundedrecyclerview);
        tech_recycler_view = view.findViewById(R.id.techrecyclerview);
        Query query = ff.collection("TechStartups");
        FirestoreRecyclerOptions<sharemodel> options = new FirestoreRecyclerOptions.Builder<sharemodel>().setQuery(query, sharemodel.class).build();
        adapter = new FirestoreRecyclerAdapter<sharemodel, AllShareFragment.PostViewHolder>(options) {

            @NonNull
            @Override
            public AllShareFragment.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
                return new AllShareFragment.PostViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull AllShareFragment.PostViewHolder postViewHolder, int i, @NonNull final sharemodel sharemodel) {
                postViewHolder.name.setText(sharemodel.getName());
                postViewHolder.aboutshare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getContext().startActivity(new Intent(getContext(), AboutShare.class).putExtra("shareid",sharemodel.getId()));
                    }
                });
            }
        };
        Query query2 = ff.collection("FundedStartups");
        FirestoreRecyclerOptions<sharemodel> options2 = new FirestoreRecyclerOptions.Builder<sharemodel>().setQuery(query2, sharemodel.class).build();
        adapter2 = new FirestoreRecyclerAdapter<sharemodel, AllShareFragment.PostViewHolder>(options2) {

            @NonNull
            @Override
            public AllShareFragment.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
                return new AllShareFragment.PostViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull AllShareFragment.PostViewHolder postViewHolder, int i, @NonNull final sharemodel sharemodel) {
                postViewHolder.name.setText(sharemodel.getName());
                postViewHolder.aboutshare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getContext().startActivity(new Intent(getContext(),AboutShare.class).putExtra("shareid",sharemodel.getId()));
                    }
                });
            }
        };
        funded_recycler_view.setAdapter(adapter2);
        funded_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        tech_recycler_view.setAdapter(adapter);
        tech_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }


    @Override
    public void onShowPress(MotionEvent e) {

    }


    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }


    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }


    @Override
    public void onLongPress(MotionEvent e) {

    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
    //ViewHolder


    public class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView name, desc;
        private ImageView postImage;
        private TextView date;
        private Button aboutshare;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.sharename);
            aboutshare=itemView.findViewById(R.id.gotoshare);


        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        adapter2.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
        adapter2.stopListening();
    }
}