package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommandeDAO extends DAO<Commande>{

	public CommandeDAO() {
		// TODO Auto-generated constructor stub
		try {
			search = this.connect.prepareStatement("SELECT * FROM Commande WHERE idCommande = ?");
			insert = this.connect.prepareStatement("INSERT INTO Commande (date,commentaires,idClient) VALUES (?,?,?)");
			update = this.connect.prepareStatement("UPDATE Commande SET date = ?,livre = ?,commentaires = ?,idClient= ? "
					+ " WHERE idCommande = ?");
			delete = this.connect.prepareStatement("DELETE FROM Commande WHERE idCommande = ?");
			last_insert_id = this.connect.prepareStatement("SELECT LAST_INSERT_ID()");
			affiche = this.connect.prepareStatement("SELECT * FROM Commande");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de l'instanciation de commandeDAO. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		
	}
	
	@Override
	public Commande find(int id) {
		Commande commande = null;
		try {
			search.setInt(1, id);
			ResultSet result = search.executeQuery();
			if(!result.next()) {
				result.close();
				return null;
			}
			else {
				ClientDAO clientDAO = new ClientDAO();
				Client client = clientDAO.find(result.getInt(5));
				commande = new Commande(result.getInt(1), result.getDate(2), result.getBoolean(3), result.getString(4), client);
				clientDAO.close();
				result.close();
			}
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche de la commande. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return commande;
	}

	@Override
	public int create(Commande obj) {
		try {
			insert.setDate(1, obj.getDate());
			insert.setString(2, obj.getCommentaires());
			insert.setInt(3, obj.getClient().getIdClient());
			insert.executeUpdate();
			
			ResultSet result = last_insert_id.executeQuery();
			result.next();
			int id = result.getInt(1);
			obj.setIdCommande(id);
			result.close();
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de l'ajout de la commande. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public boolean update(Commande obj) {
		// TODO Auto-generated method stub
		try {
			update.setDate(1, obj.getDate());
			update.setBoolean(2, obj.isLivre());
			update.setString(3, obj.getCommentaires());
			update.setInt(4, obj.getClient().getIdClient());
			update.setInt(5, obj.getIdCommande());
			
			update.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Erreur lors de la modification de la commande. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Commande obj) {
		// TODO Auto-generated method stub
		try {
			delete.setInt(1, obj.getIdCommande());
			delete.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de la suppression de la commande. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}

	
	
	@Override
	public ArrayList findAll() {
		// TODO Auto-generated method stub
		ArrayList<Commande> listCommande = new ArrayList<Commande>();
		try {
			ResultSet result = affiche.executeQuery();
			if (!result.next())
				return null;
			else
			{
				ClientDAO clientDAO = new ClientDAO();
				Client client = clientDAO.find(result.getInt(5));
				listCommande.add(new Commande(result.getInt(1), result.getDate(2), result.getBoolean(3),result.getString(4), client));
				while(result.next()) {
					listCommande.add(new Commande(result.getInt(1), result.getDate(2), result.getBoolean(3),result.getString(4), client));
				}
				
			}
			
			result.close();
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche des commandes . Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return listCommande;
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
			update.close();
			delete.close();
			last_insert_id.close();
			search.close();
			affiche.close();
		} catch (SQLException e) {
			System.out.println("Erreur lors de la fermeture de commandeDAO. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		CommandeDAO commandeDAO = new CommandeDAO();
		ClientDAO clientDAO = new ClientDAO();
		Client client = clientDAO.find(1000003);
		if(client != null) {
			System.out.println(client);
			Commande commande = commandeDAO.find(1);
			System.out.println(commande);
			GestionnaireDAO gestionnaireDAO = new GestionnaireDAO();
			Gestionnaire gestionnaire = gestionnaireDAO.find(1002);
			System.out.println(gestionnaire);
			FactureDAO factureDAO = new FactureDAO();
			Facture facture = new Facture(null, null, 0, 5000, false, gestionnaire, client, commande);
			factureDAO.create(facture);
			System.out.println(facture);
			commande = commandeDAO.find(commande.getIdCommande());
			System.out.println(commande);
			commandeDAO.close();
			clientDAO.close();
			gestionnaireDAO.close();
			factureDAO.close();
		}
	}

}
