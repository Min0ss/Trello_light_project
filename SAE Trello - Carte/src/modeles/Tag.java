package modeles;

public class Tag {

    protected int id;
    protected String nom;
    protected String couleur;
    protected static int compteurId = 1;
  

    public Tag(String nom, String couleur) throws Exception {
        this.id = compteurId++;
        if (!couleur.matches("#[0-9A-Fa-f]{6}")) {
            throw new Exception("Code hexadécimal non valide");
        }

        this.nom = nom;
        this.couleur = couleur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    @Override
    public String toString() {
        return "Tag [id=" + id + ", nom=" + nom + ", couleur=" + couleur + "]";
    }
}
