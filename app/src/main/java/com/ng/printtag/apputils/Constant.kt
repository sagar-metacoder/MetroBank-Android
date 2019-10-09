package com.ng.printtag.apputils

object Constant {
    const val ACTIVITY_PERMISSION_REQUEST_CODE = 11102

    const val emailPattern: String = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"

    const val PREFIX_IMAGE_DATA_TYPE = "data:image/jpeg;base64,"


    const val  CALL_SIGN_URL = 1
    const val CALL_STORE_URL = 2
    const val CALL_DEPARTMENT_URL = 3


}