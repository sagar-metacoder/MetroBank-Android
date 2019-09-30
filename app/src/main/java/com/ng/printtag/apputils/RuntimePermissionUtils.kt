package com.ng.printtag.apputils

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build

import android.provider.Settings
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ng.printtag.R
import com.ng.printtag.interfaces.RunTimePermissionInterface


class RuntimePermissionUtils {
    private var fragment: Fragment? = null
    private var activity: AppCompatActivity? = null
    private var permission: String? = null
    private var runTimePermissionInterface: RunTimePermissionInterface? = null


    /**
     * constructor
     */
    constructor(activity: AppCompatActivity, runTimePermissionInterface: RunTimePermissionInterface) {
        this.activity = activity
        this.runTimePermissionInterface = runTimePermissionInterface
    }

    /**
     * constructor
     */
    constructor(
        fragment: Fragment,
        activity: AppCompatActivity,
        runTimePermissionInterface: RunTimePermissionInterface
    ) {
        this.fragment = fragment
        this.activity = activity
        this.runTimePermissionInterface = runTimePermissionInterface
    }


    /**
     * Every permission will ask for accessing permission of particular feature.
     * Based on the selection, it will give the result.
     *
     * @param grantResults result of selected permission(s)
     */
    //    @RequiresApi(api = Build.VERSION_CODES.M)
    fun processPermissionsResult(grantResults: IntArray, isSettingScreenDisplay: Boolean) {
        var result = false
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                result = true
            } else {
                break
            }
        }
        if (result) {
            sendData(true)
        } else {
            if (isSettingScreenDisplay) {
                when (permission) {
                    activity!!.getString(R.string.permission_item_storage) -> {
                        val arr = activity!!.resources.getStringArray(R.array.permission_storage)

                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                activity!!,
                                arr[0]
                            ) || ActivityCompat.shouldShowRequestPermissionRationale(activity!!, arr[0])
                        ) {
                            return
                        }
                    }
                    activity!!.getString(R.string.permission_item_camera) -> {
                        val arr = activity!!.resources.getStringArray(R.array.permission_camera)
                        if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, arr[0])) {
                            return
                        }
                    }
                    activity!!.getString(R.string.permission_item_location) -> {
                        val arr = activity!!.resources.getStringArray(R.array.permission_location)

                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                activity!!,
                                arr[0]
                            ) || ActivityCompat.shouldShowRequestPermissionRationale(activity!!, arr[0])
                        ) {
                            return
                        }
                    }
                }

                val builder = AlertDialog.Builder(activity!!)
                builder.setMessage(
                    activity!!.getString(
                        R.string.permission_msg,
                        activity!!.getString(R.string.app_name),
                        permission
                    )
                )
                    .setNegativeButton(activity!!.getString(R.string.action_cancel)) { _, _ ->

                    }
                    .setPositiveButton(activity!!.getString(R.string.settings)) { _, _ ->
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        intent.addCategory(Intent.CATEGORY_DEFAULT)
                        intent.data = Uri.parse("""package:${activity!!.packageName}""")
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                        if (fragment != null)
                            fragment!!.startActivityForResult(intent, SETTING_PERMISSION_REQUEST_CODE)
                        else
                            activity!!.startActivityForResult(intent, SETTING_PERMISSION_REQUEST_CODE)
                    }
                builder.setCancelable(false)
                val alert = builder.create()
                alert.show()
            } else {
                sendData(false)
            }
        }
    }

    /**
     * This function is used to send broadcast to receiver for requested permission with granting status.
     *
     * @param granted
     */
    private fun sendData(granted: Boolean) =
        this.runTimePermissionInterface!!.onPermissionGranted(granted)


    /**
     * This method will check for requested permission with its code weather they are granted or not.
     * If asked permission is not allowed/not requested, an application will generate dialog for accessing specific feature.
     *
     * @param permission  permission name
     * @param requestCode permission request code
     */
    fun requestPermission(permission: String, permissionArray: Array<String>, requestCode: Int) {
        this.permission = permission
        if (!checkPermission(permission)) {
            if (fragment != null)
                fragment!!.requestPermissions(permissionArray, requestCode)
            else
                ActivityCompat.requestPermissions(activity!!, permissionArray, requestCode)
        } else {
            sendData(true)
        }
    }

    /**
     * Check for specific permission weather they are granted or not.
     *
     * @return {boolean} true or false
     */
    private fun checkPermission(permission: String): Boolean {
        val result = ContextCompat.checkSelfPermission(activity!!, permission)
        return result == PackageManager.PERMISSION_GRANTED
    }

    /**
     * This function is used to retrieve permission grant status from activity result function.
     *
     * @param permission
     */
    fun checkOnActivityResultPermission(permission: Array<String>) {
        val result = ContextCompat.checkSelfPermission(activity!!, permission[0])
        if (result == PackageManager.PERMISSION_GRANTED) {
            sendData(true)
        } else {
            sendData(false)
        }
    }

    companion object {
        const val SETTING_PERMISSION_REQUEST_CODE = 101
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermissions(@NonNull vararg permissions: String): Boolean {
        val context: Context = if (fragment != null) {
            fragment!!.activity!!
        } else
            this.activity!!
        for (permission in permissions)
            if (PackageManager.PERMISSION_GRANTED != context.checkSelfPermission(permission))
                return false
        return true
    }
}