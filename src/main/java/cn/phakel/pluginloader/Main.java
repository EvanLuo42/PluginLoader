package cn.phakel.pluginloader;

import cn.phakel.pluginloader.utils.JarReader;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

public class Main {
    public static void main(String args[]) {
        try {
            for(Map.Entry<String, String> jar : JarReader.getManiFest("./plugins").entrySet()) {
                URL url1 = new URL("file:" + jar.getKey());
                URLClassLoader classLoader = new URLClassLoader(new URL[] { url1 }, Thread.currentThread().getContextClassLoader());
                Class<?> PluginMainClass = classLoader.loadClass(jar.getValue());
                Object instance = PluginMainClass.newInstance();
                Method method = instance.getClass().getMethod("onCreate", void.class);
                method.invoke(instance);
                classLoader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
