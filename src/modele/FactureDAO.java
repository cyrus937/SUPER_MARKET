package modele;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class FactureDAO extends DAO<Facture>{

	public FactureDAO() {
		// TODO Auto-generated constructor stub
		try {
			search = this.connect.prepareStatement("SELECT * FROM Facture WHERE idFac = ?");
			insert = this.connect.prepareStatement("INSERT INTO Facture (dateFac,codePaiement,remise,montant,modePaiement,idCaissiere,idClient,idCommande)"
					+ " VALUES (?,?,?,?,?,?,?,?)");
			update = this.connect.prepareStatement("UPDATE Facture SET dateFac = ?,codePaiement = ?,remise = ?, "
					+ "montant = ?, modePaiement = ?, idCaissiere = ?,idClient = ?,idCommande = ? WHERE idFac = ?");
			last_insert_id = this.connect.prepareStatement("SELECT LAST_INSERT_ID()");
			
			research = this.connect.prepareStatement("Select SUM(montant) as 'somme' from facture where Date(dateFac) = ? ");
			somme = this.connect.prepareStatement("Select SUM(montant) as 'somme' from facture where WEEK(dateFac) = WEEK(?) ");
			somme1 = this.connect.prepareStatement("Select SUM(montant) as 'somme' from facture where month(dateFac) = month(?) ");
			somme2 = this.connect.prepareStatement("Select SUM(montant) as 'somme' from facture where YEAR(dateFac) = YEAR(?) ");
			somme3 = this.connect.prepareStatement("Select SUM(montant) as 'somme' from facture where month(dateFac) = ? ");
			somme4 = this.connect.prepareStatement("Select SUM(montant) as 'somme' from facture where YEAR(dateFac) = ? ");
			affiche = this.connect.prepareStatement("SELECT * FROM Facture");
			afficheCaissier = this.connect.prepareStatement("select client.nom,sum(montant) as 'Montant' from facture "
        				+ "join client on client.idClient=facture.idClient "
        				+ "where YEAR(dateFac)=YEAR(now()) and client.nom <> 'ND' "
        				+ "group by facture.idClient " + 
        				"order by Montant desc " + 
        				"limit 5");
			afficheMagasinier = this.connect.prepareStatement("select client.nom,sum(montant) as 'Montant' from facture "
    				+ "join client on client.idClient=facture.idClient "
    				+ "where monthname(dateFac)=? and YEAR(dateFac)=YEAR(now()) and client.nom <> 'ND' "
    				+ "group by facture.idClient " + 
    				"order by Montant desc " + 
    				"limit 5");
			afficheListCaissier = this.connect.prepareStatement("select gestionnaire.nomGest,sum(montant) as 'Montant' from facture "
        				+ "join gestionnaire on gestionnaire.idGest=facture.idCaissiere "
        				+ "where YEAR(dateFac)=YEAR(now())"
        				+ "group by facture.idCaissiere " + 
        				"order by Montant desc " + 
        				"limit 5");
			afficheListCaissier1 = this.connect.prepareStatement("select gestionnaire.nomGest,sum(montant) as 'Montant' from facture "
        				+ "join gestionnaire on gestionnaire.idGest=facture.idCaissiere "
        				+ "where monthname(dateFac)=? and YEAR(dateFac)=YEAR(now())"
        				+ "group by facture.idCaissiere " + 
        				"order by Montant desc " + 
        				"limit 5");
			
			searchAllDates= this.connect.prepareStatement("SELECT * FROM Facture WHERE dateFac<=? AND dateFac>=?");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public Facture find(int id) {
		// TODO Auto-generated method stub
		Facture facture = null;
		try {
			search.setInt(1, id);
			ResultSet result = search.executeQuery();
			if(!result.next()) {
				result.close();	
				return null;
			}
			else {
				GestionnaireDAO gestionnaireDAO = new GestionnaireDAO();
				Gestionnaire gestionnaire = gestionnaireDAO.find(result.getInt(7));
				ClientDAO clientDAO = new ClientDAO();
				Client client = clientDAO.find(result.getInt(8));
				CommandeDAO commandeDAO = new CommandeDAO();
				Commande commande = commandeDAO.find(result.getInt(9));
				facture = new Facture(result.getInt(1), result.getTimestamp(2), result.getString(3), result.getDouble(4), result.getDouble(5), result.getBoolean(6),gestionnaire, client, commande);
				clientDAO.close();
				gestionnaireDAO.close();
				commandeDAO.close();
				result.close();
			}
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche du produit. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return facture;
	}

	@Override
	public int create(Facture obj) {
		// TODO Auto-generated method stub
		try {
			insert.setTimestamp(1, obj.getDateFac());
			insert.setString(2, obj.getCodePaiement());
			insert.setDouble(3, obj.getRemise());
			insert.setDouble(4, obj.getMontant());
			insert.setBoolean(5, obj.isModePaiement());
			insert.setShort(6, obj.getCaissiere().getIdGest());
			insert.setInt(7, obj.getClient().getIdClient());
			if(obj.getCommande() == null)
				insert.setObject(8, null);
			else
				insert.setInt(8, obj.getCommande().getIdCommande());
			insert.executeUpdate();
			
			ResultSet result = last_insert_id.executeQuery();
			result.next();
			int id = result.getInt(1);
			obj.setIdFac(id);
			result.close();
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de l'ajout de la facture. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public boolean update(Facture obj) {
		try {
			update.setTimestamp(1, obj.getDateFac());
			update.setString(2, obj.getCodePaiement());
			update.setDouble(3, obj.getRemise());
			update.setDouble(4, obj.getMontant());
			update.setBoolean(5, obj.isModePaiement());
			update.setShort(6, obj.getCaissiere().getIdGest());
			update.setInt(7, obj.getClient().getIdClient());
			if(obj.getCommande() == null)
				update.setObject(8, null);
			else
				update.setInt(8, obj.getCommande().getIdCommande());
			update.setInt(9, obj.getIdFac());
			
			update.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Erreur lors de la modification de la facture. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}
	
	public double sommeTotal(Date date) {
		// TODO Auto-generated method stub
		try{
			research.setDate(1,date);
			ResultSet result = research.executeQuery();
			//System.out.println(result.getDouble(1));
			result.next();
			if(result == null)
				return 0.0;
			else
				return result.getDouble(1);
		}catch (SQLException e) {
			System.out.println("Erreur lors de l'obtention de la somme des ventes de la journée. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return 0.0;
		}
	}

	@Override
	public boolean delete(Facture obj) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	public ArrayList findAll() {
		// TODO Auto-generated method stub
		ArrayList<Facture> factures =new ArrayList<Facture>();

		try {
			ResultSet result = affiche.executeQuery();

			if(!result.next())
			{
				return null;
			}
			else
			{
				GestionnaireDAO gestionnaireDAO = new GestionnaireDAO();
				ClientDAO clientDAO=new ClientDAO();
				CommandeDAO commandeDAO=new CommandeDAO();

				//LigneFactureDAO ligneFactureDAO=new LigneFactureDAO();				
				Gestionnaire gestionnaire =gestionnaireDAO.find(result.getInt(7));
				Client client= clientDAO.find(result.getInt(8));
				Commande commande = commandeDAO.find(result.getInt(9));
				factures.add(new Facture(result.getInt(1), result.getTimestamp(2), result.getString(3), result.getDouble(4),result.getDouble(5),result.getBoolean(6) ,gestionnaire, client,commande));


				//private ArrayList<LigneFacture> lignesFacture = null;

				//factures.add(new Facture(result.getInt(1), result.getTimestamp(2), result.getString(3), result.getDouble(4),result.getDouble(5),result.getBoolean(6) ,gestionnaire, client,commande));

				while (result.next())
				{
					gestionnaire =gestionnaireDAO.find(result.getInt(7));
					client= clientDAO.find(result.getInt(8));
					commande = commandeDAO.find(result.getInt(9));
					factures.add(new Facture(result.getInt(1), result.getTimestamp(2), result.getString(3), result.getDouble(4),result.getDouble(5),result.getBoolean(6) ,gestionnaire, client,commande));
				}
			}
			result.close();
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche des factures . Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return factures;
	}
	
	public Facture lastInser(){
		Facture fact = null;
		
		try {
			ResultSet result = last_insert_id.executeQuery();
			result.next();
			
			fact = this.find(result.getInt(1));
			
			/*GestionnaireDAO gestionnaireDAO = new GestionnaireDAO();
			ClientDAO clientDAO=new ClientDAO();
			CommandeDAO commandeDAO=new CommandeDAO();

			//LigneFactureDAO ligneFactureDAO=new LigneFactureDAO();				
			Gestionnaire gestionnaire =gestionnaireDAO.find(result.getInt(7));
			Client client= clientDAO.find(result.getInt(8));
			Commande commande = commandeDAO.find(result.getInt(9));
			fact = new Facture(result.getInt(1), result.getTimestamp(2), result.getString(3), result.getDouble(4),result.getDouble(5),result.getBoolean(6) ,gestionnaire, client,commande);*/

		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche des factures . Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return fact;
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
			last_insert_id.close();
			search.close();
			afficheCaissier.close();
			afficheListCaissier.close();
			somme.close();
			somme1.close();
			somme2.close();
		} catch (SQLException e) {
			System.out.println("Erreur lors de la fermeture de factureDAO. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
	}
	
	public double sommeTotalSemaine(Date date) {
		// TODO Auto-generated method stub
		try{
			somme.setDate(1,date);
			ResultSet result = somme.executeQuery();
			result.next();
			if(result == null)
				return 0;
			else
				return result.getDouble(1);
		}catch (SQLException e) {
			System.out.println("Erreur lors de l'obtention de la somme des ventes de la semaine. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return 0;
		}
	}
	
	public double sommeTotalMois(Date date) {
		// TODO Auto-generated method stub
		try{
			somme1.setDate(1,date);
			ResultSet result = somme1.executeQuery();
			result.next();
			if(result == null)
				return 0;
			else
				return result.getDouble(1);
		}catch (SQLException e) {
			System.out.println("Erreur lors de l'obtention de la somme des ventes du mois. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return 0;
		}
	}
	
	public double sommeTotalAnnee(Date date) {
		// TODO Auto-generated method stub
		try{
			somme2.setDate(1,date);
			ResultSet result = somme2.executeQuery();
			result.next();
			if(result == null)
				return 0;
			else
				return result.getDouble(1);
		}catch (SQLException e) {
			System.out.println("Erreur lors de l'obtention de la somme des ventes de l'année. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return 0;
		}
	}
	
	public double sommeTotalMois(int date) {
		// TODO Auto-generated method stub
		try{
			somme3.setInt(1,date);
			ResultSet result = somme3.executeQuery();
			result.next();
			if(result == null)
				return 0;
			else
				return result.getDouble(1);
		}catch (SQLException e) {
			System.out.println("Erreur lors de l'obtention de la somme des ventes du mois. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return 0;
		}
	}
	
	public double sommeTotalAnnee(int date) {
		// TODO Auto-generated method stub
		try{
			somme4.setInt(1, date);;
			ResultSet result = somme4.executeQuery();
			result.next();
			if(result == null)
				return 0;
			else
				return result.getDouble(1);
		}catch (SQLException e) {
			System.out.println("Erreur lors de l'obtention de la somme des ventes de l'année. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
			return 0;
		}
	}
	
	public ArrayList findMeilleurClient(){
		ArrayList <Client> listClient = new ArrayList<Client>(); 
		try {
			ResultSet result = afficheCaissier.executeQuery();
			
			while(result.next())
			{
				listClient.add(new Client(result.getString(1),String.valueOf(result.getDouble(2)),null,0,true));
			}
			result.close();
		}catch (SQLException e) {
			System.out.println("Erreur lors de l'obtention des meilleurs client de l'année. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return listClient;
	}
	
	public ArrayList findMeilleurClient(String str){
		ArrayList <Client> listClient = new ArrayList<Client>(); 
		try {
			afficheMagasinier.setString(1, str);
			ResultSet result = afficheMagasinier.executeQuery();
			
			while(result.next())
			{
				listClient.add(new Client(result.getString(1),String.valueOf(result.getDouble(2)),null,0,true));
			}
			result.close();
		}catch (SQLException e) {
			System.out.println("Erreur lors de l'obtention des meilleurs client du mois. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return listClient;
	}
	
	public ArrayList findMeilleurCaissier()
	{
		ArrayList<Gestionnaire> listeCaissier = new ArrayList<Gestionnaire>();
		try {
			ResultSet result = afficheListCaissier.executeQuery();
			
			while(result.next())
			{
				listeCaissier.add(new Gestionnaire(result.getString(1),(byte)0,String.valueOf(result.getDouble(2)),null,true));
			}
			result.close();
		}catch (SQLException e) {
			System.out.println("Erreur lors de l'obtention des meilleurs caissier de l'année. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return listeCaissier;
	}
	
	public ArrayList findMeilleurCaissier(String str)
	{
		ArrayList<Gestionnaire> listeCaissier = new ArrayList<Gestionnaire>();
		try {
			afficheListCaissier1.setString(1, str);
			ResultSet result = afficheListCaissier1.executeQuery();
			
			while(result.next())
			{
				listeCaissier.add(new Gestionnaire(result.getString(1),(byte)0,String.valueOf(result.getDouble(2)),null,true));
			}
			result.close();
		}catch (SQLException e) {
			System.out.println("Erreur lors de l'obtention des meilleurs caissier de l'année. Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return listeCaissier;
	}
	
	
	public ArrayList findAllDates(Date dateSup,Date dateInf) throws SQLException {

		ArrayList<Facture> factures =new ArrayList<Facture>();
		searchAllDates.setDate(1, dateSup);
		searchAllDates.setDate(2, dateInf);
		
		try {
			
			ResultSet result = searchAllDates.executeQuery();
			
			if(!result.next())
			{
				System.out.println("impossible");
				return null;
			}
			else
			{
				GestionnaireDAO gestionnaireDAO = new GestionnaireDAO();
				ClientDAO clientDAO=new ClientDAO();
				//LigneFactureDAO ligneFactureDAO=new LigneFactureDAO();				
				CommandeDAO commandeDAO=new CommandeDAO();

				Gestionnaire gestionnaire =gestionnaireDAO.find(result.getInt(7));
				Client client= clientDAO.find(result.getInt(8));
				Commande commande = commandeDAO.find(result.getInt(9));
				factures.add(new Facture(result.getInt(1), result.getTimestamp(2), result.getString(3), result.getDouble(4),result.getDouble(5),result.getBoolean(6) ,gestionnaire, client,commande));

				//private ArrayList<LigneFacture> lignesFacture = null;

				//factures.add(new Facture(result.getInt(1), result.getTimestamp(2), result.getString(3), result.getDouble(4),result.getDouble(5),result.getBoolean(6) ,gestionnaire, client,commande));

				while (result.next())
				{
					gestionnaire =gestionnaireDAO.find(result.getInt(7));
					client= clientDAO.find(result.getInt(8));
					commande = commandeDAO.find(result.getInt(9));
					factures.add(new Facture(result.getInt(1), result.getTimestamp(2), result.getString(3), result.getDouble(4),result.getDouble(5),result.getBoolean(6) ,gestionnaire, client,commande));
				}
				System.out.println(factures);
			}
			result.close();
		}catch(SQLException e) {
			System.out.println("Erreur lors de la recherche des factures . Code erreur : " + e.getErrorCode());
			e.printStackTrace();
		}
		return factures;

	}
}
