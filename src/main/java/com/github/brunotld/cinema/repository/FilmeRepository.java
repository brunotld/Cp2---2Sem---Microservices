package com.github.brunotld.cinema.repository;

import com.github.brunotld.cinema.model.Filme;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmeRepository extends JpaRepository<Filme, Long> {

    Optional<Filme> findById(Long id);

    List<Filme> findAll();

    Filme save(Filme filme);

    void deleteById(Long id);

    boolean existsById(Long id);
}