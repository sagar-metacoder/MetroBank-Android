package com.ng.printtag.apputils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.ConnectivityManager
import android.os.Build
import android.os.Environment
import android.util.Base64
import android.util.Log
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
import androidx.core.text.HtmlCompat
import com.google.gson.Gson
import com.ng.printtag.R
import com.ng.printtag.apputils.Constant.PREFIX_IMAGE_DATA_TYPE
import com.ng.printtag.models.SystemSettingsModel
import com.ng.printtag.models.UserDetailsModel
import com.ng.printtag.models.login.LoginModel

import org.json.JSONObject
import java.io.*
import java.text.DecimalFormat
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
        fun navigateToResultScreen(context: Activity, cls: Class<*>, reqCode: Int) {
            val intent = Intent(context, cls)
            context.startActivityForResult(intent, reqCode)
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


        /**
         * Show the text in html format
         *
         * @param html type of String
         * @return type of string
         */
        fun htmlFormat(html: String): String {

            return "<html><head><body><h1><style type=\"text/css\">body{color: #FFF; }</style></head>" +
                    "$html</h1></body></html>"

        }

        /**
         * Show the Email text in html format
         *
         * @param name : name display in body format of email
         * @param title : title of email
         * @param body : body of email
         */
        fun emailHtmlFormat(
            dear: String,
            thanks: String,
            name: String,
            title: String,
            body: String,
            prospera: String
        ): String {
            val format = "<html>\n" +
                    "<head>\n" +
                    "<title>$title</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div>" + dear + " <b> " + name + "</b>,</div><br/><br/>\n\n" +
                    "    <div>$body</div><br/><br/>\n\n" +
                    "    <div>" + thanks + "</br></br><div></div>\n" + prospera + "</div>\n" +
                    "    </body>\n" +
                    "</html>"

            return format
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
         * Convert base64 image to ByteArray
         */
        fun getImageBytes(imagePath: String?): ByteArray {

            val bm = BitmapFactory.decodeFile(imagePath)
            val outputStream = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            return outputStream.toByteArray()
        }

        /**
         * Convert Data in base64 from drawable image
         */
        @JvmStatic
        fun getImageBase64Data(context: Context, filePath: String, resId: Int): String {
            val stringBuilder = StringBuilder()
            stringBuilder.append(PREFIX_IMAGE_DATA_TYPE)
            when {
                filePath.trim().isNotEmpty() -> {
                    stringBuilder.append(encoder(filePath))
                }
                resId != 0 -> {
                    stringBuilder.append(
                        Base64.encodeToString(
                            getImageBytes(getImageBitmap(context, resId)),
                            Base64.DEFAULT
                        )
                    )
                }
            }
            return stringBuilder.toString()
        }

        /**
         * Convert filepath in base64
         */
        private fun encoder(filePath: String): String {
            val bytes = File(filePath).readBytes()
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                java.util.Base64.getEncoder().encodeToString(bytes)
            } else {
                val bm = BitmapFactory.decodeFile(filePath)
                val baos = ByteArrayOutputStream()
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val byteArrayImage = baos.toByteArray()
                Base64.encodeToString(byteArrayImage, Base64.DEFAULT)
            }
        }

        /**
         * Convert image bitmap
         */
        private fun getImageBitmap(context: Context, resId: Int): String? {
            return (ContextCompat.getDrawable(context, resId) as BitmapDrawable).bitmap.toString()
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
         * This method will use to remove special character from string
         *
         *  @return type of string
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
         * This method will use to convert base64 in bitmap format
         *
         *  @return type of Bitmap
         */
        fun getBitmap(imageBase64: String): Bitmap {
            val decodedString = Base64.decode(imageBase64, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        }

        /**
         * This method will use to convert url in base64 format
         *
         *  @return type of string
         */
        @JvmStatic
        fun getFcId(fcId: String): String {
            return when {
                fcId.isEmpty() -> System.currentTimeMillis().toString()
                else -> fcId
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
                    val inputPattern = "yyyy-MM-dd HH:mm"
                    val outputPattern = "MM/dd/yyyy"
                    val inputDateFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
                    val outputDateFormat = SimpleDateFormat(outputPattern, Locale.getDefault())
                    outputDateFormat.format(inputDateFormat.parse(dateTime))
                }
                false -> "--"
            }
        }

        /**
         * This method will use to convert date into specific format
         */
        @JvmStatic
        fun convertedDateForDoc(dateTime: String): String {
            return when (dateTime.isNotEmpty()) {
                true -> {
                    val inputPattern = "yyyy-MM-dd HH:mm:ss"
                    val outputPattern = "MM/dd/yyyy"
                    val inputDateFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
                    val outputDateFormat = SimpleDateFormat(outputPattern, Locale.getDefault())
                    outputDateFormat.format(inputDateFormat.parse(dateTime))
                }
                false -> "--"
            }
        }

        /**
         * This method will use to convert date into specific format
         */
        @JvmStatic
        fun getCompleteDate(dateTime: String?): String {
            return when (!dateTime.isNullOrEmpty()) {
                true -> {
                    val inputPattern = "yyyy-MM-dd HH:mm"
                    val outputPattern = "MMMM dd, yyyy"
                    val inputDateFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
                    val outputDateFormat = SimpleDateFormat(outputPattern, Locale.getDefault())
                    outputDateFormat.format(inputDateFormat.parse(dateTime))
                }
                false -> "--"
            }
        }

        /**
         * This method will use to convert date into specific format
         */
        @JvmStatic
        fun convertedDateForTier3(dateTime: String): String {
            return when (dateTime.isNotEmpty()) {
                true -> {
                    return try {
                        val inputPattern = "yyyy-MM-dd HH:mm:ss"
                        val outputPattern = "MM/dd/yyyy"
                        val inputDateFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
                        val outputDateFormat = SimpleDateFormat(outputPattern, Locale.getDefault())
                        outputDateFormat.format(inputDateFormat.parse(dateTime))
                    } catch (e: java.lang.Exception) {
                        "N/A"
                    }
                }
                false -> "N/A"
            }
        }
        /**
         * This method will use to convert date into specific format
         */
        @JvmStatic
        fun getDate(dateTime: String): String {
            return try {
                when (dateTime.isNotEmpty()) {
                    true -> {
                        val inputPattern = "MM/dd/yyyy"
                        val outputPattern = "MMMM dd, yyyy"
                        val inputDateFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
                        val outputDateFormat = SimpleDateFormat(outputPattern, Locale.getDefault())
                        outputDateFormat.format(inputDateFormat.parse(dateTime))
                    }
                    false -> "--"
                }
            } catch (e: java.lang.Exception) {
                "--"
            }
        }

        /**
         * This method will use to convert date into specific format
         */
        @JvmStatic
        fun getUpdateDate(dateTime: String): String {
            return try {
                //2019-06-07 10:40
                when (!dateTime.isNullOrEmpty()) {
                    true -> {
                        val outputPattern = "MM/dd/yyyy"
                        val inputPattern = "yyyy-MM-dd hh:mm"
                        val inputDateFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
                        val outputDateFormat = SimpleDateFormat(outputPattern, Locale.getDefault())
                        outputDateFormat.format(inputDateFormat.parse(dateTime))
                    }
                    false -> "--"
                }
            } catch (e: java.lang.Exception) {
                "--"
            }
        }

        /**
         * This method will use to validate date of birth
         */
        @Suppress("NAME_SHADOWING")
        fun validateDOB(date: String?): Boolean {
            if (date != null) {
                var enterYear = 0
                var enterDate = ""
                var enterMonth = ""
                val calendar = Calendar.getInstance()
                val currentYear = calendar.get(Calendar.YEAR)
                if (date.contains("/")) {
                    val array = date.split("/")
                    enterDate = array[1]
                    enterMonth = array[0]
                    enterYear = array[2].toInt()
                }
                if (enterYear > currentYear) {
                    return false
                } else {
                    val value = currentYear - enterYear
                    if (value < 15) {
                        return false
                    } else {
                        return if (value == 15) {
                            val date = calendar.get(Calendar.DATE)
                            val month = calendar.get(Calendar.MONTH)
                            if (!enterDate.contains("0") && enterDate.toInt() <= 9)
                                enterDate = "0$enterDate"
                            if (!enterMonth.contains("0") && enterMonth.toInt() <= 9)
                                enterMonth = "0$enterMonth"
                            enterDate.toInt() <= date && enterMonth.toInt() <= month + 1

                        } else {
                            true
                        }
                    }
                }
            } else {
                return false
            }
        }

        /**
         * This method will use to check expiry date
         */
        @Suppress("NAME_SHADOWING")
        fun validateExpiry(date: String?): Boolean {
            if (date != null) {
                var enterYear = 0
                var enterDate = ""
                var enterMonth = ""
                val calendar = Calendar.getInstance()
                val currentYear = calendar.get(Calendar.YEAR)
                if (date.contains("/")) {
                    val array = date.split("/")
                    enterDate = array[1]
                    enterMonth = array[0]
                    enterYear = array[2].toInt()
                }
                val totalMonth = 12
                when {
                    enterMonth.isNotEmpty() && enterMonth.toInt() > totalMonth -> return false
                    enterYear < currentYear -> return false
                    enterYear > currentYear -> return true
                    else -> {
                        var enterDate = enterDate
                        var enterMonth = enterMonth
                        val date = calendar.get(Calendar.DATE)
                        val month = calendar.get(Calendar.MONTH)
                        if (!enterDate.contains("0") && enterDate.toInt() <= 9)
                            enterDate = "0$enterDate"
                        if (!enterMonth.contains("0") && enterMonth.toInt() <= 9)
                            enterMonth = "0$enterMonth"
                        if (enterMonth.toInt() == (month + 1)) {
                            return enterDate.toInt() >= date
                        } else if (enterMonth.toInt() > month + 1) {
                            return true
                        }
                    }
                }
            } else {
                return false
            }
            return false
        }

        /**
         * This method will use to convert date into specific format
         */
        @JvmStatic
        fun getDateValue(dob: String?): String {
            try {
                return if (!dob.isNullOrEmpty()) {
                    //19750508
                    val month = dob.substring(4, 6)
                    val date = dob.substring(6, 8)
                    val year = dob.substring(0, 4)

                    "$month/$date/$year"
                } else {
                    "N/A"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                if (dob != null) {
                    return dob
                }
            }

            return "N/A"
        }

        /**
         * This method will use to convert date and time into specific format
         */
        @JvmStatic
        fun getDateTimeValue(dob: String?): String {
            try {
                return if (!dob.isNullOrEmpty()) {
                    //19750508
                    val month = dob.substring(4, 6)
                    val date = dob.substring(6, 8)
                    val year = dob.substring(0, 4)
                    val hour = dob.substring(8, 10)
                    val minute = dob.substring(10, 12)


                    "$month/$date/$year $hour:$minute"
                } else {
                    "N/A"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                if (dob != null) {
                    return dob
                }
            }

            return "N/A"
        }

        fun getLastTransactionDate(dateTime: String): String {
            return try {
                when (dateTime.isNotEmpty()) {
                    true -> {
//                        2013-05-30 05:55:00
                        val inputPattern = "yyyy-MM-dd hh:mm:ss"
                        val outputPattern = "MM/dd/yyyy hh:mm:ss"
                        val inputDateFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
                        val outputDateFormat = SimpleDateFormat(outputPattern, Locale.getDefault())
                        outputDateFormat.format(inputDateFormat.parse(dateTime))
                    }
                    false -> "N/A"
                }
            } catch (e: java.lang.Exception) {
                "N/A"
            }
        }

        /**
         * This method will use to get current date and time
         */
        fun currentDate(): String {
            return SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())
        }



        /**
         * This method will use to get current date
         */
        fun getCurrentDateValue(): String {
            return SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        }

        /**
         * This method will use to convert date into specific format
         */
        @JvmStatic
        fun getCheckSubmissionDate(dateTime: String): String {
            return try {
                when (dateTime.isNotEmpty()) {
                    true -> {
                        val inputPattern = "yyyy-MM-dd hh:mm:ss"
                        val outputPattern = "MMMM dd, yyyy"
                        val inputDateFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
                        val outputDateFormat = SimpleDateFormat(outputPattern, Locale.getDefault())
                        outputDateFormat.format(inputDateFormat.parse(dateTime))
                    }
                    false -> "--"
                }
            } catch (e: java.lang.Exception) {
                "--"
            }
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


        /**
         * This method will use to convert string into decimal format
         */
        @JvmStatic
        fun getDecimalFormat(amount: String?): String {
            return if (amount.isNullOrEmpty()) {
                "0"
            } else {
                DecimalFormat("##.##").format(amount.toDouble()).toString()
            }
        }

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

        fun writeToFile(data: JSONObject, fileName: String, dirName: String) {
            // Get the directory for the user's public pictures directory.
            val path = Environment.getExternalStoragePublicDirectory(
                //Environment.DIRECTORY_PICTURES
                Environment.DIRECTORY_DOWNLOADS + "/" + dirName + "/"
            )

            // Make sure the path directory exists.
            if (!path.exists()) {
                // Make it, if it doesn't exit
                path.mkdirs()
            }

            val file = File(path, "$fileName.json")

            // Save your stream, don't forget to flush() it before closing it.

            try {
                file.createNewFile()
                val fOut = FileOutputStream(file)
                val myOutWriter = OutputStreamWriter(fOut)
                myOutWriter.append(data.toString())

                myOutWriter.close()

                fOut.flush()
                fOut.close()
            } catch (e: IOException) {
                Log.e("Exception", "File write failed: $e")
            }
        }

        fun setHtml(htmlString: String): String {
            return HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
        }

        fun getPhonFormat(phoneFormat: String?): String? {
            var phoneFormatVal = ""
            if (phoneFormat.isNullOrEmpty())
                phoneFormatVal = "(###) ### ##"
            else
                phoneFormatVal = phoneFormat

            return phoneFormatVal
        }
    }
}