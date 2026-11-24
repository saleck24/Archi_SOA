package mr.univ.soa.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import jakarta.enterprise.context.ApplicationScoped;
import mr.univ.soa.model.Module;

@ApplicationScoped
public class ModuleService {
    private final ConcurrentMap<Long, Module> donnees =
        new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public List<Module> tous() {
        List<Module> resultat = new ArrayList<>(donnees.values());
        resultat.sort(Comparator.comparing(Module::getId));
        return resultat;
    }

    public Module trouver(long id) { return donnees.get(id); }

    public Module creer(String code, String nom, Integer coefficient) {
        long id = sequence.incrementAndGet();
        int coef = (coefficient != null && coefficient > 0) ? coefficient : 1;
        Module m = new Module(id, code.trim().toUpperCase(), nom.trim(), coef);
        donnees.put(id, m);
        return m;
    }

    public boolean supprimer(long id) {
        return donnees.remove(id) != null;
    }

    public Module remplacer(long id, String code, String nom, Integer coefficient) {
        Module existant = donnees.get(id);
        if (existant == null) {
            return null;
        }
        existant.setCode(code.trim().toUpperCase());
        existant.setNom(nom.trim());
        existant.setCoefficient((coefficient != null && coefficient > 0) ? coefficient : 1);
        return existant;
    }

    public Module modifierPartiel(long id, String code, String nom, Integer coefficient) {
        Module existant = donnees.get(id);
        if (existant == null) {
            return null;
        }
        if (code != null && !code.isBlank()) {
            existant.setCode(code.trim().toUpperCase());
        }
        if (nom != null && !nom.isBlank()) {
            existant.setNom(nom.trim());
        }
        if (coefficient != null && coefficient > 0) {
            existant.setCoefficient(coefficient);
        }
        return existant;
    }
}
