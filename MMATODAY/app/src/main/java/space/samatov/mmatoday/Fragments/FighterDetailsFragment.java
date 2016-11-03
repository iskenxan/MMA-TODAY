package space.samatov.mmatoday.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.model.Database;
import space.samatov.mmatoday.model.Fighter;
import space.samatov.mmatoday.model.FighterStats;

public class FighterDetailsFragment extends Fragment implements Database.OnCountryCodeFoundListener {

    public static final String FRAGMENT_KEY="fighters_details_fragment";
    private Fighter mFighter;
    private FighterStats mFighterStats;
    private View mView;
    private TextView mTextView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args=getArguments();
        mView = inflater.inflate(R.layout.fragment_fighter_detail, container, false);
        mTextView= (TextView) mView.findViewById(R.id.fDetailsFirstTextView);
        mFighterStats=new FighterStats();
        mFighter=args.getParcelable("fighter");
        if(mFighter.getCountryCode()==null){
            Database database=new Database();
            database.addCountryCodeListener(this);
            database.getSpeficicCountryCode(mFighter);
        }
        else {
            getStats();
            mTextView.setText(mFighter.getCountryCode());
        }

        return mView;
    }


    @Override
    public void OnCountryCodeFound(String code, boolean found) {
        if(found){
            mFighterStats.setmCountryBornKey(code);
            mFighterStats.setmFirst(mFighter.getFirstName());
            mFighterStats.setmLast(mFighter.getLastName());
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTextView.setText(mFighter.getCountryCode());
                }
            });
        }
        getStats();
    }


    public void getStats(){

    }

}
