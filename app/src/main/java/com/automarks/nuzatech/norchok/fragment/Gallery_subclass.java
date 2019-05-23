package com.automarks.nuzatech.norchok.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.Volley;
import com.automarks.nuzatech.norchok.Public_Url;
import com.automarks.nuzatech.norchok.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by prakash on 3/5/2018.
 */

public class Gallery_subclass extends android.support.v4.app.Fragment {

    View mainView;
    String url = Public_Url.galleryAlbum;
    String id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.gallery_subclass_layout, container, false);

        Toast.makeText(getActivity(), "Your are in gallery subclass", Toast.LENGTH_SHORT).show();

        id = getArguments().getString("value");
        return mainView;
    }


    void sendRequest() {
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject obj = response.getJSONObject(i);
                        if (obj != null) {



                        } else {
                            Toast.makeText(getContext(), "NO data found", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Network problem", Toast.LENGTH_SHORT).show();

                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });


        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);


    }

}