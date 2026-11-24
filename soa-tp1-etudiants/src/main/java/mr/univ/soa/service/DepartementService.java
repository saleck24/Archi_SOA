package mr.univ.soa.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import jakarta.enterprise.context.ApplicationScoped;
import mr.univ.soa.model.Departement;

@ApplicationScoped
public class DepartementService {
    private final ConcurrentMap<Long, Departement> donnees =
        new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public List<Departement> tous() {
        List<Departement> resultat = new ArrayList<>(donnees.values());
        resultat.sort(Comparator.comparing(Departement::getId));
        return resultat;
    }

    public Departement trouver(long id) { return donnees.get(id); }

    public Departement creer(String nom, String code) {
        long id = sequence.incrementAndGet();
        Departement d = new Departement(id, nom.trim(), code.trim().toUpperCase());
        donnees.put(id, d);
        return d;
    }

    public boolean supprimer(long id) {
        return donnees.remove(id) != null;
    }

    public Departement remplacer(long id, String nom, String code) {
        Departement existant = donnees.get(id);
        if (existant == null) {
            return null;
        }
        existant.setNom(nom.trim());
        existant.setCode(code.trim().toUpperCase());
        return existant;
    }

    public Departement modifierPartiel(long id, String nom, String code) {
        Departement existant = donnees.get(id);
        if (existant == null) {
            return null;
        }
        if (nom != null && !nom.isBlank()) {
            existant.setNom(nom.trim());
        }
        if (code != null && !code.isBlank()) {
            existant.setCode(code.trim().toUpperCase());
        }
        return existant;
    }
}
