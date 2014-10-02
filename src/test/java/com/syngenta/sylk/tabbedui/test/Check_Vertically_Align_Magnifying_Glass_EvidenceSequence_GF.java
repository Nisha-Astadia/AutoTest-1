package com.syngenta.sylk.tabbedui.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.CreateLiteratureEvidenceDetailsForGeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.LiteratureSearchPage;
import com.syngenta.sylk.menu_add.pages.PopUpAddSequenceAccession;
import com.syngenta.sylk.menu_add.pages.PopUpAddTraitComponent;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

public class Check_Vertically_Align_Magnifying_Glass_EvidenceSequence_GF {

	private List<Object[]> testData = new ArrayList<Object[]>();

	private LandingPage lp;
	private HomePage homepage;
	private SearchSylkPage searchSylkpage;

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Check_Vertically_Align_Magnifying_Glass_Evidence_GF_Sequence.xlsx");
	}

	@AfterClass(alwaysRun = true)
	public void quitDriverIfLeftOpen() {

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}

	@BeforeMethod(alwaysRun = true)
	public void testSetUp() {
		this.lp = LandingPage.getLandingPage();
		this.homepage = this.lp.goToHomePage();
	}

	@DataProvider(name = "checkEvidenceMagnifyingGlass_vertically", parallel = false)
	public Iterator<Object[]> loadTestData() {

		return this.testData.iterator();
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp() {

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}
	@Test(enabled = true, description = "Check vertically align magnifying glass on literature evidence tab details and trait components for genetic feature sequence section", dataProvider = "checkEvidenceMagnifyingGlass_vertically", groups = {
			"rNaiConstructTabInTabbedView", "RNAI", "regression"})
	public void rNAiConstructTabInTabbedView(String testDescription,
			String row_num, HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();

		// step1

		reporter.reportPass("Login to SyLK");

		// Step 2
		this.searchSylkpage = this.homepage.goToGFRNAiTriggerROIpromoter();

		reporter.verifyEqual(this.searchSylkpage.getPageTitle(),
				PageTitles.search_sylk_page_title,
				"Select menu item 'GF/RNAi Triggers/ROI/Promoter' and open Search page.");
		// step 3
		this.searchSylkpage.selectAddedBy(columns.get("user"));

		this.searchSylkpage.selectType(columns.get("selectType"));

		this.searchSylkpage = this.searchSylkpage.clickSearch();

		int count = this.searchSylkpage.getTotalResultCount();
		if (count == 0) {
			reporter.assertThisAsFail("When searched for RNAi for this user="
					+ (columns.get("user") + " resulted in zero results"));
		} else {
			reporter.reportPass("When searched for RNAi for this user="
					+ (columns.get("user") + " displays " + count + " results"));
		}

		GeneticFeaturePage gfPage = (GeneticFeaturePage) this.searchSylkpage
				.clickAndOpenGFWithLiteratureEvidenceSequence();
		reporter.verifyEqual(
				gfPage.getPageTitle(),
				PageTitles.genetic_feature_page_title,
				"Genetic Feature Tabbed Page appears when clicked on one of the genetic feature displayed in the search results.");

		gfPage = gfPage.clickOnEvidenceSequenceTab();
		reporter.reportPass("Click on evidence tab open up the evidence tab sequence section.");

		int evidenceCount = gfPage.getEvidenceSequenceCountOnTab();
		// add only if the count == 1
		if (evidenceCount == 1) {
			LiteratureSearchPage litSearchPage = gfPage
					.selectAddEvidenceSequence(columns.get("evidence"));
			CreateLiteratureEvidenceDetailsForGeneticFeaturePage createLitPage = (CreateLiteratureEvidenceDetailsForGeneticFeaturePage) litSearchPage
					.searchThis(columns.get("search"));

			createLitPage.enterRationale(columns.get("rationale"));
			createLitPage.enterObservation(columns.get("observation"));
			PopUpAddSequenceAccession popupTrigger = createLitPage
					.clickOnAddSequence();
			createLitPage = popupTrigger.addSequenceName(columns
					.get("addSequence"));

			PopUpAddTraitComponent popupTriat = createLitPage
					.clickAddTraitComponent();
			createLitPage = (CreateLiteratureEvidenceDetailsForGeneticFeaturePage) popupTriat
					.addTrait(columns.get("trait"));

			gfPage = createLitPage.clickSave();
			gfPage.clickOnEvidenceSequenceTab();

			reporter.reportPass("Created a new evidence under sequence tab as the count for this selected Genetic Feature was one to be able to check vartical alignment of columns.");
		}

		boolean allIsGood = gfPage
				.checkIfMagnifyingGlassImageIsVerticallyAlligned_SequenceSection();

		reporter.assertTrue(allIsGood,
				"All evidence view magnifying classes should be alligned vertically.");

	}
}
