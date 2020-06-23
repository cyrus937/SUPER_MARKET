package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class CategorieDAO extends DAO<Categorie>{
	
	public CategorieDAO() {
		// TODO Auto-generated constructor stub
		try {
			search = this.connect.prepareStatement("SELECT * FROM Categorie WHERE idCat = ?");
			insert = this.connect.prepareStatement("INSERT INTO Categorie (nomCat) VALUES (?)");
			update = this.connect.prepareStatement("UPDATE Categorie SET nomCat = ? WHERE idCat = ?");
			delete = this.connect.prepareStatement("DELETE FROM Categorie WHERE idCat = ?");
			last_insert_id = this.connect.prepareStatement("SELECT LAST_INSERT_ID()");
			findForName = this.connect.prepareStatement("SELECT * FROM Categorie WHERE nomCat = ?");
			affiche = this.connect.prepareStatement("SELECT * FROM Categorie");
		} catch (SQLException e) {
			System.out.println("Erreur lors de l'instanciation de cat�gorieDAO. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		
	}
	
	@Override
	public Categorie find(int id) {
		// TODO Auto-generated method stub
		Categorie categorie = null;
		try {
			search.setInt(1, id);
			ResultSet result = search.executeQuery();
			
			if(!result.next()) {
				result.close();
				return null;
			}
			else
				categorie = new Categorie(result.getShort(1), result.getString(2));
			result.close();
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche d'une cat�gorie. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return categorie;
	}

	public Categorie findForName(String nom) {
		// TODO Auto-generated method stub
		Categorie categorie = null;
		try {
			findForName.setString(1, nom);
			ResultSet result = findForName.executeQuery();
			if(!result.next()) {
				result.close();
				return null;
			}
			else
				categorie = new Categorie(result.getShort(1), result.getString(2));
			result.close();
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche d'une cat�gorie. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return categorie;
	}
	
	@Override
	public int create(Categorie obj) {
		// TODO Auto-generated method stub
		try {
			insert.setString(1, obj.getNomCat());
			insert.executeUpdate();
			
			ResultSet result = last_insert_id.executeQuery();
			result.next();	
			short id = result.getShort(1);
			obj.setIdCat(id);
			result.close();
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de l'ajout d'une cat�gorie. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return -1;
		}
	}
	
	
	@Override
	public boolean update(Categorie obj) {
		// TODO Auto-generated method stub
		try {
			update.setString(1, obj.getNomCat());
			update.setShort(2, obj.getIdCat());
			update.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Erreur lors de la modification d'une cat�gorie. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean delete(Categorie obj) {
		// TODO Auto-generated method stub
		try {
			delete.setShort(1, obj.getIdCat());
			delete.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de la suppression d'une cat�gorie. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	@Override
	public ArrayList<Categorie> findAll() {
		// TODO Auto-generated method stub
		ArrayList<Categorie> listCategories = new ArrayList<Categorie>();
		try {
			ResultSet result = affiche.executeQuery();
			if (!result.next())
				return null;
			else
			{
				listCategories.add(new Categorie(result.getShort(1), result.getString(2)));
				while(result.next()) {
					listCategories.add(new Categorie(result.getShort(1), result.getString(2)));
				}
				
			}
			
			result.close();
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche des cat�gories . Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return listCategories;
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
	
	public void close() {
		try {
			insert.close();
			update.close();
			delete.close();
			last_insert_id.close();
			findForName.close();
			search.close();
			affiche.close();
		} catch (SQLException e) {
			System.out.println("Erreur lors de la fermeture de categorieDAO. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		CategorieDAO categorieDAO = new CategorieDAO();
		Categorie categorie = categorieDAO.find(101);
		categorieDAO.delete(categorie);
		System.out.println("OK !");
		
	}

}
