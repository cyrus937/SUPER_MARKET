package view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import application.Main;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import modele.ConnexionBD;
import modele.GestionStock;
import modele.Gestionnaire;
import modele.GestionnaireDAO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

public class AdminCaissierController implements Initializable{

	 @FXML
	    private JFXButton deconnexion;

	 @FXML
	 private Label label = new Label();
	 
	    @FXML
	    private JFXButton ajout;

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
	    
	    GestionnaireDAO caissier = new GestionnaireDAO();
	    
	    private TreeItem<Gestionnaire> caract; 
	    
	    static Short g;
	    
	    private ArrayList<Gestionnaire> listCaissiers =new ArrayList<Gestionnaire>();

	    @FXML
	    void handleAddCaissierButton(ActionEvent event) throws IOException {
	    	Stage newStage = new Stage();
			main.ajoutCaissier(newStage);
	    }

	    @FXML
	    void handleDeconnexionButton(ActionEvent event) throws IOException {
	    	main.getStageDelete().close();
	    }
	    
	   
	    
	    //final TreeTableColumn<Gestionnaire, Boolean> colBtn = new TreeTableColumn("Statut");
	   /* private void addToggleButtonToTable() {
	    	TreeTableColumn<Gestionnaire, Boolean> colBtn = new TreeTableColumn("Statut");
	    	
	    	Callback<TreeTableColumn<Gestionnaire, Boolean>, TreeTableCell<Gestionnaire, Boolean>> cellFactory = new Callback<TreeTableColumn<Gestionnaire, Boolean>, TreeTableCell<Gestionnaire, Boolean>> () {
	    		@Override
	    		public TreeTableCell<Gestionnaire, Boolean> call(final TreeTableColumn<Gestionnaire, Boolean> param) {
	    			final TreeTableCell<Gestionnaire, Boolean> cell = new TreeTableCell<Gestionnaire, Boolean>() {
	    				private final JFXToggleButton btn = new JFXToggleButton();
	    				{
	    					btn.setText("Actif");
	    					if( n != 0 ){
	    						btn.setSelected(caissier.statut.get(0));
	    						caissier.statut.remove(0);
	    						n--;
	    					}
	    				}
	    				
	    				@Override
	    				public void updateItem(Boolean item, boolean empty){
	    					super.updateItem(item, empty);
	    					if(empty){
	    						setGraphic(null);
	    					}
	    					else {
	    						setGraphic(btn);
	    					}
	    				}
	    			};
	    			return cell;
	    		}
	    	};
	    	colBtn.setCellFactory(cellFactory);
	    	treetableview.getColumns().add(colBtn);
	    }*/
	   
	    /*static int n , i = 0;
	    actif.setCellValueFactory((TreeTableColumn.CellDataFeatures<Gestionnaire, Boolean> p) -> {
	    final Gestionnaire file = p.getValue().getValue();
	    return new SimpleBooleanProperty(file.isActif());
	});*/
	
	//actif.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(actif));
	    
	    @Override
		public void initialize(URL location, ResourceBundle resources) {
	    	
	    	label.setText(LoginController.ges);
	    	
	    	listCaissiers = caissier.findAllCaissier();
	    	if(listCaissiers == null)
	    		data.addAll(new ArrayList<Gestionnaire>());
	    	else
	    		data.addAll(listCaissiers);
	    	TreeItem<Gestionnaire> rootNodeCaissier = new RecursiveTreeItem<Gestionnaire>(data,RecursiveTreeObject::getChildren);
			identifiant.setCellValueFactory(new TreeItemPropertyValueFactory<Gestionnaire, Short>("idGest"));
			nom.setCellValueFactory(new TreeItemPropertyValueFactory<Gestionnaire, String>("nomGest"));
			login.setCellValueFactory(new TreeItemPropertyValueFactory<Gestionnaire, String>("login"));
			/*actif.setCellFactory(c -> new TreeTableCell<Gestionnaire, Gestionnaire>(){
				private final ToggleButton btn = new JFXToggleButton();
				{
					btn.selectedProperty().addListener((observable, old, newvalue) -> {
						((Gestionnaire)this.getTreeTableRow().getItem()).setActif(btn.isSelected());
					});
				}
				
				@Override
				public void updateItem (Gestionnaire item, boolean empty) {
					super.updateItem(item, empty);
					if (empty)
						setGraphic(null);
					else {
						setGraphic(btn);
						if (item != null){
							System.out.println(item.isActif());
							btn.setSelected(item.isActif());
						}
					}
				}
			});*/
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
							main.modifCaissier(newStage);;
						} catch (IOException e1) {
							e1.printStackTrace();
						} 
					} 
				}
			});
			
	    }
}
