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

import rva.model.TuristickaAgencija;
import rva.service.TuristickaAgencijaService;

@RestController
@CrossOrigin
public class TuristickaAgencijaController {

	@Autowired
	private TuristickaAgencijaService service;
	
	@GetMapping("/turistickaAgencija")
	public List<TuristickaAgencija> getAllTuristickaAgencija(){
		return service.getAll();
	}
	
	@GetMapping("/turistickaAgencija/{id}")
	public ResponseEntity<?> getTuristickaAgencijaById(@PathVariable long id) {
		if(service.existsById(id)) {
			return ResponseEntity.ok(service.getById(id).get());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Requested resource does not exist");
		}
	}
	
	@GetMapping("/turistickaAgencija/naziv/{naziv}")
	public ResponseEntity<?> getTuristickaAgencijaByNaziv(@PathVariable String naziv) {
		List<TuristickaAgencija> turistickeAgencije= service.getByNaziv(naziv).get();
		if(!turistickeAgencije.isEmpty()) {
			return ResponseEntity.ok(turistickeAgencije);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Resources with requested naziv:" + naziv+ " are not found");
		}
	}
	
	
	@PostMapping("/turistickaAgencija")
	public ResponseEntity<?> createTuristickaAgencija(@RequestBody TuristickaAgencija turistickaAgencija){
		TuristickaAgencija savedTuristickaAgencija;
		
		if(!service.existsById(turistickaAgencija.getId())) {
			savedTuristickaAgencija= service.addTuristickaAgencija(turistickaAgencija);
		}else {
			List<TuristickaAgencija> lista = service.getAll();
			long najvecaVrednost = 1;
			for(int i = 0; i<lista.size();i++) {
				if(najvecaVrednost<= lista.get(i).getId()) {
					najvecaVrednost = lista.get(i).getId();
				}
				
				if(i == lista.size()-1) {
					najvecaVrednost++;
				}
			}
			
			turistickaAgencija.setId(najvecaVrednost);
			savedTuristickaAgencija= service.addTuristickaAgencija(turistickaAgencija);
		}
		
		
		
		URI uri = URI.create("/turistickaAgencija/" + savedTuristickaAgencija.getId());
		return ResponseEntity.created(uri).body(savedTuristickaAgencija);
	}
	
	
	@PutMapping("/turistickaAgencija/{id}")
	public ResponseEntity<?> updateTuristickaAgencija(@RequestBody TuristickaAgencija turistickaAgencija, @PathVariable long id){
		if(service.existsById(turistickaAgencija.getId())) {
			TuristickaAgencija savedTuristickaAgencija= service.addTuristickaAgencija(turistickaAgencija);
			return ResponseEntity.ok(savedTuristickaAgencija);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Resource with requested id: " + id + " has not been found"); 
		}
		
		
	}
	
	@DeleteMapping("/turistickaAgencija/{id}")
	public ResponseEntity<String> deleteTuristickaAgencija(@PathVariable long id){
		if(service.existsById(id)) {
			service.deleteById(id);
			return ResponseEntity.ok("Resource with requested id: " + id + " has been deleted");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Resource with requested id: " + id + " has not been found"); 
		}
	}
}
