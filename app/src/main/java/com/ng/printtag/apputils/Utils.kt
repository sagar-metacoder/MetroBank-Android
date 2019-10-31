package com.ng.printtag.apputils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
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
import com.google.gson.Gson
import com.ng.printtag.BuildConfig
import com.ng.printtag.R
import com.ng.printtag.dashboard.ActivityDashboard
import com.ng.printtag.login.ActivityLogin
import com.ng.printtag.models.allrequests.AllRequestModel
import com.ng.printtag.models.login.LoginModel
import com.ng.printtag.printrequest.ActivityNewPrintRequest
import java.text.ParseException
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

        fun navigateToNewPrintRequest(context: Context, records: AllRequestModel.Data.Records?) {
            val intent = Intent(context, ActivityNewPrintRequest::class.java)
            if (records != null)
                intent.putExtra(context.getString(R.string.records), Gson().toJson(records))
            AppUtils.navigateToOtherScreen(
                context as AppCompatActivity, intent, false
            )
        }


        /**
         * Navigate to login screen
         */
        @JvmStatic
        fun gotoLoginScreen(activity: Activity) {
            AppUtils.setUserData(activity, LoginModel())
            AppUtils.navigateToOtherScreen(activity, ActivityLogin::class.java, true)
        }


        @Suppress("DEPRECATION")
        fun setLocalForTheApp(context: Activity, language: String) {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            context.baseContext.resources.updateConfiguration(
                config,
                context.baseContext.resources.displayMetrics
            )
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



        @SuppressLint("SimpleDateFormat")
        fun parseDateToMMddyyyy(dateConvert: String): String? {
            val inputPattern = "MMM dd, yyyy"
            val outputPattern = "MM/dd/yyyy"
            val inputFormat = SimpleDateFormat(inputPattern)
            val outputFormat = SimpleDateFormat(outputPattern)

            val date: Date?
            var str: String? = null

            try {
                date = inputFormat.parse(dateConvert)
                str = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return str
        }


    }
}