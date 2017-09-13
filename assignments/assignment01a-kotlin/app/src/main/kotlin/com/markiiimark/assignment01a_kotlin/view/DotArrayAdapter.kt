package com.markiiimark.assignment01a_kotlin.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.widget.ImageView
import com.markiiimark.assignment01a_kotlin.R


/**
 * Created by MarkiiimarK on 9/10/17.
 */
class DotArrayAdapter: ArrayAdapter<DotArrayAdapter.DotColor> {

    enum class DotColor {
        GREEN,  // available
        YELLOW, // idle
        RED,    // waiting / in-use
        GRAY,   // not yet started / done
    }

    private var mResource:Int? = null

    constructor(context: Context, textViewRID:Int, dots:List<DotColor>): super(context, textViewRID, dots) {
        mResource = textViewRID
    }

    override fun getView(position: Int,
                         convertView: View?,
                         parent: ViewGroup?): View {
        val item:DotColor = getItem(position)

        val view:View
        val holder: ViewHolder
        if (convertView == null) {
            // We don't have a view to convert, make one.
            view = LayoutInflater.from(context).inflate(mResource!!,
                    parent,
                    false)
            holder = ViewHolder(view)
            holder.dot = convertView?.findViewById(R.id.dot) as ImageView

            convertView.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        // Set the appropriate drawable.
        val color: Int = when (item) {
            DotColor.GREEN -> R.drawable.green_dot
            DotColor.GRAY -> R.drawable.gray_dot
            DotColor.YELLOW -> R.drawable.yellow_dot
            DotColor.RED -> R.drawable.red_dot
            else -> throw IllegalStateException("Invalid item color")
        }

        holder.dot.setImageDrawable(
                ContextCompat.getDrawable(context, color))

        return view
    }

    private inner class ViewHolder(v:View?) {
        lateinit var dot:ImageView
    }
}