package fr.bart.gamm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class GenericDao<T> {

	protected Class<T> entityClass;
	
    private static EntityManager em;
	
	public GenericDao(Class<T> entityClass) {
        this.entityClass = entityClass;
        if(em == null) {
        	em = Persistence.createEntityManagerFactory("GammPU").createEntityManager();        	
        }
    }
	
    public T create(T t) {
    	em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
        return t;
    }

    public T read(int id) {
    	em.getTransaction().begin();
        T result = this.em.find(entityClass, id);
        em.getTransaction().commit();
        return result;
    }

    public T update(T t) {
    	em.getTransaction().begin();
        T result = this.em.merge(t);
        em.getTransaction().commit();
        return result;
    }

     public void delete(T t) {
    	em.getTransaction().begin();
        t = this.em.merge(t);
        this.em.remove(t);
        em.getTransaction().commit();
    }
     
     private List<T> findAll(Class<T> entityClass) {    	 
         return findAll(entityClass, "id", false);
     }
     
     public List<T> findAll() {
    	 em.getTransaction().begin();
    	 List<T> result = findAll(entityClass);
    	 em.getTransaction().commit();
    	 return result;
     }
     
     private List<T> findAll(Class<T> entityClass, String orderBy, boolean asc) {
         CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
         Root<T> c = cq.from(entityClass);
         cq.select(c);
         if (asc) {
             cq.orderBy(em.getCriteriaBuilder().asc(c.get(orderBy)));
         } else {
             cq.orderBy(em.getCriteriaBuilder().desc(c.get(orderBy)));
         }
         List<T> result = em.createQuery(cq).getResultList();
         return result;
     }
	
}
