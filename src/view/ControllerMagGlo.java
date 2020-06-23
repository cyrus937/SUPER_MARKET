package view;

import java.io.IOException;
import java.net.URL;
import java.security.Timestamp;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.fxml.Initializable;
import modele.*;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;

public class ControllerMagGlo implements Initializable{

	//
	
	 @FXML
	 private JFXButton deconnexion;
	 
	@FXML
	private JFXTextField searchCategorieTextField;

	@FXML
	private JFXTextField categorieTextField;

	@FXML
	private JFXButton addCategorieButton;

	@FXML
	private JFXTreeTableView<Categorie> treeTableCategorie=new JFXTreeTableView<Categorie>();

	@FXML
	private TreeTableColumn<Categorie, Short> identifiantCategorie=new TreeTableColumn<Categorie, Short>("cat");

	@FXML
	private TreeTableColumn<Categorie, String> nomCategorie=new TreeTableColumn<Categorie, String>();

	@FXML
	private JFXButton deleteCategorieButton;

	private ObservableList<Categorie> categories=FXCollections.observableArrayList();

	private Categorie categorie;

	private CategorieDAO categorieDAO=new CategorieDAO();

	private ArrayList<Categorie> listCategories=new ArrayList<Categorie>();

	//  

	//
	@FXML
	private JFXTextField nomProd;

	@FXML
	private JFXTextField qteProd;

	@FXML
	private JFXTextField prixVenteProd;

	@FXML
	private JFXTextField prixAchatProd;

	@FXML
	private JFXTextArea descriptionProd;

	@FXML
	private JFXComboBox<String> fournisseurComboBox;

	@FXML
	private JFXToggleButton etatToggleButton;
	
	@FXML
    private JFXDatePicker datePerem;
	
	@FXML
	private JFXToggleButton statutToggleButton;

	@FXML
	private JFXComboBox<String> categorieComboBox;

	@FXML
	private JFXTextField searchProduit;

	@FXML
	private JFXButton addProduitButton;

	@FXML
	private JFXTreeTableView<Produit> tableProduit= new JFXTreeTableView<Produit>();

	@FXML
	private TreeTableColumn<Produit, Integer> identifiantProd= new TreeTableColumn<Produit, Integer>();

	@FXML
	private TreeTableColumn<Produit, String> colNomProd= new TreeTableColumn<Produit, String>();

	@FXML
	private TreeTableColumn<Produit, Double> colPrixVenteProd= new TreeTableColumn<Produit, Double>();

	@FXML
	private TreeTableColumn<Produit, Double> colPrixAchatProd= new TreeTableColumn<Produit, Double>();

	@FXML
	private TreeTableColumn<Produit, Double> colQuantiteProd= new TreeTableColumn<Produit, Double>();

	@FXML
	private TreeTableColumn<Produit, String> colFournisseurProd= new TreeTableColumn<Produit, String>();

	@FXML
	private TreeTableColumn<Produit, Timestamp> colDateProd= new TreeTableColumn<Produit, Timestamp>();

	@FXML
	private TreeTableColumn<Produit, Boolean> colActifProd= new TreeTableColumn<Produit, Boolean>();

	@FXML
	private TreeTableColumn<Produit, String> colCategorieProd= new TreeTableColumn<Produit, String>();
	
	@FXML
	private TreeTableColumn<Produit, Timestamp> colPeremptionProd= new TreeTableColumn<Produit, Timestamp>();

	@FXML
	private TreeTableColumn<Produit, Boolean> colQuantifiableProd= new TreeTableColumn<Produit, Boolean>();

	private ObservableList<Produit> produits=FXCollections.observableArrayList();

	private ObservableList<String> nomCategorieList=FXCollections.observableArrayList();

	private GestionnaireDAO gestionnaireDAO=new GestionnaireDAO();
	
	private ObservableList<String> nomFournisseurList=FXCollections.observableArrayList();

	private Produit produit;

	private ProduitDAO produitDAO=new ProduitDAO();

	private ArrayList<Produit> listProduits=new ArrayList<Produit>();

	//  

	//

	@FXML
	private JFXTextField quantiteStock;

	@FXML
	private JFXToggleButton operationToggleButton;

	@FXML
	private JFXComboBox<String> codeProduitComboBox;

	@FXML
	private JFXButton addStockButton;

	@FXML
	private JFXTextField searchStock;

	@FXML
	private JFXTreeTableView<GestionStock> tableStock;

	@FXML
	private TreeTableColumn<GestionStock, Integer> identifiantStock;

	@FXML
	private TreeTableColumn<GestionStock, Double> colQuantiteStock;

	@FXML
	private TreeTableColumn<GestionStock, Timestamp> colDateStock;

	@FXML
	private TreeTableColumn<GestionStock, Boolean> colOperationStock;

	@FXML
	private TreeTableColumn<GestionStock, String> colGestionnaireStock;

	@FXML
	private TreeTableColumn<GestionStock, String> colProduitStock;

	private ObservableList<GestionStock> gestionStocks=FXCollections.observableArrayList();

	private ObservableList<String> codeProduitList=FXCollections.observableArrayList();
	
	private GestionStock gestionStock;

	private GestionStockDAO gestionStockDAO=new GestionStockDAO();

	private ArrayList<GestionStock> listGestionStocks=new ArrayList<GestionStock>();
	
	
	//  


	//

	@FXML
	private JFXTextField nomFournisseur;

	@FXML
	private JFXTextField telFournisseur;

	@FXML
	private JFXButton addFournisseurButton;

	@FXML
	private JFXTextField adresseFournisseur;

	@FXML
	private JFXTextField searchFournisseur;

	@FXML
	private JFXTreeTableView<Fournisseur> tableFournisseur;

	@FXML
	private TreeTableColumn<Fournisseur, Short> identifiantFournisseur;

	@FXML
	private TreeTableColumn<Fournisseur, String> colNomFourn;

	@FXML
	private TreeTableColumn<Fournisseur, String> colAdresseFourn;

	@FXML
	private TreeTableColumn<Fournisseur, String> colTelFourn;
	
	private ObservableList<Fournisseur> fournisseurs=FXCollections.observableArrayList();

	private Fournisseur fournisseur;

	private FournisseurDAO fournisseurDAO=new FournisseurDAO();

	private ArrayList<Fournisseur> listFournisseurs=new ArrayList<Fournisseur>();

	private Main main = new Main();
	//

	@FXML
	private Label label = new Label();
	
	@FXML
	void handleAddFournisseurButton(ActionEvent event) {

		fournisseur=new Fournisseur(nomFournisseur.getText(), adresseFournisseur.getText(), telFournisseur.getText());
		fournisseurDAO.create(fournisseur);	    	
		listFournisseurs.add(fournisseur);
		fournisseurs.add(fournisseur);
		nomFournisseur.setText("");
		adresseFournisseur.setText("");
		telFournisseur.setText("");
		
		//mise a jour du comboBox fournisseur dans les autres tables
		nomFournisseurList.add(fournisseur.getNom());
		
	}

	@FXML
	void handleAddProduitButton(ActionEvent event) {

		//on cherche la categorie associï¿½ au nom dans le combobox
		Categorie newCategorie=null;
		for (Categorie categorie : categories) {
			if (categorie.getNomCat()==categorieComboBox.getValue()) {
				newCategorie=categorie;
			}
		}
		
		if( datePerem.getValue() == null)
		{
			Main.printError("Erreur lors de l'ajout ", null,"Entrez une date de péremption");
			return;
		}
		
		produit=new Produit(nomProd.getText(),  Double.valueOf(prixVenteProd.getText()).doubleValue(),
				Double.valueOf(prixAchatProd.getText()).doubleValue(),etatToggleButton.isSelected(), 0,
				Date.valueOf(datePerem.getValue()),descriptionProd.getText(),
				fournisseurComboBox.getValue(), null, statutToggleButton.isSelected(),newCategorie);	    	
		produitDAO.create(produit);
		listProduits.add(produit);
		produits.add(produit);
		nomProd.setText("");
		prixAchatProd.setText("");
		prixVenteProd.setText("");
		datePerem.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		descriptionProd.setText("");
		
		//mise ï¿½ jour du comboBox codeProduit dans les autres tables
		codeProduitList.add(produit.getNomPro());
		
	}

	@FXML
	void handleAddStockButton(ActionEvent event) {
		double n,m;
		Produit newProduit=null;
		System.out.println(produits);
		for (Produit produit : produits) {
			System.out.println("oui");
			System.out.println(codeProduitComboBox.getValue());
			if (produit.getNomPro().equals(codeProduitComboBox.getValue()) ) {
				System.out.println("je suis là");
				System.out.println(produit);
				newProduit=produit;
				
			}
		}
		System.out.println(LoginController.user);		
		gestionStock=new GestionStock(Double.valueOf(quantiteStock.getText()), operationToggleButton.isSelected(), gestionnaireDAO.find(LoginController.user), newProduit);
		
		m = gestionStock.getQte();
		System.out.println(newProduit);
		n = newProduit.getQte();
		
		if((!operationToggleButton.isSelected()) && m > n)
		{
			Main.printError("Erreur lors de l'ajout ", null,"la quantite retirer depasse celle qui est presente");
			return;
		}
		if((!operationToggleButton.isSelected()) && m < n)
		{
			gestionStockDAO.create(gestionStock);
			listGestionStocks.add(gestionStock);
			gestionStocks.add(gestionStock);
			quantiteStock.setText("");
			newProduit.setQte(n - m);
		}
		if(operationToggleButton.isSelected())
		{
			gestionStockDAO.create(gestionStock);
			listGestionStocks.add(gestionStock);
			gestionStocks.add(gestionStock);
			quantiteStock.setText("");
			newProduit.setQte(n + m);
		}
		produitDAO.update(newProduit);
		listProduits=produitDAO.findAll();
		produits.setAll(listProduits);
	}


	@FXML
	void handleAddCategorieButton(ActionEvent event) {
		categorie=new Categorie(categorieTextField.getText());
		categorieDAO.create(categorie);	    	
		listCategories.add(categorie);
		categories.add(categorie);
		categorieTextField.setText("");

		//mise ï¿½ jour du comboBox categorie dans les autres tables
		nomCategorieList.add(categorie.getNomCat());
	}
	
	 @FXML
	    void handleDeconnexionButton(ActionEvent event) throws IOException {
		 	main.getStage().close();
	    	Stage primarynewStage = new Stage();
			main.Login(primarynewStage);
	    }


	 @FXML
	    void handleOnSelectionChanged(ActionEvent event) {
		 
		 	if(etatToggleButton.isSelected())
		 		etatToggleButton.setText("Countable");
		 	else
		 		etatToggleButton.setText("Uncountable");
		 	
		 	if(operationToggleButton.isSelected())
		 		operationToggleButton.setText("Ajout");
		 	else
		 		operationToggleButton.setText("Retrait");
		 	
		 	if(statutToggleButton.isSelected())
				statutToggleButton.setText("Actif");
			else
				statutToggleButton.setText("Inactif");
	    }



	@Override
	public void initialize(URL location, ResourceBundle resources) {

		label.setText(LoginController.ges);
		//Affichage et recherche pour la table GestionStock
		
		listGestionStocks=gestionStockDAO.findAll();
		if(listGestionStocks == null)
			gestionStocks.addAll(new ArrayList<GestionStock>());
		else
			gestionStocks.addAll(listGestionStocks);
		
		TreeItem<GestionStock> rootNodeGestionStock=new RecursiveTreeItem<GestionStock>(gestionStocks,RecursiveTreeObject::getChildren);
		identifiantStock.setCellValueFactory(new TreeItemPropertyValueFactory<GestionStock, Integer>("idStock"));
		colQuantiteStock.setCellValueFactory(new TreeItemPropertyValueFactory<GestionStock, Double>("qte"));
		colDateStock.setCellValueFactory(new TreeItemPropertyValueFactory<GestionStock, Timestamp>("dateStock"));
		colOperationStock.setCellValueFactory(new TreeItemPropertyValueFactory<GestionStock, Boolean>("operation"));
		colGestionnaireStock.setCellValueFactory(new TreeItemPropertyValueFactory<GestionStock, String>("gest"));
		colProduitStock.setCellValueFactory(new TreeItemPropertyValueFactory<GestionStock, String>("produit"));
		tableStock.setRoot(rootNodeGestionStock);
		tableStock.setShowRoot(false);
		searchStock.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				tableStock.setPredicate(new Predicate<TreeItem<GestionStock>>() {

					@Override
					public boolean test(TreeItem<GestionStock> f) {
						// TODO Auto-generated method stub
						Boolean flag2=String.valueOf(f.getValue().getIdStock()).contains(newValue) || String.valueOf(f.getValue().getProduit().getNomPro()).contains(newValue);
						return flag2;

					}
				});
			}

		});
		
		
		
		
		
		
		//Affichage et recherche pour la table fournisseur
				
			listFournisseurs=fournisseurDAO.findAll();
			if (listFournisseurs == null)
				fournisseurs.addAll(new ArrayList<Fournisseur>());
			else
				fournisseurs.addAll(listFournisseurs);
			TreeItem<Fournisseur> rootNodeFournisseur=new RecursiveTreeItem<Fournisseur>(fournisseurs,RecursiveTreeObject::getChildren);
			identifiantFournisseur.setCellValueFactory(new TreeItemPropertyValueFactory<Fournisseur, Short>("idFournisseur"));
			colNomFourn.setCellValueFactory(new TreeItemPropertyValueFactory<Fournisseur, String>("nom"));
			colAdresseFourn.setCellValueFactory(new TreeItemPropertyValueFactory<Fournisseur, String>("adresse"));
			colTelFourn.setCellValueFactory(new TreeItemPropertyValueFactory<Fournisseur, String>("tel"));
			tableFournisseur.setRoot(rootNodeFournisseur);
			tableFournisseur.setShowRoot(false);
			searchFournisseur.textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					tableFournisseur.setPredicate(new Predicate<TreeItem<Fournisseur>>() {

						@Override
						public boolean test(TreeItem<Fournisseur> f) {
							// TODO Auto-generated method stub
							Boolean flag2= f.getValue().getNom().contains(newValue) || String.valueOf(f.getValue().getIdFournisseur()).contains(newValue);
							return flag2;

						}
					});
				}

			});
		
		
		
		
		
		
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
		searchCategorieTextField.textProperty().addListener(new ChangeListener<String>() {

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
		if (listProduits == null)
			produits.addAll(new ArrayList<Produit>());
		else
			produits.addAll(listProduits);
		TreeItem<Produit> rootNodeProduit=new RecursiveTreeItem<Produit>(produits,RecursiveTreeObject::getChildren);
		identifiantProd.setCellValueFactory(new TreeItemPropertyValueFactory<Produit, Integer>("codePro"));
		colNomProd.setCellValueFactory(new TreeItemPropertyValueFactory<Produit, String>("nomPro"));
		colPrixAchatProd.setCellValueFactory(new TreeItemPropertyValueFactory<Produit, Double>("prixAchat"));
		colPrixVenteProd.setCellValueFactory(new TreeItemPropertyValueFactory<Produit, Double>("prixVente"));
		colQuantifiableProd.setCellValueFactory(new TreeItemPropertyValueFactory<Produit, Boolean>("quantifiable"));
		colQuantiteProd.setCellValueFactory(new TreeItemPropertyValueFactory<Produit, Double>("qte"));
		colPeremptionProd.setCellValueFactory(new TreeItemPropertyValueFactory<Produit, Timestamp>("datePeremption"));
		colFournisseurProd.setCellValueFactory(new TreeItemPropertyValueFactory<Produit, String>("codeFour"));
		colDateProd.setCellValueFactory(new TreeItemPropertyValueFactory<Produit, Timestamp>("dateInsertion"));
		colActifProd.setCellValueFactory(new TreeItemPropertyValueFactory<Produit, Boolean>("actif"));
		colCategorieProd.setCellValueFactory(new TreeItemPropertyValueFactory<Produit, String>("categorie"));
		// il faut revoir la gestion de idCategorie, il y a un pb !!!!!!!!
		tableProduit.setRoot(rootNodeProduit);
		tableProduit.setShowRoot(false);
		searchProduit.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				tableProduit.setPredicate(new Predicate<TreeItem<Produit>>() {

					@Override
					public boolean test(TreeItem<Produit> t) {
						// TODO Auto-generated method stub
						Boolean flag3= t.getValue().getNomPro().contains(newValue)||String.valueOf(t.getValue().getCodePro()).contains(newValue)
								||t.getValue().getCodeFour().contains(newValue)|| t.getValue().getCategorie().getNomCat().contains(newValue);
						return flag3;

					}
				});
			}

		});
		
		
		//gestion des comboBox
		for (Categorie cat: categories) {
			nomCategorieList.add(cat.getNomCat());
		}
		
		for (Fournisseur four: fournisseurs) {
			nomFournisseurList.add(four.getNom());
		}
		
		for (Produit prod : produits) {
			codeProduitList.add(prod.getNomPro());
		}
		
		codeProduitComboBox.setItems(codeProduitList);
		fournisseurComboBox.setItems(nomFournisseurList);
		categorieComboBox.setItems(nomCategorieList);

	}

}
