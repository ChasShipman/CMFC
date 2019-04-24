package com.warbs.cmfclogin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CompositionAdapter extends
        RecyclerView.Adapter<CompositionAdapter.MyViewHolder> implements Filterable {

    private List<Composition> compList;
    private List<Composition> compListFull;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, edit; //create;

        public MyViewHolder(View view) {
            super(view);
            title =  view.findViewById(R.id.title);
            edit =  view.findViewById(R.id.edit_date);
            //create =   view.findViewById(R.id.create_date);
        }
    }


    public CompositionAdapter(List<Composition> compList) {

        this.compList = compList;
        compListFull = new ArrayList<>(compList);
    }

    @Override
    public CompositionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View compView = inflater.inflate(R.layout.composition_list_row, parent, false);

        return new MyViewHolder(compView);
    }

    @Override
    public void onBindViewHolder(CompositionAdapter.MyViewHolder holder, int position) {
        Composition comp = compList.get(position);
        holder.title.setText(comp.getTitle());
        holder.edit.setText(comp.getEdit());
        // holder.create.setText(comp.getCreate());
    }

    @Override
    public int getItemCount() {
        return compList.size();
    }

    public List<Composition> getData() {
        return compList;
    }


    public void removeItem(int position) {
        compList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Composition item, int position) {
        compList.add(position, item);
        notifyItemInserted(position);
    }

    @Override
    public Filter getFilter(){
        return compFilter;
    }

    private Filter compFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Composition> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(compListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Composition composition : compListFull) {
                    if (composition.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(composition);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            compList.clear();
            compList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}

