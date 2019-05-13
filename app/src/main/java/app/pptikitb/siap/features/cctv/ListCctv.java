package app.pptikitb.siap.features.cctv;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import app.pptikitb.siap.R;
import app.pptikitb.siap.features.cctv.fragmentCctvList.CctvListFragment;
import app.pptikitb.siap.features.cctv.fragmentCctvList.CctvPresenter;
import app.pptikitb.siap.features.cctv.model.Cctvs;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListCctv extends Fragment implements ICctvView{

    List<Cctvs> cctvs = new ArrayList<>();
    List<CctvListFragment> mCctvFragment = new ArrayList<>();
    @BindView(R.id.list_layout)
    LinearLayout mListMainLayout;
    @BindView(R.id.list_viewpager)
    ViewPager mListViewPager;
    CctvPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_list_cctv, container, false);
        ButterKnife.bind(this, view);
        presenter = new CctvPresenter(this);
        presenter.getCctvList();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list_cctv);
    }

    @Override
    public void initView() {

    }

    @Override
    public void showMediaPlayer(String url) {

    }

    @Override
    public void initCctvList() {
        final CctvPageAdapter adapter = new CctvPageAdapter(getChildFragmentManager());
        CctvListFragment cctvfragment = new CctvListFragment();
        cctvfragment.setData(cctvs);
        adapter.addFragment(cctvfragment, "Tittle");
        mCctvFragment.add(cctvfragment);
        mListViewPager.setAdapter(adapter);
    }

    @Override
    public void initMarker() {

    }

    @Override
    public void onDataRready(List<Cctvs> cctv) {
        this.cctvs = cctv;
        this.initCctvList();
        //this.initMarker();
    }

    @Override
    public void onNetworkError(String message) {

    }

    @Override
    public void showLoadingIndicator() {

    }

    @Override
    public void hideLoadingIndicator() {

    }

    @Override
    public void showTrackerList() {

    }

    @Override
    public void hideTrackerList() {

    }
}