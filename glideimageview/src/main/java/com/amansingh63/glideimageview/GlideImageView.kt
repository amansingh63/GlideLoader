package com.amansingh63.glideimageview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.layout_image_view_with_progress_bar.view.*

/**
 * Created by Amanjot Singh on 01/09/18.
 */

class GlideImageView : FrameLayout {

    var enableProgressBar: Boolean = false
    var circleImageView: Boolean = false
    var errorImageResId: Int = 0
    var placeHolderImageResId: Int = 0
    var progressBarSize: Int = 0

    constructor(context: Context) : super(context) {}


    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }


    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    private fun init(attrs: AttributeSet?) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.GlideImageView)

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_image_view_with_progress_bar, this)

        enableProgressBar = typedArray.getBoolean(R.styleable.GlideImageView_show_progress_bar, true)
        circleImageView = typedArray.getBoolean(R.styleable.GlideImageView_circular_image_view, false)
        errorImageResId = typedArray.getResourceId(R.styleable.GlideImageView_error_img_src, 0)
        placeHolderImageResId = typedArray.getResourceId(R.styleable.GlideImageView_placeholder_img_src, 0)
        progressBarSize = typedArray.getDimension(R.styleable.GlideImageView_progress_bar_size, 0f).toInt()

        typedArray.recycle()
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (placeHolderImageResId != 0) {
            imageView.setImageResource(placeHolderImageResId)
        }
        if (enableProgressBar) {
            showProgressBar()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val layoutParams = progressBar.layoutParams
        layoutParams.apply {
            if (progressBarSize != 0) {
                this.width = progressBarSize
                this.height = progressBarSize
            }
        }
        progressBar.layoutParams = layoutParams
    }


    fun loadImage(imageUrl: String) {
        if (enableProgressBar) {
            showProgressBar()
        }
        var glideRequestBuilder = Glide.with(context).load(imageUrl)
        if (placeHolderImageResId != 0) {
            glideRequestBuilder = glideRequestBuilder.apply(RequestOptions().placeholder(placeHolderImageResId))
        }
        if (errorImageResId != 0) {
            glideRequestBuilder = glideRequestBuilder.apply(RequestOptions().error(errorImageResId))
        }
        if (circleImageView) {
            glideRequestBuilder = glideRequestBuilder.apply(RequestOptions().circleCrop())
        }
        glideRequestBuilder.listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                hideProgressBar()
                return false
            }

            override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                hideProgressBar()
                return false
            }
        }).into(imageView)
    }

    private fun showProgressBar() {
        progressBar.show()
    }

    private fun hideProgressBar() {
        progressBar.hide()
    }

    fun isProgressBarShown(): Boolean {
        return progressBar.visibility == View.VISIBLE
    }


}
