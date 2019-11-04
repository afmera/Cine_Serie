/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webapi_pelicula_serie.jpa.controllers;

import com.mycompany.webapi_pelicula_serie.jpa.controllers.exceptions.IllegalOrphanException;
import com.mycompany.webapi_pelicula_serie.jpa.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.webapi_pelicula_serie.jpa.entities.ContenidoGenero;
import com.mycompany.webapi_pelicula_serie.jpa.entities.Genero;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ANDRÉS FELIPE MERA TRÓCHEZ
 */
public class GeneroJpaController implements Serializable {

    public GeneroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Genero genero) {
        if (genero.getContenidoGeneroList() == null) {
            genero.setContenidoGeneroList(new ArrayList<ContenidoGenero>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ContenidoGenero> attachedContenidoGeneroList = new ArrayList<ContenidoGenero>();
            for (ContenidoGenero contenidoGeneroListContenidoGeneroToAttach : genero.getContenidoGeneroList()) {
                contenidoGeneroListContenidoGeneroToAttach = em.getReference(contenidoGeneroListContenidoGeneroToAttach.getClass(), contenidoGeneroListContenidoGeneroToAttach.getCgId());
                attachedContenidoGeneroList.add(contenidoGeneroListContenidoGeneroToAttach);
            }
            genero.setContenidoGeneroList(attachedContenidoGeneroList);
            em.persist(genero);
            for (ContenidoGenero contenidoGeneroListContenidoGenero : genero.getContenidoGeneroList()) {
                Genero oldFkGeneroOfContenidoGeneroListContenidoGenero = contenidoGeneroListContenidoGenero.getFkGenero();
                contenidoGeneroListContenidoGenero.setFkGenero(genero);
                contenidoGeneroListContenidoGenero = em.merge(contenidoGeneroListContenidoGenero);
                if (oldFkGeneroOfContenidoGeneroListContenidoGenero != null) {
                    oldFkGeneroOfContenidoGeneroListContenidoGenero.getContenidoGeneroList().remove(contenidoGeneroListContenidoGenero);
                    oldFkGeneroOfContenidoGeneroListContenidoGenero = em.merge(oldFkGeneroOfContenidoGeneroListContenidoGenero);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Genero genero) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Genero persistentGenero = em.find(Genero.class, genero.getGenId());
            List<ContenidoGenero> contenidoGeneroListOld = persistentGenero.getContenidoGeneroList();
            List<ContenidoGenero> contenidoGeneroListNew = genero.getContenidoGeneroList();
            List<String> illegalOrphanMessages = null;
            for (ContenidoGenero contenidoGeneroListOldContenidoGenero : contenidoGeneroListOld) {
                if (!contenidoGeneroListNew.contains(contenidoGeneroListOldContenidoGenero)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ContenidoGenero " + contenidoGeneroListOldContenidoGenero + " since its fkGenero field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ContenidoGenero> attachedContenidoGeneroListNew = new ArrayList<ContenidoGenero>();
            for (ContenidoGenero contenidoGeneroListNewContenidoGeneroToAttach : contenidoGeneroListNew) {
                contenidoGeneroListNewContenidoGeneroToAttach = em.getReference(contenidoGeneroListNewContenidoGeneroToAttach.getClass(), contenidoGeneroListNewContenidoGeneroToAttach.getCgId());
                attachedContenidoGeneroListNew.add(contenidoGeneroListNewContenidoGeneroToAttach);
            }
            contenidoGeneroListNew = attachedContenidoGeneroListNew;
            genero.setContenidoGeneroList(contenidoGeneroListNew);
            genero = em.merge(genero);
            for (ContenidoGenero contenidoGeneroListNewContenidoGenero : contenidoGeneroListNew) {
                if (!contenidoGeneroListOld.contains(contenidoGeneroListNewContenidoGenero)) {
                    Genero oldFkGeneroOfContenidoGeneroListNewContenidoGenero = contenidoGeneroListNewContenidoGenero.getFkGenero();
                    contenidoGeneroListNewContenidoGenero.setFkGenero(genero);
                    contenidoGeneroListNewContenidoGenero = em.merge(contenidoGeneroListNewContenidoGenero);
                    if (oldFkGeneroOfContenidoGeneroListNewContenidoGenero != null && !oldFkGeneroOfContenidoGeneroListNewContenidoGenero.equals(genero)) {
                        oldFkGeneroOfContenidoGeneroListNewContenidoGenero.getContenidoGeneroList().remove(contenidoGeneroListNewContenidoGenero);
                        oldFkGeneroOfContenidoGeneroListNewContenidoGenero = em.merge(oldFkGeneroOfContenidoGeneroListNewContenidoGenero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = genero.getGenId();
                if (findGenero(id) == null) {
                    throw new NonexistentEntityException("The genero with id " + id + " no longer exists.");
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
            Genero genero;
            try {
                genero = em.getReference(Genero.class, id);
                genero.getGenId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The genero with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ContenidoGenero> contenidoGeneroListOrphanCheck = genero.getContenidoGeneroList();
            for (ContenidoGenero contenidoGeneroListOrphanCheckContenidoGenero : contenidoGeneroListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Genero (" + genero + ") cannot be destroyed since the ContenidoGenero " + contenidoGeneroListOrphanCheckContenidoGenero + " in its contenidoGeneroList field has a non-nullable fkGenero field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(genero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Genero> findGeneroEntities() {
        return findGeneroEntities(true, -1, -1);
    }

    public List<Genero> findGeneroEntities(int maxResults, int firstResult) {
        return findGeneroEntities(false, maxResults, firstResult);
    }

    private List<Genero> findGeneroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Genero.class));
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

    public Genero findGenero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Genero.class, id);
        } finally {
            em.close();
        }
    }

    public int getGeneroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Genero> rt = cq.from(Genero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
