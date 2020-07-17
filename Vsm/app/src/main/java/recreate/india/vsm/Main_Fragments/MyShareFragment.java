package recreate.india.vsm.Main_Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import recreate.india.vsm.Constructor.sharemodel;
import recreate.india.vsm.Main_Activities.R;


public class MyShareFragment extends Fragment {

    private RecyclerView myshares_recyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_my_share, container, false);
        myshares_recyclerview = view.findViewById(R.id.myshares_recyclerview);
        List<sharemodel> mylist = new ArrayList<>();
        mylist.add(new sharemodel("My First Share","12"));

        myshares_recyclerview.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }
}