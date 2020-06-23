package view;

import java.io.IOException;
import java.net.URL;
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
import modele.Gestionnaire;
import modele.GestionnaireDAO;

public class AdminMagasinierController implements Initializable {

	 @FXML
	    private JFXButton deconnexion;

	    @FXML
	    private JFXButton ajout;
	    
	    @FXML
		 private Label label = new Label();

	    @FXML
	    private JFXTextField search;

	    @FXML
	    private JFXTreeTableView<Gestionnaire> treetableview = new JFXTreeTableView<Gestionnaire>();

	    @FXML
	    private TreeTableColumn<Gestionnaire, Short> identifiant;

	    @FXML
	    private TreeTableColumn<Gestionnaire, String> nom;

	    @FXML
	    private TreeTableColumn<Gestionnaire, String> login;

	    @FXML
	    private TreeTableColumn<Gestionnaire, Boolean> actif;
	    
	    static ObservableList<Gestionnaire> data=FXCollections.observableArrayList();
	    
	    private Main main=new Main();
	    
	    private TreeItem<Gestionnaire> caract; 
	    
	    static Short g;
	    
	    GestionnaireDAO magasinier = new GestionnaireDAO();
	    
	    
	    private ArrayList<Gestionnaire> listMagasinier = new ArrayList<Gestionnaire>();

	    @FXML
	    void handleAddButton(ActionEvent event) throws IOException {
	    	Stage newStage = new Stage();
			main.ajoutMagasinier(newStage);
	    }

	    @FXML
	    void handleDeconnexionButton(ActionEvent event) throws IOException {
	    	main.getStageDelete().close();
	    }
	    
	    @Override
		public void initialize(URL location, ResourceBundle resources) {
	    	
	    	label.setText(LoginController.ges);
	    	
	    	listMagasinier = magasinier.findAllMagasinier();
	    	if (listMagasinier == null)
	    		data.addAll(new ArrayList<Gestionnaire>());
	    	else
	    		data.addAll(listMagasinier);
	    	TreeItem<Gestionnaire> rootNodeCaissier = new RecursiveTreeItem<Gestionnaire>(data,RecursiveTreeObject::getChildren);
			identifiant.setCellValueFactory(new TreeItemPropertyValueFactory<Gestionnaire, Short>("idGest"));
			nom.setCellValueFactory(new TreeItemPropertyValueFactory<Gestionnaire, String>("nomGest"));
			login.setCellValueFactory(new TreeItemPropertyValueFactory<Gestionnaire, String>("login"));
			actif.setCellValueFactory(new TreeItemPropertyValueFactory<Gestionnaire, Boolean>("actif"));
			treetableview.setRoot(rootNodeCaissier);
			treetableview.setShowRoot(false);
			
			
			search.textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					treetableview.setPredicate(new Predicate<TreeItem<Gestionnaire>>() {

						@Override
						public boolean test(TreeItem<Gestionnaire> g) {
							// TODO Auto-generated method stub
							Boolean flag2= g.getValue().getNomGest().contains(newValue) || String.valueOf(g.getValue().getIdGest()).contains(newValue);
							return flag2;

						}
					});
				}

			});
			
			treetableview.setOnMousePressed(e -> {
				if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
					caract = treetableview.getSelectionModel().getSelectedItem();
					if (caract != null) {
						g = caract.getValue().getIdGest();
						try {
							Stage newStage = new Stage();
							main.modifMagasinier(newStage);;
						} catch (IOException e1) {
							e1.printStackTrace();
						} 
					} 
				}
			});
			
	    }
}
