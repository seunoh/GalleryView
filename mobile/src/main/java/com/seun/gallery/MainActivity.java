package com.seun.gallery;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.GridView;

import com.seun.gallery.util.ImageWorker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private GalleryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageWorker imageWorker = new ImageWorker(this, 100);
        imageWorker.setLoadingImage(R.drawable.ic_launcher);
        imageWorker.addImageCache(getSupportFragmentManager());
        imageWorker.setImageFadeIn(true);

        mAdapter = new GalleryAdapter(getApplication(), imageWorker);
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(mAdapter);

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
        String orderBy = MediaStore.Images.Media._ID + " DESC";

        return new CursorLoader(getApplicationContext(), uri, projection, null, null, orderBy);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.all(doWorker(data));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        loader.reset();
    }

    public static List<String> doWorker(Cursor data) {
        final int columnData = data.getColumnIndex(MediaStore.Images.Media.DATA);
        final int count = data.getCount();

        List<String> list = new ArrayList<String>();

        for (int position = 0; position < count; position++) {
            data.moveToPosition(position);
            final String path = data.getString(columnData);
            if (path != null && !"".equalsIgnoreCase(path)) try {
                FileInputStream fis = new FileInputStream(path);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(fis, new Rect(), options);
                list.add(path);
            } catch (FileNotFoundException ignored) {
                Log.i("LoaderCallbacks", ignored.toString());
            }
        }

        data.close();
        return list;
    }

}
