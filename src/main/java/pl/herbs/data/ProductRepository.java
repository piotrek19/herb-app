package pl.herbs.data;


import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import pl.herbs.model.Product;
import pl.herbs.model.Tag;

@RequestScoped
public class ProductRepository {


	EntityManager em;

	TagRepository tagRepo;


	public ProductRepository() {
	}

	@Inject
	public ProductRepository(EntityManager em, TagRepository tagRepo) {
		this.em = em;
		this.tagRepo = tagRepo;
	}

	public List<Product> getAll() {
		TypedQuery<Product> query = em.createNamedQuery(Product.FIND_ALL, Product.class);
		List<Product> list = query.getResultList();
		return list;
	}


	public Product getById(Long id) {
		Product product = em.find(Product.class, id);
		return product;
	}

	@Transactional
	public Product getByIdWithTags(Long id) {
		Product product = em.find(Product.class, id);
		product.getTags().size();
		return product;
	}

	@Transactional
	public Product save(Product product) {
		em.persist(product);
		return product;
		
	}

	@Transactional
	public void update(Product product) {
		em.merge(product);
	}

	@Transactional
	public void addTag(Product product, String tagValue) {

		if (product == null || tagValue == null)
			throw new IllegalArgumentException("Method argument should not be null");
		
		Product managedProduct = em.find(Product.class, product.getId());
		managedProduct.getTags();

		Tag tag = new Tag(null, tagValue);
		Tag retrievedTag = tagRepo.getByValue(tagValue);

		if (retrievedTag == null) {
			tagRepo.save(tag);
			tag.getProducts().add(managedProduct);
			return;
		}

		if (!retrievedTag.getProducts().contains(managedProduct)) {
			retrievedTag.getProducts().add(managedProduct);
		}
	}

	@Transactional
	public void removeTag(Product product, String tagValue) {

		if (product == null || tagValue == null)
			throw new IllegalArgumentException("Method argument should not be null");
		
		Product managedProduct = em.find(Product.class, product.getId());
		managedProduct.getTags();

		Tag retrievedTag = tagRepo.getByValue(tagValue);

		if (retrievedTag == null) {
			return;
		}

		if (retrievedTag.getProducts().contains(managedProduct)) {
			retrievedTag.getProducts().remove(managedProduct);

			if (retrievedTag.getProducts().isEmpty()) {
				tagRepo.remove(retrievedTag);
			}

		}
	
	}


}
