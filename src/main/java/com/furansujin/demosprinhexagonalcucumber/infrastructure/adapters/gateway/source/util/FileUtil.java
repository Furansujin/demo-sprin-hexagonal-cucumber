package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.source.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static boolean deleteAndRecreateDirectory(String directoryPath) {
        try {
            // Créez une instance File pour le dossier
            File directory = new File(directoryPath);

            // Si le dossier existe, supprimez-le
            if (directory.exists()) {
                deleteDirectory(directory);
            }

            // Recréez le dossier
            return directory.mkdirs();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode récursive pour supprimer un dossier et son contenu
    private static boolean deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            String[] children = directory.list();
            for (String child : children) {
                boolean success = deleteDirectory(new File(directory, child));
                if (!success) {
                    return false;
                }
            }
        }

        return directory.delete();
    }

    public static List<File> getFilesInDirectory(String directoryPath) {
        List<File> fileList = new ArrayList<>();
        File directory = new File(directoryPath);

        // Vérifiez si le chemin donné est un dossier.
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        fileList.add(file);
                    }
                }
            }
        } else {
            System.out.println(directoryPath + " n'est pas un dossier valide.");
        }

        return fileList;
    }



    public static void copyDirectory(String sourcePath, String targetPath) throws IOException {
        Path source = Paths.get(sourcePath);
        Path target = Paths.get(targetPath);
        // Si le dossier source n'existe pas, lancez une exception
        if (!Files.exists(source) || !Files.isDirectory(source)) {
            throw new IOException("Source directory does not exist: " + source);
        }

        // Si le dossier cible n'existe pas, créez-le
        if (!Files.exists(target)) {
            Files.createDirectories(target);
        }

        // Copiez le dossier source vers le dossier cible
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path targetDir = target.resolve(source.relativize(dir));
                if (!Files.exists(targetDir)) {
                    Files.createDirectory(targetDir);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.copy(file, target.resolve(source.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}