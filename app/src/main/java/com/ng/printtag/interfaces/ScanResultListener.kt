package com.ng.printtag.interfaces

interface ScanResultListener {
    fun onDocumentScanned(imageBase64: String, docType: String)
}