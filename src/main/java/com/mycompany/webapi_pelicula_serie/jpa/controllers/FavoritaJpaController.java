/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webapi_pelicula_serie.jpa.controllers;

import com.mycompany.webapi_pelicula_serie.jpa.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.webapi_pelicula_serie.jpa.entities.Contenido;
import com.mycompany.webapi_pelicula_serie.jpa.entities.Favorita;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ANDRÉS FELIPE MERA TRÓCHEZ
 */
public class FavoritaJpaController implements Serializable {

    public FavoritaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Favorita favorita) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contenido fkContenido = favorita.getFkContenido();
            if (fkContenido != null) {
                fkContenido = em.getReference(fkContenido.getClass(), fkContenido.getConId());
                favorita.setFkContenido(fkContenido);
            }
            em.persist(favorita);
            if (fkContenido != null) {
                Favorita oldFavoritaOfFkContenido = fkContenido.getFavorita();
                if (oldFavoritaOfFkContenido != null) {
                    oldFavoritaOfFkContenido.setFkContenido(null);
                    oldFavoritaOfFkContenido = em.merge(oldFavoritaOfFkContenido);
                }
                fkContenido.setFavorita(favorita);
                fkContenido = em.merge(fkContenido);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Favorita favorita) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Favorita persistentFavorita = em.find(Favorita.class, favorita.getFavId());
            Contenido fkContenidoOld = persistentFavorita.getFkContenido();
            Contenido fkContenidoNew = favorita.getFkContenido();
            if (fkContenidoNew != null) {
                fkContenidoNew = em.getReference(fkContenidoNew.getClass(), fkContenidoNew.getConId());
                favorita.setFkContenido(fkContenidoNew);
            }
            favorita = em.merge(favorita);
            if (fkContenidoOld != null && !fkContenidoOld.equals(fkContenidoNew)) {
                fkContenidoOld.setFavorita(null);
                fkContenidoOld = em.merge(fkContenidoOld);
            }
            if (fkContenidoNew != null && !fkContenidoNew.equals(fkContenidoOld)) {
                Favorita oldFavoritaOfFkContenido = fkContenidoNew.getFavorita();
                if (oldFavoritaOfFkContenido != null) {
                    oldFavoritaOfFkContenido.setFkContenido(null);
                    oldFavoritaOfFkContenido = em.merge(oldFavoritaOfFkContenido);
                }
                fkContenidoNew.setFavorita(favorita);
                fkContenidoNew = em.merge(fkContenidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = favorita.getFavId();
                if (findFavorita(id) == null) {
                    throw new NonexistentEntityException("The favorita with id " + id + " no longer exists.");
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
            Favorita favorita;
            try {
                favorita = em.getReference(Favorita.class, id);
                favorita.getFavId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The favorita with id " + id + " no longer exists.", enfe);
            }
            Contenido fkContenido = favorita.getFkContenido();
            if (fkContenido != null) {
                fkContenido.setFavorita(null);
                fkContenido = em.merge(fkContenido);
            }
            em.remove(favorita);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Favorita> findFavoritaEntities() {
        return findFavoritaEntities(true, -1, -1);
    }

    public List<Favorita> findFavoritaEntities(int maxResults, int firstResult) {
        return findFavoritaEntities(false, maxResults, firstResult);
    }

    private List<Favorita> findFavoritaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Favorita.class));
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

    public Favorita findFavorita(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Favorita.class, id);
        } finally {
            em.close();
        }
    }

    public int getFavoritaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Favorita> rt = cq.from(Favorita.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
