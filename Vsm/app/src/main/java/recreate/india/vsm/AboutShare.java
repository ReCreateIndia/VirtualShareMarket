package recreate.india.vsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class AboutShare extends AppCompatActivity {
    private TextView textView;
    private  FirebaseFirestore ff;
    FirestoreRecyclerAdapter adapter;
    private List<PostModal> tech_list;
    private RecyclerView tech_recycler_view;
    private overallrecycleradapter blogRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_share);
        textView = findViewById(R.id.shareid);
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("shareid");
        textView.setText(message);
        ff = FirebaseFirestore.getInstance();
        tech_list=new ArrayList<>();
        tech_list.add(new PostModal("name",1));
        tech_list.add(new PostModal("name",2));
        tech_list.add(new PostModal("name",3));
        blogRecyclerAdapter=new overallrecycleradapter(tech_list,this);
        tech_recycler_view=findViewById(R.id.shareRecyclerView);
        tech_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        tech_recycler_view.setAdapter(blogRecyclerAdapter);
//        ff.collection("OurWorkPost").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                for(DocumentChange documentChange:queryDocumentSnapshots.getDocumentChanges()){
//                    PostModal postModal=documentChange.getDocument().toObject(PostModal.class);
//                    tech_list.add(postModal);
//                    blogRecyclerAdapter.notifyDataSetChanged();
//                }
//            }
//        });

    }
}