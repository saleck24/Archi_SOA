package mr.univ.soa.model;

import java.util.HashSet;
import java.util.Set;

public class Etudiant {
    private Long id;
    private String nom;
    private String email;

    // Relation 1-N : un etudiant appartient a un departement (optionnel a l'inscription)
    private Long departementId;

    // Relation N-N : un etudiant est inscrit a plusieurs modules (et un module a plusieurs etudiants)
    private Set<Long> moduleIds = new HashSet<>();

    public Etudiant() {}

    public Etudiant(Long id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    public Etudiant(Long id, String nom, String email, Long departementId) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.departementId = departementId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getDepartementId() { return departementId; }
    public void setDepartementId(Long departementId) { this.departementId = departementId; }

    public Set<Long> getModuleIds() {
        if (moduleIds == null) {
            moduleIds = new HashSet<>();
        }
        return moduleIds;
    }

    public void setModuleIds(Set<Long> moduleIds) {
        this.moduleIds = (moduleIds != null) ? moduleIds : new HashSet<>();
    }
}
