package fr.bart.gamm.controller;

import java.util.ArrayList;
import java.util.List;

import fr.bart.gamm.dao.MagasinDao;
import fr.bart.gamm.model.Magasin;

public class MagasinController {

	private MagasinDao magasinDao = new MagasinDao();
	
	
	public Magasin creerMagasin(Magasin magasin) {
		try {
			return magasinDao.create(magasin);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Magasin> recupererMagasins() {
		List<Magasin> magasinList = new ArrayList<Magasin>();
		try {
			magasinList = magasinDao.findAll();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return magasinList;
	}
	
}
