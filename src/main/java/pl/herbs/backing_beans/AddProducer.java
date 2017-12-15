package pl.herbs.backing_beans;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.*;

import pl.herbs.model.Producer;
import pl.herbs.data.ProducerRepository;

@ViewScoped
@Named
public class AddProducer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProducerRepository producerRepo;

	private Producer Producer;
	

	public AddProducer() {
	}

	public AddProducer(ProducerRepository producerRepo, Producer Producer) {
		this.producerRepo = producerRepo;
		this.Producer = Producer;
	}


	public void onload() {
		Producer = new Producer();
	}

	
	public Producer getProducer() {
		return Producer;
	}

	
	public String submit() {
		
		producerRepo.save(Producer);
		return "success";
		
	}

}
