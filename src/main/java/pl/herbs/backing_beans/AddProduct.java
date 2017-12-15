package pl.herbs.backing_beans;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.*;

import pl.herbs.model.Product;
import pl.herbs.data.ProductRepository;

@ViewScoped
@Named
public class AddProduct implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProductRepository productRepo;

	private Product product;
	

	public AddProduct() {
	}

	public AddProduct(ProductRepository productRepo, Product product) {
		this.productRepo = productRepo;
		this.product = product;
	}


	public void onload() {
		product = new Product();
	}

	
	public Product getProduct() {
		return product;
	}

	
	public String submit() {
		
		productRepo.save(product);
		return "success";
		
	}

}
