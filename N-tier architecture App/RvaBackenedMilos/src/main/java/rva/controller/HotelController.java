package rva.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rva.model.Hotel;
import rva.service.DestinacijaService;
import rva.service.HotelService;

@RestController
@CrossOrigin
public class HotelController {
	
	@Autowired
	private HotelService service;
	
	@Autowired
	private DestinacijaService destinacijaService;
	
	@GetMapping("/hotel")
	public List<Hotel> getAllHotel(){
		return service.getAll();
	}
	
	@GetMapping("/hotel/{id}")
	public ResponseEntity<?> getHotelById(@PathVariable long id){
		if(service.getById(id).isPresent()) {
			Hotel hotel = service.getById(id).get();
			return ResponseEntity.ok(hotel);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource with requested id: " + id + " has not been found");
		}
	}
	
	@GetMapping("/hotel/brojZvezdica/{brojZvezdica}")
	public ResponseEntity<?> getHotelByBrojZvezdicaGreaterThan(@PathVariable int brojZvezdica){
		if(!service.getByBrojZvezdica(brojZvezdica).get().isEmpty()) {
			return ResponseEntity.ok(service.getByBrojZvezdica(brojZvezdica).get());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with broj_zvezdica greater than : " + brojZvezdica + " has not been found");

		}
	}
	
	@GetMapping("/hotel/destinacija/{id}")
	public ResponseEntity<?> getHotelByDestinacija(@PathVariable long id){
		if(!service.getHotelByDestinacija(destinacijaService.getById(id).get()).get().isEmpty()) {
			return ResponseEntity.ok(service.getHotelByDestinacija(destinacijaService.getById(id).get()).get());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with requested value of foreign key destinacija: " + id + " has not been found"); 
		}
	}
	
	@PostMapping("hotel")
	public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
		Hotel savedHotel;
		
		if(!service.existsById(hotel.getId())) {
			savedHotel= service.addHotel(hotel);
		}else {
			List<Hotel> lista = service.getAll();
			long najvecaVrednost = 1;
			for(int i = 0; i<lista.size();i++) {
				if(najvecaVrednost<= lista.get(i).getId()) {
					najvecaVrednost = lista.get(i).getId();
				}
				
				if(i == lista.size()-1) {
					najvecaVrednost++;
				}
			}
			
			hotel.setId(najvecaVrednost);
			savedHotel= service.addHotel(hotel);
		}
		
		
		
		URI uri = URI.create("/hotel/" + savedHotel.getId());
		return ResponseEntity.created(uri).body(savedHotel);
	}
	
	@PutMapping("hotel/{id}")
	public ResponseEntity<?> updateHotel(@RequestBody Hotel hotel, @PathVariable long id){
		if(service.existsById(id)) {
			return ResponseEntity.ok(service.addHotel(hotel));
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with requested ID:  " + id + " has not been found"); 
		}
	}
	
	@DeleteMapping("hotel/{id}")
	public ResponseEntity<?> deleteHotel(@PathVariable long id){
		if(service.existsById(id)) {
			service.deleteById(id);
			return ResponseEntity.ok("Resource with ID: " + id + " has been deleted ");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with requested ID:  " + id + " has not been found"); 
		}
	}
	

}
