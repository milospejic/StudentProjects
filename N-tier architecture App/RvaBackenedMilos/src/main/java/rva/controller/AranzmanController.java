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

import rva.model.Aranzman;
import rva.service.AranzmanService;
import rva.service.TuristickaAgencijaService;
import rva.service.HotelService;

@RestController
@CrossOrigin
public class AranzmanController {
	
	@Autowired
	private AranzmanService service;
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private TuristickaAgencijaService turistickaAgencijaService;
	
	@GetMapping("/aranzman")
	public List<Aranzman> getAllAranzman(){
		return service.getAll();
	}
	
	@GetMapping("/aranzman/{id}")
	public ResponseEntity<?> getAranzmanById(@PathVariable long id){
		if(service.getById(id).isPresent()) {
			Aranzman aranzman = service.getById(id).get();
			return ResponseEntity.ok(aranzman);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource with requested id: " + id + " has not been found");
		}
	}
	
	@GetMapping("/aranzman/ukupnaCena/{ukupnaCena}")
	public ResponseEntity<?> getAranzmanByUkupnaCenaGreaterThan(@PathVariable int ukupnaCena){
		if(!service.getByUkupnaCena(ukupnaCena).get().isEmpty()) {
			return ResponseEntity.ok(service.getByUkupnaCena(ukupnaCena).get());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with ukupnaCena greater than : " + ukupnaCena + " has not been found");

		}
	}
	
	@GetMapping("/aranzman/turistickaAgencija/{id}")
	public ResponseEntity<?> getAranzmanByTuristickaAgencija(@PathVariable long id){
		if(!service.getAranzmanByTuristickaAgencija(turistickaAgencijaService.getById(id).get()).get().isEmpty()) {
			return ResponseEntity.ok(service.getAranzmanByTuristickaAgencija(turistickaAgencijaService.getById(id).get()).get());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with requested value of foreign key turistickaAgencija: " + id + " has not been found"); 
		}
	}
	
	@GetMapping("/aranzman/hotel/{id}")
	public ResponseEntity<?> getAranzmanByHotel(@PathVariable long id){
		if(!service.getAranzmanByHotel(hotelService.getById(id).get()).get().isEmpty()) {
			return ResponseEntity.ok(service.getAranzmanByHotel(hotelService.getById(id).get()).get());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with requested value of foreign key hotel: " + id + " has not been found"); 
		}
	}
	
	@PostMapping("aranzman")
	public ResponseEntity<Aranzman> createAranzman(@RequestBody Aranzman aranzman){
		Aranzman savedAranzman;
		
		if(!service.existsById(aranzman.getId())) {
			savedAranzman= service.addAranzman(aranzman);
		}else {
			List<Aranzman> lista = service.getAll();
			long najvecaVrednost = 1;
			for(int i = 0; i<lista.size();i++) {
				if(najvecaVrednost<= lista.get(i).getId()) {
					najvecaVrednost = lista.get(i).getId();
				}
				
				if(i == lista.size()-1) {
					najvecaVrednost++;
				}
			}
			
			aranzman.setId(najvecaVrednost);
			savedAranzman= service.addAranzman(aranzman);
		}
		
		
		
		URI uri = URI.create("/aranzman/" + savedAranzman.getId());
		return ResponseEntity.created(uri).body(savedAranzman);
	}
	
	@PutMapping("aranzman/{id}")
	public ResponseEntity<?> updateAranzman(@RequestBody Aranzman aranzman, @PathVariable long id){
		if(service.existsById(id)) {
			return ResponseEntity.ok(service.addAranzman(aranzman));
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with requested ID:  " + id + " has not been found"); 
		}
	}
	
	@DeleteMapping("aranzman/{id}")
	public ResponseEntity<?> deleteAranzman(@PathVariable long id){
		if(service.existsById(id)) {
			service.deleteById(id);
			return ResponseEntity.ok("Resource with ID: " + id + " has been deleted ");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with requested ID:  " + id + " has not been found"); 
		}
	}
}
