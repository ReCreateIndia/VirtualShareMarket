package recreate.india.vsm.Dialog_Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import recreate.india.vsm.Constructor.Comments;
import recreate.india.vsm.Constructor.Likes;
import recreate.india.vsm.Main_Activities.R;
import recreate.india.vsm.Recycler_View_Adapters.Comments_Adapter;
import recreate.india.vsm.Recycler_View_Adapters.Likes_Adapter;

public class Like_Comment extends DialogFragment {
    private RecyclerView likes_recyclerview, comments_recyclerview;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.like_comment_dialog,null,false);
        likes_recyclerview = view.findViewById(R.id.likes_recycler_view);
        comments_recyclerview = view.findViewById(R.id.comments_recycler_view);
        int choice = getArguments().getInt("choice");
        HashMap<String,String> lcmap = (HashMap<String, String>) getArguments().getSerializable("map");
        if(choice==10)
        {
            //User has clicked on likes. Display likes.
            comments_recyclerview.setVisibility(View.GONE);
            ArrayList<Likes> likes_list = new ArrayList<>();
            //Retrieve data of likes from the backend into likes_list.
            // Refer to the class Likes (inside Constructor package) for details.
            Likes_Adapter adapter = new Likes_Adapter(likes_list);
            likes_recyclerview.setAdapter(adapter);
            likes_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        else if(choice==20)
        {
            //User has clicked on comments. Display data of comments
            likes_recyclerview.setVisibility(View.GONE);
            ArrayList<Comments> comments_list = new ArrayList<>();
            //Retrieve data of comments from the backend into comments_list.
            // Refer to the class Comments (inside Constructor package) for details.
            Comments_Adapter adapter = new Comments_Adapter(comments_list);
            comments_recyclerview.setAdapter(adapter);
            comments_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        builder.setView(view);
        return builder.create();
    }

}
