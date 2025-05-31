package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rva.model.Aranzman;
import rva.model.Hotel;
import rva.model.TuristickaAgencija;

public interface AranzmanRepository extends JpaRepository<Aranzman, Long>{
	
	List<Aranzman> findByUkupnaCenaGreaterThanOrderById(int ukupnaCena);
	List<Aranzman> findByTuristickaAgencija(TuristickaAgencija turistickaAgencija);
	List<Aranzman> findByHotel(Hotel hotel);
}