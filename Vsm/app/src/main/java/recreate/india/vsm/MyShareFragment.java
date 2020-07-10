package recreate.india.vsm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MyShareFragment extends Fragment {

    private RecyclerView myshares_recyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_my_share, container, false);
        myshares_recyclerview = view.findViewById(R.id.myshares_recyclerview);
        List<sharemodel> mylist = new ArrayList<>();
        mylist.add(new sharemodel("My First Share"));

        myshares_recyclerview.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }
}