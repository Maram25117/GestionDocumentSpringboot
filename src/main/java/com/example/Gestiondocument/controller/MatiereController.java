package com.example.Gestiondocument.controller;

import com.example.Gestiondocument.model.Matiere;
import com.example.Gestiondocument.service.MatiereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import java.util.Map;
import java.util.HashMap;

import java.util.List;

@RestController
@RequestMapping("/matiere")
public class MatiereController {

    @Autowired
    private MatiereService matiereService;

    @GetMapping
    public List<Matiere> getAllMatieres() {
        return matiereService.getAllMatiere();
    }

    /*@PostMapping("/add")
    public ResponseEntity<Matiere> addMatiere(@RequestBody Matiere matiere) {
        Matiere newMatiere = matiereService.saveMatiere(matiere);
        return ResponseEntity.ok(newMatiere);
    }*/
    @PostMapping("/add")
    public ResponseEntity<?> addMatiere(@RequestBody Matiere matiere) {
        Optional<Matiere> existingMatiere = matiereService.findByName(matiere.getNom());

        if (existingMatiere.isPresent()) {
            // Si la matière existe déjà, renvoie un message avec un statut 409
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Matière existe déjà"));
        }

        // Si elle n'existe pas, on l'ajoute et on renvoie la nouvelle matière
        Matiere newMatiere = matiereService.saveMatiere(matiere);
        return ResponseEntity.ok(Map.of("message", "Matière ajoutée avec succès", "matiere", newMatiere));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Matiere> getMatiereById(@PathVariable Long id) {
        Matiere matiere = matiereService.getMatiereById(id);
        if (matiere != null) {
            return ResponseEntity.ok(matiere);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/matiere/{id}")
    public ResponseEntity<Matiere> getMatieresById(@PathVariable Long id) {
        Matiere matiere = matiereService.findById(id); // Implémentez cette méthode dans votre service
        if (matiere != null) {
            return ResponseEntity.ok(matiere);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMatiere(@PathVariable Long id) {
        matiereService.deleteMatiere(id);
        return ResponseEntity.ok("Matière supprimée avec succès.");
    }
}
