package modele;

import java.sql.Array;
import java.sql.Timestamp;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class GestionStock extends RecursiveTreeObject<GestionStock>{
	private int idStock;
	private double qte;
	private Timestamp dateStock = new Timestamp(System.currentTimeMillis());
	private boolean operation;
	private Gestionnaire gest;
	private Produit produit;
	
	public GestionStock(double qte, Timestamp dateStock, boolean operation, Gestionnaire gest,
			Produit produit) {
		this.qte = qte;
		this.dateStock = dateStock;
		this.operation = operation;
		this.gest = gest;
		this.produit = produit;
	}
	
	public GestionStock(int idStock, double qte, Timestamp dateStock, boolean operation, Gestionnaire gest,
			Produit produit) {
		this(qte, dateStock, operation, gest, produit);
		this.idStock = idStock;
	}

	public GestionStock(double qte, boolean operation, Gestionnaire gest, Produit produit) {
		// TODO Auto-generated constructor stub
		this.qte = qte;
		this.operation = operation;
		this.gest = gest;
		this.produit = produit;
	}

	public int getIdStock() {
		return idStock;
	}

	public void setIdStock(int idStock) {
		this.idStock = idStock;
	}

	public double getQte() {
		return qte;
	}

	public void setQte(double qte) {
		this.qte = qte;
	}

	public Timestamp getDateStock() {
		return dateStock;
	}

	public void setDateStock(Timestamp dateStock) {
		this.dateStock = dateStock;
	}

	public boolean isOperation() {
		return operation;
	}

	public void setOperation(boolean operation) {
		this.operation = operation;
	}

	public Gestionnaire getGest() {
		return gest;
	}

	public void setGest(Gestionnaire gest) {
		this.gest = gest;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	@Override
	public String toString() {
		return "GestionStock [idStock=" + idStock + ", qte=" + qte + ", dateStock=" + dateStock + ", operation="
				+ operation + ", gest=" + gest.getNomGest() + ", produit=" + produit.getNomPro() + "]";
	}
	
	
}
