package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AdministrateurGlobalController implements Initializable {

	 @FXML
	    private JFXButton caissierButton;

	    @FXML
	    private JFXButton magasinierButton;
	    
	    @FXML
	    private JFXButton historyButton;

	    @FXML
	    private JFXButton fournisseurButton;

	    @FXML
	    private JFXButton stockButton;

	    @FXML
	    private JFXButton statButton;

	    @FXML
	    private JFXButton deconnexion;
	    
	    @FXML
	    private Label label = new Label();
	    
	    private Main main=new Main();
	    
	    @FXML
	    void handleCaissierButton(ActionEvent event) throws IOException {
	    	Stage newStage = new Stage();
	    	main.AdminCaissier(newStage);
	    }

	    @FXML
	    void handleDeconnexionButton(ActionEvent event) throws IOException {
	    	main.getStage().close();
	    	Stage primarynewStage = new Stage();
			main.Login(primarynewStage);
	    }

	    @FXML
	    void handleFournisseurButton(ActionEvent event) throws IOException {
	    	Stage newStage = new Stage();
	    	main.AdminFournisseur(newStage);
	    }


	    @FXML
	    void handleHistoryButton(ActionEvent event) throws IOException {
	    	Stage newStage = new Stage();
	    	main.GestionFacture(newStage);
	    }
	    
	    @FXML
	    void handleMagasinierButton(ActionEvent event) throws IOException {
	    	Stage newStage = new Stage();
	    	main.AdminMagasinier(newStage);
	    }

	    @FXML
	    void handleStatButton(ActionEvent event) throws IOException {
	    	Stage newStage = new Stage();
			main.Statistiques(newStage);
	    }

	    @FXML
	    void handleStockButton(ActionEvent event) throws IOException {
	    	Stage newStage = new Stage();
			main.AdminStock(newStage);
	    }
	    
	    public void initialize(URL location, ResourceBundle resources) {
	    	label.setText(LoginController.ges);
	    }
}
