package com.ng.printtag.printrequest

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.ng.printtag.R
import com.ng.printtag.apputils.AppUtils
import com.ng.printtag.apputils.CallDialog
import com.ng.printtag.base.BaseFragment
import com.ng.printtag.databinding.FragmentAddProductBinding
import com.ng.printtag.interfaces.CallBackInterfaces
import com.ng.printtag.interfaces.OnItemClickListener
import com.ng.printtag.models.newrequests.AddProductModel
import kotlinx.android.synthetic.main.dialog_product_view.view.*


class FragmentAddProduct : BaseFragment<FragmentAddProductBinding>() {

    lateinit var binding: FragmentAddProductBinding
    var context: ActivityNewPrintRequest? = null
    lateinit var adapter: AddProductAdapter
    override fun initFragment() {
        binding = getFragmentDataBinding()
        context = activity as ActivityNewPrintRequest


        if (context!!.addProducts.isNullOrEmpty()) {
            binding.rvAllProduct.visibility = View.GONE
            binding.linearFound.visibility = View.VISIBLE
            binding.relSave.visibility = View.GONE
        } else if (!context!!.addProducts.isNullOrEmpty() && context!!.isDraft && ActivityNewPrintRequest.fromAll) {
            binding.rvAllProduct.visibility = View.VISIBLE
            binding.linearFound.visibility = View.GONE
            binding.relSave.visibility = View.VISIBLE
        } else if (!context!!.addProducts.isNullOrEmpty() && !context!!.isDraft && ActivityNewPrintRequest.fromAll) {
            binding.rvAllProduct.visibility = View.VISIBLE
            binding.linearFound.visibility = View.GONE
            binding.relSave.visibility = View.GONE
        } else if (!context!!.addProducts.isNullOrEmpty() && !ActivityNewPrintRequest.fromAll) {
            binding.rvAllProduct.visibility = View.VISIBLE
            binding.linearFound.visibility = View.GONE
            binding.relSave.visibility = View.VISIBLE
        }


        handleClick()
        setAdapter()

    }

    override fun getLayoutId(): Int = R.layout.fragment_add_product


    fun setAdapter() {

        adapter = AddProductAdapter(
            context!!.addProducts, context!!.isDraft, context!!.newPrintReq,
            object : OnItemClickListener {
                override fun onItemClick(item: Any, position: Int) {

                    if (item.equals("view")) {


                        val view = layoutInflater.inflate(R.layout.dialog_product_view, null)
                        val dialog = BottomSheetDialog(activity!!, R.style.AppBottomSheetDialogTheme)
                        view.upcName.text = context!!.addProducts[position].upcName
                        view.upcNumber.text = context!!.addProducts[position].upcNumber
                        view.upcQty.text = context!!.addProducts[position].qty
                        val multiFormatWriter = MultiFormatWriter()
                        try {

                            val bitMatrix = multiFormatWriter.encode(
                                context!!.addProducts[position].upcNumber,
                                BarcodeFormat.CODE_128,
                                400,
                                80
                            )
                            val barcodeEncoder = BarcodeEncoder()
                            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
                            view.image_barcode.setImageBitmap(bitmap)
                        } catch (e: WriterException) {
                            e.printStackTrace()
                        }

                        dialog.setContentView(view)
                        dialog.show()


                        /*    if (isUp) {
                                slideDown(binding.liViewProduct);
                            } else {
                                slideUp(binding.liViewProduct);
                            }
                            isUp = !isUp;*/

                    } else if (item.equals("delete")) {

                        CallDialog.errorDialog(
                            activity!!,
                            getString(R.string.a_lbl_warning_title),
                            getString(R.string.a_msg_delete),
                            getString(R.string.a_btn_yes),
                            getString(R.string.a_btn_no),
                            getString(R.string.action_delete),
                            object : CallBackInterfaces {
                                override fun onCallBack(item: Any, fromWhere: Any) {

                                    if (fromWhere.equals("ok")) {
                                        context!!.addProducts.removeAt(position)
                                        adapter.notifyDataSetChanged()
                                    }

                                    if (context!!.addProducts.isNullOrEmpty()) {
                                        binding.rvAllProduct.visibility = View.GONE
                                        binding.linearFound.visibility = View.VISIBLE
                                        binding.relSave.visibility = View.GONE
                                    }
                                }

                            })


                    } else {

                        if (!(item as String).isEmpty()) {

                            if (context!!.maxQuantity.toInt() < (item as String).toInt()) {
                                AppUtils.hideKeyBoard(activity!!)
                                CallDialog.errorDialog(
                                    activity!!,
                                    getString(R.string.a_lbl_warning_title),
                                    String.format(getString(R.string.a_msg_quantity), context!!.maxQuantity),
                                    "",
                                    getString(R.string.a_btn_ok),
                                    "", null
                                )
                                adapter.notifyDataSetChanged()
                            } else {
                                try {


                                    val allProducts = AddProductModel()
                                    allProducts.qty = item
                                    allProducts.upcName = context!!.addProducts[position].upcName
                                    allProducts.upcNumber = context!!.addProducts[position].upcNumber
                                    context!!.addProducts.set(position, allProducts)
                                    //adapter.notifyDataSetChanged()
                                } catch (e: Exception) {

                                }
                            }
                        }
                    }
                }
            })
        binding.rvAllProduct.adapter = adapter
    }

    private fun handleClick() {

        binding.btnSubmit.setOnClickListener {
            context!!.callSubmitApi(resources.getString(R.string.action_submit))
        }
        binding.btnDraft.setOnClickListener {
            context!!.callSubmitApi(resources.getString(R.string.action_draft))

        }

        binding.btnPrintNow.setOnClickListener {
            context!!.callSubmitApi(resources.getString(R.string.action_printed))

        }

    }

    override fun onResume() {
        super.onResume()
        if (context!!.emdkManager != null) {
            context!!.initBarcodeManager()
            context!!.enumerateScannerDevices()
            context!!.initScanner()
        }
    }

    override fun onStop() {
        super.onStop()
        context!!.deInitScanner()
        context!!.deInitBarcodeManager()
    }


}