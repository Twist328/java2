package ru.progwards.java2.lessons.less11.starter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.SecureRandom;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleAutoLoader extends ClassLoader {
    final static String PATH_OF_TASKS = "c:/data/";
    final static String DOT_CLASS = ".class";
    private static SimpleAutoLoader loader = new SimpleAutoLoader(PATH_OF_TASKS);

    private final String basePath;

    public SimpleAutoLoader(String basePath) {
        this(basePath, ClassLoader.getSystemClassLoader());
    }

    public SimpleAutoLoader(String basePath, ClassLoader parent) {
        super(parent);
        this.basePath = basePath;
    }

    @Override
    public Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            String classPath = className.replace(".", "/");
            Path classPathName = Paths.get(basePath + classPath + DOT_CLASS);
            if (Files.exists(classPathName)) {
                byte b[] = Files.readAllBytes(classPathName);
                return defineClass(className, b, 0, b.length);
            } else {
                return findSystemClass(className);
            }
        } catch (IOException ex) {
            throw new ClassNotFoundException(className);
        }
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        Class<?> c = findClass(name);
        if (resolve)
            resolveClass(c);
        return c;
    }

    private static String makeClassName(Path path) throws IOException {
        path = path.toAbsolutePath().toRealPath();
        Path relPath = Paths.get(PATH_OF_TASKS).relativize(path);
        String className = relPath.toString().replaceAll("[\\/\\\\]", ".");
        if (className.toLowerCase().endsWith(DOT_CLASS))
            className = className.substring(0, className.length() - DOT_CLASS.length());

        return className;
    }

    private static void updateTaskList(Map<String, Task> tasks)
            throws IOException {
        Files.walkFileTree(Paths.get(PATH_OF_TASKS), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                if (path.toString().endsWith(DOT_CLASS)) {
                    String className = makeClassName(path);
                    Task task = tasks.get(className);
                    if (task == null ) {
                        try {
                            Class taskClass = loader.loadClass(className, true);
                            Task newTask = (Task)taskClass.getDeclaredConstructor().newInstance();
                            tasks.put(className, newTask);
                            System.out.println("Добавлен класс " + className);
                        } catch (ClassNotFoundException |
                                IllegalAccessException |
                                InstantiationException |
                                NoSuchMethodException |
                                InvocationTargetException e) {
                            e.printStackTrace();
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

    public static void main(String[] args) throws Exception {
        Map<String, Task> tasks = new LinkedHashMap<>();

        while(true) {
            System.out.println("\n");
            System.out.println("Проверка классов и запуск задач: " +
                    String.format("%1$tI:%1$tM:%1$tS.%1$tN", new Date()));

            updateTaskList(tasks);

            SecureRandom random = new SecureRandom();
            byte[] data = new byte[1000];
            random.nextBytes(data);

            for (var task : tasks.entrySet())
                System.out.println("     " + task.getValue().process(data));

            Thread.sleep(5_000);
        }
    }
}

