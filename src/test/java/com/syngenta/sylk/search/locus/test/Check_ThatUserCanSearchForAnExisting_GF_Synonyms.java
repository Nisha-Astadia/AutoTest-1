package com.syngenta.sylk.search.locus.test;

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
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

/**
 * @author Nisha Pillai
 * 
 */
public class Check_ThatUserCanSearchForAnExisting_GF_Synonyms {

	private List<Object[]> testData = new ArrayList<Object[]>();
	private LandingPage lp;
	private HomePage homepage;
	private AddNewGeneticFeaturePage addNewGFPage;
	private String symbol;
	private String synonyms;
	private String existingGFName;
	private GeneticFeaturePage gfpage;

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Check_ThatUserCanSearchForAnExisting_GF_Synonyms.xlsx");
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

	@Test(enabled = true, description = "check that user can search for an existing GF >> SYNONYMS", dataProvider = "TestData", groups = {
			"check_That_UserCanSearchForAnExisting_GF_Synonyms", "Locus",
			"Search SyLK", "regression"})
	public void check_That_UserCanSearchForAnExisting_GF_Synonyms(
			String testDescription, String row_num,
			HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();
		try {

			reporter.reportPass("Login to SyLK");

			this.symbol = columns.get("symbol");
			this.existingGFName = (columns.get("existingGF"));
			SearchSylkPage searchPage = this.homepage
					.goToGFRNAiTriggerROIpromoter();

			reporter.verifyEqual(searchPage.getPageTitle(),
					PageTitles.search_sylk_page_title,
					"Navigate to Find >> GF/RANI Triggers/ ROI/Promoter");

			searchPage.enterSylkSearch(this.existingGFName);

			reporter.reportPass(" enter existing GF  " + this.existingGFName
					+ " in search field");
			searchPage.selectType("Genetic Feature");
			reporter.reportPass("select type GF");

			searchPage = searchPage.clickSearch();

			String locateExistingGF = "Existing GF  \"" + this.existingGFName
					+ "\" show up in the seach result";

			BasePage basepage = searchPage
					.clickAndOpenThisGF(this.existingGFName);
			if (basepage instanceof GeneticFeaturePage) {
				reporter.reportPass(locateExistingGF);
				this.gfpage = ((GeneticFeaturePage) basepage);
				// gfpage.clickDeleleThisGeneticFeature();
			} else {
				reporter.verifyThisAsFail(locateExistingGF);

				this.symbol = this.gfpage.getSymbol();
				GeneticFeaturePage gfpage = (GeneticFeaturePage) searchPage
						.clickAndOpenThisGF(this.symbol);

				String tagText = gfpage.getTextOfSynonyms();

				this.homepage = this.lp.goToHomePage();
				searchPage = this.homepage.goToGFRNAiTriggerROIpromoter();

				reporter.verifyEqual(searchPage.getPageTitle(),
						PageTitles.search_sylk_page_title,
						"Navigate to Find >> GF/RANI Triggers/ ROI/Promoter");

				searchPage.enterSylkSearch(tagText);
				reporter.reportPass(" Search Existing GF with synonyms  "
						+ (tagText) + " in search field");

				searchPage.selectType("Genetic Feature");
				reporter.reportPass("select type GF");
				searchPage = searchPage.clickSearch();

				String finalStep = "GF with Synonym  \"" + tagText
						+ "\" should be appeared in search result";

				BasePage base = searchPage.clickAndOpenThisGF(this.symbol);
				if (base instanceof GeneticFeaturePage) {
					reporter.reportPass(finalStep);
					gfpage = ((GeneticFeaturePage) base);

				} else {
					reporter.verifyThisAsFail(finalStep);
					// this.homepage = searchPage.gotoHomePage();
					// this.homepage.deleteThisGFWithOutCheckingAllTabs(this.homepage,
					// this.symbol);

				}
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
