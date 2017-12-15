package pl.herbs.backing_beans;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.herbs.data.TagRepository;
import pl.herbs.db_helper.BaseDBUnitTestForJPA;
import pl.herbs.model.Product;
import pl.herbs.model.Tag;


public class SearchServiceTest extends BaseDBUnitTestForJPA {

	public static final String SEARCH_SERVICE_TEST_DATA_SET_FILE = "SearchServiceTest_FlatXMLDataSet.xml";
	
	private SearchService target = null;
	
	private IDataSet dataSet = null;
	
	@Before
	public void setup() throws Exception {
		
		target = new SearchService(new TagRepository(entityManager));
				
		// Add data set initialization
		InputStream is =
			ClassLoader.getSystemResourceAsStream(SEARCH_SERVICE_TEST_DATA_SET_FILE);
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		dataSet = builder.build(is);
		
		DatabaseOperation.INSERT.execute(CONN, dataSet);
			
	}
	
	@After
	public void teardown() throws Exception {
		DatabaseOperation.DELETE_ALL.execute(CONN, dataSet);
	}
	
	@Test
	public void findProductsByRegex_shouldReturnCompleteResult() throws Exception {

		// Setup
		final List<Tag> tagList = Arrays.asList(new Tag[]{new Tag(1L, "tag1"), new Tag(2L, "tag2"), new Tag(3L, "tag3"), new Tag(4L, "tag4"), });
		final List<Product> productList = Arrays
				.asList(new Product[] { new Product(1L, "pokrzywa","ho ho ho", null, null, null, null),
						new Product(2L, "tag4", "ho ho ho", null, null, null, null),
						new Product(3L, "ziele", "ho ho ho", null, null, null, null) });
		final String regex = "tag4";	
		Set<Product> productSet = new HashSet<>();
		
		// Execution
		productSet = this.target.findProductsByRegex(tagList, productList, regex);
		
		// Verification
		Assert.assertNotNull(productSet);
		Assert.assertEquals(2, productSet.size());
		
	}
	
	@Test
	public void findProductsByProductFields_shouldReturnProductPartResult() throws Exception {

		// Setup
		final List<Product> productList = Arrays
				.asList(new Product[] { new Product(1L, "pokrzywa","ho ho ho", null, null, null, null),
						new Product(2L, "tag4", "ho ho ho", null, null, null, null),
						new Product(3L, "ziele", "ho ho ho", null, null, null, null) });
		final String regex = "tag4";	
		Set<Product> productSet = new HashSet<>();
		
		// Execution
		productSet = this.target.findProductsByProductFields(productList, regex);
		
		
		// Verification
		Assert.assertNotNull(productSet);
		Assert.assertEquals(1, productSet.size());
		Assert.assertEquals("tag4", productSet.iterator().next().getName());
		
	}
	
	@Test
	public void findProductsByTag_shouldReturnTagPartResult() throws Exception {

		// Setup
		final List<Tag> tagList = Arrays.asList(new Tag[]{new Tag(1L, "tag1"), new Tag(2L, "tag2"), new Tag(3L, "tag3"), new Tag(4L, "tag4"), });
		final String regex = "tag4";	
		Set<Product> productSet = new HashSet<>();
		
		// Execution
		productSet = this.target.findProductsByTag(tagList, regex);
		
		// Verification
		Assert.assertNotNull(productSet);
		Assert.assertEquals(1, productSet.size());
		Assert.assertEquals("ziele", productSet.iterator().next().getName());
	}
	

	private Object[][] createProducerRows() {
		
		Object[][] producerRows = new Object[][] {
				new Object[] {
					1,
					"producer",
					"producer",
					"producer",
					"producer",
					"producer"				
				}
		};
		return producerRows;
	}
	
	
	private Object[][] createProductRows() {
		
		Object[][] productRows = new Object[][] {
				new Object[] {
					1,
					"pokrzywa",
					"ho ho ho",
					12.12,
					1,
					1
				},
				new Object[] {
					2,
					"tag4",
					"ho ho ho",
					12.12,
					2,
					1
				},
				new Object[] {
					3,
					"ziele",
					"ho ho ho",
					12.12,
					3,
					1
				}
			};
		return productRows;
	}
	
	
	private Object[][] createTagRows() {
		
		Object[][] tagRows = new Object[][] {
				new Object[] {
					1,
					"tag1"
				},
				new Object[] {
					2,
					"tag2"
				},
				new Object[] {
					3,
					"tag3"
				},
				new Object[] {
					4,
					"tag4"
				}
			};
		return tagRows;
	}
	
	
	private Object[][] createProductTagRows() {
		
		Object[][] productTagRows = new Object[][] {
				new Object[] {
					1,
					1
				},
				new Object[] {
					2,
					1
				},
				new Object[] {
					3,
					2
				},
				new Object[] {
					4,
					3
				}
			};
		return productTagRows;
	}
	
	
	
}
