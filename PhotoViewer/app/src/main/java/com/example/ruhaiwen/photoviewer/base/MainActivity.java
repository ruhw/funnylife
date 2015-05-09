package com.example.ruhaiwen.photoviewer.base;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.ruhaiwen.photoviewer.R;
import com.example.ruhaiwen.photoviewer.com.example.ruhaiwen.photoviewer.common.LoadImageService;
import com.nostra13.universalimageloader.core.ImageLoader;


public class MainActivity extends FragmentActivity implements BaseListFragment.OnFragmentInteractionListener ,
                                            DetailsFragment.OnFragmentInteractionListener{

    public int mPosition;

    /**
     *  用于加载文件
     */
    private LoadImageService.MyBinder myBinder;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (LoadImageService.MyBinder) service;
            myBinder.searchImages();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createFragment();
        Intent bindIntent = new Intent(this, LoadImageService.class);
        bindService(bindIntent, connection, BIND_AUTO_CREATE);

    }

    public  Fragment createFragment(){

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        FragmentTransaction transaction =fm.beginTransaction();
        if(fragment == null){
            fragment = HomeFragment.newInstance();
            transaction.add(R.id.fragmentContainer, fragment);

        }
        else {
            fragment = DetailsFragment.newInstance(mPosition);
            transaction.replace(R.id.fragmentContainer,fragment);
            transaction.addToBackStack(null);
        }
        transaction.commit();
        return fragment;
    }

    @Override
    public void onFragmentInteraction(int mPosition) {
        this.mPosition = mPosition;
        createFragment();
    }

    @Override
    public void onFragmentInteraction(Boolean isShow) {

    }



   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
