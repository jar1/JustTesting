package com.exigen.ipb.product;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Test;

import com.exigen.ipb.product.pages.ProductActionsPage.ActionState;
import com.exigen.ipb.product.pages.ProductActionsPage.ProcessState;
import com.exigen.ipb.product.pages.ProductActionsPage.PolicyState;
import com.exigen.ipb.product.pages.ProductActionsPage.QuoteState;
import com.exigen.ipb.product.pages.ProductActionsPage.TxType;
import com.exigen.ipb.product.pages.dialogs.AssignActionDialog;
import com.exigen.ipb.product.pages.ProductActionsPage;
import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import com.exigen.ipb.selenium.utils.ProductAction;


/**
 * @category FitNesse
 * 
 * Covers: EisTestCases.TestSuite.ProductSuite.ProductStatesUiConf
 *         EisTestCases.TestSuite.ProductSuite.ProductActionsTabUiConf
 * 
 * @author gzukas
 * @since 3.9
 */
public class ProductActionsFitnesseWebTests extends AbstractProductSeleniumTests {

	private final static String PRODUCT_DIR = "target/test-classes/products/withPolicyRoot.zip";	
	private final static String PRODUCT_CD = "seleniumProduct";	
	private final static Double PRODUCT_VERSION = 1.0D;

	private ProductConsolidatedViewPage productConsolidatedViewPage;
	
	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		productConsolidatedViewPage = navigateToProductView(PRODUCT_CD, 1.0);
	}
	
	/**
	 * Tests whether correct actions are assignable to dataGather state
	 * for 'New Business' transition.
	 */
	@Test
	public void dataGatherStateShouldHaveCorrectActions() {
		AssignActionDialog dialog = productConsolidatedViewPage.updateProductActions()
			.editActions(ProcessState.quote, TxType.newBusiness, QuoteState.dataGather);

		List<ProductAction> actions = dialog.getActions();
		
		List<ProductAction> expectedActions = Arrays.asList(
				ProductAction.bind,
				ProductAction.pendedEndorsementChange,
				ProductAction.CommissionOverride,
				ProductAction.policyVersionCompare,
				ProductAction.copyQuote,
				ProductAction.dataGather,
				ProductAction.declineByCompanyQuote,
				ProductAction.declineByCustomerQuote,
				ProductAction.deletePendingTransaction,
				ProductAction.quoteDocGen,
				ProductAction.quoteInquiry,
				ProductAction.issue,
				ProductAction.proposalPreview,
				ProductAction.propose,
				ProductAction.quickQuote,
				ProductAction.quoteVersionHistory,
				ProductAction.removeSuspendQuote,
				ProductAction.suspendQuote,
				ProductAction.quoteRulesOverrideUpdate
			);
		
		assertTrue("Some expected actions are missing", actions.containsAll(expectedActions));
	}
	
	/**
	 * Tests whether correct actions are assignable to issued state
	 * for 'Mid Term Endorsement' transition.
	 */
	@Test
	public void issuedStateShouldHaveCorrectActions() {
		List<ProductAction> actions = productConsolidatedViewPage.updateProductActions()
				.editActions(ProcessState.policy, TxType.endorsement, PolicyState.issued)
				.getActions();
			
		List<ProductAction> expectedActions = Arrays.asList(
				ProductAction.cancelNotice,
				ProductAction.cancel,
				ProductAction.changeBrokerRequest,
				ProductAction.policyCopy,
				ProductAction.copyPolicyProduct,
				ProductAction.doNotRenew,
				ProductAction.endorse,
				ProductAction.renewalExtension,
				ProductAction.policyDocGen,
				ProductAction.policyInquiry,
				ProductAction.manualRenew,
				ProductAction.reinstate,
				ProductAction.deleteCancelNotice,
				ProductAction.removeDoNotRenew,
				ProductAction.removeManualRenew,
				ProductAction.renew,
				ProductAction.addFromCancel,
				ProductAction.retroEndorse,
				ProductAction.rollOn,
				ProductAction.policySpin,
				ProductAction.policySplit,
				ProductAction.nonPremiumBearingEndorsement,
				ProductAction.policyRulesOverrideUpdate
			);
		
		assertTrue("Some expected actions are missing", actions.containsAll(expectedActions));
	}
	
	/**
	 * Tests whether quotation process state transitions have correct states.
	 */
	@Test
	public void quoteShouldHaveCorrectStates() {
		List<? extends ActionState> expectedStates = Arrays.asList(
				QuoteState.bound,
				QuoteState.companyDeclined,
				QuoteState.customerDeclined,
				QuoteState.dataGather,
				QuoteState.premiumCalculated,
				QuoteState.proposed,
				QuoteState.quoteExpired,
				QuoteState.quoteSuspended);
		
		ProductActionsPage actionsPage = productConsolidatedViewPage.updateProductActions();
		
		List<ActionState> newBusinessStates = actionsPage.getActionStates(
				ProcessState.quote, TxType.newBusiness);
		
		List<ActionState> endorsementStates = actionsPage.getActionStates(
				ProcessState.quote, TxType.endorsement);
		
		List<ActionState> renewalStates = actionsPage.getActionStates(
				ProcessState.quote, TxType.renewal);
		
		assertTrue("Some new business states are missing",
				newBusinessStates.containsAll(expectedStates));
		
		assertTrue("Some mid term endorsement states are missing",
				endorsementStates.containsAll(expectedStates));
		
		assertTrue("Some renewal states are missing",
				renewalStates.containsAll(expectedStates));
	}	
	
	@After
	public void tearDown() {
		getProductManager().deleteProduct(PRODUCT_CD, PRODUCT_VERSION);
	}
	
	@Override
	public Application setUpApplication() {
		return new AdminApplication(getDriver(), getConfiguration());
	}
}
