package space.samatov.smellslikebacon;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class RecyclerAdapter extends RecyclerView.Adapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(getLayoutId(),parent,false);
        return new ViewHolder(view);
    }

    protected abstract int getLayoutId();

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return Recipies.names.length;
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTextView;
        private ImageView mImageView;
        private int mIndex;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView=(TextView)itemView.findViewById(R.id.itemTextView);
            mImageView=(ImageView)itemView.findViewById(R.id.itemImageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRecipeSelected(mIndex);
        }

        public void bindView(int position){
            mIndex=position;
            mTextView.setText(Recipies.names[position]);
            mImageView.setImageResource(Recipies.resourceIds[position]);

        }
    }

    protected abstract void onRecipeSelected(int index);
}
