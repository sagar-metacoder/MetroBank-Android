package com.ng.printtag.printrequest

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ng.printtag.R
import com.ng.printtag.databinding.RowAddProductBinding
import com.ng.printtag.interfaces.OnItemClickListener
import com.ng.printtag.models.newrequests.AddProductModel


/**
 * View Items of search customer and click for view profile and re-registration
 */
class AddProductAdapter(
    private val results: MutableList<AddProductModel>,
    private var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<AddProductAdapter.CustomViewHolder>() {

    /**
     *  Bind the Row using CustomViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowAddProductBinding.inflate(inflater, parent, false)
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
    inner class CustomViewHolder(val binding: RowAddProductBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(view: View?) {

            if (view!!.id == R.id.linear_row) {
                onItemClickListener.onItemClick("view", adapterPosition)

            } else if (view.id == R.id.img_delete) {
                onItemClickListener.onItemClick("delete", adapterPosition)


            }
        }


        init {
            binding.imgDelete.setOnClickListener(this)
            binding.linearRow.setOnClickListener(this)
            binding.edtQty.onFocusChangeListener = object : OnFocusChangeListener {
                override fun onFocusChange(v: View, hasFocus: Boolean) {
                    if (hasFocus) {
                        //got focus
                    } else {
                        onItemClickListener.onItemClick(binding.edtQty.text.toString(), adapterPosition)
                    }
                }
            }


            //  binding.btnSelect.setOnClickListener(this)
            //  binding.tvrefresh.setOnClickListener(this)
        }

        /**
         * Bind the txt with xml
         */
        fun bind(item: AddProductModel) {
            binding.model = item
            /* if (binding.model!!.dataType == Constant.TIER3 || binding.model!!.dataType == Constant.POWER_CHECK) {
                 binding.tvProfileFlag.text = Utils.getLabel(context.getString(R.string.a_lbl_legacy_record))
             } else {
                 binding.tvProfileFlag.text = ""
             }*/
            binding.executePendingBindings()
        }

    }


}