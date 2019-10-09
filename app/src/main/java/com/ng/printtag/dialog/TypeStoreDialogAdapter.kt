package com.ng.printtag.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ng.printtag.R
import com.ng.printtag.databinding.RowDialogTypeStoreBinding

class TypeStoreDialogAdapter : RecyclerView.Adapter<TypeStoreDialogAdapter.ViewHolder>() {

    private var mInflater: LayoutInflater? = null
    private lateinit var context: Context
    private lateinit var storeList: ArrayList<String>
    var selPosValue: Int = 0
    var selected: AppCompatRadioButton? = null


    fun setData(
        context: Context,
        storeList: ArrayList<String>
    ) {
        this.context = context
        this.storeList = storeList
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutDataBinding = DataBindingUtil.inflate(
            mInflater!!,
            viewType, parent, false
        ) as RowDialogTypeStoreBinding
        return ViewHolder(layoutDataBinding)
    }

    override fun getItemCount(): Int {
        return storeList.size
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.row_dialog_type_store
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(storeList[position])
    }

    inner class ViewHolder(private val dataBinding: RowDialogTypeStoreBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        init {
            dataBinding.rbType.setOnClickListener {
                if (selected != null) {
                    selected!!.isChecked = false
                }
                dataBinding.rbType.isChecked = true
                selected = dataBinding.rbType
                if (dataBinding.rbType.isChecked) {
                    selPosValue = position
                }
            }
        }

        fun bind(obj: String?) {
            dataBinding.model = obj
            dataBinding.executePendingBindings()
        }

    }
}