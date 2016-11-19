package space.samatov.mmatoday.Adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.model.OctagonGirl;
import space.samatov.mmatoday.model.OnOctagonGirlItemClicked;

public class AdapterOctagonGirlsRecycler extends RecyclerView.Adapter {

    private ArrayList<OctagonGirl> mOctagonGirls;
    private OnOctagonGirlItemClicked mListener;

    public AdapterOctagonGirlsRecycler(ArrayList<OctagonGirl> octagonGirls,OnOctagonGirlItemClicked listener){
        mOctagonGirls=octagonGirls;
        mListener=listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.octagon_girls_recycler_item,parent,false);
        return new ViewHolderOctagonGirls(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderOctagonGirls)holder).bind(position);
    }

    @Override
    public int getItemCount() {
        return mOctagonGirls.size();
    }


    public class ViewHolderOctagonGirls extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTextView;
        ImageView mImageView;
        private int mPosition;
        public ViewHolderOctagonGirls(View itemView) {
            super(itemView);
            mTextView= (TextView) itemView.findViewById(R.id.octagonGirlRecyclerTextView);
            mImageView= (ImageView) itemView.findViewById(R.id.octagonGirlRecyclerImageView);
            itemView.setOnClickListener(this);
        }

        public void bind(int position){
            OctagonGirl girl=mOctagonGirls.get(position);
            Glide.with(itemView.getContext()).load(girl.getmBanner()).into(mImageView);
            mTextView.setText(girl.getmFirst()+" "+girl.getmLast());
            mPosition=position;
        }

        @Override
        public void onClick(View view) {
            notifyListener(mPosition);
        }

        public void notifyListener(int position){
            mListener.OnItemClickedOctagonGirl(position);
        }
    }
}
