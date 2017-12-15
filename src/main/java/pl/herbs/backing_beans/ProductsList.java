package pl.herbs.backing_beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;

import pl.herbs.data.ProductRepository;
import pl.herbs.data.TagRepository;
import pl.herbs.model.Product;
import pl.herbs.model.Tag;

import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class ProductsList {

	@Inject
	private ProductRepository productRepo;

	@Inject
	private TagRepository tagRepo;

	private List<Product> products;

	@Size(min = 3, max = 20, message = "For products searching please enter text with size between {min} and {max} chars.")
	private String regex;

	@Inject
	private SearchService searchService;

	@PostConstruct
	public void initialize() {
		products = productRepo.getAll();
	}

	public List<Product> getProducts() {
		return products;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public void search() {

		if (regex == null || regex.length() < 3)
			return;

		List<Tag> tagList = tagRepo.getAll();

		products = new ArrayList<>(searchService.findProductsByRegex(tagList, products, regex));

		if (products.size() == 0) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			ctx.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "No product matches up to entered '" + regex + "' text.", "No product matches up to entered '" + regex + "' text."));
		}

		return;

	}

}
