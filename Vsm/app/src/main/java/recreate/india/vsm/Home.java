package recreate.india.vsm;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
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


public class Home extends Fragment {

    private RecyclerView tech_recycler_view,funded_recycler_view,third_recycler_view;
    private List<PostModal> tech_list,funded_list,third_list;
    private FirebaseFirestore ff;
    private BlogRecyclerAdapter blogRecyclerAdapter;
    private LinearLayout techll,fundll,thirdll;
    private ImageView techl,techr,fundl,fundr,thirdl,thirdr;
    Button gotoshare;
    public Home() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        techll=view.findViewById(R.id.techlinearlayout);
        fundll=view.findViewById(R.id.fundedlinearlayout);
        thirdll=view.findViewById(R.id.thirdlinearlayout);
        techl=view.findViewById(R.id.techleft);
        techr=view.findViewById(R.id.techright);
        fundl=view.findViewById(R.id.fundedleft);
        fundr=view.findViewById(R.id.fundedright);
        thirdl=view.findViewById(R.id.thirdleft);
        thirdr=view.findViewById(R.id.thirdright);
        techll.setVisibility(View.VISIBLE);
        fundll.setVisibility(View.GONE);
        thirdll.setVisibility(View.GONE);
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
        ff=FirebaseFirestore.getInstance();
        tech_list=new ArrayList<>();
        tech_list.add(new PostModal("Tech Private Ltd"));
        tech_list.add(new PostModal("Tech Private Ltd"));
        tech_list.add(new PostModal("Tech Private Ltd"));
        tech_list.add(new PostModal("Tech Private Ltd"));
        tech_list.add(new PostModal("Tech Private Ltd"));
        tech_list.add(new PostModal("Tech Private Ltd"));
        blogRecyclerAdapter=new BlogRecyclerAdapter(tech_list,getContext());
        tech_recycler_view=view.findViewById(R.id.techrecyclerview);
        tech_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        tech_recycler_view.setAdapter(blogRecyclerAdapter);

        funded_list=new ArrayList<>();
        funded_list.add(new PostModal("Funded Private Ltd"));
        blogRecyclerAdapter=new BlogRecyclerAdapter(funded_list,getContext());
        funded_recycler_view=view.findViewById(R.id.fundedrecyclerview);
        funded_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        funded_recycler_view.setAdapter(blogRecyclerAdapter);

        third_list=new ArrayList<>();
        third_list.add(new PostModal("Third Private Ltd"));
        blogRecyclerAdapter=new BlogRecyclerAdapter(third_list,getContext());
        third_recycler_view=view.findViewById(R.id.thirdrecyclerview);
        third_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        third_recycler_view.setAdapter(blogRecyclerAdapter);
        ff.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange documentChange:queryDocumentSnapshots.getDocumentChanges()){
                    PostModal postModal=documentChange.getDocument().toObject(PostModal.class);
                    tech_list.add(postModal);
                    blogRecyclerAdapter.notifyDataSetChanged();
                }
            }
        });
        return view;
    }

    //V
}