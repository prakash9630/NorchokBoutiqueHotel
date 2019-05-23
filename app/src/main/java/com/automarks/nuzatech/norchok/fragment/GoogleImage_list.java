package com.automarks.nuzatech.norchok.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.automarks.nuzatech.norchok.R;

/**
 * Created by prakash on 3/14/2018.
 */

public class GoogleImage_list extends Fragment {
    View mainView;
    LinearLayout a, b, c, d;
    String url;
    String aprtmentname;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.googleimage_list_layout, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Google images lists");

        aprtmentname = getArguments().getString("book");


        a = (LinearLayout) mainView.findViewById(R.id.apartment_A);
        b = (LinearLayout) mainView.findViewById(R.id.apartmen_B);
        c = (LinearLayout) mainView.findViewById(R.id.apartment_c);
        d = (LinearLayout) mainView.findViewById(R.id.apartment_d);


        if (aprtmentname.equals("2_bedroom_standard")) {

            c.setVisibility(View.GONE);


            a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    url = "https://photos.google.com/share/AF1QipMpN5v6EH3ao2wFbjN4cZ5Ws8LmlGE5BzRahmliBmmHPuCrUM0IN3fe1FwlQP94uA?key=V3BBWVNLTU9zWVZrLXgzWjQ0UE1NMnZ0Y2FqcXd3";


                    Redirect();

                }
            });

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    url = "https://photos.google.com/share/AF1QipN_JRDMagRVipcHksFbF7udoKoxhqOY9pHJpFIcYaWxkfb3W6LcAlME6pVRCRpwaQ?key=cVpFY2ZUOTJqMnhISDFUUVhqYUdRaFRiZXhDMW1n";


                    Redirect();

                }


            });


            d.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    url = "https://photos.google.com/share/AF1QipPd4RtYt-MRM0kFSEXo3Ke369xNvVr8FmeWK1kGr19LKNlkrWQ3FIxiuwtqV6GiEw?key=SklSTEVHT2dUelJfT2QtRjhZWXJRcFR4YVNZbVBn";
                    Redirect();

                }
            });


        } else {

            a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    url = "https://photos.google.com/share/AF1QipNSQnkZehZ5UlgAPBjCiLFSuhUHPdiR32B3Zjdyzc8vkIwzge12yXj5l60FmG4wOA?key=TWV0SFFWSUQ1ajYwZENZR0hDVE9wdVNiVFk1Nk9n";


                    Redirect();

                }
            });

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    url = "https://photos.google.com/share/AF1QipN1SpJujkbWTv6HYnCgzkULFwaoI3WZbUvV0X-ykdKWqBo3O-iq673WPw5OJ2WTuA?key=MWFQV0RRMTZCZnBwM3pWUlJlaFozUzF2Vng1TkdR";


                    Redirect();

                }


            });

            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    url = "https://photos.google.com/share/AF1QipOAdWcKS9aJNeS1tio-Ci3Jksumgn2oQHiVKPbLUBesNe0-37oIO4A5KRxGkN_5Kg?key=enRVX3MtVFNBS1BmZ01FYnhnVTRlOTVNUHhUdDN3";
                    Redirect();

                }
            });


            d.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    url = "https://photos.google.com/share/AF1QipM0Xa7DQs6vqJyzQMvAtomfxT8j0-dWVf6ylA3V4eug5yUg9-f4fLCUiLQsxk3xzw?key=VjBvdFJVVTdMRHY5UDdQWUhaUG4wWlVVUk1sdEZ3";
                    Redirect();

                }
            });


        }


        return mainView;
    }

    void Redirect()

    {

//        FragmentTransaction transaction=getFragmentManager().beginTransaction();
//        Google_webimage list=new Google_webimage();
//        Bundle arg=new Bundle();
//        arg.putString("url",url);
//        list.setArguments(arg);
//        transaction.replace(R.id.mainFragment,list);
//        transaction.addToBackStack("Google image");
//        transaction.commit();

        Intent i = new Intent(getContext(), Google_webimage.class);
        i.putExtra("url", url);
        startActivity(i);

    }
}
