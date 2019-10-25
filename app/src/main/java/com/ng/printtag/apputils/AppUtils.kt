package com.ng.printtag.apputils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.ng.printtag.R
import com.ng.printtag.models.SystemSettingsModel
import com.ng.printtag.models.login.LoginModel
import org.json.JSONObject
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.text.SimpleDateFormat
import java.util.*

class AppUtils {

    companion object {

        /**
         * This function is used to check Internet availability
         * call this method when you want to perform network operation
         */
        @SuppressLint("MissingPermission")
        @Suppress("DEPRECATION")
        fun isNetworkAvailable(context: Context): Boolean {
            val networkTypes = intArrayOf(ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI)
            try {
                val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                for (networkType in networkTypes) {
                    val activeNetworkInfo = connectivityManager.activeNetworkInfo
                    if (activeNetworkInfo != null && activeNetworkInfo.type == networkType)
                        return true
                }
            } catch (e: Exception) {
                return false
            }

            return false
        }

        /**
         * This function is used to show activity using class name
         * call this method when you don't have any userModel via intent
         */
        fun navigateToOtherScreen(context: Activity, cls: Class<*>, clearStack: Boolean) {
            val intent = Intent(context, cls)
            if (clearStack)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
            context.overridePendingTransition(
                R.anim.push_in_from_left,
                R.anim.push_out_to_right
            )
        }

        /**
         * This function is used to show activity using class name
         * call this method when you don't have any userModel via intent
         */
        fun navigateToOtherScreen(context: Activity, intent: Intent, clearStack: Boolean) {
            if (clearStack)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
            context.overridePendingTransition(
                R.anim.push_in_from_left,
                R.anim.push_out_to_right
            )
        }

        /**
         * Finish activity
         *
         * @param activity the activity
         */
        fun finishActivity(activity: Activity) {
            activity.finish()
            activity.overridePendingTransition(R.anim.push_in_from_right, R.anim.push_out_to_left)
        }

        /**
         * This function is used to check installed android sdk version is greater than 23 or not.
         *
         *  @return version status
         */
        fun checkSdkVersion(): Boolean {
            return Build.VERSION.SDK_INT >= 23
        }

        /**
         * This function is used to show toast message for long time
         *
         * @param context
         * @param message String message to be displayed
         */
        fun showLongToast(context: Context, message: CharSequence): Toast {
            return Toast.makeText(context, message, Toast.LENGTH_LONG).apply { show() }
        }

        /**
         * This function is used to show Toast messages for short time
         *
         * @param context
         * @param message String message to be displayed
         */
        @SuppressLint
        fun showShortToast(context: Context, message: CharSequence): Toast {
            return Toast.makeText(context, message, Toast.LENGTH_SHORT).apply { show() }
        }

        /**
         * This method used for hide the keyboard if showing
         */
        fun hideSoftKeyboard(activity: Activity) {
            try {
                if (activity.currentFocus != null && activity.currentFocus!!.windowToken != null) {
                    val inputMethodManager =
                        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }


        fun hideKeyBoard(context: Context, view: View) {
            val inputMethodManager: InputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        /**
         * This function is used to hide soft keyboard
         */
        fun hideKeyBoard(context: Activity?) {
            if (context != null) {
                val view = context.currentFocus
                if (view != null) {
                    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
        }

        /**
         * Method is use for fill screen activity status bar showing
         */
        fun setStatusBar(window: Window, context: Context) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = ContextCompat.getColor(context, android.R.color.transparent)
                window.navigationBarColor = ContextCompat.getColor(context, android.R.color.transparent)
                window.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_app))
            }
        }

        /**
         * Method is use for fill screen activity no status bar showing
         */
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun noStatusBar(window: Window) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        /**
         * This function is used to get [Map] instance containing key value label data retrieved
         */
        @Throws(Exception::class)
        fun setDataInMap(context: Context, jsonObject: JSONObject): Map<String, String> {
            val innerObj = JSONObject(jsonObject.get("language").toString())
            val iterator = innerObj.keys()
            val hashMap = HashMap<String, String>()
            while (iterator.hasNext()) {
                val key = iterator.next() as String
                val id = context.resources.getIdentifier(key, "string", context.packageName)
                var valueString = "0"
                if (id != 0)
                    valueString = context.getString(id)
                if (!valueString.equals("0", ignoreCase = true)) {
                    var value = innerObj.getString(key)
                    if (value != null && value.isNotEmpty()) {
                        if (value.contains("\'"))
                            value = value.replace("\'", "'")
                        hashMap[valueString] = value
                    }
                    if (value != null && value.isNotEmpty()) {
                        if (value.contains(" &amp;"))
                            value = value.replace(" &amp;", "&")
                        hashMap[valueString] = value
                    } else
                        hashMap[valueString] = context.resources.getString(id)

                }
            }
            saveRequestObject(context, hashMap)
            return hashMap

        }

        /**
         * Save request user model.
         * Saves from Login & after resposne from Splash as UserCommanSettingsModel & RequestUserModel
         *
         * @param context the context
         * @param hashMap the request  hashMap
         */
        private fun saveRequestObject(context: Context, hashMap: Map<String, String>) {
            try {
                val fos = context.openFileOutput("textValue", Context.MODE_PRIVATE)
                val os = ObjectOutputStream(fos)
                os.writeObject(hashMap)
                os.close()
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        /**
         * This Method will get RequestUserModel
         *
         * @param context type Context
         * @return type of RequestUserModel
         */
        @Suppress("UNCHECKED_CAST")
        fun getSaveLabels(context: Context): Map<String, String>? {
            try {
                return if (context.getFileStreamPath("textValue") != null) {
                    val fis = context.openFileInput("textValue")
                    val inputStream = ObjectInputStream(fis)
                    val value = inputStream.readObject()
                    inputStream.close()
                    fis.close()
                    value as Map<String, String>
                } else {
                    null
                }
            } catch (e: IOException) {
                //e.printStackTrace()
                return HashMap()
            } catch (e: ClassNotFoundException) {
                //e.printStackTrace()
                return HashMap()
            }
        }

        /**
         * Used to data store in session
         */
        fun setUserData(context: Context, model: LoginModel) {
            val userData = Gson().toJson(model)
            BaseSharedPreference.getInstance(context).putValue(context.getString(R.string.pref_user_data), userData)
        }

        fun getUserModel(context: Context): LoginModel {
            val userData =
                BaseSharedPreference.getInstance(context).getPrefValue(context.getString(R.string.pref_user_data))
            return if (userData.isNullOrEmpty()) {
                val model = LoginModel()
                model

            } else {
                Gson().fromJson(userData, LoginModel::class.java) as LoginModel
            }
        }

        /**
         * This method will use in progressbar animation
         */
        @JvmStatic
        fun animateProgressDot(view: View) {
            view.clearAnimation()
            val radarAnim2 = ScaleAnimation(
                0f, 1f, // Start and end values for the X axis scaling
                0f, 1f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f
            ) // Pivot point of Y scaling
            radarAnim2.fillAfter = true // Needed to keep the result of the animation
            radarAnim2.duration = 700.toLong()
            radarAnim2.interpolator = AccelerateInterpolator()
            radarAnim2.repeatCount = Animation.INFINITE
            radarAnim2.repeatMode = Animation.REVERSE
            view.startAnimation(radarAnim2)
        }

        /**
         * This method will use to open keyboard
         */
        @JvmStatic
        fun openKeyboardForceFully(context: Context) {
            (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        }

        /**
         * This method will use to convert date into specific format
         */
        @JvmStatic
        fun convertedDate(dateTime: String): String {
            return when (dateTime.isNotEmpty()) {
                true -> {
                    val inputPattern = "MMM dd, yyyy"
                    val outputPattern = "MM/dd/yyyy"
                    val inputDateFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
                    val outputDateFormat = SimpleDateFormat(outputPattern, Locale.getDefault())
                    outputDateFormat.format(inputDateFormat.parse(dateTime))
                }
                false -> "--"
            }
        }


        /**
         * This method will use to get current date and time
         */
        fun currentDate(): String {
            return SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())
        }


        fun formattedDate(date: Int): String {

            if (date < 10) {

                return ("0" + date)
            }
            return date.toString()

        }

        /**
         * This method will use to convert string into specific format
         */
        @JvmStatic
        fun replaceString(ssNo: String): String {
            val b = StringBuilder(ssNo)
            b.replace(0, 5, "*****")
            val a = b.substring(0, 3) + "-" + b.substring(3, 5) + "-" + b.substring(5, b.length)
            return a
        }

        @JvmStatic
        var isFromProfileInfo = 0


        @JvmStatic
        fun getSystemSetting(context: Context): SystemSettingsModel {
            val model = SystemSettingsModel()
            model.bundleId = context.applicationContext.packageName
            model.appVersion =
                context.applicationContext.packageManager.getPackageInfo(context.applicationContext.packageName, 0)
                    .versionName.toString()
            model.deviceModel = Build.MODEL
            model.deviceSystemName = "Android"
            model.deviceSystemVersion = Build.VERSION.RELEASE
            return model
        }
    }
}