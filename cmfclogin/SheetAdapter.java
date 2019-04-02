package com.warbs.cmfclogin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SheetAdapter extends RecyclerView.Adapter<
        SheetAdapter.MyViewHolder>{

    private List<SheetBuilder> SheetList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, content;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            content = (TextView) view.findViewById(R.id.content);

        }
    }

    public SheetAdapter(List<SheetBuilder> SheetList) {
        this.SheetList = SheetList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SheetBuilder note = SheetList.get(position);
        holder.title.setText(note.getID());
        holder.content.setText(note.getContent());
    }

    @Override
    public int getItemCount() {
        return SheetList.size();
    }
}
