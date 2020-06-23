package modele;

public class LigneFactureAImprimer {
	private String produit;
	private double prixVente;
	private double qte;
	
	public LigneFactureAImprimer(LigneFacture ligneFacture) {
		// TODO Auto-generated constructor stub
		this.produit = ligneFacture.getProduit().getNomPro();
		this.prixVente = ligneFacture.getPrixVente();
		this.qte = ligneFacture.getQte();
	}

	public String getProduit() {
		return produit;
	}

	public void setProduit(String produit) {
		this.produit = produit;
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
	
}
