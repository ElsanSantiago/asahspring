package com.asahSpring.repository;

import org.springframework.data.repository.CrudRepository;

import com.asahSpring.models.AsahSpring;

public interface AsahSpringRepository extends CrudRepository<AsahSpring, String>{
	AsahSpring findByCodigo(long codigo);
}
