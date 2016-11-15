package space.samatov.mmatoday.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import samatov.space.mmatoday.R;

public class NewViewPagerFragment  extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_news_view_pager,container,false);
        ViewPager viewPager= (ViewPager) view.findViewById(R.id.news_view_pager);





        return view;
    }
}
