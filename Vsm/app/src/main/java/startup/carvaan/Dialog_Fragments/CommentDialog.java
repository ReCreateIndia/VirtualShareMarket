package startup.carvaan.Dialog_Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import startup.carvaan.Constructor.Comments;
import startup.carvaan.Main_Activities.R;


public class CommentDialog extends DialogFragment {
    private FirebaseFirestore ff;
    private FirestoreRecyclerAdapter adapter;
    private RecyclerView commentsView;
    private EditText comment;
    private ImageView post_button,user_image;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    String id;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.like_comment_dialog,null,false);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        comment=view.findViewById(R.id.write_comment_edittext);
        post_button=view.findViewById(R.id.post_comment);
        user_image=view.findViewById(R.id.commenting_user_image);
        ff=FirebaseFirestore.getInstance();
        commentsView=view.findViewById(R.id.comments_recycler_view);
        commentsView.setVisibility(View.VISIBLE);
        Bundle bundle=getArguments();
        id=bundle.getString("shareid");
        Query query=ff.collection("Shares").document(id).collection("Bloging").document(
                "oVG37qiPn6NlUPuFE746").collection("comments").orderBy("created_at");
        FirestoreRecyclerOptions<Comments>options=new FirestoreRecyclerOptions.Builder<Comments>().setQuery(query,Comments.class).build();
        adapter= new FirestoreRecyclerAdapter<Comments, PostView>(options) {
            @NonNull
            @Override
            public PostView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1=LayoutInflater.from(getContext()).inflate(R.layout.comments_item,parent,false);
                return new PostView(view1);
            }

            @Override
            protected void onBindViewHolder(@NonNull PostView postView, int i, @NonNull Comments comments) {
                postView.username.setText(comments.getUsername());
                postView.comment.setText(comments.getComment());
            }
        };
        post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String ,Object>map=new HashMap<>();
                Random r=new Random();
                map.put("comment",comment.getText().toString());
                map.put("created_at",new Date());
                map.put("username",firebaseUser.getEmail().substring(0,firebaseUser.getEmail().length()-10));
                ff.collection("Shares").document(id).collection("Bloging").document(
                        "oVG37qiPn6NlUPuFE746").collection("comments").document(r+firebaseUser.getUid()).set(map);
                ff.collection("Shares").document(id).collection("Bloging").document(
                        "oVG37qiPn6NlUPuFE746").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        int no_of_comments=Integer.parseInt(task.getResult().getString("no_of_comments"));
                        String s=String.valueOf(no_of_comments+1);
                        ff.collection("Shares").document(id).collection("Bloging").document(
                                "oVG37qiPn6NlUPuFE746").update("no_of_comments",s);
                    }
                });
            }
        });
        commentsView.setAdapter(adapter);
        commentsView.setLayoutManager(new LinearLayoutManager(getContext()));
        builder.setView(view);
        return builder.create();
    }
    public class PostView extends RecyclerView.ViewHolder {
        private TextView comment,username;
        public PostView(@NonNull View itemView) {
            super(itemView);
            comment=itemView.findViewById(R.id.actualcomment);
            username=itemView.findViewById(R.id.commentername);
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
