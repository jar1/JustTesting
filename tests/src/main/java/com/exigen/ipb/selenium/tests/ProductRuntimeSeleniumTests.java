package com.exigen.ipb.selenium.tests;

import org.junit.After;
import org.junit.Before;

import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.MainApplication;
import com.exigen.ipb.selenium.utils.ProductImportInfo;

/**
 * Base class of test which intentions are verification of product runtime
 * functionality. This class is responsible for product preparation steps:
 * import, deployment and activation. Also it deletes imported product after
 * test is finished.
 * 
 * @author gzukas
 * @since 3.9
 */
public abstract class ProductRuntimeSeleniumTests extends AbstractProductSeleniumTests {
	/**
	 * Imports, deploys and activates specified product.
	 * 
	 * @param product  The information of product to be imported.
	 */
	protected void prepareProduct(ProductImportInfo product) {
		
		/**
		 *  If you need guarantee of product uniqueness, please complain to the
		 *  importTestProduct() as it is the one that should be responsible
		 *  for doing so. Otherwise, you are on your own.
		 */
		//importTestProduct(product);
		getProductManager().importProduct(product.getProductPath());
		
		// Will use WS here
		//productManager.deployAndActivate(product.getProductCd(), product.getProductVersion());
		
		navigateToProductView(product.getProductCd(), product.getProductVersion())
			.deployProduct().activateProduct();
	}

	@Before
	public void importProduct() {
		ProductImportInfo product = getProductImportInfo();
		if (beforeImport(product)) {
			prepareProduct(product);
		}
	}
	
	@After
	public void deleteProduct() {
		ProductImportInfo product = getProductImportInfo();
		if (beforeDelete(product)) {
			getProductManager().deleteProduct(product.getProductCd(), product.getProductVersion());
		}
	}
	
	/**
	 * Override this method if any instructions before importing the product are
	 * needed to be executed.
	 * 
	 * @param product  The information of product to be imported.
	 * @return  True if import of product should be continued, otherwise - false.
	 */
	protected boolean beforeImport(ProductImportInfo product) {
		// Should be overridden if needed.
		return true;
	}
	
	/**
	 * Override this method if any instructions before deletion of product are
	 * needed to be executed.
	 * 
	 * @param product  The information of product to be deleted.
	 * @return  True if deletion of product should be continued, otherwise - false.
	 */
	protected boolean beforeDelete(ProductImportInfo product) {
		// Should be overridden if needed.
		return true;
	}

	@Override
	public Application setUpApplication() {
		return new MainApplication(getDriver(), getConfiguration());
	}
	
	/**
	 * Returns information about product that will be used in import-test-delete cycle.
	 * @return
	 */
	protected abstract ProductImportInfo getProductImportInfo();

}
