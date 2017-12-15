package pl.herbs.data;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.enterprise.inject.Produces;

public class EntityManagerProducer {

	@PersistenceContext(unitName = "herbPU")
	EntityManager em;

	@Produces
	public EntityManager createEntityManager() {
		return em;
	}

}
