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
    var selectedDept: ArrayList<Int> = ArrayList()

    private lateinit var onItemClickListener: OnItemClickListener


    fun setData(
        context: Context,
        departmentList: ArrayList<String>,
        selectedDept: ArrayList<Int>,
        onItemClickListener: OnItemClickListener

    ) {
        this.context = context
        this.departmentList = departmentList
        this.selectedDept = selectedDept
        this.onItemClickListener = onItemClickListener

        mInflater = LayoutInflater.from(context)
    }

    override fun getItemCount(): Int {
        return departmentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBinding.chkDepartment.setTag(position)
        if (selectedDept.contains(position)) {
            holder.dataBinding.chkDepartment.isChecked = true
        }
        holder.bind(departmentList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutDataBinding = DataBindingUtil.inflate(
            mInflater!!,
            viewType, parent, false
        ) as RowDialogDepartmentBinding

        return ViewHolder(layoutDataBinding)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.row_dialog_department
    }


    inner class ViewHolder(val dataBinding: RowDialogDepartmentBinding) :
        RecyclerView.ViewHolder(dataBinding.root), CompoundButton.OnCheckedChangeListener {
        init {
            dataBinding.chkDepartment.setOnCheckedChangeListener(this)
        }

        override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
            if (p1) {

                selectedDeptList.add(adapterPosition)
                onItemClickListener.onItemClick(selectedDeptList, adapterPosition)

            } else {
                if (selectedDeptList.contains(adapterPosition)) {
                    selectedDeptList.remove(adapterPosition)
                    onItemClickListener.onItemClick(selectedDeptList, adapterPosition)

                }
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