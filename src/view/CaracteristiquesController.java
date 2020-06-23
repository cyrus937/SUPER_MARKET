package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import modele.*;

public class CaracteristiquesController implements Initializable {

	 @FXML
	    private Label code;

	    @FXML
	    private Label nom;

	    @FXML
	    private Label prixVente;

	    @FXML
	    private Label prixAchat;

	    @FXML
	    private Label qte;

	    @FXML
	    private Label description;

	    @FXML
	    private Label fouenisseur;

	    @FXML
	    private Label date;

	    @FXML
	    private Label actif;

	    @FXML
	    private Label categorie;
	    
	    @FXML
	    private Label datePerem;
	    
	    private ProduitDAO produitDAO = new ProduitDAO();
	    
	    private Produit q = produitDAO.find(MenuCaisController.g);
	    
	    public void initialize(URL location, ResourceBundle resources) {
	    	
	    	if (q != null) {
				code.setText(String.valueOf(q.getCodePro()));
				nom.setText(q.getNomPro());
				prixVente.setText(String.valueOf(q.getPrixVente()));
				prixAchat.setText(String.valueOf(q.getPrixAchat()));
				qte.setText(String.valueOf(q.getQte()));
				datePerem.setText(String.valueOf(q.getDatePeremption()));
				description.setText(q.getDescription());
				fouenisseur.setText(q.getCodeFour());
				date.setText(String.valueOf(q.getDateInsertion()));
				actif.setText(String.valueOf(q.isActif()));
				categorie.setText(q.getCategorie().getNomCat());
			}
	    }
}
