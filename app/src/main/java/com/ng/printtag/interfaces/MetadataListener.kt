package com.ng.printtag.interfaces

interface MetadataListener {
    fun onMetadataReceived(imageBase64: String, docType: String)
}