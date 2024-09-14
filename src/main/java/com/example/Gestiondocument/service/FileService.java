package com.example.Gestiondocument.service;

import com.example.Gestiondocument.model.File;
import com.example.Gestiondocument.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileService {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private FileRepository fileRepository;

    public FileService() {
        java.io.File uploadDir = new java.io.File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Utilisez mkdirs() pour créer les sous-répertoires si nécessaire
        }
    }

    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }

    public File saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Cannot save an empty file.");
        }

        String fileName = file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);

        // Assurez-vous que le répertoire existe
        Files.createDirectories(path.getParent());

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, path);
        } catch (IOException e) {
            throw new IOException("Failed to store file " + fileName, e);
        }

        File newFile = new File();
        newFile.setName(fileName);
        newFile.setPath(path.toString());
        return fileRepository.save(newFile);
    }

    public void deleteFile(Long id) {
        File file = getFile(id);
        if (file != null) {
            java.io.File fileToDelete = new java.io.File(file.getPath());
            if (fileToDelete.exists()) {
                fileToDelete.delete();
            }
            fileRepository.deleteById(id);
        }
    }

    public File getFile(Long id) {
        return fileRepository.findById(id).orElse(null);
    }
}

//ici on n'a pas utiliser @override car on n'a pas d'interface