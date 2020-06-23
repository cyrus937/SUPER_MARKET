package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GestionStockDAO extends DAO<GestionStock>{

	public GestionStockDAO() {
		// TODO Auto-generated constructor stub
		try {
			insert = this.connect.prepareStatement("INSERT INTO GestionStock (qte,dateStock,operation,idGest,codePro) "
					+ " VALUES (?,?,?,?,?)");
			update = this.connect.prepareStatement("UPDATE GestionStock SET qte = ?, dateStock = ?, operation = ?, idGest = ?, "
					+ "codePro = ? WHERE idStock = ?");
			search = this.connect.prepareStatement("SELECT * FROM GestionStock WHERE idStock = ?");
			last_insert_id = this.connect.prepareStatement("SELECT LAST_INSERT_ID()");
			affiche = this.connect.prepareStatement("SELECT * FROM gestionstock");
			//affiche = this.connect.prepareStatement("SELECT idStock, qte, dateStock,operation,nomGest, nomPro FROM (gestionstock INNER JOIN Gestionnzire ON gestionstock.idGest = Gestionnaire.idGest) INNER JOIN produit ON gestionstock.codePro = produit.codePro");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de l'instanciation de GestionStockDAO. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		
	}
	
	@Override
	public GestionStock find(int id) {
		// TODO Auto-generated method stub
		GestionStock gestionStock = null;
		try {
			search.setInt(1, id);
			ResultSet result = search.executeQuery();
			if(!result.next()) {
				result.close();
				return null;
			}
			else {
				GestionnaireDAO gestionnaireDAO = new GestionnaireDAO();
				ProduitDAO produitDAO = new ProduitDAO();
				Gestionnaire gestionnaire = gestionnaireDAO.find(result.getInt(5));
				Produit produit = produitDAO.find(result.getInt(6));
				
				gestionStock = new GestionStock(id, result.getDouble(2), result.getTimestamp(3), result.getBoolean(4), gestionnaire, produit);
				result.close();
				gestionnaireDAO.close();
				produitDAO.close();
			}
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche de l'operation. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return gestionStock;
	}

	@Override
	public int create(GestionStock obj) {
		// TODO Auto-generated method stub
		try {
			insert.setDouble(1, obj.getQte());
			insert.setTimestamp(2, obj.getDateStock());
			insert.setBoolean(3, obj.isOperation());
			insert.setShort(4, obj.getGest().getIdGest());
			insert.setInt(5, obj.getProduit().getCodePro1());
			
			insert.executeUpdate();
			
			ResultSet result = last_insert_id.executeQuery();
			result.next();
			int id = result.getInt(1);
			obj.setIdStock(id);
			result.close();
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de l'insertion de l'op�ration. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public boolean update(GestionStock obj) {
		// TODO Auto-generated method stub
		try {
			update.setDouble(1, obj.getQte());
			update.setTimestamp(2, obj.getDateStock());
			update.setBoolean(3, obj.isOperation());
			update.setShort(4, obj.getGest().getIdGest());
			update.setInt(5, obj.getProduit().getCodePro1());
			update.setInt(6, obj.getIdStock());
			
			update.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de la modification de l'op�ration. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(GestionStock obj) {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public ArrayList findAll() {
		// TODO Auto-generated method stub
		ArrayList<GestionStock> stock = new ArrayList<GestionStock>();
		try {
			ResultSet result = affiche.executeQuery();
			if(!result.next())
				{
					return null;
				}
			else
			{
				GestionnaireDAO gestionnaireDAO = new GestionnaireDAO();
				ProduitDAO produitDAO = new ProduitDAO();
				Gestionnaire gestionnaire = gestionnaireDAO.find(result.getInt(5));
				Produit produit = produitDAO.find(result.getInt(6));
				stock.add(new GestionStock(result.getInt(1), result.getDouble(2), result.getTimestamp(3), result.getBoolean(4), gestionnaire, produit));
				//statut.add(result.getBoolean(4));
				 while (result.next())
				 {
					 
					 stock.add(new GestionStock(result.getInt(1), result.getDouble(2), result.getTimestamp(3), result.getBoolean(4), gestionnaireDAO.find(result.getInt(5)), produitDAO.find(result.getInt(6))));
				 }
			}
			result.close();
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche des stocks . Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return stock;
	}
	
	@Override
	public ArrayList findAllCaissier() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList findAllMagasinier() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		try {
			insert.close();
			affiche.close();
			search.close();
			update.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de la fermeture de GestionStockDAO. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
	}

}
