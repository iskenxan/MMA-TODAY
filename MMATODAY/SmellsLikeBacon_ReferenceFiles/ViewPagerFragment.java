package space.samatov.smellslikebacon;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerFragment extends Fragment {
    public static final String KEY_RECIPE_INDEX="recipe_index";
    public static final String IS_INGREDIENTS="is_ingredients";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_view_pager, container, false);
        int index=getArguments().getInt(KEY_RECIPE_INDEX);
        getActivity().setTitle(Recipies.names[index]);
        final CheckboxesFragment ingredientsFragment=new CheckboxesFragment();
        final CheckboxesFragment directionsFragment=new CheckboxesFragment();
        Bundle ingredientsBundle=new Bundle();
        Bundle directionsBundle=new Bundle();
        ingredientsBundle.putInt(KEY_RECIPE_INDEX, index);
        directionsBundle.putInt(KEY_RECIPE_INDEX,index);
        directionsBundle.putBoolean(IS_INGREDIENTS,false);
        ingredientsBundle.putBoolean(IS_INGREDIENTS,true);
        ingredientsFragment.setArguments(ingredientsBundle);
        directionsFragment.setArguments(directionsBundle);
        ViewPager viewPager= (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return position==0? ingredientsFragment:directionsFragment;
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return position==0?"Ingredients":"Directions";
            }
        });

        TabLayout tabLayout= (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onDestroy() {
        getActivity().setTitle(getResources().getString(R.string.app_name));
        super.onDestroy();
    }
}
