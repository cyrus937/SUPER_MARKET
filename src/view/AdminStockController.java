package view;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import modele.*;

public class AdminStockController implements Initializable {

	 @FXML
	    private JFXTextField search;
	 
	    @FXML
	    private JFXButton deconnexion;
	 
	 @FXML
	 private Label label = new Label();

	    @FXML
	    private JFXTreeTableView<GestionStock> treetableview;

	    @FXML
	    private TreeTableColumn<GestionStock, Integer> identifiant;

	    @FXML
	    private TreeTableColumn<GestionStock, Double> qte;

	    @FXML
	    private TreeTableColumn<GestionStock, Timestamp> date;

	    @FXML
	    private TreeTableColumn<GestionStock, Boolean> operation;

	    @FXML
	    private TreeTableColumn<GestionStock, String> gestionnaire;

	    @FXML
	    private TreeTableColumn<GestionStock, String> produit;
	    
	    private ObservableList<GestionStock> data=FXCollections.observableArrayList();
	    
	    private Main main=new Main();
	    
	    GestionStockDAO stock = new GestionStockDAO();
	   
	    
	    private ArrayList<GestionStock> listStock =new ArrayList<GestionStock>();
	    
	    private TreeItem<GestionStock> caract ;
	    
	    static Produit p;
	    
	    @FXML
	    void handleDeconnexion(ActionEvent event) {
	    	main.getStageDelete().close();
	    }
	    
	    @Override
		public void initialize(URL location, ResourceBundle resources) {
	    	label.setText(LoginController.ges);
	    	listStock = stock.findAll();
	    	if (listStock == null)
	    		data.addAll(new ArrayList<GestionStock>());
	    	else
	    		data.addAll(listStock);
	    	TreeItem<GestionStock> rootNodeCaissier = new RecursiveTreeItem<GestionStock>(data,RecursiveTreeObject::getChildren);
			identifiant.setCellValueFactory(new TreeItemPropertyValueFactory<GestionStock, Integer>("idStock"));
			qte.setCellValueFactory(new TreeItemPropertyValueFactory<GestionStock, Double>("qte"));
			date.setCellValueFactory(new TreeItemPropertyValueFactory<GestionStock, Timestamp>("dateStock"));
			operation.setCellValueFactory(new TreeItemPropertyValueFactory<GestionStock, Boolean>("operation"));
			gestionnaire.setCellValueFactory(new TreeItemPropertyValueFactory<GestionStock, String>("gest"));
			produit.setCellValueFactory(new TreeItemPropertyValueFactory<GestionStock, String>("produit"));
			
			treetableview.setRoot(rootNodeCaissier);
			treetableview.setShowRoot(false);
			
			
			search.textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					treetableview.setPredicate(new Predicate<TreeItem<GestionStock>>() {

						@Override
						public boolean test(TreeItem<GestionStock> g) {
							// TODO Auto-generated method stub
							Boolean flag2= g.getValue().getGest().getNomGest().contains(newValue) || String.valueOf(g.getValue().getIdStock()).contains(newValue);
							Boolean flag1 = g.getValue().getProduit().getNomPro().contains(newValue) || String.valueOf(g.getValue().getQte()).contains(newValue);
							return flag2 || flag1;

						}
					});
				}

			});
			
			treetableview.setOnMousePressed(e -> {
				if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
					caract = treetableview.getSelectionModel().getSelectedItem();
					if (caract != null) {
						p = caract.getValue().getProduit();
						try {
							Stage newStage = new Stage();
							main.AfficheCaracteristique(newStage);
						} catch (IOException e1) {
							e1.printStackTrace();
						} 
					} 
				}
			});
			
	    }
}
