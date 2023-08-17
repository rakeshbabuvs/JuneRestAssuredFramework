package com.qa.gorest.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager {

    private Properties prop;
    private FileInputStream ip;

    public Properties initProp() {
        prop = new Properties();
        try {
            ip = new FileInputStream("/Users/rakeshbabu/Desktop/Rakesh/Java Repo/June2023RestAssuredFramework/src/test/resources/config/config.properties");
            prop.load(ip);
            }
        catch (FileNotFoundException e) {
                e.printStackTrace();
            }
     catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

}
