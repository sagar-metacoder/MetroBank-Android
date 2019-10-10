package com.ng.printtag.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ng.printtag.R
import com.ng.printtag.databinding.RowDialogDepartmentBinding

class DepartmentDialogAdapter : RecyclerView.Adapter<DepartmentDialogAdapter.ViewHolder>() {
    private var mInflater: LayoutInflater? = null
    private lateinit var departmentList: ArrayList<String>
    private lateinit var context: Context
    var selectedDeptList: ArrayList<Int> = ArrayList()


    fun setData(
        context: Context,
        departmentList: ArrayList<String>
    ) {
        this.context = context
        this.departmentList = departmentList
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
            dataBinding.chkDepartment.setOnCheckedChangeListener(this)
        }

        override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
            if (p1) {
                selectedDeptList.add(position)
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