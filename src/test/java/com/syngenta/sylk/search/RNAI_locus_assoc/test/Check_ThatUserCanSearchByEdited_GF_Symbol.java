package com.syngenta.sylk.search.RNAI_locus_assoc.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.libraries.SyngentaException;
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

public class Check_ThatUserCanSearchByEdited_GF_Symbol {

	private List<Object[]> testData = new ArrayList<Object[]>();
	private LandingPage lp;
	private HomePage homepage;
	private AddNewGeneticFeaturePage addNewGFPage;
	private String symbol;
	private String editedSymbol;
	private GeneticFeaturePage gfpage;

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Check_ThatUserCanSearchByEdited_GF_Symbol.xlsx");
	}

	@BeforeMethod(alwaysRun = true)
	public void testSetUp() {
		this.lp = LandingPage.getLandingPage();
		this.homepage = this.lp.goToHomePage();
	}

	@DataProvider(name = "TestData", parallel = false)
	public Iterator<Object[]> loadTestData() {
		return this.testData.iterator();
	}

	@AfterClass(alwaysRun = true)
	public void cleanUp() {

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}

	@Test(enabled = true, description = "check that user can search by edited GF>> symbol", dataProvider = "TestData", groups = {
			"Check_ThatUserCanSearchByEdited_GF_Symbol", "RNAI_LOCUS_ASSOC",
			"Search SyLK", "regression"})
	public void check_ThatUserCanSearchForANewAdded_GF_Symbol(
			String testDescription, String row_num,
			HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();

		try {

			reporter.reportPass("Login to SyLK");

			this.addNewGFPage = this.homepage.goToAddGeneticFeaturePage();

			reporter.verifyEqual(this.addNewGFPage.getPageTitle(),
					PageTitles.add_new_genetic_feature_page_title,
					"Open 'Add New Genetic Feature Page'");

			this.symbol = columns.get("symbol");

			this.editedSymbol = columns.get("editedSymbol");

			// step 7
			this.addNewGFPage.selectGeneType(columns.get("gene_type"));

			// step 8
			this.addNewGFPage.enterTextInSequence(columns.get("proteindata"));

			GeneticFeatureManualEntryPage gfmanualEntrypage = (GeneticFeatureManualEntryPage) this.addNewGFPage
					.clickFindMatches();

			BLASTSearchResultPage blastSearchResultPage = gfmanualEntrypage
					.clickDuplicateCheck();

			NewGeneticFeaturePage newGFPage = null;
			BasePage page = blastSearchResultPage
					.clickAddAsNewGFAndGoToNewGFPage();
			if (page instanceof PopUpFlagForCurationPage) {
				PopUpFlagForCurationPage popup = (PopUpFlagForCurationPage) page;
				popup.enterRationale("test");
				newGFPage = (NewGeneticFeaturePage) popup
						.clickContinueAndGoToNewGFPage();
			} else {
				newGFPage = (NewGeneticFeaturePage) page;
			}

			newGFPage.enterSymbolId(this.symbol);
			newGFPage.enterSourceSpeciesTaxonomy(columns.get("sourcespecies"));
			GeneticFeaturePage gfPage = newGFPage.clickAddGeneticFeature();
			/*
			 * Edit gf and add name
			 */
			gfPage = gfPage.clickOnEditDetailTab();
			gfPage.enterSymbolyDetail(this.editedSymbol);
			gfPage = gfPage.clickOnSaveDetailTab();
			reporter.reportPass("fill in the mandatory fields");
			reporter.reportPass("enter \"" + this.editedSymbol
					+ "\" in name field");

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
					"Navigate to Find >> GF/RNAI Triggers/ ROI/Promoter");

			searchPage.enterSylkSearch(this.editedSymbol);
			reporter.reportPass("enter  \"" + this.editedSymbol
					+ "\" in search field");
			searchPage.selectType("Genetic Feature");
			searchPage.selectAddedBy(columns.get("addedBy"));
			reporter.reportPass("select type GF");
			searchPage = searchPage.clickSearch();
			String finalStep = "GF with edited symbol  \"" + this.editedSymbol
					+ "\" should be appeared in search result";

			BasePage base = searchPage.clickAndOpenThisGF(this.editedSymbol);
			if (base instanceof GeneticFeaturePage) {
				reporter.reportPass(finalStep);
				GeneticFeaturePage gfpage = ((GeneticFeaturePage) base);
				gfpage.clickDeleleThisGeneticFeature();
			} else {
				reporter.verifyThisAsFail(finalStep);
				this.homepage = searchPage.gotoHomePage();
				this.homepage.deleteThisGFWithOutCheckingAllTabs(this.homepage,
						this.editedSymbol);

			}
		} catch (SkipException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SyngentaException("Test Failed:"
					+ new CommonLibrary().getStackTrace(e));
		} finally {
			reporter.assertAll();
		}
	}

}
