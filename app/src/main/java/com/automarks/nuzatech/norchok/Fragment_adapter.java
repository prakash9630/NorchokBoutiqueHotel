package com.automarks.nuzatech.norchok;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.automarks.nuzatech.norchok.fragment.Cultural_places_activity;
import com.automarks.nuzatech.norchok.fragment.Recreational_sites;
import com.automarks.nuzatech.norchok.fragment.Restaurant_activity;



/**
 * Created by prakash on 9/21/2016.
 */
public class Fragment_adapter extends FragmentStatePagerAdapter {
    public Fragment_adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                Cultural_places_activity c=new Cultural_places_activity();
            return c;
            case 1:
                Restaurant_activity r=new Restaurant_activity();
                return r;
            case 2:
                Recreational_sites rs=new Recreational_sites();
                return rs;

        }


        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}

