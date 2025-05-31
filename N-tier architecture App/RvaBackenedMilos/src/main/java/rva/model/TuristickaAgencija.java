package rva.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class TuristickaAgencija implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name ="TURISTICKAAGENCIJA_ID_GENERATOR", sequenceName="TURISTICKAAGENCIJA_SEQ", allocationSize= 1)
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator ="TURISTICKAAGENCIJA_ID_GENERATOR")
	private long id;
	private String naziv;
	private String adresa;
	private String kontakt;
	
	public TuristickaAgencija(){
		
	}
	@JsonIgnore
	@OneToMany(mappedBy = "turistickaAgencija", cascade=CascadeType.REMOVE)
	private List<Aranzman> aranzmani;
	
	public List<Aranzman> getAranzmani() {
		return aranzmani;
	}
	public void setAranzmani(List<Aranzman> aranzmani) {
		this.aranzmani = aranzmani;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public String getKontakt() {
		return kontakt;
	}
	public void setKontakt(String kontakt) {
		this.kontakt = kontakt;
	}
	
	
}
