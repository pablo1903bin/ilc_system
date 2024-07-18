package com.ilc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ilc.model.Solicitud;

public interface SolicitudesRepository extends JpaRepository<Solicitud, Integer> {

}
