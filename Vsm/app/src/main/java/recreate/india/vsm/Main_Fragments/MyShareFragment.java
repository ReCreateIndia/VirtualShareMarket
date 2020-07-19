package recreate.india.vsm.Main_Fragments;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import recreate.india.vsm.Constructor.MyShareInfo;
import recreate.india.vsm.Constructor.sharemodel;
import recreate.india.vsm.Main_Activities.AboutShare;
import recreate.india.vsm.Main_Activities.R;


public class MyShareFragment extends Fragment {

    private RecyclerView myshares_recyclerview;
    private FirebaseFirestore ff;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    public MyShareFragment(){}
    private FirestoreRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_my_share, container, false);
        myshares_recyclerview = view.findViewById(R.id.myshares_recyclerview);
        ff=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        Query query = ff.collection("Users").document(firebaseUser.getUid()).collection("Shares");
        FirestoreRecyclerOptions<MyShareInfo> options = new FirestoreRecyclerOptions.Builder<MyShareInfo>().setQuery(query, MyShareInfo.class).build();
        adapter = new FirestoreRecyclerAdapter<MyShareInfo, MyShareFragment.PostViewHolder>(options) {

            @NonNull
            @Override
            public MyShareFragment.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_myshare_item, parent, false);
                return new MyShareFragment.PostViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MyShareFragment.PostViewHolder postViewHolder, int i, @NonNull final MyShareInfo myShareInfo) {

                postViewHolder.holdings.setText(myShareInfo.getHolding());
                postViewHolder.trade.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(),AboutShare.class).putExtra("shareid",myShareInfo.getId()));
                    }
                });
            }
        };
        myshares_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        myshares_recyclerview.setAdapter(adapter);
        return view;
    }
    public class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView holdings;
        private Button trade;


        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            trade=itemView.findViewById(R.id.trade);
            holdings=itemView.findViewById(R.id.myHoldings);


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
}