package com.fitpeo.mvvmhilt.base.adapter

/**
 * On item click listener attached to list
 *
 * @constructor Create On item click listener
 */
interface OnItemClickListener {
    /**
     * On Click event for adapter class
     *
     * @param item get item type while clicked position
     */
    fun onClick(item: Any)
}