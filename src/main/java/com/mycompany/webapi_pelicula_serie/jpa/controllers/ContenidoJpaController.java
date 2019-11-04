/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webapi_pelicula_serie.jpa.controllers;

import com.mycompany.webapi_pelicula_serie.jpa.controllers.exceptions.IllegalOrphanException;
import com.mycompany.webapi_pelicula_serie.jpa.controllers.exceptions.NonexistentEntityException;
import com.mycompany.webapi_pelicula_serie.jpa.entities.Contenido;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.webapi_pelicula_serie.jpa.entities.Favorita;
import com.mycompany.webapi_pelicula_serie.jpa.entities.ContenidoGenero;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author ANDRÉS FELIPE MERA TRÓCHEZ
 */
public class ContenidoJpaController implements Serializable {

    public ContenidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Contenido> findByContenidoTitulo(String titulo){
        List<Contenido> contenidos = null;
        EntityManager em = emf.createEntityManager();
        TypedQuery<Contenido> consulta = em.createNamedQuery("Contenido.findByConTitulo",Contenido.class);
        consulta.setParameter("conTitulo", titulo);
        contenidos = consulta.getResultList();
        return contenidos;
    }
    
    public void create(Contenido contenido) {
        if (contenido.getContenidoGeneroList() == null) {
            contenido.setContenidoGeneroList(new ArrayList<ContenidoGenero>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Favorita favorita = contenido.getFavorita();
            if (favorita != null) {
                favorita = em.getReference(favorita.getClass(), favorita.getFavId());
                contenido.setFavorita(favorita);
            }
            List<ContenidoGenero> attachedContenidoGeneroList = new ArrayList<ContenidoGenero>();
            for (ContenidoGenero contenidoGeneroListContenidoGeneroToAttach : contenido.getContenidoGeneroList()) {
                contenidoGeneroListContenidoGeneroToAttach = em.getReference(contenidoGeneroListContenidoGeneroToAttach.getClass(), contenidoGeneroListContenidoGeneroToAttach.getCgId());
                attachedContenidoGeneroList.add(contenidoGeneroListContenidoGeneroToAttach);
            }
            contenido.setContenidoGeneroList(attachedContenidoGeneroList);
            em.persist(contenido);
            if (favorita != null) {
                Contenido oldFkContenidoOfFavorita = favorita.getFkContenido();
                if (oldFkContenidoOfFavorita != null) {
                    oldFkContenidoOfFavorita.setFavorita(null);
                    oldFkContenidoOfFavorita = em.merge(oldFkContenidoOfFavorita);
                }
                favorita.setFkContenido(contenido);
                favorita = em.merge(favorita);
            }
            for (ContenidoGenero contenidoGeneroListContenidoGenero : contenido.getContenidoGeneroList()) {
                Contenido oldFkContenidoOfContenidoGeneroListContenidoGenero = contenidoGeneroListContenidoGenero.getFkContenido();
                contenidoGeneroListContenidoGenero.setFkContenido(contenido);
                contenidoGeneroListContenidoGenero = em.merge(contenidoGeneroListContenidoGenero);
                if (oldFkContenidoOfContenidoGeneroListContenidoGenero != null) {
                    oldFkContenidoOfContenidoGeneroListContenidoGenero.getContenidoGeneroList().remove(contenidoGeneroListContenidoGenero);
                    oldFkContenidoOfContenidoGeneroListContenidoGenero = em.merge(oldFkContenidoOfContenidoGeneroListContenidoGenero);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contenido contenido) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contenido persistentContenido = em.find(Contenido.class, contenido.getConId());
            Favorita favoritaOld = persistentContenido.getFavorita();
            Favorita favoritaNew = contenido.getFavorita();
            List<ContenidoGenero> contenidoGeneroListOld = persistentContenido.getContenidoGeneroList();
            List<ContenidoGenero> contenidoGeneroListNew = contenido.getContenidoGeneroList();
            List<String> illegalOrphanMessages = null;
            for (ContenidoGenero contenidoGeneroListOldContenidoGenero : contenidoGeneroListOld) {
                if (!contenidoGeneroListNew.contains(contenidoGeneroListOldContenidoGenero)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ContenidoGenero " + contenidoGeneroListOldContenidoGenero + " since its fkContenido field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (favoritaNew != null) {
                favoritaNew = em.getReference(favoritaNew.getClass(), favoritaNew.getFavId());
                contenido.setFavorita(favoritaNew);
            }
            List<ContenidoGenero> attachedContenidoGeneroListNew = new ArrayList<ContenidoGenero>();
            for (ContenidoGenero contenidoGeneroListNewContenidoGeneroToAttach : contenidoGeneroListNew) {
                contenidoGeneroListNewContenidoGeneroToAttach = em.getReference(contenidoGeneroListNewContenidoGeneroToAttach.getClass(), contenidoGeneroListNewContenidoGeneroToAttach.getCgId());
                attachedContenidoGeneroListNew.add(contenidoGeneroListNewContenidoGeneroToAttach);
            }
            contenidoGeneroListNew = attachedContenidoGeneroListNew;
            contenido.setContenidoGeneroList(contenidoGeneroListNew);
            contenido = em.merge(contenido);
            if (favoritaOld != null && !favoritaOld.equals(favoritaNew)) {
                favoritaOld.setFkContenido(null);
                favoritaOld = em.merge(favoritaOld);
            }
            if (favoritaNew != null && !favoritaNew.equals(favoritaOld)) {
                Contenido oldFkContenidoOfFavorita = favoritaNew.getFkContenido();
                if (oldFkContenidoOfFavorita != null) {
                    oldFkContenidoOfFavorita.setFavorita(null);
                    oldFkContenidoOfFavorita = em.merge(oldFkContenidoOfFavorita);
                }
                favoritaNew.setFkContenido(contenido);
                favoritaNew = em.merge(favoritaNew);
            }
            for (ContenidoGenero contenidoGeneroListNewContenidoGenero : contenidoGeneroListNew) {
                if (!contenidoGeneroListOld.contains(contenidoGeneroListNewContenidoGenero)) {
                    Contenido oldFkContenidoOfContenidoGeneroListNewContenidoGenero = contenidoGeneroListNewContenidoGenero.getFkContenido();
                    contenidoGeneroListNewContenidoGenero.setFkContenido(contenido);
                    contenidoGeneroListNewContenidoGenero = em.merge(contenidoGeneroListNewContenidoGenero);
                    if (oldFkContenidoOfContenidoGeneroListNewContenidoGenero != null && !oldFkContenidoOfContenidoGeneroListNewContenidoGenero.equals(contenido)) {
                        oldFkContenidoOfContenidoGeneroListNewContenidoGenero.getContenidoGeneroList().remove(contenidoGeneroListNewContenidoGenero);
                        oldFkContenidoOfContenidoGeneroListNewContenidoGenero = em.merge(oldFkContenidoOfContenidoGeneroListNewContenidoGenero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contenido.getConId();
                if (findContenido(id) == null) {
                    throw new NonexistentEntityException("The contenido with id " + id + " no longer exists.");
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
            Contenido contenido;
            try {
                contenido = em.getReference(Contenido.class, id);
                contenido.getConId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contenido with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ContenidoGenero> contenidoGeneroListOrphanCheck = contenido.getContenidoGeneroList();
            for (ContenidoGenero contenidoGeneroListOrphanCheckContenidoGenero : contenidoGeneroListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Contenido (" + contenido + ") cannot be destroyed since the ContenidoGenero " + contenidoGeneroListOrphanCheckContenidoGenero + " in its contenidoGeneroList field has a non-nullable fkContenido field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Favorita favorita = contenido.getFavorita();
            if (favorita != null) {
                favorita.setFkContenido(null);
                favorita = em.merge(favorita);
            }
            em.remove(contenido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contenido> findContenidoEntities() {
        return findContenidoEntities(true, -1, -1);
    }

    public List<Contenido> findContenidoEntities(int maxResults, int firstResult) {
        return findContenidoEntities(false, maxResults, firstResult);
    }

    private List<Contenido> findContenidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contenido.class));
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

    public Contenido findContenido(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contenido.class, id);
        } finally {
            em.close();
        }
    }

    public int getContenidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contenido> rt = cq.from(Contenido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
