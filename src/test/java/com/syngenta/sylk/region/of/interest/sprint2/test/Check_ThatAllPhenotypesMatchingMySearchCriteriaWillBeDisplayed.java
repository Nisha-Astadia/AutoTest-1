package com.syngenta.sylk.region.of.interest.sprint2.test;

import java.util.List;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.AddNewRegionOfInterestROIPage;
import com.syngenta.sylk.menu_add.pages.PopUpAddOrRemovePhenotypesPage;

/**
 * @author Nisha Pillai
 * 
 */
public class Check_ThatAllPhenotypesMatchingMySearchCriteriaWillBeDisplayed {

	private HomePage homepage;
	private LandingPage lp;

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

		CommonLibrary common = new CommonLibrary();
		SyngentaReporter reporter = new SyngentaReporter();

		try {

			reporter.reportPass("Login to SyLK");

			AddNewRegionOfInterestROIPage ROIPage = this.homepage
					.goToROIDetailsPage();

			reporter.verifyEqual(ROIPage.getPageTitle(),
					PageTitles.add_New_ROI_Page_title,
					("'Add New Region Of Interest (ROI)' Page Opens up "));

			PopUpAddOrRemovePhenotypesPage popup = ROIPage
					.clickEditPhenotypes();
			String key = "ab";
			popup.enterSearchKeyAndClickOnFind(key);
			List<String> list = popup.getListOfPhenotypes();

			boolean sorted = new CommonLibrary().isSorted(list);
			reporter.verifyTrue(
					sorted,
					"All phenotypes that match my search criteria will be displayed sorted in ascending order by trait component name");
			String name = popup.getNameOfThePhenoTypeSelectBox();
			reporter.verifyEqual(
					name,
					"Select one or more phenotype to assign to ROI",
					"Matching phenotypes will be displayed in a box in the right handside of the Add or Remove phenotype pop up named 'Select one or more phenotype to assign to ROI'");
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
