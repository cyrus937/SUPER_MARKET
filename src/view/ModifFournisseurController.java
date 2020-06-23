package view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modele.Fournisseur;
import modele.FournisseurDAO;
import modele.Gestionnaire;

public class ModifFournisseurController implements Initializable  {
	
	@FXML
    private JFXTextField nom;

    @FXML
    private JFXTextField adresse;

    @FXML
    private JFXTextField tel;

    @FXML
    private JFXButton okButton;

    @FXML
    private JFXButton annulerButton;

    @FXML
    private JFXTextField identifiant;
    
    private Main main=new Main();
    
    private ArrayList<Fournisseur> listFourn =new ArrayList<Fournisseur>();
    
    FournisseurDAO fournDAO = new FournisseurDAO();
    
    Fournisseur fourn = fournDAO.find(AdminFournisseurController.id);
    
    public void initialize(URL location, ResourceBundle resources) {
    	identifiant.setText(String.valueOf(fourn.getIdFournisseur()));
    	nom.setText(fourn.getNom());
    	adresse.setText(fourn.getAdresse());
    	tel.setText(fourn.getTel());
    }

    @FXML
    void handleAnnulerButton(ActionEvent event) {
    	main.getStageDelete1().close();
    }

    @FXML
    void handleOkButton(ActionEvent event) throws IOException, SQLException{
    	if(nom.getText().equals("")) {
			Main.printError("Erreur lors de l'ajout ", null,"Veuillez remplir le champ Nom");
			return;
		}
    	
    	fourn.setIdFournisseur(Short.valueOf(identifiant.getText()));
    	fourn.setNom(nom.getText());
    	fourn.setAdresse(adresse.getText());
    	fourn.setTel(tel.getText());
    	
    	fournDAO.update(fourn);
    	
    	listFourn = fournDAO.findAll();
    	AdminFournisseurController.data.setAll(listFourn);
    	main.getStageDelete1().close();
    }

    @FXML
    void handle_adresse(KeyEvent e) {
    	if(e.getCode().equals(KeyCode.ENTER)) {
			tel.requestFocus();
		}
    }

    @FXML
    void handle_id(KeyEvent e) {
    	if(e.getCode().equals(KeyCode.ENTER)) {
			nom.requestFocus();
		}
    }

    @FXML
    void handle_nom(KeyEvent e) {
    	if(e.getCode().equals(KeyCode.ENTER)) {
			adresse.requestFocus();
		}
    }

    @FXML
    void handle_tel(KeyEvent e) {
    	if(e.getCode().equals(KeyCode.ENTER)) {
			try {
				handleOkButton(new ActionEvent());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
    	}
    }
}
