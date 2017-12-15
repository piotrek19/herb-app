package pl.herbs.backing_beans;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import pl.herbs.data.ProducerRepository;
import pl.herbs.data.ProductRepository;
import pl.herbs.model.Producer;
import pl.herbs.model.Product;
import pl.herbs.model.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class ProductDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProductRepository productRepo;

	@Inject
	private ProducerRepository producerRepo;

	private long productId;
	private Product product;

	private Map<String, Producer> producersMap = new HashMap<>();
	private List<String> producersNames = new ArrayList<>();
	private String chosenProducerName;

	private List<String> tagValues = new ArrayList<>();
	private String additionalTagValue;
	private String removableTagValue;

	public void onload() {
		product = productRepo.getByIdWithTags(productId);

		for (Tag tag : product.getTags()) {
			tagValues.add(tag.getValue());
		}

	}

	public void loadProducers() {

		chosenProducerName = (product.getProducer() != null) ?  product.getProducer().getName() : "undefined";
		
		for (Producer producer : producerRepo.getAll()) {
			producersMap.put(producer.getName(), producer);
			producersNames.add(producer.getName());
		}
		
		producersNames.sort((n1, n2) -> n1.compareTo(n2));

	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	
	public List<String> getProducersNames() {
		return producersNames;
	}

	public String getChosenProducerName() {
		return chosenProducerName;
	}
	
	public void setChosenProducerName(String chosenProducerName) {
		this.chosenProducerName = chosenProducerName;
	}

	public String getAdditionalTagValue() {
		return additionalTagValue;
	}

	public void setAdditionalTagValue(String additionalTagValue) {
		this.additionalTagValue = additionalTagValue;
	}

	public List<String> getTagValues() {
		return tagValues;
	}

	public void setTagValues(List<String> tagValues) {
		this.tagValues = tagValues;
	}

	public String getRemovableTagValue() {
		return removableTagValue;
	}

	public void setRemovableTagValue(String removableTagValue) {
		this.removableTagValue = removableTagValue;
	}

	@Transactional
	public String saveChanges() {

		// change of producer:
		if (chosenProducerName != null) 
			if ((product.getProducer() == null) || (!chosenProducerName.equals(product.getProducer().getName())))
			product.setProducer(producersMap.get(chosenProducerName));
		
		// update product fields:
		List<Tag> emptyTagList = new ArrayList<>();
		product.setTags(emptyTagList);
		productRepo.update(product);

		// remove relation to tag:
		if (!(removableTagValue == null) && !removableTagValue.isEmpty())
			productRepo.removeTag(product, removableTagValue);
		
		// add relation to tag:
		if (!(additionalTagValue == null) && !additionalTagValue.isEmpty())
			productRepo.addTag(product, additionalTagValue);

		return "success";
	}
}
