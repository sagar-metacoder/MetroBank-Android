package com.ng.printtag.allrequest

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ng.printtag.databinding.RowAllRequeustsBinding
import com.ng.printtag.interfaces.OnItemClickListener
import com.ng.printtag.models.allrequests.AllRequestModel


/**
 * View Items of search customer and click for view profile and re-registration
 */
class AllRequestsAdapter(
    var results: MutableList<AllRequestModel.Data.Records>,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<AllRequestsAdapter.CustomViewHolder>() {

    /**
     *  Bind the Row using CustomViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowAllRequeustsBinding.inflate(inflater, parent, false)
        return CustomViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(results[position])
    }

    override fun getItemCount(): Int {
        return results.size
    }

    /**
     * Custom holder and click listener
     */
    inner class CustomViewHolder(val binding: RowAllRequeustsBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }
        override fun onClick(view: View?) {
            onItemClickListener.onItemClick(adapterPosition, adapterPosition)
        }
        /**
         * Bind the txt with xml
         */
        fun bind(item: AllRequestModel.Data.Records) {
            binding.model = item
            binding.executePendingBindings()
        }

    }
}