package com.example.Gestiondocument.repository;

import com.example.Gestiondocument.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByMatiereIdAndType(Long matiereId , String fileType);
}
