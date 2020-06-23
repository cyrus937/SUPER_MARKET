package modele;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Fournisseur extends RecursiveTreeObject<Fournisseur>{
	private Short idFournisseur = null;
	private String nom;
	private String adresse =null;
	private String tel = null;
	
	public Fournisseur(String nom) {
		super();
		this.nom = nom;
	}
	
	public Fournisseur(short idFournisseur, String nom, String adresse, String tel) {
		this(nom);
		this.idFournisseur = idFournisseur;
		this.adresse = adresse;
		this.tel = tel;
	}
	
	public Fournisseur(String nom, String adresse, String tel) {
		// TODO Auto-generated constructor stub
		this(nom);
		this.adresse = adresse;
		this.tel = tel;
	}

	public short getIdFournisseur() {
		return idFournisseur;
	}
	public void setIdFournisseur(short idFournisseur) {
		this.idFournisseur = idFournisseur;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public String toString() {
		return "Fournisseur [idFournisseur=" + idFournisseur + ", nom=" + nom + ", adresse=" + adresse + ", tel=" + tel
				+ "]";
	}
	
	
}
