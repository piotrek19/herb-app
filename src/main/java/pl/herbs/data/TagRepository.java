package pl.herbs.data;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import pl.herbs.model.Tag;


@RequestScoped
public class TagRepository {

	
	EntityManager em;	
	
	public TagRepository(){
	}
	
	@Inject
	public TagRepository(EntityManager em){
		this.em=em;
	}
	
	public List<Tag> getAll() {
		TypedQuery<Tag> query = em.createNamedQuery(Tag.FIND_ALL, Tag.class);
		List<Tag> list = query.getResultList();
		return list;
	}
	
	public Tag getById(Long id) {
		return em.find(Tag.class, id);
	}
	
	@Transactional
	public Tag getByIdWithProducts(Long id) {
		Tag tag = em.find(Tag.class, id);
		tag.getProducts().size();
		return tag;
	}
	
	public Tag getByValue(String value) {
		TypedQuery<Tag> query = em.createNamedQuery(Tag.FIND_ALL_BY_VALUE, Tag.class);
		query.setParameter("value", value);
		List<Tag> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0);
	}
	
	@Transactional
	public void save(Tag tag) {
		em.persist(tag);
	}
	
	@Transactional
	public void remove(Tag tag) {
		em.remove(tag);
	}
	
	
	
}


