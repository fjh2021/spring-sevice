package com.fjh.pf4j.service;

import com.fjh.pf4j.bean.PluginInfo;
import com.fjh.pf4j.demo.MyExtensionsInjector;
import org.pf4j.PluginManager;
import org.pf4j.PluginState;
import org.pf4j.PluginWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class PluginsService {

    @Autowired
    private PluginManager pluginManager;

    @Autowired
    private MyExtensionsInjector myExtensionsInjector;

    public List<PluginInfo> getPlugins() {
        List<PluginWrapper> pluginWrapperList = pluginManager.getPlugins();
        List<PluginInfo> list = new ArrayList<>();
        pluginWrapperList.forEach(x -> {
            PluginInfo pluginInfo = new PluginInfo();
            BeanUtils.copyProperties(x.getDescriptor(), pluginInfo);
            pluginInfo.setPluginPath(x.getPluginPath());
            pluginInfo.setPluginState(x.getPluginState());
            list.add(pluginInfo);
        });
        return list;
    }


    public PluginState submit(String pluginFileName) {
        String pluginsHome = System.getProperty("pf4j.pluginsDir", "plugins");
        String newPluginID = pluginManager.loadPlugin(
                new File(pluginsHome + "/" + pluginFileName).toPath());

        pluginManager.enablePlugin(newPluginID);
        PluginState pluginState = pluginManager.startPlugin(newPluginID);
        myExtensionsInjector.injectExtensions(newPluginID);
        return pluginState;
    }

    /**
     * Checks if is active.
     *
     * @param pluginWrapper the plugin wrapper
     * @return true, if is active
     */
    public boolean isActive(PluginWrapper pluginWrapper) {
        return pluginWrapper.getPluginState().equals(PluginState.STARTED);

    }


    public PluginState disable(String pluginId) {
        return pluginManager.stopPlugin(pluginId);
    }


    public boolean delete(String pluginId) {
        myExtensionsInjector.destroyExtensions(pluginId);
        boolean flag=pluginManager.deletePlugin(pluginId);
        return flag;
    }


    public PluginState enable(String pluginId) {
        pluginManager.enablePlugin(pluginId);
        return pluginManager.startPlugin(pluginId);
    }

}
