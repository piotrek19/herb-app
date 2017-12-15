package pl.herbs.data;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import pl.herbs.model.Producer;


@RequestScoped
public class ProducerRepository {
	
	EntityManager em;
	
	
	public ProducerRepository() {
	}

	@Inject
	public ProducerRepository(EntityManager em) {
		this.em = em;
	}

	public List<Producer> getAll() {
		TypedQuery<Producer> query = em.createNamedQuery(Producer.FIND_ALL, Producer.class);
		List<Producer> list = query.getResultList();
		return list;
	}

	
	public Producer getById(Long id) {
		Producer Producer = em.find(Producer.class, id);
		return Producer;
	}

	@Transactional
	public Producer getByIdWithProducts(Long id) {
		Producer Producer = em.find(Producer.class, id);
		Producer.getProducts().size();
		return Producer;
	}

	@Transactional
	public Producer save(Producer Producer) {
		em.persist(Producer);
		return Producer;
		
	}

	@Transactional
	public void update(Producer Producer) {
		em.merge(Producer);
	}
	
	
	
}
