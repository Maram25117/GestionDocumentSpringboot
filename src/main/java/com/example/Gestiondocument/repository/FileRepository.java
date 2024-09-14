package com.example.Gestiondocument.repository;

import com.example.Gestiondocument.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
