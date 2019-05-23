package com.automarks.nuzatech.norchok.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidadvance.topsnackbar.TSnackbar;
import com.daimajia.slider.library.SliderLayout;

import com.daimajia.slider.library.SliderTypes.TextSliderView;

import com.automarks.nuzatech.norchok.Pojo.Main_sliderData;
import com.automarks.nuzatech.norchok.Public_Url;
import com.automarks.nuzatech.norchok.R;

import com.automarks.nuzatech.norchok.app.MyApplication;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by prakash on 3/28/2017.
 */

public class Dashboard extends android.support.v4.app.Fragment{

    View mainView;
    SliderLayout sliderLayout;
    LinearLayout contactus,apartment,gallery,location,travel,facilities,aboutus,booknow,testimonial;
    int duration=200;
    LinearLayout lineartop;

    Main_sliderData sliderdata;
    static String Registerurl=Public_Url.RegisterToken;


    TextView weather_location;

    ArrayList <Main_sliderData> data;

    TextView booknowtxt,facilitytxt,contacttxt;
    RecyclerView.LayoutManager layoutManager;
    Typeface type;
    RecyclerView recyclerView;
    String[] title={"Section 1","Section 2","Section 2","Section 3"};
    int[] image={R.drawable.apartment,R.drawable.apartmentpic,R.drawable.apartment,R.drawable.apartmentpic};
DashAdapter adapter;
    static String url=Public_Url.galleryAlbum;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        mainView=inflater.inflate(R.layout.dashboard_layout,container,false);


        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");


        recyclerView=(RecyclerView)mainView.findViewById(R.id.menu_list);
        recyclerView.setNestedScrollingEnabled(false);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        final Animation animScale = AnimationUtils.loadAnimation(getContext(), R.anim.button_animation);

recyclerView=(RecyclerView)mainView.findViewById(R.id.menu_list);


        Log.e("token id:",""+FirebaseInstanceId.getInstance().getToken());


        RegisterToken();


        weather_location=(TextView)mainView.findViewById(R.id.place_id);
        booknowtxt=(TextView)mainView.findViewById(R.id.booknow_text);
        facilitytxt=(TextView)mainView.findViewById(R.id.facility_text);
        contacttxt=(TextView)mainView.findViewById(R.id.contact_text);



//        aboutus=(LinearLayout) mainView.findViewById(R.id.aboutus_id);
//        gallery=(LinearLayout) mainView.findViewById(R.id.gallery_id);
//        location=(LinearLayout) mainView.findViewById(R.id.location_id);
//        travel=(LinearLayout)mainView.findViewById(R.id.travel_id);
//        apartment=(LinearLayout)mainView.findViewById(R.id.apartment_id);
        facilities=(LinearLayout)mainView.findViewById(R.id.linear_facilities);
        contactus=(LinearLayout)mainView.findViewById(R.id.linear_contact);
        booknow=(LinearLayout)mainView.findViewById(R.id.booknow);
//        testimonial=(LinearLayout)mainView.findViewById(R.id.testimonial_id);

        sendRequest();

        runAnimation(recyclerView);
        type = Typeface.createFromAsset(getContext().getAssets(),"fonts/Raleway-ExtraLight.ttf");
        booknowtxt.setTypeface(type);
        facilitytxt.setTypeface(type);
        contacttxt.setTypeface(type);


        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(animScale);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Facilities");
                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
                        Contact_us contact=new Contact_us();
                        transaction.replace(R.id.mainFragment,contact);
                        transaction.addToBackStack("Contact");
                        transaction.commit();

                    }
                }, duration);

            }
        });


        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animScale);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
                        Booking_form form=new Booking_form();

                        Bundle arg=new Bundle();
                        arg.putString("unit_name","All");
                        form.setArguments(arg);

                        transaction.replace(R.id.mainFragment,form);
                        transaction.addToBackStack("book now");
                        transaction.commit();

                    }
                }, duration);
            }
        });

        facilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(animScale);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Facilities");
                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
                        Facilities facilities=new Facilities();
                        transaction.replace(R.id.mainFragment,facilities);
                        transaction.addToBackStack("Facilities");
                        transaction.commit();

                    }
                }, duration);

            }
        });

        sliderLayout = (SliderLayout) mainView.findViewById(R.id.dashboard_slider);


        lineartop=(LinearLayout)mainView.findViewById(R.id.linearapt);


        if (!isOnline())
        {

            TSnackbar snackbar = TSnackbar.make(lineartop, "No Internet connection", TSnackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.WHITE);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
            TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();


        }





lineartop.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        Apartments apartments=new Apartments();
        transaction.replace(R.id.mainFragment,apartments);
        transaction.addToBackStack("apartment");
        transaction.commit();

    }
});


        return mainView;



    }
    void RegisterToken()
    {
        StringRequest request=new StringRequest(Request.Method.POST, Registerurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj = new JSONObject(response);




                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(getContext(), "Parse error", Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (isOnline())
                {
                    Toast.makeText(getContext(), "Unable to register token", Toast.LENGTH_SHORT).show();
                }




            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parms=new HashMap<>();
                parms.put("token",FirebaseInstanceId.getInstance().getToken());
                parms.put("type","android");
                parms.put("language","en");

                Log.d("Token no :",""+FirebaseInstanceId.getInstance().getToken());



                return parms;



            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

    void sendRequest() {
        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(final JSONArray response) {

                data=new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {

                    try {

                        final JSONObject obj = response.getJSONObject(i);
                        if (obj != null) {

                            sliderdata=new Main_sliderData();
                            sliderdata.setImage(obj.getString("image_url"));
                            sliderdata.setRoomname(obj.getString("Room Type"));
                            data.add(sliderdata);

                            final TextSliderView textSliderView = new TextSliderView(getContext());
                            textSliderView
                                    .description(obj.getString("Room Type"))
                                    .image(obj.getString("image_url"));


                            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Fade);
                            sliderLayout.addSlider(textSliderView);




                        } else {
                            Toast.makeText(getContext(), "NO data found", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
//                    slider.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
                    sliderLayout.setDuration(5000);


                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (isOnline()) {
                    Toast.makeText(getContext(), "Server error", Toast.LENGTH_SHORT).show();
                }

            }
        });


        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);


    }


    private void runAnimation(RecyclerView recyclerView)
    {
        Context context=recyclerView.getContext();
        LayoutAnimationController controller=null;



        controller = AnimationUtils.loadLayoutAnimation(context,R.anim.layout_slide_from_right);

        adapter=new DashAdapter(title,image);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }


    class DashAdapter extends RecyclerView.Adapter<DashHolder>
    {
        String[] title;
        int[] img;

        public DashAdapter(String[] tite,int[] img)
        {

            this.title=tite;
            this.img=img;
        }
        @NonNull
        @Override
        public DashHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dashboard_design,viewGroup,false);
            DashHolder holder=new DashHolder(view);


            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull DashHolder viewHolder, int i) {
            viewHolder.title.setText(title[i]);
            viewHolder.image.setImageResource(image[i]);

//        setAnimation(viewHolder.itemView, i);


        }

        @Override
        public int getItemCount() {
            return title.length;
        }

    }


    class DashHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView title;
        ImageView image;

        public DashHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener((View.OnClickListener) this);
            title=(TextView)itemView.findViewById(R.id.dash_title);
            image=(ImageView) itemView.findViewById(R.id.dash_image);
        }

        @Override
        public void onClick(View view) {
            int positon = getAdapterPosition();


            if (positon==0)
            {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();

//                Payment_form form=new Payment_form();
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.replace(R.id.pFragment, form);
//                fragmentTransaction.commit();

            }
            if  (positon==1)
            {


            }
            if  (positon==2)
            {


            }
        }
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
        MyApplication.getInstance().trackScreenView("Home Page");
    }





}
