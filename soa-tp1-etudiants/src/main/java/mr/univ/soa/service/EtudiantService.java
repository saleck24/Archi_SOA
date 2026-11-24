package mr.univ.soa.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;
import mr.univ.soa.model.Etudiant;

@ApplicationScoped
public class EtudiantService {
    private final ConcurrentMap<Long, Etudiant> donnees =
        new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public List<Etudiant> tous() {
        List<Etudiant> resultat = new ArrayList<>(donnees.values());
        resultat.sort(Comparator.comparing(Etudiant::getId));
        return resultat;
    }

    public Etudiant trouver(long id) { return donnees.get(id); }

    public Etudiant creer(String nom, String email) {
        return creer(nom, email, null, null);
    }

    public Etudiant creer(String nom, String email, Long departementId, Set<Long> moduleIds) {
        long id = sequence.incrementAndGet();
        Etudiant e = new Etudiant(id, nom.trim(), email.trim());
        e.setDepartementId(departementId);
        if (moduleIds != null) {
            e.setModuleIds(new HashSet<>(moduleIds));
        }
        donnees.put(id, e);
        return e;
    }

    public boolean supprimer(long id) {
        return donnees.remove(id) != null;
    }

    public Etudiant remplacer(long id, String nom, String email, Long departementId, Set<Long> moduleIds) {
        Etudiant existant = donnees.get(id);
        if (existant == null) {
            return null;
        }
        existant.setNom(nom.trim());
        existant.setEmail(email.trim());
        existant.setDepartementId(departementId);
        existant.setModuleIds(moduleIds != null ? new HashSet<>(moduleIds) : new HashSet<>());
        return existant;
    }

    public Etudiant remplacer(long id, String nom, String email) {
        Etudiant existant = donnees.get(id);
        if (existant == null) {
            return null;
        }
        existant.setNom(nom.trim());
        existant.setEmail(email.trim());
        return existant;
    }

    public Etudiant modifierPartiel(long id, String nom, String email, Long departementId, Set<Long> moduleIds) {
        Etudiant existant = donnees.get(id);
        if (existant == null) {
            return null;
        }
        if (nom != null && !nom.isBlank()) {
            existant.setNom(nom.trim());
        }
        if (email != null && !email.isBlank()) {
            existant.setEmail(email.trim());
        }
        if (departementId != null) {
            existant.setDepartementId(departementId);
        }
        if (moduleIds != null) {
            existant.setModuleIds(new HashSet<>(moduleIds));
        }
        return existant;
    }

    public Etudiant modifierPartiel(long id, String nom, String email) {
        return modifierPartiel(id, nom, email, null, null);
    }

    // =========================================================
    // RELATION 1-N : Etudiant -> Departement
    // =========================================================
    public boolean assignerDepartement(long etudiantId, long departementId) {
        Etudiant e = donnees.get(etudiantId);
        if (e == null) return false;
        e.setDepartementId(departementId);
        return true;
    }

    public List<Etudiant> trouverParDepartement(long departementId) {
        return donnees.values().stream()
            .filter(e -> e.getDepartementId() != null && e.getDepartementId().equals(departementId))
            .sorted(Comparator.comparing(Etudiant::getId))
            .collect(Collectors.toList());
    }

    // =========================================================
    // RELATION N-N : Etudiant <-> Module
    // =========================================================
    public boolean inscrireModule(long etudiantId, long moduleId) {
        Etudiant e = donnees.get(etudiantId);
        if (e == null) return false;
        return e.getModuleIds().add(moduleId);
    }

    public boolean desinscrireModule(long etudiantId, long moduleId) {
        Etudiant e = donnees.get(etudiantId);
        if (e == null) return false;
        return e.getModuleIds().remove(moduleId);
    }

    public List<Etudiant> trouverParModule(long moduleId) {
        return donnees.values().stream()
            .filter(e -> e.getModuleIds() != null && e.getModuleIds().contains(moduleId))
            .sorted(Comparator.comparing(Etudiant::getId))
            .collect(Collectors.toList());
    }
}
