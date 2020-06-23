package modele;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class LigneCommande extends RecursiveTreeObject<LigneCommande>{
	

	private Produit produit;
	private Commande commande;
	private double qte;
	
	public LigneCommande(Produit produit, Commande commande, double qte) {
		if(produit.getQte() < qte)
			System.out.println("Pas assez de produit en stock !");
		else {
			this.qte = qte;
			this.produit = produit;
			this.commande = commande;
			commande.addLigneCommande(this);
		}
	}

	public double getQte() {
		return qte;
	}

	public void setQte(double qte) {
		this.qte = qte;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	@Override
	public String toString() {
		return "LigneCommande [produit=" + produit + ", commande=" + commande + ", qte=" + qte + "]";
	}
	
}
