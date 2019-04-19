package com.android.mobinsafaeian.detfhappinesspeyk.PageMain

interface RecyclerViewDialogInterface {
    fun showInformationDialog(name:String , timeStamp:String)
    fun getPermissionsAndDeleteImage(fileName:String)
    fun getPermissionsAndDownloadImage(url:String , name:String)
}