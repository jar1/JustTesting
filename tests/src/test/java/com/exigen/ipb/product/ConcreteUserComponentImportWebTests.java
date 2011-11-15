
package com.exigen.ipb.product;

import static org.junit.Assert.*;

import com.exigen.ipb.components.domain.ConcreteUserComponent;
import com.exigen.ipb.product.pages.CUCConflictPage;
import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;
import com.exigen.ipb.product.pages.ProductSearchPage;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import java.io.File;
import org.junit.After;
import org.junit.Test;

/**
 * Tests handling of concrete user component import conflicts.
 * 
 * @author ggrazevicius
 * @since 3.9
 */
public class ConcreteUserComponentImportWebTests extends AbstractProductSeleniumTests {

	private final String PRODUCT1 = "target/test-classes/products/CUCRT.zip";	
	private final String PRODUCT2 = "target/test-classes/products/CUCRT2.zip";

	@Test
	public void shouldImportThruRestful() {
		getProductManager().importProduct(new File(PRODUCT1), true);
		ConcreteUserComponent ccomponent = getComponentService().findConcreteUserComponent("UserPolicy", 1.0);
		
		assertFalse(ccomponent.getComponentConfiguration().getDisplayHeaderInd());
		
		getProductManager().importProduct(new File(PRODUCT2), true);
		ccomponent = getComponentService().findConcreteUserComponent("UserPolicy", 1.0);
		assertTrue(ccomponent.getComponentConfiguration().getDisplayHeaderInd());
		
		getProductManager().deleteProduct("CUCRT", 1.0);
		getProductManager().importProduct(new File(PRODUCT1), false);
		ccomponent = getComponentService().findConcreteUserComponent("UserPolicy", 1.0);
		
		assertTrue(ccomponent.getComponentConfiguration().getDisplayHeaderInd());
		
		getProductManager().deleteProduct("CUCRT", 1.0);
		getProductManager().deleteProduct("CUCRT2", 1.0);
	}
	
	@Test
	public void shouldImportThruUI() {
		// Remove component incase its already in the database
		getComponentService().removeConcreteUserComponent("UserPolicy", 1.0);
		
		// Go to search page ...
		getApplication().navigateToFlow(FLOW_PRODUCT_MAIN);
    	ProductSearchPage productSearchPage = create(ProductSearchPage.class);
		productSearchPage.openImportDialog();

		String path = (new File(PRODUCT1)).getAbsolutePath();
		
		// ... and import product
		productSearchPage.sendProductPath(path);
		productSearchPage.doImport();
		
		ProductConsolidatedViewPage productConsolidatedViewPage = create(ProductConsolidatedViewPage.class);
		
		// Check if product was imported
		assertTrue("CUCRT product should be imported", productConsolidatedViewPage.existsProduct("CUCRT"));
		productConsolidatedViewPage.clickCancel();
		
		// Check if displayHeader is FALSE (meaning component is imported and is from first product)
		ConcreteUserComponent ccomponent = getComponentService().findConcreteUserComponent("UserPolicy", 1.0);
		assertFalse(ccomponent.getComponentConfiguration().getDisplayHeaderInd());
		
		// Then import a second product
		productSearchPage = create(ProductSearchPage.class);
		
		productSearchPage.openImportDialog();
		String path2 = (new File(PRODUCT2)).getAbsolutePath();
		productSearchPage.sendProductPath(path2);
		productSearchPage.doImport();

		// We should get the conflict page, choose override
		CUCConflictPage cucConflictPage = create(CUCConflictPage.class);
		cucConflictPage.clickOverride();
		
		// Check if import was successful
		productConsolidatedViewPage = create(ProductConsolidatedViewPage.class);
		assertTrue("CUCRT2 product should be imported", productConsolidatedViewPage.existsProduct("CUCRT2"));
		productConsolidatedViewPage.clickCancel();
		
		// Check if user component was overriden
		ccomponent = getComponentService().findConcreteUserComponent("UserPolicy", 1.0);
		assertTrue(ccomponent.getComponentConfiguration().getDisplayHeaderInd());

		// Now, import the first product again ...
		getProductManager().deleteProduct("CUCRT", 1.0);
		
		productSearchPage = create(ProductSearchPage.class);
		
		productSearchPage.openImportDialog();
		productSearchPage.sendProductPath(path);
		productSearchPage.doImport();
		
		// ... and do not override existing user component
		cucConflictPage = create(CUCConflictPage.class);
		cucConflictPage.clickSkip();

		// Check if product was imported
		productConsolidatedViewPage = create(ProductConsolidatedViewPage.class);
		assertTrue("CUCRT product should be imported", productConsolidatedViewPage.existsProduct("CUCRT"));
		productConsolidatedViewPage.clickCancel();
		
		// Check if it's still the component from second product
		ccomponent = getComponentService().findConcreteUserComponent("UserPolicy", 1.0);
		assertTrue(ccomponent.getComponentConfiguration().getDisplayHeaderInd());

	}	
	
	@After
	public void onTearDown() {
		getProductManager().deleteProduct("CUCRT", 1.0);
		getProductManager().deleteProduct("CUCRT2", 1.0);
		getComponentService().removeConcreteUserComponent("UserPolicy", 1.0);
	}	
	
	@Override
	public Application setUpApplication() {
		return new AdminApplication(getDriver(), getConfiguration());
	}
}
