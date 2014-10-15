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
import com.syngenta.sylk.main.pages.BasePage;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.CreateLiteratureEvidenceDetailsForGeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.LiteratureSearchPage;
import com.syngenta.sylk.menu_add.pages.PopUpAddSequenceAccession;
import com.syngenta.sylk.menu_add.pages.PopUpAddTraitComponent;
import com.syngenta.sylk.menu_add.pages.ViewLiteratureEvidenceDetailsPageSequence;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

public class Check_DeleteThisSequenceButton_Disabled_In_DetailTab_SequenceSec_GF {

	private LandingPage lp;
	private HomePage homepage;
	private SearchSylkPage searchSylkpage;
	private GeneticFeaturePage gfPage;

	private List<Object[]> testData = new ArrayList<Object[]>();

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Check_DeleteThisSequenceButton_DetailTab_SeqSec.xlsx");
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

	@DataProvider(name = "testData", parallel = false)
	public Iterator<Object[]> loadTestData() {

		return this.testData.iterator();
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp() {

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}
	@Test(enabled = true, description = "Check Delete Button disabled in tabbed view GF (GF with Evidence cannot be deleted) ", dataProvider = "testData", groups = {
			"Check_DeleteThisSequenceButton_disabled_In_DetailTab_SequenceSec_GF",
			"tabbedui", "Genetic Feature", "regression"})
	public void gfDeleteButtonDisabledinTabbedView(String testDescription,
			String row_num, HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();
		CommonLibrary common = new CommonLibrary();
		// step1

		reporter.reportPass("Login to SyLK");

		String gfName = "Selenium_GF2";
		String user = "Pillai, Nisha";

		BasePage page = common.searchAndSelectThisGF(this.homepage, user,
				gfName);
		if (page instanceof GeneticFeaturePage) {
			this.gfPage = (GeneticFeaturePage) page;
		}
		if (this.gfPage == null) {
			this.homepage = common.addANewGeneticFeature(this.homepage,
					columns, columns.get("gfName"));
			this.gfPage = this.homepage.clickNewGeneticFeatureLink(columns
					.get("gfName"));
		}
		String step1 = "Go to Find -> GF/RNAi Triggers/ROI/Promoter";
		String step2 = "1- Select your username from \"Added By\" drop down list."
				+ "2- Select \"Genetic Feature\" from \"Type\" drop down list."
				+ "3- Click on \"Search\" button.";
		reporter.reportPass(step1);
		reporter.reportPass(step2);

		reporter.verifyEqual(
				this.gfPage.getPageTitle(),
				PageTitles.genetic_feature_page_title,
				" click on one of the displayed GF on search result page opens 'Genetic Feature Page' Tabbed view .");

		int evidenceCount = this.gfPage.getEvidenceSequenceCountOnTab();
		// add only if the count == 1
		if (evidenceCount == 0) {
			this.gfPage.clickOnEvidenceSequenceTab();
			LiteratureSearchPage litSearchPage = this.gfPage
					.selectAddEvidenceSequence(columns.get("evidence"));
			CreateLiteratureEvidenceDetailsForGeneticFeaturePage createLitPage = (CreateLiteratureEvidenceDetailsForGeneticFeaturePage) litSearchPage
					.searchThis(columns.get("search"));

			createLitPage.enterRationale(columns.get("rationale"));
			createLitPage.enterObservation(columns.get("observation"));
			boolean flag = createLitPage.isThereOneSequenceWhenAddingEvidence();
			if (!flag) {
				PopUpAddSequenceAccession popupTrigger = createLitPage
						.clickOnAddSequence();
				createLitPage = popupTrigger.addSequenceName(columns
						.get("addSequence"));
			}
			PopUpAddTraitComponent popupTriat = createLitPage
					.clickAddTraitComponent();
			createLitPage = (CreateLiteratureEvidenceDetailsForGeneticFeaturePage) popupTriat
					.addTrait(columns.get("trait"));

			this.gfPage = createLitPage.clickSave();
			this.gfPage.clickOnEvidenceSequenceTab();

			reporter.reportPass("Created a new evidence under sequence tab ,  as there was no associated Evidences , now the  Sequence has one Evidence.");

		}

		this.gfPage.clickDetailSequenceTab();
		boolean flag = this.gfPage.isDeleteThisGFButtonEnabled();
		reporter.verifyTrue(
				!flag,
				"In Details tab, check the existence of the  \"Delete This Sequence\" button and that it is Disabled");

		String hoover = this.gfPage.getDeleteButtonToolTip();

		reporter.verifyEqual(
				hoover,
				"Some sequences have associated evidences.",
				"Hover over the \"Delete This Sequence\" button. Tool tip appearing showing \"Some sequences have associated evidences.\" ");
		int evdCount = this.gfPage.getEvidenceSequenceCountOnTab();
		if (evdCount != 0) {
			this.gfPage = this.gfPage.clickOnEvidenceSequenceTab();
			ViewLiteratureEvidenceDetailsPageSequence viewLit = this.gfPage
					.clickviewLiteratureEvidenceSequence();
			this.gfPage = viewLit.clickOnDelete();

		}
	}
}
