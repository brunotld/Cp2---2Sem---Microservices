package com.github.brunotld.cinema.controller;

import com.github.brunotld.cinema.model.Sala;
import com.github.brunotld.cinema.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    private SalaRepository salaRepository;

    @GetMapping
    public List<Sala> listarSalas() {
        return salaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sala> buscarPorId(@PathVariable Long id) {
        Optional<Sala> sala = salaRepository.findById(id);
        return sala.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Sala criarSala(@RequestBody Sala sala) {
        return salaRepository.save(sala);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sala> atualizarSala(@PathVariable Long id, @RequestBody Sala dados) {
        return salaRepository.findById(id).map(sala -> {
            sala.setNome(dados.getNome());
            sala.setTipo(dados.getTipo());
            sala.setCapacidade(dados.getCapacidade());
            sala.setTresD(dados.getTresD());
            sala.setObservacao(dados.getObservacao());
            return ResponseEntity.ok(salaRepository.save(sala));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSala(@PathVariable Long id) {
        if (!salaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        salaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}