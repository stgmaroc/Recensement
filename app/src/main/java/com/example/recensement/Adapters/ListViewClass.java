package com.example.recensement.Adapters;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ListViewClass {
    //private RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;
    private DrawerLayout drawer;

    public ListViewClass(RecyclerView.Adapter mAdapter, RecyclerView recyclerView,Context context) {
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(mAdapter);
        //RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, LinearLayoutManager.VERTICAL);
        //recyclerView.addItemDecoration(itemDecoration);

    }


}
