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
import modelo.Respuesta;

/**
 *
 * @author DAVID
 */
public class RespuestaJpaController implements Serializable {

    public RespuestaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("mostrarYcomentarPU");
    }

    public RespuestaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Respuesta respuesta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(respuesta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Respuesta respuesta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            respuesta = em.merge(respuesta);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = respuesta.getIdRespuesta();
                if (findRespuesta(id) == null) {
                    throw new NonexistentEntityException("The respuesta with id " + id + " no longer exists.");
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
            Respuesta respuesta;
            try {
                respuesta = em.getReference(Respuesta.class, id);
                respuesta.getIdRespuesta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The respuesta with id " + id + " no longer exists.", enfe);
            }
            em.remove(respuesta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Respuesta> findRespuestaEntities() {
        return findRespuestaEntities(true, -1, -1);
    }

    public List<Respuesta> findRespuestaEntities(int maxResults, int firstResult) {
        return findRespuestaEntities(false, maxResults, firstResult);
    }

    private List<Respuesta> findRespuestaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Respuesta.class));
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

    public Respuesta findRespuesta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Respuesta.class, id);
        } finally {
            em.close();
        }
    }

    public int getRespuestaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Respuesta> rt = cq.from(Respuesta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
