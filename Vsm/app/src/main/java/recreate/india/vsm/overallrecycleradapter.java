package recreate.india.vsm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class overallrecycleradapter extends RecyclerView.Adapter<overallrecycleradapter.ViewHolder> {

    List<sharemodel> list;
    Context context;
    public overallrecycleradapter(List<sharemodel> list,Context context){
        this.list=list;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.oneforall,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if(list.get(position).getType()==1){
            holder.userposttext.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.GONE);
            holder.videoView.setVisibility(View.GONE);
        }
        else if(list.get(position).getType()==2){
            holder.userposttext.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.VISIBLE);
            holder.videoView.setVisibility(View.GONE);
        }
        else{
            holder.userposttext.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.GONE);
            holder.videoView.setVisibility(View.VISIBLE);
        }
        holder.companyname.setText(list.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView companyname,userposttext;
        private ImageView imageView;
        private VideoView videoView;
        Button gotoshare;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            companyname=itemView.findViewById(R.id.CompanyName);
            userposttext=itemView.findViewById(R.id.userPostText);
            imageView=itemView.findViewById(R.id.userPostImage);
            videoView=itemView.findViewById(R.id.userPostVideo);


        }
    }
}
