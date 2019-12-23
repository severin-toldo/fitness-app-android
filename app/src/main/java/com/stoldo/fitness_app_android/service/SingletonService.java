package com.stoldo.fitness_app_android.service;

import android.content.Context;

import com.stoldo.fitness_app_android.model.annotaions.Singleton;

import org.apache.commons.lang3.StringUtils;

import java.io.InvalidClassException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

/**
 * Usage example (after first time instantion -> for this see in MainActivity):
 *
 * SingletonService s = SingletonService.getInstance(null);
 * Object someService = s.getSingletonByClass(SomeService.class);
 *
 * if (service instanceof TestService) {
 *      ((SomeService) service).callSomeMethod();
 * }
 * */
public class SingletonService {
    private static SingletonService instance;

    private int calledTimes = 0;
    private Map<String, Object> singletonObjects = new HashMap<>(); // key = class name, value = object of class (instance)
    private Context context;

    private SingletonService(Context context) {
        this.context = context;
    }

    /**
     * caution: if called more than once, the new context will be ignored!
     * */
    public static SingletonService getInstance(Context context) {
        if (instance == null) {
            synchronized (SingletonService.class) {
                if(instance == null) {
                    instance = new SingletonService(context);
                }
            }
        }

        return instance;
    }

    public Object getSingletonByClass(Class singletonClass) {
        if (singletonClass.isAnnotationPresent(Singleton.class)) {
            return singletonObjects.get(singletonClass.getName());
        } else {
            throw new IllegalArgumentException("Passed class is not annotated with @Singleton!");
        }
    }

    /**
     * Source: https://stackoverflow.com/questions/15446036/find-all-classes-in-a-package-in-android
     * */
    public void instantiateSingletons() throws Exception {
        if (calledTimes == 0) {
            PathClassLoader classLoader = (PathClassLoader) context.getClassLoader();
            DexFile dexFile = new DexFile(context.getPackageCodePath());
            Enumeration<String> classNames = dexFile.entries();

            while (classNames.hasMoreElements()) {
                String className = classNames.nextElement();

                if (isClassInMyPackage(className)) {
                    Class<?> clazz = classLoader.loadClass(className);

                    if (clazz.isAnnotationPresent(Singleton.class)) {
                        Constructor constructor = clazz.getDeclaredConstructor();

                        if (Modifier.isPublic(constructor.getModifiers())) {
                            throw new InvalidClassException("Annotated Class has a public constructor, is therefore not a singleton!")   ;
                        }

                        constructor.setAccessible(true);
                        Object singletonInstance = constructor.newInstance();
                        singletonObjects.put(clazz.getName(), singletonInstance);
                    }
                }
            }
        }

        calledTimes++;
    }

    private boolean isClassInMyPackage(String className) {
        return StringUtils.startsWith(className, context.getPackageName()) // I want classes under my package
                && !StringUtils.contains(className, "$"); // I don't need none-static inner classes
    }
}
