package com.automarks.nuzatech.norchok.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidadvance.topsnackbar.TSnackbar;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.automarks.nuzatech.norchok.Public_Url;
import com.automarks.nuzatech.norchok.R;
import com.automarks.nuzatech.norchok.app.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by prakash on 4/6/2017.
 */

public class Apartment_detail extends Fragment {

    String id, value;
    String googleurl;
    View mainView;

    String arrayurl = Public_Url.gallerysubclass;

    String url = Public_Url.detailimages;
    SliderLayout sliderShow;
    String title, body, image;


    WebView mBody;
    String pish = "<html><head><style type=\"text/css\">@font-face {font-family: 'Raleway';" +
            "src: url(\"file:///android_asset/fonts/Raleway-ExtraLight.ttf\")}body {font-family: 'Raleway';font-size: medium;text-align: justify;}</style></head><body>";
    String pas = "</body></html>";

    FrameLayout frame;
    ProgressDialog pDilog;
    LinearLayout booknow, photos, googlephotos;
    String machinename;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.apartment_detail, container, false);


        sliderShow = (SliderLayout) mainView.findViewById(R.id.slider_apartmetn);
        booknow = (LinearLayout) mainView.findViewById(R.id.booklinear);
        photos = (LinearLayout) mainView.findViewById(R.id.photogallery);
        googlephotos = (LinearLayout) mainView.findViewById(R.id.googlelayout);


        pDilog = ProgressDialog.show(getActivity(), null, null, true);
        pDilog.setContentView(R.layout.apartment_layout);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();


        mBody = (WebView) mainView.findViewById(R.id.body_room);
        mBody.setBackgroundColor(Color.parseColor("#00000000"));


        id = getArguments().getString("nid");
        machinename = getArguments().getString("machinename");

        if (!isOnline()) {

            frame = (FrameLayout) mainView.findViewById(R.id.frame_aptdetail);
            TSnackbar snackbar = TSnackbar.make(frame, "No Internet connection", TSnackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.WHITE);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
            TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();

        }

        getData();
        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApartment();
            }


        });

        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Booking_form form = new Booking_form();
                Bundle arg = new Bundle();
                arg.putString("book", machinename);
                arg.putString("unit_name", title);
                form.setArguments(arg);
                transaction.replace(R.id.mainFragment, form);
                transaction.addToBackStack("Apartment detail");
                transaction.commit();

            }
        });

        googlephotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getAprtmentgoogledata();

            }
        });


        return mainView;
    }

    void getData() {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                booknow.setVisibility(View.VISIBLE);
                photos.setVisibility(View.VISIBLE);
                googlephotos.setVisibility(View.VISIBLE);

                pDilog.dismiss();

                try {


                    String mobilebody = response.getString("field_mobile_body");

                    Object json = new JSONTokener(mobilebody).nextValue();
                    if (json instanceof JSONArray) {
                        JSONObject p2 = response.getJSONObject("body");
                        JSONArray array = p2.getJSONArray("und");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject paragraph = array.getJSONObject(i);
                            body = paragraph.getString("value");

                        }
                    } else if (json instanceof JSONObject) {
                        JSONArray mobilearray = ((JSONObject) json).getJSONArray("und");
                        for (int i = 0; i < mobilearray.length(); i++) {
                            JSONObject moblieobject = mobilearray.getJSONObject(i);

                            body = moblieobject.getString("value");
                        }
                    }


                    String mobileimage = response.getString("field_mobile_image");
                    Object jsonimage = new JSONTokener(mobileimage).nextValue();

                    if (jsonimage instanceof JSONArray) {
                        JSONObject fieldImage = response.getJSONObject("field_image");
                        JSONArray imagearrey = fieldImage.getJSONArray("und");
                        for (int ii = 0; ii < mobileimage.length(); ii++) {
                            JSONObject objimage = imagearrey.getJSONObject(ii);

                            image = objimage.getString("uri");
                            String uri = image.replace("public://", "");

                            TextSliderView textSliderView = new TextSliderView(getContext());
                            textSliderView
                                    .image("https://www.retreatservicedapartments.com/sites/default/files/" + uri);


                            sliderShow.addSlider(textSliderView);

                            sliderShow.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
//        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
//                    slider.setCustomAnimation(new DescriptionAnimation());
                            sliderShow.setDuration(5000);


                        }

                    } else if (jsonimage instanceof JSONObject) {
                        JSONObject mobilefieldImage = response.getJSONObject("field_mobile_image");
                        JSONArray mobileimagearrey = mobilefieldImage.getJSONArray("und");
                        for (int i = 0; i < mobileimagearrey.length(); i++) {
                            JSONObject mobileobject = mobileimagearrey.getJSONObject(i);
                            image = mobileobject.getString("filename");

                            TextSliderView textSliderView = new TextSliderView(getContext());
                            textSliderView
                                    .image("https://template.nuza.solutions/sites/default/files/mobile_image/" + image);


                            sliderShow.addSlider(textSliderView);

                            sliderShow.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);

                            sliderShow.setDuration(5000);


                        }

                    }

                    title = response.getString("title");
                    String myHtmlString = pish + body + pas;

                    mBody.loadDataWithBaseURL(null, myHtmlString, "text/html", "UTF-8", null);

                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);


                } catch (JSONException e) {

                    Toast.makeText(getContext(), "Exceptin occur", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Server error", Toast.LENGTH_SHORT).show();


                pDilog.dismiss();
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

    }


    void getAprtmentgoogledata() {


        final JsonArrayRequest request = new JsonArrayRequest(arrayurl + id, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pDilog.dismiss();

                if (response.length() != 0)

                {

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    GoogleImage_list list = new GoogleImage_list();
                    Bundle arg = new Bundle();
                    arg.putString("book", machinename);
                    list.setArguments(arg);
                    transaction.replace(R.id.mainFragment, list);
                    transaction.addToBackStack("Apartment detail");
                    transaction.commit();

                } else {
                    if (id.equals("43")) {
                        googleurl = "https://photos.google.com/share/AF1QipOwqSKjaoKcbfM_hIfuVF5HhNf7J_CpvPa4vvdcDrkcPFTD3YfbmWyerRi2SLVC9Q?key=NXliTnYtcUp0eXZhcFFPcUoySzlPaHFGMU1aYW5R";
                        getIntent();

                    } else if (id.equals("36")) {
                        googleurl = "https://photos.google.com/share/AF1QipNZDo4-YDUDXEjV2aFY-Ttd1a2B8xFZ78eKDAlZ_caB-4-1m--9yhPPFliJYRQUEQ?key=V1hNNDdzTGZWTUJaYVRNSFZvZHl4RktTb1V5QlRR";
                        getIntent();

                    } else if (id.equals("42")) {

                        googleurl = "https://photos.google.com/share/AF1QipMfwtWv2tXbFZD5g3_CFd75D9q1AuhMwSTXGLDIoZwFVXXm0EWI_b5V4htDh_qrag?key=NUVlV3R2WUdzNE1jd1hOQWpES0pQcXllSnZuWThR";
                        getIntent();
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

    void getApartment() {

        final JsonArrayRequest request = new JsonArrayRequest(arrayurl + id, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pDilog.dismiss();

                if (response.length() != 0)

                {


                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    Gallery_detailsubclass typephotos = new Gallery_detailsubclass();
                    Bundle args = new Bundle();
                    args.putString("nid", id);
                    typephotos.setArguments(args);

                    transaction.replace(R.id.mainFragment, typephotos);
                    transaction.addToBackStack("Gallery");
                    transaction.commit();

                } else {

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();


                    Gallery_photos photos = new Gallery_photos();
                    Bundle args = new Bundle();
                    args.putString("nid", id);
                    photos.setArguments(args);

                    transaction.replace(R.id.mainFragment, photos);
                    transaction.addToBackStack("Gallery");
                    transaction.commit();

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

    void getIntent() {

        Intent i = new Intent(getContext(), Google_webimage.class);
        i.putExtra("url", "" + googleurl);
        startActivity(i);
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

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("Apartment detail");
    }

}
