package space.samatov.mmatoday.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import samatov.space.mmatoday.R;

public abstract class ViewPagerFragment extends Fragment {
    protected ViewPager mViewPager;
    protected FragmentManager fragmentManager;
    protected ArrayList<Fragment> mChildFragments =new ArrayList<>();
    protected ArrayList<String> mFragmentTittles=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_viewpager,container,false);
        mViewPager= (ViewPager) view.findViewById(R.id.viewPager);
        fragmentManager=getChildFragmentManager();
        setupChildFragments();
        setupFragmentTittles();
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return position==0? mChildFragments.get(0): mChildFragments.get(1);
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return position==0?mFragmentTittles.get(0):mFragmentTittles.get(1);
            }
        });
        TabLayout tabLayout= (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setTabTextColors(getResources().getColorStateList(R.color.tab_color_selector));
        tabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    protected abstract void setupChildFragments();
    protected abstract void setupFragmentTittles();
}
