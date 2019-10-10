package com.ng.printtag.printrequest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ng.printtag.databinding.RowTemplateListBinding
import com.ng.printtag.interfaces.OnItemClickListener
import com.ng.printtag.models.newrequests.DepartmentModel

class TemplateListAdapter(
    private var context: Context,
    private val results: MutableList<DepartmentModel.Template>,
    private var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<TemplateListAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowTemplateListBinding.inflate(inflater, parent, false)
        return CustomViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(results[position])
    }


    inner class CustomViewHolder(val binding: RowTemplateListBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            onItemClickListener.onItemClick(adapterPosition, adapterPosition)
        }

        init {

        }

        /**
         * Bind the txt with xml
         */
        fun bind(item: DepartmentModel.Template) {
            binding.model = item
            binding.executePendingBindings()
        }

    }
}