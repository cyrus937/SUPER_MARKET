package view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modele.Gestionnaire;
import modele.GestionnaireDAO;

public class modifMagasinierController implements Initializable {

	@FXML
    private JFXToggleButton statut;

    @FXML
    private JFXTextField nom;

    @FXML
    private JFXTextField login;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton okButton;

    @FXML
    private JFXButton annulerButton;

    @FXML
    private JFXTextField id;
    
    private Main main=new Main();
    
    private ArrayList<Gestionnaire> listMagasinier =new ArrayList<Gestionnaire>();
    
    
    GestionnaireDAO gestDAO = new GestionnaireDAO();
    
    Gestionnaire gest = gestDAO.find(AdminMagasinierController.g);

    @FXML
    void handleAnnulerButton(ActionEvent event) {
    	main.getStageDelete1().close();
    }

    @FXML
    void handleOkButton(ActionEvent event) throws IOException, SQLException {
    	if(id.getText().equals("")) {
			Main.printError("Erreur lors de la modification ", null,"Veuillez remplir le champ de l'identifiant");
			return;
		}
    	else{
    		if (!Short.valueOf(id.getText()).equals((Short)AdminMagasinierController.g) ) {
				if (gestDAO.find(Short.valueOf(id.getText())) != null) {
					Main.printError("Erreur lors de la modification", null,
							"Veuillez changer l'identifiant car il existe d�j�");
				} 
			}
    	}
    	if(nom.getText().equals("")) {
			Main.printError("Erreur lors de la modification ", null,"Veuillez remplir le champ Nom");
			return;
		}
    	
    	gest.setIdGest(Short.valueOf(id.getText()));
    	gest.setNomGest(nom.getText());
    	gest.setLogin(login.getText());
    	gest.setPwd(password.getText());
    	gest.setActif(statut.isSelected());
    	
    	gestDAO.update(gest);
    	listMagasinier = gestDAO.findAllMagasinier();
    	AdminMagasinierController.data.setAll(listMagasinier);
    	main.getStageDelete1().close();
    }

    @FXML
    void handle_id(KeyEvent e) {
    	if(e.getCode().equals(KeyCode.ENTER)) {
			nom.requestFocus();
		}
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
			statut.requestFocus();
		}
    }

    @FXML
    void handle_statut(KeyEvent e) {
    	if(e.getCode().equals(KeyCode.RIGHT))
    		statut.setSelected(true);
    	if (e.getCode().equals(KeyCode.LEFT))
    		statut.setSelected(false);
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
    
    public void initialize(URL location, ResourceBundle resources) {
    	id.setText(String.valueOf(gest.getIdGest()));
    	nom.setText(gest.getNomGest());
    	login.setText(gest.getLogin());
    	password.setText(gest.getPwd());
    	statut.setSelected(gest.isActif());
    }
}
