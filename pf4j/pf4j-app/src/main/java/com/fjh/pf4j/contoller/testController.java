package com.fjh.pf4j.contoller;

import com.fjh.pf4j.bean.PluginInfo;
import com.fjh.pf4j.demo.Greetings;
import com.fjh.pf4j.service.PluginsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("test")
public class testController {

    @Autowired
    private Greetings greetings;


    @Autowired
    private PluginsService pluginService;

    @GetMapping("greetings")
    public List<String> test() {

        return greetings.printGreetings();
    }

    /**
     * Gets the plugins.
     *
     * @return the plugins
     */
    @GetMapping("getPlugins")
    public List<PluginInfo> getPlugins() {
        return pluginService.getPlugins();
    }

    @DeleteMapping("deletePlugin")
    public boolean deletePlugin(String pluginId) {
        return pluginService.delete(pluginId);
    }

    @GetMapping("submit")
    public void submit(String pluginFileName) {
        pluginService.submit(pluginFileName);
    }


}
