package startup.carvaan.Recycler_View_Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import startup.carvaan.Constructor.Likes;
import startup.carvaan.Main_Activities.R;

public class Likes_Adapter extends RecyclerView.Adapter<Likes_Adapter.Likes_View_Holder> {

    ArrayList<Likes> mylist;

    public Likes_Adapter(ArrayList<Likes> mylist)
    {
        this.mylist = mylist;
    }
    @NonNull
    @Override
    public Likes_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.likes_item,parent,false);
        return new Likes_View_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Likes_View_Holder holder, int position) {
        Picasso.get().load(mylist.get(position).getImageuri()).into(holder.likerimage);
        holder.likername.setText(mylist.get(position).getUsername()+"");
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public class Likes_View_Holder extends RecyclerView.ViewHolder{
        ImageView likerimage;
        TextView likername;
        public Likes_View_Holder(@NonNull View itemView) {
            super(itemView);
            likerimage = itemView.findViewById(R.id.likerimage);
            likername = itemView.findViewById(R.id.likername);
        }
    }
}
