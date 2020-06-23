package view;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import modele.*;
import util.DateUtil;


public class StatistiquesController {
	@FXML
    private AreaChart<String, Double> barChart;
	
	@FXML
    private PieChart circle;

    @FXML
    private CategoryAxis xAxis = new CategoryAxis();
    
    @FXML
    private Label label;
    @FXML
	private Label venteJourneeLabel;
    @FXML
	private Label venteMoisLabel;
    @FXML
	private Label venteSemaineLabel;
    @FXML
	private Label venteAnneeLabel;
    
    //Details Ventes.
    @FXML
    private ComboBox<Categorie> categorieBox;
    @FXML
    private ComboBox<Produit> produitBox;
    @FXML
    private DatePicker dateDebut;
    @FXML
    private DatePicker dateFin;
    @FXML
    private JFXButton afficheButton;
    @FXML
    private Label montantVenteLabel;
    @FXML
    private Label labus;
    
    //
    @FXML
    private ComboBox<String> moisBoxClient;
    @FXML
	private TreeTableView<Client> personTableClient;
    @FXML
	private TreeTableView<Gestionnaire> personTableCaissiere;
    @FXML
    private ComboBox<String> moisBoxCaissiere;
    @FXML
	private TreeTableColumn<Client, String> nomClientColumn;
    @FXML
	private TreeTableColumn<Gestionnaire, String> nomCaissiereColumn;
	@FXML
	private TreeTableColumn<Client, String> recetteClientColumn;
	@FXML
	private TreeTableColumn<Gestionnaire, String> montantVenduCaissiereColumn;
    
    
    private ObservableList<String> dateNames = FXCollections.observableArrayList();
    private ObservableList<Categorie> categorieList  = FXCollections.observableArrayList();
    private ObservableList<Produit> produitList  = FXCollections.observableArrayList();
    private ObservableList<String> monthNames = FXCollections.observableArrayList();
    private ObservableList<Client> personData =  FXCollections.observableArrayList();
    private ObservableList<Gestionnaire> personDataCaissiere =  FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    
    private ArrayList<Client> listeClient = new ArrayList<Client>();
    private ArrayList<Gestionnaire> listeCaissier = new ArrayList<Gestionnaire>();
    private Produit produit;
    private ProduitDAO produitDAO = new ProduitDAO();
    private Client client;
    private ClientDAO clientDAO = new ClientDAO();
    private Categorie categorie;
    private CategorieDAO categorieDAO = new CategorieDAO();
    private Facture facture;
    private FactureDAO factureDAO = new FactureDAO();
    private LigneFacture ligneFacture;
    private LigneFactureDAO ligneFactureDAO = new LigneFactureDAO();
    
    private Main main = new Main();
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	label.setText(LoginController.ges);
    	
    	showEvolution();
        showVenteJournee();
        showVenteSemaine();
        showVenteMois();
        showVenteAnnee();
        showDetailsVentes();
        handleMontantVentes();
        initializeLesMeilleurs();
        meilleurClient();
        meilleurCaissiere();
        
    }
    
    /*
     * 
     */
    
    /*
     * Affiche les ventes de la journée
     */
    public void showVenteJournee() {
    	
    	double somme = 0.0;
	 	LocalDate date = LocalDate.now();
	 	String dateString;
	 	dateString = DateUtil.format(date);
	 	
		venteJourneeLabel.setText(Double.toString(factureDAO.sommeTotal(Date.valueOf(dateString)))+" Fcfa");
    }
    
    
    /*
     * Affiche les ventes de la semaine
     */
    public void showVenteSemaine() {
    	
    	double somme = 0.0;
	 	LocalDate date = LocalDate.now();
	 	String dateString;
	 	dateString = DateUtil.format(date);
	 	
		venteSemaineLabel.setText(Double.toString(factureDAO.sommeTotalSemaine(Date.valueOf(dateString)))+" Fcfa");
    }
    
    /*
     * Affiche les ventes du mois
     */
    public void showVenteMois() {
    	
    	double somme = 0.0;
	 	LocalDate date = LocalDate.now();
	 	String dateString;
	 	dateString = DateUtil.format(date);
	 	
		venteMoisLabel.setText(Double.toString(factureDAO.sommeTotalMois(Date.valueOf(dateString)))+" Fcfa");
    }
    
    /*
     * Affiche les ventes de l'année
     */
    public void showVenteAnnee() {
    	
    	double somme = 0.0;
	 	LocalDate date = LocalDate.now();
	 	String dateString;
	 	dateString = DateUtil.format(date);
	 	
		venteAnneeLabel.setText(Double.toString(factureDAO.sommeTotalAnnee(Date.valueOf(dateString)))+" Fcfa");
    }
    
    public void showEvolution() {
    	//liste de mois
    	ArrayList<String> mois = new ArrayList<String>();
    	mois.add("Janvier");mois.add("Fevrier");mois.add("Mars");mois.add("Avril");
    	mois.add("Mai");mois.add("Juin");mois.add("Juillet");mois.add("Août");
    	mois.add("Septembre");mois.add("Octobre");mois.add("Novembre");mois.add("Decembre");
    	
    	// Get an array with the dates.
    	ArrayList <String> dates= new ArrayList<String>();
    	for (int i=6; i >=0; i--) {
    		LocalDate date = LocalDate.now();
    		dates.add((DateUtil.format(date.minusDays(i))).substring(5,10));
    	}
    	dateNames.addAll(dates);
    	
        // Assign the dates as categories for the horizontal axis.
        xAxis.setCategories(dateNames);
  
        // recupère les montants des factures des 7 derniers jours
    	double montantFacture []= new double [7];
    	LocalDate date = LocalDate.now();
    	String dateString;
    	double montant = 0;
    	for (int i =6; i>=0; i--) {
    		dateString = DateUtil.format(date.minusDays(i));
    		montantFacture[6-i] = factureDAO.sommeTotal(Date.valueOf(dateString));
    	}
    			
    	XYChart.Series<String, Double> series = new XYChart.Series<>();
        
    	series.setName("Ventes");
        // Create a XYChart.Data object for each month. Add it to the series.
        for (int i = 0; i <= 6; i++) {
            series.getData().add(new XYChart.Data<>(dateNames.get(i), montantFacture[i]));
        }
        
        int j = 1;
        for(String str : mois)
        {
        	if (!(factureDAO.sommeTotalMois(j) == 0.0)) {
				pieChartData.add(new PieChart.Data(str, ((factureDAO.sommeTotalMois(j)))));
			}
			j++;
        }
        
        circle.setLabelLineLength(50);
        circle.setLabelsVisible(true); 
        circle.autosize();
        //Setting the start angle of the pie chart  
        //circle.setStartAngle(180);
        
        circle.setData(pieChartData);
        
        barChart.setLegendVisible(true);
        
        barChart.getData().add(series);	
    }
    
    /*
     * Affiche les detazils des ventes
     */
    public void showDetailsVentes() {
    	ArrayList<Categorie> categorie = new ArrayList<Categorie>();
    	//categorie.add("Toutes les catégories");
    	categorieList.add(new Categorie("toutes les catégories"));
    	categorieList.addAll(categorieDAO.findAll());
    	categorieBox.setValue(categorieList.get(0));
    	categorieBox.setItems(categorieList);
    	
    	
    	produitBox.setValue(null);
    	
    	dateDebut.setValue(LocalDate.now());
    	dateFin.setValue(LocalDate.now());
    	
    	// Ecoute les changements de selection de categorie et affiche les produits en conséquence
    	categorieBox.getSelectionModel().selectedItemProperty().addListener(
    			observable ->  initializeProduit(categorieBox.getSelectionModel().getSelectedItem()));
    	  	
    } 
    
    /*
     * 
     */
    public void initializeProduit(Categorie nomCat) {
    	ArrayList<String> produit = new ArrayList<String>();
    	produit.add("Tous les produits");
    	produitList.clear();
    	/*try {
    		String requete = "select nomPro from produit "
    				+ "where id_Categorie in (select idCat from categorie where nomCat='"+nomCat+"')";
    		ResultSet result = connexionBD().executeQuery(requete);
    		while(result.next()) {
    			produit.add(result.getString("nomPro"));
    		}
    	}catch (SQLException ex) {
            System.err.println("une erreur0!");
            ex.printStackTrace();
    	}*/
    	produitList.add(new Produit("tous les produits",0.0,0.0,true,0.0,null,null,null,null,true,null));
    	produitList.addAll(produitDAO.findAll_2(nomCat.getNomCat()));
    	//produitBox.setValue("Tous les produits");
    	produitBox.setItems(produitList);
    }
    
    /*
     * 
     */
    public void initializeLesMeilleurs() {
    	
    	// Get an array with the English month names.
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        // Convert it to a list and add it to our ObservableList of months.
        monthNames.add("Toute l'année");  
        moisBoxCaissiere.setValue("Toute l'année");
        moisBoxCaissiere.setItems(monthNames);
        monthNames.addAll(Arrays.asList(months));
        moisBoxClient.setValue("Toute l'année");
        moisBoxClient.setItems(monthNames);
      
        
      //Initializes the person table with the two column;
        TreeItem<Client> rootNodeClient=new RecursiveTreeItem<Client>(personData,RecursiveTreeObject::getChildren);
        nomClientColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Client, String>("nom"));
        recetteClientColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Client,String>("tel"));
        personTableClient.setRoot(rootNodeClient);
		personTableClient.setShowRoot(false);
  		//recetteClientColumn.setCellValueFactory(cellData -> cellData.getValue().montantProperty().asObject());	
        TreeItem<Gestionnaire> rootNodeCaissier=new RecursiveTreeItem<Gestionnaire>(personDataCaissiere,RecursiveTreeObject::getChildren);
        nomCaissiereColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Gestionnaire, String>("nomGest"));
        montantVenduCaissiereColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Gestionnaire,String>("login"));
        personTableCaissiere.setRoot(rootNodeCaissier);
		personTableCaissiere.setShowRoot(false);
  		//montantVenduCaissiereColumn.setCellValueFactory(cellData -> cellData.getValue().montantProperty().asObject());
        
    }
    
    /*
     * 
     */
    @FXML
    private void handleMontantVentes() {
    	double montant = 0.0 ;
    	Categorie categorieSelected =  categorieBox.getSelectionModel().getSelectedItem();
    	Produit produitSelected =  produitBox.getSelectionModel().getSelectedItem();
    	
    	if(!((dateDebut.getValue()).isAfter(dateFin.getValue())) ) {
    		if(categorieSelected.getNomCat().equals("toutes les catégories")) {
				montant = ligneFactureDAO.findAllLigneFacture(String.valueOf(dateDebut.getValue()), String.valueOf(dateFin.getValue()));
			}else {
				if(produitSelected.getNomPro().equals("tous les produits")) {
					montant = ligneFactureDAO.findAllLigneFacture1(String.valueOf(dateDebut.getValue()), String.valueOf(dateFin.getValue()), categorieSelected.getNomCat());
				}else {
					montant = ligneFactureDAO.findAllLigneFacture2(String.valueOf(dateDebut.getValue()), String.valueOf(dateFin.getValue()), produitSelected.getNomPro());
				}
			}
    	}else {
    		//Show the error Message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Periode Invalide");
			alert.setHeaderText("Corrigez les dates");
			alert.setContentText("La date de debut doit etre plus anciene ou egale a la date de fin");
			
			alert.showAndWait();
    	}
    	labus.setText(Double.toString(montant)+" Fcfa");
    }
    
    /**
     * 
     */
    @FXML
    private void meilleurClient() {
    	personData.clear();
    	String mois1Selected = (String) moisBoxClient.getSelectionModel().getSelectedItem();
    	if(mois1Selected.equals("Toute l'année")) {
    		listeClient = factureDAO.findMeilleurClient();
    	}else {
    		listeClient = factureDAO.findMeilleurClient(mois1Selected);
   		}
    	if (listeClient == null)
    		personData.addAll(new ArrayList<Client>());
    	else
    		personData.addAll(listeClient);
    	//personTableClient.setItems(personData);
    }
    
    /**
     * 
     */
    @FXML
    private void meilleurCaissiere() {
    	//personTableCaissiere.getItems().clear();
    	personDataCaissiere.clear();
    	String moisSelected = (String) moisBoxCaissiere.getSelectionModel().getSelectedItem();
    	
   		if(moisSelected.equals("Toute l'année")) {
   			listeCaissier = factureDAO.findMeilleurCaissier();
   		}else {
   			listeCaissier = factureDAO.findMeilleurCaissier(moisSelected);
    	}
   		if (listeCaissier == null)
   			personDataCaissiere.addAll(new ArrayList<Gestionnaire>());
   		else
   			personDataCaissiere.addAll(listeCaissier);
    	//personTableCaissiere.setItems(personDataCaissiere);
    }
    
    @FXML
    void handleCancel(ActionEvent event) {
    	main.getStageDelete().close();
    }
}
