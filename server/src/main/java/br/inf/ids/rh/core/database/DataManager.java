package br.inf.ids.rh.core.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Component
public class DataManager {
	
	private EntityManager em = null;
	
	private EntityManager getEm() {
		if (em==null) {
			em = new EntityManagerUtil().getEntityManager();
		}
		return em;
	}
	
	private EntityManager transacao() {
		if (!this.getEm().getTransaction().isActive()) {
			this.getEm().getTransaction().begin();
		}
		return em;
	}
	
	
	public void commit() throws Exception {
		em.getTransaction().commit();
	}
	
	public void rollback() {
		em.getTransaction().rollback();
	}
	
	public void close() {
		try {
			if (em!=null) {
				em.close();
			}
		} catch (Exception e) {
		}
		this.em = null;
	}
	
	public <T> TypedQuery<T> createQuery(String sql, Class<T> classe) {
		TypedQuery<T> createQuery = this.getEm().createQuery(sql, classe);
		return createQuery;
	}
	
	public <T extends EntityId> void gravar(T entity) {
		entity.setId(null);
		this.transacao().persist(entity);
	}

	public <T extends EntityId> void alterar(T entity) {
		this.transacao().merge(entity);
	}
	
	public <T extends EntityId> void excluir(T entity) {
		em.remove(entity);
	}

	public <T> T byId(Class<T> classe, long id) {
		return getEm().find(classe, id);
	}
	
	public <T> List<T> all(Class<T> classe) {
		
		String sql = "from "+classe.getSimpleName();
		TypedQuery<T> query = getEm().createQuery(sql, classe);
		return query.getResultList();
		
	}
	
}