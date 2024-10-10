import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void CreateZip(String inputPath, String outputZipPath) throws IOException {
        Path sourcePath = Paths.get(inputPath);
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(outputZipPath))) {
            if (Files.isDirectory(sourcePath)) {
                // Если это папка, упаковываем все ее содержимое
                Files.walk(sourcePath).forEach(path -> {
                    String zipEntryName = sourcePath.relativize(path).toString().replace("\\", "/");
                    try {
                        if (Files.isDirectory(path)) {
                            // Для папок добавляем запись с "/"
                            if (!zipEntryName.endsWith("/")) {
                                zipEntryName += "/";
                            }
                            zout.putNextEntry(new ZipEntry(zipEntryName));
                            zout.closeEntry();
                        } else {
                            // Для файлов
                            zout.putNextEntry(new ZipEntry(zipEntryName));
                            Files.copy(path, zout);
                            zout.closeEntry();
                        }
                    } catch (IOException e) {
                        System.err.println("Ошибка при добавлении в архив: " + path + " -> " + e.getMessage());
                    }
                });
            } else if (Files.isRegularFile(sourcePath)) {
                // Если это отдельный файл, просто добавляем его в архив
                zout.putNextEntry(new ZipEntry(sourcePath.getFileName().toString()));
                Files.copy(sourcePath, zout);
                zout.closeEntry();
            } else {
                System.err.println("Ошибка: Указанный путь не является файлом или директорией.");
            }
        }
    }
    public static void ReadZip(String inputZipPath, String outputPath) throws IOException {
        File base = new File(outputPath);
        // Создаём корневую директорию, если она не существует
        if (!base.exists()) {
            base.mkdirs();
        }

        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(inputZipPath))) {
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                File outputFile = new File(base, entry.getName());

                // Если это директория, создаем её
                if (entry.isDirectory()) {
                    outputFile.mkdirs(); // Создаём все необходимые вложенные директории
                } else {
                    // Убедимся, что родительские директории существуют
                    outputFile.getParentFile().mkdirs();

                    // Записываем файл
                    try (FileOutputStream fout = new FileOutputStream(outputFile)) {
                        for (int c = zin.read(); c != -1; c = zin.read()) {
                            fout.write(c);
                        }
                    }
                }
                zin.closeEntry();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String command = args[0];
        String path_to_zip = args[1];
        String path_to_file = args[2];
        try {
            if (Objects.equals(command, "pack")) {
                CreateZip(path_to_file, path_to_zip);
            }
            else {
                ReadZip(path_to_zip, path_to_file);
            }
        }
        catch (Exception e){
            System.out.println("Некорректный ввод");
            System.out.println(e);
        }
    }
}