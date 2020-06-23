package view;

import java.net.URL;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import application.Main;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import modele.*;

public class AjoutCommandeController implements Initializable {

	@FXML
    private JFXTextField identifiantClientTextField;

    @FXML
    private JFXTextField qteTextField;

    @FXML
    private JFXTextField codeProduitTextField;

    @FXML
    private JFXButton AnnulerProduit;

    @FXML
    private JFXButton ValiderProduit;
    
    @FXML
    private Label quatiteLabel;

    @FXML
    private JFXTextArea commentairestextArea;

    @FXML
    private JFXTreeTableView<LigneCommande> treeTableLigneCommande;

    @FXML
    private TreeTableColumn<LigneCommande, String> codeProCommande;

    @FXML
    private TreeTableColumn<LigneCommande, String> nomProCommande;

    @FXML
    private TreeTableColumn<LigneCommande, Double> qteCommande;

    @FXML
    private JFXButton ValiderCommande;

    @FXML
    private JFXButton AnnulerCommande;
    
    private ObservableList<LigneCommande> ligneCommandes =FXCollections.observableArrayList();

	private LigneCommande ligneCommande;
	
	private ClientDAO clientDAO = new ClientDAO();
	
	private ProduitDAO produitDAO = new ProduitDAO();

	private LigneCommandeDAO ligneCommandeDAO = new LigneCommandeDAO();

	//private ArrayList<LigneCommande> listLigneCommande =new ArrayList<LigneCommande>();

	private Main main = new Main();
	
	private int idClient;
	private String comment;

	private int idCommande;

	private Produit produit;
	private Commande commande;
	private CommandeDAO commandeDAO = new CommandeDAO();

    @FXML
    void handleCancelCommande(ActionEvent event) {
    	main.getStageDelete1().close();
    }

    @FXML
    void handleCancelProduit(ActionEvent event) {
    	
    }

    @FXML
    void handleOkCommande(ActionEvent event) {
    	if(!ligneCommandes.isEmpty()) {
    		//Insertion de la commande et des lignesCommandes dans la BD
    		commandeDAO.create(commande);
    		for(LigneCommande temp : ligneCommandes) {
    			ligneCommandeDAO.create(temp);
    		}
    		
    		//Insertion de la commande dans le tableau du menu
    		MenuCaisController.commandes.add(commande);
    		main.getStageDelete1().close();
    	}
    }

    @FXML
    void handleOkProduit(ActionEvent event) {
    	if(identifiantClientTextField.getText().equals("")) {
			Main.printError("Erreur lors de l'ajout ", null,"Veuillez remplir le champ de l'identifiant du client");
			return;
		}
    	
    	if(codeProduitTextField.getText().equals("")) {
			Main.printError("Erreur lors de l'ajout ", null,"Veuillez remplir le champ du code du produit");
			return;
		}
    	
    	//idCommande = MenuCaisController.commande1.getIdCommande();
    	comment = commentairestextArea.getText();
    	idClient = Integer.valueOf(identifiantClientTextField.getText());
    	
    	commande.setClient(clientDAO.find(idClient));
    	commande.setCommentaires(comment);
    	ligneCommande = new LigneCommande(produitDAO.find(Integer.valueOf(codeProduitTextField.getText().substring(0, 3)+codeProduitTextField.getText().substring(4, 7))), 
    			commande, 
    			Double.valueOf(qteTextField.getText()));
    	//ligneCommandeDAO.create(ligneCommande);
    		
    	ligneCommandes.add(ligneCommande);
    	codeProduitTextField.setText("");
    	qteTextField.setText("");
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	
    	codeProduitTextField.textProperty().addListener((observable,oldvalue,newvalue) -> {
			if(newvalue.matches("[0-9]*")) {
				if(newvalue.length() == 6) {
	    		   produit = produitDAO.find(Integer.valueOf(newvalue));
	    		   if(produit != null) {
	    			   quatiteLabel.setText(String.valueOf(produit.getQte()));
	    			   if(produit.getQte() <= 0)
	    				   quatiteLabel.setTextFill(Color.RED);
	    			   else
	    				   quatiteLabel.setTextFill(Color.GREEN);
	    		   }
	    	   }
	    	   else {
	    		   quatiteLabel.setText("ND");
	    		   quatiteLabel.setTextFill(Color.RED);
		    	   ValiderProduit.setDisable(true);
	    	   }
	       }
	       else {
	    	   codeProduitTextField.setText(oldvalue);
	    	   quatiteLabel.setText("ND");
	    	   quatiteLabel.setTextFill(Color.RED);
	    	   ValiderProduit.setDisable(true);
	       }
	    });
    	
    	
    	qteTextField.textProperty().addListener((observable,oldvalue,newvalue) -> {
			if(!quatiteLabel.getText().equals("ND")) {
				if(newvalue.matches("\\d+(\\.\\d+)?")) {
					double qte = Double.valueOf(newvalue);
					if(qte <= produit.getQte()) {
						quatiteLabel.setText(String.valueOf(produit.getQte() - qte));
		    		   ValiderProduit.setDisable(false);
					}
					else {
						quatiteLabel.setText(oldvalue);
					}  
				}
				else {
					if(!newvalue.isEmpty())
						quatiteLabel.setText(oldvalue);
					else {
						quatiteLabel.setText(String.valueOf(produit.getQte()));
						ValiderProduit.setDisable(true);
					}
				}	
			}
			else {
				qteTextField.setText("");
				ValiderProduit.setDisable(false);
			}
	    });
    	
		TreeItem<LigneCommande> rootNodeLigneCommande =new RecursiveTreeItem<LigneCommande>(ligneCommandes,RecursiveTreeObject::getChildren);
		codeProCommande.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LigneCommande,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<LigneCommande, String> param) {
				// TODO Auto-generated method stub
				return new SimpleObjectProperty<String>(param.getValue().getValue().getProduit().getCodePro());
			}
		});
		
		nomProCommande.setCellValueFactory(new TreeItemPropertyValueFactory<LigneCommande, String>("produit"));
		qteCommande.setCellValueFactory(new TreeItemPropertyValueFactory<LigneCommande, Double>("qte"));
		treeTableLigneCommande.setRoot(rootNodeLigneCommande);
		treeTableLigneCommande.setShowRoot(false);
		
		identifiantClientTextField.setText(String.valueOf(ClientDAO.DEFAULT_CLIENT_ID));
		
		commande = new Commande();
    }
}
