package com.srinivas.mudavath.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.srinivas.mudavath.cubeit.R;
import com.srinivas.mudavath.pojo.GalleryItem;

import java.util.ArrayList;

/**
 * Created by Mudavath Srinivas on 27-02-2016.
 */
public class GalleryAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<GalleryItem> galleryImages = new ArrayList<GalleryItem>();
    public static final String LIST = "list";
    public static final String GRID = "grid";
    public static final String STAGGERED_GRID = "staggered_grid";
    private static final int VIEW_TYPE_LIST = 0;
    private static final int VIEW_TYPE_GRID = 1;
    private static final int VIEW_TYPE_STAGGERED_GRID = 2;

    private View.OnClickListener clickListener;
    Context context;

    private ImageLoader imageLoader;
    public ImageLoaderConfiguration config;
    public DisplayImageOptions options;
    private String viewType=GRID;

    public GalleryAdapter(Context context, ArrayList<GalleryItem> galleryImages,View.OnClickListener clickListener){
        this.clickListener=clickListener;
        this.galleryImages=galleryImages;
        this.context=context;

        imageLoader = ImageLoader.getInstance();

        config = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(5)
                .threadPriority(Thread.MIN_PRIORITY + 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2000000))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();

        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_default_pic)
                .showImageForEmptyUri(R.drawable.ic_default_pic)
                .cacheInMemory()
                .cacheOnDisc()
                .build();

        imageLoader.init(config);

    }

    public void setViewType(String viewType){
        if(viewType.equals(LIST)){
            this.viewType=LIST;
        }else if(viewType.equals(GRID)){
            this.viewType=GRID;
        } else {
            this.viewType=STAGGERED_GRID;
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (viewType.equals(LIST)) {
            return VIEW_TYPE_LIST;
        } else if (viewType.equals(GRID)) {
            return VIEW_TYPE_GRID;
        } else if (viewType.equals(STAGGERED_GRID)) {
            return VIEW_TYPE_STAGGERED_GRID;
        } else {
            return VIEW_TYPE_GRID;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolderType = null;
        ViewHolderForGrid viewHolderForGrid = null;
        ViewHolderForList viewHolderForList = null;
        ViewHolderForStaggeredList viewHolderForStaggeredList = null;

        switch (viewType){
            case VIEW_TYPE_GRID:
                viewHolderForGrid = new ViewHolderForGrid(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.gallery_grid_item, viewGroup, false));
                viewHolderType = viewHolderForGrid;
                break;
            case VIEW_TYPE_LIST:
                viewHolderForList = new ViewHolderForList(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.gallery_list_item, viewGroup, false));
                viewHolderType = viewHolderForList;
                break;
            case VIEW_TYPE_STAGGERED_GRID:
                viewHolderForStaggeredList = new ViewHolderForStaggeredList(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.gallery_staggered_grid_item, viewGroup, false));
                viewHolderType = viewHolderForStaggeredList;
                break;
            default:
                break;
        }
        return viewHolderType;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        ViewHolderForGrid viewHolderForGrid;
        ViewHolderForList viewHolderForList;
        ViewHolderForStaggeredList viewHolderForStaggeredList;
        Uri uri=Uri.parse(galleryImages.get(position).getImagePath());
        if(viewType.equals(GRID)){
            viewHolderForGrid=(ViewHolderForGrid)viewHolder;
            viewHolderForGrid.imgThumbnail.setTag(galleryImages.get(position).getId());
            imageLoader.displayImage("file://" + uri.getPath(), viewHolderForGrid.imgThumbnail, options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
        }else if(viewType.equals(LIST)){
            viewHolderForList=(ViewHolderForList)viewHolder;
            viewHolderForList.imgThumbnail.setTag(galleryImages.get(position).getId());
            imageLoader.displayImage("file://" + uri.getPath(), viewHolderForList.imgThumbnail, options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
        }else if(viewType.equals(STAGGERED_GRID)){
            viewHolderForStaggeredList=(ViewHolderForStaggeredList)viewHolder;
            viewHolderForStaggeredList.imgThumbnail.setTag(galleryImages.get(position).getId());
            imageLoader.displayImage("file://" + uri.getPath(), viewHolderForStaggeredList.imgThumbnail, options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return galleryImages.size();
    }

    class ViewHolderForGrid extends RecyclerView.ViewHolder{

        public ImageView imgThumbnail;

        public ViewHolderForGrid(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.gallery_image_item);
            imgThumbnail.setOnClickListener(clickListener);

        }
    }

    class ViewHolderForList extends RecyclerView.ViewHolder{

        public ImageView imgThumbnail;

        public ViewHolderForList(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.gallery_image_item);
            imgThumbnail.setOnClickListener(clickListener);

        }
    }

    class ViewHolderForStaggeredList extends RecyclerView.ViewHolder{

        public ImageView imgThumbnail;

        public ViewHolderForStaggeredList(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.gallery_image_item);
            imgThumbnail.setOnClickListener(clickListener);

        }
    }
}

