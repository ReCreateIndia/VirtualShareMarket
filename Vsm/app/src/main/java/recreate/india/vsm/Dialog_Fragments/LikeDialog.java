package recreate.india.vsm.Dialog_Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import recreate.india.vsm.Constructor.Comments;
import recreate.india.vsm.Main_Activities.AboutShare;
import recreate.india.vsm.Main_Activities.R;

public class LikeDialog extends DialogFragment {
    private FirebaseFirestore ff;
    private FirestoreRecyclerAdapter adapter;
    private RecyclerView commentsView;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.like_comment_dialog,null,false);
        ff=FirebaseFirestore.getInstance();
        commentsView=view.findViewById(R.id.comments_recycler_view);
        Query query=ff.collection("Shares").document("1509").collection("Bloging").document(
                "oVG37qiPn6NlUPuFE746").collection("likes");
        FirestoreRecyclerOptions<Comments>options=new FirestoreRecyclerOptions.Builder<Comments>().setQuery(query,Comments.class).build();
        adapter= new FirestoreRecyclerAdapter<Comments, PostView>(options) {
            @NonNull
            @Override
            public PostView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1=LayoutInflater.from(getContext()).inflate(R.layout.likes_item,parent,false);
                return new PostView(view1);
            }

            @Override
            protected void onBindViewHolder(@NonNull PostView postView, int i, @NonNull Comments comments) {
                postView.username.setText(comments.getUsername());
            }
        };
        commentsView.setAdapter(adapter);
        commentsView.setLayoutManager(new LinearLayoutManager(getContext()));
        builder.setView(view);
        return builder.create();
    }
    public class PostView extends RecyclerView.ViewHolder {
        private TextView comment,username;
        public PostView(@NonNull View itemView) {
            super(itemView);

            username=itemView.findViewById(R.id.likername);
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
