
package modeles;

public class Carte {

	private static int prochainId = 1;
	private int id;
	private String nom;
	private String description;
	private String dateDebut;
	private String dateFin;
	private Tag tag;

	public Carte() {
		// Constructeur vide
	}

	public Carte(String nom, String description, String dateDebut, String dateFin) {
		this.id = prochainId;
		this.nom = nom;
		this.description = description;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		  prochainId++;// Incrémenter l'ID pour la prochaine carte
	}

	// Ajouter les getters et les setters pour les membres privés

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}

	public String getDateFin() {
		return dateFin;
	}

	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "Carte [id=" + id + ", nom=" + nom + ", description=" + description + ", dateDebut=" + dateDebut
				+ ", dateFin=" + dateFin + ", tag=" + tag + "]";
	}
}


