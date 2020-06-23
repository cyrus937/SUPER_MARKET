package view;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import modele.FicheFacture;
import modele.LigneFacture;
import modele.LigneFactureDAO;

public class FicheFactureController implements Initializable{

	@FXML
	private JFXTextField idFacture;

	@FXML
	private JFXTextField montant;

	@FXML
	private JFXTreeTableView<FicheFacture> tableProduit=new JFXTreeTableView<FicheFacture>();;

	@FXML
	private TreeTableColumn<FicheFacture, Integer> codeProduit;

	@FXML
	private TreeTableColumn<FicheFacture, String> nomProduit;

	@FXML
	private TreeTableColumn<FicheFacture, Double> quantite;

	@FXML
	private TreeTableColumn<FicheFacture, Double> prixVente;

	@FXML
	private TreeTableColumn<FicheFacture, String> codeFour;

	LigneFactureDAO ligneFactureDAO=new LigneFactureDAO();
	private ArrayList<LigneFacture> ligneFactures=new ArrayList<LigneFacture>();
	private ObservableList<FicheFacture> produitsObservable=FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
			
		if (MenuCaisController.factureAffiche!=null) {
			idFacture.setText(String.valueOf(MenuCaisController.idFactureAffiche));
			montant.setText(String.valueOf(MenuCaisController.factureAffiche.getValue().getMontant()));
			System.out.println(montant.getText());

			try {
				ligneFactures=ligneFactureDAO.findAllProduits(MenuCaisController.idFactureAffiche);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println(ligneFactures);

		}

		else {
			idFacture.setText(String.valueOf(GestionFactureController.idFactureAffiche));
			montant.setText(String.valueOf(GestionFactureController.factureAffiche.getValue().getMontant()));
			System.out.println(montant.getText());
			try {
				ligneFactures=ligneFactureDAO.findAllProduits(GestionFactureController.idFactureAffiche);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println(ligneFactures);
		}
		
		/*idFacture.setText(String.valueOf(GestionFactureController.idFactureAffiche));
		montant.setText(String.valueOf(GestionFactureController.factureAffiche.getValue().getMontant()));
		System.out.println(montant.getText());
		try {
			ligneFactures=ligneFactureDAO.findAllProduits(GestionFactureController.idFactureAffiche);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(ligneFactures);*/
		
		for (LigneFacture ligneFacture :ligneFactures ) {
			
			produitsObservable.add(new FicheFacture(ligneFacture.getProduit().getCodePro1(), ligneFacture.getProduit().getNomPro(), ligneFacture.getQte(), ligneFacture.getProduit().getPrixVente(), ligneFacture.getProduit().getCodeFour()));
			
		}
		System.out.println(produitsObservable);
		
		TreeItem<FicheFacture> rootNodeFicheFacture=new RecursiveTreeItem<FicheFacture>(produitsObservable,RecursiveTreeObject::getChildren);
		codeProduit.setCellValueFactory(new TreeItemPropertyValueFactory<FicheFacture, Integer>("codePro"));
		nomProduit.setCellValueFactory(new TreeItemPropertyValueFactory<FicheFacture,String>("nomPro"));
		quantite.setCellValueFactory(new TreeItemPropertyValueFactory<FicheFacture, Double>("quantiteVendue"));
		prixVente.setCellValueFactory(new TreeItemPropertyValueFactory<FicheFacture, Double>("prixVente"));
		codeFour.setCellValueFactory(new TreeItemPropertyValueFactory<FicheFacture, String>("codeFour"));
		tableProduit.setRoot(rootNodeFicheFacture);
		tableProduit.setShowRoot(false);
				
	}

	
	
	
	
}




