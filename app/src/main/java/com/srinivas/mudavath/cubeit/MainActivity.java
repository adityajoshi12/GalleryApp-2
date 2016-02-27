package com.srinivas.mudavath.cubeit;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.srinivas.mudavath.adapter.GalleryAdapter;
import com.srinivas.mudavath.pojo.GalleryItem;
import com.srinivas.mudavath.views.OnSwipeTouchListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    public static final String LIST = "list";
    public static final String GRID = "grid";
    public static final String STAGGERED_GRID = "staggered_grid";
    ArrayList<GalleryItem> galleryImages = new ArrayList<GalleryItem>();

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    GalleryAdapter mAdapter;
    View.OnClickListener viewClickListener = null;
    private String viewType=GRID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView)findViewById(R.id.rv_gallery);
        mRecyclerView.setHasFixedSize(true);

        if(viewType.equals(GRID)){
            mLayoutManager = new GridLayoutManager(this, 3);
        }else if(viewType.equals(STAGGERED_GRID)){
            mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        }else {
            mLayoutManager = new LinearLayoutManager(this);
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                swipeGalleryType(true);
            }
            public void onSwipeLeft() {
                swipeGalleryType(false);
            }
            public void onPinch() {
                swipeGalleryType(false);
            }
            public void onZoom() {
                swipeGalleryType(true);
            }
        });
        mClickListener();

        new GetImages().execute();
        mAdapter = new GalleryAdapter(getApplicationContext(), galleryImages, viewClickListener);
        mRecyclerView.setAdapter(mAdapter);

    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actions, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int position=0;
        switch (item.getItemId()){
            case R.id.list_view_type:
                position=getFirstVisiblePosition();
                viewType=LIST;
                mAdapter.setViewType(viewType);
                item.setIcon(R.drawable.ic_list_view);
                mLayoutManager = new LinearLayoutManager(this);
                break;
            case R.id.grid_view_type:
                position=getFirstVisiblePosition();
                viewType=GRID;
                mAdapter.setViewType(viewType);
                item.setIcon(R.drawable.ic_grid_view);
                mLayoutManager = new GridLayoutManager(this, 3);
                break;
            case R.id.staggered_grid_view_type:
                position=getFirstVisiblePosition();
                viewType=STAGGERED_GRID;
                mAdapter.setViewType(viewType);
                item.setIcon(R.drawable.ic_staggered_grid_view);
                mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                break;
            default:
                break;
        }
        item.setChecked(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(position);
        return super.onOptionsItemSelected(item);

    }

    private int getFirstVisiblePosition() {
        int position=0;
        switch (viewType){
            case LIST:
                position=((LinearLayoutManager)mLayoutManager).findFirstVisibleItemPosition();
                break;
            case GRID:
                position=((GridLayoutManager)mLayoutManager).findFirstVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                int a[]={1,2};
                a=((StaggeredGridLayoutManager)mLayoutManager).findFirstVisibleItemPositions(a);
                position=a[1];
                break;
            default:break;
        }
        return position;

    }

    private void mClickListener(){
        viewClickListener =new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                long position= (long) v.getTag();
                switch (v.getId()){
                    case R.id.gallery_image_item:
                        String[] projection = {MediaStore.Images.Media.DATA};
                        String selection=MediaStore.Images.Media._ID+"="+(int)position;

                        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                projection,
                                selection,
                                null,
                                null);

                        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        String imagePath = cursor.getString(columnIndex);
                        startViewImageActivity(imagePath);
                        cursor.close();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void startViewImageActivity(String imagePath) {
        Intent intent=new Intent(this,ViewImageActivity.class);
        intent.putExtra("imagePath",imagePath);
        startActivity(intent);
    }


    private void swipeGalleryType(boolean leftToRight) {
        int position=0;
        switch (viewType){
            case LIST:
                if(!leftToRight){
                    break;
                }else {
                    position=getFirstVisiblePosition();
                    viewType=STAGGERED_GRID;
                    mAdapter.setViewType(viewType);
                    mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                }
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.scrollToPosition(position);
                break;
            case GRID:
                if(!leftToRight){
                    position=getFirstVisiblePosition();
                    viewType=STAGGERED_GRID;
                    mAdapter.setViewType(viewType);
                    mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                }else {
                    break;
                }
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.scrollToPosition(position);
                break;
            case STAGGERED_GRID:
                if(!leftToRight){
                    position=getFirstVisiblePosition();
                    viewType=LIST;
                    mAdapter.setViewType(viewType);
                    mLayoutManager = new LinearLayoutManager(this);
                }else {
                    position=getFirstVisiblePosition();
                    viewType=GRID;
                    mAdapter.setViewType(viewType);
                    mLayoutManager = new GridLayoutManager(this, 3);
                }
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.scrollToPosition(position);
                break;
            default:
                break;
        }

    }

    private class GetImages extends AsyncTask<Object,GalleryItem,ArrayList<GalleryItem>>{

        @Override
        protected ArrayList<GalleryItem> doInBackground(Object... params) {
            ArrayList<GalleryItem> galleryData =new ArrayList<GalleryItem>();
            try {
                final String[] columns = {MediaStore.Images.Media._ID,MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DISPLAY_NAME,MediaStore.Images.Media.DATE_TAKEN };
                final String orderBy = MediaStore.Images.Media._ID;

                Cursor imageCursor = getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
                        null, null, orderBy);
                if (imageCursor != null && imageCursor.getCount() > 0) {
                    while (imageCursor.moveToNext()) {
                        long imageId=imageCursor.getLong(imageCursor.getColumnIndex(MediaStore.Images.Media._ID));
                        String path=imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        String displayName=imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                        String dateTaken=imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));

                        if(path.contains(".jpg")||path.contains(".png")||path.contains(".jpeg")){
                            galleryData.add(new GalleryItem(imageId,path,displayName,dateTaken));
                        }
                    }
                    imageCursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return galleryData;
        }

        @Override
        protected void onPostExecute(ArrayList<GalleryItem> galleryData) {
            Log.e("Data", "Data post into an Array : " + galleryData);
            galleryImages.clear();

            Collections.sort(galleryImages, new Comparator<GalleryItem>() {
                @Override
                public int compare(GalleryItem g1, GalleryItem g2) {
                    return -new Double(Double.parseDouble(g1.getDateTaken())).compareTo(new Double(Double.parseDouble(g2.getDateTaken()))); // Desc
                }

            });
            galleryImages.addAll(galleryData);
            mAdapter.notifyDataSetChanged();
        }
    }
}
