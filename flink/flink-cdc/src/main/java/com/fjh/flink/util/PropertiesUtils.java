package com.fjh.flink.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {

    public static Properties getProperties(String path) throws IOException {
        Properties props = new Properties();

        FileInputStream in= new FileInputStream( path );
        props.load(in);
        in.close();
        return props;
    }


}
