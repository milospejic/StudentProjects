package rva.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rva.model.TuristickaAgencija;
import rva.repository.TuristickaAgencijaRepository;

@Service
public class TuristickaAgencijaService {

	@Autowired
	private TuristickaAgencijaRepository repo;
	
	
	public List<TuristickaAgencija> getAll(){
		return repo.findAll();
	}
	
	public Optional<TuristickaAgencija> getById(long id){
		return repo.findById(id);
	}
	
	public Optional<List<TuristickaAgencija>> getByNaziv(String naziv){
		Optional<List<TuristickaAgencija>> turistickaAgencije = Optional.of(repo.findByNazivContainingIgnoreCase(naziv));
		return turistickaAgencije;
	}
	
	public TuristickaAgencija addTuristickaAgencija(TuristickaAgencija turistickaAgencija) {
		return repo.save(turistickaAgencija);
	}
	
	public boolean existsById(long id) {
		return getById(id).isPresent();
	}
	
	public void deleteById(long id) {
		repo.deleteById(id);
	}
}
