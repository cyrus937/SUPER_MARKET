package modele;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Gestionnaire extends RecursiveTreeObject<Gestionnaire>{
	private Short idGest = null;
	private String nomGest;
	private byte typeGest;
	private String login;
	private String pwd;
	private boolean actif = false;
	
	public Gestionnaire(String nomGest, byte typeGest, String login, String pwd, boolean actif) {
		super();
		this.nomGest = nomGest;
		this.typeGest = typeGest;
		this.login = login;
		this.pwd = pwd;
		this.actif = actif;
	}
	
	public Gestionnaire(short idGest, String nomGest, byte typeGest, String login, String pwd, boolean actif) {
		this(nomGest, typeGest, login, pwd, actif);
		this.idGest = idGest;
	}

	public Gestionnaire(short idGest, String nomGest, String login, boolean actif) {
		// TODO Auto-generated constructor stub
		this.idGest = idGest;
		this.nomGest = nomGest;
		this.login = login;
		this.actif = actif;
		
	}

	public Short getIdGest() {
		return idGest;
	}

	public void setIdGest(short idGest) {
		this.idGest = idGest;
	}

	public String getNomGest() {
		return nomGest;
	}

	public void setNomGest(String nomGest) {
		this.nomGest = nomGest;
	}

	public byte getTypeGest() {
		return typeGest;
	}

	public void setTypeGest(byte typeGest) {
		this.typeGest = typeGest;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	/*@Override
	public String toString() {
		return "Gestionnaire [idGest=" + idGest + ", nomGest=" + nomGest + ", typeGest=" + typeGest + ", login=" + login
				+ ", pwd=" + pwd + ", actif=" + actif + "]";
	}*/
	
	@Override
	public String toString() {
		return nomGest;
	}
	
}
