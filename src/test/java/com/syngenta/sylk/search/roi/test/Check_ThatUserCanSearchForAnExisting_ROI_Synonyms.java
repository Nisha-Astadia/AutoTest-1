package com.syngenta.sylk.search.roi.test;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
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

public class Check_ThatUserCanSearchForAnExisting_ROI_Synonyms {
	private LandingPage lp;
	private HomePage homepage;
	private ROIDetailPage roiDetailpage;
	private String symbol;
	private String synonyms;

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

	@Test(enabled = true, description = "Check that all phenotypes matching my search criteria will be displayed ", groups = {
			"Check_ThatAllPhenotypesMatchingMySearchCriteriaWillBeDisplayed",
			"ROI", "regression"})
	public void checkPhenotypeSearchGivesMatchingResults() {

		SyngentaReporter reporter = new SyngentaReporter();
		try {

			reporter.reportPass("Login to SyLK");

			this.symbol = ("SYLKROI000262_v1");
			this.synonyms = ("Zea_mays_corn_v3_chr0_10_15");
			SearchSylkPage searchPage = this.homepage
					.goToGFRNAiTriggerROIpromoter();

			reporter.verifyEqual(searchPage.getPageTitle(),
					PageTitles.search_sylk_page_title,
					"Navigate to Find >> GF/RANI Triggers/ ROI/Promoter");

			searchPage.enterSylkSearch(this.synonyms);

			reporter.reportPass(" enter existing ROI synonyms  "
					+ this.synonyms + " in search field");
			searchPage.selectType("ROI");
			reporter.reportPass("select type ROI");

			searchPage = searchPage.clickSearch();

			String locateExistingGF = "Existing ROI synonyms  \""
					+ this.synonyms + "\" show up in the seach result";

			BasePage basepage = searchPage.clickAndOpenThisGF(this.symbol);
			if (basepage instanceof ROIDetailPage) {
				reporter.reportPass("located Existing ROI ");
				this.roiDetailpage = ((ROIDetailPage) basepage);
				// gfpage.clickDeleleThisGeneticFeature();
			} else {
				reporter.verifyThisAsFail(locateExistingGF);

				String finalStep = "ROI  with   \"" + this.synonyms
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
