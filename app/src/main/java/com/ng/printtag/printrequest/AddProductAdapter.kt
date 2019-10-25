package com.ng.printtag.printrequest

import android.annotation.SuppressLint
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
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
    private val isDraft: Boolean,
    private val newPrintRequest: Boolean,
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

            binding.edtQty.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
                if ((actionId == EditorInfo.IME_ACTION_DONE) || (event.action == KeyEvent.ACTION_DOWN) ||
                    ((event.keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN))
                ) {

                    binding.edtQty.clearFocus()

                    true

                } else false
            })
            /* binding.edtQty.setOnKeyListener(object : View.OnKeyListener {

                 override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                     // If the event is a key-down event on the "enter" button
                     if (event.getAction() == EditorInfo.IME_ACTION_DONE) {
                         // Perform action on Enter key press
                         onItemClickListener.onItemClick(binding.edtQty.text.toString(), adapterPosition)
                         binding.edtQty.clearFocus()


                         return true
                     }
                     return false
                 }
             })
 */

            //  binding.btnSelect.setOnClickListener(this)
            //  binding.tvrefresh.setOnClickListener(this)
        }

        /**
         * Bind the txt with xml
         */
        fun bind(item: AddProductModel) {
            if (isDraft && !newPrintRequest) {
                binding.edtQty.isClickable = true
                binding.edtQty.isFocusable = true
                binding.imgDelete.visibility = View.VISIBLE

            } else if (!isDraft && !newPrintRequest) {
                binding.edtQty.isClickable = false
                binding.edtQty.isFocusable = false
                binding.imgDelete.visibility = View.GONE

            }
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