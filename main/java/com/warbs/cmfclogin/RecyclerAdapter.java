package com.warbs.cmfclogin;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageViewHolder> {

    private int[] images;

    public RecyclerAdapter(int[] images) {
        this.images = images;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album, parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);


        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        int image_id = images[position];
        holder.Lennon.setImageResource(image_id);
        holder.Lennon2.setTag(image_id);

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        ImageView Lennon;
        TextView Lennon2;


        public ImageViewHolder(View itemView) {
            super(itemView);
            Lennon = itemView.findViewById(R.id.lennon);
            Lennon2 = itemView.findViewById(R.id.lennon2);
            Lennon.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            final MediaPlayer play = MediaPlayer.create(v.getContext(), R.raw.magic);
           play.start();
        }

    }
}
