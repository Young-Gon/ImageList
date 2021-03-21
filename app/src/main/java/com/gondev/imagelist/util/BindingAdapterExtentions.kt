package com.gondev.imagelist.util

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

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

@BindingAdapter("hasFixedSize")
fun RecyclerView.hasFixedSize(fix: Boolean) {
    setHasFixedSize(fix)
}