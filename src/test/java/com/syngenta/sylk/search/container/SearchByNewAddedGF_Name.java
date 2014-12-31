package com.syngenta.sylk.search.container;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.main.pages.BasePage;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.AddNewGeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.BLASTSearchResultPage;
import com.syngenta.sylk.menu_add.pages.GeneticFeatureManualEntryPage;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.NewGeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.PopUpFlagForCurationPage;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

public class SearchByNewAddedGF_Name {

	private LandingPage lp;
	private HomePage homepage;
	private AddNewGeneticFeaturePage addNewGFPage;
	private String symbol;
	private String proteinData = "METLVNLIVASFLYKLGLFSSLGVSQSHYVKANGLSTTTKLSSICKTSDLTI"
			+ "HKKSNRTRKFSVSAGYRDGSRSGSSGDFIAGFLLGGAVFGAVAYIFAPQIRR"
			+ "SVLNEEDEYGFEKPKQPTYYDEGLEKTRETLNEKIGQLNSAIDNVSSRLRGRE"
			+ "KNTSSLNVPVETDPEVEATT";
	private String sourceSpice = "Acetobacter xylinum";

	// @BeforeClass(alwaysRun = true)
	// public void loadData() {
	//
	// }

	@BeforeMethod(alwaysRun = true)
	public void testSetUp() {
		this.lp = LandingPage.getLandingPage();
		this.homepage = this.lp.goToHomePage();
	}

	@AfterClass(alwaysRun = true)
	public void cleanUp() {

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}

	@Test(enabled = true, description = "check that user can search by already existing GF >> Container source code", groups = {
			"CheckAlreadyExistinGF_ContainerSourceCode", "synonyms",
			"regression"})
	public void checkAlreadyExistinGF_ContainerSourceCode() {
		String name = "name_GF";
		SyngentaReporter reporter = new SyngentaReporter();
		CommonLibrary common = new CommonLibrary();

		reporter.reportPass("Login to SyLK");

		this.addNewGFPage = this.homepage.goToAddGeneticFeaturePage();

		reporter.verifyEqual(this.addNewGFPage.getPageTitle(),
				PageTitles.add_new_genetic_feature_page_title,
				"Open 'Add New Genetic Feature Page'");

		this.symbol = "GF_name_test";

		// step 7
		this.addNewGFPage.selectGeneType("Gene");

		// step 8
		this.addNewGFPage.enterTextInSequence(this.proteinData);

		GeneticFeatureManualEntryPage gfmanualEntrypage = (GeneticFeatureManualEntryPage) this.addNewGFPage
				.clickFindMatches();

		BLASTSearchResultPage blastSearchResultPage = gfmanualEntrypage
				.clickDuplicateCheck();

		NewGeneticFeaturePage newGFPage = null;
		BasePage page = blastSearchResultPage.clickAddAsNewGFAndGoToNewGFPage();
		if (page instanceof PopUpFlagForCurationPage) {
			PopUpFlagForCurationPage popup = (PopUpFlagForCurationPage) page;
			popup.enterRationale("test");
			newGFPage = (NewGeneticFeaturePage) popup
					.clickContinueAndGoToNewGFPage();
		} else {
			newGFPage = (NewGeneticFeaturePage) page;
		}

		newGFPage.enterSymbolId(this.symbol);
		newGFPage.enterSourceSpeciesTaxonomy(this.sourceSpice);
		GeneticFeaturePage gfPage = newGFPage.clickAddGeneticFeature();
		/*
		 * Edit gf and add name
		 */
		gfPage = gfPage.clickOnEditDetailTab();
		gfPage.enterNameDetailTab(name);
		gfPage = gfPage.clickOnSaveDetailTab();
		reporter.reportPass("fill in the mandatory fields");
		reporter.reportPass("enter \"" + name
				+ "\" in name field after clik on edit in detail tab");

		reporter.verifyEqual(
				gfPage.getPageTitle(),
				PageTitles.genetic_feature_page_title,
				"click add as new GF and Genetic feature is created and genetic feature page shows up");

		this.homepage = gfPage.gotoHomePage();

		this.homepage.driverQuit();

		this.lp = LandingPage.getLandingPage();
		this.homepage = this.lp.goToHomePage();

		SearchSylkPage searchPage = this.homepage
				.goToGFRNAiTriggerROIpromoter();
		reporter.verifyEqual(searchPage.getPageTitle(),
				PageTitles.search_sylk_page_title,
				"Navigate to Find >> GF/RANI Triggers/ ROI/Promoter");

		searchPage.enterSylkSearch(this.symbol);
		reporter.reportPass("enter  \"" + name + "\" in search field");
		searchPage.selectType("Genetic Feature");
		reporter.reportPass("select type GF");
		searchPage = searchPage.clickSearch();
		String finalStep = "GF with Synonym  \"GF_Synonyms\" should be appeared in search result";
		BasePage base = searchPage.clickAndOpenThisGF(this.symbol);
		if (base instanceof GeneticFeaturePage) {
			reporter.reportPass(finalStep);
			this.homepage = searchPage.gotoHomePage();
		} else {
			reporter.verifyThisAsFail(finalStep);
			this.homepage = ((GeneticFeaturePage) base).gotoHomePage();
		}

		this.homepage.deleteThisGFWithOutCheckingAllTabs(this.homepage,
				this.symbol);

	}
}
