package modele;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.Main;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class FactureAImpimer {
	private int idFac;
	private Timestamp dateFac = new Timestamp(System.currentTimeMillis());
	private String codePaiement = "000000";
	private double remise = 0;
	private double montant = 0;
	private double montantVerse = 0;
	private String modePaiement = "Especes";
	private String caissiere;
	private String client;
	private ArrayList<LigneFactureAImprimer> lignesFacture = null;
	private JasperReport jasperReport = null;
	
	
	public FactureAImpimer() {
	
	}
	public void setParametres(Facture facture, double montantVerse) {
		// TODO Auto-generated constructor stub
		
		this.idFac = facture.getIdFac();
		this.dateFac = facture.getDateFac();
		this.codePaiement = facture.getCodePaiement();
		this.remise = facture.getRemise();
		this.montant = facture.getMontant();
		if(facture.isModePaiement())
			this.modePaiement = "E-money";
		this.caissiere = facture.getCaissiere().getNomGest();
		this.client = facture.getClient().getNom();
		this.montantVerse = montantVerse;
		lignesFacture = new ArrayList<LigneFactureAImprimer>();
		for(LigneFacture ligneFacture : facture.getLignesFacture()) {
			lignesFacture.add(new LigneFactureAImprimer(ligneFacture));
		}
	}
	
	public void init() {
		 //read jrxml file and creating jasperdesign object
        
	}
	
	public void imprimer() throws FileNotFoundException, JRException {
		String outputFile = "./Factures/Facture" + idFac + ".pdf";
	
		JRBeanCollectionDataSource ligne = new JRBeanCollectionDataSource(lignesFacture);
		 
	        
        /* Map to hold Jasper report Parameters */
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("TableParameter", ligne);
        parameters.put("dateFac", dateFac);
        parameters.put("idFac", idFac);
        parameters.put("montant", montant);
        parameters.put("remise", remise);
        parameters.put("caissiere", caissiere);
        parameters.put("client", client);
        parameters.put("montantVerse", montantVerse);
        parameters.put("image", Main.image_url);
       
        InputStream input = new FileInputStream(new File(".\\Facture_A4.jrxml"));
        
        JasperDesign jasperDesign = JRXmlLoader.load(input);
		/*compiling jrxml with help of JasperReport class*/
		
        if(lignesFacture.size() > 4){
        	for(int i = 4; i < lignesFacture.size() ; i++ ){
        		jasperDesign.setPageHeight(jasperDesign.getPageHeight() + 50);
        	}
        }
        jasperReport = JasperCompileManager.compileReport(jasperDesign);
		
        /* Using jasperReport object to generate PDF */
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource()); 
        
        /* outputStream to create PDF */
        OutputStream outputStream = new FileOutputStream(new File(outputFile));
        
        
        /* Write content to PDF file */
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        try {
			outputStream.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			Desktop.getDesktop().open(new File(outputFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("File Generated");	
	
		
	}

	@Override
	public String toString() {
		return "FactureAImpimer [idFac=" + idFac + ", dateFac=" + dateFac + ", codePaiement=" + codePaiement
				+ ", remise=" + remise + ", montant=" + montant + ", modePaiement=" + modePaiement + ", caissiere="
				+ caissiere + ", client=" + client + ", lignesFacture=" + lignesFacture + "]";
	}
	
	
}
