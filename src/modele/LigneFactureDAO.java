package modele;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import util.DateUtil;

public class LigneFactureDAO extends DAO<LigneFacture>{

	Produit p;
	Categorie cat;
	public LigneFactureDAO() {
		// TODO Auto-generated constructor stub
		try {
			insert = this.connect.prepareStatement("INSERT INTO LigneFacture VALUES (?,?,?,?,?)");
			search = this.connect.prepareStatement("SELECT * FROM LigneFacture WHERE codePro = ? AND idFac = ?");
			delete = this.connect.prepareStatement("DELETE FROM LigneFacture WHERE codePro = ? AND idFac = ?");
			update = this.connect.prepareStatement("UPDATE LigneFacture SET prixVente = ?, qte = ?, prixAchat = ?"
					+ " WHERE codePro = ? AND idFac = ?");
			somme = this.connect.prepareStatement("select SUM(qte*prixVente) as 'Somme' from lignefacture "
							+ "where idFac in (select idFac from facture where Date(dateFac) between ?  and  ?)");
			somme1 = this.connect.prepareStatement("select SUM(qte*prixVente) as 'prixTotal' from lignefacture "
        						+ "where idFac in (select idFac from facture where Date(dateFac) between "
        						+" ? and ? )"
        						+ "and codePro in (select codePro from produit where id_Categorie in"
        						+ "(select idCat from categorie where nomCat= ? ) )");
			
			somme2 = this.connect.prepareStatement("select SUM(qte*prixVente) as 'prixTotal' from lignefacture "
        						+ "where idFac in (select idFac from facture where Date(dateFac) between "
        						+" ? and ? )"
        						+ "and codePro in (select codePro from produit where nomPro= ? )");
			
			searchAllFactures=this.connect.prepareStatement("SELECT * FROM LigneFacture WHERE codePro = ?");
			searchAllProduits=this.connect.prepareStatement("SELECT * FROM LigneFacture WHERE idFac=?");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de l'instanciation de LigneFactureDAO. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
	}
	
	@Override
	public LigneFacture find(int id) {
		// TODO Auto-generated method stub
		System.out.println("Ce n'est pas la m�thode appropri�e.");
		return null;
	}
	
	public LigneFacture find(int codePro,int idFac) {
		LigneFacture ligneFacture = null;
		try {
			search.setInt(1, codePro);
			search.setInt(2, idFac);
			ResultSet result = search.executeQuery();
			if(!result.next()) {
				result.close();
				return null;
			}
			else {
				ProduitDAO produitDAO = new ProduitDAO();
				Produit produit = produitDAO.find(result.getInt(1));
				FactureDAO factureDAO = new FactureDAO();
				Facture facture = factureDAO.find(result.getInt(2));
				
				ligneFacture = new LigneFacture(produit, facture, result.getDouble(3), result.getDouble(4), result.getDouble(5));
				result.close();
				factureDAO.close();
				produitDAO.close();
			}
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche de la ligneFacture. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return ligneFacture;
	}
	
	@Override
	public int create(LigneFacture obj) {
		// TODO Auto-generated method stub
		try {
			insert.setInt(1, obj.getProduit().getCodePro1());
			insert.setInt(2, obj.getFacture().getIdFac());
			insert.setDouble(3, obj.getPrixVente());
			insert.setDouble(4, obj.getQte());
			insert.setDouble(5, obj.getPrixAchat());
			
			insert.executeUpdate();
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de l'insertion du produit dans la facture. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return -1;
		}
	}
	
	@Override
	public boolean update(LigneFacture obj) {
		// TODO Auto-generated method stub
		try {
			update.setDouble(1, obj.getPrixVente());
			update.setDouble(2, obj.getQte());
			update.setDouble(3, obj.getPrixAchat());
			update.setInt(4, obj.getProduit().getCodePro1());
			update.setInt(5, obj.getFacture().getIdFac());
			
			update.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de la modification de LigneFacture. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean delete(LigneFacture obj) {
		// TODO Auto-generated method stub
		try {
			delete.setInt(1, obj.getProduit().getCodePro1());
			delete.setInt(2, obj.getFacture().getIdFac());
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de la suppression du produit dans la facture. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}

	
	
	@Override
	public ArrayList findAll() {
		// TODO Auto-generated method stub
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
	
	public double findAllLigneFacture(String d1, String d2) {
		double d =0;
		try{
			somme.setString(1, d1);
			somme.setString(2, d2);
			ResultSet result = somme.executeQuery();
			result.next();
			d = result.getDouble(1);
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors du calcul du prix total. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return d;
	}
	
	public double findAllLigneFacture1(String string, String string2, String str) {
		double d = 0;
		try{
			somme1.setString(1, string);
			somme1.setString(2, string2);
			somme1.setString(3, str);
			
			ResultSet result = somme1.executeQuery();
			result.next();
			return result.getDouble(1);			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors du calcul du prix total. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return 0;
		}
	}
	
	public double findAllLigneFacture2(String string, String string2, String str) {
		try{
			somme2.setString(1, string);
			somme2.setString(2, string2);
			somme2.setString(3, str);
			
			ResultSet result = somme2.executeQuery();
			result.next();
			return result.getDouble(1);
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors du calcul du prix total. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return 0.0;
		}
	}
	
	public ArrayList findAllFactures(int codePro) throws SQLException{

		ArrayList<LigneFacture> ligneFactures=new ArrayList<LigneFacture>();

		searchAllFactures.setInt(1, codePro);
		ResultSet result = searchAllFactures.executeQuery();

		if(!result.next()) {
			System.out.println("yes impossible");
			result.close();
			return null;
		}
		else {
			ProduitDAO produitDAO = new ProduitDAO();
			FactureDAO factureDAO = new FactureDAO();
			Produit produit = produitDAO.find(result.getInt(1));
			Facture facture = factureDAO.find(result.getInt(2));
			ligneFactures.add(new LigneFacture(produit, facture, result.getDouble(3), result.getDouble(4), result.getDouble(5)));
			
			while (result.next()) {

				produit = produitDAO.find(result.getInt(1));
				facture = factureDAO.find(result.getInt(2));					
				ligneFactures.add(new LigneFacture(produit, facture, result.getDouble(3), result.getDouble(4), result.getDouble(5)));								
				//System.out.println("yes yes");
				//System.out.println(ligneFactures);


			}

			result.close();
		}

		return ligneFactures;
	}

	public ArrayList findAllProduits(int idFac) throws SQLException {
		
		ArrayList<LigneFacture> ligneFactures=new ArrayList<LigneFacture>();

		searchAllProduits.setInt(1, idFac);
		ResultSet result = searchAllProduits.executeQuery();
		
		if(!result.next()) {
			result.close();
			return null;
		}
		else {
			ProduitDAO produitDAO = new ProduitDAO();
			FactureDAO factureDAO = new FactureDAO();

			Produit produit = produitDAO.find(result.getInt(1));
			Facture facture = factureDAO.find(result.getInt(2));
			ligneFactures.add(new LigneFacture(produit, facture, result.getDouble(3), result.getDouble(4), result.getDouble(5)));
			
			while (result.next()) {
				System.out.println("yes bro");
				produit = produitDAO.find(result.getInt(1));
				facture = factureDAO.find(result.getInt(2));	
				System.out.println(produit+"  "+facture);
				ligneFactures.add(new LigneFacture(produit, facture, result.getDouble(3), result.getDouble(4), result.getDouble(5)));								
				System.out.println(ligneFactures);


			}

			result.close();
		}
		System.out.println(ligneFactures);

		return ligneFactures;
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		try {
			insert.close();
			delete.close();
			update.close();
			search.close();
			somme.close();
			somme1.close();
			somme2.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de la fermeture de LigneFactureDAO. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
	}
}
