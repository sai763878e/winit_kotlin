package com.example.winit_kotlin.parsers

class DefaultHandler  : BaseXmlHandler<SoapEnvelope>(){
    override fun parseXML(xml: String) {

    }

   lateinit var response : SoapEnvelope
    override fun parseXMLForAll(xml: String, methodName: String) {
        try {
            response  = serializer.read(SoapEnvelope::class.java,xml)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}