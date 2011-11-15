package com.exigen.ipb.selenium.tests;

import static org.junit.Assert.fail;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.After;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;
import com.exigen.ipb.selenium.AbstractWebTests;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.utils.ProductImportInfo;
import com.exigen.ipb.selenium.utils.RestfulComponentService;
import com.exigen.ipb.selenium.utils.PageUtil;
import com.exigen.ipb.selenium.utils.RestfulProductManager;
import common.Logger;

/**
 *
 * @see ProductRuntimeSeleniumTests
 * 
 * @author ?
 * @since ?
 * 
 * @author gzukas
 * @since 3.9
 *
 */
//@RunWith(AlternateViewRunner.class)
public abstract class AbstractProductSeleniumTests extends AbstractWebTests {
    
	public static final long PF_AJAX_TIMEOUT = 15000;
	
	public final static String FLOW_PRODUCT_MAIN = "product-main-flow";
	public final static String FLOW_SEARCH_MAIN = "search-main-flow";
	
	private static final Logger log = Logger.getLogger(AbstractProductSeleniumTests.class);
	
	private RestfulProductManager productManager;
	
	private RestfulComponentService componentService;
    
    /**
     * Navigates to consolidated view of specified product page.
     * 
     * @param productCd  Product code.
     * @param productVersion  Product version.
     * @return  Product consolidated view page.
     */
    public ProductConsolidatedViewPage navigateToProductView(String productCd, double productVersion) {
    	ProductConsolidatedViewPage consolidatedView =
    			create(ProductConsolidatedViewPage.class, productCd, productVersion);
    	
		consolidatedView.navigate(getApplication());
		return consolidatedView;
    }
    
    /**
     * Hook to guarantee that this After is called after all others in subclasses
     */
	@After
	public void tearDown() {
	}
	
	/**
	 * Imports product using RestfulProductManager and then deploys&activates it manually through Admin UI.
	 * 
	 * @param productInfo information about product.
	 */
	public void importTestProductAndActivate(ProductImportInfo productInfo) {
		importTestProductAndActivate(productInfo.getProductCd(), productInfo.getProductVersion(), productInfo.getProductPath());
	}
	
	/**
	 * Imports product using RestfulProductManager and then deploys&activates it manually through Admin UI.
	 * 
	 * @param productCd Code of product being imported. It is used for product deployment&activation and deletion if needed.
	 * @param productVersion Version of product being imported. It is used for product deployment&activation and deletion if needed.
	 * @param productDir Path to zipped product file to import
	 */
	public void importTestProductAndActivate(String productCd, double productVersion, String productDir) {
		importTestProduct(productCd, productVersion, productDir);
		deployAndActivate2(productCd, productVersion);
	}
	
	@Deprecated
	public void deployAndActivate2(String productCd, double productVersion) {
		Application admin = new AdminApplication(getDriver(), getConfiguration());
		ProductConsolidatedViewPage consolidatedViewPage = create(ProductConsolidatedViewPage.class, productCd, productVersion);
		consolidatedViewPage.navigate(admin);
		consolidatedViewPage.deployProduct();
		consolidatedViewPage.activateProduct();
		Assert.assertTrue(consolidatedViewPage.isActivated());
	}

	/**
	 * Deploys&activates product using RestfulProductManager
	 * (doesn't work for now due to bug in WebClient (?))
	 * @param productCd Code of product being deployed&activated.
	 * @param productVersion Version of product being deployed&activated.
	 */
	public void deployAndActivate(String productCd, double productVersion) {
		try {
			getProductManager().deployAndActivate(productCd, productVersion);
		} catch (IllegalStateException ex) {
			
			try {
				deleteTestProduct(productCd, productVersion);
			} catch (Exception e) {}
			
			fail("Could not deploy/activate product with " + ex.getMessage());
		}
	}
	
	/**
	 * Deletes product using RestfulProductManager
	 * 
	 * @param productCd Code of product to delete.
	 * @param productVersion Version of product to delete.
	 */
	public void deleteTestProduct(String productCd, double productVersion) {
		getProductManager().deleteProduct(productCd, productVersion);
	}
	
	public void deleteTestProduct(ProductImportInfo productImportInfo) {
		getProductManager().deleteProduct(productImportInfo.getProductCd(), productImportInfo.getProductVersion());
	}
	
	/**
	 * Imports product using RestfulProductManager.
	 * If IllegalStateException is thrown while importing product for first time,
	 * it assumes that product exists and tries to delete and reimport.
	 * If exception is still thrown then fail("Could not import test product") is called;
	 * 
	 * @param productCd Code of product being imported. It is used only for product deletion if it already exists.
	 * @param productVersion Version of product being imported. It is used only for product deletion if it already exists.
	 * @param productDir Path to zipped product file to import
	 */
	public void importTestProduct(String productCd, double productVersion, String productDir) {
		Response response = null;

		try {
			response = getProductManager().importProduct(productDir);
		} catch (IllegalStateException e) {
			log.warn(e);
		}

		if(response == null || response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			try {
				getProductManager().deleteProduct(productCd, productVersion);
				response = getProductManager().importProduct(productDir);
			} catch (IllegalStateException e) {
				log.warn(e);
			}
		}

		if(response == null || response.getStatus() != Response.Status.CREATED.getStatusCode()) {
			fail("Could not import test product");
		}
	}
	
	public void importTestProduct(ProductImportInfo productInfo) {
		importTestProduct(productInfo.getProductCd(), productInfo.getProductVersion(), productInfo.getProductPath());
	}
	
    /**
     * @return Manager that can be used to manage product directly
     */
    public RestfulProductManager getProductManager() {
    	if (getConfiguration() == null) {
    		throw new IllegalStateException("Should be called after custom settings have been set");
    	}
    	
    	if (productManager == null) {
    		productManager = new RestfulProductManager(getConfiguration());
    	}
    	return productManager;
    }
	
	public RestfulComponentService getComponentService() {
    	if (getConfiguration() == null) {
    		throw new IllegalStateException("Should be called after custom settings have been set");
    	}
    	
    	if (componentService == null) {
    		componentService = new RestfulComponentService(getConfiguration());
    	}
    	return componentService;
    }
    
    public void waitForAjax(long time){
    	PageUtil.waitForAjax(getDriver(), new WebDriverWait(getDriver(), time), "wait for ajax");
    }
    
    public void waitForAjax(long time, String message){
    	PageUtil.waitForAjax(getDriver(), new WebDriverWait(getDriver(), time), message);
    }  
    
    public void setProductManager(RestfulProductManager productManager) {
		this.productManager = productManager;
	}

}
