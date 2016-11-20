package space.samatov.mmatoday.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.florent37.materialleanback.MaterialLeanBack;

import java.util.ArrayList;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.model.OnLeanBackClicked;

public class AdapterLeanBack extends MaterialLeanBack.Adapter{

    private ArrayList<String> mGallery;
    private OnLeanBackClicked mListener;

    private AdapterLeanBack(ArrayList<String> gallery,OnLeanBackClicked listener){
        mGallery=gallery;
        mListener=listener;
    }

    public static AdapterLeanBack getGalleryAndListener(ArrayList<String> gallery,OnLeanBackClicked listener){

        return new AdapterLeanBack(gallery,listener);
    }


    @Override
    public int getLineCount() {
        return 3;
    }

    @Override
    public int getCellsCount(int line) {
        return mGallery.size();
}

    @Override
    public MaterialLeanBack.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int row) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lean_back_item,viewGroup,false);

        return new ViewHolderLeanBack(view);
    }

    @Override
    public void onBindViewHolder(MaterialLeanBack.ViewHolder viewHolder, int position) {
        ((ViewHolderLeanBack)viewHolder).bind(position);
    }

    @Override
    public String getTitleForRow(int row) {
        return "Line "+row;
    }



    private class ViewHolderLeanBack extends MaterialLeanBack.ViewHolder{

        private ImageView mImageView;
        public ViewHolderLeanBack(View itemView) {
            super(itemView);
            mImageView= (ImageView) itemView.findViewById(R.id.leanBackImageView);
        }


        public void bind(int position){
            Glide.with(mImageView.getContext()).load(mGallery.get(position)).into(mImageView);
        }
    }
}
