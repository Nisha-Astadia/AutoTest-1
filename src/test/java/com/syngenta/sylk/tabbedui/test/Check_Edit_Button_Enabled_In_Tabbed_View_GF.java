package com.syngenta.sylk.tabbedui.test;

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

public class Check_Edit_Button_Enabled_In_Tabbed_View_GF {

	private LandingPage lp;
	private HomePage homepage;
	private SearchSylkPage searchSylkpage;

	private List<Object[]> testData = new ArrayList<Object[]>();

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Check_EditButton_Disabled_DetailTab.xlsx");
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

	@DataProvider(name = "checkEditButtonDisabled", parallel = false)
	public Iterator<Object[]> loadTestData() {

		return this.testData.iterator();
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp() {

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}
	@Test(enabled = true, description = "Check Edit Button enabled in tabbed view GF", dataProvider = "checkEditButtonDisabled", groups = {
			"Check_Edit_Button_Enabled_In_Tabbed_View_GF", "TabbedView",
			"regression"})
	public void rNAiConstructTabInTabbedView(String testDescription,
			String row_num, HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();

		try {
			// step1

			reporter.reportPass("Login to SyLK");

			// Step 2
			this.searchSylkpage = this.homepage.goToGFRNAiTriggerROIpromoter();

			reporter.verifyEqual(this.searchSylkpage.getPageTitle(),
					PageTitles.search_sylk_page_title,
					"Select menu item 'GF/RNAi Triggers/ROI/Promoter' and open Search page.");

			// step 3
			this.searchSylkpage.selectAddedBy("Pillai,Nisha");

			this.searchSylkpage.selectType("Genetic Feature");

			this.searchSylkpage = this.searchSylkpage.clickSearch();

			int count = this.searchSylkpage.getTotalResultCount();
			if (count == 0) {
				reporter.assertThisAsFail("When searched for Genetic Feature for this user="
						+ columns.get("user") + " resulted in zero results");
			} else {
				reporter.reportPass("When searched for Genetic Feature for this user="
						+ columns.get("user")
						+ " displays "
						+ count
						+ " results");
			}
			// step
			GeneticFeaturePage gfPage = (GeneticFeaturePage) this.searchSylkpage
					.clickAndOpenThisGF("selenium_GF1");

			reporter.verifyEqual(
					gfPage.getPageTitle(),
					PageTitles.genetic_feature_page_title,
					"From the search result click on GF link and open a GF which has at least one evidence.");
			// step 5
			gfPage = gfPage.clickDetailTab();

			reporter.reportPass("Click on detail tab open up the detail tab.");

			// step 6

			boolean editenabled = gfPage.isEditButtonUnderDetailTabEnabled();

			reporter.verifyTrue(
					editenabled,
					"Edit Button is Enabled in details Tab in tabbed view 'Genetic Feature' created by another user");

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
