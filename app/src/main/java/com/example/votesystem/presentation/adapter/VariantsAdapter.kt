package com.example.votesystem.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.votesystem.R
import com.example.votesystem.domain.entities.Variant

class VariantsAdapter(val data: List<Variant>) : RecyclerView.Adapter<VariantsAdapter.VariantsViewHolder>() {



    class VariantsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val radioButton:RadioButton = view.findViewById(R.id.radioButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariantsViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.varaint_item, parent, false)

        return VariantsViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: VariantsViewHolder, position: Int) {
        holder.radioButton.text = data[position].text
    }

    override fun getItemCount(): Int {
        return data.size
    }
}