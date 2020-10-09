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


    const val CALL_SIGN_URL = 1
    const val CALL_ALL_REQUEST = 4
    const val CALL_SHOP_SUPPLY_REQUEST = 5
    val CALL_PUBLICATION = 2
    val CALL_SHOP_NAME = 3
    val CALL_PUBLICATION_PRICE = 6
    val CALL_SHOP_TRAN = 7
    val CALL_CREATE_RECIVED_DETAILS = 8
    val CALL_CREATE_SUPPLY = 9
    val CALL_CREATE_SHOP_PAYMENT = 10


}