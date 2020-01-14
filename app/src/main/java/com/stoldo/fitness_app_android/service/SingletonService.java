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
        return singletonObjects.get(singletonClass.getName());
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
                    Class clazz = classLoader.loadClass(className);
                    instantiateSingleton(clazz);
                }
            }
        }

        calledTimes++;
    }

    private void instantiateSingleton(Class clazz) throws Exception {
        if (clazz.isAnnotationPresent(Singleton.class)) {
            Constructor constructor = getConstructor(clazz);
            constructor.setAccessible(true);

            Object singletonInstance = null;

            if (constructor.getParameterCount() == 0) {
                singletonInstance = constructor.newInstance();
            } else {
                singletonInstance = constructor.newInstance(context);
            }

            singletonObjects.put(clazz.getName(), singletonInstance);
        }
    }

    private Constructor getConstructor(Class clazz) throws Exception {
        Constructor[] constructors = clazz.getDeclaredConstructors();

        if (constructors.length > 1) {
            throw new IllegalStateException("Annotated Class has multiple constructors!");
        }

        if (Modifier.isPublic(constructors[0].getModifiers())) {
            throw new InvalidClassException("Annotated Class has a public constructor, is therefore not a singleton!")   ;
        }

        if (constructors[0].getParameterCount() > 1) {
            throw new InvalidClassException("Annotated Class has more than one argument!")   ;
        }

        return constructors[0];
    }

    private boolean isClassInMyPackage(String className) {
        return StringUtils.startsWith(className, context.getPackageName()) // I want classes under my package
                && !StringUtils.contains(className, "$"); // I don't need none-static inner classes
    }
}
