package com.techyourchance.coroutines.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.techyourchance.coroutines.R

class HomeArrayAdapter(context: Context, private val listener: Listener) : ArrayAdapter<ScreenReachableFromHome>(context, 0) {

    interface Listener {
        fun onScreenClicked(screenReachableFromHome: ScreenReachableFromHome)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val newConvertView = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_screen_reachable_from_home, parent, false)

        val screenReachableFromHome = getItem(position)!!

        // display screen name
        val txtName = newConvertView.findViewById<TextView>(R.id.txt_screen_name)
        txtName.text = screenReachableFromHome.description

        // set click listener on individual item view
        newConvertView.setOnClickListener { listener.onScreenClicked(screenReachableFromHome) }

        return newConvertView
    }

}