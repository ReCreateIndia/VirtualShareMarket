package startup.carvaan.Recycler_View_Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import startup.carvaan.Main_Activities.R;

public class Ads_Adapter extends RecyclerView.Adapter<Ads_Adapter.Ads_ViewHolder> {


    @NonNull
    @Override
    public Ads_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.credits_by_ads_item,parent,false);
        return new Ads_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Ads_ViewHolder holder, int position) {

        switch (position)
        {
            case 1: holder.ads_text.setText("150 Credits - 45 second ad"); break;
            case 2: holder.ads_text.setText("200 Credits - 60 second ad"); break;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class Ads_ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout ad_layout;
        TextView ads_text;
        public Ads_ViewHolder(@NonNull View itemView) {
            super(itemView);
            ad_layout = itemView.findViewById(R.id.ad_layout);
            ads_text = itemView.findViewById(R.id.ads_text);
        }
    }
}
