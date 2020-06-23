package modele;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;


public class FicheFacture extends RecursiveTreeObject<FicheFacture> {

	private int codePro;
	private String nomPro;
	private double quantiteVendue;
	private double prixVente;
	private String codeFour;

	public FicheFacture(int codePro, String nomPro, double quantiteVendue, double prixVente, String codeFour) {
		this.codePro = codePro;
		this.nomPro = nomPro;
		this.quantiteVendue = quantiteVendue;
		this.prixVente = prixVente;
		this.codeFour = codeFour;
	}


	public int getCodePro() {
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



	public double getQuantiteVendue() {
		return quantiteVendue;
	}



	public void setQuantiteVendue(double quantiteVendue) {
		this.quantiteVendue = quantiteVendue;
	}



	public double getPrixVente() {
		return prixVente;
	}



	public void setPrixVente(double prixVente) {
		this.prixVente = prixVente;
	}



	public String getCodeFour() {
		return codeFour;
	}



	public void setCodeFour(String codeFour) {
		this.codeFour = codeFour;
	}



	@Override
	public String toString() {
		String str="codePro: "+codePro+", nomPro: "+nomPro+", quantiteVendue: "+quantiteVendue+", prixVente:"+prixVente+", codeFour: "+codeFour+"\n";
		return str;
	}
	
}
