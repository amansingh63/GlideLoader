package com.amansingh63.glideloader;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

/**
 * Created by Aman Singh on 31-10-2017.
 */

public class GlideLoader extends AppCompatImageView {

    private ProgressBar progressBar;
    private boolean showProgressBar;
    private int errorImageResId, placeHolderImageResId;
    private int progressBarSize;

    //Constructor matching super class
    public GlideLoader(Context context) {
        super(context);
    }

    //Constructor matching super Class
    public GlideLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    //Constructor matching super Class
    public GlideLoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    // Method to check progress bar is shown or not
    public boolean isProgressBarShown() {
        return progressBar != null && progressBar.isShown();
    }

    public GlideLoader loadImage(Object model) {
        progressBar.setVisibility(VISIBLE);
        Glide.with(getContext()).load(model).apply(new RequestOptions().placeholder(placeHolderImageResId))
                .apply(new RequestOptions().error(errorImageResId))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(INVISIBLE);
                        return false;
                    }
                }).into(this);
        return this;
    }

    public void setErrorImageResId(int resId) {
        this.errorImageResId = resId;

    }

    public void setPlaceHolderImageResId(int resId) {
        this.placeHolderImageResId = resId;
    }

    public void setShowProgressBar(boolean showProgressBar) {
        this.showProgressBar = showProgressBar;
    }

    public void showProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(VISIBLE);
        }
    }

    public void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(INVISIBLE);
        }
    }

    private void init(AttributeSet attrs) {
        progressBar = new ProgressBar(getContext(), attrs, android.R.attr.progressBarStyleSmall);
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.GlideLoader);

        showProgressBar = typedArray.getBoolean(R.styleable.GlideLoader_show_progress_bar, true);
        errorImageResId = typedArray.getResourceId(R.styleable.GlideLoader_error_img_src, 0);
        placeHolderImageResId = typedArray.getResourceId(R.styleable.GlideLoader_placeholder_img_src, 0);
        progressBarSize = typedArray.getInteger(R.styleable.GlideLoader_progress_bar_size, 2);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (showProgressBar && progressBar.getParent() == null) {
            ViewParent viewGroupParent = getParent();

            if (viewGroupParent != null) {

                ViewGroup parent = (ViewGroup) viewGroupParent;
                FrameLayout frameLayout = new FrameLayout(getContext());


                int position = parent.indexOfChild(this);

                parent.removeViewAt(position);
                frameLayout.addView(this);

                progressBar.setLayoutParams(new FrameLayout.LayoutParams(getLayoutParams().width / progressBarSize,
                        getLayoutParams().height / progressBarSize, Gravity.CENTER));
                frameLayout.addView(progressBar);
                frameLayout.setLayoutParams(getLayoutParams());
                parent.addView(frameLayout, position);

            }
        }
    }
}
