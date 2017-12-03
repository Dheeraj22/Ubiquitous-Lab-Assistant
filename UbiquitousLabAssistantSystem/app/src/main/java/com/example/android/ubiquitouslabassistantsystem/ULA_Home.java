package com.example.android.ubiquitouslabassistantsystem;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.List;

public class ULA_Home extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ApplianceAdapter adapter;
    private List<Appliance> applianceList;
    private Toolbar toolbar;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ula__home);

        toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");

        //initCollapsingToolbar();
        createBurgerTab();

        recyclerView = findViewById(R.id.recycler_view);
        firebaseAuth = FirebaseAuth.getInstance();

        applianceList = new ArrayList<>();
        adapter = new ApplianceAdapter(this, applianceList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();

        try {
            //Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
            */

    private void createBurgerTab(){
        //            <-- Creating the Navigation Drawer -->      //

        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(0).withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_login).withIcon(FontAwesome.Icon.faw_sign_in);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_theatres).withIcon(FontAwesome.Icon.faw_industry);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_location).withIcon(FontAwesome.Icon.faw_location_arrow);
        SecondaryDrawerItem item5 = (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(0).withName(R.string.drawer_item_about_us).withIcon(FontAwesome.Icon.faw_info_circle);
        SecondaryDrawerItem item6 = (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(0).withName(R.string.drawer_item_contact_us).withIcon(FontAwesome.Icon.faw_whatsapp);
        SecondaryDrawerItem item7 = (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(0).withName(R.string.drawer_item_feedback).withIcon(FontAwesome.Icon.faw_commenting);
        SecondaryDrawerItem item8 = (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(0).withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_wrench);

        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.colorPrimaryDark)
                .addProfiles(
                        new ProfileDrawerItem().withName(getResources().getString(R.string.app_name)).withEmail(getResources().getString(R.string.email_id)).withIcon(getResources().getDrawable(R.drawable.ula_logo))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        //Intent intent = new Intent(Shmoti_Home.this, ShmotiWebView.class);
                        //startActivity(intent);
                        return false;
                    }
                })
                .build();
        //Create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1, item2, item3, item4,
                        new SectionDrawerItem().withName("Extras"),
                        item5, item6, item7, item8
                )

                //Set onClick options for drawer item click
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        switch (position) {
                            //Home
                            case 0:{
                                //Stay here
                                break;
                            }
                            //Logout
                            case 1:{

                            }
                            //signout
                            case 2:{firebaseAuth.signOut();
                                finish();
                                startActivity(new Intent(ULA_Home.this,MainActivity.class));
                                break;

                            }
                            //modes
                            case 3: {Intent intent = new Intent(ULA_Home.this, ModesActivity.class);
                                startActivity(intent);
                                break;

                            }
                            //Theatres
                            case 4:{
                                break;
                            }
                            //Location
                            case 5:{
                                break;

                            }
                            //About us
                            case 6:{
                                break;
                            }
                            //Contact us
                            case 7:{
                                break;
                            }
                            //Feedback
                            case 8:{
                                startActivity(new Intent(ULA_Home.this,Feedback.class));
                                break;
                            }
                            //Settings
                            case 9:{
                                startActivity(new Intent(ULA_Home.this,Feedback.class));
                                break;
                            }

                        }
                        return true;
                    }
                })
                .build();
    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.light4,
                R.drawable.fan,
                R.drawable.curtains,
                R.drawable.projector,
                };

        Appliance lights = new Appliance("Lights", 8, covers[0]);
        applianceList.add(lights);

        Appliance fans = new Appliance("Fans", 6, covers[1]);
        applianceList.add(fans);

        Appliance curtains = new Appliance("Curtains", 2, covers[2]);
        applianceList.add(curtains);

        Appliance projector = new Appliance("Projector", 1, covers[3]);
        applianceList.add(projector);

        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home: break;
        }
        return super.onOptionsItemSelected(item);
    }
}