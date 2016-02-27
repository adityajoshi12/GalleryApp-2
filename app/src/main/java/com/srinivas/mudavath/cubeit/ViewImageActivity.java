package com.srinivas.mudavath.cubeit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.srinivas.mudavath.views.TouchImageView;

/**
 * Created by Mudavath Srinivas on 18-12-2015.
 */
public class ViewImageActivity extends AppCompatActivity {

    private TouchImageView iv_blog_image;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        setContentView(R.layout.activity_view_image);
        iv_blog_image= (TouchImageView) findViewById(R.id.iv_blog_image);
        String imagePath=null;
        if(getIntent().hasExtra("imagePath")){
            imagePath=getIntent().getStringExtra("imagePath");
        }else {
            onBackPressed();
        }

        iv_blog_image.setImageURI(Uri.parse(imagePath));
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
