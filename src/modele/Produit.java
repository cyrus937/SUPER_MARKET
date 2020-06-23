package modele;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Random;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Produit extends RecursiveTreeObject<Produit>{
	private int codePro;
	private String nomPro;
	private double prixVente;
	private double prixAchat;
	private boolean quantifiable = true;
	private double qte;
	private Date datePeremption;
	private String description = "RAS";
	private String codeFour;
	private Timestamp dateInsertion = new Timestamp(System.currentTimeMillis());
	private boolean actif = true;
	private Categorie categorie;
	
	public static int MIN =  100000;
	public static int MAX = 999999;
	public static SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	public int generation() {
		Random random = new Random();
		int nb = MIN + random.nextInt(MAX-MIN);
		return nb;
	}
	
	public Produit(String nomPro, double prixVente, double prixAchat, boolean quantifiable, double qte, Date datePeremption, String description,
			String codeFour, Timestamp dateInsertion, boolean actif,Categorie categorie) {
		super();
		this.codePro = generation();
		this.nomPro = nomPro;
		this.prixVente = prixVente;
		this.prixAchat = prixAchat;
		this.qte = qte;
		this.quantifiable = quantifiable;
		if(datePeremption != null)
			this.datePeremption = datePeremption;
		if(description != null)
			this.description = description;
		this.codeFour = codeFour;
		if(dateInsertion != null)
			this.dateInsertion = dateInsertion;
		this.actif = actif;
		this.categorie = categorie;
	}
	
	public Produit(int codePro, String nomPro, double prixVente, double prixAchat,boolean quantifiable,
			double qte, Date datePeremption, String description,String codeFour, 
			Timestamp dateInsertion, boolean actif,Categorie categorie) {
		super();
		this.codePro = codePro;
		this.nomPro = nomPro;
		this.prixVente = prixVente;
		this.prixAchat = prixAchat;
		this.quantifiable = quantifiable;
		this.qte = qte;
		if(datePeremption != null)
			this.datePeremption = datePeremption;
		if(description != null)
			this.description = description;
		this.codeFour = codeFour;
		if(dateInsertion != null)
			this.dateInsertion = dateInsertion;
		this.actif = actif;
		this.categorie = categorie;
	}

	public String getCodePro() {
		//return codePro;
		//return (String)(codePro/1000)+"-"+(String)(codePro%1000);
		if(codePro%1000 - 100>=0)
			return String.valueOf(codePro/1000)+"-"+String.valueOf(codePro%1000);
		else
			return String.valueOf(codePro/1000)+"-0"+String.valueOf(codePro%1000);
	}
	
	public int getCodePro1() {
		return codePro;
	}

	public void setCodePro(int codePro) {
		this.codePro = codePro;
	}

	public String getNomPro() {
		return nomPro;
	}

	public void setNomPro(String nomPro) {
		this.nomPro = nomPro;
	}

	public double getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(double prixVente) {
		this.prixVente = prixVente;
	}

	public double getPrixAchat() {
		return prixAchat;
	}

	public void setPrixAchat(double prixAchat) {
		this.prixAchat = prixAchat;
	}

	public double getQte() {
		return qte;
	}

	public void setQte(double qte) {
		this.qte = qte;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCodeFour() {
		return codeFour;
	}

	public void setCodeFour(String codeFour) {
		this.codeFour = codeFour;
	}

	public Timestamp getDateInsertion() {
		return dateInsertion;
	}

	public void setDateInsertion(Timestamp dateInsertion) {
		this.dateInsertion = dateInsertion;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}
	
	public Date getDatePeremption() {
		return datePeremption;
	}

	public void setDatePeremption(Date datePeremption) {
		this.datePeremption = datePeremption;
	}
	
	public boolean isQuantifiable() {
		return quantifiable;
	}

	public void setQuantifiable(boolean quantifiable) {
		this.quantifiable = quantifiable;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	@Override
	public String toString() {
		return nomPro;
	}
	
}
