package mr.univ.soa.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import mr.univ.soa.model.Departement;
import mr.univ.soa.model.Etudiant;
import mr.univ.soa.model.Module;
import mr.univ.soa.service.DepartementService;
import mr.univ.soa.service.EtudiantService;
import mr.univ.soa.service.ModuleService;

@Path("/etudiants")
@Produces(MediaType.APPLICATION_JSON)
public class EtudiantResource {

    @Inject
    EtudiantService service;

    @Inject
    DepartementService departementService;

    @Inject
    ModuleService moduleService;

    @GET
    public Response lister() {
        return Response.ok(service.tous()).build();
    }

    @GET
    @Path("/{id}")
    public Response trouver(@PathParam("id") long id) {
        Etudiant e = service.trouver(id);
        if (e == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "ETUDIANT_INTROUVABLE",
                               "message", "Identifiant inconnu"))
                .build();
        }
        return Response.ok(e).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ajouter(Etudiant entree, @Context UriInfo uriInfo) {
        if (entree == null
            || entree.getNom() == null
            || entree.getNom().isBlank()
            || entree.getEmail() == null
            || !entree.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("erreur", "DONNEES_INVALIDES",
                               "message", "Nom ou email invalide"))
                .build();
        }

        // Si un departement est specifie, verifier son existence
        if (entree.getDepartementId() != null) {
            Departement d = departementService.trouver(entree.getDepartementId());
            if (d == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erreur", "DEPARTEMENT_INEXISTANT",
                                   "message", "Le departement specifie n'existe pas"))
                    .build();
            }
        }

        Etudiant cree = service.creer(entree.getNom(), entree.getEmail(),
                                      entree.getDepartementId(), entree.getModuleIds());
        URI uri = uriInfo.getAbsolutePathBuilder()
            .path(String.valueOf(cree.getId())).build();
        return Response.created(uri).entity(cree).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifier(@PathParam("id") long id, Etudiant entree) {
        if (entree == null
            || entree.getNom() == null
            || entree.getNom().isBlank()
            || entree.getEmail() == null
            || !entree.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("erreur", "DONNEES_INVALIDES",
                               "message", "Nom ou email invalide"))
                .build();
        }

        if (entree.getDepartementId() != null) {
            Departement d = departementService.trouver(entree.getDepartementId());
            if (d == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erreur", "DEPARTEMENT_INEXISTANT",
                                   "message", "Le departement specifie n'existe pas"))
                    .build();
            }
        }

        Etudiant misAJour = service.remplacer(id, entree.getNom(), entree.getEmail(),
                                              entree.getDepartementId(), entree.getModuleIds());
        if (misAJour == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "ETUDIANT_INTROUVABLE",
                               "message", "Identifiant inconnu"))
                .build();
        }
        return Response.ok(misAJour).build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifierPartiel(@PathParam("id") long id, Etudiant entree) {
        if (entree == null || (entree.getNom() == null && entree.getEmail() == null
            && entree.getDepartementId() == null && entree.getModuleIds() == null)) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("erreur", "DONNEES_INVALIDES",
                               "message", "Au moins un champ doit etre renseigne"))
                .build();
        }
        if (entree.getNom() != null && entree.getNom().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("erreur", "DONNEES_INVALIDES",
                               "message", "Le nom ne peut pas etre vide"))
                .build();
        }
        if (entree.getEmail() != null && !entree.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("erreur", "DONNEES_INVALIDES",
                               "message", "Format d'email invalide"))
                .build();
        }
        if (entree.getDepartementId() != null) {
            Departement d = departementService.trouver(entree.getDepartementId());
            if (d == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erreur", "DEPARTEMENT_INEXISTANT",
                                   "message", "Le departement specifie n'existe pas"))
                    .build();
            }
        }

        Etudiant misAJour = service.modifierPartiel(id, entree.getNom(), entree.getEmail(),
                                                    entree.getDepartementId(), entree.getModuleIds());
        if (misAJour == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "ETUDIANT_INTROUVABLE",
                               "message", "Identifiant inconnu"))
                .build();
        }
        return Response.ok(misAJour).build();
    }

    @DELETE
    @Path("/{id}")
    public Response supprimer(@PathParam("id") long id) {
        boolean supprime = service.supprimer(id);
        if (!supprime) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "ETUDIANT_INTROUVABLE",
                               "message", "Identifiant inconnu"))
                .build();
        }
        return Response.noContent().build();
    }

    // =========================================================
    // ENDPOINTS RELATION 1-N : ETUDIANT <-> DEPARTEMENT
    // =========================================================

    @GET
    @Path("/{id}/departement")
    public Response obtenirDepartement(@PathParam("id") long id) {
        Etudiant e = service.trouver(id);
        if (e == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "ETUDIANT_INTROUVABLE", "message", "Identifiant inconnu"))
                .build();
        }
        if (e.getDepartementId() == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "AUCUN_DEPARTEMENT", "message", "L'etudiant n'est affecte a aucun departement"))
                .build();
        }
        Departement d = departementService.trouver(e.getDepartementId());
        if (d == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "DEPARTEMENT_INEXISTANT", "message", "Departement introuvable"))
                .build();
        }
        return Response.ok(d).build();
    }

    @PUT
    @Path("/{id}/departement/{deptId}")
    public Response assignerDepartement(@PathParam("id") long id, @PathParam("deptId") long deptId) {
        Etudiant e = service.trouver(id);
        if (e == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "ETUDIANT_INTROUVABLE", "message", "Etudiant inconnu"))
                .build();
        }
        Departement d = departementService.trouver(deptId);
        if (d == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "DEPARTEMENT_INTROUVABLE", "message", "Departement inconnu"))
                .build();
        }
        service.assignerDepartement(id, deptId);
        return Response.ok(service.trouver(id)).build();
    }

    // =========================================================
    // ENDPOINTS RELATION N-N : ETUDIANT <-> MODULE
    // =========================================================

    @GET
    @Path("/{id}/modules")
    public Response listerModulesDeEtudiant(@PathParam("id") long id) {
        Etudiant e = service.trouver(id);
        if (e == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "ETUDIANT_INTROUVABLE", "message", "Etudiant inconnu"))
                .build();
        }
        List<Module> modules = new ArrayList<>();
        for (Long mId : e.getModuleIds()) {
            Module m = moduleService.trouver(mId);
            if (m != null) {
                modules.add(m);
            }
        }
        return Response.ok(modules).build();
    }

    @POST
    @Path("/{id}/modules/{moduleId}")
    public Response inscrireModule(@PathParam("id") long id, @PathParam("moduleId") long moduleId) {
        Etudiant e = service.trouver(id);
        if (e == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "ETUDIANT_INTROUVABLE", "message", "Etudiant inconnu"))
                .build();
        }
        Module m = moduleService.trouver(moduleId);
        if (m == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "MODULE_INTROUVABLE", "message", "Module inconnu"))
                .build();
        }
        service.inscrireModule(id, moduleId);
        return Response.ok(service.trouver(id)).build();
    }

    @DELETE
    @Path("/{id}/modules/{moduleId}")
    public Response desinscrireModule(@PathParam("id") long id, @PathParam("moduleId") long moduleId) {
        Etudiant e = service.trouver(id);
        if (e == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "ETUDIANT_INTROUVABLE", "message", "Etudiant inconnu"))
                .build();
        }
        service.desinscrireModule(id, moduleId);
        return Response.noContent().build();
    }
}
