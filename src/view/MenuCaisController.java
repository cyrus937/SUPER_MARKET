package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.Timestamp;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.fxml.Initializable;
import modele.*;
import net.sf.jasperreports.engine.JRException;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import application.Main;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;


public class MenuCaisController implements Initializable {
		
	
		@FXML
		private Label user_name;
		
		
		
		@FXML
	    private JFXButton Deconnexion;	
		
		//Produit
		@FXML
		private JFXTreeTableView<Produit> tableProduit= new JFXTreeTableView<Produit>();
	
		@FXML
		private TreeTableColumn<Produit, Integer> identifiantProduit= new TreeTableColumn<Produit, Integer>();
	
		@FXML
		private TreeTableColumn<Produit, String> nomProduit= new TreeTableColumn<Produit, String>();
		
		@FXML
		private TreeTableColumn<Produit, Double> prixVenteProduit= new TreeTableColumn<Produit, Double>();

		@FXML
		private TreeTableColumn<Produit, Double> qteProduit= new TreeTableColumn<Produit, Double>();
		
		@FXML
		private TreeTableColumn<Produit, String> categorieProduit= new TreeTableColumn<Produit, String>();

		@FXML
		private JFXTextField rechercheProduitTextfield;
		
		@FXML
		private JFXTextField codeProTextfield;
		
		private ObservableList<Produit> produits=FXCollections.observableArrayList();

		private ProduitDAO produitDAO=new ProduitDAO();
		
		private Produit produit;

		private ArrayList<Produit> listProduits=new ArrayList<Produit>();

		
	    // Categorie
	    @FXML
		private JFXTreeTableView<Categorie> treeTableCategorie=new JFXTreeTableView<Categorie>();

		@FXML
		private TreeTableColumn<Categorie, Short> identifiantCategorie=new TreeTableColumn<Categorie, Short>("cat");

		@FXML
		private TreeTableColumn<Categorie, String> nomCategorie=new TreeTableColumn<Categorie, String>();
		
		@FXML
		private JFXTextField rechercheCategorieTextfield;

		private ObservableList<Categorie> categories=FXCollections.observableArrayList();

		private Categorie categorie;

		private CategorieDAO categorieDAO=new CategorieDAO();

		private ArrayList<Categorie> listCategories=new ArrayList<Categorie>();


		//Client
		
		@FXML
		private JFXTreeTableView<Client> treeTableClient=new JFXTreeTableView<Client>();

		@FXML
		private TreeTableColumn<Client, Integer> identifiantClient=new TreeTableColumn<Client, Integer>();

		@FXML
		private TreeTableColumn<Client, String> nomClient=new TreeTableColumn<Client, String>();
		
		@FXML
		private TreeTableColumn<Client, String> numeroClient=new TreeTableColumn<Client, String>();
		
		@FXML
		private TreeTableColumn<Client, String> adresseClient=new TreeTableColumn<Client, String>();
		
		@FXML
		private TreeTableColumn<Client, Integer> bonusClient=new TreeTableColumn<Client, Integer>();
		
		@FXML
		private TreeTableColumn<Client, Boolean> actifClient= new TreeTableColumn<Client, Boolean>();

	    @FXML
	    private JFXButton ajouterClient;
	    
		@FXML
		private JFXTextField rechercheClientTextfield;

		
		static ObservableList<Client> clients=FXCollections.observableArrayList();

		private Client client;

		private ClientDAO clientDAO=new ClientDAO();

		private ArrayList<Client> listClients=new ArrayList<Client>();

				
		//Facture
		@FXML
		private JFXTextField idClientTextfield;

		@FXML
		private JFXTextField remiseTextfield;

		@FXML
		private JFXTextField nomProTextfield;

		@FXML
		private JFXTextField qteProTextfield;
		
		@FXML
		private JFXTextField regleTextField;
		
		@FXML
		private Label qteStockLabel;
		
		@FXML
		private Label nomProLabel;
		
		@FXML
		private Label prixProLabel;
		
		@FXML
		private Label total_label;
		
		@FXML
		private Label netAPayer_label;

		@FXML
		private Label rendu_label;

		@FXML
		private JFXComboBox<String> modeComboBox;

		@FXML
		private JFXToggleButton statutToggleButton;

		@FXML
		private JFXComboBox<String> categorieComboBox;

		@FXML
		private JFXTextField searchProduit;
		
		@FXML
		private JFXTreeTableView<LigneFacture> treeTableLigneFacture=new JFXTreeTableView<LigneFacture>();

		@FXML
		private TreeTableColumn<LigneFacture, String> codePro=new TreeTableColumn<LigneFacture, String>();

		@FXML
		private TreeTableColumn<LigneFacture, String> nomPro= new TreeTableColumn<LigneFacture, String>();
	    
		@FXML
		private TreeTableColumn<LigneFacture, Double> prixPro= new TreeTableColumn<LigneFacture, Double>();
		
		@FXML
		private TreeTableColumn<LigneFacture, Double> qtePro= new TreeTableColumn<LigneFacture, Double>();
		
		@FXML
		private TreeTableColumn<LigneFacture, Double> sousTotal= new TreeTableColumn<LigneFacture, Double>();
		
		@FXML
		private JFXButton AnnulerProduit;
    
		@FXML
		private JFXButton ValiderProduit;
		
		@FXML
		private JFXButton PlusUnProduit;
		
		@FXML
		private JFXButton MoinsUnProduit;
    
	    @FXML
	    private JFXButton AppercuFacture;
    
	    @FXML
	    private JFXButton ValiderFacture;
	
	    @FXML
	    private JFXButton AnnulerFacture;
	    
	    @FXML
	    private JFXButton SupprimerLigneFacture;
	    
	    
	    private ObservableList<LigneFacture> ligneFactures =FXCollections.observableArrayList();

		private LigneFacture ligneFacture;
		
		private Facture facture;
		
		private LigneFactureDAO ligneFactureDAO = new LigneFactureDAO();

		private FactureDAO factureDAO=new FactureDAO();
		
		private FactureAImpimer factureAImprimer = new FactureAImpimer();

		//private ArrayList<LigneFacture> listLigneFacture=new ArrayList<LigneFacture>();

		
	    
	    //Commande 
	    @FXML
		private JFXTextField rechercheCommandeTextfield;
	    
	    @FXML
	    private JFXButton AjouterCommande;
	    
	    @FXML
	    private JFXButton ValiderCommande;
	    
	    @FXML
	    private JFXButton AnnulerCommande;
	    
	    @FXML
		private JFXTreeTableView<Commande> treeTableCommande=new JFXTreeTableView<Commande>();

		@FXML
		private TreeTableColumn<Commande, Integer> idClient=new TreeTableColumn<Commande, Integer>();

		@FXML
		private TreeTableColumn<Commande, String> nomCli=new TreeTableColumn<Commande, String>();
		
		@FXML
		private TreeTableColumn<Commande, String> commentaires=new TreeTableColumn<Commande, String>();
		
		@FXML
		private TreeTableColumn<Commande, Timestamp> dateCommande= new TreeTableColumn<Commande, Timestamp>();
		
		static ObservableList<Commande> commandes=FXCollections.observableArrayList();
		
		//static Commande commande1;

		private CommandeDAO commandeDAO=new CommandeDAO();

		private ArrayList<Commande> listCommandes=new ArrayList<Commande>();
		

		private ObservableList<String> modePaiementList=FXCollections.observableArrayList();
		
		private Main main=new Main();
		
		private TreeItem<Produit> caract; 
	    
	    static Integer g;
		/*static ObservableList<ObservableList> data=FXCollections.observableArrayList();
	    
	    private ConnexionBD connexionBD=new ConnexionBD();
	    
	    static TableView<?> tablerefFacture;
		static TableView<?> tablerefCategorie;
		static TableView<?> tablerefProduit;
		static TableView<?> tablerefCommande;
		static TableView<?> tablerefClient;*/
	    
	  //Gestion des factures

		@FXML
		private JFXTextField searchFactureTextField;

		@FXML
		private JFXTextField codeProduit;

		@FXML
		private JFXDatePicker dateSup;

		@FXML
		private JFXDatePicker dateInf;

		@FXML
		private JFXTreeTableView<Facture> tableViewFacture= new JFXTreeTableView<Facture>();

		@FXML
		private TreeTableColumn<Facture, Integer> idFacture;

		@FXML
		private TreeTableColumn<Facture, Timestamp> dateFacture;

		@FXML
		private TreeTableColumn<Facture, String> codePaiement;

		@FXML
		private TreeTableColumn<Facture, Double> remise;

		@FXML
		private TreeTableColumn<Facture, Double> montant;

		@FXML
		private TreeTableColumn<Facture, Boolean> modePaiement;
		
		@FXML
		private TreeTableColumn<Facture, Double> quantite;
		

		@FXML
		private TreeTableColumn<Facture,String > idCaissiere=new TreeTableColumn<Facture, String>();

		@FXML
		private TreeTableColumn<Facture, String> idClient_2;

		@FXML
		private TreeTableColumn<Facture, String> idCommande;

		@FXML
		private JFXTextField recette;

		@FXML
		private JFXButton validerButton;

		private FactureDAO factureDAO_2 = new FactureDAO();
		private ObservableList<Facture> factures=FXCollections.observableArrayList();
		private ArrayList<Facture> listFactures=new ArrayList<Facture>();
		private LigneFactureDAO ligneFactureDAO_2=new LigneFactureDAO();
		
		//ArrayList contenant tous les lignes factures d'un produit en particulier
		private ArrayList<LigneFacture> ligneFactures_2=new ArrayList<LigneFacture>();

		@FXML
		void handle_dateInf(KeyEvent event) throws SQLException {


			System.out.println(Date.valueOf(dateSup.getValue()));

			if (event.getCode().equals(KeyCode.ENTER)) {
				listFactures = factureDAO_2.findAllDates( Date.valueOf(dateSup.getValue()), Date.valueOf(dateInf.getValue()));
				System.out.println(listFactures);

				double recettes=0;
				for (Facture facture : listFactures) {
					recettes+=facture.getMontant()-((facture.getMontant()*facture.getRemise())/100);
				}

				factures.clear();
				factures.addAll(listFactures);
				recette.setText(String.valueOf(recettes));

			}
		}

		@FXML
		void handle_dateSup(KeyEvent event) {

			if (event.getCode().equals(KeyCode.ENTER)) {
				dateInf.requestFocus();
			}

		}
		
		@FXML
		void handle_codeProduit(KeyEvent event) throws NumberFormatException, SQLException {


		}
	    
	 // Liste qui sera utilis�e pour stocker une �l�ment � supprimer sur la table
	//	private ObservableList selection = FXCollections.observableArrayList();
		
		//private Main main=new Main();
	    
		@FXML
	    void handleAddClient(ActionEvent event) throws IOException {
			System.out.println("Love is good");
			Stage newStage = new Stage();
			main.ajoutClient(newStage);
	    }
		
		@FXML
		void handleSeeFacture(ActionEvent event) throws FileNotFoundException, JRException{
			factureAImprimer.setParametres(facture, 0);
			factureAImprimer.imprimer();
		}
		
		@FXML
	    void handleAddCommande(ActionEvent event) throws IOException {
			/*client = clientDAO.find(1000000);
			commande1 = new Commande(client);
			commandeDAO.create(commande1);
			listCommandes.add(commande1);*/
			Stage newStage = new Stage();
			main.ajoutCommande(newStage);
	    }
		
		@FXML
		void handleOkCommande(ActionEvent event) throws IOException, SQLException {
			
		}
		
		@FXML
		void handleCancelCommande(ActionEvent event) throws IOException, SQLException {
			
		}
		
		@FXML 
		void handleSupprimerLigneFacture(ActionEvent event) throws IOException,SQLException{
			TreeItem<LigneFacture> node = treeTableLigneFacture.getSelectionModel().getSelectedItem();
			LigneFacture ligne = node.getValue();
			if(node != null) {
				TreeItem<LigneFacture> parent = node.getParent();
				if(parent != null) {
					parent.getChildren().remove(node);
					facture.removeLigneFacture(ligne);
					SupprimerLigneFacture.setDisable(true);
					if(facture.getMontant() == 0) {
						handleCancelFacture(event);
					}
					else {
						total_label.setText(String.valueOf(facture.getMontant()));
					}
				}
			}
		}
		
		@FXML
		void handleCancelProduit(ActionEvent event) throws IOException, SQLException {
			codeProTextfield.setText("");
			//qteStockLabel.setText("ND");
			qteProTextfield.setText("");
			//qteStockLabel.setTextFill(Color.RED);
		}
		
		@FXML
		void handleOkProduit(ActionEvent event) throws IOException, SQLException {
			double qte = Double.valueOf(qteProTextfield.getText());
			ligneFacture = new LigneFacture(produit, null, produit.getPrixVente(), qte, produit.getPrixAchat()); 
			Boolean test = null;
			try {
				 test = ligneFacture.setFacture(facture);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
			if(test != null){
				if(test)
					treeTableLigneFacture.refresh();
				else
					ligneFactures.add(ligneFacture);
				total_label.setText(String.valueOf(facture.getMontant()));
				total_label.setTextFill(Color.GREEN);
				
				handleCancelProduit(event);
			}
			
		}
		
		@FXML
		void handlePlusUnQte(ActionEvent event) throws IOException, SQLException {
			double qte = Double.valueOf(qteProTextfield.getText());
			qteProTextfield.setText(String.valueOf(qte+1));
			
		}
		
		@FXML
		void handleMoinsUnQte(ActionEvent event) throws IOException, SQLException {
			double qte = Double.valueOf(qteProTextfield.getText());
			qteProTextfield.setText(String.valueOf(qte-1));
		}
		
		@FXML
		void handleOkFacture(ActionEvent event) throws IOException, SQLException, JRException {
			facture.setClient(clientDAO.find(Integer.valueOf(idClientTextfield.getText())));
			//facture.
			facture.validerFacture();
			factureDAO.create(facture);
			System.out.println("MenuCaisController " + facture.getMontant());
			factures.add(factureDAO.lastInser());
			tableViewFacture.refresh();
			System.out.println("MenuCaisController " + facture.getMontant());
			for(LigneFacture temp : ligneFactures)
				ligneFactureDAO.create(temp);
			factureAImprimer.setParametres(facture, Double.valueOf(regleTextField.getText()));
			factureAImprimer.imprimer();
			//System.out.println(facture.getMontant());
			
			
			handleCancelFacture(event);
		}
		
		//gestion de selection des factures
		static TreeItem<Facture> factureAffiche;
		Stage stage=new Stage();
		static int idFactureAffiche;
		
		@FXML
		void handleSelectedItem(MouseEvent event) throws IOException {

			factureAffiche = tableViewFacture.getSelectionModel().getSelectedItem();
			System.out.println(factureAffiche.getValue());
			idFactureAffiche=factureAffiche.getValue().getIdFac();
			
			URL ressource = Main.class.getResource("../view/ficheFacture.fxml"); 
			Parent root = FXMLLoader.load(ressource);
			Scene scene = new Scene(root);
			stage.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
			stage.setScene(scene);
			stage.setTitle("ficheFacture");
			stage.setMaximized(true);
			stage.setResizable(true);
			stage.setScene(scene);
			stage.show();

		}
		
		@FXML
		void handleCancelFacture(ActionEvent event) throws IOException, SQLException {
			handleCancelProduit(event);
			idClientTextfield.setText(String.valueOf(ClientDAO.DEFAULT_CLIENT_ID));
			facture.annulerFacture();
			if (!modeComboBox.getValue().equals("CASH"))	{
				modeComboBox.setValue("CASH");
			}
			total_label.setText("ND");
			total_label.setTextFill(Color.RED);
			rendu_label.setText("ND");
			rendu_label.setTextFill(Color.RED);
			ligneFactures.clear();
			regleTextField.clear();
		}
		
		@FXML
	    void handleDeconnexionButton(ActionEvent event) throws IOException {
			
			idFactureAffiche = 0 ;
			factureAffiche = null ;
	    	main.getStage().close();
	    	Stage primarynewStage = new Stage();
			main.Login(primarynewStage);
	    }

		
		public void initialize(URL location, ResourceBundle resources) {

			user_name.setText(LoginController.ges);
			AppercuFacture.setDisable(true);
			factureAImprimer.init();
			//Pour gérer le code du produit
			total_label.textProperty().addListener((observable,oldvalue,newvalue) ->{
				if(newvalue.equals("ND")){
					netAPayer_label.setText("ND");
					netAPayer_label.setTextFill(Color.RED);
				}
				else{
					netAPayer_label.setText(newvalue);
					netAPayer_label.setTextFill(Color.GREEN);
				}
			});
			codeProTextfield.textProperty().addListener((observable,oldvalue,newvalue) -> {
				if(newvalue.matches("[0-9]*")) {
					if(newvalue.length() == 6) {
		    		   produit = produitDAO.find(Integer.valueOf(newvalue));
		    		   if(produit != null) {
		    			   qteStockLabel.setText(String.valueOf(produit.getQte()));
		    			   nomProLabel.setText(String.valueOf(produit.getNomPro()));
		    			   prixProLabel.setText(String.valueOf(produit.getPrixVente()));
		    			   if(produit.getQte() <= 0) {
		   						qteStockLabel.setTextFill(Color.RED);
		    			   		nomProLabel.setTextFill(Color.RED);
		    			   		prixProLabel.setTextFill(Color.RED);
		    			   }else {
		   						qteStockLabel.setTextFill(Color.GREEN);
		    			   		nomProLabel.setTextFill(Color.GREEN);
		    			   		prixProLabel.setTextFill(Color.GREEN);
		    			   		qteProTextfield.setText("1");
		    		   	  }
		    		   }
		    	   }
		    	   else {
		    		   qteStockLabel.setText("ND");
			    	   qteStockLabel.setTextFill(Color.RED);
			    	   nomProLabel.setText("ND");
			    	   nomProLabel.setTextFill(Color.RED);
			    	   prixProLabel.setText("ND");
			    	   prixProLabel.setTextFill(Color.RED);
			    	   ValiderProduit.setDisable(true);
			    	   PlusUnProduit.setDisable(true);
			    	   MoinsUnProduit.setDisable(true);
		    	   }
		       }
		       else {
		    	   codeProTextfield.setText(oldvalue);
		    	   qteStockLabel.setText("ND");
		    	   qteStockLabel.setTextFill(Color.RED);
		    	   nomProLabel.setText("ND");
		    	   nomProLabel.setTextFill(Color.RED);
		    	   prixProLabel.setText("ND");
		    	   prixProLabel.setTextFill(Color.RED);
		    	   ValiderProduit.setDisable(true);
		    	   PlusUnProduit.setDisable(true);
		    	   MoinsUnProduit.setDisable(true);
		       }
		    });
			
			//Pour gerer la quantité du produit
			
			qteProTextfield.textProperty().addListener((observable,oldvalue,newvalue) -> {
				if(!qteStockLabel.getText().equals("ND")) {
					if((!produit.isQuantifiable() && newvalue.matches("[0-9]*\\.?[0-9]*")) || (produit.isQuantifiable() && newvalue.matches("[0-9]*"))) {
						if (!newvalue.isEmpty()) {
							double qte = Double.valueOf(newvalue);
							if (qte <= produit.getQte()) {
								if (qte < produit.getQte() && qte>0) {
									PlusUnProduit.setDisable(false);
							    	MoinsUnProduit.setDisable(false);
								}
								if(qte==0) {
									MoinsUnProduit.setDisable(true);
									PlusUnProduit.setDisable(false);
								}
								if (qte == produit.getQte()) {
									MoinsUnProduit.setDisable(false);
									PlusUnProduit.setDisable(true);
								}
								qteStockLabel.setText(String.valueOf(produit.getQte() - qte));
								ValiderProduit.setDisable(false);
								
							} else {
								qteProTextfield.setText(oldvalue);
							} 
						} 
						else {
							qteStockLabel.setText(String.valueOf(produit.getQte()));
							ValiderProduit.setDisable(true);
							PlusUnProduit.setDisable(true);
					    	MoinsUnProduit.setDisable(true);
						}
					}
					else {
						qteProTextfield.setText(oldvalue);
					}	
				}
				else {
					PlusUnProduit.setDisable(true);
					MoinsUnProduit.setDisable(true);
					qteProTextfield.setText("");
					ValiderProduit.setDisable(true);
				}
		    });
			
			regleTextField.textProperty().addListener((observable,oldvalue,newvalue) -> {
				System.out.println("New" + newvalue);
				System.out.println("Old" + oldvalue);
				if(!total_label.getText().equals("ND")) {
					if(newvalue.matches("[0-9]+") ) {
						if(!newvalue.isEmpty()) {
							double regle = Double.valueOf(newvalue);
							if(regle >= facture.getMontant()) {
				    		   ValiderFacture.setDisable(false);
							   rendu_label.setText(String.valueOf(regle - facture.getMontant()));
				    		   rendu_label.setTextFill(Color.GREEN);
							}
							else {
								ValiderFacture.setDisable(true);
								 rendu_label.setText("ND");
					    		 rendu_label.setTextFill(Color.RED);
							}
						}
					}
					else {
						if(!newvalue.isEmpty())
							regleTextField.setText(oldvalue);
					}	
				}
				else {
					regleTextField.setText("");
					rendu_label.setText("ND");
					rendu_label.setTextFill(Color.RED);
				}
		    });
			
			ligneFactures.addListener((ListChangeListener.Change<? extends LigneFacture> change) -> {
				System.out.println("Entr�");
				if(ligneFactures.isEmpty()) {
					AppercuFacture.setDisable(true);
				}
				else {
					AppercuFacture.setDisable(false);
				}
			});
			
			//Client par défaut
			idClientTextfield.setText(String.valueOf(ClientDAO.DEFAULT_CLIENT_ID));
			
			//ComboBox des modes de Paiement
			modePaiementList.add("CASH");
			modePaiementList.add("E-MONEY");
			modeComboBox.setItems(modePaiementList);
			modeComboBox.setValue("CASH");
			
			//Initialisation de la facture
			facture = new Facture();
			client  = clientDAO.find(ClientDAO.DEFAULT_CLIENT_ID);
			facture.setClient(client);
			GestionnaireDAO gestionnaireDAO = new GestionnaireDAO();
			//Gestionnaire gestionnaire = gestionnaireDAO.find(LoginController.)
			facture.setCaissiere(gestionnaireDAO.find(LoginController.user));
			
			// Affichage et recherche pour la table categorie	
			listCategories=categorieDAO.findAll();
			if (listCategories == null)
				categories.addAll(new ArrayList<Categorie>());
			else
				categories.addAll(listCategories);
			TreeItem<Categorie> rootNodeCategorie=new RecursiveTreeItem<Categorie>(categories,RecursiveTreeObject::getChildren);
			identifiantCategorie.setCellValueFactory(new TreeItemPropertyValueFactory<Categorie, Short>("idCat"));
			nomCategorie.setCellValueFactory(new TreeItemPropertyValueFactory<Categorie, String>("nomCat"));		
			treeTableCategorie.setRoot(rootNodeCategorie);
			treeTableCategorie.setShowRoot(false);
			
			rechercheCategorieTextfield.textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					treeTableCategorie.setPredicate(new Predicate<TreeItem<Categorie>>() {

						@Override
						public boolean test(TreeItem<Categorie> t) {
							// TODO Auto-generated method stub
							Boolean flag= t.getValue().getNomCat().contains(newValue)||String.valueOf(t.getValue().getIdCat()).contains(newValue);
							return flag;

						}
					});
				}

			});
			
			// Affichage et recherche pour la table produit
			listProduits=produitDAO.findAll();
			if (listProduits ==  null)
				produits.addAll(new ArrayList<Produit>());
			else
				produits.addAll(listProduits);
			TreeItem<Produit> rootNodeProduit=new RecursiveTreeItem<Produit>(produits,RecursiveTreeObject::getChildren);
			identifiantProduit.setCellValueFactory(new TreeItemPropertyValueFactory<Produit, Integer>("codePro"));
			nomProduit.setCellValueFactory(new TreeItemPropertyValueFactory<Produit, String>("nomPro"));
			prixVenteProduit.setCellValueFactory(new TreeItemPropertyValueFactory<Produit, Double>("prixVente"));
			qteProduit.setCellValueFactory(new TreeItemPropertyValueFactory<Produit, Double>("qte"));
			categorieProduit.setCellValueFactory(new TreeItemPropertyValueFactory<Produit, String>("categorie"));
			tableProduit.setRoot(rootNodeProduit);
			tableProduit.setShowRoot(false);
			
			rechercheProduitTextfield.textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					tableProduit.setPredicate(new Predicate<TreeItem<Produit>>() {

						@Override
						public boolean test(TreeItem<Produit> t) {
							// TODO Auto-generated method stub
							Boolean flag3= t.getValue().getNomPro().contains(newValue)|| 
									String.valueOf(t.getValue().getCodePro()).contains(newValue);
									//||t.getValue().getCodeFour().contains(newValue);
							return flag3;

						}
					});
				}

			});
			
			tableProduit.setOnMousePressed(e -> {
				if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
					caract = tableProduit.getSelectionModel().getSelectedItem();
					if (caract != null) {
						g = caract.getValue().getCodePro1();
						try {
							Stage newStage = new Stage();
							main.AfficheCaracteristique(newStage);
						} catch (IOException e1) {
							e1.printStackTrace();
						} 
					} 
				}
			});
			
			//Affichage pour la table Lignefacture
			treeTableLigneFacture.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					SupprimerLigneFacture.setDisable(false);
				}
			
			});
			TreeItem<LigneFacture> rootNodeLigneCommande =new RecursiveTreeItem<LigneFacture>(ligneFactures,RecursiveTreeObject::getChildren);
			codePro.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LigneFacture,String>, ObservableValue<String>>() {
				
				@Override
				public ObservableValue<String> call(CellDataFeatures<LigneFacture, String> param) {
					// TODO Auto-generated method stub
					return new SimpleObjectProperty<String>(param.getValue().getValue().getProduit().getCodePro());
				}
			});
			nomPro.setCellValueFactory(new TreeItemPropertyValueFactory<LigneFacture, String>("produit"));
			prixPro.setCellValueFactory(new TreeItemPropertyValueFactory<LigneFacture,Double>("prixVente"));
			qtePro.setCellValueFactory(new TreeItemPropertyValueFactory<LigneFacture, Double>("qte"));
			sousTotal.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LigneFacture,Double>, ObservableValue<Double>>() {
				
				@Override
				public ObservableValue<Double> call(CellDataFeatures<LigneFacture, Double> param) {
					// TODO Auto-generated method stub
					return new SimpleObjectProperty<Double>(param.getValue().getValue().getPrixVente() * param.getValue().getValue().getQte() );
				}
			});
			treeTableLigneFacture.setRoot(rootNodeLigneCommande);
			treeTableLigneFacture.setShowRoot(false);
			
			//Affichage et recherche pour la table commande	
			listCommandes=commandeDAO.findAll();
			if (listCommandes == null)
				commandes.addAll(new ArrayList<Commande>());
			else
				commandes.addAll(listCommandes);
			TreeItem<Commande> rootNodeCommandes=new RecursiveTreeItem<Commande>(commandes,RecursiveTreeObject::getChildren);
			idClient.setCellValueFactory(new TreeItemPropertyValueFactory<Commande, Integer>("client"));
			//nomCli.setCellValueFactory(new TreeItemPropertyValueFactory<Commande, String>("nomCat"));
			commentaires.setCellValueFactory(new TreeItemPropertyValueFactory<Commande, String>("commantaires"));
			dateCommande.setCellValueFactory(new TreeItemPropertyValueFactory<Commande, Timestamp>("date"));
			treeTableCommande.setRoot(rootNodeCommandes);
			treeTableCommande.setShowRoot(false);		
			rechercheCommandeTextfield.textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					treeTableCommande.setPredicate(new Predicate<TreeItem<Commande>>() {

						@Override
						public boolean test(TreeItem<Commande> t) {
							// TODO Auto-generated method stub
							Boolean flag = String.valueOf(t.getValue().getClient().getIdClient()).contains(newValue);
							return flag;

						}
					});
				}

			});
			
			// Affichage et recherche pour la table client
						listClients=clientDAO.findAll();
						if (listClients == null)
							clients.addAll(new ArrayList<Client>());
						else
							clients.addAll(listClients);
						TreeItem<Client> rootNodeClient=new RecursiveTreeItem<Client>(clients,RecursiveTreeObject::getChildren);
						identifiantClient.setCellValueFactory(new TreeItemPropertyValueFactory<Client, Integer>("idClient"));
						nomClient.setCellValueFactory(new TreeItemPropertyValueFactory<Client, String>("nom"));
						numeroClient.setCellValueFactory(new TreeItemPropertyValueFactory<Client, String>("tel"));
						adresseClient.setCellValueFactory(new TreeItemPropertyValueFactory<Client, String>("adresse"));
						bonusClient.setCellValueFactory(new TreeItemPropertyValueFactory<Client, Integer>("bonus"));
						actifClient.setCellValueFactory(new TreeItemPropertyValueFactory<Client, Boolean>("actif"));
						treeTableClient.setRoot(rootNodeClient);
						treeTableClient.setShowRoot(false);
						
						rechercheClientTextfield.textProperty().addListener(new ChangeListener<String>() {

							@Override
							public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
								// TODO Auto-generated method stub
								treeTableClient.setPredicate(new Predicate<TreeItem<Client>>() {

									@Override
									public boolean test(TreeItem<Client> t) {
										// TODO Auto-generated method stub
										Boolean flag3= t.getValue().getNom().contains(newValue)|| 
												String.valueOf(t.getValue().getIdClient()).contains(newValue);
												//||t.getValue().getCodeFour().contains(newValue);
										return flag3;

									}
								});
							}

						});
						
						//Gestion factures
						
						double recettes=0;
						listFactures=factureDAO_2.findAll();

						if(listFactures == null)
							factures.addAll(new ArrayList());
						else {
							factures.addAll(listFactures);

							for (Facture facture : factures) {
								recettes+=facture.getMontant()-((facture.getMontant()*facture.getRemise())/100);
							}

							recette.setText(String.valueOf(recettes));
						}
						
						TreeItem<Facture> rootNodeFacture=new RecursiveTreeItem<Facture>(factures,RecursiveTreeObject::getChildren);
						idFacture.setCellValueFactory(new TreeItemPropertyValueFactory<Facture, Integer>("idFac"));
						dateFacture.setCellValueFactory(new TreeItemPropertyValueFactory<Facture, Timestamp>("dateFac"));
						codePaiement.setCellValueFactory(new TreeItemPropertyValueFactory<Facture, String>("codePaiement"));
						remise.setCellValueFactory(new TreeItemPropertyValueFactory<Facture, Double>("remise"));
						//montant.setCellValueFactory(new TreeItemPropertyValueFactory<Facture, Double>("montant"));
						montant.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Facture,Double>, ObservableValue<Double>>() {
							
							@Override
							public ObservableValue<Double> call(CellDataFeatures<Facture, Double> param) {
								// TODO Auto-generated method stub
								return new SimpleObjectProperty<Double>(param.getValue().getValue().getMontant());
							}
						});
						modePaiement.setCellValueFactory(new TreeItemPropertyValueFactory<Facture, Boolean>("modePaiement"));
						idCaissiere.setCellValueFactory(new TreeItemPropertyValueFactory<Facture, String>("caissiere"));
						idClient_2.setCellValueFactory(new TreeItemPropertyValueFactory<Facture, String>("client"));
						idCommande.setCellValueFactory(new TreeItemPropertyValueFactory<Facture, String>("commande"));
						
						quantite.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Facture,Double>, ObservableValue<Double>>() {
							
							@Override
							public ObservableValue<Double> call(CellDataFeatures<Facture, Double> param) {
								// TODO Auto-generated method stub
								
								return null;
							}
						});
						tableViewFacture.setRoot(rootNodeFacture);
						tableViewFacture.setShowRoot(false);


						searchFactureTextField.textProperty().addListener(new ChangeListener<String>() {

							@Override
							public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
								
								recette.setText("");
								tableViewFacture.setPredicate(new Predicate<TreeItem<Facture>>() {
									
									@Override
									public boolean test(TreeItem<Facture> t) {
										Boolean flag=String.valueOf(t.getValue().getIdFac()).contains(newValue) || String.valueOf(t.getValue().getDateFac()).contains(newValue) || String.valueOf(t.getValue().isModePaiement()).contains(newValue) || String.valueOf(t.getValue().getCaissiere()).contains(newValue) || String.valueOf(t.getValue().getClient()).contains(newValue) || String.valueOf(t.getValue().getCommande()).contains(newValue) ;
										return flag;
									}
								});

							}

						});

						codeProduit.textProperty().addListener(new ChangeListener<String>() {

							@Override
							public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

								if (newValue.isEmpty()) {
									factures.clear();
									recette.setText("");
									initialize(location, resources);
								}

								else if(newValue.length()==6){
									
									recette.setText("");
									System.out.println(codeProduit.getText());
									try {
										ligneFactures_2=ligneFactureDAO_2.findAllFactures(Integer.valueOf(codeProduit.getText()).intValue());
										
										quantite.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Facture,Double>, ObservableValue<Double>>() {
											
											@Override
											public ObservableValue<Double> call(CellDataFeatures<Facture, Double> param) {
												// TODO Auto-generated method stub
												
												return new SimpleObjectProperty<Double>(param.getValue().getValue().getQteProduit(Integer.valueOf(codeProduit.getText()).intValue(), param.getValue().getValue().getIdFac()) );
											}
										});
									
									
									} catch (NumberFormatException | SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									System.out.println(ligneFactures_2);
									//double recettes=0;
									factures.clear();
									//*
									for (LigneFacture ligneFacture : ligneFactures_2) {
										factures.add(ligneFacture.getFacture());
										//recettes+=ligneFacture.getPrixVente()*ligneFacture.getQte();
									}
									//*/
									TreeItem<Facture> rootNodeFacture=new RecursiveTreeItem<Facture>(factures,RecursiveTreeObject::getChildren);
									tableViewFacture.setRoot(rootNodeFacture);
									//recette.setText(String.valueOf(recettes));
									recette.setText("");
								}


							}
						});	

					
						dateInf.valueProperty().addListener(new ChangeListener<LocalDate>() {

							@Override
							public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
									LocalDate newValue) {
								
								if (newValue!=null) {
									try {
										listFactures=factureDAO_2.findAllDates( Date.valueOf(dateSup.getValue()), Date.valueOf(dateInf.getValue()));
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									System.out.println(listFactures);
									
									double recettes=0;
									for (Facture facture : listFactures) {
										recettes+=facture.getMontant()-((facture.getMontant()*facture.getRemise())/100);
									}
									
									factures.clear();
									factures.addAll(listFactures);
									recette.setText(String.valueOf(recettes));
								}
								
							}
						});
		}
    
}
    