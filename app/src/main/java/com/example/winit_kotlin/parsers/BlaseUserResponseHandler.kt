package com.example.winit_kotlin.parsers

class BlaseUserResponseHandler : BaseXmlHandler<SoapEnvelope>() {
    lateinit var checkLoginResponse: CheckLoginResponse
    override fun parseXML(xml: String) {
        try {
            val soapEnvelope: SoapEnvelope = serializer.read(SoapEnvelope::class.java, xml)
            checkLoginResponse = soapEnvelope.body?.checkLoginResponse ?: throw Exception("CheckLoginResponse is null")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    fun getResponse() : CheckLoginResponse{
        return checkLoginResponse
    }

    fun getUserInfo() : List<BlaseUserDco>? {
       return checkLoginResponse.checkLoginResult?.blaseUsers?.blaseUserDcos
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