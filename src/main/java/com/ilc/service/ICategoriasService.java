package com.ilc.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ilc.model.Categoria;

public interface ICategoriasService {
	void guardar(Categoria categoria);
	void eliminar(Integer idCategoria);
	List<Categoria> buscarTodas();
	Categoria buscarPorId(Integer idCategoria);
	Page<Categoria> buscarTodas(Pageable page);
}
