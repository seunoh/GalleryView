package com.seun.gallery;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.seun.gallery.util.ImageWorker;
import com.seun.gallery.util.RecyclingImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author by seunoh on 2014. 07. 05..
 */
public class GalleryAdapter extends ArrayAdapter<String> {

    private List<String> mItems;
    private ImageWorker mImageWorker;

    public GalleryAdapter(Context context, ImageWorker imageWorker) {
        this(context, new ArrayList<String>(), imageWorker);
    }

    public GalleryAdapter(Context context, List<String> list, ImageWorker imageWorker) {
        super(context, 0, list);
        this.mItems = list;
        mImageWorker = imageWorker;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (position < 0) {
            if (convertView == null) {
                convertView = new View(getContext());
            }
            convertView.setLayoutParams(new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return convertView;
        }

        // Now handle the main ImageView thumbnails
        ImageView imageView;
        if (convertView == null) { // if it's not recycled, instantiate and initialize
            imageView = new RecyclingImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        } else { // Otherwise re-use the converted view
            imageView = (ImageView) convertView;
        }

        mImageWorker.loadImage(getItem(position), imageView);

        return imageView;
    }

    @Override
    public String getItem(int position) {
        return mItems.get(position);
    }

    public void all(final Collection<String> collection) {
        clear();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (collection != null) {
                    for (String aCollection : collection) {
                        add(aCollection);
                    }
                }
                notifyDataSetChanged();
            }
        });

    }
}