package de.datlag.skeleton.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

typealias RecyclerClickListener = (view: View, position: Int) -> Unit
typealias RecyclerLongClickListener = (view: View, position: Int) -> Boolean

abstract class ClickableRecyclerAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    protected open var clickListener: RecyclerClickListener? = null
    protected open var longClickListener: RecyclerLongClickListener? = null

    open fun setOnClickListener(listener: RecyclerClickListener?) {
        clickListener = listener
    }

    open fun setOnLongClickListener(listener: RecyclerLongClickListener?) {
        longClickListener = listener
    }
}
