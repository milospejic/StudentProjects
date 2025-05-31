package rva.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rva.model.Destinacija;
import rva.repository.DestinacijaRepository;

@Service
public class DestinacijaService {
	@Autowired
	private DestinacijaRepository repo;
	
	
	public List<Destinacija> getAll(){
		return repo.findAll();
	}
	
	public Optional<Destinacija> getById(long id){
		return repo.findById(id);
	}
	
	public Optional<List<Destinacija>> getByMesto(String mesto){
		Optional<List<Destinacija>> destinacije = Optional.of(repo.findByMestoContainingIgnoreCase(mesto));
		return destinacije;
	}
	
	public Optional<List<Destinacija>> getByDrzava(String drzava){
		Optional<List<Destinacija>> destinacije = Optional.of(repo.findByDrzavaContainingIgnoreCase(drzava));
		return destinacije;
	}
	
	public Destinacija addDestinacija(Destinacija destinacija) {
		return repo.save(destinacija);
	}
	
	public boolean existsById(long id) {
		return getById(id).isPresent();
	}
	
	public void deleteById(long id) {
		repo.deleteById(id);
	}
}
