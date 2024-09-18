package com.example.Gestiondocument.service;

import com.example.Gestiondocument.model.Matiere;
import com.example.Gestiondocument.repository.MatiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class MatiereService {

    @Autowired
    private MatiereRepository matiereRepository;

    public List<Matiere> getAllMatiere() {
        return matiereRepository.findAll();
    }

    public Optional<Matiere> findByName(String nom) {
        return matiereRepository.findByNom(nom);
    }

    public Matiere saveMatiere(Matiere matiere) {
        return matiereRepository.save(matiere);
    }

    public Matiere getMatiereById(Long id) {
        return matiereRepository.findById(id).orElse(null);
    }

    public Matiere findById(Long id) {
        return matiereRepository.findById(id).orElse(null); // Utilisez Optional pour gérer les cas où l'entité n'existe pas
    }

    public void deleteMatiere(Long id) {
        matiereRepository.deleteById(id);
    }
}
