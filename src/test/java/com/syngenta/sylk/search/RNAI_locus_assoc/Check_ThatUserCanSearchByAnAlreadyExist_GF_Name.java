package com.syngenta.sylk.search.RNAI_locus_assoc;

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
import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.main.pages.BasePage;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.AddNewGeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

public class Check_ThatUserCanSearchByAnAlreadyExist_GF_Name {

	private List<Object[]> testData = new ArrayList<Object[]>();
	private LandingPage lp;
	private HomePage homepage;
	private AddNewGeneticFeaturePage addNewGFPage;
	private String existingGF = "test";
	private String symbol;

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Check_ThatUserCanSearchByAnAlreadyExist_GF_Name.xlsx");
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

	@Test(enabled = true, description = "check that user can search by an already exist GF >> Name", dataProvider = "TestData", groups = {
			"Check_ThatUserCanSearchByAnAlreadyExist_GF_Name",
			"RNAI_LOCUS_ASSOC", "Search SyLK", "regression"})
	public void CheckThatTheUserCanSearchByAlreadyExistingGFName(
			String testDescription, String row_num,
			HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();
		CommonLibrary common = new CommonLibrary();

		reporter.reportPass("Login to SyLK");

		this.symbol = columns.get("symbol");

		this.lp = LandingPage.getLandingPage();
		this.homepage = this.lp.goToHomePage();

		SearchSylkPage searchPage = this.homepage
				.goToGFRNAiTriggerROIpromoter();
		reporter.verifyEqual(searchPage.getPageTitle(),
				PageTitles.search_sylk_page_title,
				"Navigate to Find >> GF/RANI Triggers/ ROI/Promoter");

		searchPage.enterSylkSearch(columns.get("existingGF"));
		reporter.reportPass("enter  \"" + this.existingGF
				+ "\" in search field");
		searchPage.selectAddedBy(columns.get("user"));
		searchPage.selectType("Genetic Feature");
		reporter.reportPass("select type GF");
		searchPage = searchPage.clickSearch();

		String finalStep = "GF which already exists with name  \""
				+ this.existingGF + "\" should be appeared in search result";
		BasePage base = searchPage.clickAndOpenThisGF(this.symbol);
		if (base instanceof GeneticFeaturePage) {
			reporter.reportPass(finalStep);
			this.homepage = searchPage.gotoHomePage();
		} else {
			reporter.verifyThisAsFail(finalStep);
			this.homepage = ((GeneticFeaturePage) base).gotoHomePage();
		}

	}
}