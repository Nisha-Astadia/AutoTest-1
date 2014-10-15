package com.syngenta.sylk.tabbedui.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.main.pages.BasePage;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.ViewLiteratureEvidenceDetailsPageSequence;
import com.syngenta.sylk.menu_find.pages.RNAiPage;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

public class Check_Edit_LeadInfo_And_RegulatoryCheck_Sequence_Tab {

	private LandingPage lp;
	private HomePage homepage;
	private GeneticFeaturePage gfPage;
	private SearchSylkPage searchSylkpage;
	private RNAiPage rnai;

	private List<Object[]> testData = new ArrayList<Object[]>();

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Check_Edit_LeadInfo_And_RegulatoryCheck_Sequence_Tab.xlsx");
	}

	@BeforeMethod(alwaysRun = true)
	public void testSetUp() {
		this.lp = LandingPage.getLandingPage();
		this.homepage = this.lp.goToHomePage();
	}

	@DataProvider(name = "getdata", parallel = false)
	public Iterator<Object[]> loadTestData() {
		return this.testData.iterator();
	}

	@AfterClass(alwaysRun = true)
	public void cleanUp() {

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}

	@Test(enabled = true, description = "Check Edit Lead Info Button Enabled from Lead Info tab (User is creator of the nominated GF to DB Lead)", dataProvider = "getdata", groups = {
			"CheckRNAiTabInGFTest", "RNAI", "regression"})
	public void editLeadInfoAndRegulatoryCheckTab(String testDescription,
			String row_num, HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();
		CommonLibrary common = new CommonLibrary();
		try {
			reporter.reportPass("Login to SyLK");
			String gfName = "selenium_GF1";
			String user = "Pillai, Nisha";
			BasePage page = common.searchAndSelectThisGF(this.homepage, user,
					gfName);
			if (page instanceof GeneticFeaturePage) {
				this.gfPage = (GeneticFeaturePage) page;
			}
			if (this.gfPage == null) {
				this.homepage = common.addANewGeneticFeatureProtein(
						this.homepage, columns, gfName);
				this.gfPage = this.homepage.clickNewGeneticFeatureLink(gfName);
			}
			String step1 = "Go to Find -> GF/RNAi Triggers/ROI/Promoter";
			String step2 = "1- Select your username from \"Added By\" drop down list."
					+ "2- Select \"Genetic Feature\" from \"Type\" drop down list."
					+ "3- Click on \"Search\" button.";
			String step3 = "Click on one of the genetic features displayed in the search results. Genetic Feature Page Tabbed view appears";
			reporter.reportPass(step1);
			reporter.reportPass(step2);
			reporter.reportPass(step3);

			int evdCount = this.gfPage.getEvidenceSequenceCountOnTab();
			if (evdCount == 0) {
				this.gfPage = this.gfPage.clickOnEvidenceSequenceTab();
				this.gfPage = this.gfPage.addEvidenceInSequenceSection(
						this.gfPage, columns);
			}

			int leadCount = this.gfPage.getLeadInfoCountOnTab();
			if (leadCount == 0) {
				this.gfPage = this.gfPage.addLeadInfoAndNominateDatabaseLead(
						this.gfPage, columns);
			}
			String step4 = "IF GF Sequence does not have evidence associated to it, and if it is not nominated as a DB Lead "
					+ "1- Add Evidence to the sequences and Curate the GF "
					+ "2- Add Lead Info (nominated as a Database Lead) "
					+ "Go to GF Page tabbed view";
			reporter.reportPass(step4);
			this.gfPage.clickOnEditLeadInfo();
			HashMap<String, String> data = this.gfPage
					.getAllFieldValuesLeadInfoTab(this.gfPage);
			System.out.println(data);
			String step5 = "Click on the Lead Info tab of one of the sequences, you will see an edit button, and that button should be enabled";

			reporter.reportPass(step5);

			HashMap<String, String> newVals = new HashMap<String, String>();
			newVals.put("leadtype", "Gene");
			newVals.put("leadname", "updated_leadname");
			newVals.put("leadsource", "Collaboration");
			newVals.put("countryoforigin", "INDIA");
			newVals.put("leadfunction", "updated lead function");
			this.gfPage.setAllFieldValuesLeadInfoTab(this.gfPage, newVals);
			this.gfPage.clickOnCancelLeadInfoTab();
			String step6 = "Modify some of the values including adding another \"Suggested Project Name\" and another \"Source Of Lead Function Info\" and Click Cancel";
			reporter.reportPass(step6);

			HashMap<String, String> blankVals = new HashMap<String, String>();
			blankVals.put("leadtype", "");
			blankVals.put("leadname", "");
			blankVals.put("leadsource", "");
			blankVals.put("countryoforigin", "");
			blankVals.put("leadfunction", "");
			this.gfPage.clickOnEditLeadInfo();
			this.gfPage.setAllFieldValuesLeadInfoTab(this.gfPage, blankVals);
			this.gfPage.clickOnSaveLeadInfoTab();
			String error = this.gfPage.getLeadNameErrorLeadInfoTab();
			reporter.verifyEqual(
					error,
					"Field is required",
					"Lead Name field does not show the error message 'Field is required' when left blank and saved.");
			error = null;
			error = this.gfPage.getLeadSourceErrorLeadInfoTab();
			reporter.verifyEqual(
					error,
					"Field is required",
					"Lead Source field does not show the error message 'Field is required' when left blank and saved.");
			String step7 = "Clear Lead Source fiels and Click Save, you see a \"Field is required\" message next to Lead Source field";
			reporter.reportPass(step7);
			error = null;
			error = this.gfPage.getLeadTypeErrorLeadInfoTab();
			reporter.verifyEqual(
					error,
					"Field is required",
					"Lead Type field does not show the error message 'Field is required' when left blank and saved.");
			step7 = "Clear Lead Type fiels and Click Save, you see a \"Field is required\" message next to Lead Type field";
			reporter.reportPass(step7);
			error = null;
			error = this.gfPage.getLeadFunctionErrorLeadInfoTab();
			reporter.verifyEqual(
					error,
					"Field is required",
					"Lead Type field does not show the error message 'Field is required' when left blank and saved.");
			step7 = "Clear Lead Function fiels and Click Save, you see a \"Field is required\" message next to Lead Function field";
			reporter.reportPass(step7);

			/*
			 * Update with new values
			 */
			// first check if this is still in edit mode. We will look for an
			// edit button.
			boolean editMode = this.gfPage.isThereAnEditButtonInLeadInfo();
			// if there is an edit button we will click on edit
			if (!editMode) {
				this.gfPage = this.gfPage.clickOnEditLeadInfo();
			}

			// this hashmap 'newvals' contains all update value
			this.gfPage.setAllFieldValuesLeadInfoTab(this.gfPage, newVals);

			String step8 = "Add a new Project name by clicking \"Add Suggested Project Name\" button and click on save button. The new project gets updated and saved.";
			this.gfPage = this.gfPage
					.addSuggestedProjectNameLeadInfo("Other Proj:Other Projects");

			this.gfPage.clickOnSaveLeadInfoTab();
			/*
			 * Verfify updated values after clicking save
			 */
			this.gfPage = this.gfPage.clickOnEditLeadInfo();
			HashMap<String, String> updateddata = this.gfPage
					.getAllFieldValuesLeadInfoTab(this.gfPage);
			String leadName = updateddata.get("leadname");
			reporter.verifyEqual(leadName, newVals.get("leadname"),
					"Lead Name updated with a new value after clicking save");

			// String leadType = updateddata.get("leadType");
			// reporter.verifyEqual(leadType, newVals.get("leadtype"),
			// "Lead Type updated with a new value after clicking save");

			// String leadSource = updateddata.get("leadSource");
			// reporter.verifyEqual(leadSource, newVals.get("leadsource"),
			// "Lead Source updated with a new value after clicking save");

			String countryoforigin = updateddata.get("countryoforigin");
			reporter.verifyEqual(countryoforigin,
					newVals.get("countryoforigin"),
					"Country Of Origin updated with a new value after clicking save");

			String leadfunction = updateddata.get("leadfunction");
			reporter.verifyEqual(leadfunction, newVals.get("leadfunction"),
					"Lead Function updated with a new value after clicking save");

			List<String> names = this.gfPage.getAllAddedProjectNames();
			boolean contains = names.contains("Other Proj:Other Projects");
			reporter.verifyTrue(contains, step8);
			if (contains) {
				this.gfPage = this.gfPage
						.deleteThisProjectNameLeadInfo("Other Proj:Other Projects");
			}

			this.gfPage.clickOnDeleteLeadInfoTab();
			new CommonLibrary().slowDown();
			this.gfPage.clickOnEvidenceSequenceTab();
			ViewLiteratureEvidenceDetailsPageSequence lit = this.gfPage
					.clickviewLiteratureEvidenceSequence();
			this.gfPage = lit.clickOnDelete();

		} catch (Exception e) {
			e.printStackTrace();
			throw new SyngentaException("Test Failed:"
					+ new CommonLibrary().getStackTrace(e));
		} finally {
			reporter.assertAll();
		}
	}

}
