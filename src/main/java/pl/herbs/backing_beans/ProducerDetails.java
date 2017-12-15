package pl.herbs.backing_beans;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import pl.herbs.data.ProducerRepository;
import pl.herbs.model.Producer;
import pl.herbs.model.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class ProducerDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProducerRepository producerRepo;

	private long producerId;

	private Producer producer;


	public long getProducerId() {
		return producerId;
	}

	public void setProducerId(long producerId) {
		this.producerId = producerId;
	}

	public void onload() {
		producer = producerRepo.getByIdWithProducts(producerId);

	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	@Transactional
	public String saveChanges() {

		List<Product> emptyProductList = new ArrayList<>();
		producer.setProducts(emptyProductList);
		producerRepo.update(producer);

		return "success";
	}

}
