package application;
	
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modele.Categorie;
import modele.CategorieDAO;
import modele.Client;
import modele.ClientDAO;
import modele.Fournisseur;
import modele.FournisseurDAO;
import modele.Gestionnaire;
import modele.GestionnaireDAO;
import modele.Produit;
import modele.ProduitDAO;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	private static Stage ref;
	private static Stage ref1;
	private static Stage ref2;
	public static String image_url = "C:\\Users\\public.DESKTOP-D54OFB2\\workspace\\SUPERMARKET\\src\\application\\logo.jpg";
	
	public Stage getStage() {
		return ref;
	}
	
	public Stage getStageDelete() {
		return ref1;
	}
	
	public Stage getStageDelete1() {
		return ref2;
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		URL ressource = Main.class.getResource("../view/Chargement.fxml"); 
		Parent root = FXMLLoader.load(ressource);
		Scene scene = new Scene(root);
		ref = primaryStage;
		ref.centerOnScreen();
		ref.initStyle(StageStyle.UNDECORATED);
		System.out.println(ref);
		ref.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void Login(Stage primaryStage) throws IOException {
		URL ressource = Main.class.getResource("../view/connexion.fxml"); 
		Parent root = FXMLLoader.load(ressource);
		Scene scene = new Scene(root);
		primaryStage.setTitle("Login");
		ref = primaryStage;
		System.out.println(ref);
		ref.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
		primaryStage.setScene(scene);
		//primaryStage.setMaximized(true);
		primaryStage.setResizable(true);
		primaryStage.show();
	}
	
	public void menuAdministrateur() throws IOException {
		URL ressource = Main.class.getResource("../view/AdministrateurGlobal.fxml"); 
		Parent root = FXMLLoader.load(ressource);
		Scene scene = new Scene(root);
		System.out.println(ref);
		ref.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
		ref.setScene(scene);
		ref.setTitle("ADMINISTRATEUR");
		ref.setResizable(true);
		ref.setScene(scene);
		ref.show();
	}
	
	public void menuMagasinier() throws IOException {
		URL ressource = Main.class.getResource("../view/MagasinierGlobal.fxml"); 
		Parent root = FXMLLoader.load(ressource);
		Scene scene = new Scene(root);
		System.out.println(ref);
		ref.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
		ref.setScene(scene);
		ref.setTitle("MAGASINIER");
		ref.setResizable(true);
		ref.setScene(scene);
		ref.show();
	}
	
	public void menu_cais() throws IOException {
		URL ressource = Main.class.getResource("../view/Menu_cais.fxml"); 
		Parent root = FXMLLoader.load(ressource);
		Scene scene = new Scene(root);
		System.out.println(ref);
		ref.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
		ref.setScene(scene);
		ref.setTitle("Caissier");
		ref.setResizable(true);
		//ref.setFullScreen(true);
		ref.setScene(scene);
		ref.show();
	}
	
	public void AdminCaissier(Stage newstage) throws IOException {
		URL ressource = Main.class.getResource("../view/Caissier.fxml"); 
		Parent root = FXMLLoader.load(ressource);
		Scene scene = new Scene(root);
		ref1=newstage;
		ref1.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
		ref1.setScene(scene);
		ref1.setTitle("Administrateur");
		ref1.setResizable(true);
		ref1.setScene(scene);
		ref1.show();
	}
	
	public void AdminMagasinier(Stage newstage) throws IOException {
		URL ressource = Main.class.getResource("../view/Magasinier.fxml"); 
		Parent root = FXMLLoader.load(ressource);
		Scene scene = new Scene(root);
		ref1=newstage;
		ref1.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
		ref1.setScene(scene);
		ref1.setTitle("Administrateur");
		ref1.setResizable(true);
		ref1.setScene(scene);
		ref1.show();
	}
	
	public void AdminFournisseur(Stage newstage) throws IOException {
		URL ressource = Main.class.getResource("../view/Fournisseur.fxml"); 
		Parent root = FXMLLoader.load(ressource);
		Scene scene = new Scene(root);
		ref1=newstage;
		ref1.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
		ref1.setScene(scene);
		ref1.setTitle("Administratuer");
		ref1.setResizable(true);
		ref1.setScene(scene);
		ref1.show();
	}
	
	public void AdminStock(Stage newstage) throws IOException {
		URL ressource = Main.class.getResource("../view/Stock.fxml"); 
		Parent root = FXMLLoader.load(ressource);
		Scene scene = new Scene(root);
		ref1=newstage;
		ref1.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
		ref1.setScene(scene);
		ref1.setTitle("Administrateur");
		ref1.setResizable(true);
		ref1.setScene(scene);
		ref1.show();
	}
	
	public void Statistiques(Stage newstage) throws IOException {
		URL ressource = Main.class.getResource("../view/Statistiques.fxml"); 
		Parent root = FXMLLoader.load(ressource);
		Scene scene = new Scene(root);
		ref1=newstage;
		ref1.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
		ref1.setScene(scene);
		ref1.setTitle("Administrateur");
		ref1.setResizable(true);
		ref1.setScene(scene);
		ref1.show();
	}
	
	public void ajoutCaissier(Stage newstage) throws IOException {
		URL ressource = Main.class.getResource("../view/Ajout_caissier.fxml"); 
		Parent root = FXMLLoader.load(ressource);
		Scene scene = new Scene(root);
		ref2=newstage;
		ref2.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
		newstage.setScene(scene);
		newstage.setTitle("Ajout Caissier");
		newstage.setResizable(false);
		newstage.setScene(scene);
		newstage.show();
	}
	
	public void ajoutFournisseur(Stage newstage) throws IOException {
		URL ressource = Main.class.getResource("../view/Ajout_fournisseur.fxml"); 
		Parent root = FXMLLoader.load(ressource);
		Scene scene = new Scene(root);
		ref2=newstage;
		ref2.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
		newstage.setScene(scene);
		newstage.setTitle("Ajout Fournisseur");
		newstage.setResizable(false);
		newstage.setScene(scene);
		newstage.show();
	}
	
	public void ajoutMagasinier(Stage newstage) throws IOException {
		URL ressource = Main.class.getResource("../view/Ajout_magasinier.fxml"); 
		Parent root = FXMLLoader.load(ressource);
		Scene scene = new Scene(root);
		ref2=newstage;
		ref2.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
		newstage.setScene(scene);
		newstage.setTitle("Ajout Magasinier");
		newstage.setResizable(false);
		newstage.setScene(scene);
		newstage.show();
	}
	
	public void modifCaissier(Stage newstage) throws IOException {
		URL ressource = Main.class.getResource("../view/Modifier_caissier.fxml"); 
		Parent root = FXMLLoader.load(ressource);
		Scene scene = new Scene(root);
		ref2=newstage;
		ref2.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
		newstage.setScene(scene);
		newstage.setTitle("Modification Caissier");
		newstage.setResizable(false);
		newstage.setScene(scene);
		newstage.show();
	}
	
	public void modifFourinisseur(Stage newstage) throws IOException {
		URL ressource = Main.class.getResource("../view/Modifier_fournisseur.fxml"); 
		Parent root = FXMLLoader.load(ressource);
		Scene scene = new Scene(root);
		ref2=newstage;
		ref2.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
		newstage.setScene(scene);
		newstage.setTitle("Modification Fournisseur");
		newstage.setResizable(false);
		newstage.setScene(scene);
		newstage.show();
	}
	
	public void modifMagasinier(Stage newstage) throws IOException {
		URL ressource = Main.class.getResource("../view/Modifier_magasinier.fxml"); 
		Parent root = FXMLLoader.load(ressource);
		Scene scene = new Scene(root);
		ref2=newstage;
		ref2.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
		newstage.setScene(scene);
		newstage.setTitle("Modification Magasinier");
		newstage.setResizable(false);
		newstage.setScene(scene);
		newstage.show();
	}
	
	public void ajoutClient(Stage newstage) throws IOException {
		URL ressource = Main.class.getResource("../view/Ajout_client .fxml"); 
		Parent root = FXMLLoader.load(ressource);
		Scene scene = new Scene(root);
		ref2=newstage;
		ref2.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
		newstage.setScene(scene);
		newstage.setTitle("Ajout d'un client");
		newstage.setResizable(false);
		newstage.setScene(scene);
		newstage.show();
	}
	
	public void ajoutCommande(Stage newstage) throws IOException {
		// TODO Auto-generated method stub
		URL ressource = Main.class.getResource("../view/Ajout_commande.fxml"); 
		Parent root = FXMLLoader.load(ressource);
		Scene scene = new Scene(root);
		ref2=newstage;
		ref2.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
		newstage.setScene(scene);
		newstage.setTitle("Ajout d'une commande");
		newstage.setResizable(false);
		newstage.setScene(scene);
		newstage.show();
	}
	
	public void AfficheCaracteristique(Stage newstage) throws IOException {
		URL ressource = Main.class.getResource("../view/Produit_caractéristiques.fxml"); 
		Parent root = FXMLLoader.load(ressource);
		Scene scene = new Scene(root);
		ref2=newstage;
		ref2.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
		newstage.setScene(scene);
		newstage.setTitle("Caracteristique du produit");
		newstage.setResizable(false);
		newstage.setScene(scene);
		newstage.show();
	}
	
	public void GestionFacture(Stage newStage) throws IOException {
		URL ressource = Main.class.getResource("../view/GestionFacture.fxml"); 
		Parent root = FXMLLoader.load(ressource);
		Scene scene = new Scene(root);
		ref1=newStage;
		ref1.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
		ref1.setScene(scene);
		ref1.setTitle("Factures");
		ref1.setResizable(true);
		ref1.setMaximized(true);;
		ref1.setScene(scene);
		ref1.show();
	}
	
	public static void printError(String title,String headerText,String contentText) {
		Alert error = new Alert(AlertType.ERROR);
		if(title == null || contentText == null ) 
			System.out.println("Erreur dans printError");
		else{
			error.setTitle(title);
			error.setHeaderText(headerText);
			error.setContentText(contentText);
			
			Stage dStage = (Stage)(error.getDialogPane().getScene().getWindow());
			dStage.getIcons().add(new Image("/view/error.jpg"));
			
			error.showAndWait();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		// TODO Auto-generated method stub
				// Ceci est une phase de test sur toutes les tables
				//On commence par les tables faciles. Pas de rï¿½fï¿½rence
				/*ClientDAO clientDAO = new ClientDAO();
				Client client = new Client("Franck Fuller");
				clientDAO.create(client);
				System.out.println(client);
				client.setTel("691855114");
				client.setAdresse("Damas, Yaoundï¿½ Cameroun");
				client.setBonus(500);
				clientDAO.update(client);
				System.out.println(client);
				
				CategorieDAO catDAO = new CategorieDAO();
				Categorie cat = new Categorie("Produits laitiers");
				catDAO.create(cat);
				System.out.println(cat);
				
				CategorieDAO categorieDAO = new CategorieDAO();
				Categorie categorie = categorieDAO.findForName("Produits laitiers");
				System.out.println(categorie);
				categorie.setNomCat("Lait et produits laitiers");
				categorieDAO.update(categorie);
				System.out.println(categorie);
				categorie.setNomCat("Boissons");
				categorieDAO.create(categorie);
				System.out.println(categorie);
				
				FournisseurDAO fournisseurDAO = new FournisseurDAO();
				Fournisseur fournisseur = new Fournisseur("Harold");
				fournisseurDAO.create(fournisseur);
				fournisseur.setAdresse("Melen, Yaoundï¿½ Cameroun");
				fournisseur.setTel("699892306");
				fournisseurDAO.update(fournisseur);
				System.out.println(fournisseur);
				
				GestionnaireDAO gestionnaireDAO = new GestionnaireDAO();
				Gestionnaire gestionnaire = new Gestionnaire("Cyrille", (byte)3, "Kenfack", "17p043", true);
				gestionnaireDAO.create(gestionnaire);
				System.out.println(gestionnaire);
				gestionnaire.setActif(false);
				gestionnaireDAO.update(gestionnaire);
				System.out.println(gestionnaire);
				gestionnaire.setNomGest("Kenmogne");
				gestionnaire.setTypeGest((byte)0);
				gestionnaire.setLogin("Lea Astrid");
				gestionnaire.setLogin("17p022");
				gestionnaire.setActif(false);
				gestionnaireDAO.create(gestionnaire);
				System.out.println(gestionnaire);
				
				// Table legeremment compliquï¿½e
				ProduitDAO produitDAO = new ProduitDAO();
				Produit produit = new Produit("Coca Cola Zï¿½ro", 500, 450, 450, null, String.valueOf((int)gestionnaire.getIdGest()), null, true, categorie);
				produitDAO.create(produit);
				produit.setQte(500);
				produitDAO.update(produit);*/
	}

}
