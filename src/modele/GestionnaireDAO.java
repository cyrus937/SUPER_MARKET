package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GestionnaireDAO extends DAO<Gestionnaire>{
	public ArrayList <Boolean> statut = new ArrayList <Boolean>();
	//public String nom;
	public GestionnaireDAO() {
		try {
			searchGest=this.connect.prepareStatement("SELECT * FROM Gestionnaire WHERE login=?");
			search = this.connect.prepareStatement("SELECT * FROM Gestionnaire WHERE idGest = ?");
			insert = this.connect.prepareStatement("INSERT INTO Gestionnaire (nomGest,typeGest,login,pwd,actif) VALUES (?,?,?,?,?)");
			update = this.connect.prepareStatement("UPDATE Gestionnaire SET nomGest = ?, typeGest = ?, login = ?, pwd = ?, actif = ?"
					+ " WHERE idGest = ?");
			last_insert_id = this.connect.prepareStatement("SELECT LAST_INSERT_ID()");
			research = this.connect.prepareStatement("SELECT * FROM Gestionnaire WHERE login =? AND pwd = ?");
			afficheCaissier = this.connect.prepareStatement("SELECT idGest, nomGest, login, actif FROM Gestionnaire WHERE typeGest=0");
			afficheMagasinier = this.connect.prepareStatement("SELECT idGest, nomGest, login, actif FROM Gestionnaire WHERE typeGest=1");
			affiche = this.connect.prepareStatement("SELECT idGest, nomGest, typeGest,login, actif FROM Gestionnaire ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de l'instanciation de GestionnaireDAO. Code erreur :  " + e.getErrorCode());
			e.printStackTrace();
		}
	}
	@Override
	public Gestionnaire find(int id) {
		// TODO Auto-generated method stub
		Gestionnaire gestionnaire = null;
		try {
			search.setInt(1, id);
			ResultSet result = search.executeQuery();
			if(!result.next())
				return null;
			else
				gestionnaire = new Gestionnaire((short)id, result.getString(2), result.getByte(3), result.getString(4), result.getString(5), result.getBoolean(6));
			result.close();
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche du Gestionnaire. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return gestionnaire;
	}
	
	public Gestionnaire find(String login) {
		// TODO Auto-generated method stub
		Gestionnaire gestionnaire = null;
		try {
			searchGest.setString(1, login);
			ResultSet result = searchGest.executeQuery();
			if(!result.next())
				return null;
			else
				gestionnaire = new Gestionnaire(result.getShort(1), result.getString(2), result.getByte(3), result.getString(4), result.getString(5), result.getBoolean(6));
			result.close();
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche du Gestionnaire. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return gestionnaire;
	}

	@Override
	public int create(Gestionnaire obj) {
		// TODO Auto-generated method stub
		try {
			insert.setString(1, obj.getNomGest());
			insert.setByte(2, obj.getTypeGest());
			insert.setString(3, obj.getLogin());
			insert.setString(4, obj.getPwd());
			insert.setBoolean(5, obj.isActif());
			insert.executeUpdate();
			
			ResultSet result = last_insert_id.executeQuery();
			result.next();
			short id = result.getShort(1);
			obj.setIdGest(id);
			result.close();
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de l'ajout du gestionnaire. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public boolean update(Gestionnaire obj) {
		// TODO Auto-generated method stub
		try {
			update.setString(1, obj.getNomGest());
			update.setByte(2, obj.getTypeGest());
			update.setString(3, obj.getLogin());
			update.setString(4, obj.getPwd());
			update.setBoolean(5, obj.isActif());
			update.setShort(6, obj.getIdGest());
			update.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Erreur lors de la modification du gestionnaire. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Gestionnaire obj) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public Gestionnaire researchTypeGest(String login, String password)
	{
		Gestionnaire gestionnaire = null;
		try {
			research.setString(1,login);
			research.setString(2,password);
			ResultSet result = research.executeQuery();
			if(result.next()){
				gestionnaire = new Gestionnaire(result.getShort(1), result.getString(2), result.getByte(3), result.getString(4), null, result.getBoolean(6));
			}
			result.close();
			return gestionnaire;
		}catch(SQLException e) {
			System.out.println("Erreur lors de l'obtention du type de gestionnaire du Gestionnaire. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ArrayList findAllCaissier ()
	{
		ArrayList<Gestionnaire> gests = new ArrayList<Gestionnaire>();
		try {
			ResultSet result = afficheCaissier.executeQuery();
			if(!result.next())
				return null;
			else
			{
				gests.add(new Gestionnaire(result.getShort(1), result.getString(2), result.getString(3), result.getBoolean(4)));
				statut.add(result.getBoolean(4));
				 while (result.next())
				 {
					 gests.add(new Gestionnaire(result.getShort(1), result.getString(2), result.getString(3), result.getBoolean(4)));
					 statut.add(result.getBoolean(4));
				 }
			}
			result.close();
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche du Gestionnaire. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return gests;
	}
	
	@Override
	public ArrayList findAllMagasinier ()
	{
		ArrayList<Gestionnaire> gests = new ArrayList<Gestionnaire>();
		try {
			ResultSet result = afficheMagasinier.executeQuery();
			if(!result.next())
				return null;
			else
			{
				gests.add(new Gestionnaire(result.getShort(1), result.getString(2), result.getString(3), result.getBoolean(4)));
				//statut.add(result.getBoolean(4));
				 while (result.next())
				 {
					 gests.add(new Gestionnaire(result.getShort(1), result.getString(2), result.getString(3), result.getBoolean(4)));
				 }
			}
			result.close();
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche du Gestionnaire. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return gests;
	}
	
	@Override
	public ArrayList findAll() {
		ArrayList<Gestionnaire> gests = new ArrayList<Gestionnaire>();
		try {
			ResultSet result = affiche.executeQuery();
			if(!result.next())
				return null;
			else
			{
				gests.add(new Gestionnaire(result.getShort(1), result.getString(2), result.getString(4), result.getBoolean(5)));
				
				if (result.getByte(3) == (byte)0) {
					statut.add(true);
					statut.add(result.getBoolean(5));
				}
				else
				{
					statut.add(false);
					statut.add(result.getBoolean(5));
				}
				while (result.next())
				 {
					gests.add(new Gestionnaire(result.getShort(1), result.getString(2), result.getString(4), result.getBoolean(5)));
					if (result.getByte(3) == (byte) 0) {
						statut.add(true);
						statut.add(result.getBoolean(5));
					}
					else
					{
						statut.add(false);
						statut.add(result.getBoolean(5));
					}
				 }
			}
			result.close();
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche du Gestionnaire. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return gests;
	}

	public static void main(String[] args) {
		/*DAO<Gestionnaire> gestionnaireDAO = new GestionnaireDAO();
		Gestionnaire gestionnaire = gestionnaireDAO.find(1000);
		System.out.println(gestionnaire);
		gestionnaire.setTypeGest((byte)1);
		gestionnaireDAO.update(gestionnaire);
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
			research.close();
			afficheCaissier.close();
			afficheMagasinier.close();
			affiche.close();
			searchGest.close();
		} catch (SQLException e) {
			System.out.println("Erreur lors de la fermeture de gestionnaireDAO. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
	}
}
