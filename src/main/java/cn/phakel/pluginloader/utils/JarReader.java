package cn.phakel.pluginloader.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class JarReader {
    public static Map<String, String> getManiFest(String jarPath) throws IOException {
        File file = new File(jarPath);
        File[] fs = file.listFiles();
        Map jars = new HashMap<String, String>();

        for(File jar : fs) {
            String filePath = jar.getCanonicalPath();
            JarFile jarFile=new JarFile(filePath);
            Manifest manifest=jarFile.getManifest();
            if (manifest != null){
                String classPaths = (String) manifest.getMainAttributes().get(new Attributes.Name("Class-Path"));
                if (classPaths != null && !classPaths.isEmpty()) {
                    jars.put(filePath, classPaths);
                }
            }
        }

        return jars;
    }
}
