package com.streaming.properties;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author customfurnish.com
 */
public final class PropertyLoader {
    public Properties getProperties() throws Exception {
        InputStream input = null;
        Properties properties = new Properties();
        input = getClass().getClassLoader().getResourceAsStream("application.properties");
        if (input != null) {
            properties.load(input);
        } else {
            throw new FileNotFoundException("Application.properties doesn't exist");
        }
        return properties;
    }

    public Properties getSmsProperties() throws Exception {
        InputStream input = null;
        Properties properties = new Properties();
        input = getClass().getClassLoader().getResourceAsStream("sms.properties");
        if (input != null) {
            properties.load(input);
        } else {
            throw new FileNotFoundException("sms.properties doesn't exist");
        }
        return properties;
    }
}
