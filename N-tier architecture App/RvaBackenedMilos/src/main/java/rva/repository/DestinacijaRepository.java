package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rva.model.Destinacija;

	public interface DestinacijaRepository extends JpaRepository<Destinacija,Long>{

		List<Destinacija> findByMestoContainingIgnoreCase(String mesto);
		List<Destinacija> findByDrzavaContainingIgnoreCase(String drzava);
	}
