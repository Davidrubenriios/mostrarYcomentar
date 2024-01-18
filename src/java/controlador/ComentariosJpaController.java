/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Comentarios;
import modelo.Publicacion;

/**
 *
 * @author DAVID
 */
public class ComentariosJpaController implements Serializable {

    public ComentariosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("mostrarYcomentarPU");
    }

    public ComentariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comentarios comentarios) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Publicacion idPublicacion = comentarios.getIdPublicacion();
            if (idPublicacion != null) {
                idPublicacion = em.getReference(idPublicacion.getClass(), idPublicacion.getIdPublicacion());
                comentarios.setIdPublicacion(idPublicacion);
            }
            em.persist(comentarios);
            if (idPublicacion != null) {
                idPublicacion.getComentariosList().add(comentarios);
                idPublicacion = em.merge(idPublicacion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comentarios comentarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comentarios persistentComentarios = em.find(Comentarios.class, comentarios.getIdComentarios());
            Publicacion idPublicacionOld = persistentComentarios.getIdPublicacion();
            Publicacion idPublicacionNew = comentarios.getIdPublicacion();
            if (idPublicacionNew != null) {
                idPublicacionNew = em.getReference(idPublicacionNew.getClass(), idPublicacionNew.getIdPublicacion());
                comentarios.setIdPublicacion(idPublicacionNew);
            }
            comentarios = em.merge(comentarios);
            if (idPublicacionOld != null && !idPublicacionOld.equals(idPublicacionNew)) {
                idPublicacionOld.getComentariosList().remove(comentarios);
                idPublicacionOld = em.merge(idPublicacionOld);
            }
            if (idPublicacionNew != null && !idPublicacionNew.equals(idPublicacionOld)) {
                idPublicacionNew.getComentariosList().add(comentarios);
                idPublicacionNew = em.merge(idPublicacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comentarios.getIdComentarios();
                if (findComentarios(id) == null) {
                    throw new NonexistentEntityException("The comentarios with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comentarios comentarios;
            try {
                comentarios = em.getReference(Comentarios.class, id);
                comentarios.getIdComentarios();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comentarios with id " + id + " no longer exists.", enfe);
            }
            Publicacion idPublicacion = comentarios.getIdPublicacion();
            if (idPublicacion != null) {
                idPublicacion.getComentariosList().remove(comentarios);
                idPublicacion = em.merge(idPublicacion);
            }
            em.remove(comentarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comentarios> findComentariosEntities() {
        return findComentariosEntities(true, -1, -1);
    }

    public List<Comentarios> findComentariosEntities(int maxResults, int firstResult) {
        return findComentariosEntities(false, maxResults, firstResult);
    }

    private List<Comentarios> findComentariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comentarios.class));
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

    public Comentarios findComentarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comentarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getComentariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comentarios> rt = cq.from(Comentarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}