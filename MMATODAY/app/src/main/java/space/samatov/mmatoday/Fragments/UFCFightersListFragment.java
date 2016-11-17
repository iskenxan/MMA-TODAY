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

public class UFCFightersListFragment extends Fragment{
    public ArrayList<Fighter> mFighters;
    public static final String FRAGMENT_KEY="viewpager_fragment";
    public static final String ARGS_KEY="fighters";
    public MenListFragment menListFragment;
    public WomenListFragment womenListFragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_viewpager,container,false);
        mFighters=getArguments().getParcelableArrayList("fighters");
        FragmentManager fragmentManager=getChildFragmentManager();
        menListFragment = (MenListFragment) fragmentManager.findFragmentByTag(MenListFragment.FRAGMENT_KEY);
        womenListFragment= (WomenListFragment) fragmentManager.findFragmentByTag(WomenListFragment.FRAGMENT_KEY);
             if(menListFragment==null&&womenListFragment==null) {
             menListFragment = new MenListFragment();
            womenListFragment = new WomenListFragment();
            Bundle menListBundle = new Bundle();
            Bundle womenListBundle = new Bundle();
            menListBundle.putParcelableArrayList("fighters", mFighters);
            womenListBundle.putParcelableArrayList("fighters", mFighters);
            menListFragment.setArguments(menListBundle);
            womenListFragment.setArguments(womenListBundle);
        }
        ViewPager viewPager= (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return position==0?menListFragment:womenListFragment;
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return position==0?"Men Division":"Women Division";
            }
        });
        TabLayout tabLayout= (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}
