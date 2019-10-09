package com.ng.printtag.apputils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Base64
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.ng.printtag.BuildConfig
import com.ng.printtag.R
import com.ng.printtag.common.ActivityInstructions
import com.ng.printtag.dashboard.ActivityDashboard
import com.ng.printtag.login.ActivityLogin
import com.ng.printtag.models.login.LoginModel
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {


        /**
         *  This method used for navigate from one fragment to another
         *
         *  @param view Given the kind of a view which want to popup from
         *  @param resId Given the kind of an action which want to popup to
         *  @param bundle Pass argument parameter with fragment popup
         */
        fun navigateTo(view: View, resId: Int, bundle: Bundle?) {
            Navigation.findNavController(view).navigate(resId, bundle)
        }

        /**
         * This method used for navigate pop back stack
         */
        fun popUpTo(view: View) {
            Navigation.findNavController(view).popBackStack()
        }

        /**
         * Navigate to home screen
         */
        @JvmStatic
        fun gotoHomeScreen(context: Activity) {
            AppUtils.navigateToOtherScreen(context, ActivityDashboard::class.java, true)

        }



        /**
         * Navigate to login screen
         */
        @JvmStatic
        fun gotoLoginScreen(activity: Activity) {
            AppUtils.setUserData(activity, LoginModel())
            AppUtils.navigateToOtherScreen(activity, ActivityLogin::class.java, true)
        }

        /**
         * Navigate to home screen
         */
        @JvmStatic
        fun setErrorToast(context: Context, msg: String?) {
            if (msg == null || msg.isEmpty())
                AppUtils.showLongToast(
                    context,
                    context.getString(R.string.a_msg_server_error)
                )
            else
                AppUtils.showLongToast(context, msg)
        }

        /**
         * This method used to get format number with add special character
         */
        @JvmStatic
        fun getNumber(formattedNumber: String): String {
            var number = formattedNumber
            val specialChar = "() -+"
            for (actualNumber in formattedNumber) {
                for (item in specialChar) {
                    number = number.replace(item.toString(), "")
                }
            }
            return number
        }


        /**
         * This method used to get file name from uri
         */
        @SuppressLint("Recycle")
        fun getFileName(uri: Uri, context: Context): String? {
            var result: String? = null
            if (uri.scheme == "content") {
                val cursor = context.contentResolver.query(uri, null, null, null, null)
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    cursor?.close()
                }
            }
            if (result == null) {
                result = uri.lastPathSegment
            }
            return result
        }

        /**
         * Navigate to instruction screen
         */
        fun navigateInstructionScreen(context: Context, action: String) {
            val intent = Intent(context, ActivityInstructions::class.java)
            intent.putExtra(
                context.getString(R.string.instruction_action),
                action
            )
            intent.putExtra(
                context.getString(R.string.instruction_action_from),
                ""
            )
            AppUtils.navigateToOtherScreen(context as AppCompatActivity, intent, false)
        }


        /**
         * Navigate to instruction screen
         */
        fun navigateInstructionScreen(context: Context, action: String, actionFrom: String) {
            val intent = Intent(context, ActivityInstructions::class.java)
            intent.putExtra(
                context.getString(R.string.instruction_action),
                action
            )
            intent.putExtra(
                context.getString(R.string.instruction_action_from),
                actionFrom
            )
            AppUtils.navigateToOtherScreen(context as AppCompatActivity, intent, false)
        }



        /**
         * Check current sdk version
         */
        @JvmStatic
        fun checkSdkVersion(): Boolean {
            return Build.VERSION.SDK_INT >= 23
        }

        /**
         * This method used to convert base64 image into bitmap
         */
        @JvmStatic
        fun getBitmap(imageBase64: String): Bitmap {
            val decodedString = Base64.decode(imageBase64, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        }


        /**
         * This method used to get app version with server name
         */
        @JvmStatic
        fun getAppVersion(isForHeader: Boolean): String {
            var date: String
            try {
                val outputPattern: String = if (isForHeader)
                    "dd MMMM"
                else {
                    "MMMM dd, yyyy"
                }
                val outputDateFormat = SimpleDateFormat(outputPattern, Locale.getDefault())
                date = outputDateFormat.format(Date())
            } catch (e: java.lang.Exception) {
                date = "--"
                e.printStackTrace()
            }
            return if (isForHeader)
                "v" + BuildConfig.VERSION_NAME + "-" + date
            else {
                val buildType = if (BuildConfig.DOMAIN_URL.contains("stage"))
                    "Stage"
                else
                    "Dev"

                buildType + " :  v" + BuildConfig.VERSION_NAME + " (" + date + ")"
            }
        }

        fun addDecimal(digits: String): String {
            var string = "" // Your currency
            // Amount length greater than 2 means we need to add a decimal point
            when {
                digits.length > 2 -> {
                    val pound = digits.substring(0, digits.length - 2) // Pound
                    // part
                    val pence = digits.substring(digits.length - 2) // Pence part
                    string += "$pound.$pence"
                }
                digits.length == 1 -> {
                    string += digits
                }
                digits.length == 2 -> {
                    string += ".$digits"
                }
            }
            return string
        }

        

    }
}