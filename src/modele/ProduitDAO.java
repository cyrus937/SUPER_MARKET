package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ProduitDAO extends DAO<Produit>{
	private PreparedStatement search_names;

	public ProduitDAO() {
		// TODO Auto-generated constructor stub
		try {
			search = this.connect.prepareStatement("SELECT * FROM Produit WHERE codePro = ?");
			insert = this.connect.prepareStatement("INSERT INTO Produit VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
			update = this.connect.prepareStatement("UPDATE Produit SET nomPro = ?, prixVente = ?,prixAchat = ?,quantifiable = ?,qte = ?, datePeremption = ?, description = ?,codeFour = ?,"
					+ "dateInsertion = ?,actif = ?, id_Categorie = ? WHERE codePro = ?");
			last_insert_id = this.connect.prepareStatement("SELECT LAST_INSERT_ID()");
			affiche = this.connect.prepareStatement("SELECT * FROM Produit");
			search_names = this.connect.prepareStatement("SELECT nomPro FROM Produit");
			findForName = this.connect.prepareStatement("SELECT * FROM Produit WHERE nomPro = ?");
			afficheCaissier = this.connect.prepareStatement("select * from produit "
    				+ "where id_Categorie in (select idCat from categorie where nomCat= ? )");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de l'instanciation de ProduitDAO. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		
	}
	
	@Override
	public Produit find(int id) {
		Produit produit = null;
		try {
			search.setInt(1, id);
			ResultSet result = search.executeQuery();
			if(!result.next())
				return null;
			else {
				CategorieDAO categorieDAO = new CategorieDAO();
				Categorie categorie = categorieDAO.find(result.getInt(10));
				produit = new Produit(id, result.getString(2), result.getDouble(3), result.getDouble(4),
						result.getBoolean(5),result.getDouble(6),result.getDate(7), result.getString(8),
						result.getString(9), result.getTimestamp(10),result.getBoolean(11), categorie);
				categorieDAO.close();
				result.close();
			}
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche du produit. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return produit;
	}

	@Override
	public int create(Produit obj) {
		// TODO Auto-generated method stub
		try {
			insert.setInt(1, obj.getCodePro1());
			insert.setString(2, obj.getNomPro());
			insert.setDouble(3, obj.getPrixVente());
			insert.setDouble(4, obj.getPrixAchat());
			insert.setBoolean(5, obj.isQuantifiable());
			insert.setDouble(6, obj.getQte());
			insert.setDate(7, obj.getDatePeremption());
			insert.setString(8, obj.getDescription());
			insert.setString(9, obj.getCodeFour());
			insert.setTimestamp(10, obj.getDateInsertion());
			insert.setBoolean(11, obj.isActif());
			insert.setShort(12, obj.getCategorie().getIdCat());
			insert.executeUpdate();
			
			return obj.getCodePro1();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de l'ajout du produit. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public boolean update(Produit obj) {
		// TODO Auto-generated method stub
		try {
			update.setString(1, obj.getNomPro());
			update.setDouble(2, obj.getPrixVente());
			update.setDouble(3, obj.getPrixAchat());
			update.setBoolean(4, obj.isQuantifiable());
			update.setDouble(5, obj.getQte());
			update.setDate(6, obj.getDatePeremption());
			update.setString(7, obj.getDescription());
			update.setString(8, obj.getCodeFour());
			update.setTimestamp(9, obj.getDateInsertion());
			update.setBoolean(10, obj.isActif());
			update.setShort(11, obj.getCategorie().getIdCat());
			update.setInt(12, obj.getCodePro1());
			update.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Erreur lors de la modification du produit. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}
	
	public Produit findForName(String nom) {
		// TODO Auto-generated method stub
		Produit produit = null;
		try {
			findForName.setString(1, nom);
			ResultSet result = findForName.executeQuery();
			if(!result.next()) {
				result.close();
				return null;
			}
			else {
				CategorieDAO categorieDAO = new CategorieDAO();
				Categorie categorie = categorieDAO.find(result.getInt(10));
				produit = new Produit(result.getInt(1), result.getString(2), result.getDouble(3), result.getDouble(4),
						result.getBoolean(5),result.getDouble(6),result.getDate(7), result.getString(8),
						result.getString(9), result.getTimestamp(10),result.getBoolean(11), categorie);
				categorieDAO.close();
				result.close();
			}
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche d'un produit à partir du nom. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return produit;
	}

	@Override
	public boolean delete(Produit obj) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public ArrayList<String> findAllNomPro(){
		ArrayList<String> names = new ArrayList<String>();
		try {
			ResultSet result = search_names.executeQuery();
			
			while(result.next()) {
				names.add(result.getString(1));
			}
			
			result.close();
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche des noms des produits");
		}
		return names;
	}
	
	@Override
	public ArrayList findAll() {
		// TODO Auto-generated method stub
		ArrayList<Produit> listProduits = new ArrayList<Produit>();
		try {
			ResultSet result = affiche.executeQuery();
			CategorieDAO categorieDAO= new CategorieDAO();
			
			
			while(result.next()) {
				Categorie categorie= categorieDAO.find(result.getInt(12));
				listProduits.add(new Produit(result.getInt(1), result.getString(2), result.getDouble(3), result.getDouble(4),
						result.getBoolean(5),result.getDouble(6),result.getDate(7), result.getString(8),
						result.getString(9), result.getTimestamp(10),result.getBoolean(11), categorie));
			}
			
			result.close();
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche des produits. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return listProduits;
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
	
	public static void main(String[] args) {
		/*DAO<Produit> produitDAO = new ProduitDAO();
		CategorieDAO categorieDAO = new CategorieDAO();
		Produit produit = new Produit("Test", 350, 300, 500, null, "000", null, true,categorieDAO.find(100));
		produitDAO.create(produit);
		System.out.println(produit.getCodePro());
		System.out.println(produit);*/
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		try {
			insert.close();
			update.close();
			last_insert_id.close();
			search.close();
			affiche.close();
		} catch (SQLException e) {
			System.out.println("Erreur lors de la fermeture de produitDAO. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
	}

	public ArrayList findAll_2(String str) {
		// TODO Auto-generated method stub
		ArrayList<Produit> listProduits = new ArrayList<Produit>();
		try {
			afficheCaissier.setString(1, str);
			ResultSet result = afficheCaissier.executeQuery();
			CategorieDAO categorieDAO= new CategorieDAO();
			
			
			while(result.next()) {
				Categorie categorie= categorieDAO.find(result.getInt(10));
				listProduits.add(new Produit(result.getInt(1), result.getString(2), result.getDouble(3), result.getDouble(4),
						result.getBoolean(5),result.getDouble(6),result.getDate(7), result.getString(8),
						result.getString(9), result.getTimestamp(10),result.getBoolean(11), categorie));
			}
			
			result.close();
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche des produits. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return listProduits;
	}

}
