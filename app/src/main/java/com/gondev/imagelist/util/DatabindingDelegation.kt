@file:JvmName("DatabindingDelegation")
package com.gondev.imagelist.util

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlin.properties.ReadOnlyProperty

/**
 * Use setContentView before using the binding variable.
 * ``` kotlin
 * class DataBindingActivity : FragmentActivity() {
 *     // Declare the `binding` variable using `by dataBinding()`.
 *     private val binding: DataBindingActivityBinding by dataBinding()
 *
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *         setContentView(R.layout.data_binding_activity)
 *         // You can use binding
 *     }
 * }
 * ```
 *
 * Use Activity's secondary constructor passing layout res id.
 * ``` kotlin
 * class DataBindingActivity : AppCompatActivity(R.layout.data_binding_activity) {
 *     // Declare the `binding` variable using `by dataBinding()`.
 *     private val binding: DataBindingActivityBinding by dataBinding()
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *         // You can use binding
 *     }
 * }
 * ```
 */
fun <V : ViewDataBinding> FragmentActivity.dataBinding() = ReadOnlyProperty<FragmentActivity, V>{ thisRef, property ->
    bind<V>(checkNotNull(thisRef.findViewById<ViewGroup>(android.R.id.content).getChildAt(0)) {
        "Call setContentView or Use Activity's secondary constructor passing layout res id."
    }).also {
        it.lifecycleOwner = thisRef
    }
}

/**
 * Use inflater.inflate in onCreateView.
 * ``` kotlin
 * class DataBindingFragment : Fragment() {
 *
 *     // Declare the `binding` variable using `by dataBinding()`.
 *     private val binding: DataBindingFragmentBinding by dataBinding()
 *
 *     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
 *         return inflater.inflate(R.layout.data_binding_fragment, container, false)
 *     }
 *
 *     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
 *         super.onViewCreated(view, savedInstanceState)
 *         // You can use binding
 *     }
 * }
 * ```
 * Use Fragment's secondary constructor passing layout res id.
 * ``` kotlin
 * class DataBindingFragment : Fragment(R.layout.data_binding_fragment) {
 *
 *     // Declare the `binding` variable using `by dataBinding()`.
 *     private val binding: DataBindingFragmentBinding by dataBinding()
 *
 *     // DO NOT override onCreateView
 *     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
 *         super.onViewCreated(view, savedInstanceState)
 *         // You can use binding
 *     }
 * }
 * ```
 */
fun <T : ViewDataBinding> Fragment.dataBinding() = ReadOnlyProperty<Fragment, T>{ thisRef, property ->
    bind<T>(requireView()).also {
        it.lifecycleOwner = thisRef.viewLifecycleOwner
    }
}

private fun <T : ViewDataBinding> bind(view: View): T = DataBindingUtil.bind(view)!!