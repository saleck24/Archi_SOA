package mr.univ.soa.api;

import java.net.URI;
import java.util.Map;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import mr.univ.soa.model.Departement;
import mr.univ.soa.service.DepartementService;
import mr.univ.soa.service.EtudiantService;

@Path("/departements")
@Produces(MediaType.APPLICATION_JSON)
public class DepartementResource {

    @Inject
    DepartementService service;

    @Inject
    EtudiantService etudiantService;

    @GET
    public Response lister() {
        return Response.ok(service.tous()).build();
    }

    @GET
    @Path("/{id}")
    public Response trouver(@PathParam("id") long id) {
        Departement d = service.trouver(id);
        if (d == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "DEPARTEMENT_INTROUVABLE",
                               "message", "Identifiant inconnu"))
                .build();
        }
        return Response.ok(d).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ajouter(Departement entree, @Context UriInfo uriInfo) {
        if (entree == null
            || entree.getNom() == null
            || entree.getNom().isBlank()
            || entree.getCode() == null
            || entree.getCode().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("erreur", "DONNEES_INVALIDES",
                               "message", "Nom et code du département requis"))
                .build();
        }
        Departement cree = service.creer(entree.getNom(), entree.getCode());
        URI uri = uriInfo.getAbsolutePathBuilder()
            .path(String.valueOf(cree.getId())).build();
        return Response.created(uri).entity(cree).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifier(@PathParam("id") long id, Departement entree) {
        if (entree == null
            || entree.getNom() == null
            || entree.getNom().isBlank()
            || entree.getCode() == null
            || entree.getCode().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("erreur", "DONNEES_INVALIDES",
                               "message", "Nom et code du département requis"))
                .build();
        }
        Departement misAJour = service.remplacer(id, entree.getNom(), entree.getCode());
        if (misAJour == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "DEPARTEMENT_INTROUVABLE",
                               "message", "Identifiant inconnu"))
                .build();
        }
        return Response.ok(misAJour).build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifierPartiel(@PathParam("id") long id, Departement entree) {
        if (entree == null || (entree.getNom() == null && entree.getCode() == null)) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("erreur", "DONNEES_INVALIDES",
                               "message", "Au moins un champ (nom ou code) doit être renseigné"))
                .build();
        }
        if (entree.getNom() != null && entree.getNom().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("erreur", "DONNEES_INVALIDES",
                               "message", "Le nom ne peut pas être vide"))
                .build();
        }
        if (entree.getCode() != null && entree.getCode().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("erreur", "DONNEES_INVALIDES",
                               "message", "Le code ne peut pas être vide"))
                .build();
        }

        Departement misAJour = service.modifierPartiel(id, entree.getNom(), entree.getCode());
        if (misAJour == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "DEPARTEMENT_INTROUVABLE",
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
                .entity(Map.of("erreur", "DEPARTEMENT_INTROUVABLE",
                               "message", "Identifiant inconnu"))
                .build();
        }
        return Response.noContent().build();
    }

    // =========================================================
    // RELATION 1-N : Lister les etudiants du departement
    // =========================================================
    @GET
    @Path("/{id}/etudiants")
    public Response listerEtudiantsDuDepartement(@PathParam("id") long id) {
        Departement d = service.trouver(id);
        if (d == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "DEPARTEMENT_INTROUVABLE", "message", "Departement inconnu"))
                .build();
        }
        return Response.ok(etudiantService.trouverParDepartement(id)).build();
    }
}
