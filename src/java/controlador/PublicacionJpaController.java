/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Admisnistrador;
import modelo.Publicacionfotos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Comentarios;
import modelo.Publicacion;


/**
 *
 * @author DAVID
 */
public class PublicacionJpaController implements Serializable {

    public PublicacionJpaController() {
        this.emf = Persistence.createEntityManagerFactory("mostrarYcomentarPU");
    }

    public PublicacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Publicacion publicacion) {
        if (publicacion.getPublicacionfotosList() == null) {
            publicacion.setPublicacionfotosList(new ArrayList<Publicacionfotos>());
        }
        if (publicacion.getComentariosList() == null) {
            publicacion.setComentariosList(new ArrayList<Comentarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Admisnistrador idAdministrador = publicacion.getIdAdministrador();
            if (idAdministrador != null) {
                idAdministrador = em.getReference(idAdministrador.getClass(), idAdministrador.getIdAdministrador());
                publicacion.setIdAdministrador(idAdministrador);
            }
            List<Publicacionfotos> attachedPublicacionfotosList = new ArrayList<Publicacionfotos>();
            for (Publicacionfotos publicacionfotosListPublicacionfotosToAttach : publicacion.getPublicacionfotosList()) {
                publicacionfotosListPublicacionfotosToAttach = em.getReference(publicacionfotosListPublicacionfotosToAttach.getClass(), publicacionfotosListPublicacionfotosToAttach.getIdPublicacionFotos());
                attachedPublicacionfotosList.add(publicacionfotosListPublicacionfotosToAttach);
            }
            publicacion.setPublicacionfotosList(attachedPublicacionfotosList);
            List<Comentarios> attachedComentariosList = new ArrayList<Comentarios>();
            for (Comentarios comentariosListComentariosToAttach : publicacion.getComentariosList()) {
                comentariosListComentariosToAttach = em.getReference(comentariosListComentariosToAttach.getClass(), comentariosListComentariosToAttach.getIdComentarios());
                attachedComentariosList.add(comentariosListComentariosToAttach);
            }
            publicacion.setComentariosList(attachedComentariosList);
            em.persist(publicacion);
            if (idAdministrador != null) {
                idAdministrador.getPublicacionList().add(publicacion);
                idAdministrador = em.merge(idAdministrador);
            }
            for (Publicacionfotos publicacionfotosListPublicacionfotos : publicacion.getPublicacionfotosList()) {
                Publicacion oldIdPublicacionOfPublicacionfotosListPublicacionfotos = publicacionfotosListPublicacionfotos.getIdPublicacion();
                publicacionfotosListPublicacionfotos.setIdPublicacion(publicacion);
                publicacionfotosListPublicacionfotos = em.merge(publicacionfotosListPublicacionfotos);
                if (oldIdPublicacionOfPublicacionfotosListPublicacionfotos != null) {
                    oldIdPublicacionOfPublicacionfotosListPublicacionfotos.getPublicacionfotosList().remove(publicacionfotosListPublicacionfotos);
                    oldIdPublicacionOfPublicacionfotosListPublicacionfotos = em.merge(oldIdPublicacionOfPublicacionfotosListPublicacionfotos);
                }
            }
            for (Comentarios comentariosListComentarios : publicacion.getComentariosList()) {
                Publicacion oldIdPublicacionOfComentariosListComentarios = comentariosListComentarios.getIdPublicacion();
                comentariosListComentarios.setIdPublicacion(publicacion);
                comentariosListComentarios = em.merge(comentariosListComentarios);
                if (oldIdPublicacionOfComentariosListComentarios != null) {
                    oldIdPublicacionOfComentariosListComentarios.getComentariosList().remove(comentariosListComentarios);
                    oldIdPublicacionOfComentariosListComentarios = em.merge(oldIdPublicacionOfComentariosListComentarios);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Publicacion publicacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Publicacion persistentPublicacion = em.find(Publicacion.class, publicacion.getIdPublicacion());
            Admisnistrador idAdministradorOld = persistentPublicacion.getIdAdministrador();
            Admisnistrador idAdministradorNew = publicacion.getIdAdministrador();
            List<Publicacionfotos> publicacionfotosListOld = persistentPublicacion.getPublicacionfotosList();
            List<Publicacionfotos> publicacionfotosListNew = publicacion.getPublicacionfotosList();
            List<Comentarios> comentariosListOld = persistentPublicacion.getComentariosList();
            List<Comentarios> comentariosListNew = publicacion.getComentariosList();
            List<String> illegalOrphanMessages = null;
            for (Publicacionfotos publicacionfotosListOldPublicacionfotos : publicacionfotosListOld) {
                if (!publicacionfotosListNew.contains(publicacionfotosListOldPublicacionfotos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Publicacionfotos " + publicacionfotosListOldPublicacionfotos + " since its idPublicacion field is not nullable.");
                }
            }
            for (Comentarios comentariosListOldComentarios : comentariosListOld) {
                if (!comentariosListNew.contains(comentariosListOldComentarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comentarios " + comentariosListOldComentarios + " since its idPublicacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idAdministradorNew != null) {
                idAdministradorNew = em.getReference(idAdministradorNew.getClass(), idAdministradorNew.getIdAdministrador());
                publicacion.setIdAdministrador(idAdministradorNew);
            }
            List<Publicacionfotos> attachedPublicacionfotosListNew = new ArrayList<Publicacionfotos>();
            for (Publicacionfotos publicacionfotosListNewPublicacionfotosToAttach : publicacionfotosListNew) {
                publicacionfotosListNewPublicacionfotosToAttach = em.getReference(publicacionfotosListNewPublicacionfotosToAttach.getClass(), publicacionfotosListNewPublicacionfotosToAttach.getIdPublicacionFotos());
                attachedPublicacionfotosListNew.add(publicacionfotosListNewPublicacionfotosToAttach);
            }
            publicacionfotosListNew = attachedPublicacionfotosListNew;
            publicacion.setPublicacionfotosList(publicacionfotosListNew);
            List<Comentarios> attachedComentariosListNew = new ArrayList<Comentarios>();
            for (Comentarios comentariosListNewComentariosToAttach : comentariosListNew) {
                comentariosListNewComentariosToAttach = em.getReference(comentariosListNewComentariosToAttach.getClass(), comentariosListNewComentariosToAttach.getIdComentarios());
                attachedComentariosListNew.add(comentariosListNewComentariosToAttach);
            }
            comentariosListNew = attachedComentariosListNew;
            publicacion.setComentariosList(comentariosListNew);
            publicacion = em.merge(publicacion);
            if (idAdministradorOld != null && !idAdministradorOld.equals(idAdministradorNew)) {
                idAdministradorOld.getPublicacionList().remove(publicacion);
                idAdministradorOld = em.merge(idAdministradorOld);
            }
            if (idAdministradorNew != null && !idAdministradorNew.equals(idAdministradorOld)) {
                idAdministradorNew.getPublicacionList().add(publicacion);
                idAdministradorNew = em.merge(idAdministradorNew);
            }
            for (Publicacionfotos publicacionfotosListNewPublicacionfotos : publicacionfotosListNew) {
                if (!publicacionfotosListOld.contains(publicacionfotosListNewPublicacionfotos)) {
                    Publicacion oldIdPublicacionOfPublicacionfotosListNewPublicacionfotos = publicacionfotosListNewPublicacionfotos.getIdPublicacion();
                    publicacionfotosListNewPublicacionfotos.setIdPublicacion(publicacion);
                    publicacionfotosListNewPublicacionfotos = em.merge(publicacionfotosListNewPublicacionfotos);
                    if (oldIdPublicacionOfPublicacionfotosListNewPublicacionfotos != null && !oldIdPublicacionOfPublicacionfotosListNewPublicacionfotos.equals(publicacion)) {
                        oldIdPublicacionOfPublicacionfotosListNewPublicacionfotos.getPublicacionfotosList().remove(publicacionfotosListNewPublicacionfotos);
                        oldIdPublicacionOfPublicacionfotosListNewPublicacionfotos = em.merge(oldIdPublicacionOfPublicacionfotosListNewPublicacionfotos);
                    }
                }
            }
            for (Comentarios comentariosListNewComentarios : comentariosListNew) {
                if (!comentariosListOld.contains(comentariosListNewComentarios)) {
                    Publicacion oldIdPublicacionOfComentariosListNewComentarios = comentariosListNewComentarios.getIdPublicacion();
                    comentariosListNewComentarios.setIdPublicacion(publicacion);
                    comentariosListNewComentarios = em.merge(comentariosListNewComentarios);
                    if (oldIdPublicacionOfComentariosListNewComentarios != null && !oldIdPublicacionOfComentariosListNewComentarios.equals(publicacion)) {
                        oldIdPublicacionOfComentariosListNewComentarios.getComentariosList().remove(comentariosListNewComentarios);
                        oldIdPublicacionOfComentariosListNewComentarios = em.merge(oldIdPublicacionOfComentariosListNewComentarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = publicacion.getIdPublicacion();
                if (findPublicacion(id) == null) {
                    throw new NonexistentEntityException("The publicacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Publicacion publicacion;
            try {
                publicacion = em.getReference(Publicacion.class, id);
                publicacion.getIdPublicacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The publicacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Publicacionfotos> publicacionfotosListOrphanCheck = publicacion.getPublicacionfotosList();
            for (Publicacionfotos publicacionfotosListOrphanCheckPublicacionfotos : publicacionfotosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Publicacion (" + publicacion + ") cannot be destroyed since the Publicacionfotos " + publicacionfotosListOrphanCheckPublicacionfotos + " in its publicacionfotosList field has a non-nullable idPublicacion field.");
            }
            List<Comentarios> comentariosListOrphanCheck = publicacion.getComentariosList();
            for (Comentarios comentariosListOrphanCheckComentarios : comentariosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Publicacion (" + publicacion + ") cannot be destroyed since the Comentarios " + comentariosListOrphanCheckComentarios + " in its comentariosList field has a non-nullable idPublicacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Admisnistrador idAdministrador = publicacion.getIdAdministrador();
            if (idAdministrador != null) {
                idAdministrador.getPublicacionList().remove(publicacion);
                idAdministrador = em.merge(idAdministrador);
            }
            em.remove(publicacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Publicacion> findPublicacionEntities() {
        return findPublicacionEntities(true, -1, -1);
    }

    public List<Publicacion> findPublicacionEntities(int maxResults, int firstResult) {
        return findPublicacionEntities(false, maxResults, firstResult);
    }

    private List<Publicacion> findPublicacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Publicacion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Publicacion findPublicacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Publicacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPublicacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Publicacion> rt = cq.from(Publicacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}