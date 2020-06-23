package modele;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Client extends RecursiveTreeObject<Client>{
	private Integer idClient = null;
	private String nom;
	private String tel = null;
	private String adresse = null;
	private Integer bonus = 0;
	private boolean actif = true;
	
	public Client(String nom) {
		super();
		this.nom = nom;
	}
	
	public Client(String nom, String tel, String adresse, int bonus, boolean actif) {
		this(nom);
		this.tel = tel;
		this.adresse = adresse;
		this.bonus = bonus;
		this.actif = actif;
	}
	
	public Client(String nom, String tel, String adresse, boolean actif) {
		this(nom);
		this.tel = tel;
		this.adresse = adresse;
		this.actif = actif;
	}
	
	public Client(int id,String nom, String tel, String adresse, int bonus, boolean actif) {
		this(nom, tel, adresse, bonus, actif);
		this.idClient = id;
	}
	
	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	@Override
	public String toString() {
		return ""+idClient ;
	}
	
	
}
