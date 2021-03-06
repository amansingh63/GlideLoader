package com.amansingh63.glideloader;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

/**
 * Created by Amanjot Singh on 11/1/17.
 */

public class GlideLoader extends FrameLayout {

    private ProgressBar progressBar;
    private ImageView imageView;
    private boolean showProgressBar;
    private boolean customProgressBarSize = false;
    private boolean circleImageView;
    private int errorImageResId, placeHolderImageResId;
    private int progressBarSize;
    private final int progressBarSizeDivisor = 3;


    public GlideLoader(@NonNull Context context) {
        super(context);
    }


    public GlideLoader(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }


    public GlideLoader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GlideLoader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    private void init(AttributeSet attrs) {

        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.GlideLoader);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_image_view_with_progress_bar, this);

        showProgressBar = typedArray.getBoolean(R.styleable.GlideLoader_show_progress_bar, true);
        circleImageView = typedArray.getBoolean(R.styleable.GlideLoader_circular_image_view, false);
        errorImageResId = typedArray.getResourceId(R.styleable.GlideLoader_error_img_src, 0);
        placeHolderImageResId = typedArray.getResourceId(R.styleable.GlideLoader_placeholder_img_src, 0);
        progressBarSize = (int) typedArray.getDimension(R.styleable.GlideLoader_progress_bar_size, 30.0f);
        if (typedArray.hasValue(R.styleable.GlideLoader_progress_bar_size)) {
            customProgressBarSize = true;
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        imageView = (ImageView) findViewById(R.id.image_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (placeHolderImageResId != 0) {
            imageView.setImageResource(placeHolderImageResId);
        }
        if (!showProgressBar) {
            hideProgressBar();
        }
        ViewGroup.LayoutParams layoutParams = progressBar.getLayoutParams();
        ViewGroup.LayoutParams frameLayoutParams = this.getLayoutParams();
        if (!customProgressBarSize) {
            if (frameLayoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                layoutParams.width = progressBarSize;
                layoutParams.height = progressBarSize;
            } else {
                layoutParams.width = this.getLayoutParams().height / progressBarSizeDivisor;
                layoutParams.height = this.getLayoutParams().height / progressBarSizeDivisor;
            }
        } else {
            layoutParams.width = progressBarSize;
            layoutParams.height = progressBarSize;
        }
        progressBar.setLayoutParams(layoutParams);
    }

    public void loadImage(String imageUrl) {
        if (showProgressBar) {
            showProgressBar();
        }
        RequestBuilder<Drawable> glideRequestBuilder = Glide.with(getContext()).load(imageUrl);
        if (placeHolderImageResId != 0) {
            glideRequestBuilder = glideRequestBuilder.apply(new RequestOptions().placeholder(placeHolderImageResId));
        }
        if (errorImageResId != 0) {
            glideRequestBuilder = glideRequestBuilder.apply(new RequestOptions().error(errorImageResId));
        }
        if (circleImageView) {
            glideRequestBuilder = glideRequestBuilder.apply(new RequestOptions().circleCrop());
        }
        glideRequestBuilder.listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                hideProgressBar();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                hideProgressBar();
                return false;
            }
        }).into(imageView);
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

    public void setShowProgressBar(boolean showProgressBar) {
        this.showProgressBar = showProgressBar;
    }

    public void setProgressBarSize(int progressBarSize) {
        this.progressBarSize = progressBarSize;

    }

    public boolean isProgressBarShown() {
        return progressBar != null && progressBar.isShown();
    }

    public void setErrorImageResId(int resId) {
        this.errorImageResId = resId;
    }

    public void setPlaceHolderImageResId(int resId) {
        this.placeHolderImageResId = resId;
    }


}
