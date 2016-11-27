package space.samatov.mmatoday.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.Adapters.AdapterOctagonGirlsRecycler;
import space.samatov.mmatoday.model.OctagonGirl;
import space.samatov.mmatoday.model.interfaces.OctagonGirlItemClicked;

public class OctagonGirlsRecyclerViewFragment extends Fragment {

    public static final String FRAGMENT_KEY="octagon_girls_fragment";
    public static final String ARGS_KEY ="octagon_girls_fragment";

    private RecyclerView mRecyclerView;
    private ArrayList<OctagonGirl> mOctagonGirls;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_recycler,container,false);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.recyclerViewOnly);
        mOctagonGirls=getArguments().getParcelableArrayList(ARGS_KEY);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AdapterOctagonGirlsRecycler adapter=new AdapterOctagonGirlsRecycler(mOctagonGirls,((OctagonGirlItemClicked) getActivity()));

        mRecyclerView.setAdapter(adapter);

        return view;
    }
}
