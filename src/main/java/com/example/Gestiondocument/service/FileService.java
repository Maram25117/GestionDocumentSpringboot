package com.example.Gestiondocument.service;

import com.example.Gestiondocument.model.File;
import com.example.Gestiondocument.model.Matiere;
import com.example.Gestiondocument.repository.FileRepository;
import com.example.Gestiondocument.repository.MatiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private MatiereRepository matiereRepository;

    public List<File> getFilesByMatiereAndType(Long matiereId, String fileType) {
        return fileRepository.findByMatiereIdAndType(matiereId, fileType);
    }

    public void saveFile(MultipartFile file, Long matiereId, String fileType) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR, matiereId.toString(), fileType);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        if (Files.exists(filePath)) {
            fileName = generateUniqueFileName(fileName);
            filePath = uploadPath.resolve(fileName);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Failed to save file " + fileName, e);
        }

        // Retrieve Matiere entity and set it in the File entity
        Optional<Matiere> matiereOpt = matiereRepository.findById(matiereId);
        if (matiereOpt.isPresent()) {
            File fileEntity = new File();
            fileEntity.setName(fileName);
            fileEntity.setPath(filePath.toString());
            fileEntity.setType(fileType);
            fileEntity.setMatiere(matiereOpt.get());
            fileRepository.save(fileEntity);
        } else {
            throw new IllegalArgumentException("Matiere with id " + matiereId + " not found");
        }
    }

    private String generateUniqueFileName(String originalFileName) {
        String fileNameWithoutExt = FilenameUtils.getBaseName(originalFileName);
        String extension = FilenameUtils.getExtension(originalFileName);
        return fileNameWithoutExt + "_" + System.currentTimeMillis() + "." + extension;
    }

    public File getFile(Long id) {
        Optional<File> fileOpt = fileRepository.findById(id);
        return fileOpt.orElse(null);
    }

    @Transactional
    public boolean deleteFile(Long id) {
        Optional<File> fileOpt = fileRepository.findById(id);
        if (fileOpt.isPresent()) {
            File file = fileOpt.get();
            try {
                Files.delete(Paths.get(file.getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileRepository.delete(file);
            return true;
        }
        return false;
    }
}
