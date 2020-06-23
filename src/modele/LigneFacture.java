package modele;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class LigneFacture extends RecursiveTreeObject<LigneFacture>{

	private Produit produit;
	private Facture facture;
	private double prixVente;
	private double qte;
	private double prixAchat;
	
	public LigneFacture( Produit produit, Facture facture, double prixVente, double qte, double prixAchat ) {
		if(this.qte > produit.getQte())
			System.out.println("Pas assez de produit en stock !");
		else {
			this.prixVente = prixVente;
			this.qte = qte;
			this.prixAchat = prixAchat;
			this.produit = produit;
			this.facture = facture;
			if(this.facture != null){
				facture.addLigneFacture(this);
			}
		}
	}

	public double getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(double prixVente) {
		this.prixVente = prixVente;
	}

	public double getQte() {
		return qte;
	}

	public void setQte(double qte) {
		this.qte = qte;
	}

	public double getPrixAchat() {
		return prixAchat;
	}

	public void setPrixAchat(double prixAchat) {
		this.prixAchat = prixAchat;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Facture getFacture() {
		return facture;
	}

	public boolean setFacture(Facture facture) throws Exception {
		if (facture != null) {
			this.facture = facture;
			return facture.addLigneFacture(this);
		}
		throw new Exception("La facture est nulle");
	}

	@Override
	public String toString() {
		return "LigneFacture [produit=" + produit + ", facture=" + facture + ", prixVente=" + prixVente + ", qte=" + qte
				+ ", prixAchat=" + prixAchat + "]";
	}
	
	
}
