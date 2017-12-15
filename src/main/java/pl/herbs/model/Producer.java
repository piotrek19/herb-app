package pl.herbs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;


import org.hibernate.validator.constraints.Email;

@Entity
@Table(name="producer")
@NamedQueries({
    @NamedQuery(name = Producer.FIND_ALL, query = "SELECT p FROM Producer p"),
    @NamedQuery(name = Producer.FIND_ALL_NAMES, query = "SELECT p.name FROM Producer p")
})
public class Producer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String FIND_ALL = "Producer.findAll";
	public static final String FIND_ALL_NAMES = "Producer.findAllNames";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="producer_id")
	private Long id;
	@NotNull
	private String name;
	private String description;
	private String address;
	private String phone;
	@Email
	private String email;
	@OneToMany(mappedBy="producer")
	private List<Product> products= new ArrayList<>();
	
	// CONSTRUCTORS:
	
	public Producer(){}


	public Producer(String name, String description, String address, String phone, String email,
			List<Product> products) {
		this.name = name;
		this.description = description;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.products = products;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
		return "Producer [id=" + id + ", name=" + name + ", description=" + description + ", address=" + address
				+ ", phone=" + phone + ", email=" + email + "]";
	}
	
	
	
}
