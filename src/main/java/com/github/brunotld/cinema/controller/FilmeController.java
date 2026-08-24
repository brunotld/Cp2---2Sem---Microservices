package com.github.brunotld.cinema.controller;

import com.github.brunotld.cinema.model.Filme;
import com.github.brunotld.cinema.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeRepository filmeRepository;

    @GetMapping
    public List<Filme> listarFilmes() {
        return filmeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filme> buscarPorId(@PathVariable Long id) {
        Optional<Filme> filme = filmeRepository.findById(id);
        return filme.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Filme criarFilme(@RequestBody Filme filme) {
        return filmeRepository.save(filme);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filme> atualizarFilme(@PathVariable Long id, @RequestBody Filme dados) {
        return filmeRepository.findById(id).map(filme -> {
            filme.setTitulo(dados.getTitulo());
            filme.setGenero(dados.getGenero());
            filme.setDuracaoMinutos(dados.getDuracaoMinutos());
            filme.setClassificacaoEtaria(dados.getClassificacaoEtaria());
            filme.setSinopse(dados.getSinopse());
            return ResponseEntity.ok(filmeRepository.save(filme));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFilme(@PathVariable Long id) {
        if (!filmeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        filmeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}