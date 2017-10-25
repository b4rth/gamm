package fr.bart.gamm.model;

import java.text.Normalizer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "adresse")
public class Adresse {

	@Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE )
	private int id;
	
	@Column(name = "numero")
	private Integer numero;
	
	@Column(name = "rue")
	private String rue;
	
	@Column(name = "codePostal")
	private Integer codePostal;
	
	@Column(name = "ville")
	private String ville;
	
	public int getId() {
		return id;
	}
	
	public Adresse() {
		
	}
	
	public Adresse(Integer numero, String rue, Integer codePostal, String ville) {
		super();
		this.numero = numero;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Integer getNumero() {
		return numero;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public String getRue() {
		return rue;
	}
	
	public void setRue(String rue) {
		this.rue = rue;
	}
	
	public Integer getCodePostal() {
		return codePostal;
	}
	
	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}
	
	public String getVille() {
		return ville;
	}
	
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	public String toString() {
		return numero + " " + rue + " " + codePostal + " " + ville;
	}
	
	public String getAdresseForURL() {
		String result = "";
		result += (numero != null ? numero : "") + "+" + normalizeString(rue) + "+" + (codePostal != null ? codePostal : "") + "+" + normalizeString(ville); 
		return result;
	}
	
	private String normalizeString(String s) {
		if(s != null) {
			return stripAccents(s.trim().replace("\t", "+").replace(" ", "+"));
		} else {
			return "";
		}
	}
	
	private String stripAccents(String s) 
	{
	    s = Normalizer.normalize(s, Normalizer.Form.NFD);
	    s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	    return s;
	}
	
}
