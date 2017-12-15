package pl.herbs.backing_beans;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;


import pl.herbs.data.TagRepository;
import pl.herbs.model.Product;
import pl.herbs.model.Tag;

@RequestScoped
public class SearchService {
	
	
	@Inject
	private TagRepository tagRepo;
	
	
	public SearchService(){	
	}
	
	public SearchService(TagRepository tagRepo){
		this.tagRepo=tagRepo;
	}

	public Set<Product> findProductsByTag(List<Tag> tags, String regex) {
		Set<Product> result = new HashSet<Product>();
		Pattern pattern = Pattern.compile(regex);

		for (Tag tag : tags) {
			if (pattern.matcher(tag.getValue()).find()){
				result.addAll(tagRepo.getByIdWithProducts(tag.getId()).getProducts());
			} 
		}
		return result;
	}

	
	public Set<Product> findProductsByProductFields(List<Product> products, String regex) {
		Set<Product> result = new HashSet<Product>();
		Pattern pattern = Pattern.compile(regex);
		
		for (Product product : products) {
			if (pattern.matcher(product.getName()).find() || pattern.matcher(product.getDescription()).find()){
				result.add(product);
			} 
		}	
		return result;
	}
	
	public Set<Product> findProductsByRegex(List<Tag> tags, List<Product> products, String regex){
		Set<Product> result = findProductsByTag(tags, regex);
		result.addAll(findProductsByProductFields(products, regex));	
		return result;
	}
	
	

}
