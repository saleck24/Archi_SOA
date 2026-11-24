package mr.univ.soa.api;

import java.net.URI;
import java.util.Map;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import mr.univ.soa.model.Module;
import mr.univ.soa.service.EtudiantService;
import mr.univ.soa.service.ModuleService;

@Path("/modules")
@Produces(MediaType.APPLICATION_JSON)
public class ModuleResource {

    @Inject
    ModuleService service;

    @Inject
    EtudiantService etudiantService;

    @GET
    public Response lister() {
        return Response.ok(service.tous()).build();
    }

    @GET
    @Path("/{id}")
    public Response trouver(@PathParam("id") long id) {
        Module m = service.trouver(id);
        if (m == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "MODULE_INTROUVABLE",
                               "message", "Identifiant inconnu"))
                .build();
        }
        return Response.ok(m).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ajouter(Module entree, @Context UriInfo uriInfo) {
        if (entree == null
            || entree.getCode() == null
            || entree.getCode().isBlank()
            || entree.getNom() == null
            || entree.getNom().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("erreur", "DONNEES_INVALIDES",
                               "message", "Code et nom du module requis"))
                .build();
        }
        Module cree = service.creer(entree.getCode(), entree.getNom(), entree.getCoefficient());
        URI uri = uriInfo.getAbsolutePathBuilder()
            .path(String.valueOf(cree.getId())).build();
        return Response.created(uri).entity(cree).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifier(@PathParam("id") long id, Module entree) {
        if (entree == null
            || entree.getCode() == null
            || entree.getCode().isBlank()
            || entree.getNom() == null
            || entree.getNom().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("erreur", "DONNEES_INVALIDES",
                               "message", "Code et nom du module requis"))
                .build();
        }
        Module misAJour = service.remplacer(id, entree.getCode(), entree.getNom(), entree.getCoefficient());
        if (misAJour == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "MODULE_INTROUVABLE",
                               "message", "Identifiant inconnu"))
                .build();
        }
        return Response.ok(misAJour).build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifierPartiel(@PathParam("id") long id, Module entree) {
        if (entree == null || (entree.getCode() == null && entree.getNom() == null && entree.getCoefficient() == null)) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("erreur", "DONNEES_INVALIDES",
                               "message", "Au moins un champ (code, nom ou coefficient) doit être renseigné"))
                .build();
        }
        if (entree.getCode() != null && entree.getCode().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("erreur", "DONNEES_INVALIDES",
                               "message", "Le code ne peut pas être vide"))
                .build();
        }
        if (entree.getNom() != null && entree.getNom().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("erreur", "DONNEES_INVALIDES",
                               "message", "Le nom ne peut pas être vide"))
                .build();
        }

        Module misAJour = service.modifierPartiel(id, entree.getCode(), entree.getNom(), entree.getCoefficient());
        if (misAJour == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "MODULE_INTROUVABLE",
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
                .entity(Map.of("erreur", "MODULE_INTROUVABLE",
                               "message", "Identifiant inconnu"))
                .build();
        }
        return Response.noContent().build();
    }

    // =========================================================
    // RELATION N-N : Lister les etudiants d'un module
    // =========================================================
    @GET
    @Path("/{id}/etudiants")
    public Response listerEtudiantsDuModule(@PathParam("id") long id) {
        Module m = service.trouver(id);
        if (m == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "MODULE_INTROUVABLE", "message", "Module inconnu"))
                .build();
        }
        return Response.ok(etudiantService.trouverParModule(id)).build();
    }
}
