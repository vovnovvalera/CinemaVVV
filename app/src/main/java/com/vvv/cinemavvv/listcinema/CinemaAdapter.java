package com.vvv.cinemavvv.listcinema;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vvv.cinemavvv.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valera on 31.01.2017.
 */

public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.ItemViewHolder> {

    List<CinemaItem> cinemaItems;
    Context context;

    public interface CallBack {
        void onClickItem(int position, CinemaItem cinemaItem);
    }

    CallBack callBack;

    public CinemaItem getItem(int position) {

        return cinemaItems.get(position);
    }

    public CinemaAdapter(Context context, CallBack callBack) {
        cinemaItems = new ArrayList<>();
        this.context = context;
        this.callBack = callBack;
    }

    public void addItem(CinemaItem item) {
        cinemaItems.add(item);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cinema, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        Glide.with(context)
                .load(getItem(position).image)
                //.placeholder(R.drawable.no_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL) //кэш изображений , что бы грузил без интернета
                .into(holder.imageCinema);
        holder.name.setText(getItem(position).nameEng + "/" + getItem(position).name);
        holder.cw.setOnClickListener(view -> callBack.onClickItem(position, cinemaItems.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return cinemaItems.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageCinema;
        TextView name;
        CardView cw;

        ItemViewHolder(View v) {
            super(v);
            imageCinema = (ImageView) v.findViewById(R.id.image_cinema);
            name = (TextView) v.findViewById(R.id.name);
            cw = (CardView) v.findViewById(R.id.card_view);
        }
    }
}
