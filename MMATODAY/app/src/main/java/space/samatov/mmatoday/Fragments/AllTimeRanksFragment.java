package space.samatov.mmatoday.Fragments;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.Adapters.RecyclerViewAdapter;
import space.samatov.mmatoday.model.OnListItemClicked;
import space.samatov.mmatoday.model.Sorter;

public class AllTimeRanksFragment extends List_Fragment {


    public static final String FRAGMENT_KEY="all_time_ranks_fragment";

    @Override
    public void setupList() {

        ArrayAdapter<CharSequence> spinnerAdapter=ArrayAdapter.createFromResource(getContext(),
                R.array.all_time_rank_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinnerAdapter);
        mSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void spinnerCallback(int i) {
        mRecyclerView.getLayoutManager().scrollToPosition(0);
        mCurrentFighters= Sorter.getSpinnerSelectedDivisionAllTimeRanks(i,mFighters);
        notifyListeners(mCurrentFighters);
    }

    @Override
    public void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerViewAdapter adapter=new RecyclerViewAdapter(mCurrentFighters,(OnListItemClicked)getActivity(),false);
        mListeners.add(adapter);
        mRecyclerView.setAdapter(adapter);
    }
}
