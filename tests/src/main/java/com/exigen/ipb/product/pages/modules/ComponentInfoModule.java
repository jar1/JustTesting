package com.exigen.ipb.product.pages.modules;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.product.pages.dialogs.AppliedRuleDialog;
import com.exigen.ipb.product.pages.dialogs.ComponentInstanceLabelPopUp;
import com.exigen.ipb.product.pages.dialogs.ComponentInstanceSummaryPopUp;
import com.exigen.ipb.selenium.pages.AbstractParentedWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;

/**
 * A module that represents a part of alternative product data page that is
 * provides component information.
 * 
 * @author gzukas
 * @since 3.9
 */
public class ComponentInfoModule extends AbstractParentedWebPage<AlternativeProductDataPage> {

	@FindBy(id="componentInfoForm:componentName")
	private WebElement componentInfoName;
	
	@FindBy(id="componentInfoForm:componentVersion")
	private WebElement componentInfoVersion;
	
	@FindBy(id="componentInfoForm:componentLabel")
	private WebElement componentInfoLabel;
	
	@FindBy(id="componentInfoForm:componentLabeltempValue")
	private WebElement componentInfoLabelValue;
	
	@FindBy(id="componentInfoForm:componentMinInstances")
	private WebElement componentInfoMin;
	
	@FindBy(id="componentInfoForm:componentMinInstancestempValue")
	private WebElement componentInfoMinValue;
	
	@FindBy(id="componentInfoForm:componentMinInstancesok")
	private WebElement componentInfoMinConfirm;
	
	@FindBy(id="componentInfoForm:componentMaxInstances")
	private WebElement componentInfoMax;
	
	@FindBy(id="componentInfoForm:componentMaxInstancestempValue")
	private WebElement componentInfoMaxValue;
	
	@FindBy(id="componentInfoForm:componentMaxInstancesok")
	private WebElement componentInfoMaxConfirm;
	
	@FindBy(id="componentInfoForm:summaryConfig")
	private WebElement instanceSummary;
	
	@FindBy(id="componentInfoForm:instanceLabel")
	private WebElement instanceLabel;
	
	@FindBy(id="componentInfoForm:optionalQuestion")
	private WebElement optionalQuestion;
	
	@FindBy(id="componentInfoForm:optionalQuestiontempValue")
	private WebElement optionalQuestionTempValue;
	
	@FindBy(id="componentInfoForm:defaultOptionalQuestionAnswer")
	private WebElement defaultOptionalQuestionAnswer;
	
	@FindBy(id="componentInfoForm:optionalQuestionLabel")
	private WebElement optionalQuestionLabel;
	
	@FindBy(id="componentInfoForm:componentLabelRenderedInConsolidatedView")
	private WebElement showInConsolidatedViewCheckbox;
	
	@FindBy(id="componentInfoForm:componentLabelRendered")
	private WebElement labelIsRenderedCheckbox;
	
	@FindBy(id="componentInfoForm:hiddenAddButtonInd")
	private WebElement addButtonHiddenCheckbox;
	
	@FindBy(id="componentInfoForm:hiddenRemoveButtonInd")
	private WebElement removeButtonHiddenCheckbox;
	
	@FindBy(id="componentInfoForm:appliedRuleConfig")
	private WebElement appliedRuleConfig;
	
	
	public ComponentInfoModule(WebDriver driver, PageConfiguration conf, AlternativeProductDataPage parentPage) {
		super(driver, conf, parentPage);
	}
	
	/**
	 * @return  Selected component's name from component info panel.
	 */
	public String getComponentName() {
		return componentInfoName.getText();
	}
	
	/**
	 * @return  Selected component's version from component info panel.
	 */
	public String getComponentVersion() {
		return componentInfoVersion.getText();
	}
	
	/**
	 * @return  Selected component's label from component info panel.
	 */
	public String getComponentLabel() {
		return componentInfoLabel.getText();
	}

	/**
	 * @param label  A text to set as components label. Any previous text in place will be cleared.
	 */
	public ComponentInfoModule setComponentLabel(String label) {
		componentInfoLabel.click();
		
		componentInfoLabelValue.clear();
		componentInfoLabelValue.sendKeys(label, Keys.RETURN);
		
		return this;
	}
	
	/**
	 * @return  Selected component's optional question from component info panel.
	 */
	public String getOptionalQuestion() {
		return optionalQuestion.getText();
	}

	public String getOptionalQuestionLabel() {
		return optionalQuestionLabel.getText();
	}

	/**
	 * @param label  A text to set as optional question. Any previous text in place will be cleared.
	 */
	public ComponentInfoModule setOptionalQuestion(String label) {
		optionalQuestion.click();
		
		optionalQuestionTempValue.clear();
		optionalQuestionTempValue.sendKeys(label, Keys.RETURN);
		
		return this;
	}
	
	/**
	 * Gets default optional question answer
	 * @return
	 */
	public String getDefaultOptionalQuestionAnswer() {
		Select select = new Select(defaultOptionalQuestionAnswer);
		
		return select.getFirstSelectedOption().getText();
	}
	
	/**
	 * Sets specified answer for optional question as default
	 * @param answer
	 * @return
	 */
	public ComponentInfoModule setDefaultOptionalQuestionAnswer(String answer) {
		Select select = new Select(defaultOptionalQuestionAnswer);
		
		select.selectByVisibleText(answer);
		
		return this;
	}
	
	public ComponentInstanceLabelPopUp configureInstanceLabel() {
		instanceLabel.click();
		return create(ComponentInstanceLabelPopUp.class);
	}
	
	public AppliedRuleDialog configureAppliedRule() {
		appliedRuleConfig.click();
		return create(AppliedRuleDialog.class, parent());
	}

	public ComponentInstanceSummaryPopUp configureInstanceSummary() {
		instanceSummary.click();
		return create(ComponentInstanceSummaryPopUp.class);
	}
	
	public boolean getShowInConsolidatedView() {
		return showInConsolidatedViewCheckbox.isSelected();
	}
	
	public boolean getLabelIsRendered() {
		return labelIsRenderedCheckbox.isSelected();
	}
	
	public boolean getAddButtonHidden() {
		return addButtonHiddenCheckbox.isSelected();
	}
	
	public boolean getRemoveButtonHidden() {
		return removeButtonHiddenCheckbox.isSelected();
	}
	
	public ComponentInfoModule setAddButtonIsHiddenTo(boolean set) {
		ElementUtils.setSelected(addButtonHiddenCheckbox, set);
		return this;
	}
	
	public ComponentInfoModule setRemoveButtonIsHiddenTo(boolean set) {
		ElementUtils.setSelected(removeButtonHiddenCheckbox, set);
		return this;
	}
	
	public ComponentInfoModule setLabelIsRenderedTo(boolean set) {
		ElementUtils.setSelected(labelIsRenderedCheckbox, set);
		return this;
	}
	
	public ComponentInfoModule setShowInConsolidatedViewTo(boolean set) {
		ElementUtils.setSelected(showInConsolidatedViewCheckbox, set);
		return this;
	}
	
	/**
	 * @param instances  A number to set as components max instances. Any previous text in place will be cleared.
	 */
	public ComponentInfoModule setComponentsMaxInstances(int instances) {
		componentInfoMax.click();
		componentInfoMaxValue.clear();
		componentInfoMaxValue.sendKeys(Integer.toString(instances), Keys.RETURN);
		return this;
	}
	
	/**
	 * @param instances  A number to set as components min instances. Any previous text in place will be cleared.
	 */
	public ComponentInfoModule setComponentsMinInstances(int instances) {
		componentInfoMin.click();
		componentInfoMinValue.clear();
		componentInfoMinValue.sendKeys(Integer.toString(instances), Keys.RETURN);
		return this;
	}
	
	/**
	 * @return  Selected component's min instances from component info panel.
	 */
	public String getComponentMinInstances() {
		return componentInfoMin.getText();
	}
	
	/**
	 * @return Selected component's max instances from component info panel.
	 */
	public String getComponentMaxInstances() {
		return componentInfoMax.getText();
	}

}
