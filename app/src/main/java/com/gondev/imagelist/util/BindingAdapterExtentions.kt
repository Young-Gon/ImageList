package com.gondev.imagelist.util

import android.graphics.drawable.Drawable
import android.text.util.Linkify
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.ListenerUtil
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.request.transition.TransitionFactory
import com.bumptech.glide.signature.ObjectKey
import com.gondev.imagelist.R
import java.util.regex.Pattern

@BindingAdapter("visibleGone")
fun View.showHide(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}

@BindingAdapter("items")
fun <T> RecyclerView.setItems(items: List<T>?) {
    // layoutManager를 실수로 넣지 않을 경우
    // list에 item이 있어도 RecyclerView에는 아이템이 나타나지 않는다
    // 이런 실수를 방지 하려면 이런 경우 아에 앱을 빨리 죽여 버리는게 낫다
    if (layoutManager == null)
        throw NullPointerException("layoutManager가 없습니다")

    (this.adapter as? ListAdapter<T, *>)?.run {
        submitList(items)
    }
}

@BindingAdapter("items")
fun <T> ViewPager2.setItems(items: PagedList<T>?) {
    (this.adapter as? PagedListAdapter<T, *>)?.run {
        submitList(items)
    }
}

@BindingAdapter("hasFixedSize")
fun RecyclerView.hasFixedSize(fix: Boolean) {
    setHasFixedSize(fix)
}

@BindingAdapter("onPageScrollStateChanged")
fun ViewPager2.setOnPageScrolledListener(onPageScrollStateChangedListener: OnPageScrolledListener) {
    val onPageChangeListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            onPageScrollStateChangedListener.onPageSelected(position)
        }
    }
    ListenerUtil.trackListener<ViewPager2.OnPageChangeCallback>(
        this,
        onPageChangeListener, R.id.currentItemSelectedListener
    )?.let {
        unregisterOnPageChangeCallback(it)
    }

    registerOnPageChangeCallback(onPageChangeListener)
}

interface OnPageScrolledListener {
    fun onPageSelected(position: Int)
}

@BindingAdapter("imgUrl")
fun ImageView.loadThumbnail(thumbnail: String?) {
    if (thumbnail == null) {
        return
    }

    Glide.with(context).load(thumbnail)
        .transition(DrawableTransitionOptions.with(DrawableAlwaysCrossFadeFactory()))
        .apply(getGlideRequestOption(thumbnail))
        .error(R.drawable.ic_empty_image)
        .into(this)
}

/**
 * Best strategy to load images using Glide
 * https://android.jlelse.eu/best-strategy-to-load-images-using-glide-image-loading-library-for-android-e2b6ba9f75b2
 */
fun getGlideRequestOption(imageName: String) =
    RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .signature(ObjectKey(imageName))
        .override(1024, 2048)

/**
 * 이미지 교체시 faid-in이 말을 듣지 않을때
 * 해당 클래스로 처리
 * https://stackoverflow.com/questions/53664645/how-to-apply-animation-on-cached-image-in-glide
 */
class DrawableAlwaysCrossFadeFactory : TransitionFactory<Drawable> {
    private val resourceTransition: DrawableCrossFadeTransition = DrawableCrossFadeTransition(
        300,
        true
    ) //customize to your own needs or apply a builder pattern

    override fun build(dataSource: DataSource?, isFirstResource: Boolean): Transition<Drawable> {
        return resourceTransition
    }
}

fun interface ItemClickListener<T> {
    fun onItemClicked(view: View, item: T)
}

val transform: Linkify.TransformFilter by lazy {
    Linkify.TransformFilter { match, url -> "" }
}

@BindingAdapter("pattern", "addLink", requireAll = true)
fun TextView.addLinkBinding(pattern: String?, url: String?) {
    if (pattern == null || url == null) {
        return
    }
    Linkify.addLinks(this, Pattern.compile(pattern), url, null, transform)
}