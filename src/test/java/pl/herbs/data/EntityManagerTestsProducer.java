package pl.herbs.data;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.enterprise.inject.Alternative;


@Alternative
public class EntityManagerTestsProducer {


	@PersistenceContext(unitName = "herbPU")
	EntityManager em;

	
	@Produces
	public EntityManager createEntityManager() {
		return em;
	}
}
