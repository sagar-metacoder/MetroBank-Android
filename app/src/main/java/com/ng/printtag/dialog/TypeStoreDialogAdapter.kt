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
    private var fromWhere: Int = 0

    private lateinit var storeList: ArrayList<String>
    var selPosValue: Int = 0
    var selected: AppCompatRadioButton? = null
    var position1: Int = 0


    fun setData(
        context: Context,
        storeList: ArrayList<String>,
        fromWhere: Int
    ) {
        this.context = context
        this.storeList = storeList
        mInflater = LayoutInflater.from(context)
        this.fromWhere = fromWhere
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
        position1 = position
    }

    inner class ViewHolder(private val dataBinding: RowDialogTypeStoreBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        init {
            /*   if(fromWhere == Constant.TAG_TYPE) {
                   Log.e("dfdf",BaseSharedPreference.getInstance(context).getPrefValue(context.getString(R.string.tagType)))
                   if (!(BaseSharedPreference.getInstance(context).getPrefValue(context.getString(R.string.tagType))).isNullOrBlank()) {
                       if( (BaseSharedPreference.getInstance(context).getPrefValue(context.getString(R.string.tagType)))!!.toInt() == position1)
                       {
                           dataBinding.rbType.isChecked = true

                       }
                   }
               }

               if(fromWhere == Constant.TAG_STORE)
               {
                   if (!(BaseSharedPreference.getInstance(context).getPrefValue(context.getString(R.string.storeNumber))).isNullOrBlank()) {

                       if ((BaseSharedPreference.getInstance(context).getPrefValue(context.getString(R.string.storeNumber)))!!.toInt() == position1) {
                           if (selected != null) {
                               selected!!.isChecked = true
                           }

                       }
                   }
               }*/
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