package com.example.recensement;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.recensement.ui.AddClient;
import com.example.recensement.ui.ListActivity;

public class toolBar extends Activity {
    private DrawerLayout drawer;
    private Toolbar toolbar;


    public toolBar(DrawerLayout drawer,Toolbar toolBar) {
        this.drawer = drawer;
        this.toolbar = toolBar;
    }

    public void toolbarActivate (DrawerLayout drawer,Toolbar toolbar){

        //this.toolbar=findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //this.drawer= findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open_nav,R.string.close_nav);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


}
