package com.trannguyentanthuan2903.volleyjson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 4/21/2017.
 */

public class Adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Video> arrayVideo;

    public Adapter(Context context, int layout, List<Video> arrayVideo) {
        this.context = context;
        this.layout = layout;
        this.arrayVideo = arrayVideo;
    }

    @Override
    public int getCount() {
        return arrayVideo.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayVideo.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{

        TextView txTitle,txDate;
        ImageView imAva;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowview= view;
        ViewHolder holder = new ViewHolder();
        if (rowview == null){
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview =layoutInflater.inflate(layout,null);
            holder.txTitle =(TextView) rowview.findViewById(R.id.textViewTitle);
            holder.txDate=(TextView) rowview.findViewById(R.id.textViewdate);
            holder.imAva=(ImageView) rowview.findViewById(R.id.imageViewAva);
            rowview.setTag(holder);
        }else {
            holder =(ViewHolder) rowview.getTag();
        }

        Video vd = arrayVideo.get(i);
        holder.txTitle.setText(vd.Title);
        holder.txDate.setText(vd.Date);
        Picasso.with(context).load(vd.Image).into(holder.imAva);
        return rowview;
    }
}
