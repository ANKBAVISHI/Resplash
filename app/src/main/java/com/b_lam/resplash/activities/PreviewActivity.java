package com.b_lam.resplash.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.b_lam.resplash.Resplash;
import com.b_lam.resplash.data.data.Photo;
import com.b_lam.resplash.util.LocaleUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.b_lam.resplash.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

public class PreviewActivity extends AppCompatActivity {

    @BindView(R.id.preview_image) PhotoView mPhotoView;
    @BindView(R.id.preview_progress) ProgressBar mProgressBar;
    PhotoViewAttacher mAttacher;
    Photo mPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocaleUtils.loadLocale(this);

        setTheme(R.style.PreviewTheme);
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);

        mPhoto = Resplash.getInstance().getPhoto();

        mAttacher = new PhotoViewAttacher(mPhotoView);

        if(mPhoto.urls.regular != null){
            Glide.with(this)
                    .load(mPhoto.urls.regular)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            mProgressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(mPhotoView);
        }else{
            finish();
            Toast.makeText(PreviewActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
    }
}
