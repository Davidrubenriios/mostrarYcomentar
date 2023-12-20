
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Publicacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import modelo.Admisnistrador;

/**
 *
 * @author DAVID
 */
public class AdmisnistradorJpaController implements Serializable {

    public AdmisnistradorJpaController() {
        this.emf = Persistence.createEntityManagerFactory("mostrarYcomentarPU");
    }

    public AdmisnistradorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Admisnistrador admisnistrador) {
        if (admisnistrador.getPublicacionList() == null) {
            admisnistrador.setPublicacionList(new ArrayList<Publicacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Publicacion> attachedPublicacionList = new ArrayList<Publicacion>();
            for (Publicacion publicacionListPublicacionToAttach : admisnistrador.getPublicacionList()) {
                publicacionListPublicacionToAttach = em.getReference(publicacionListPublicacionToAttach.getClass(), publicacionListPublicacionToAttach.getIdPublicacion());
                attachedPublicacionList.add(publicacionListPublicacionToAttach);
            }
            admisnistrador.setPublicacionList(attachedPublicacionList);
            em.persist(admisnistrador);
            for (Publicacion publicacionListPublicacion : admisnistrador.getPublicacionList()) {
                Admisnistrador oldIdAdministradorOfPublicacionListPublicacion = publicacionListPublicacion.getIdAdministrador();
                publicacionListPublicacion.setIdAdministrador(admisnistrador);
                publicacionListPublicacion = em.merge(publicacionListPublicacion);
                if (oldIdAdministradorOfPublicacionListPublicacion != null) {
                    oldIdAdministradorOfPublicacionListPublicacion.getPublicacionList().remove(publicacionListPublicacion);
                    oldIdAdministradorOfPublicacionListPublicacion = em.merge(oldIdAdministradorOfPublicacionListPublicacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Admisnistrador admisnistrador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Admisnistrador persistentAdmisnistrador = em.find(Admisnistrador.class, admisnistrador.getIdAdministrador());
            List<Publicacion> publicacionListOld = persistentAdmisnistrador.getPublicacionList();
            List<Publicacion> publicacionListNew = admisnistrador.getPublicacionList();
            List<String> illegalOrphanMessages = null;
            for (Publicacion publicacionListOldPublicacion : publicacionListOld) {
                if (!publicacionListNew.contains(publicacionListOldPublicacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Publicacion " + publicacionListOldPublicacion + " since its idAdministrador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Publicacion> attachedPublicacionListNew = new ArrayList<Publicacion>();
            for (Publicacion publicacionListNewPublicacionToAttach : publicacionListNew) {
                publicacionListNewPublicacionToAttach = em.getReference(publicacionListNewPublicacionToAttach.getClass(), publicacionListNewPublicacionToAttach.getIdPublicacion());
                attachedPublicacionListNew.add(publicacionListNewPublicacionToAttach);
            }
            publicacionListNew = attachedPublicacionListNew;
            admisnistrador.setPublicacionList(publicacionListNew);
            admisnistrador = em.merge(admisnistrador);
            for (Publicacion publicacionListNewPublicacion : publicacionListNew) {
                if (!publicacionListOld.contains(publicacionListNewPublicacion)) {
                    Admisnistrador oldIdAdministradorOfPublicacionListNewPublicacion = publicacionListNewPublicacion.getIdAdministrador();
                    publicacionListNewPublicacion.setIdAdministrador(admisnistrador);
                    publicacionListNewPublicacion = em.merge(publicacionListNewPublicacion);
                    if (oldIdAdministradorOfPublicacionListNewPublicacion != null && !oldIdAdministradorOfPublicacionListNewPublicacion.equals(admisnistrador)) {
                        oldIdAdministradorOfPublicacionListNewPublicacion.getPublicacionList().remove(publicacionListNewPublicacion);
                        oldIdAdministradorOfPublicacionListNewPublicacion = em.merge(oldIdAdministradorOfPublicacionListNewPublicacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = admisnistrador.getIdAdministrador();
                if (findAdmisnistrador(id) == null) {
                    throw new NonexistentEntityException("The admisnistrador with id " + id + " no longer exists.");
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
            Admisnistrador admisnistrador;
            try {
                admisnistrador = em.getReference(Admisnistrador.class, id);
                admisnistrador.getIdAdministrador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The admisnistrador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Publicacion> publicacionListOrphanCheck = admisnistrador.getPublicacionList();
            for (Publicacion publicacionListOrphanCheckPublicacion : publicacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Admisnistrador (" + admisnistrador + ") cannot be destroyed since the Publicacion " + publicacionListOrphanCheckPublicacion + " in its publicacionList field has a non-nullable idAdministrador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(admisnistrador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Admisnistrador> findAdmisnistradorEntities() {
        return findAdmisnistradorEntities(true, -1, -1);
    }

    public List<Admisnistrador> findAdmisnistradorEntities(int maxResults, int firstResult) {
        return findAdmisnistradorEntities(false, maxResults, firstResult);
    }

    private List<Admisnistrador> findAdmisnistradorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Admisnistrador.class));
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

    public Admisnistrador findAdmisnistrador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Admisnistrador.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdmisnistradorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Admisnistrador> rt = cq.from(Admisnistrador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Admisnistrador findByEmail(String email) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Admisnistrador> q = em.createNamedQuery("Admisnistrador.findByEmail", Admisnistrador.class);
            q.setParameter("email", email);
            List<Admisnistrador> resultList = q.getResultList();
            return resultList.isEmpty() ? null : resultList.get(0);
        } finally {
            em.close();
        }
    }
}