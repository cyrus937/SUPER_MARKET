package modele;

import java.sql.Date;
import java.util.ArrayList;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Commande extends RecursiveTreeObject<Commande>{
	private int idCommande;
	private Date date = new Date(System.currentTimeMillis());
	private boolean livre = false;
	private String commentaires = "ND";
	private Client client = null;
	private ArrayList<LigneCommande> lignesCommande;
	
	public Commande() {
		
	}
	public Commande(Date date, boolean livre, String commentaires, Client client) {
		super();
		if(date != null)
			this.date = date;
		this.livre = livre;
		if(commentaires != null)
			this.commentaires = commentaires;
		this.client = client;
	}
	
	public Commande(int idCommande, Date date, boolean livre, String commentaires, Client client) {
		this(date, livre, commentaires, client);
		this.idCommande = idCommande;
	}

	public Commande(int idCommande, Date date, boolean livre, String commentaires, Client client, ArrayList<LigneCommande> lignesCommande) {
		this(idCommande,date, livre, commentaires, client);
		this.lignesCommande = lignesCommande;
	}
	
	public Commande(Client client) {
		// TODO Auto-generated constructor stub
		super();
		this.client = client;
	}

	public int getIdCommande() {
		return idCommande;
	}

	public void setIdCommande(int idCommande) {
		this.idCommande = idCommande;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isLivre() {
		return livre;
	}

	public void setLivre(boolean livre) {
		this.livre = livre;
	}

	public String getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(String commentaires) {
		this.commentaires = commentaires;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ArrayList<LigneCommande> getLignesCommande() {
		return lignesCommande;
	}

	public void setLignesCommande(ArrayList<LigneCommande> lignesCommande) {
		this.lignesCommande = lignesCommande;
	}
	
	public void addLigneCommande(LigneCommande ligneCommande) {
		if(this.lignesCommande == null)
			this.lignesCommande = new ArrayList<LigneCommande>();
		this.lignesCommande.add(ligneCommande);
	}
	
	public void removeLigneCommande(LigneCommande ligneCommande) {
		this.lignesCommande.remove(ligneCommande);
	}
	
	public void clearLigneCommande() {
		this.lignesCommande.clear();
	}
	
	public void annulerCommande() {
		clearLigneCommande();
		livre = false;
		commentaires = "ND";
	}

	@Override
	public String toString() {
		return  String.valueOf(idCommande);
	}
	
	
}
