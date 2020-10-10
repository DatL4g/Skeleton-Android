package de.datlag.skeleton.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import de.datlag.skeleton.R
import de.datlag.skeleton.data.RecyclerItem
import de.datlag.skeleton.databinding.ActivityRecyclerItemBinding
import kotlinx.android.extensions.LayoutContainer

class RecyclerAdapter : ClickableRecyclerAdapter<RecyclerAdapter.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<RecyclerItem>() {
        override fun areItemsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener, LayoutContainer {

        override val containerView: View?
            get() = itemView

        val binding = ActivityRecyclerItemBinding.bind(containerView ?: itemView)

        override fun onClick(v: View?) {
            clickListener?.invoke(v ?: containerView ?: itemView, adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            return longClickListener?.invoke(v ?: containerView ?: itemView, adapterPosition) ?: true
        }

        init {
            binding.root.setOnClickListener(this)
            binding.root.setOnLongClickListener(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder.binding) {
        val item = differ.currentList[position]

        recyclerTextView.text = item.text
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<RecyclerItem>) {
        differ.submitList(list)
    }
}
