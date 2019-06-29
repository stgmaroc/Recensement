package com.example.recensement.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recensement.Model.Pointer.PointageListModel;
import com.example.recensement.R;

import java.util.ArrayList;

public class PointerRecycleViewAdapter extends RecyclerView.Adapter<PointerRecycleViewAdapter.DataObjectHolder> {
    //list des donnés à fetcher
    private ArrayList<PointageListModel> clients;
    private static MyClickListener myClickListener;

    Context context;

    //Class Holder qui implement les evenement de click sur les items
    public class DataObjectHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{
        //declaration of items vew composer
        TextView time;
        TextView status;


        //constructor for data items Holder
        public DataObjectHolder(@NonNull View itemView) {
            super(itemView);
            time= (TextView) itemView.findViewById(R.id.time);
            status = (TextView) itemView.findViewById(R.id.status);
            itemView.setOnClickListener(this);
        }

        // Listner sur les evenement du click pour les items
        @Override
        public void onClick(View v) {
            //myClickListener.onItemClick(getAdapterPosition(), v);
            //Log.d("test",""+getAdapterPosition());
            //finish();
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public PointerRecycleViewAdapter(ArrayList<PointageListModel> myDataset) {
        clients = myDataset;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pointer_item, viewGroup,false);
        DataObjectHolder dataObjectHolder=new DataObjectHolder(view);
        context=viewGroup.getContext();
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DataObjectHolder dataObjectHolder, int i) {
        dataObjectHolder.time.setText(clients.get(i).getEnter_exit_time());
        dataObjectHolder.status.setText(clients.get(i).getStatus());



    }

    public void deleteItem(int index) {
        clients.remove(index);
        notifyItemRemoved(index);
    }

    public void deleteAllItem() {
        int size = this.clients.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                clients.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public void addItem(PointageListModel dataObj, int index) {
        clients.add(dataObj);
        notifyItemInserted(index);
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);

    }



}
