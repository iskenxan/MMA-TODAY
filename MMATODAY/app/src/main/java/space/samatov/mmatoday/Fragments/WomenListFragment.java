package space.samatov.mmatoday.Fragments;


import android.view.View;
import android.widget.ArrayAdapter;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.model.Sorter;

public class WomenListFragment extends List_Fragment {

    public static final String FRAGMENT_KEY="women_list_fragment";
    @Override
    public void setupList() {
        mFighters= Sorter.getGender(Sorter.GENDER_WOMEN,mFighters);
        ArrayAdapter<CharSequence> spinnerAdapter=ArrayAdapter.createFromResource(getContext(),
                R.array.women_weightclass_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinnerAdapter);
        mSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void spinnerCallback(int i) {
        try {
            mRecyclerView.getLayoutManager().scrollToPosition(0);
            mCurrentFighters = Sorter.getSpinnerSelectedDivionWomen(i, mFighters);
            mCurrentFighters = Sorter.sortAlphabetically(mCurrentFighters);
            notifyListeners(mCurrentFighters);
        }
        catch (Exception e){
            e.getMessage();
        }
    }
}
