package com.fitpeo.mvvmhilt.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fitpeo.mvvmhilt.base.adapter.OnItemClickListener

/**
 * Base Adapter for all recyclerview adapter
 *
 * @param T  the arraylist object model
 * @param R  the recyclerview holder
 * @constructor Create Base adapter
 */
abstract class BaseAdapter<T, R : RecyclerView.ViewHolder> : RecyclerView.Adapter<R>() {

    var listener: OnItemClickListener? = null
    val items = ArrayList<T>()

    /**
     * Set item click listener for the adapter item view
     *
     * @param listener the item click listener
     */
    fun setItemListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    /**
     * Set items list to the adapter
     *
     * @param newItems  set new item list to the adapter
     */
    fun setItems(newItems: List<T>) {
        val diffCallback = DiffUtils(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    /**
     * DiffUtilCallback calculate the difference between two lists and output
     * a list of update operations that converts the first list into the second one
     * DiffUtil is based on Eugene Myers’ algorithm.
     *
     * @property oldList     the old list item in this adapter
     * @property newList   the new list item from setItem function
     */
    inner class DiffUtils(private val oldList: List<T>, private val newList: List<T>) : DiffUtil.Callback() {

        /**
         * @return the size of the old list.
         */
        override fun getOldListSize(): Int = oldList.size

        /**
         * @return the size of the new list.
         */
        override fun getNewListSize(): Int = newList.size

        /**
         * It decides whether two object represent the same Item in the old and new list.
         *
         * @param oldItemPosition    the index position of the old item position
         * @param newItemPosition   the index position of the old item positionthe index position of the old item position
         * @return
         */
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] === newList[newItemPosition]
        }

        /**
         * Its comparing the two object have the same item or different
         *
         * @param oldItemPosition  the index position of the old item position
         * @param newItemPosition the index position of the old item position
         * @return
         */
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] === newList[newItemPosition]
        }


    }
}