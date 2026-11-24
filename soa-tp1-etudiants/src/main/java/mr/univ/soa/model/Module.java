package mr.univ.soa.model;

public class Module {
    private Long id;
    private String code;
    private String nom;
    private Integer coefficient;

    public Module() {}

    public Module(Long id, String code, String nom, Integer coefficient) {
        this.id = id;
        this.code = code;
        this.nom = nom;
        this.coefficient = coefficient;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Integer getCoefficient() { return coefficient; }
    public void setCoefficient(Integer coefficient) { this.coefficient = coefficient; }
}
