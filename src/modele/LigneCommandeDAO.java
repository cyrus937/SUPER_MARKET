package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import view.AjoutCommandeController;

public class LigneCommandeDAO extends DAO<LigneCommande>{

	public LigneCommandeDAO() {
		// TODO Auto-generated constructor stub
		try {
			research = this.connect.prepareStatement("SELECT * FROM LigneCommande WHERE idCommande = ?");
			search = this.connect.prepareStatement("SELECT * FROM LigneCommande WHERE idCommande = ? AND codePro = ?");
			insert = this.connect.prepareStatement("INSERT INTO LigneCommande VALUES (?,?,?)");
			update = this.connect.prepareStatement("UPDATE LigneCommande SET qte = ? WHERE idCommande = ? AND codePro = ?");
			delete = this.connect.prepareStatement("DELETE FROM LigneCommande WHERE idCommande = ? AND codePro = ?");
			affiche = this.connect.prepareStatement("SELECT codePro, idCommande, qte FROM LigneCommande WHERE idCommande = ?");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de l'instanciation de LigneCommandeDAO. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		
	}
	@Override
	public LigneCommande find(int id) {
		// TODO Auto-generated method stub
		
		return null;
	}
	
	public LigneCommande find(int codePro, int idCommande) {
		LigneCommande ligneCommande= null;
		try {
			search.setInt(1, idCommande);
			search.setInt(2, codePro);
			ResultSet result = search.executeQuery();
			if(!result.next()) {
				result.close();
				return null;
			}
			else {
				ProduitDAO produitDAO = new ProduitDAO();
				Produit produit = produitDAO.find(result.getInt(1));
				CommandeDAO commandeDAO = new CommandeDAO();
				Commande commande = commandeDAO.find(result.getInt(2));
				
				ligneCommande = new LigneCommande(produit, commande, result.getDouble(3));
				result.close();
				commandeDAO.close();
				produitDAO.close();
			}
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche de la ligneCommande. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return ligneCommande;
	}

	@Override
	public int create(LigneCommande obj) {
		// TODO Auto-generated method stub
		try {
			insert.setInt(1, obj.getProduit().getCodePro1());
			insert.setInt(2, obj.getCommande().getIdCommande());
			insert.setDouble(3, obj.getQte());
			insert.executeUpdate();
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de l'insertion du produit dans la commande. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public boolean update(LigneCommande obj) {
		// TODO Auto-generated method stub
		try {
			update.setDouble(1, obj.getQte());
			update.setInt(2, obj.getCommande().getIdCommande());
			update.setInt(3, obj.getProduit().getCodePro1());
			update.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de la modification de LigneCommande. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(LigneCommande obj) {
		// TODO Auto-generated method stub
		try {
			delete.setInt(1, obj.getCommande().getIdCommande());
			delete.setInt(2, obj.getProduit().getCodePro1());
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de la suppression du produit dans la commande. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}
	

	
	@Override
	public ArrayList findAll() {
		// TODO Auto-generated method stub
		/*ArrayList<LigneCommande> listLigneCommande = new ArrayList<LigneCommande>();
		try {
			affiche.setInt(1, AjoutCommandeController.idCommande);
			ResultSet result = affiche.executeQuery();
			if (!result.next())
				return null;
			else
			{
				ProduitDAO produitDAO = new ProduitDAO();
				Produit produit = produitDAO.find(result.getInt(1));
				CommandeDAO commandeDAO = new CommandeDAO();
				Commande commande = commandeDAO.find(result.getInt(2));
				listLigneCommande.add(new LigneCommande(produit, commande, result.getDouble(3)));
				while(result.next()) {
					listLigneCommande.add(new LigneCommande(produit, commande, result.getDouble(3)));
				}
				
			}
			
			result.close();
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche des lignes de commande . Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return listLigneCommande;*/
		
		return null;
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
			research.close();
			insert.close();
			delete.close();
			update.close();
			search.close();
			affiche.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de la fermeture de LigneCommandeDAO. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
	}

}
