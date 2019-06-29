package com.example.recensement.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recensement.Model.Client.Respond;
import com.example.recensement.R;
import com.example.recensement.ui.DetailsClient;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.DataObjectHolder> {
    //list des donnés à fetcher
    private ArrayList<Respond> clients;
    private static MyClickListener myClickListener;

    Context context;



    //Class Holder qui implement les evenement de click sur les items
    public class DataObjectHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{
        //declaration of items vew composer
        TextView label;
        TextView datetime;
        TextView magasin;
        TextView save_scale_type;
        TextView city;
        TextView adress;
        ImageView imageView;



        //constructor for data items Holder
        public DataObjectHolder(@NonNull View itemView) {
            super(itemView);
            label= (TextView) itemView.findViewById(R.id.label);
            datetime = (TextView) itemView.findViewById(R.id.magasin);
            magasin = (TextView) itemView.findViewById(R.id.magasin);
            save_scale_type = (TextView) itemView.findViewById(R.id.save_scale_type);
            city = (TextView) itemView.findViewById(R.id.city);
            imageView = itemView.findViewById(R.id.magasin_image);
            // Listner sur les evenement du click pour les items
            itemView.setOnClickListener(this);
        }

        // Listner sur les evenement du click pour les items
        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
            //Log.d("test",""+getAdapterPosition());
            //dataObjectHolder.label.setText(clients.get(i).getFull_name());
            //getAdapterPosition()
            SharedPreferences sharedPref= context.getSharedPreferences("Mypref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("first_name",clients.get(getAdapterPosition()).getFirst_name());
            editor.putString("last_name",clients.get(getAdapterPosition()).getLast_name());
            editor.putString("city",clients.get(getAdapterPosition()).getCity());
            editor.putString("type",clients.get(getAdapterPosition()).getType());
            editor.putString("magasin",clients.get(getAdapterPosition()).getMagazin_name());
            editor.putString("phone",clients.get(getAdapterPosition()).getPhone());
            editor.putString("localisation",clients.get(getAdapterPosition()).getLocalisation());
            editor.putString("adress",clients.get(getAdapterPosition()).getAddress());
            //editor.putString("name",name);
            editor.apply();
            clients.get(getAdapterPosition()).getFull_name();
            Intent intent = new Intent(context, DetailsClient.class);
            Log.d("test",""+clients.get(getAdapterPosition()).getFull_name());
            /*intent.putExtra("first_name",clients.get(getAdapterPosition()).getFirst_name());
            intent.putExtra("last_name",clients.get(getAdapterPosition()).getLast_name());
            intent.putExtra("city",clients.get(getAdapterPosition()).getCity());
            intent.putExtra("type",clients.get(getAdapterPosition()).getType());
            intent.putExtra("magasin",clients.get(getAdapterPosition()).getMagazin_name());
            intent.putExtra("phone",clients.get(getAdapterPosition()).getPhone());
            intent.putExtra("localisation",clients.get(getAdapterPosition()).getLocalisation());*/
            //intent.putExtra("location",clients.get(getAdapterPosition()).getPhone());
            context.startActivity(intent);




            //finish();
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecycleViewAdapter(ArrayList<Respond> myDataset) {
        clients = myDataset;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.client_item, viewGroup,false);
        DataObjectHolder dataObjectHolder=new DataObjectHolder(view);
        context=viewGroup.getContext();
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DataObjectHolder dataObjectHolder, int i) {
        dataObjectHolder.label.setText(clients.get(i).getFull_name());
        dataObjectHolder.datetime.setText(clients.get(i).getLast_name());
        if (clients.get(i).getMagazin_name()!=null) {
            dataObjectHolder.magasin.setText(clients.get(i).getMagazin_name());
        }else {
            dataObjectHolder.imageView.setVisibility(View.GONE);
            dataObjectHolder.magasin.setVisibility(View.GONE);
        }
        dataObjectHolder.city.setText(clients.get(i).getCity());
        dataObjectHolder.save_scale_type.setText(clients.get(i).getType());


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

    public void addItem(Respond dataObj, int index) {
        clients.add(dataObj);
        notifyItemInserted(index);
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);

    }



}
