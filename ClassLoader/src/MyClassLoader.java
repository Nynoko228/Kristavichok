import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyClassLoader extends ClassLoader{
    private final String[] classPaths;
    public MyClassLoader(String[] classPaths){
        this.classPaths = classPaths;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException{
        byte[] classBytes = null;

        // Проходим по путям, пытаясь найти класс
        for (String path : classPaths) {
            try {
                System.out.println(path);
                // Формируем путь к классу
                String classFilePath = path + File.separator + name.replace('.', File.separatorChar) + ".class";
                System.out.println(classFilePath);
                if (Files.exists(Paths.get(classFilePath))) {
                    try (InputStream inputStream = new FileInputStream(classFilePath)) {
                        classBytes = inputStream.readAllBytes();
                    }
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (classBytes == null) {
            throw new ClassNotFoundException("Class " + name + " not found");
        }

        // Определяем класс из массива байт
        return defineClass(name, classBytes, 0, classBytes.length);
    }
}
