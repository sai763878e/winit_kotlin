package com.example.winit_kotlin.parsers

import org.simpleframework.xml.Serializer
import org.simpleframework.xml.core.Persister

abstract class BaseXmlHandler<T> {
    protected var serializer : Serializer = Persister()
    abstract fun parseXML(xml : String)
    abstract fun parseXMLForAll(xml : String,methodName : String)
}