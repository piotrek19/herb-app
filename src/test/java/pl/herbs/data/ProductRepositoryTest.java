package pl.herbs.data;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.herbs.db_helper.BaseDBUnitTestForJPA;
import pl.herbs.model.Product;
import pl.herbs.model.Tag;


public class ProductRepositoryTest extends BaseDBUnitTestForJPA {

	public static final String PRODUCT_REPOSITORY_TEST_DATA_SET_FILE = "SearchServiceTest_FlatXMLDataSet.xml";

	
	private ProductRepository target = null;
	
	private IDataSet dataSet = null;
	
	@Before
	public void setup() throws Exception {
		
		target = new ProductRepository(entityManager, new TagRepository(entityManager));
		
		// Add data set initialization
		InputStream is =
			ClassLoader.getSystemResourceAsStream(PRODUCT_REPOSITORY_TEST_DATA_SET_FILE);
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		dataSet = builder.build(is);
		
		DatabaseOperation.INSERT.execute(CONN, dataSet);
	}
	
	@After
	public void teardown() throws Exception {
		DatabaseOperation.DELETE_ALL.execute(CONN, dataSet);
	}
	
	@Test
	public void getAll_shouldReturnAllProducts() throws Exception {

		// Setup

		// Execution
		List<Product> productList = this.target.getAll();
		
		// Verification
		Assert.assertNotNull(productList);
		Assert.assertEquals(3, productList.size());
		Assert.assertEquals(new BigDecimal("12.12"), productList.get(0).getPrice());
		
	}
	
	
	
	@Test(expected = IllegalArgumentException.class)
	public void addTag_shouldThrowExceptionDueToBadArgument() {

		// Setup
		EntityTransaction tx = entityManager.getTransaction();

		// Execution
		tx.begin();
		this.target.addTag(null, "tagValue");
		tx.commit();
		// Verification

	}
	
	@Test
	public void addTag_shouldAddExistingTag() throws Exception {
		
		// Setup
		Product product = new Product();
		product.setId(1L);
		
		List<Tag> productTags = new ArrayList<>();
		
		Tag tag = new Tag(3L, "tag3");

		EntityTransaction tx = entityManager.getTransaction();
		
		// Execution
		tx.begin();
		this.target.addTag(product, tag.getValue());
		tx.commit();
		productTags= this.target.getByIdWithTags(product.getId()).getTags();
		
		// Verification
		Assert.assertEquals(3, productTags.size());
		Assert.assertTrue("product should be link with added tag", productTags.contains(tag));
		
	}
	
	
	@Test
	public void addTag_shouldAddNewTag() throws Exception {
		
		// Setup
		Product product = new Product();
		product.setId(1L);
		
		List<Tag> productTags = new ArrayList<>();
		
		String tagValue = "tag4";

		EntityTransaction tx = entityManager.getTransaction();
		
		// Execution
		tx.begin();
		this.target.addTag(product, tagValue);
		tx.commit();
		
		productTags= this.target.getByIdWithTags(product.getId()).getTags();
		
		// Verification
		Assert.assertEquals(3, productTags.size());
		Assert.assertTrue("Added tag should have valid properties", checkAddedTag(tagValue, productTags));
	}
	
	
	@Test
	public void removeTag_shouldRemoveTag() throws Exception {
		
		// Setup
		Product product = new Product();
		product.setId(1L);
		
		List<Tag> productTags = new ArrayList<>();
		
		String tagValue = "tag1";

		EntityTransaction tx = entityManager.getTransaction();
		
		// Execution
		tx.begin();
		this.target.removeTag(product, tagValue);
		tx.commit();
		
		productTags= this.target.getByIdWithTags(product.getId()).getTags();
		
		// Verification
		Assert.assertEquals(1, productTags.size());		
	}
	

	
	private boolean checkAddedTag(String tagValue, List<Tag> productTags) {
		boolean result = false;

		for (Tag currentTag : productTags) {
			if (currentTag.getValue().equals(tagValue) && currentTag.getId() != null) {
				result = true;
				break;
			}
		}

		return result;

	}
	
	
	
}
