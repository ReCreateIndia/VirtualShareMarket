package recreate.india.vsm.Recycler_View_Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import recreate.india.vsm.Constructor.Comments;
import recreate.india.vsm.Main_Activities.R;

public class Comments_Adapter extends RecyclerView.Adapter<Comments_Adapter.Comments_ViewHolder> {

    ArrayList<Comments> mylist;
    public Comments_Adapter(ArrayList<Comments> mylist)
    {
        this.mylist = mylist;
    }
    @NonNull
    @Override
    public Comments_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.comments_item,parent,false);
        return new Comments_ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Comments_ViewHolder holder, int position) {
        Picasso.get().load(mylist.get(position).getImageuri()).into(holder.commenterimage);
        holder.commentername.setText(mylist.get(position).getUsername()+"");
        holder.actualcomment.setText(mylist.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public class Comments_ViewHolder extends RecyclerView.ViewHolder{
        ImageView commenterimage;
        TextView commentername, actualcomment;
        public Comments_ViewHolder(@NonNull View itemView) {
            super(itemView);
            commenterimage = itemView.findViewById(R.id.commenterimage);
            commentername = itemView.findViewById(R.id.commentername);
            actualcomment = itemView.findViewById(R.id.actualcomment);
        }
    }
}
