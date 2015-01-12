package com.syngenta.sylk.search.roi.test;

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
import com.syngenta.sylk.menu_add.pages.ROIDetailPage;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

/**
 * @author Nisha Pillai
 * 
 */

public class Check_ThatUserCanSearchByAnAlreadyExist_ROI_Symbol {

	private List<Object[]> testData = new ArrayList<Object[]>();
	private LandingPage lp;
	private HomePage homepage;
	private String symbol;
	private String existingroi;
	private ROIDetailPage roiDetailpage;

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Check_ThatUserCanSearchByAnAlreadyExist_ROI_Symbol.xlsx");
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

	@Test(enabled = true, description = "check that user can search by an already exist roi SYMBOL  ", dataProvider = "TestData", groups = {
			"Check_ThatUserCanSearchByNewAdded_GF_Symbol", "ROI",
			"Search SyLK", "regression"})
	public void check_ThatUserCanSearchByAnAlreadyExistROISymbol(
			String testDescription, String row_num,
			HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();
		try {

			reporter.reportPass("Login to SyLK");

			this.symbol = columns.get("symbol");
			this.existingroi = (columns.get("existingROI"));
			SearchSylkPage searchPage = this.homepage
					.goToGFRNAiTriggerROIpromoter();

			reporter.verifyEqual(searchPage.getPageTitle(),
					PageTitles.search_sylk_page_title,
					"Navigate to Find >> GF/RANI Triggers/ ROI/Promoter");

			searchPage.enterSylkSearch(this.existingroi);

			reporter.reportPass(" enter existing ROI  " + this.existingroi
					+ " in search field");
			searchPage.selectType("ROI");
			reporter.reportPass("select type ROI");

			searchPage = searchPage.clickSearch();

			String locateExistingGF = "Existing ROI  \"" + this.existingroi
					+ "\" show up in the seach result";

			BasePage basepage = searchPage.clickAndOpenThisGF(this.existingroi);
			if (basepage instanceof ROIDetailPage) {
				reporter.reportPass("located Existing ROI ");
				this.roiDetailpage = ((ROIDetailPage) basepage);
				// gfpage.clickDeleleThisGeneticFeature();
			} else {
				reporter.verifyThisAsFail("locate Existing ROI");

				String finalStep = "GF  already exists with name  \""
						+ this.existingroi
						+ "\" should be appeared in search result";
				reporter.verifyThisAsFail(finalStep);

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