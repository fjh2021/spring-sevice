package com.fjh.pf4j.bean;

import lombok.Data;
import org.pf4j.*;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;


@Data
public class PluginInfo implements Serializable {

    private String pluginId;
    private String pluginDescription;
    private String pluginClass;
    private String version;
    private String requires ;
    private String provider;
    private String license;

    private Path pluginPath;
    private PluginState pluginState;
}
