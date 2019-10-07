package com.ng.printtag.api

/**
 * Class containing flags used while calling api and passed to api service class of specific module.
 */
class RestClientModel {
    var isProgressDialogShow = false //flag defines that have to show progress loader or not.
    var isErrorScreenShow = false //flag defines that have to show error screen or not.
    var isNetWorkScreenShow = false //flag defines that have to show network connection screen or not.
    var prgMsg: String = "" //message to be shown on progress dialog.
}