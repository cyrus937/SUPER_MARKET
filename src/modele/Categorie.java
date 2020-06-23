package modele;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;


public class Categorie extends RecursiveTreeObject<Categorie>{
	private Short idCat = null;
	private String nomCat;
	

	public Categorie(String nomCat) {
		super();
		this.nomCat = nomCat;
	}
	
	public Categorie(short idCat, String nomCat) {
		this(nomCat);
		this.idCat = idCat;
	}
	
	public short getIdCat() {
		return idCat;
	}
	public void setIdCat(short idCat) {
		this.idCat = idCat;
	}
	public String getNomCat() {
		return nomCat;
	}
	public void setNomCat(String nomCat) {
		this.nomCat = nomCat;
	}

	@Override
	public String toString() {
		return nomCat;
	}
	
}
