package space.samatov.mmatoday.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


//import com.bumptech.glide.Glide;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.Fragments.List_Fragment;
import space.samatov.mmatoday.model.Fighter;
import space.samatov.mmatoday.model.ListGroupHeaders;
import space.samatov.mmatoday.model.ListItem;
import space.samatov.mmatoday.model.OnListItemClicked;

public class FightersRecyclerViewAdapter extends RecyclerView.Adapter implements List_Fragment.RecyclerViewDataChangedListener {

    private ArrayList<ListItem> mFighters;
    private boolean mIsUFCList=false;
    private ArrayList<OnListItemClicked> mListeners =new ArrayList<>();
    public FightersRecyclerViewAdapter(ArrayList<ListItem> fighters, OnListItemClicked listener, boolean isUFCList){
        mFighters=fighters;
        mListeners.add(listener);
        mIsUFCList=isUFCList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=new View(parent.getContext());
        ListViewHolder viewHolder=new HeaderViewHolder(view);
        switch (viewType){
            case 1: {
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_header,parent,false);
                viewHolder= new HeaderViewHolder(view);
                break;
            }
            case 0:{
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
                viewHolder=new FighterViewHolder(view);
                break;
            }
            case 2:
            {
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_champ,parent,false);
                viewHolder=new ChampViewHolder(view);
                break;
            }
        }

        return viewHolder;
    }

    public void notifyListeners(Fighter fighter){
        for (OnListItemClicked listener: mListeners){
            listener.OnListItemSelected(fighter);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListViewHolder currentHolder= (ListViewHolder) holder;

        if(position==0&&mIsUFCList)
            ((ChampViewHolder)holder).bindView( position);
        else if(currentHolder.isHeader())
            ((HeaderViewHolder)holder).bindView( position);
        else
        ((FighterViewHolder)holder).bindView( position);
    }

    @Override
    public int getItemCount() {
        return mFighters.size();
    }

    @Override
    public void onDataChanged(ArrayList<ListItem> data) {
        mFighters=data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0&&mIsUFCList)//champions are always first in the list
            return 2;
        ListItem item=mFighters.get(position);
        if(item.isGroupHeader())
            return 1;
        else
            return 0;
    }



    public abstract class   ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public int mIndex;
        public ListViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bindView(int position);
        public abstract boolean isHeader();
    }





    public class HeaderViewHolder extends ListViewHolder{
        private TextView mTextView;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            mTextView= (TextView) itemView.findViewById(R.id.headerTextView);
        }

        public void bindView(int position){
            final ListItem item=mFighters.get(position);
            ListGroupHeaders header= (ListGroupHeaders) item;
            mTextView.setText(header.getmGroupName());
        }

        @Override
        public boolean isHeader() {
            return true;
        }

        @Override
        public void onClick(View view) {

        }
    }







    public  class FighterViewHolder extends ListViewHolder{
        private ImageView mImageView;
        private TextView mTextView;
        public FighterViewHolder(View itemView) {
            super(itemView);
             mImageView= (ImageView) itemView.findViewById(R.id.fighterListImageView);
             mTextView= (TextView) itemView.findViewById(R.id.fighterListTextView);
            itemView.setOnClickListener(this);
        }

        public void bindView(int position){
            final ListItem item=mFighters.get(position);
            Fighter fighter=(Fighter)item;
            if(fighter.getProfileUrl().equals("default")) {
                if(fighter.getmWeightClass().equals("Women"))
                    mImageView.setImageResource(R.drawable.fighter_profile_women_default);
                else
                mImageView.setImageResource(R.drawable.fighter_profile_default);
            }
            else
            Glide.with(itemView.getContext()).load(fighter.getProfileUrl()).override(150,150).diskCacheStrategy(DiskCacheStrategy.RESULT).into(mImageView);
            mTextView.setText(fighter.getFirstName()+" "+fighter.getLastName());
            mIndex=position;
        }

        @Override
        public boolean isHeader() {
            return false;
        }

        @Override
        public void onClick(View view) {
            Fighter fighter= (Fighter) mFighters.get(mIndex);
            notifyListeners(fighter);
        }
    }

    public class ChampViewHolder extends ListViewHolder {
        TextView mTextView;
        ImageView mImageView;
        public ChampViewHolder(View itemView) {
            super(itemView);
            mTextView= (TextView) itemView.findViewById(R.id.champTextView);
            mImageView= (ImageView) itemView.findViewById(R.id.champImageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void bindView(int position) {
            final ListItem item=mFighters.get(position);
            Fighter fighter=(Fighter)item;
            mTextView.setText(fighter.getFirstName()+" "+fighter.getLastName());

            Glide.with(itemView.getContext()).load(fighter.getmBeltProfileUrl()).override(500,500).diskCacheStrategy(DiskCacheStrategy.RESULT).into(mImageView);
            mIndex=position;
        }

        @Override
        public boolean isHeader() {
            return false;
        }

        @Override
        public void onClick(View view) {
            Fighter fighter= (Fighter) mFighters.get(mIndex);
            notifyListeners(fighter);
        }
    }


}
