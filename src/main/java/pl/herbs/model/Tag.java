package pl.herbs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;

@Entity
@Table(name="tag")
@NamedQueries({
    @NamedQuery(name = Tag.FIND_ALL, query = "SELECT t FROM Tag t"),
    @NamedQuery(name = Tag.FIND_ALL_BY_VALUE, query = "SELECT t FROM Tag t WHERE t.value= :value")
    
})
public class Tag implements Serializable {


	private static final long serialVersionUID = 1L;
	
	public static final String FIND_ALL = "Tag.findAll";
	public static final String FIND_ALL_BY_VALUE = "Tag.findAllByValue";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="tag_id")
	private Long id;
	private String value;
	
 
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(
	        name="product_tag",
	        inverseJoinColumns=
	            @JoinColumn(name="product_id", referencedColumnName="product_id"),
	        joinColumns=
	            @JoinColumn(name="tag_id", referencedColumnName="tag_id")
	    )
	private List<Product> products = new ArrayList<>();

	
	// CONSTRUCTORS:

	public Tag(){}


	/**
	 * @param id
	 * @param value
	 * @param products
	 */
	public Tag(Long id, String value, List<Product> products) {
		this.id = id;
		this.value = value;
		this.products = products;
	}
	
	
	/**
	 * @param id
	 * @param value
	 */
	public Tag(Long id, String value) {
		this.id = id;
		this.value = value;
	}

	
	// GETTERS/SETTERS:

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}


	/**
	 * @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}


	/**
	 * @param products the products to set
	 */
	public void setProducts(List<Product> products) {
		this.products = products;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tag [id=" + id + ", value=" + value + ", products=" + products.size() + "]";
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tag other = (Tag) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
}
