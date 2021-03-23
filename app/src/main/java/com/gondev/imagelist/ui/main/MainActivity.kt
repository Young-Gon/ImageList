package com.gondev.imagelist.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import com.gondev.imagelist.BR
import com.gondev.imagelist.R
import com.gondev.imagelist.databinding.MainActivityBinding
import com.gondev.imagelist.domain.model.network.response.ImageData
import com.gondev.imagelist.ui.gallery.startGalleryActivity
import com.gondev.imagelist.util.DataBindingListAdapter
import com.gondev.imagelist.util.ItemClickListener
import com.gondev.imagelist.util.dataBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: MainActivityBinding by dataBinding()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding.vm = viewModel

        binding.recyclerView.adapter = DataBindingListAdapter(
            layoutResId = R.layout.item_image,
            bindingVariableId = BR.item,
            diffCallback = object : DiffUtil.ItemCallback<ImageData>() {
                override fun areItemsTheSame(oldItem: ImageData, newItem: ImageData) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: ImageData, newItem: ImageData) =
                    oldItem == newItem
            },
            lifecycleOwner = this,
            BR.vm to viewModel,
            BR.itemClickListener to ItemClickListener<ImageData> { view, item ->
                startGalleryActivity(item.id, view.findViewById(R.id.imageView))
            }
        )
    }
}