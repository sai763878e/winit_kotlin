package com.example.winit_kotlin.interfaces

interface OnDialogListeners {
    fun onButtonYesClick(from :String)
    fun onButtonYesClick(from :String,any: Any)
    fun onButtonNoClick(from :String)
    fun onButtonNoClick(from :String,any: Any)
}