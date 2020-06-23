package view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import modele.ConnexionBD;
import modele.Gestionnaire;
import modele.GestionnaireDAO;

public class AjoutCaissierController {

	 @FXML
	    private JFXToggleButton statut;

	    @FXML
	    private JFXTextField nom;

	    @FXML
	    private JFXTextField login;

	    @FXML
	    private JFXPasswordField password;

	    @FXML
	    private JFXPasswordField password1;

	    @FXML
	    private JFXButton okButton;

	    @FXML
	    private JFXButton annulerButton;
	    
	    private Main main=new Main();
		

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
	    	
	    
	    	if (!password1.getText().equals(password.getText()))
	    	{
	    		Main.printError("Erreur lors de l'ajout ", null,"Les mots de passe sont diff�rents");
				return;
	    	}
	    	else
	    	{
	    		
				GestionnaireDAO gestionnaireDAO = new GestionnaireDAO();
	    		Gestionnaire gestionnaire = new Gestionnaire(nom.getText(), (byte)0, login.getText(), password.getText(), statut.isSelected());
				System.out.println(gestionnaire);
	    		gestionnaireDAO.create(gestionnaire);
	    		AdminCaissierController.data.add(gestionnaire);
	    	}
	 
	    	main.getStageDelete1().close();
	    }
	    
	    @FXML
	    void handle_login(KeyEvent e) {
	    	if(e.getCode().equals(KeyCode.ENTER)) {
				password.requestFocus();
			}
	    }

	    @FXML
	    void handle_password(KeyEvent e) {
	    	if(e.getCode().equals(KeyCode.ENTER)) {
				password1.requestFocus();
			}
	    }

	    @FXML
	    void handle_password1(KeyEvent e) {
	    	if(e.getCode().equals(KeyCode.ENTER)) {
				statut.requestFocus();
			}
	    }
	    
	    @FXML
	    void handle_statut(KeyEvent e) {
	    	if(e.getCode().equals(KeyCode.RIGHT))
	    	{
	    		statut.setSelected(true);
	    		statut.setText("Actif");
	    	}
	    	if (e.getCode().equals(KeyCode.LEFT))
	    	{
	    		statut.setSelected(false);
	    		statut.setText("Inactif");
	    	}
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

	    @FXML
	    void handle_username(KeyEvent e) {
	    	if(e.getCode().equals(KeyCode.ENTER)) {
				login.requestFocus();
			}
	    }

	    @FXML
	    void handleOnSelectionChanged(ActionEvent event) {
	    	if(statut.isSelected())
    			statut.setText("Actif");
    		else
    			statut.setText("Inactif");
	    }
}
