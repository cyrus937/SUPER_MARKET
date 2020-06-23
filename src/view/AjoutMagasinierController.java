package view;

import java.io.IOException;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import modele.Gestionnaire;
import modele.GestionnaireDAO;

public class AjoutMagasinierController {

	@FXML
    private JFXToggleButton actif;

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

    private Main main = new Main();
    
    @FXML
    void handleAnnulerButton(ActionEvent event) {
    	main.getStageDelete1().close();
    }

    @FXML
    void handleOkButton(ActionEvent event) throws IOException , SQLException {
    	
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
    		if(actif.isSelected())
    			actif.setText("Actif");
    		else
    			actif.setText("Inactif");
			GestionnaireDAO gestionnaireDAO = new GestionnaireDAO();
    		Gestionnaire gestionnaire = new Gestionnaire(nom.getText(), (byte)1, login.getText(), password.getText(), actif.isSelected());
			System.out.println(gestionnaire);
    		gestionnaireDAO.create(gestionnaire);
    		AdminMagasinierController.data.add(gestionnaire);
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
    void handle_nom(KeyEvent e) {
    	if(e.getCode().equals(KeyCode.ENTER)) {
			login.requestFocus();
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
			actif.requestFocus();
		}
    }
    
    @FXML
    void handle_actif(KeyEvent e) {
    	if(e.getCode().equals(KeyCode.RIGHT))
    	{
    		actif.setSelected(true);
    		actif.setText("Actif");
    	}
    	if (e.getCode().equals(KeyCode.LEFT))
    	{
    		actif.setSelected(false);
    		actif.setText("Inactif");
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
    void handleOnSelectionChanged(ActionEvent event) {
    	if(actif.isSelected())
			actif.setText("Actif");
		else
			actif.setText("Inactif");
    }
}
