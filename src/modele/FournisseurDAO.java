package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FournisseurDAO extends DAO<Fournisseur>{

	public FournisseurDAO() {
		// TODO Auto-generated constructor stub
		try {
			insert = this.connect.prepareStatement("INSERT INTO Fournisseur (nom,adresse,tel) VALUES (?,?,?)");
			search = this.connect.prepareStatement("SELECT * FROM Fournisseur WHERE idFournisseur = ?");
			update = this.connect.prepareStatement("UPDATE Fournisseur SET nom = ?, adresse = ?, tel = ? WHERE idFournisseur = ?");
			last_insert_id = this.connect.prepareStatement("SELECT LAST_INSERT_ID()");
			affiche = this.connect.prepareStatement("SELECT * FROM Fournisseur");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur dans l'instanciation de FournisseurDAO. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
	}
	
	@Override
	public Fournisseur find(int id) {
		// TODO Auto-generated method stub
		Fournisseur fournisseur = null;
		try {
			search.setInt(1, id);
			ResultSet result = search.executeQuery();
			if(!result.next())
				return null;
			else
				fournisseur = new Fournisseur(result.getShort(1), result.getString(2), result.getString(3), result.getString(4));
			result.close();
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche du Fournisseur. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return fournisseur;
	}

	@Override
	public int create(Fournisseur obj) {
		// TODO Auto-generated method stub
		try {
			insert.setString(1, obj.getNom());
			insert.setString(2, obj.getAdresse());
			insert.setString(3, obj.getTel());
			insert.executeUpdate();
			
			ResultSet result = last_insert_id.executeQuery();
			result.next();
			short id = result.getShort(1);
			obj.setIdFournisseur(id);
			result.close();
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de l'ajout du Fournisseur. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public boolean update(Fournisseur obj) {
		// TODO Auto-generated method stub
		try {
			update.setString(1, obj.getNom());
			update.setString(2, obj.getAdresse());
			update.setString(3, obj.getTel());
			update.setShort(4, obj.getIdFournisseur());
			update.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Erreur lors de la modification du Fournisseur. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Fournisseur obj) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	@Override
	public ArrayList findAll() {
		// TODO Auto-generated method stub
		ArrayList<Fournisseur> fourns = new ArrayList<Fournisseur>();
		try {
			ResultSet result = affiche.executeQuery();
			if(!result.next())
				return null;
			else
			{
				fourns.add(new Fournisseur(result.getShort(1), result.getString(2), result.getString(3), result.getString(4)));
				
				while (result.next())
				 {
					fourns.add(new Fournisseur(result.getShort(1), result.getString(2), result.getString(3), result.getString(4)));
				 }
			}
			result.close();
		}catch(SQLException e) {
			System.out.println("Erreur lors de la selection des fournisseurs. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return fourns;
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
		/*DAO<Fournisseur> fournisseurDAO = new FournisseurDAO();
		Fournisseur fournisseur = new Fournisseur("Fuller");
		fournisseur.setIdFournisseur((short)fournisseurDAO.create(fournisseur));
		System.out.println(fournisseur);
		fournisseur.setAdresse("Damas, Yaound� Cameroun");
		fournisseurDAO.update(fournisseur);
		System.out.println("OK !");*/
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
			System.out.println("Erreur lors de la fermeture de fournisseurDAO. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
	}

}
