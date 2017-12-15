package pl.herbs.backing_beans;

import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.internal.invocation.finder.VerifiableInvocationsFinder;


import pl.herbs.data.ProductRepository;
import pl.herbs.model.Product;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddProductTest {

	private static final String SUCCESS = "success";
	
	
	private AddProduct target = null;
	
	
	@Before
	public void setup(){
		
	}

	
	@Test
	public void submit_shouldOrderProductPersistance() throws Exception {
		
		// Setup
		Product productFixture = new Product();
		ProductRepository mockProductRepository = Mockito.mock(ProductRepository.class);
		Mockito.when(mockProductRepository.save(productFixture)).thenReturn(productFixture);
		target = new AddProduct(mockProductRepository, new Product());
		
		String result;
		
		// Execution
		
		result = this.target.submit();
		
		// Verification
		
		Assert.assertNotNull(result);
		Assert.assertEquals(SUCCESS, result);
		Mockito.verify(mockProductRepository, Mockito.times(1)).save(productFixture);
		
	}
	
	
	
	@Test
	public void getProduct_shouldReturnProduct() throws Exception {
		
		// Setup
		Product productFixture = new Product();
		target = new AddProduct(new ProductRepository(), productFixture);
		Product result;
		
		// Execution
		result = this.target.getProduct();
		
		// Verification
		Assert.assertNotNull(result);
		Assert.assertSame(productFixture, result);		
	}


}
