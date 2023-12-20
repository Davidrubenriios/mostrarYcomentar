
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Publicacionfotos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Fotos;

/**
 *
 * @author DAVID
 */
public class FotosJpaController implements Serializable {

    public FotosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("mostrarYcomentarPU");
    }

    public FotosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fotos fotos) {
        if (fotos.getPublicacionfotosList() == null) {
            fotos.setPublicacionfotosList(new ArrayList<Publicacionfotos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Publicacionfotos> attachedPublicacionfotosList = new ArrayList<Publicacionfotos>();
            for (Publicacionfotos publicacionfotosListPublicacionfotosToAttach : fotos.getPublicacionfotosList()) {
                publicacionfotosListPublicacionfotosToAttach = em.getReference(publicacionfotosListPublicacionfotosToAttach.getClass(), publicacionfotosListPublicacionfotosToAttach.getIdPublicacionFotos());
                attachedPublicacionfotosList.add(publicacionfotosListPublicacionfotosToAttach);
            }
            fotos.setPublicacionfotosList(attachedPublicacionfotosList);
            em.persist(fotos);
            for (Publicacionfotos publicacionfotosListPublicacionfotos : fotos.getPublicacionfotosList()) {
                Fotos oldIdFotosOfPublicacionfotosListPublicacionfotos = publicacionfotosListPublicacionfotos.getIdFotos();
                publicacionfotosListPublicacionfotos.setIdFotos(fotos);
                publicacionfotosListPublicacionfotos = em.merge(publicacionfotosListPublicacionfotos);
                if (oldIdFotosOfPublicacionfotosListPublicacionfotos != null) {
                    oldIdFotosOfPublicacionfotosListPublicacionfotos.getPublicacionfotosList().remove(publicacionfotosListPublicacionfotos);
                    oldIdFotosOfPublicacionfotosListPublicacionfotos = em.merge(oldIdFotosOfPublicacionfotosListPublicacionfotos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fotos fotos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fotos persistentFotos = em.find(Fotos.class, fotos.getIdFotos());
            List<Publicacionfotos> publicacionfotosListOld = persistentFotos.getPublicacionfotosList();
            List<Publicacionfotos> publicacionfotosListNew = fotos.getPublicacionfotosList();
            List<String> illegalOrphanMessages = null;
            for (Publicacionfotos publicacionfotosListOldPublicacionfotos : publicacionfotosListOld) {
                if (!publicacionfotosListNew.contains(publicacionfotosListOldPublicacionfotos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Publicacionfotos " + publicacionfotosListOldPublicacionfotos + " since its idFotos field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Publicacionfotos> attachedPublicacionfotosListNew = new ArrayList<Publicacionfotos>();
            for (Publicacionfotos publicacionfotosListNewPublicacionfotosToAttach : publicacionfotosListNew) {
                publicacionfotosListNewPublicacionfotosToAttach = em.getReference(publicacionfotosListNewPublicacionfotosToAttach.getClass(), publicacionfotosListNewPublicacionfotosToAttach.getIdPublicacionFotos());
                attachedPublicacionfotosListNew.add(publicacionfotosListNewPublicacionfotosToAttach);
            }
            publicacionfotosListNew = attachedPublicacionfotosListNew;
            fotos.setPublicacionfotosList(publicacionfotosListNew);
            fotos = em.merge(fotos);
            for (Publicacionfotos publicacionfotosListNewPublicacionfotos : publicacionfotosListNew) {
                if (!publicacionfotosListOld.contains(publicacionfotosListNewPublicacionfotos)) {
                    Fotos oldIdFotosOfPublicacionfotosListNewPublicacionfotos = publicacionfotosListNewPublicacionfotos.getIdFotos();
                    publicacionfotosListNewPublicacionfotos.setIdFotos(fotos);
                    publicacionfotosListNewPublicacionfotos = em.merge(publicacionfotosListNewPublicacionfotos);
                    if (oldIdFotosOfPublicacionfotosListNewPublicacionfotos != null && !oldIdFotosOfPublicacionfotosListNewPublicacionfotos.equals(fotos)) {
                        oldIdFotosOfPublicacionfotosListNewPublicacionfotos.getPublicacionfotosList().remove(publicacionfotosListNewPublicacionfotos);
                        oldIdFotosOfPublicacionfotosListNewPublicacionfotos = em.merge(oldIdFotosOfPublicacionfotosListNewPublicacionfotos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fotos.getIdFotos();
                if (findFotos(id) == null) {
                    throw new NonexistentEntityException("The fotos with id " + id + " no longer exists.");
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
            Fotos fotos;
            try {
                fotos = em.getReference(Fotos.class, id);
                fotos.getIdFotos();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fotos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Publicacionfotos> publicacionfotosListOrphanCheck = fotos.getPublicacionfotosList();
            for (Publicacionfotos publicacionfotosListOrphanCheckPublicacionfotos : publicacionfotosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Fotos (" + fotos + ") cannot be destroyed since the Publicacionfotos " + publicacionfotosListOrphanCheckPublicacionfotos + " in its publicacionfotosList field has a non-nullable idFotos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(fotos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fotos> findFotosEntities() {
        return findFotosEntities(true, -1, -1);
    }

    public List<Fotos> findFotosEntities(int maxResults, int firstResult) {
        return findFotosEntities(false, maxResults, firstResult);
    }

    private List<Fotos> findFotosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fotos.class));
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

    public Fotos findFotos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fotos.class, id);
        } finally {
            em.close();
        }
    }

    public int getFotosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fotos> rt = cq.from(Fotos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
      public List<Fotos> listarFotos() {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("Fotos.findAll", Fotos.class).getResultList();
        } finally {
            em.close();
        }
    }
   public Fotos obtenerFotoPorId(int idFoto) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fotos.class, idFoto);
        } finally {
            em.close();
        }
    }
}
