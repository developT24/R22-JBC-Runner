package com.t24.pacs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * TODO: This program will run load and fetch properties from config.properties file. 
 *
 * @author
 *
 */

public class ConfigLoader {
    
    private static final Logger logger = LogManager.getLogger(ConfigLoader.class.getName());
    
    /*
     * This function will load the values in config.properties file and return as Properties object.
     * @return Properties property from config.properties file.
     */
    protected static Properties loadProperties () {
        logger.debug("Loading config.properties file");
        Properties properties = new Properties();
        try {
            InputStream input = ExecuteJBCRoutine.class.getClassLoader().getResourceAsStream("config.properties");
            if (input != null) {
                properties.load(input);
                return properties;
            } else
                logger.error("Blank config.properties");
        } catch (IOException e) {
            logger.error("Missing config.properties");
            logger.error("Error in fetching config.properties " + e.toString());
        }
        return null;
    }
    
    /*
     * This function will fetch the value of property required.
     * @param Properties properties : Key-value pair as per config.properties file.
     * @param String property : Property value to be fetched.
     * @returns String property value.
     */
    protected static String getPropertyValue(Properties properties, String property) {
        
        if (property.isEmpty()) {
            logger.error("Property is not passed");
            return null;
        }
        
        if (properties.isEmpty())
            properties = loadProperties();
        
        return properties.getProperty(property);
    }
    
}
