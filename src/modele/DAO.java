package modele;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;

public abstract class DAO<T> {
	public Connection connect = ConnexionBD.getInstance();
	
	protected PreparedStatement searchAllProduits;
	protected PreparedStatement searchAllDates;
	protected PreparedStatement searchAllFactures;
	protected PreparedStatement search;
	protected PreparedStatement insert;
	protected PreparedStatement update;
	protected PreparedStatement delete;
	protected PreparedStatement last_insert_id;
	protected PreparedStatement findForName;
	protected PreparedStatement research;
	protected PreparedStatement affiche;
	protected PreparedStatement afficheCaissier;
	protected PreparedStatement afficheMagasinier;
	protected PreparedStatement searchGest;
	protected PreparedStatement somme;
	protected PreparedStatement somme1;
	protected PreparedStatement somme2;
	protected PreparedStatement somme3;
	protected PreparedStatement somme4;
	protected PreparedStatement afficheListCaissier;
	protected PreparedStatement afficheListCaissier1;
	/*
	 * Permet de r�cup�rer un objet via son ID
	 * @param id
	 * @return
	 */
	public abstract T find(int id);
	
	
	/*
	 * Permet de cr�er une entr�e dans la base de donn�es
	 * par rapport � un objet
	 * @param obj
	 * @return la cl� de l'objet cr��
	 */
	
	public abstract int create(T obj);
	
	/*
	 * Permet de mettre � jour les donn�es d'une entr�e dans la base
	 * @param obj
	 */
	
	public abstract boolean update(T obj);
	
	/*
	 * Permet la suppression d'une entr�e dans la base
	 * @param obj
	 */
	
	public abstract boolean delete(T obj);
	
	/*
	 * Permet de fermer les requêtes préparées
	 */
	
	public abstract void close();
	
	/*
	 * permet d'obtenir le type de gestionnaire
	 * @param login, password
	 * @return String*/
	//public abstract String researchTypeGest(String login, String password);

	/*
	 * Permet de retourner une liste d'objet
	 * @return un arraylist
	 */
	public abstract ArrayList findAll();
	
	/*
	 * Permet de retourner une liste des Caissiers
	 * @return un arraylist
	 */
	public abstract ArrayList findAllCaissier();
	
	/*
	 * Permet de retourner une liste des magasiniers
	 * @return un arraylist
	 */
	public abstract ArrayList findAllMagasinier();
	
	/*
	 * Permet de retourner la somme des ventes
	 * @return un double
	 * @param obj
	 */
	/*public abstract double sommeTotal(Timestamp d);
	
	public abstract double sommeTotalSemaine(Timestamp d);
	
	public abstract double sommeTotalMois(Timestamp d);
	
	public abstract double sommeTotalAnnee(Timestamp d);
	
	public abstract ArrayList findAll_2(String str);
	
	public abstract ArrayList findAllLigneFacture(String str);*/
}
