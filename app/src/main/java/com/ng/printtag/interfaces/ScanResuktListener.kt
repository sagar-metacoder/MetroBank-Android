package com.ng.printtag.interfaces

interface ScanResuktListener {
    fun onDocumentScanned(imageBase64: String, docType: String)
}