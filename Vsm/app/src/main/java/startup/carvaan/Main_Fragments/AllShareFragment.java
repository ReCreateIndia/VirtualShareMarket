package startup.carvaan.Main_Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.view.LineChartView;
import startup.carvaan.Constructor.sharemodel;
import startup.carvaan.Main_Activities.AboutShare;
import startup.carvaan.Main_Activities.R;



public class AllShareFragment extends Fragment implements GestureDetector.OnGestureListener{

    private RecyclerView share_recycler_view, funded_recycler_view, third_recycler_view;
    private List<sharemodel> tech_list, funded_list, third_list;
    private FirebaseFirestore ff;
    private LinearLayout techll, fundll, thirdll;
    private ImageView techl, techr, fundl, fundr, thirdl, thirdr;
    private Button gotoshare;
    private FirestoreRecyclerAdapter adapter,adapter2;

    public AllShareFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_share, container, false);
        ff = FirebaseFirestore.getInstance();
        share_recycler_view = view.findViewById(R.id.shareRecyclerView);
        Query query = ff.collection("Shares");
        FirestoreRecyclerOptions<sharemodel> options = new FirestoreRecyclerOptions.Builder<sharemodel>().setQuery(query, sharemodel.class).build();
        adapter = new FirestoreRecyclerAdapter<sharemodel, AllShareFragment.PostViewHolder>(options) {

            @NonNull
            @Override
            public AllShareFragment.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
                return new AllShareFragment.PostViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull AllShareFragment.PostViewHolder postViewHolder, int i, @NonNull final sharemodel sharemodel) {
                postViewHolder.name.setText(sharemodel.getName());
                postViewHolder.aboutshare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getContext().startActivity(new Intent(getContext(), AboutShare.class).putExtra("shareid",sharemodel.getId()));
                    }
                });
                ArrayList<Entry> yvalues = new ArrayList<>();
                yvalues.add(new Entry(1,25));
                yvalues.add(new Entry(2,50));
                yvalues.add(new Entry(3,75));
                yvalues.add(new Entry(4,60));
                yvalues.add(new Entry(5,35));
                yvalues.add(new Entry(6,80));
                yvalues.add(new Entry(7,40));
                LineDataSet set1 = new LineDataSet(yvalues,"Share Prices");
                set1.setColor(Color.GREEN);
                set1.setLineWidth(3f);
                set1.setFillAlpha(110);
                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);
                LineData data = new LineData(dataSets);
                postViewHolder.chart.setData(data);
                postViewHolder.chart.getAxisRight().setEnabled(false);
                String[] values = new String[] {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
                XAxis xAxis = postViewHolder.chart.getXAxis();
                xAxis.setValueFormatter(new myformatter(values));
                xAxis.setGranularity(1);

                //postViewHolder.chart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        };
        share_recycler_view.setAdapter(adapter);
        share_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }


    @Override
    public void onShowPress(MotionEvent e) {

    }


    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }


    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }


    @Override
    public void onLongPress(MotionEvent e) {

    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
    //ViewHolder


    public class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView name, desc;
        private ImageView postImage;
        private TextView date;
        private Button aboutshare;
        private LineChart chart;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.sharename);
            aboutshare=itemView.findViewById(R.id.gotoshare);
            chart = itemView.findViewById(R.id.chart);

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
    public class myformatter extends ValueFormatter implements IAxisValueFormatter {
        private String[] myvalues;
        public myformatter(String[] myvalues)
        {
            this.myvalues = myvalues;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return myvalues[(int)value];
        }
    }
}