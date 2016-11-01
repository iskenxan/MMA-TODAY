package space.samatov.mmatoday.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.Spinner;

import java.util.ArrayList;
import samatov.space.mmatoday.R;
import space.samatov.mmatoday.Adapters.RecyclerViewAdapter;
import space.samatov.mmatoday.model.Fighter;
import space.samatov.mmatoday.model.ListItem;
import space.samatov.mmatoday.model.OnListItemClicked;


public abstract class List_Fragment extends android.support.v4.app.Fragment implements AdapterView.OnItemSelectedListener {
    protected ArrayList<Fighter> mFighters;
    protected ArrayList<ListItem> mCurrentFighters;
    private ArrayList<RecyclerViewDataChangedListener> mListeners;
    protected RecyclerView mRecyclerView;
    protected Spinner mSpinner;

    public interface RecyclerViewDataChangedListener{
       void onDataChanged(ArrayList<ListItem> data);
    }

    public static final String FRAGMENT_KEY="list_fragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_list,container,false);

        mFighters=getArguments().getParcelableArrayList("fighters");
        mCurrentFighters=new ArrayList<ListItem>();
        mListeners= new ArrayList<>();
         mRecyclerView = (RecyclerView) view.findViewById(R.id.listRecyclerView);
        mSpinner= (Spinner) view.findViewById(R.id.weightClassSpinner);

        setupList();
       setupRecyclerView();
        return view;
    }

    public abstract void setupList();


    //Spinner callback methods
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       spinnerCallback(i);
    }

    public abstract void spinnerCallback(int i);

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void notifyListeners(ArrayList<ListItem> data){
        for (RecyclerViewDataChangedListener listener:mListeners)
            listener.onDataChanged(data);
    }

    public void setupRecyclerView(){
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerViewAdapter adapter=new RecyclerViewAdapter(mCurrentFighters,(OnListItemClicked)getActivity());
        mListeners.add(adapter);
        mRecyclerView.setAdapter(adapter);
    }

}
