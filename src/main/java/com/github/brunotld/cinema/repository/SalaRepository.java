package com.github.brunotld.cinema.repository;

import com.github.brunotld.cinema.model.Sala;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {

    List<Sala> findAll();

    Optional<Sala> findById(Long id);

    Sala save(Sala sala);

    boolean existsById(Long id);

    void deleteById(Long id);
}