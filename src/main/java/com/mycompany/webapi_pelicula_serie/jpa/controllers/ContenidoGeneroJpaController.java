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
import com.mycompany.webapi_pelicula_serie.jpa.entities.ContenidoGenero;
import com.mycompany.webapi_pelicula_serie.jpa.entities.Genero;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ANDRÉS FELIPE MERA TRÓCHEZ
 */
public class ContenidoGeneroJpaController implements Serializable {

    public ContenidoGeneroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContenidoGenero contenidoGenero) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contenido fkContenido = contenidoGenero.getFkContenido();
            if (fkContenido != null) {
                fkContenido = em.getReference(fkContenido.getClass(), fkContenido.getConId());
                contenidoGenero.setFkContenido(fkContenido);
            }
            Genero fkGenero = contenidoGenero.getFkGenero();
            if (fkGenero != null) {
                fkGenero = em.getReference(fkGenero.getClass(), fkGenero.getGenId());
                contenidoGenero.setFkGenero(fkGenero);
            }
            em.persist(contenidoGenero);
            if (fkContenido != null) {
                fkContenido.getContenidoGeneroList().add(contenidoGenero);
                fkContenido = em.merge(fkContenido);
            }
            if (fkGenero != null) {
                fkGenero.getContenidoGeneroList().add(contenidoGenero);
                fkGenero = em.merge(fkGenero);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContenidoGenero contenidoGenero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContenidoGenero persistentContenidoGenero = em.find(ContenidoGenero.class, contenidoGenero.getCgId());
            Contenido fkContenidoOld = persistentContenidoGenero.getFkContenido();
            Contenido fkContenidoNew = contenidoGenero.getFkContenido();
            Genero fkGeneroOld = persistentContenidoGenero.getFkGenero();
            Genero fkGeneroNew = contenidoGenero.getFkGenero();
            if (fkContenidoNew != null) {
                fkContenidoNew = em.getReference(fkContenidoNew.getClass(), fkContenidoNew.getConId());
                contenidoGenero.setFkContenido(fkContenidoNew);
            }
            if (fkGeneroNew != null) {
                fkGeneroNew = em.getReference(fkGeneroNew.getClass(), fkGeneroNew.getGenId());
                contenidoGenero.setFkGenero(fkGeneroNew);
            }
            contenidoGenero = em.merge(contenidoGenero);
            if (fkContenidoOld != null && !fkContenidoOld.equals(fkContenidoNew)) {
                fkContenidoOld.getContenidoGeneroList().remove(contenidoGenero);
                fkContenidoOld = em.merge(fkContenidoOld);
            }
            if (fkContenidoNew != null && !fkContenidoNew.equals(fkContenidoOld)) {
                fkContenidoNew.getContenidoGeneroList().add(contenidoGenero);
                fkContenidoNew = em.merge(fkContenidoNew);
            }
            if (fkGeneroOld != null && !fkGeneroOld.equals(fkGeneroNew)) {
                fkGeneroOld.getContenidoGeneroList().remove(contenidoGenero);
                fkGeneroOld = em.merge(fkGeneroOld);
            }
            if (fkGeneroNew != null && !fkGeneroNew.equals(fkGeneroOld)) {
                fkGeneroNew.getContenidoGeneroList().add(contenidoGenero);
                fkGeneroNew = em.merge(fkGeneroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contenidoGenero.getCgId();
                if (findContenidoGenero(id) == null) {
                    throw new NonexistentEntityException("The contenidoGenero with id " + id + " no longer exists.");
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
            ContenidoGenero contenidoGenero;
            try {
                contenidoGenero = em.getReference(ContenidoGenero.class, id);
                contenidoGenero.getCgId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contenidoGenero with id " + id + " no longer exists.", enfe);
            }
            Contenido fkContenido = contenidoGenero.getFkContenido();
            if (fkContenido != null) {
                fkContenido.getContenidoGeneroList().remove(contenidoGenero);
                fkContenido = em.merge(fkContenido);
            }
            Genero fkGenero = contenidoGenero.getFkGenero();
            if (fkGenero != null) {
                fkGenero.getContenidoGeneroList().remove(contenidoGenero);
                fkGenero = em.merge(fkGenero);
            }
            em.remove(contenidoGenero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContenidoGenero> findContenidoGeneroEntities() {
        return findContenidoGeneroEntities(true, -1, -1);
    }

    public List<ContenidoGenero> findContenidoGeneroEntities(int maxResults, int firstResult) {
        return findContenidoGeneroEntities(false, maxResults, firstResult);
    }

    private List<ContenidoGenero> findContenidoGeneroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContenidoGenero.class));
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

    public ContenidoGenero findContenidoGenero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContenidoGenero.class, id);
        } finally {
            em.close();
        }
    }

    public int getContenidoGeneroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContenidoGenero> rt = cq.from(ContenidoGenero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
