package com.exigen.ipb.product.pages;

import com.exigen.ipb.product.pages.dialogs.ProductCopyPopUpPage;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.exigen.ipb.product.pages.dialogs.ProductNewRootAddPopUp;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.LandingPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;
import com.exigen.ipb.selenium.utils.ExtractTools;
import com.exigen.ipb.selenium.utils.ProductConsolidatedViewActions;

import java.util.Formatter;
import org.openqa.selenium.By;
import org.springframework.util.StringUtils;

public class ProductConsolidatedViewPage extends AbstractWebPage implements LandingPage {

	public static final String PRODUCT_DETAIL_FLOW_URL = "product-detail-flow&productCd=%s&productVersion=%s"; 

	private static final String ACTION_CONFIGURE_WORKSPACE = "configureWorkspace";
	private static final String ACTION_CONFIGURE_RULES = "configureRules";
	private static final String ACTION_CONFIGURE_RULES_BY_MARKER = "configureRulesByMarker";
	private static final String ACTION_CONFIGURE_ALL_RULES = "configureAllRules";
	private static final String ACTION_EXPORT = "export";
	private static final String ACTION_IMPORT_RULE_SET = "importRuleSet";
	private static final String ACTION_EXPORT_RULE_SET = "exportRuleSet";
	private static final String ACTION_VIEW_RULE_SETS = "viewRuleSets";
	private static final String ACTION_EXPORT_RESOURCES = "exportResources";
	private static final String ACTION_REPORTS = "reports";
	private static final String ACTION_ACTIVATE = "activate";
	private static final String ACTION_DEACTIVATE = "deactivate";
	private static final String ACTION_DEPLOY = "deploy";
	private static final String ACTION_UPDATE_PRODUCT_PROPERTIES = "updateProductProperties";
	private static final String ACTION_UPDATE_PRODUCT_ACTIONS = "updateProductActions";
	private static final String ACTION_UPDATE_PRODUCT_DATA = "updateProductData";
	private static final String ACTION_ALTERNATIVE_UPDATE_DATA = "alternateComponentView";
	
	public static final String ROOT_ENTITY_COLUMN_NAME = "entityConfigName";
	public static final String ROOT_ENTITY_COLUMN_TYPE = "column_rootEntityType";
	public static final String ROOT_ENTITY_COLUMN_VERSION = "column_entityVersion";
	public static final String ROOT_ENTITY_COLUMN_ENTRYPOINT = "column_entryPointInd";
	public static final String ROOT_ENTITY_CONFIGURE = "configureRootEntity";
	public static final String ROOT_ENTITY_ELIMINATE = "eliminateRootEntity";
	
	private static final String STATUS_ACTIVATED = "Activated";
	private static final String STATUS_DEACTIVATED = "Deactivated";
	private static final String STATUS_DEPLOYED = "Deployed";
	private static final String STATUS_UNDEPLOYED = "Undeployed";
	
	@FindBy(id="productActionForm:productCode")
	private WebElement txtFieldProductCodeHeader;
	
	@FindBy(id="productActionForm:productName")
	private WebElement txtFieldProductNameHeader;
	
	@FindBy(id="productActionForm:productStatus")
	private WebElement txtFieldProductStatusHeader;
	
	@FindBy(id="productActionForm:productStatusHistory")
	private WebElement linkStatusHistory;
	
	@FindBy(id="productActionForm:actionSelect")
	private WebElement optionActionSelect;
	
	@FindBy(id="productActionForm:go")
	private WebElement buttonGo;

	@FindBy(id="activitiesAlertPlaceholderTogglePanel_header")
	private WebElement panelBAMSection;
	
	@FindBy(id="activityCommandsForm:body_activityTable:tb")
	private WebElement activityTable;
	
	@FindBy(id="productForm:body_productRootEntitiesTable:tb")
	private WebElement rootEntityTable;
	
	@FindBy(id="productActionForm:rootSelect")
	private WebElement dropDownRootEntity;	
	
	@FindBy(id="productForm:body_productRootEntitiesTable:addNewRootEntity")
	private WebElement buttonAddNewRootEntity;

	@FindBy(id="topCancelLink")
	private WebElement buttonCancel;
	
	@FindBy(id="productForm:productVersions_header")
	private WebElement productVersionToggleControl;
	
	@FindBy(id="productForm:body_productVersionListTable:tb")
	private WebElement productVersionListTable;
	
	@FindBy(id="copyProductConfirmForm:copyProductConfirmOk")
	private WebElement productVersionConfirmationButton;
	
	@FindBy(id="productForm:body_productDetailsMainTable:tb")
	private WebElement productDetailsMainTable;
	
	@FindBy(id="productForm:changePropertiesBtn")
	private WebElement buttonChangeProperties;
	
	
	private String productCd;
	
	private double productVersion;
	
	public ProductConsolidatedViewPage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}
	
	public ProductConsolidatedViewPage(WebDriver driver, PageConfiguration conf, String productCd, double productVersion) {
		this(driver, conf);
		this.productCd = productCd;
		this.productVersion = productVersion;
	}
	
	public ProductSearchPage clickCancel() {
		buttonCancel.click();
		return create(ProductSearchPage.class);
	}
	
	public boolean isDisplayed() {
		try {
			return !txtFieldProductCodeHeader.equals(null);
		}
		catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public boolean isActivated() {
		return txtFieldProductStatusHeader.getText().equals(STATUS_ACTIVATED);
	}
	
	public ProductConsolidatedViewPage deployProduct() {
		Select select = new Select(optionActionSelect);
		select.selectByValue(ACTION_DEPLOY);
		buttonGo.click();
		return create(ProductConsolidatedViewPage.class);
	}
	
	public ProductConsolidatedViewPage activateProduct() {
		if(!isActivated()){	
			Select select = new Select(optionActionSelect);
			select.selectByValue(ACTION_ACTIVATE);
			buttonGo.click();
			return create(ProductConsolidatedViewPage.class);
		}
		return this;
	}
	
	public ProductConsolidatedViewPage deactivateProduct() {
		if(isActivated()){		
			Select select = new Select(optionActionSelect);
			select.selectByValue(ACTION_DEACTIVATE);
			buttonGo.click();
			return create(ProductConsolidatedViewPage.class);
		}
		return this;
	}
	
	public boolean linkStatusHistoryVisible() {
		return linkStatusHistory != null && linkStatusHistory.isDisplayed();
	}
	
	/**
	 * Checks if correct product is open
	 * @param productCode
	 * @return 
	 */
	public boolean existsProduct(String productCode){
		return (productCode + " v1.0").equals(txtFieldProductCodeHeader.getText());
	}
	
	/**
	 * Go to product properties update page
	 * @return 
	 */
	public ProductPropertiesPage updateProductProperties() {
		return navigateToActionPage(ACTION_UPDATE_PRODUCT_PROPERTIES, ProductPropertiesPage.class);
	}
	
	/**
	 * Go to product actions update page
	 * @return 
	 */
	public ProductActionsPage updateProductActions() {
		return navigateToActionPage(ACTION_UPDATE_PRODUCT_ACTIONS, ProductActionsPage.class);
	}
	
	/**
	 * Go to product alternative update data page.
	 * @return
	 */
	public AlternativeProductDataPage updateProductDataAlternative() {
		return navigateToActionPage(ACTION_ALTERNATIVE_UPDATE_DATA, AlternativeProductDataPage.class);
	}
	
	/**
	 * Go to product original update data page.
	 * @return
	 */
	public ProductDataPage updateProductData() {
		return navigateToActionPage(ACTION_UPDATE_PRODUCT_DATA, ProductDataPage.class);
	}
	
	/**
	 * Go to product original update data page.
	 * @return
	 */
	public ProductWorkspacePage configureWorkspace() {
		return navigateToActionPage(ACTION_CONFIGURE_WORKSPACE, ProductWorkspacePage.class);
	}
	
	/**
	 * Go to product rules configuration page.
	 * @return
	 */
	public ProductRulesConfigurationPage configureRules() {
		return navigateToActionPage(ACTION_CONFIGURE_RULES, ProductRulesConfigurationPage.class);
	}	
	
	private <T extends AbstractWebPage> T navigateToActionPage(String actionName, Class<T> pageClass) {
		Select select = new Select(optionActionSelect);
		select.selectByValue(actionName);

		buttonGo.click();
		
		return create(pageClass);
	}
	
	public List<String> getActionsDropDownAsText() {
		return ElementUtils.getOptions(new Select(optionActionSelect));
	}

	public ProductConsolidatedViewPage expandBAMSection() {
		panelBAMSection.click();
		
		return this;
	}
	
	public String getBAMActivityDescription(int Nr) {
		WebElement activity = activityTable.findElement(By.id("activityCommandsForm:body_activityTable:"+ Nr +":activityDescription"));

		return activity.getText();
	}
	
	public String getBAMActivityStatus(int Nr) {
		WebElement status = activityTable.findElement(By.id("activityCommandsForm:body_activityTable:"+ Nr +":activityStatus"));

		return status.getText();
	}
	
	public ProductNewRootAddPopUp clickAddNewRootEntity() {
		buttonAddNewRootEntity.click();

		return create(ProductNewRootAddPopUp.class);
	}
	
	public ProductConsolidatedViewPage addNewRootEntity(String newName) {
		clickAddNewRootEntity().setName(newName).save();

		return this;
	}	
	
	public List<String> getRootEntityColumn(String columnName) {		
		List<WebElement> rootEntityList = rootEntityTable.findElements(By.cssSelector("tr.rich-table-row"));
		List<String> rootEntityElements = new ArrayList<String>();	
		
		int i = 0;
		for (WebElement arr : rootEntityList) {
			WebElement rowName = arr.findElement(By.id("productForm:body_productRootEntitiesTable:"+ i +":"+columnName));
			rootEntityElements.add(rowName.getText());
			i++;
		}
		return rootEntityElements;
	}
	
	public ProductNewRootAddPopUp configureRootEntity(String rootEntity, String action) {
		List<WebElement> tableRows = rootEntityTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows) {
			WebElement td = table.findElement(By.id("productForm:body_productRootEntitiesTable:"+ i +":"+ROOT_ENTITY_COLUMN_NAME));			
			if (td.getText().equals(rootEntity)) {				
				table.findElement(By.id("productForm:body_productRootEntitiesTable:"+ i +":"+ action)).click();
				return create(ProductNewRootAddPopUp.class);
			}
			i++;
		}
		return null;
	}	
	
	public List<String> getRootEntityDropDown() {		
		List<WebElement> rootEntitydropDown = dropDownRootEntity.findElements(By.tagName("option"));
		List<String> rootEntityElements = new ArrayList<String>();	
		
		for (WebElement arr : rootEntitydropDown) {
			rootEntityElements.add(arr.getText());
		}
		return rootEntityElements;
	}
	
	public ProductConsolidatedViewPage selectRootEntity(String rootEntityName) {		
		List<WebElement> rootEntitydropDown = dropDownRootEntity.findElements(By.tagName("option"));
		
		for(WebElement arr : rootEntitydropDown) {
			if(arr.getText().equals(rootEntityName)) {
				arr.click();
				return this;
			}				
		}
		return null;		
	}
	
	/**
	 * toggles product versions tables' visibility
	 * @return 
	 */
	public ProductConsolidatedViewPage clickProductVersionsToggleControl() {
		productVersionToggleControl.click();
		return this;
	}

	/**
	 * confirms product version creation 
	 * @return 
	 */
	public ProductPropertiesPage confirmVersionCreation() {
		productVersionConfirmationButton.click();
		return create(ProductPropertiesPage.class);
	}
	
	/**
	 * clicks copy link for provided product version
	 * @param productVersion
	 * @return 
	 */
	public ProductCopyPopUpPage clickCopyProductVersion(double productVersion) {
		List<WebElement> rows = productVersionListTable.findElements(By.tagName("tr"));
		if(rows == null) {
			return null;
		}
		
		for(int i = 0; i < rows.size(); i++) {
			WebElement row = rows.get(i);
			double currentVersion = resolveProductVersionFromRow(row, i);
			if(currentVersion != productVersion) {
				continue;
			}
			
			WebElement createVersionLink = row.findElement(By.id("productForm:body_productVersionListTable:"+i+":action"));
			createVersionLink.click();
			return create(ProductCopyPopUpPage.class);
		}
		
		return null;
	}
	
	/**
	 * indicates that provided version exists in product versions table
	 * @param productVersion
	 * @return 
	 */
	public boolean productVersionExists(double productVersion) {
		return productRowCellMatches(productVersion);
	}

	/**
	 * indicates that provided version is deployed
	 * @param productVersion
	 * @return 
	 */
	public boolean productVersionIsDeployed(double productVersion) {
		return productRowCellMatches(
				productVersion, 
				"productForm:body_productVersionListTable:%1d:column_deploymentStatus", 
				STATUS_DEPLOYED
		);
	}

	
	/**
	 * indicates that provided version is active
	 * @param productVersion
	 * @return 
	 */
	public boolean productVersionIsActive(double productVersion) {
		return productRowCellMatches(
				productVersion, 
				"productForm:body_productVersionListTable:%1d:column_productStatus", 
				STATUS_ACTIVATED
		);
	}
	
	private double resolveProductVersionFromRow(WebElement productVersionsTableRow, int rowNumber) {
		WebElement versionLabelCell = productVersionsTableRow
				.findElement(By.id("productForm:body_productVersionListTable:"+rowNumber+":column_productVersion"))
				.findElement(By.tagName("a"));
		return Double.parseDouble(versionLabelCell.getText().split("\\s")[0]);
	}
	
	private boolean productRowCellMatches(double productVersion) {
		return productRowCellMatches(productVersion, null, null);
	}
	
	private boolean productRowCellMatches(double productVersion, String rowElementId, String value) {
		List<WebElement> rows = productVersionListTable.findElements(By.tagName("tr"));
		if(rows == null) {
			return false;
		}
		
		Formatter formatter = new Formatter();
		for(int i = 0; i < rows.size(); i++) {
			WebElement row = rows.get(i);
			double currentVersion = resolveProductVersionFromRow(row, i);
			if(productVersion != currentVersion) {
				continue;
			}
			
			if(value == null) {
				return true;
			}
			
			WebElement deploymentInfoCell = row.findElement(By.id(formatter.format(rowElementId, i).toString()));
			String deploymentInfo = deploymentInfoCell.getText();
			return value.equals(deploymentInfo);
		}
		
		return false;
	}
	
	public ProductPropertiesPage changeProductProperties() {
		buttonChangeProperties.click();
		return create(ProductPropertiesPage.class);
	}
	
	public ViewRuleSetsPage viewRuleSets() {
		return navigateToActionPage(ACTION_VIEW_RULE_SETS, ViewRuleSetsPage.class);
	}
	
	/**
	 * Navigates to this page.
	 * @param app  The application instance.
	 */
	public void navigate(Application app) {
		if (!StringUtils.hasText(productCd)) {
			throw new IllegalStateException("Product code must be set");
		}
		
		if (productVersion <= 0) {
			throw new IllegalStateException("Product version must be greater then zero");
		}
		
		app.navigateToFlow(String.format(PRODUCT_DETAIL_FLOW_URL, productCd, productVersion));
	}
	
	/*
	 * TODO: refactor this and other related to product properties module
	 */
	public String getPropertyProductCode(int row) {
		return productDetailsMainTable.findElement(By.id("productForm:body_productDetailsMainTable:"+row+":column_productCd")).getText();
	}
	public String getPropertyProductName(int row) {
		return productDetailsMainTable.findElement(By.id("productForm:body_productDetailsMainTable:"+row+":column_productName")).getText();
	}
	public String getPropertyTransactionDate(int row) {
		return productDetailsMainTable.findElement(By.id("productForm:body_productDetailsMainTable:"+row+":column_availableForTxDate")).getText();
	}
	public String getPropertyEffectiveDate(int row) {
		return productDetailsMainTable.findElement(By.id("productForm:body_productDetailsMainTable:"+row+":column_availabilityTerm_effective")).getText();
	}
	public String getPropertyBroadLOB(int row) {
		return productDetailsMainTable.findElement(By.id("productForm:body_productDetailsMainTable:"+row+":column_broadLOBCd")).getText();
	}
	public String getPropertyLOB(int row) {
		return productDetailsMainTable.findElement(By.id("productForm:body_productDetailsMainTable:"+row+":column_LOBCd")).getText();
	}
	public String getPropertyRenewalTypesAllowed(int row) {
		return productDetailsMainTable.findElement(By.id("productForm:body_productDetailsMainTable:"+row+":column_renewalTypesAllowed")).getText();
	}
	
	
	/**
	 * @return actual product code visible by user
	 */
	public String getProductCode() {
		return txtFieldProductCodeHeader.getText();
	}
	
	/**
	 * @return actual product name visible by user
	 */
	public String getProductName() {
		return txtFieldProductNameHeader.getText();
	}
	
	public String getProductCd() {
		return productCd;
	}
	
	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}
	
	public double getProductVersion() {
		return productVersion;
	}
	
	public void setProductVersion(double productVersion) {
		this.productVersion = productVersion;
	}
}
