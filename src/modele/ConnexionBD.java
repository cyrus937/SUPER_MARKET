package modele;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

import com.jfoenix.controls.JFXTreeTableView;
import com.mysql.jdbc.Statement;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

import java.sql.Connection;
public class ConnexionBD {

		private static Connection con;
		
		private static String url = "jdbc:mysql://localhost:3306/alimentation";
		private static String user = "root";
		 
		private static String password = "Kcobablb*21";
		
		/*
		 * M�thode qui va nous retourner notre instance 
		 * et la cr�er si elle n'existe pas...
		 * @return
		 */
		
		
		public static Connection getInstance() {
			if( con == null) {
				try {
					con = DriverManager.getConnection(url, user, password);
					
				}catch(SQLException e) {
					System.out.println("Erreur lors de la connexion a la BD. Code erreur : " + e.getErrorCode());
					e.printStackTrace();
				}
			}
			
			return con;
		}
		
		public void Close() throws SQLException {
			con.close();
		}
		
		/*public void afficheCaissier(ObservableList<Gestionnaire> data, TableView tableview) throws SQLException {
			// TODO Auto-generated method stub
			if(data!=null) data.removeAll(data);
			System.out.println("Table Caissier");
			Statement statement=null;
			String query =null;
			ResultSet resultSet=null;
			statement= (Statement) con.createStatement();
			query="select idGest Identifiant,nomGest Nom,login Login, actif Actif from gestionnaire where typeGest = 0";
			resultSet=statement.executeQuery(query);
			
			// Pour que la largeur des colonnes se r�glent automatiquement
			/*tableview.setColumnResizePolicy((param) -> true);
			Platform.runLater(() -> customResize(tableview));*/
					
			/*for(int i=0 ; i<resultSet.getMetaData().getColumnCount(); i++){
	            //We are using non property style for making dynamic table
	            final int j = i;                
	            TableColumn col = new TableColumn(resultSet.getMetaData().getColumnLabel(i+1));
	            col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
	                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
	                	if(param.getValue().get(j) == null)
	                    	return new SimpleStringProperty("");
	                    else
	                    	return new SimpleStringProperty(param.getValue().get(j).toString());
	                	//return new SimpleStringProperty(param.getValue().get(j).toString());                        
	                }                    
	            });

	            tableview.getColumns().addAll(col); 
	            System.out.println("Column ["+i+"] ");
	        }*/

	        /********************************
	         * Data added to ObservableList *
	         ********************************/
	        /*while(resultSet.next()){
	            //Iterate Row
	            ObservableList<String> row = FXCollections.observableArrayList();
	            for(int i=1 ; i<=resultSet.getMetaData().getColumnCount(); i++){
	                //Iterate Column
	                row.add(resultSet.getString(i));
	            }
	            System.out.println("Row [1] added "+row );
	            data.add((Gestionnaire) row);

	        }*/

	        //FINALLY ADDED TO TableView
	       //tableview.setItems(data);
		//}

		/*public void afficheFournisseur(ObservableList<ObservableList> data, TableView tableview) throws SQLException {
			// TODO Auto-generated method stub
			if(data!=null) data.removeAll(data);
			System.out.println("Table Fournisseur");
			Statement statement=null;
			String query =null;
			ResultSet resultSet=null;
			statement= (Statement) con.createStatement();
			query="select idFournisseur Identifiant,nom Nom,adresse Adresse,tel T�l�phon from Fournisseur";
			resultSet=statement.executeQuery(query);
			
			// Pour que la largeur des colonnes se r�glent automatiquement
			tableview.setColumnResizePolicy((param) -> true);
			//Platform.runLater(() -> customResize(tableview));
					
			for(int i=0 ; i<resultSet.getMetaData().getColumnCount(); i++){
	            //We are using non property style for making dynamic table
	            final int j = i;                
	            TableColumn col = new TableColumn(resultSet.getMetaData().getColumnLabel(i+1));
	            col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
	                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
	                	if(param.getValue().get(j) == null)
	                    	return new SimpleStringProperty("");
	                    else
	                    	return new SimpleStringProperty(param.getValue().get(j).toString());
	                	//return new SimpleStringProperty(param.getValue().get(j).toString());                        
	                }                    
	            });

	            tableview.getColumns().addAll(col); 
	            System.out.println("Column ["+i+"] ");
	        }

	        /********************************
	         * Data added to ObservableList *
	         ********************************/
	      /*  while(resultSet.next()){
	            //Iterate Row
	            ObservableList<String> row = FXCollections.observableArrayList();
	            for(int i=1 ; i<=resultSet.getMetaData().getColumnCount(); i++){
	                //Iterate Column
	                row.add(resultSet.getString(i));
	            }
	            System.out.println("Row [1] added "+row );
	            data.add(row);

	        }

	        //FINALLY ADDED TO TableView
	       tableview.setItems(data);
		}*/

		
		
}
