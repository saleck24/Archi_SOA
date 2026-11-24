package mr.univ.soa.model;

public class Departement {
    private Long id;
    private String nom;
    private String code;

    public Departement() {}

    public Departement(Long id, String nom, String code) {
        this.id = id;
        this.nom = nom;
        this.code = code;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
}
