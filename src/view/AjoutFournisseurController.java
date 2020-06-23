package view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modele.ConnexionBD;
import modele.Fournisseur;
import modele.FournisseurDAO;
import modele.Gestionnaire;
import modele.GestionnaireDAO;

public class AjoutFournisseurController {

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
    
    private Main main=new Main();
    
    private ObservableList<String> data = FXCollections.observableArrayList();
	
	private ConnexionBD connexionBD=new ConnexionBD();

    @FXML
    void handleAnnulerButton(ActionEvent event) {
    	main.getStageDelete().close();
    }

    @FXML
    void handleOkButton(ActionEvent event) throws IOException, SQLException {
    	
    	if(nom.getText().equals("")) {
			Main.printError("Erreur lors de l'ajout ", null,"Veuillez remplir le champ Nom");
			return;
		}
    	
		FournisseurDAO fournisseurDAO = new FournisseurDAO();
   		Fournisseur fournisseur = new Fournisseur(nom.getText(),adresse.getText(),tel.getText());
		System.out.println(fournisseur);
   		fournisseurDAO.create(fournisseur);
   		AdminFournisseurController.data.add(fournisseur);
    	
    	main.getStageDelete1().close();
    }

    @FXML
    void handle_adresse(KeyEvent e) {
    	if(e.getCode().equals(KeyCode.ENTER)) {
			tel.requestFocus();
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
