package view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import modele.ConnexionBD;
import modele.Fournisseur;
import modele.FournisseurDAO;
import modele.Gestionnaire;

public class AdminFournisseurController implements Initializable{

	  @FXML
	    private JFXButton deconnexion;

	    @FXML
	    private JFXButton ajout;

	    @FXML
		 private Label label = new Label();
	    
	    @FXML
	    private JFXTextField search;

	    @FXML
	    private JFXTreeTableView<Fournisseur> treetableview = new JFXTreeTableView<Fournisseur>();;

	    @FXML
	    private TreeTableColumn<Fournisseur, Short> identifiant;

	    @FXML
	    private TreeTableColumn<Fournisseur, String> nom;

	    @FXML
	    private TreeTableColumn<Fournisseur, String> adresse;

	    @FXML
	    private TreeTableColumn<Fournisseur, String> tel;

	    @FXML
	    private JFXButton modif;
	    
	    static ObservableList<Fournisseur> data=FXCollections.observableArrayList();
	    
	    private Main main=new Main();
	    
	    FournisseurDAO fourn = new FournisseurDAO();
	    
	    static Short id = null;
	    
	    private ArrayList<Fournisseur> listfourn =new ArrayList<Fournisseur>();
	    
	    private TreeItem<Fournisseur> selection ;

	    @FXML
	    void handleAddFournisseurButton(ActionEvent event) throws IOException {
	    	Stage newStage = new Stage();
			main.ajoutFournisseur(newStage);
	    }

	    @FXML
	    void handleDeconnexionButton(ActionEvent event) throws IOException {
	    	main.getStageDelete().close();
	    }

	    @FXML
	    void handleModifButton(ActionEvent event) throws IOException {
	    	selection = treetableview.getSelectionModel().getSelectedItem();
	    	if (selection == null)
	    		Main.printError("Erreur","Aucune s�lection !","Veuillez s�lectionnez la ligne que vous voulez modifier");
	    	else
	    	{
	    		id = (Short) selection.getValue().getIdFournisseur();
	    		Stage newStage = new Stage();
	    		main.modifFourinisseur(newStage);
	    	}
	    }
	    
	    @Override
			public void initialize(URL location, ResourceBundle resources) {
		    	
	    	label.setText(LoginController.ges);
	    	
		    	listfourn = fourn.findAll();
		    	if (listfourn == null)
		    		data.addAll(new ArrayList<Fournisseur>());
		    	else
		    		data.addAll(listfourn);
		    	TreeItem<Fournisseur> rootNodeFourn = new RecursiveTreeItem<Fournisseur>(data,RecursiveTreeObject::getChildren);
				identifiant.setCellValueFactory(new TreeItemPropertyValueFactory<Fournisseur, Short>("idFournisseur"));
				nom.setCellValueFactory(new TreeItemPropertyValueFactory<Fournisseur, String>("nom"));
				adresse.setCellValueFactory(new TreeItemPropertyValueFactory<Fournisseur, String>("adresse"));
				tel.setCellValueFactory(new TreeItemPropertyValueFactory<Fournisseur, String>("tel"));
				treetableview.setRoot(rootNodeFourn);
				treetableview.setShowRoot(false);
				
				
				search.textProperty().addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						// TODO Auto-generated method stub
						treetableview.setPredicate(new Predicate<TreeItem<Fournisseur>>() {

							@Override
							public boolean test(TreeItem<Fournisseur> f) {
								// TODO Auto-generated method stub
								Boolean flag2= f.getValue().getNom().contains(newValue) || String.valueOf(f.getValue().getIdFournisseur()).contains(newValue);
								return flag2;

							}
						});
					}

				});
				
		    }
}
