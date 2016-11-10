package space.samatov.mmatoday.Fragments;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.Adapters.RecyclerViewAdapter;
import space.samatov.mmatoday.model.Fighter;
import space.samatov.mmatoday.model.OnListItemClicked;
import space.samatov.mmatoday.model.Sorter;

public class MenListFragment extends List_Fragment {

    public static final String FRAGMENT_KEY="men_list_fragment";
    @Override
    public void setupList() {
        mFighters= Sorter.getGender(Sorter.GENDER_MEN,mFighters);
        ArrayAdapter<CharSequence> spinnerAdapter=ArrayAdapter.createFromResource(getContext(),
                R.array.men_weightclass_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinnerAdapter);
        mSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void spinnerCallback(int i) {
        mRecyclerView.getLayoutManager().scrollToPosition(0);
        mCurrentFighters=Sorter.getSpinnerSelectedDivisionMen(i,mFighters);
        mCurrentFighters=Sorter.sortAlphabetically(mCurrentFighters);
        notifyListeners(mCurrentFighters);
    }

    @Override
    public void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerViewAdapter adapter=new RecyclerViewAdapter(mCurrentFighters,(OnListItemClicked)getActivity(),true);
        mListeners.add(adapter);
        mRecyclerView.setAdapter(adapter);
    }
}
