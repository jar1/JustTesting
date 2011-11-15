package com.exigen.ipb.product.pages;

/**
 * @author mulevicius
 * @author jdaskevicius
 * 
 * @since 3.9
 */

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.exigen.ipb.product.domain.ProductStatus;
import com.exigen.ipb.product.pages.dialogs.ProductCopyPopUpPage;
import com.exigen.ipb.product.pages.dialogs.ProductExpirationDialog;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.experimental.Tag;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.LandingPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;
import com.exigen.ipb.selenium.utils.LOB;
import com.exigen.ipb.selenium.utils.ProductType;
import com.google.common.base.Predicate;

public class ProductSearchPage extends AbstractWebPage implements LandingPage {

	public final static String groupFields   = "fields";
	public final static String groupButtons  = "buttons";
	
	//private static final String SEARCH_PAGE_CRITERIA_FIELDS = "searchForm:productSearchCriteria_%s";
	private static final String PRODUCT_SEARCH_FLOW = "product-main-flow";
	
	private static final String EXPIRE_LINK_FORMAT = "searchForm:body_foundProducts:%d:expire";
	
	@Tag(groupFields)
	@FindBy(id="searchForm:productSearchCriteria_productName")
	private WebElement txtFieldProductNameSearchCriteria;
	
	@Tag(groupFields)
	@FindBy(id="searchForm:productSearchCriteria_productCd")
	private WebElement txtFieldProductCodeSearchCriteria;	
	
	@Tag(groupFields)
	@FindBy(id="searchForm:productSearchCriteria_LOBCd")
	private WebElement dropDownLineOfBusinessSearchCriteria;
	
	@Tag(groupFields)
	@FindBy(id="searchForm:productSearchCriteria_productStatus")
	private WebElement dropDownStatusSearchCriteria;
	
	@Tag(groupFields)
	@FindBy(id="searchForm:productSearchCriteria_rootEntityType")
	private WebElement dropDownProductTypeSearchCriteria;	
	
	@Tag(groupButtons)
	@FindBy(id="searchForm:search")
	private WebElement buttonSearch;
	
	@Tag(groupButtons)
	@FindBy(id="searchForm:clear")
	private WebElement buttonClear;
	
	@Tag(groupButtons)
	@FindBy(id="searchForm:addbtn")
	private WebElement buttonAddNewProduct;
	
	@Tag(groupButtons)
	@FindBy(id="searchForm:deleteBtn")
	private WebElement buttonDeleteProducts;
	
	@Tag(groupButtons)
	@FindBy(id="searchForm:importBtn")
	private WebElement buttonImport;
	
	@Tag(groupButtons)
	@FindBy(id="searchForm:importResourcesBtn")
	private WebElement buttonImportResources;
	
	@Tag(groupButtons)
	@FindBy(id="searchForm:cleanupBlocks")
	private WebElement buttonCleanupBlocks;	
	
	@Tag(groupButtons)
	@FindBy(id="searchForm:usageReport")
	private WebElement buttonComponentUsage;

	@FindBy(id="searchForm")
	private WebElement productSearchForm;
	
	@FindBy(id="searchForm:body_foundProducts:tb")
	private WebElement tableProductSearchResult;
	
	@FindBy(id="productTypeSelectForm:selectedNewProductType")
	private WebElement dropDownProductType;
	
	@FindBy(id="productTypeSelectForm:productTypeSelectFormSubmit")
	private WebElement buttonCreateProductType;
	
	@FindBy(id="importProductForm:fileupload")
	private WebElement fileSelection;
	
	@FindBy(id="importProductForm:importProductOk")
	private WebElement productImportOK;


	public ProductSearchPage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}	
	
	public void navigate(Application app) {
		app.navigateToFlow(PRODUCT_SEARCH_FLOW);
	}
	
	public boolean searchForProduct(String productCode) {
		ElementUtils.setInputValue(txtFieldProductCodeSearchCriteria, productCode);
		buttonSearch.click();
		
		try {
			return (!tableProductSearchResult.equals(null)) && (tableProductSearchResult.getText().contains(productCode));
		} catch (NoSuchElementException e) {
			return false;
		}
	}	

	public ProductConsolidatedViewPage searchAndOpenProduct(String productCode) {
		ElementUtils.setInputValue(txtFieldProductCodeSearchCriteria, productCode);
		buttonSearch.click();
		
		try {
			if(!tableProductSearchResult.equals(null) && (tableProductSearchResult.getText().contains(productCode))) {
				WebElement href = tableProductSearchResult.findElement(By.id("searchForm:body_foundProducts:0:updateProduct"));
				href.click();
				return create(ProductConsolidatedViewPage.class);
			}
		} catch (NoSuchElementException e) {
			return null;
		}
		return null;
	}
	
	public ProductSearchPage enterProductNameCriteria(String productName) {
		ElementUtils.setInputValue(txtFieldProductNameSearchCriteria, productName);
		return this;
	}
	
	public ProductSearchPage enterProductCodeCriteria(String productCode) {
		ElementUtils.setInputValue(txtFieldProductCodeSearchCriteria, productCode);
		return this;
	}
	
	public ProductSearchPage selectLineOfBusinessCriteria(LOB lob) {
		new Select(dropDownLineOfBusinessSearchCriteria).selectByVisibleText(lob.toString());
		return this;
	}
	
	public ProductSearchPage selectProductTypeCriteria(ProductType productType) {
		new Select(dropDownProductTypeSearchCriteria).selectByValue(productType.toString());
		return this;
	}	
	
	public ProductSearchPage selectStatusCriteria(String status) {
		new Select(dropDownStatusSearchCriteria).selectByVisibleText(status);
		return this;
	}
	
	public ProductSearchPage clickSearch() {
		buttonSearch.click();		
		return this;
	}	
	
	public ProductSearchPage selectProduct(String productCode) {
		List<WebElement> tableRows = tableProductSearchResult.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows) {
			WebElement td = table.findElement(By.id("searchForm:body_foundProducts:"+ i +":column_productCd"));			
			if (td.getText().equals(productCode)) {
				table.findElement(By.id("searchForm:body_foundProducts:"+ i +":productSelect")).click();
				return this;
			}
			i++;
		}
		return null;
	}	
	
	public ProductPropertiesPage createProduct(ProductType productType) {
		buttonAddNewProduct.click();	
		new Select(dropDownProductType).selectByValue(productType.toString());
		buttonCreateProductType.click();
		return create(ProductPropertiesPage.class);
	}
	
	public ProductCopyPopUpPage copyProduct(String productCode) {
		List<WebElement> tableRows =
				tableProductSearchResult.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows) {
			WebElement td = table.findElement(By.id("searchForm:body_foundProducts:"+ i +":column_productCd"));			
			if (td.getText().equals(productCode)) {
				table.findElement(By.id("searchForm:body_foundProducts:"+ i +":action")).click();
				return create(ProductCopyPopUpPage.class);
			}
			i++;
		}
		return null;
	}
	
	public ProductExpirationDialog clickExpire(int resultIndex) {
		String expireId = String.format(EXPIRE_LINK_FORMAT, resultIndex);
		tableProductSearchResult.findElement(By.id(expireId)).click();
		
		return create(ProductExpirationDialog.class);
	}
	
	public ProductsDeletePage deleteProduct() {
		buttonDeleteProducts.click();
		
		return create(ProductsDeletePage.class);
	}
	
	public ProductsDeletePage deleteProduct(String productCode) {
		selectProduct(productCode);
		buttonDeleteProducts.click();
		
		return create(ProductsDeletePage.class);
	}
	
	public boolean isSearchEmpty() {
		try {
			productSearchForm.findElement(By.id("searchForm:messagesPanel"));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}		
	}
	
	public List<String> getSearchResultCodes() {
		List<String> foundList = new ArrayList<String>();
				
		try {
			tableProductSearchResult.findElements(By.cssSelector("tr.rich-table-row"));
		}
		catch (NoSuchElementException e){
			foundList.clear();
			return foundList;
		}
		
		List<WebElement> tableRows = tableProductSearchResult.findElements(By.cssSelector("tr.rich-table-row"));
		int i = 0;
		for (WebElement table : tableRows) {
			foundList.add(table.findElement(By.id("searchForm:body_foundProducts:"+ i +":column_productCd")).getText());
			i++;
		}
		return foundList;
	}
	
	public void openImportDialog() {
		buttonImport.click();
		
		this.waitUntil(new Predicate<WebDriver>() {
			public boolean apply(WebDriver t) {
				return fileSelection.isDisplayed() && fileSelection.isEnabled();
			}			
		});
	}
	
	public void sendProductPath(String path) {
		fileSelection.sendKeys(path);
	}
	
	public void doImport() {
		productImportOK.click();
	}
}
