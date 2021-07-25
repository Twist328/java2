package ru.progwards.java2.lessons.classloader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class PathLoader extends ClassLoader {
    final static String PATH_MAIN = "C:\\Projects\\java2\\src\\main\\resources\\users.json";
    final static String DOT_CLASS = ".class";
    private static PathLoader loader = new PathLoader(PATH_MAIN);

    private final String basePath;
    private static String date;

    public PathLoader(String basePath) {
        this(basePath, ClassLoader.getSystemClassLoader());
    }

    public PathLoader(String basePath, ClassLoader parent) {
        super(parent);
        this.basePath = basePath;
    }

    @Override
    public Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            String classPath = className.replace(".", "\\");
            Path classPathName = Paths.get(basePath + date + "\\" + classPath + DOT_CLASS);
            System.out.println(1);
            System.out.println(classPathName.toString());
            if (Files.exists(classPathName)) {
                System.out.println("dsdsds");
                byte[] b = Files.readAllBytes(classPathName);
                System.out.println(Arrays.toString(b));
                System.out.println(defineClass(className, b, 0, b.length).getSimpleName());
                return defineClass(className, b, 0, b.length);
            } else
                return null;
        } catch (IOException ex) {
            throw new ClassNotFoundException(className);
        }
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> c = findClass(name);
        if (resolve)
            resolveClass(c);
        return c;
    }

    private static void updateTaskList(Map<String, TestLoader> tasks) throws IOException {
        Files.walkFileTree(Paths.get(PATH_MAIN), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                if (path.toString().endsWith(DOT_CLASS)) {
                    String classNameWithDate = makeClassNameWithDate(path);
                    date = makeDateName(classNameWithDate);
                    String className = classNameWithDate.substring(date.length()+1, classNameWithDate.length());
                    TestLoader task = tasks.get(className);
                    if (task == null || task.getModifiedTime() != attrs.lastModifiedTime().toMillis()) {
                        try {
                            if (task != null)
                                loader = new PathLoader(PATH_MAIN);
                            Class<?> taskClass = loader.loadClass(className, true);
                            System.out.println("dsdsds " + taskClass.getSimpleName());
                            TestLoader newTask = (TestLoader) taskClass.getConstructor().newInstance();
                            newTask.setModifiedTime(attrs.lastModifiedTime().toMillis());
                            System.out.println(2);
                            tasks.put(className, newTask);
                            System.out.println((task == null ? "Добавлен" : "Обновлен") + " класс " + className);
                        } catch (ClassNotFoundException | NoSuchMethodException |
                                InstantiationException | IllegalAccessException |
                                InvocationTargetException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException e) {
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static String makeDateName(String className) {
        return className.split("[.]")[0];
    }

    private static Path makeRelPath(Path path) {
        path = path.toAbsolutePath();
        return Paths.get(PATH_MAIN).relativize(path);
    }

    private static String makeClassNameWithDate(Path path) {
        Path relPath = makeRelPath(path);
        String className = relPath.toString().replace("\\", ".");
        return className.substring(0, className.length() - DOT_CLASS.length());
    }

    public static void main(String[] args) throws IOException {
        try {
            Class<?> claZZ = Class.forName("ru.progwards.java2.lessons.classloader.TestLoader");
            TestLoader newTask = (TestLoader) claZZ.getDeclaredConstructor().newInstance();
            newTask.printString();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
               InvocationTargetException ex) {
            ex.printStackTrace();
        }

        Map<String, TestLoader> map = new LinkedHashMap<>();

        while(true) {
            updateTaskList(map);

            for (var task : map.entrySet()) {
                task.getValue().printString();
            }

            try {
                Thread.sleep(7_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

