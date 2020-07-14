package com.streaming.utils;

import java.io.IOException;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XMLToJSONConverter<T> {
    public T convert(String xml, Class<T> clazz) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(xml, clazz);
    }
}
