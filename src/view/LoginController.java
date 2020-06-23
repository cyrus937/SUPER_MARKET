package view;

import java.io.IOException;
import java.sql.SQLException;

import com.jfoenix.controls.JFXProgressBar;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modele.Gestionnaire;
import modele.GestionnaireDAO;

public class LoginController {

	@FXML
    private PasswordField password;
	
	@FXML
	private JFXProgressBar progressBar;

    @FXML
    private Button signButton;

    @FXML
    private TextField userName;
    
    @FXML
    private Button signUp;
    
    private static Main main=new Main(); 
	
	public static String user = null;
	
	static String ges; 

    @FXML
    void handleSignButton(ActionEvent event) throws SQLException, IOException {
    	byte type;
		GestionnaireDAO gest = new GestionnaireDAO();
		Gestionnaire gestionnaire ;
		gestionnaire = gest.researchTypeGest(userName.getText(), password.getText());
		
		if (gestionnaire == null) {
			Main.printError("Erreur de Compte ", null,"Vous vous êtes trompés de mot de passe");
			return;
		}
		else {
			ges = gestionnaire.getNomGest();
			user = userName.getText();
			type = gestionnaire.getTypeGest();
			if (type == 3 ) {
				//mettre le lancement de l'interrface du boss je ne l'ai pas	
			}
			else if (type == 2) {
				main.menuAdministrateur();
			}
			else if (type == 1) {
				main.menuMagasinier();
			}
			else if (type == 0){
				main.menu_cais();
			}
			
		}
    }
    
    @FXML
    void handleSignUpButton(ActionEvent event) {

    }

    @FXML
    void handle_username(KeyEvent e) {
    	if(e.getCode().equals(KeyCode.ENTER)) {
			password.requestFocus();
		}
    }
    
    @FXML
    void handle_password(KeyEvent e) {
    	if(e.getCode().equals(KeyCode.ENTER)) {
			try {
				handleSignButton(new ActionEvent());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
    	
    	if(e.getCode().equals(KeyCode.UP))
    	{
    		userName.requestFocus();
    	}
    }

}
