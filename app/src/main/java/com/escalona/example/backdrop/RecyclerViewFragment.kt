package com.escalona.example.backdrop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewFragment : Fragment(R.layout.recycler_view_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView?.adapter = MyAdapter()
        ViewCompat.setNestedScrollingEnabled(recyclerView, true)
    }
}

private class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    val items = (1..100).map { it.toString() }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: String) {
            view.findViewById<TextView>(R.id.title).text = item
        }
    }
}