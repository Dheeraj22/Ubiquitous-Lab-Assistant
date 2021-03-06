package com.example.android.ubiquitouslabassistantsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LightsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LightsAdapter adapter;
    private List<Lights> lightsList;
    private Toolbar toolbar;
    private EditText etUrl;
    private EditText etPort;
    private TextInputLayout inputTextURL, inputTextPort;
    public static SharedPreferences sp;
    Boolean state = false;
    private String[] states = new String[]{"OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lights);

        initToolbar();

        recyclerView = findViewById(R.id.recycler_lights);

        lightsList = new ArrayList<>();
        adapter = new LightsAdapter(this, lightsList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.addItemDecoration(new LightsActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        ConfigUrl();



        prepareAlbums();

//        if(sp.getBoolean("isFirstLaunch", true)){
//            new showMessageAsync().execute();
//            sp.edit().putBoolean("isFirstLaunch", false).apply();
//        }
    }

    private void initToolbar(){
        toolbar = findViewById(R.id.toolbar_lights);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void prepareAlbums() {
        final int cover = R.drawable.light_off;

//        // Get a reference to our posts
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("Lights");
//
//        // Attach a listener to read the data at our posts reference
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(int i = 0; i < 9; i++){
//                    states[i] = dataSnapshot.child("light" + String.valueOf(i+1)).getValue(String.class);
//                    Log.d("Status", states[i]);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });


        Lights light1 = new Lights("Light1", states[0], cover);
        lightsList.add(light1);

        Lights light2 = new Lights("Light2", states[1], cover);
        lightsList.add(light2);

        Lights light3 = new Lights("Light3", states[2], cover);
        lightsList.add(light3);

        Lights light4 = new Lights("Light4", states[3], cover);
        lightsList.add(light4);

        Lights light5 = new Lights("Light5", states[4], cover);
        lightsList.add(light5);

        Lights light6 = new Lights("Light6", states[5], cover);
        lightsList.add(light6);

        Lights light7 = new Lights("Light7", states[6], cover);
        lightsList.add(light7);

        Lights light8 = new Lights("Light8", states[7], cover);
        lightsList.add(light8);

        Lights light9 = new Lights("All Lights", states[8], cover);
        lightsList.add(light9);

        adapter.notifyDataSetChanged();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void ConfigUrl() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alert_url, null);
        etUrl =  alertLayout.findViewById(R.id.input_url);
        etPort =  alertLayout.findViewById(R.id.input_port);
        inputTextURL = alertLayout.findViewById(R.id.input_layout_name);
        inputTextPort = alertLayout.findViewById(R.id.input_layout_email);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = sp.edit();
        String url_saved = sp.getString("url", "");
        String port_saved = sp.getString("port", "");

        if (!(url_saved.toString().isEmpty() && port_saved.toString().isEmpty())) {
            etUrl.setText(url_saved);
            etPort.setText(port_saved);
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Configure");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String url = etUrl.getText().toString();
                String port = etPort.getText().toString();
                if (validateURL()) {
                    editor.putString("url", url);
                    editor.putString("port", port);
                    editor.apply();
                    dialog.dismiss();
                    Toast.makeText(LightsActivity.this, "Configuration Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LightsActivity.this, "Do not leave blank", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();


    }

    private Boolean validateURL() {
        if (etUrl.getText().toString().isEmpty()) {
            inputTextURL.setError("Enter a URL");
            return false;
        } else {
            inputTextURL.setErrorEnabled(false);
        }
        if (etPort.getText().toString().isEmpty()) {
            inputTextPort.setError("Enter a port number");
            return false;
        } else {
            inputTextPort.setErrorEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home: onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private class showMessageAsync extends AsyncTask<String, Void, String> {
        AlertDialog.Builder alert;
        View alertLayout;
        SharedPreferences.Editor editor;

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            LayoutInflater inflater = getLayoutInflater();
            alertLayout = inflater.inflate(R.layout.alert_url, null);
            etUrl =  alertLayout.findViewById(R.id.input_url);
            etPort =  alertLayout.findViewById(R.id.input_port);
            inputTextURL = alertLayout.findViewById(R.id.input_layout_name);
            inputTextPort = alertLayout.findViewById(R.id.input_layout_email);
            sp = PreferenceManager.getDefaultSharedPreferences(LightsActivity.this);
            editor = sp.edit();
            String url_saved = sp.getString("url", "");
            String port_saved = sp.getString("port", "");

            if (!(url_saved.toString().isEmpty() && port_saved.toString().isEmpty())) {
                etUrl.setText(url_saved);
                etPort.setText(port_saved);
            }

            alert = new AlertDialog.Builder(LightsActivity.this);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            alert.setTitle("Configure");
            alert.setView(alertLayout);
            alert.setCancelable(false);
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String url = etUrl.getText().toString();
                    String port = etPort.getText().toString();
                    if (validateURL()) {
                        editor.putString("url", url);
                        editor.putString("port", port);
                        editor.apply();
                        dialog.dismiss();
                        Toast.makeText(LightsActivity.this, "Configuration Saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LightsActivity.this, "Do not leave blank", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            AlertDialog dialog = alert.create();
            dialog.show();
        }

    }


}
