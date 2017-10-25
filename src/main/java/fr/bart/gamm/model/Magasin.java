package fr.bart.gamm.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "magasin")
public class Magasin {

	@Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE )
	private int id;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	private Adresse adresse;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	private Type type;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	

	
	
	
}
