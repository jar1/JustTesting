package com.exigen.ipb.product.pages;

import java.util.ArrayList;
import java.util.List;

import com.exigen.ipb.product.pages.dialogs.AssignActionDialog;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.support.ByAll;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object that represents 'Update product actions' page.
 *
 * @author ggrazevicius
 * @author mulevicius
 * @author jdaskevicius
 * @author gzukas
 * @since 3.9
 */
public class ProductActionsPage extends AbstractWebPage {
	
	private static final String ACTION_STATE_TITLE_FORMAT =
			"iconproductUpdateForm:actionStateTransition__%s_%s";
	
	private static final String ACTION_TABLE_FORMAT =
			"productUpdateForm:renewalPolicyTable__%s_%s";
	
	private static final String ACTION_STATE_SELECT_FORMAT =
			ACTION_TABLE_FORMAT + ":%d:assignActionsLink";
	
	/**
	 * Transfer type
	 */
	public enum TxType {
		newBusiness, endorsement, renewal
	}
	
	public enum ProcessState {
		quote(QuoteState.values()),
		policy(PolicyState.values());

		private ActionState[] actionStates;
		
		private ProcessState(ActionState[] actionStates) {
			this.actionStates = actionStates;
		}
		
		public ActionState[] getActionStates() {
			return actionStates;
		}
		
		public ActionState actionOf(String name) {
			for (ActionState actionState : actionStates) {
				if (actionState.getName().equals(name)) {
					return actionState;
				}
			}
			return null;
		}
	}
	
	public interface ActionState {

		/**
		 * @return   Ordinal value of action state.
		 */
		int ordinal();
		
		/**
		 * Returns a name of action state.
		 * @return
		 */
		String getName();
	}
	
	public enum QuoteState implements ActionState {
		bound("Bound"),
		companyDeclined("Company Declined"),
		customerDeclined("Customer Declined"),
		dataGather("Data Gather"),
		premiumCalculated("Premium Calculated"),
		proposed("Proposed"),
		quoteExpired("Quote Expired"),
		quoteSuspended("Quote Suspended");

		private String name;

		private QuoteState(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
	
	public enum PolicyState implements ActionState {
		cancelled("Canceled"),
		expired("Expired"),
		issued("Issued"),
		pendingCompletion("Pending Completion");

		private String name;
		
		private PolicyState(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}		
	}
		
	@FindBy(id="topSaveLink")
	private WebElement buttonTopSave;
	
	@FindBy(id="topCancelLink")
	private WebElement buttonTopCancel;
	
	@FindBy(id="productUpdateForm")
	private WebElement productUpdateForm;
	
	public ProductActionsPage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}

	/**
	 * Opens a dialog for assigning actions for specific state and transition.
	 * 
	 * @param process  The process.
	 * @param type  The type.
	 * @param state  The state.
	 * @return  Instance of dialog.
	 */
	public AssignActionDialog editActions(ProcessState process, TxType type, ActionState state) {
		expandActionsPanel(process, type);
		
		String selectId = String.format(ACTION_STATE_SELECT_FORMAT, process, type, state.ordinal());
		productUpdateForm.findElement(By.id(selectId)).click();
		
		return create(AssignActionDialog.class, this);
	}
	
	public List<ActionState> getActionStates(ProcessState process, TxType type) {
		List<ActionState> states = new ArrayList<ActionState>();

		expandActionsPanel(process, type);

		String tableId = String.format(ACTION_TABLE_FORMAT, process, type);
		WebElement stateTable = productUpdateForm.findElement(By.id(tableId));
		List<WebElement> stateRows = stateTable.findElements(
				new ByAll(By.className("oddRow"), By.className("evenRow")));
		
		for (WebElement stateRow : stateRows) {
			ActionState actionState = process.actionOf(stateRow.getText());
			if (actionState != null) {
				states.add(actionState);
			}
		}
		
		return states;
	}
	
	private void expandActionsPanel(ProcessState process, TxType type) {
		String titleId = String.format(ACTION_STATE_TITLE_FORMAT, process, type);
		productUpdateForm.findElement(By.id(titleId)).click();
	}

	/**
	 * Saves product actions
	 * @return Product consolidated view
	 */
	public ProductConsolidatedViewPage clickSave() {
		buttonTopSave.click();
		return create(ProductConsolidatedViewPage.class);
	}
	
	/**
	 * Cancels product action editing
	 * @return Product consolidated view
	 */
	public ProductConsolidatedViewPage cancel() {
		buttonTopCancel.click();
		return create(ProductConsolidatedViewPage.class);
	}
}
