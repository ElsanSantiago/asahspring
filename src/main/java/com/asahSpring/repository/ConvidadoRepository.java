package com.asahSpring.repository;

import org.springframework.data.repository.CrudRepository;

import com.asahSpring.models.AsahSpring;
import com.asahSpring.models.Convidado;

public interface ConvidadoRepository extends CrudRepository<Convidado, String>{

	Iterable<Convidado> findByAsahSpring(AsahSpring asahsprig);
	Convidado findByRg(String rg);
}
