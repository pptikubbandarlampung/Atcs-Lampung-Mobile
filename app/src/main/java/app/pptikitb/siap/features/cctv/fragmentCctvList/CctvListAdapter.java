package app.pptikitb.siap.features.cctv.fragmentCctvList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import app.pptikitb.siap.R;
import app.pptikitb.siap.features.cctv.VideoStreamsActivity;
import app.pptikitb.siap.features.cctv.model.Cctvs;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class CctvListAdapter extends RecyclerView.Adapter<CctvListAdapter.ViewHolder> {

    private Context context;
    private List<Cctvs>cctvs ;
    private Bitmap bitmap ;
    public interface OnItemSelected {
        void onSelect(String VideoUrl);
    }


    public CctvListAdapter(Context context , List<Cctvs> cctv) {
        this.context = context ;
        this.cctvs = cctv ;
    }

    @Override
    public CctvListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_cctv_list, parent, false);
        CctvListAdapter.ViewHolder viewHolder = new CctvListAdapter.ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Cctvs cctv = cctvs.get(position);
        Glide.with(context)
                .asBitmap()
                .load(cctv.getUrl())
                .apply(new RequestOptions().placeholder(R.mipmap.loading_image).error(R.mipmap.kamera_akses_error))
                .into(holder.mVideoView);
        holder.mCctvName.setText(cctv.getNama());
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder
                                     .getFromLocation(Double.parseDouble(cctv.getLocation()
                                     .getCoordinates().get(0)),
                                     Double.parseDouble(cctv.getLocation()
                                     .getCoordinates().get(1)),
                                             1);
            int maxAddressLine = addresses.get(0).getMaxAddressLineIndex();
            String countryName = addresses.get(0).getAddressLine(maxAddressLine);
            holder.mCctvLocation.setText(countryName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoStreamsActivity.class);
                intent.putExtra("videoUrl", cctv.getUrl());
                context.startActivity(intent);

            }
        });

    }



    @Override
    public int getItemCount() {
        return cctvs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public ImageView mVideoView ;
        public TextView mCctvName ;
        public TextView mCctvLocation;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mVideoView = view.findViewById(R.id.video_view);
            mCctvName = view.findViewById(R.id.mCctvName);
            mCctvLocation = view.findViewById(R.id.mcctvLocation);
        }


    }



    public void swap(List<Cctvs> datas) {
        if (datas == null || datas.size() == 0)
            return;
        if (cctvs != null && cctvs.size() > 0)
            cctvs.clear();
        cctvs.addAll(datas);

        notifyDataSetChanged();

    }






}
