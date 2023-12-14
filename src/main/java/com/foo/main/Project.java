package com.foo.main;

import java.io.File;
import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Project {

    private static final Path MAVEN_REPO_PATH = Paths.get("/home/pavel/.m2/repository");

    /**
     * Main method.
     * @param args
     */
    public static void main(String[] args) {
        var layerA = createLayerA(ModuleLayer.boot());
        var layerB = createLayerB(layerA);
    }

    private static ModuleLayer createLayerA(ModuleLayer parentLayer) {
        var moduleY = resolveModulePath("com.google.code.gson", "gson", "2.8.6");
        var projectLayer = createLayer(List.of(parentLayer), List.of(moduleY));
        System.out.println("Created layer A: " + projectLayer);
        return projectLayer;
    }

    private static ModuleLayer createLayerB(ModuleLayer parentLayer) {
        var moduleY = resolveModulePath("com.google.code.gson", "gson", "2.8.6");
        var moduleX = resolveModulePath("com.google.guava", "guava", "29.0-jre");
        var projectLayer = createLayer(List.of(parentLayer), List.of(moduleX, moduleY));
        System.out.println("Created layer B: " + projectLayer);
        return projectLayer;
    }

    private static ModuleLayer createLayer(List<ModuleLayer> parentLayers, List<Path> modulePaths) {
        final List<Configuration> parentConfs = parentLayers.stream().map((l) -> l.configuration()).toList();
        final ModuleFinder moduleFinder = ModuleFinder.of(modulePaths.toArray(new Path[modulePaths.size()]));
        final Set<ModuleReference> foundModuleReferences = moduleFinder.findAll();
        final Set<String> foundModuleNames = foundModuleReferences.stream()
                .map((r) -> r.descriptor().name())
                .collect(Collectors.toSet());
        var parentClassLoader = ClassLoader.getSystemClassLoader();
        Configuration cf = Configuration.resolveAndBind​(moduleFinder, parentConfs, ModuleFinder.of(), foundModuleNames);
        ModuleLayer.Controller controller =
                    ModuleLayer.defineModulesWithOneLoader(cf, parentLayers, parentClassLoader);
        return controller.layer();
    }

    private static Path resolveModulePath(String group, String artifact, String version) {
        //suppose it is linux
        String modulePath = group.replaceAll(Pattern.quote("."), File.separator);
        modulePath = modulePath
                + File.separator
                + artifact
                + File.separator
                + version
                + File.separator
                + artifact
                + "-"
                + version
                + ".jar";
        var resolvedPath = MAVEN_REPO_PATH.resolve(modulePath);
        System.out.println("Resolved path: " + resolvedPath);
        return resolvedPath;
    }


}
