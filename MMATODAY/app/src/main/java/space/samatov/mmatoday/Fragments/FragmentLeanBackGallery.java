package space.samatov.mmatoday.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialleanback.MaterialLeanBack;

import java.util.ArrayList;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.Adapters.AdapterLeanBack;
import space.samatov.mmatoday.model.OnLeanBackClicked;

public class FragmentLeanBackGallery extends Fragment {

    public static final String FRAGMENT_KEY="lean_back_fragment";
    public static final String ARGS_KEY="lean_back_args";


    private MaterialLeanBack mLeanBack;
    private ArrayList<String> mGallery;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_lean_back,container,false);
        mLeanBack= (MaterialLeanBack) view.findViewById(R.id.leanBack);
        mGallery=getArguments().getStringArrayList(ARGS_KEY);


        AdapterLeanBack adapterLeanBack=AdapterLeanBack.getGalleryAndListener(mGallery,(OnLeanBackClicked)getActivity());
        mLeanBack.setAdapter(adapterLeanBack);

        return view;
    }
}
