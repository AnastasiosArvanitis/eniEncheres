package fr.eni.eniEncheres.bo;

public class Retrait {

    private int id;
    private String rue;
    private String codePostal;
    private String ville;
    private boolean retrait;




    public Retrait() {}

    public Retrait(String rue, String codePostal, String ville) {
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public Retrait(int id, String rue, String codePostal, String ville) {
        this.id = id;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public Retrait(int id, String rue, String codePostal, String ville, boolean retrait) {
        this.id = id;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.retrait = retrait ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public boolean getRetrait() {
        return retrait;
    }

    public void setRetrait(boolean retrait) {
        this.retrait = retrait;
    }

    @Override
    public String toString() {
        return "Retrait{" +
                "id=" + id +
                ", rue='" + rue + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }
}
