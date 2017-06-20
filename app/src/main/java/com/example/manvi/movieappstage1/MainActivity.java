package com.example.manvi.movieappstage1;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity{

    private final String TAG = MainActivity.class.getSimpleName();
    private final String Frag_TAG = "Main_Fragment";

    private MainActivityFragment mfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        mfragment = (MainActivityFragment) fm.findFragmentByTag(Frag_TAG);

        if(mfragment == null)
        {
            mfragment = new MainActivityFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_movies,mfragment,Frag_TAG).commit();
        }
    }

}

