package com.example.android.ubiquitouslabassistantsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lights);

        initToolbar();

//

        recyclerView = findViewById(R.id.recycler_lights);

        lightsList = new ArrayList<>();
        adapter = new LightsAdapter(this, lightsList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new LightsActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();

//        if(sp.getBoolean("isFirstLaunch", true)){
//            ConfigUrl();
//            sp.edit().putBoolean("isFirstLaunch", false).apply();
//        }

        ConfigUrl();

        try {
            //Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initToolbar(){
        toolbar = findViewById(R.id.toolbar_lights);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void prepareAlbums() {
        int cover = R.drawable.light_off;

        String status = "OFF";

        Lights light1 = new Lights("Light1", status, cover);
        lightsList.add(light1);

        Lights light2 = new Lights("Light2", status, cover);
        lightsList.add(light2);

        Lights light3 = new Lights("Light3", status, cover);
        lightsList.add(light3);

        Lights light4 = new Lights("Light4", status, cover);
        lightsList.add(light4);

        Lights light5 = new Lights("Light5", status, cover);
        lightsList.add(light5);

        Lights light6 = new Lights("Light6", status, cover);
        lightsList.add(light6);

        Lights light7 = new Lights("Light7", status, cover);
        lightsList.add(light7);

        Lights light8 = new Lights("Light8", status, cover);
        lightsList.add(light8);

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
        inputTextPort = alertLayout.findViewById(R.id.input_layout_password);
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


}
