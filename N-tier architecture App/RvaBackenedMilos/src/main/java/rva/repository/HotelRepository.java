package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rva.model.Destinacija;
import rva.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long>{
	
	List<Hotel> findByBrojZvezdicaGreaterThanOrderById(int brojZvezdica);
	List<Hotel> findByDestinacija(Destinacija destinacija);
}
