package view;

import java.io.IOException;
import java.net.URL;
import java.security.Timestamp;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import application.Main;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import modele.Facture;
import modele.FactureDAO;
import modele.GestionStock;
import modele.LigneFacture;
import modele.LigneFactureDAO;

public class GestionFactureController implements Initializable{

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
	private TreeTableColumn<Facture,String > idCaissiere=new TreeTableColumn<Facture, String>();

	@FXML
	private TreeTableColumn<Facture, String> idClient;

	@FXML
	private TreeTableColumn<Facture, String> idCommande;

	@FXML
	private TreeTableColumn<Facture, Double> quantite;

	@FXML
	private JFXTextField recette;

	@FXML
	private JFXButton validerButton;

	private FactureDAO factureDAO = new FactureDAO();
	private ObservableList<Facture> factures=FXCollections.observableArrayList();
	private ArrayList<Facture> listFactures=new ArrayList<Facture>();
	private LigneFactureDAO ligneFactureDAO=new LigneFactureDAO();

	//ArrayList contenant tous les lignes factures d'un produit en particulier
	private ArrayList<LigneFacture> ligneFactures=new ArrayList<LigneFacture>();

	//observableList pour la facture a afficher
	static TreeItem<Facture> factureAffiche;
	private ObservableList<Double> quantites=FXCollections.observableArrayList();


	@FXML
	void handleValiderButton(ActionEvent event) {

	}

	@FXML
	void handle_codeProduit(KeyEvent event) throws NumberFormatException, SQLException {
		/*
		if(event.getCode().equals(KeyCode.ENTER)) {
			System.out.println(codeProduit.getText());
			ligneFactures=ligneFactureDAO.findAllFactures(Integer.valueOf(codeProduit.getText()).intValue());
			System.out.println(ligneFactures);

			factures.clear();
			for (LigneFacture ligneFacture : ligneFactures) {
				factures.add(ligneFacture.getFacture());
			}
			TreeItem<Facture> rootNodeFacture=new RecursiveTreeItem<Facture>(factures,RecursiveTreeObject::getChildren);
			tableViewFacture.setRoot(rootNodeFacture);


		}
		//*/

	}

	@FXML
	void handle_dateInf(KeyEvent event) throws SQLException {


		System.out.println(Date.valueOf(dateSup.getValue()));

		if (event.getCode().equals(KeyCode.ENTER)) {
			listFactures=factureDAO.findAllDates( Date.valueOf(dateSup.getValue()), Date.valueOf(dateInf.getValue()));
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


	Stage stage=new Stage();
	static int idFactureAffiche;
	@FXML
	void handleSelectedItem(MouseEvent event) throws IOException {

		factureAffiche= tableViewFacture.getSelectionModel().getSelectedItem();
		System.out.println(factureAffiche.getValue());
		idFactureAffiche=factureAffiche.getValue().getIdFac();

		URL ressource = Main.class.getResource("../view/ficheFacture.fxml"); 
		Parent root = FXMLLoader.load(ressource);
		Scene scene = new Scene(root);
		stage.getIcons().add(new Image(Main.class.getResourceAsStream("logo.jpg")));
		stage.setScene(scene);
		stage.setTitle("ficheFacture");
		stage.setResizable(true);
		stage.setScene(scene);
		stage.show();


	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		double recettes=0;
		listFactures=factureDAO.findAll();

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
		montant.setCellValueFactory(new TreeItemPropertyValueFactory<Facture, Double>("montant"));
		modePaiement.setCellValueFactory(new TreeItemPropertyValueFactory<Facture, Boolean>("modePaiement"));
		idCaissiere.setCellValueFactory(new TreeItemPropertyValueFactory<Facture, String>("caissiere"));
		idClient.setCellValueFactory(new TreeItemPropertyValueFactory<Facture, String>("client"));
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
						ligneFactures=ligneFactureDAO.findAllFactures(Integer.valueOf(codeProduit.getText()).intValue());


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
					System.out.println(ligneFactures);
					//double recettes=0;
					factures.clear();
					//*
					for (LigneFacture ligneFacture : ligneFactures) {
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
						listFactures=factureDAO.findAllDates( Date.valueOf(dateSup.getValue()), Date.valueOf(dateInf.getValue()));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(listFactures);

					double recettes=0;

					if(listFactures!=null){
						for (Facture facture : listFactures) {
							recettes+=facture.getMontant()-((facture.getMontant()*facture.getRemise())/100);
						}
					}
					factures.clear();
					factures.addAll(listFactures);
					recette.setText(String.valueOf(recettes));
				}

			}
		});


	}

}

