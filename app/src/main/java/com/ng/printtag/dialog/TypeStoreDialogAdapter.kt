package com.ng.printtag.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ng.printtag.R
import com.ng.printtag.databinding.RowDialogTypeStoreBinding
import com.ng.printtag.interfaces.OnItemClickListener

class TypeStoreDialogAdapter : RecyclerView.Adapter<TypeStoreDialogAdapter.ViewHolder>() {

    private var mInflater: LayoutInflater? = null
    private lateinit var context: Context
    private lateinit var onItemClickListener: OnItemClickListener

    private lateinit var storeList: ArrayList<String>
    var selPosValue: Int = -1



    fun setData(
        context: Context,
        storeList: ArrayList<String>,
        selPosValue: Int,
        onItemClickListener: OnItemClickListener

    ) {
        this.context = context
        this.storeList = storeList
        mInflater = LayoutInflater.from(context)
        this.selPosValue = selPosValue
        this.onItemClickListener = onItemClickListener

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
        holder.dataBinding.rbType.isChecked = position == selPosValue
        holder.bind(storeList[position])


    }

    inner class ViewHolder(val dataBinding: RowDialogTypeStoreBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {


        private val clickHandler: (View) -> Unit = {

            selPosValue = adapterPosition


            if (selPosValue != -1) {
                onItemClickListener.onItemClick(storeList[adapterPosition], adapterPosition)
            }
            notifyDataSetChanged()

        }


        init {
            dataBinding.apply {
                root.setOnClickListener(clickHandler)
                dataBinding.rbType.setOnClickListener(clickHandler)
            }


/*
            dataBinding.rbType.setOnClickListener {


                if (selected != null) {
                    selected!!.isChecked = false
                }
                dataBinding.rbType.isChecked = true

                selected = dataBinding.rbType
                if (dataBinding.rbType.isChecked) {
                    onItemClickListener.onItemClick(storeList[position], adapterPosition)
                    selPosValue = position
                }
            }
*/
        }

        fun bind(obj: String?) {
            dataBinding.model = obj
            dataBinding.executePendingBindings()
        }

    }


}