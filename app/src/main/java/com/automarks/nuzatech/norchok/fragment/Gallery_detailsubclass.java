package com.automarks.nuzatech.norchok.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.androidadvance.topsnackbar.TSnackbar;
import com.automarks.nuzatech.norchok.Pojo.Album_data;

import com.automarks.nuzatech.norchok.Public_Url;
import com.automarks.nuzatech.norchok.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by prakash on 3/5/2018.
 */

public class Gallery_detailsubclass extends android.support.v4.app.Fragment {

    String url= Public_Url.gallerysubclass;
    String id;
    View mainView;
    ProgressDialog pDilog;
    ArrayList<Album_data> data;
    Album_data gallery;
    RecyclerView recycler;
    SubgalleryRecycler adapter;
    FrameLayout frame;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
mainView=inflater.inflate(R.layout.gallery_subclass_layout,container,false);

        pDilog = ProgressDialog.show(getActivity(), null, null, true);
        pDilog.setContentView(R.layout.apartment_layout);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();

recycler=(RecyclerView)mainView.findViewById(R.id.album_recycler_subclass);

        id=getArguments().getString("nid");


        if (!isOnline())
        {

            frame=(FrameLayout)mainView.findViewById(R.id.frame_typeclass) ;



            TSnackbar snackbar = TSnackbar.make(frame, "No Internet connection", TSnackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.WHITE);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
            TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();




        }

        getApartment();

        return mainView;
    }
    void getApartment()
    {

        final JsonArrayRequest request=new JsonArrayRequest(url+id, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pDilog.dismiss();
                data=new ArrayList<>();




                    for (int i=0;i<response.length();i++)
                    {
                        try {

                            gallery=new Album_data();

                            JSONObject object=response.getJSONObject(i);



                            gallery=new Album_data();


                            gallery.setmImage(object.getString("mobile_banner"));
                            gallery.setMunitname(object.getString("title"));
                            gallery.setNid(object.getString("nid"));



                            data.add(gallery);


                            adapter = new SubgalleryRecycler(getContext(), data);
                            recycler.setAdapter(adapter);
//                            recycler.setLayoutManager(new LinearLayoutManager(Album_View_Gallery.this));
                            recycler.setLayoutManager(new GridLayoutManager(getContext(),2));



                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Exception occure", Toast.LENGTH_SHORT).show();
                        }


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Server error", Toast.LENGTH_SHORT).show();

            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    class SubgalleryRecycler extends RecyclerView.Adapter<SubgalleryHolder> {

        Context context;
        ArrayList<Album_data> data;
        LayoutInflater layoutinflater;

        public SubgalleryRecycler(Context context, ArrayList<Album_data> data) {
            this.context = context;
            this.data = data;
            layoutinflater = LayoutInflater.from(context);

        }

        @Override
        public SubgalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = layoutinflater.inflate(R.layout.gallery_album_design, parent, false);
            SubgalleryHolder holder = new SubgalleryHolder(view, context, data);
            return holder;
        }

        @Override
        public void onBindViewHolder(final SubgalleryHolder holder, int position) {
            final Album_data current = data.get(position);
            holder.mText.setText(current.getMunitname());

            Picasso.with(context)
                    .load(current.getmImage())
                    .placeholder(R.drawable.defult)   // optional
                    .error(R.drawable.defult)      // optional
                    .resize(600, 340)
                    .into(holder.gImage);



        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
    class SubgalleryHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView gImage;
        TextView mText;

        Context context;
        ArrayList<Album_data> data;

        public SubgalleryHolder(View itemView, Context context, ArrayList<Album_data> data) {
            super(itemView);
            this.context = context;
            this.data = data;

            itemView.setOnClickListener((View.OnClickListener) this);

            mText = (TextView) itemView.findViewById(R.id.gallery_label);
            gImage = (ImageView) itemView.findViewById(R.id.gallary_image);

        }

        @Override
        public void onClick(View v) {
            int positon=getAdapterPosition();
            Album_data current = data.get(positon);





            FragmentTransaction transaction=getFragmentManager().beginTransaction();

            Gallery_photos photos = new Gallery_photos();
            Bundle args = new Bundle();
            args.putString("nid", current.getNid());
            photos.setArguments(args);

            transaction.replace(R.id.mainFragment,photos);
            transaction.addToBackStack("Gallery");
            transaction.commit();



        }
    }

}


