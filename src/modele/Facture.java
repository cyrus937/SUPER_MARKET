package modele;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Facture extends RecursiveTreeObject<Facture>{
	private int idFac;
	private Timestamp dateFac = new Timestamp(System.currentTimeMillis());
	private String codePaiement = "000000";
	private double remise = 0;
	private double montant = 0;
	private boolean modePaiement = false;
	private Gestionnaire caissiere;
	private Client client;
	private Commande commande = null;
	private ArrayList<LigneFacture> lignesFacture = null;
	
	
	
	public Facture() {
		
	}
	
	public Facture(Timestamp dateFac, String codePaiement, double remise, double montant, boolean modePaiement,
			Gestionnaire caissiere, Client client, Commande commande) {
		super();
		if(dateFac != null)
			this.dateFac = dateFac;
		if(codePaiement != null)
			this.codePaiement = codePaiement;
		this.remise = remise;
		this.montant = montant;
		this.modePaiement = modePaiement;
		this.caissiere = caissiere;
		this.client = client;
		if(commande != null)
			this.commande = commande;
	}
	
	public Facture(Timestamp dateFac, String codePaiement, double remise, double montant, boolean modePaiement,
			Gestionnaire caissiere, Client client, Commande commande,ArrayList<LigneFacture> lignesFacture) {
		this(dateFac, codePaiement, remise, montant, modePaiement, caissiere, client, commande);
		this.lignesFacture = lignesFacture;
	}
	
	public Facture(int idFac, Timestamp dateFac, String codePaiement, double remise, double montant, boolean modePaiement,
			Gestionnaire caissiere, Client client, Commande commande) {
		this(dateFac, codePaiement, remise, montant, modePaiement, caissiere, client, commande);
		this.idFac = idFac;
	}

	public Facture(int idFac, Timestamp dateFac, String codePaiement, double remise, double montant, boolean modePaiement,
			Gestionnaire caissiere, Client client, Commande commande,ArrayList<LigneFacture> lignesFacture) {
		this(idFac, dateFac, codePaiement, remise, montant, modePaiement, caissiere, client, commande);
		this.lignesFacture = lignesFacture;
	}
	
	public int getIdFac() {
		return idFac;
	}

	public void setIdFac(int idFac) {
		this.idFac = idFac;
	}

	public Timestamp getDateFac() {
		return dateFac;
	}

	public void setDateFac(Timestamp dateFac) {
		this.dateFac = dateFac;
	}

	public String getCodePaiement() {
		return codePaiement;
	}

	public void setCodePaiement(String codePaiement) {
		this.codePaiement = codePaiement;
	}

	public double getRemise() {
		return remise;
	}

	public void setRemise(double remise) {
		this.remise = remise;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public boolean isModePaiement() {
		return modePaiement;
	}

	public void setModePaiement(boolean modePaiement) {
		this.modePaiement = modePaiement;
	}

	public Gestionnaire getCaissiere() {
		return caissiere;
	}

	public void setCaissiere(Gestionnaire caissiere) {
		this.caissiere = caissiere;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	
	public ArrayList<LigneFacture> getLignesFacture() {
		return lignesFacture;
	}

	public void setLigneFacture(ArrayList<LigneFacture> ligneFacture) {
		this.lignesFacture = ligneFacture;
	}

	public LigneFacture contains(LigneFacture ligneFacture){
		for(LigneFacture temp : this.lignesFacture){
			if(temp.getProduit().getCodePro1() == ligneFacture.getProduit().getCodePro1())
				return temp;
		}
		return null;
	}
	public boolean addLigneFacture(LigneFacture ligneFacture) {
		boolean result = false;
		if(ligneFacture != null) {
			if(this.lignesFacture == null)
				this.lignesFacture = new ArrayList<LigneFacture>();
			LigneFacture temp = this.contains(ligneFacture);
			if(temp != null){
				temp.setQte( ligneFacture.getQte() + temp.getQte());
				result = true;
				System.out.println("Ligne présente");
			}
			else
				this.lignesFacture.add(ligneFacture);
			this.montant = this.montant + ligneFacture.getPrixVente()*ligneFacture.getQte();
		}
		return result;
	}
	
	public void removeLigneFacture(LigneFacture ligneFacture) {
		this.lignesFacture.remove(ligneFacture);
		this.montant = this.montant - ligneFacture.getPrixVente()*ligneFacture.getQte();
	}
	
	public void clearLigneFacture() {
		if(lignesFacture != null)
			this.lignesFacture.clear();
	}
	
	public void validerFacture() {
		for(LigneFacture ligneFacture : this.lignesFacture) {
			ligneFacture.getProduit().setQte(ligneFacture.getProduit().getQte() - ligneFacture.getQte());
		}
	}
	
	public double getQteProduit(int codeProduit,int idFacture){
		LigneFactureDAO ligneFactureDAO=new LigneFactureDAO();
		LigneFacture ligneFacture=ligneFactureDAO.find(codeProduit, idFacture);
		return ligneFacture.getQte();
	}
	
	public void annulerFacture() {
		clearLigneFacture();
		codePaiement = "000000";
		remise = 0;
		montant = 0;
		modePaiement = false;
		commande = null;
		lignesFacture = null;
	}
	
	@Override
	public String toString() {
		return  " " + idFac;
	}
	
	public static void main(String[] args) {
		Timestamp date = new Timestamp(System.currentTimeMillis());
		System.out.println(date);
	}
}
