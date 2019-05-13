package app.pptikitb.siap.features.cctv;

import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import java.util.ArrayList;
import java.util.List;

import app.pptikitb.siap.R;
import app.pptikitb.siap.features.cctv.fragmentCctvList.CctvListFragment;
import app.pptikitb.siap.features.cctv.fragmentCctvList.CctvPresenter;
import app.pptikitb.siap.features.cctv.model.Cctvs;
import app.pptikitb.siap.ui.AnimationHelper;
import app.pptikitb.siap.ui.BottomDialogs;
import app.pptikitb.siap.ui.CustomDrawable;
import app.pptikitb.siap.ui.SweetDialogs;
import app.pptikitb.siap.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CctvActivity extends Fragment implements ICctvView, OnMapReadyCallback, View.OnClickListener {

    GoogleMap Gmap;
    SupportMapFragment GmapView;
    Animation slideUp, slideDown;
    @BindView(R.id.list_layout)
    LinearLayout mListMainLayout;
    @BindView(R.id.list_viewpager)
    ViewPager mListViewPager;
    @BindView(R.id.listbtn_fab)
    FloatingActionButton mListBtnFab;
    List<CctvListFragment> mCctvFragment = new ArrayList<>();
    CctvPresenter presenter;
    List<Cctvs> cctvs = new ArrayList<>();
    @BindView(R.id.loading_layout)
    RelativeLayout mLoadingLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_cctv, container, false);
        ButterKnife.bind(this, view);
        this.initView();
        this.hideTrackerList();
        presenter = new CctvPresenter(this);
        presenter.getCctvList();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_cctv);
        //getSupportActionBar().hide();

    }

    @Override
    public void initView() {
        GmapView = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.maps);
        GmapView.getMapAsync(this);
        //GmapView = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.maps)).getMap();

        //mListBtnFab.setOnClickListener(this);

    }

    @Override
    public void showMediaPlayer(String url) {

    }

    @Override
    public void initCctvList() {
        mListBtnFab.setOnClickListener(this);
        final CctvPageAdapter adapter = new CctvPageAdapter(getChildFragmentManager());
        CctvListFragment cctvfragment = new CctvListFragment();
        cctvfragment.setData(cctvs);
        //Log.v("woii", String.valueOf(cctvs));
        //for (Cctvs cctv : this.cctvs) {
        //    Log.v("woii", String.valueOf(cctv.getLocation().getCoordinates().get(0)));
        //}
        adapter.addFragment(cctvfragment, "Tittle");
        mCctvFragment.add(cctvfragment);
        mListViewPager.setAdapter(adapter);

    }

    @Override
    public void initMarker() {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        int height = 60;
        int width = 50;
        for (Cctvs cctv : this.cctvs) {
            BitmapDescriptor icon = null;
            LatLng coordinate = new LatLng(Double.parseDouble(cctv.getLocation().getCoordinates().get(0)), Double.parseDouble(cctv.getLocation().getCoordinates().get(1)));
//            Gmap.addMarker(new MarkerOptions().position(coordinate)
//                    .title(location.getNama()));
            Bitmap myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.cctv_icon);
            Bitmap tintImage = Utils.tintImage(myIcon);
            Bitmap markers = Bitmap.createScaledBitmap(tintImage, width, height, false);
            icon = BitmapDescriptorFactory.fromBitmap(markers);
            Gmap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(cctv.getLocation().getCoordinates().get(0)), Double.parseDouble(cctv.getLocation().getCoordinates().get(1))))
                    .title(cctv.getNama())
                    .snippet(cctv.getUrl())
                    .icon(icon));
            if (coordinate != null) {
                builder.include(coordinate);
                LatLngBounds bounds = builder.build();
                Gmap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 180));
            }

            Gmap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    marker.hideInfoWindow();
                    BottomDialogs.showTrackerDetail(getActivity(), marker);
//                    CustomInfoWindowAdapter markerInfoWindowAdapter = new CustomInfoWindowAdapter(getApplicationContext());
//                    Gmap.setInfoWindowAdapter(markerInfoWindowAdapter);
                    return true;
                }
            });
        }


    }

    @Override
    public void onDataRready(List<Cctvs> cctv) {
        this.cctvs = cctv;
        this.initCctvList();
        this.initMarker();
    }

    @Override
    public void onNetworkError(String message) {
        Log.e("String error", message);
        SweetDialogs.endpointError(getActivity());
    }

    @Override
    public void showLoadingIndicator() {
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mLoadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showTrackerList() {
        if (slideUp == null) {
            slideUp = AnimationHelper.getAnimation(getActivity(), R.anim.slide_up, anim -> {

            });
        }
        mListBtnFab.setImageDrawable(CustomDrawable.googleMaterialDrawable(
                getActivity(), R.color.flat_blue, 24, GoogleMaterial.Icon.gmd_keyboard_arrow_down
        ));
        mListMainLayout.setVisibility(View.VISIBLE);
        mListMainLayout.startAnimation(slideUp);
    }

    @Override
    public void hideTrackerList() {
        if (slideDown == null) {
            slideDown = AnimationHelper.getAnimation(getActivity(), R.anim.slide_down, anim -> {
                mListMainLayout.setVisibility(View.GONE);
                mListBtnFab.setImageDrawable(CustomDrawable.googleMaterialDrawable(
                        getActivity(), R.color.flat_blue, 24, GoogleMaterial.Icon.gmd_view_list
                ));

            });
        }
        mListMainLayout.startAnimation(slideDown);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Gmap = googleMap;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (GmapView != null)
            GmapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (GmapView != null)
            GmapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (GmapView != null)
            GmapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (GmapView != null)
            GmapView.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.listbtn_fab:
                if (mListMainLayout.getVisibility() == View.VISIBLE)
                    this.hideTrackerList();
                else this.showTrackerList();
                break;
        }
    }

    /*@Override
    public void onBackPressed() {
        if (mListMainLayout.getVisibility() == View.VISIBLE)
            this.hideTrackerList();
        else super.onBackPressed();
    }*/
}
