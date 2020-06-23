package view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import application.Main;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import modele.*;

public class AjoutClientController {
	
	@FXML
    private JFXToggleButton statut;

    @FXML
    private JFXTextField nom;

    @FXML
    private JFXTextField numero;

    @FXML
    private JFXTextField adresse;
    
    @FXML
    private JFXButton ValiderClient;

    @FXML
    private JFXButton AnnulerButton;
    
    private Main main=new Main();

	 @FXML
	    void handleCancelButton(ActionEvent event) {
	    	main.getStageDelete().close();
	    }

	    @FXML
	    void handleOkButton(ActionEvent event) throws IOException, SQLException {
	    	if(nom.getText().equals("")) {
				Main.printError("Erreur lors de l'ajout ", null,"Veuillez remplir le champ Nom");
				return;
			}
	    	
			ClientDAO clientDAO = new ClientDAO();
	   		Client client = new Client(nom.getText(),numero.getText(),adresse.getText(), statut.isSelected());
			System.out.println(client);
	   		clientDAO.create(client);
	    	
	    	MenuCaisController.clients.add(client);
	    	main.getStageDelete1().close();
	    	
	    }
	    
	    @FXML
	    void handle_nom(KeyEvent e) {
	    	if(e.getCode().equals(KeyCode.ENTER)) {
				numero.requestFocus();
			}
	    }

	    @FXML
	    void handle_numero(KeyEvent e) {
	    	if(e.getCode().equals(KeyCode.ENTER)) {
				adresse.requestFocus();
			}
	    }
	    
	    @FXML
	    void handle_adresse(KeyEvent e) {
	    	if(e.getCode().equals(KeyCode.ENTER)) {
				statut.requestFocus();
			}
	    }
	    
	    @FXML
	    void handle_statut(KeyEvent e) {
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
