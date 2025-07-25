package com.t24.pacs;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.temenos.tafj.api.client.TAFJRuntime;
import com.temenos.tafj.api.client.impl.T24Context;
import com.temenos.tafj.api.client.impl.TAFJRuntimeFactory;

/**
 * TODO: This program will run jBC programs of Temenos. Can be used to perform corrections with all logic owned in jBC routine to start and end T24 session. 
 *
 * @author bsauravkumar@temenos.com
 *
 */

public class ExecuteJBCRoutine {
    
    private static final Logger logger = LogManager.getLogger(ExecuteJBCRoutine.class.getName());
    private Properties properties;
    
    /*
     * Load the config.properties file when initializing the class so that we can use it all the time.
     */
    public ExecuteJBCRoutine () {
        properties = ConfigLoader.loadProperties();
    }
    
    /*
     * This function will check if the routine requested to execute is compiled not not. The package name where the class file is generated for the routine
     * will be specified in config.properties file as property external.jar.
     * @param String routineName : jBC routine name check if exists on runtime class path or not
     * @return boolean true if class file exists else false
     */
    
    public boolean checkRoutineExists(String routineName) {
        
        boolean routineExists = true;
        File jarFile = new File(ConfigLoader.getPropertyValue(properties, "external.jar"));
        if (!jarFile.exists()) {
            logger.error("Jar file location not defined");
            return false;
        }
        
        String className = routineName.replace(".", "_");
        String classFile = "com.temenos.t24." + className + "_cl";
        URLClassLoader loader = null;
        try {   // Load the jar
            URL jarUrl = jarFile.toURI().toURL();
            loader = new URLClassLoader(new URL[]{jarUrl}, ExecuteJBCRoutine.class.getClassLoader());
            logger.debug("Jar loaded " + jarFile.getName());
            try {   // Check class file exists in the jar
                logger.debug("Checking class file " + classFile);
                Class<?> clazz = loader.loadClass(classFile);
                logger.debug("Class found");
            } catch (ClassNotFoundException classError) {
                logger.error("Missing class file for " + routineName + " in " + jarFile.getAbsolutePath());
                routineExists = false;  // Missing class file in the jar
            } catch (Exception e) {
                logger.error("Error in checking class " + e.toString());
                routineExists = false;  // Missing class file in the jar
            } finally { // Always perform cleanup and close the URL class loader
                try {
                    loader.close();
                } catch (Exception e) {
                    logger.error("Failed to close classloader " + e.toString());
                }
            }
        } catch (Exception e) {
            logger.error("Failed to load jar " + jarFile.getName());
            logger.error("Failed due to error " + e.toString());
            routineExists = false;  // Jar not found so class file also is not present
        }
        
        return routineExists;
    }
    
    /*
     * This function will initiate TAFJ session and invoke jBC routine. Currently arguments is not supported for jBC routine.
     * @param String routineName : jBC routine name check if exists on runtime class path or not
     * @return String response
     */
    public String invokeJBC(String routineName) {

        final String TAFJ_HOME = ConfigLoader.getPropertyValue(properties, "tafj.home");
        logger.debug("Creating T24 context");
        String paramString = TAFJ_HOME + "\\conf\\tafj.properties";
        T24Context ctx = new T24Context(paramString);

        logger.debug("T24 context created");
        TAFJRuntime runtime = TAFJRuntimeFactory.getTAFJRuntime();
        try {
            String tafjProperties = ConfigLoader.getPropertyValue(properties, "tafj.property.name");
            if (tafjProperties.isEmpty())
                tafjProperties = "tafj.properties";
            
            logger.debug("Invoking " + routineName);
            logger.debug("Initializing runtime at " + TAFJ_HOME + " and file " + tafjProperties);
            
            //JVarUtils util = new JVarUtils();
            //List<String> myList = new ArrayList<String>();
            //jVar parameter = util.toJVar(myList);
            
            runtime.init(TAFJ_HOME, tafjProperties, "");
            try {                
                runtime.callJBC(routineName, "");
                return routineName + " executed successfully";
            } catch (Exception e) {
                return "Error in invoking " + routineName;
            }
        } catch (Exception e) {
            return "Error in initializing TAFJ runtime";
        }
    }
}