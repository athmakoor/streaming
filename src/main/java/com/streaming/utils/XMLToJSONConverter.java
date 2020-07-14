package com.streaming.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XMLToJSONConverter<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(XMLToJSONConverter.class);

    public T convert(String xml, Class<T> clazz) throws IOException {
        LOGGER.debug(xml);

        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(xml, clazz);
    }
}
