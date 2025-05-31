package rva.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rva.model.Aranzman;
import rva.model.TuristickaAgencija;
import rva.model.Hotel;
import rva.repository.AranzmanRepository;


@Service
public class AranzmanService {
	@Autowired
	private AranzmanRepository repo;
	
	public List<Aranzman> getAll(){
		return repo.findAll();
	}

	
	public Optional<Aranzman> getById(long id){
		return repo.findById(id);
	}
	
	public Optional<List<Aranzman>> getByUkupnaCena(int ukupnaCena){
		Optional<List<Aranzman>> lista = Optional.of(repo.findByUkupnaCenaGreaterThanOrderById(ukupnaCena));
		return lista;
	}
	
	public Optional<List<Aranzman>> getAranzmanByHotel(Hotel hotel){
		Optional<List<Aranzman>> lista = Optional.of(repo.findByHotel(hotel));
		return lista;
	}
	
	public Optional<List<Aranzman>> getAranzmanByTuristickaAgencija(TuristickaAgencija turistickaAgencija){
		Optional<List<Aranzman>> lista = Optional.of(repo.findByTuristickaAgencija(turistickaAgencija));
		return lista;
	}
	
	public Aranzman addAranzman(Aranzman aranzman){
		return repo.save(aranzman);
	}
	
	public boolean existsById(long id){
		if(getById(id).isPresent()) {
			return true;
		}else {
			return false;
		}
	}
	
	public void deleteById(long id) {
		repo.deleteById(id);
	}
}
