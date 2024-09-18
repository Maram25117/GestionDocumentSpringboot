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

           @GetMapping("/matiere/{matiereId}/{fileType}")
           public List<File> getFilesByMatiereAndType(@PathVariable Long matiereId, @PathVariable String fileType) {
               return fileService.getFilesByMatiereAndType(matiereId, fileType);
           }

           @PostMapping("/upload")
           @PreAuthorize("hasRole('ADMIN')")
           public ResponseEntity<?> uploadFiles(
                   @RequestParam("files") MultipartFile[] files,
                   @RequestParam("matiereId") Long matiereId,
                   @RequestParam("type") String fileType) {
               try {
                   for (MultipartFile file : files) {
                       fileService.saveFile(file, matiereId, fileType);
                   }
                   return ResponseEntity.ok(new ResponseMessage("Fichiers téléchargés avec succès"));
               } catch (IOException e) {
                   e.printStackTrace();
                   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Erreur lors du téléversement des fichiers"));
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
           }

           @DeleteMapping("/{id}")
           @PreAuthorize("hasRole('ADMIN')")
           public ResponseEntity<?> deleteFile(@PathVariable Long id) {
               boolean deleted = fileService.deleteFile(id);
               if (deleted) {
                   return ResponseEntity.ok(new ResponseMessage("Fichier supprimé avec succès"));
               } else {
                   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Fichier non trouvé"));
               }
           }

           // ResponseMessage class for wrapping responses
           public static class ResponseMessage {
               private String message;

               public ResponseMessage(String message) {
                   this.message = message;
               }

               public String getMessage() {
                   return message;
               }

               public void setMessage(String message) {
                   this.message = message;
               }
           }
       }



