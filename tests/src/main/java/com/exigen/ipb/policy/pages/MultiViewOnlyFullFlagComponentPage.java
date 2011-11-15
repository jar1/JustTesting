package com.exigen.ipb.policy.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.selenium.experimental.Tag;
import com.exigen.ipb.selenium.pages.PageConfiguration;

/**
 * A page for component of multiViewOnly product.
 * 
 * @author gzukas
 * @since 3.9
 */

// Visibility of unused elements is verified through java reflection API.
@SuppressWarnings("unused")
public class MultiViewOnlyFullFlagComponentPage extends DataGatheringPage {

	private static final String RISKITEM_PREFIX =
			"policyDataGatherForm:sedit_MultiViewOnlyComponent_proxiedComponents_RiskItem_";
	
	private static final String NOT_DISPLAYED = "notDisplayed";
	
	@FindBy(id=RISKITEM_PREFIX + "territoryCd")
	private WebElement territoryCd;
	
	@FindBy(id=RISKITEM_PREFIX + "itemName")
	private WebElement itemName;
	
	@FindBy(id=RISKITEM_PREFIX + "seqNo")
	private WebElement seqNo;
	
	@FindBy(id=RISKITEM_PREFIX + "additionalIntExistsInd")
	private WebElement additionalIntExistsInd;

	@FindBy(id=RISKITEM_PREFIX + "additionalFields_Test1_stringValue")
	private WebElement testOne;

	@FindBy(id=RISKITEM_PREFIX + "additionalFields_Test2_stringValue")
	private WebElement testTwo;

	@FindBy(id=RISKITEM_PREFIX + "additionalFields_Test3_stringValue")
	private WebElement testThree;

	@FindBy(id=RISKITEM_PREFIX + "additionalFields_Test4_stringValue")
	private WebElement testFour;
	
	@FindBy(id=RISKITEM_PREFIX + "additionalFields_Test5_stringValue")
	private WebElement testFive;

	@FindBy(id=RISKITEM_PREFIX + "additionalFields_Test6_stringValue")
	private WebElement testSix;

	@FindBy(id=RISKITEM_PREFIX + "additionalFields_Test7_stringValue")
	private WebElement testSeven;
	
	public MultiViewOnlyFullFlagComponentPage(WebDriver driver, PageConfiguration conf, String productCd) {
		super(driver, conf, productCd);
	}
	
}
