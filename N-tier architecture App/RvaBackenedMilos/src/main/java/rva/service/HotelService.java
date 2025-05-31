package rva.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rva.model.Destinacija;
import rva.model.Hotel;
import rva.repository.HotelRepository;

@Service
public class HotelService {
	
	@Autowired
	private HotelRepository repo;
	
	public List<Hotel> getAll(){
		return repo.findAll();
	}

	
	public Optional<Hotel> getById(long id){
		return repo.findById(id);
	}
	
	public Optional<List<Hotel>> getByBrojZvezdica(int broj_zvezdica){
		Optional<List<Hotel>> lista = Optional.of(repo.findByBrojZvezdicaGreaterThanOrderById(broj_zvezdica));
		return lista;
	}
	
	public Optional<List<Hotel>> getHotelByDestinacija(Destinacija destinacija){
		Optional<List<Hotel>> lista = Optional.of(repo.findByDestinacija(destinacija));
		return lista;
	}
	
	public Hotel addHotel(Hotel hotel){
		return repo.save(hotel);
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
