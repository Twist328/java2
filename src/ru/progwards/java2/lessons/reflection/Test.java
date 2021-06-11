package ru.progwards.java2.lessons.reflection;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

public class Test {
    static void employee() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Class clazz = Class.forName("ru.progwards.java2.lessons.reflect.Employee");
        System.out.println(clazz.getPackage());
        int m = clazz.getModifiers();
        System.out.println(Modifier.toString(m));
        System.out.println(clazz.getSuperclass());
        Class[] interfaces = clazz.getInterfaces();
        for(Class i: interfaces) {
            System.out.println(i);
        }
        Constructor[] constr = clazz.getDeclaredConstructors();
        for(Constructor c: constr) {
            System.out.println(c);
        }
        Constructor constructor = clazz.getDeclaredConstructor(String.class, int.class);
        constructor.setAccessible(true);
        System.out.println(Modifier.toString(constructor.getModifiers()));
        Object employee = constructor.newInstance("Петя", 33);
        System.out.println(employee);
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        System.out.println(name.get(employee));
        name.set(employee, "Коля");
        System.out.println(name.get(employee));
        Method salary = clazz.getMethod("increaseSalary", int.class);
        salary.invoke(employee, 100000);
        System.out.println(employee);
        Field label = clazz.getField("LABEL");
        System.out.println(label.get(employee));
        System.out.println(Modifier.toString(label.getModifiers()));
    }

    public List<String> list;
    static void generic() throws NoSuchFieldException {
        Class clazz = Test.class;
        Field list = clazz.getField("list");
        Type tl = list.getGenericType();
        if (tl instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)tl;
            Type[] args = pt.getActualTypeArguments();
            System.out.println(Arrays.toString(args));
        }
    }

    public int[] intarr;
    public String[] strarr;
    public long[][] lonarr;
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Class clazz = Test.class;
        Field intarr = clazz.getField("intarr");
        System.out.println(intarr.getType());
        Class ca = Class.forName("[I");
        Field strarr = clazz.getField("strarr");
        System.out.println(strarr.getType());
        Field lonarr = clazz.getField("lonarr");
        System.out.println(lonarr.getType());
    }
}

