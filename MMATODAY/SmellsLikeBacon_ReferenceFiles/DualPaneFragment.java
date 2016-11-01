package space.samatov.smellslikebacon;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DualPaneFragment extends Fragment {
    private static final String INGREDIENTS_FRAGMENT = "ingredients_fragment";
    private static final String DIRECTIONS_FRAGMENT = "directions_fragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_view_pager, container, false);
        int index=getArguments().getInt(ViewPagerFragment.KEY_RECIPE_INDEX);
        getActivity().setTitle(Recipies.names[index]);
        android.support.v4.app.FragmentManager fragmentManager=getChildFragmentManager();
        CheckboxesFragment ingredientsFragmentSaved= (CheckboxesFragment) fragmentManager.findFragmentByTag(INGREDIENTS_FRAGMENT);
        CheckboxesFragment directionsFragmentSaved= (CheckboxesFragment) fragmentManager.findFragmentByTag(DIRECTIONS_FRAGMENT);
        if(ingredientsFragmentSaved==null){
            CheckboxesFragment ingredientsFragment=new CheckboxesFragment();
            Bundle ingredientsBundle=new Bundle();
            ingredientsBundle.putInt(ViewPagerFragment.KEY_RECIPE_INDEX, index);
            ingredientsBundle.putBoolean(ViewPagerFragment.IS_INGREDIENTS, true);
            ingredientsFragment.setArguments(ingredientsBundle);
            fragmentManager.beginTransaction().add(R.id.leftPlaceholder,ingredientsFragment,INGREDIENTS_FRAGMENT).commit();
        }
        if(directionsFragmentSaved==null){
            CheckboxesFragment directionsFragment=new CheckboxesFragment();
            Bundle directionsBundle = new Bundle();
            directionsBundle.putInt(ViewPagerFragment.KEY_RECIPE_INDEX, index);
            directionsBundle.putBoolean(ViewPagerFragment.IS_INGREDIENTS, false);
            directionsFragment.setArguments(directionsBundle);
            fragmentManager.beginTransaction().add(R.id.rightPlaceholder,directionsFragment,DIRECTIONS_FRAGMENT).commit();
        }
        return view;
    }

    @Override
    public void onDestroy() {
        getActivity().setTitle(getResources().getString(R.string.app_name));
        super.onDestroy();
    }
}
