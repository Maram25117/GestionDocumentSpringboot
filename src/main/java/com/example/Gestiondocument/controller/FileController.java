package com.example.Gestiondocument.controller;

import com.example.Gestiondocument.model.File;
import com.example.Gestiondocument.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping
    public List<File> getAllFiles() {
        return fileService.getAllFiles();
    } //affichage de tous les fichiers lors du chargement du page

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        try {
            for (MultipartFile file : files) {
                fileService.saveFile(file);
            }
            return ResponseEntity.ok("Fichiers téléchargés avec succès");
        } catch (IOException e) {
            // Log the exception for debugging
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du téléversement des fichiers");
        } catch (Exception e) {
            // Catch any other exceptions
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur inconnue lors du téléversement des fichiers");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<FileSystemResource> getFile(@PathVariable Long id) {
        File fileEntity = fileService.getFile(id);
        if (fileEntity != null) {
            FileSystemResource resource = new FileSystemResource(fileEntity.getPath());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=" + fileEntity.getName());
            return ResponseEntity.ok().headers(headers).body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    } //affichage du fichier lorsque on clique sur lui

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
        return ResponseEntity.ok("Fichier supprimé avec succès");
    }
}
