package space.samatov.mmatoday.Fragments;

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
import space.samatov.mmatoday.model.Fighter;

public class UFCFightersFragment extends ViewPagerFragment{
    public ArrayList<Fighter> mFighters;
    public static final String FRAGMENT_KEY="viewpager_fragment";
    public static final String ARGS_KEY="fighters";


    @Override
    protected void setupChildFragments() {
        mFighters=getArguments().getParcelableArrayList(ARGS_KEY);
        MenListFragment menListFragment = new MenListFragment();
        WomenListFragment womenListFragment = new WomenListFragment();
        Bundle menListBundle = new Bundle();
        Bundle womenListBundle = new Bundle();
        menListBundle.putParcelableArrayList("fighters", mFighters);
        womenListBundle.putParcelableArrayList("fighters", mFighters);
        menListFragment.setArguments(menListBundle);
        womenListFragment.setArguments(womenListBundle);

        mChildFragments.add(menListFragment);
        mChildFragments.add(womenListFragment);
    }

    @Override
    protected void setupFragmentTittles() {
        mFragmentTittles.add("Male Fighters");
        mFragmentTittles.add("Female Fighters");
    }
}
