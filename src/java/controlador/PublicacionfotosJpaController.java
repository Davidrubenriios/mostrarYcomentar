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
import modelo.Fotos;
import modelo.Publicacion;
import modelo.Publicacionfotos;

/**
 *
 * @author DAVID
 */
public class PublicacionfotosJpaController implements Serializable {
    
        public PublicacionfotosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("mostrarYcomentarPU");
    }


    public PublicacionfotosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Publicacionfotos publicacionfotos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fotos idFotos = publicacionfotos.getIdFotos();
            if (idFotos != null) {
                idFotos = em.getReference(idFotos.getClass(), idFotos.getIdFotos());
                publicacionfotos.setIdFotos(idFotos);
            }
            Publicacion idPublicacion = publicacionfotos.getIdPublicacion();
            if (idPublicacion != null) {
                idPublicacion = em.getReference(idPublicacion.getClass(), idPublicacion.getIdPublicacion());
                publicacionfotos.setIdPublicacion(idPublicacion);
            }
            em.persist(publicacionfotos);
            if (idFotos != null) {
                idFotos.getPublicacionfotosList().add(publicacionfotos);
                idFotos = em.merge(idFotos);
            }
            if (idPublicacion != null) {
                idPublicacion.getPublicacionfotosList().add(publicacionfotos);
                idPublicacion = em.merge(idPublicacion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Publicacionfotos publicacionfotos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Publicacionfotos persistentPublicacionfotos = em.find(Publicacionfotos.class, publicacionfotos.getIdPublicacionFotos());
            Fotos idFotosOld = persistentPublicacionfotos.getIdFotos();
            Fotos idFotosNew = publicacionfotos.getIdFotos();
            Publicacion idPublicacionOld = persistentPublicacionfotos.getIdPublicacion();
            Publicacion idPublicacionNew = publicacionfotos.getIdPublicacion();
            if (idFotosNew != null) {
                idFotosNew = em.getReference(idFotosNew.getClass(), idFotosNew.getIdFotos());
                publicacionfotos.setIdFotos(idFotosNew);
            }
            if (idPublicacionNew != null) {
                idPublicacionNew = em.getReference(idPublicacionNew.getClass(), idPublicacionNew.getIdPublicacion());
                publicacionfotos.setIdPublicacion(idPublicacionNew);
            }
            publicacionfotos = em.merge(publicacionfotos);
            if (idFotosOld != null && !idFotosOld.equals(idFotosNew)) {
                idFotosOld.getPublicacionfotosList().remove(publicacionfotos);
                idFotosOld = em.merge(idFotosOld);
            }
            if (idFotosNew != null && !idFotosNew.equals(idFotosOld)) {
                idFotosNew.getPublicacionfotosList().add(publicacionfotos);
                idFotosNew = em.merge(idFotosNew);
            }
            if (idPublicacionOld != null && !idPublicacionOld.equals(idPublicacionNew)) {
                idPublicacionOld.getPublicacionfotosList().remove(publicacionfotos);
                idPublicacionOld = em.merge(idPublicacionOld);
            }
            if (idPublicacionNew != null && !idPublicacionNew.equals(idPublicacionOld)) {
                idPublicacionNew.getPublicacionfotosList().add(publicacionfotos);
                idPublicacionNew = em.merge(idPublicacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = publicacionfotos.getIdPublicacionFotos();
                if (findPublicacionfotos(id) == null) {
                    throw new NonexistentEntityException("The publicacionfotos with id " + id + " no longer exists.");
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
            Publicacionfotos publicacionfotos;
            try {
                publicacionfotos = em.getReference(Publicacionfotos.class, id);
                publicacionfotos.getIdPublicacionFotos();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The publicacionfotos with id " + id + " no longer exists.", enfe);
            }
            Fotos idFotos = publicacionfotos.getIdFotos();
            if (idFotos != null) {
                idFotos.getPublicacionfotosList().remove(publicacionfotos);
                idFotos = em.merge(idFotos);
            }
            Publicacion idPublicacion = publicacionfotos.getIdPublicacion();
            if (idPublicacion != null) {
                idPublicacion.getPublicacionfotosList().remove(publicacionfotos);
                idPublicacion = em.merge(idPublicacion);
            }
            em.remove(publicacionfotos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Publicacionfotos> findPublicacionfotosEntities() {
        return findPublicacionfotosEntities(true, -1, -1);
    }

    public List<Publicacionfotos> findPublicacionfotosEntities(int maxResults, int firstResult) {
        return findPublicacionfotosEntities(false, maxResults, firstResult);
    }

    private List<Publicacionfotos> findPublicacionfotosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Publicacionfotos.class));
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

    public Publicacionfotos findPublicacionfotos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Publicacionfotos.class, id);
        } finally {
            em.close();
        }
    }

    public int getPublicacionfotosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Publicacionfotos> rt = cq.from(Publicacionfotos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
