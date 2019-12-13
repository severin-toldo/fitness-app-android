package com.stoldo.fitness_app_android.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

// ignore and dont use for now
// TODO not used right now, do we need it anyway?
// TODO can mybe converted and merged with json util to object util? or reused in service
public class CloneUtil {
//    public static Object deepClone(Object obj) throws IOException, ClassNotFoundException {
//        return deserializableObject(serializableObject(obj));
//    }

//    public static List<?> cloneArrayList(List<?> list) {
//        return new ArrayList<>(list);
//    }

    public static <T> List<T> cloneArrayList(List<T> list) {
        return new ArrayList<>(list);
    }

    private static byte[] serializableObject(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        oos.close();
        bos.close();

        return bos.toByteArray();
    }

    private static Object deserializableObject(byte[] byteArray) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
        return (Object) new ObjectInputStream(bais).readObject();
    }
}
