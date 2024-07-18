package com.ilc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ilc.model.Categoria;

public interface CategoriasRepository extends JpaRepository<Categoria, Integer> {
	
}
