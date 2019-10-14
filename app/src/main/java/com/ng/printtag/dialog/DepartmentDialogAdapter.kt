package com.ng.printtag.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ng.printtag.R
import com.ng.printtag.databinding.RowDialogDepartmentBinding
import com.ng.printtag.interfaces.OnItemClickListener

class DepartmentDialogAdapter : RecyclerView.Adapter<DepartmentDialogAdapter.ViewHolder>() {
    private var mInflater: LayoutInflater? = null
    private lateinit var departmentList: ArrayList<String>
    private lateinit var context: Context
    var selectedDeptList: ArrayList<Int> = ArrayList()
    private lateinit var onItemClickListener: OnItemClickListener


    fun setData(
        context: Context,
        departmentList: ArrayList<String>,
        onItemClickListener: OnItemClickListener

    ) {
        this.context = context
        this.departmentList = departmentList
        this.onItemClickListener = onItemClickListener

        mInflater = LayoutInflater.from(context)
    }

    override fun getItemCount(): Int {
        return departmentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(departmentList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutDataBinding = DataBindingUtil.inflate(
            mInflater!!,
            viewType, parent, false
        ) as RowDialogDepartmentBinding
        return ViewHolder(layoutDataBinding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.row_dialog_department
    }

    inner class ViewHolder(private val dataBinding: RowDialogDepartmentBinding) :
        RecyclerView.ViewHolder(dataBinding.root), CompoundButton.OnCheckedChangeListener {
        init {
            /* if(BaseSharedPreference.getInstance(context).getPrefValue(context.getString(R.string.department))!!.contains(position.toString()))
             {
                 dataBinding.chkDepartment.isChecked = true

             }*/
            dataBinding.chkDepartment.setOnCheckedChangeListener(this)
        }


        override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
            if (p1) {
                selectedDeptList.add(position)
                onItemClickListener.onItemClick(departmentList[position], adapterPosition)


            }
        }

        /**
         * Bind the txt with xml
         */
        fun bind(obj: String?) {
            dataBinding.model = obj

            dataBinding.executePendingBindings()


        }
    }
}