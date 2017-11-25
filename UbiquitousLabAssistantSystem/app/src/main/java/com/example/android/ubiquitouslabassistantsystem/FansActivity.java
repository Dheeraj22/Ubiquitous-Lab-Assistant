package com.example.android.ubiquitouslabassistantsystem;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FansActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FansAdapter adapter;
    private List<Fans> fansList;
    private Toolbar toolbar;
    private EditText etUrl;
    private EditText etPort;
    private TextInputLayout inputTextURL, inputTextPort;
    public static SharedPreferences spFan;
    Boolean state = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fans);

        initToolbar();

        recyclerView = findViewById(R.id.recycler_fans);

        fansList = new ArrayList<>();
        adapter = new FansAdapter(this, fansList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new FansActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
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
        toolbar = findViewById(R.id.toolbar_fans);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void prepareAlbums() {
        int cover = R.drawable.fan;

        String status = "OFF";

        Fans fan1 = new Fans("Fan1", status, cover);
        fansList.add(fan1);

        Fans fan2 = new Fans("Fan2", status, cover);
        fansList.add(fan2);

        Fans fan3 = new Fans("Fan3", status, cover);
        fansList.add(fan3);

        Fans fan4 = new Fans("Fan4", status, cover);
        fansList.add(fan4);

        Fans fan5 = new Fans("Fan5", status, cover);
        fansList.add(fan5);

        Fans fan6 = new Fans("Fan6", status, cover);
        fansList.add(fan6);

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
        spFan = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = spFan.edit();
        String url_saved = spFan.getString("url", "");
        String port_saved = spFan.getString("port", "");

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
                    Toast.makeText(FansActivity.this, "Configuration Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FansActivity.this, "Do not leave blank", Toast.LENGTH_SHORT).show();
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
