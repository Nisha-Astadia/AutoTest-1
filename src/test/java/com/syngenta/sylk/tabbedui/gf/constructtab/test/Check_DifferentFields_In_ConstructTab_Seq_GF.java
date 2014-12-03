package com.syngenta.sylk.tabbedui.gf.constructtab.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

public class Check_DifferentFields_In_ConstructTab_Seq_GF {

	private List<Object[]> testData = new ArrayList<Object[]>();
	private LandingPage lp;
	private HomePage homepage;
	private SearchSylkPage searchSylkpage;

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Check_ConstructTab_VerifyColumns_ConstructTab.xlsx");
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

	@DataProvider(name = "checkDifferentFieldsInConstructTabSeq", parallel = false)
	public Iterator<Object[]> loadTestData() {

		return this.testData.iterator();
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp() {

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}

	@Test(enabled = true, description = "Check different fields in Construct Tab of Sequence section", dataProvider = "checkDifferentFieldsInConstructTabSeq", groups = {
			"Check_DifferentFields_In_ConstructTab_SequenceSec_GF", "RNAI",
			"regression"})
	public void CheckDifferentFieldsInConstructTab(String testDescription,
			String row_num, HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();

		try {

			reporter.reportPass("Login to SyLK");
			// step1

			this.searchSylkpage = this.homepage.goToGFRNAiTriggerROIpromoter();

			reporter.verifyEqual(this.searchSylkpage.getPageTitle(),
					PageTitles.search_sylk_page_title,
					"Select menu item 'GF/RNAi Triggers/ROI/Promoter' and open Search page.");

			this.searchSylkpage.selectAddedBy(columns.get("user"));

			this.searchSylkpage.selectType("Genetic Feature");

			this.searchSylkpage = this.searchSylkpage.clickSearch();

			int searchResults = this.searchSylkpage.getTotalResultCount();

			if (searchResults == 0) {
				reporter.assertThisAsFail("When searched for genetic feature for this user="
						+ columns.get("user") + " resulted in zero results");
			} else {
				reporter.reportPass("When searched for genetic feature for this user="
						+ columns.get("user")
						+ " displays "
						+ searchResults
						+ " results");

				GeneticFeaturePage gfPage = (GeneticFeaturePage) this.searchSylkpage
						.clickAndOpenAGFWithConstruct();
				gfPage.clickOnConstructTab();

				List<String> a = gfPage.getConstructColumns();
				System.out.println(a);
				if (a.size() == 8) {
					reporter.reportPass("Verify That there is a header composed of 8 columns");
					reporter.reportPass(a.toString());
				} else {
					reporter.verifyThisAsFail("Verify That there is a header composed of 8 columns");
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
