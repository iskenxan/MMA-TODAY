package space.samatov.smellslikebacon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class CheckboxesFragment extends Fragment {
    private CheckBox[] mCheckBoxes;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int index=getArguments().getInt(ViewPagerFragment.KEY_RECIPE_INDEX);
        boolean isIngredients=getArguments().getBoolean(ViewPagerFragment.IS_INGREDIENTS);

        View view=inflater.inflate(R.layout.checkboxes_fragment, container, false);
        LinearLayout linearLayout=linearLayout= (LinearLayout) view.findViewById(R.id.checkboxesLayout);
        String[] strings;

        if(isIngredients)
            strings=Recipies.ingredients[index].split("`");
        else
            strings=Recipies.directions[index].split("`");

        mCheckBoxes=new CheckBox[strings.length];
        boolean[] checkedBoxes=new boolean[strings.length];
        if(savedInstanceState!=null&&savedInstanceState.getBooleanArray("checkboxes_state")!=null)
            checkedBoxes=savedInstanceState.getBooleanArray("checkboxes_state");

        setupCheckboxes(strings,linearLayout,checkedBoxes);
        return view;
    }

    private void setupCheckboxes(String[] strings,ViewGroup viewGroup,boolean[] checkedBoxes) {
        int i=0;
        for (String string:strings){
            mCheckBoxes[i]=new CheckBox(getActivity());
            mCheckBoxes[i].setPadding(mCheckBoxes[i].getPaddingLeft() + 10, 16, 8, 16);
            mCheckBoxes[i].setTextSize(20f);
            mCheckBoxes[i].setText(string);
            viewGroup.addView(mCheckBoxes[i]);
            if(checkedBoxes[i]){
                mCheckBoxes[i].toggle();
            }
            i++;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        boolean[] stateOfCheckBoxes=new boolean[mCheckBoxes.length];
        for (int i=0;i<mCheckBoxes.length;i++){
            stateOfCheckBoxes[i]=mCheckBoxes[i].isChecked();
        }
        outState.putBooleanArray("checkboxes_state",stateOfCheckBoxes);
        super.onSaveInstanceState(outState);
    }
}
