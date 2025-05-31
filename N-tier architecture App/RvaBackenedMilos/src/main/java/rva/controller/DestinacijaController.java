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

import rva.model.Destinacija;
import rva.service.DestinacijaService;

@RestController
@CrossOrigin
public class DestinacijaController {

	@Autowired
	private DestinacijaService service;
	
	@GetMapping("/destinacija")
	public List<Destinacija> getAllDestinacije(){
		return service.getAll();
	}
	
	@GetMapping("/destinacija/{id}")
	public ResponseEntity<?> getDestinacijaById(@PathVariable long id) {
		if(service.existsById(id)) {
			return ResponseEntity.ok(service.getById(id).get());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Requested resource does not exist");
		}
	}
	
	@GetMapping("/destinacija/mesto/{mesto}")
	public ResponseEntity<?> getDestinacijaByMesto(@PathVariable String mesto) {
		List<Destinacija> destinacije= service.getByMesto(mesto).get();
		if(!destinacije.isEmpty()) {
			return ResponseEntity.ok(destinacije);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Resources with requested mesto:" + mesto+ " are not found");
		}
	}
	
	@GetMapping("/destinacija/drzava/{drzava}")
	public ResponseEntity<?> getDestinacijaByDrzava(@PathVariable String drzava) {
		List<Destinacija> destinacije= service.getByDrzava(drzava).get();
		if(!destinacije.isEmpty()) {
			return ResponseEntity.ok(destinacije);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Resources with requested drzava:" + drzava+ " are not found");
		}
	}
	
	@PostMapping("/destinacija")
	public ResponseEntity<?> createDestinacija(@RequestBody Destinacija destinacija){
		Destinacija savedDestinacija;
		
		if(!service.existsById(destinacija.getId())) {
			savedDestinacija= service.addDestinacija(destinacija);
		}else {
			List<Destinacija> lista = service.getAll();
			long najvecaVrednost = 1;
			for(int i = 0; i<lista.size();i++) {
				if(najvecaVrednost<= lista.get(i).getId()) {
					najvecaVrednost = lista.get(i).getId();
				}
				
				if(i == lista.size()-1) {
					najvecaVrednost++;
				}
			}
			
			destinacija.setId(najvecaVrednost);
			savedDestinacija= service.addDestinacija(destinacija);
		}
		
		
		
		URI uri = URI.create("/destinacija/" + savedDestinacija.getId());
		return ResponseEntity.created(uri).body(savedDestinacija);
	}
	
	
	@PutMapping("/destinacija/{id}")
	public ResponseEntity<?> updateDestinacija(@RequestBody Destinacija destinacija, @PathVariable long id){
		if(service.existsById(destinacija.getId())) {
			Destinacija savedDestinacija= service.addDestinacija(destinacija);
			return ResponseEntity.ok(savedDestinacija);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Resource with requested id: " + id + " has not been found"); 
		}
		
		
	}
	
	@DeleteMapping("/destinacija/{id}")
	public ResponseEntity<String> deleteDestinacija(@PathVariable long id){
		if(service.existsById(id)) {
			service.deleteById(id);
			return ResponseEntity.ok("Resource with requested id: " + id + " has been deleted");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Resource with requested id: " + id + " has not been found"); 
		}
	}
}
