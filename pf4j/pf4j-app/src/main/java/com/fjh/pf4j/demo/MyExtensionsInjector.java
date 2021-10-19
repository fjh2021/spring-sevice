package com.fjh.pf4j.demo;

import org.pf4j.PluginWrapper;
import org.pf4j.spring.SpringPluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;
import java.util.Set;

public class MyExtensionsInjector implements ApplicationContextAware {


    private static final Logger log = LoggerFactory.getLogger(MyExtensionsInjector.class);

    protected final SpringPluginManager springPluginManager;

    protected AbstractAutowireCapableBeanFactory beanFactory;

    private ApplicationContext applicationContext;

    public MyExtensionsInjector(SpringPluginManager springPluginManager) {
        this.springPluginManager = springPluginManager;
    }

    public void injectExtensions(String pluginId) {
        // add extensions from classpath (non plugin)
        Set<String> extensionClassNames;

        // add extensions for each started plugin
        PluginWrapper plugin = springPluginManager.getPlugin(pluginId);
        log.debug("Registering extensions of the plugin '{}' as beans", plugin.getPluginId());
        extensionClassNames = springPluginManager.getExtensionClassNames(plugin.getPluginId());
        for (String extensionClassName : extensionClassNames) {
            try {
                log.debug("Register extension '{}' as bean", extensionClassName);
                Class<?> extensionClass = plugin.getPluginClassLoader().loadClass(extensionClassName);
                registerExtension(extensionClass);
            } catch (ClassNotFoundException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public void  destroyExtensions(String pluginId) {
        // add extensions from classpath (non plugin)
        Set<String> extensionClassNames;

        // add extensions for each started plugin
        PluginWrapper plugin = springPluginManager.getPlugin(pluginId);
        log.debug("Registering extensions of the plugin '{}' as beans", plugin.getPluginId());
        extensionClassNames = springPluginManager.getExtensionClassNames(plugin.getPluginId());
        for (String extensionClassName : extensionClassNames) {
            try {
                log.debug("Register extension '{}' as bean", extensionClassName);
                Class<?> extensionClass = plugin.getPluginClassLoader().loadClass(extensionClassName);
                destroyExtension(extensionClass);
            } catch (ClassNotFoundException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * Register an extension as bean.
     * Current implementation register extension as singleton using {@code beanFactory.registerSingleton()}.
     * The extension instance is created using {@code pluginManager.getExtensionFactory().create(extensionClass)}.
     * The bean name is the extension class name.
     * Override this method if you wish other register strategy.
     */
    protected void registerExtension(Class<?> extensionClass) {
        Map<String, ?> extensionBeanMap = springPluginManager.getApplicationContext().getBeansOfType(extensionClass);
        if (extensionBeanMap.isEmpty()) {
            Object extension = springPluginManager.getExtensionFactory().create(extensionClass);
            beanFactory.registerSingleton(extensionClass.getName(), extension);
        } else {
            log.debug("Bean registeration aborted! Extension '{}' already existed as bean!", extensionClass.getName());
        }
    }

    protected void destroyExtension(Class<?> extensionClass) {
        Map<String, ?> extensionBeanMap = springPluginManager.getApplicationContext().getBeansOfType(extensionClass);
        if (!extensionBeanMap.isEmpty()) {
            for (Map.Entry<String, ?> extensionBean : extensionBeanMap.entrySet()) {
                beanFactory.destroySingleton(extensionBean.getKey());
            }
        } else {
            log.debug("Bean registeration aborted! Extension '{}' not  existed as bean!", extensionClass.getName());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.beanFactory = (AbstractAutowireCapableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
    }
}
