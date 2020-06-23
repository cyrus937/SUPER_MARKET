package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ClientDAO extends DAO<Client> {
	
	public static final int DEFAULT_CLIENT_ID = 1000000;
	public ClientDAO() {
		try {
			search = this.connect.prepareStatement("SELECT * FROM Client WHERE idClient = ?");
			insert = this.connect.prepareStatement("INSERT INTO Client (nom,tel,adresse,bonus,actif) VALUES (?,?,?,?,?)");
			update = this.connect.prepareStatement("UPDATE Client SET nom = ?,tel = ?,adresse = ?,bonus = ?,actif = ?"
					+ " WHERE idClient = ?");
			last_insert_id = this.connect.prepareStatement("SELECT LAST_INSERT_ID()");
			affiche = this.connect.prepareStatement("SELECT * FROM Client");
		} catch (SQLException e) {
			System.out.println("Erreur lors de l'instanciation de ClientDAO. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		
	}
	@Override
	public Client find(int id) {
		// TODO Auto-generated method stub
		Client client = null;
		try {
			search.setInt(1, id);
			ResultSet result = search.executeQuery();
			
			if(!result.next()) {
				result.close();
				return null;
			}
			else
				client = new Client(id,result.getString(2),result.getString(3),result.getString(4),result.getInt(5),result.getBoolean(6));
			result.close();
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche d'un client. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return client;
	}

	@Override
	public int create(Client obj) {
		// TODO Auto-generated method stub
		try {
			
			insert.setString(1, obj.getNom());
			insert.setString(2, obj.getTel());
			insert.setString(3, obj.getAdresse());
			insert.setInt(4, obj.getBonus());
			insert.setBoolean(5, obj.isActif());
			
			insert.executeUpdate();
			ResultSet result = last_insert_id.executeQuery();
			result.next();
			int id = result.getInt(1);
			obj.setIdClient(id);
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de l'ajout d'un client. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return -1;
		}
		
	}

	@Override
	public boolean update(Client obj) {
		// TODO Auto-generated method stub
		try {
			
			update.setString(1, obj.getNom());
			update.setString(2, obj.getTel());
			update.setString(3, obj.getAdresse());
			update.setInt(4, obj.getBonus());
			update.setBoolean(5, obj.isActif());
			update.setInt(6, obj.getIdClient());
			
			update.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Erreur lors de la modification du client. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public boolean delete(Client obj) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	@Override
	public ArrayList findAll() {
		// TODO Auto-generated method stub
		ArrayList<Client> client = new ArrayList<Client>();
		try {
			ResultSet result = affiche.executeQuery();
			if(!result.next())
				{
					return null;
				}
			else
			{
				client.add(new Client(result.getInt(1), result.getString(2), result.getString(3), 
						result.getString(4), result.getInt(5), result.getBoolean(6)));
				 while (result.next())
				 {
					 
					 client.add(new Client(result.getInt(1), result.getString(2), result.getString(3), 
							 result.getString(4), result.getInt(5), result.getBoolean(6)));
				 }
			}
			result.close();
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche des stocks . Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return client;
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
			last_insert_id.close();
			search.close();
			affiche.close();
		} catch (SQLException e) {
			System.out.println("Erreur lors de la fermeture de clientDAO. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		/*DAO<Client> clientDAO = new ClientDAO();
		Client client = clientDAO.find(10000000);
		client.setNom("Fuller");
		client.setTel("691855114");
		client.setAdresse("Damas, Yaound� Cameroun");
		clientDAO.update(client);
		client = clientDAO.find(1000002);
		System.out.println(client);*/
		
	}
}
