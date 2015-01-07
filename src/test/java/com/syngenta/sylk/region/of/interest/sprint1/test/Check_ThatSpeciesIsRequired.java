package com.syngenta.sylk.region.of.interest.sprint1.test;

import java.util.ArrayList;
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
import com.syngenta.sylk.menu_add.pages.PopUpAddProjectPage;

/**
 * @author Nisha Pillai
 * 
 */
public class Check_ThatSpeciesIsRequired {

	private LandingPage lp;
	private HomePage homepage;
	private AddNewRegionOfInterestROIPage ROIPage;

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

	@Test(enabled = true, description = "Check that species is required ", groups = {
			"Check_CheckThatSpeciesIsRequired", "ROI", "regression"})
	public void checkAlreadyExistinGF_ContainerSourceCode() {

		SyngentaReporter reporter = new SyngentaReporter();
		CommonLibrary common = new CommonLibrary();

		try {
			reporter.reportPass("Login to SyLK");

			this.ROIPage = this.homepage.goToROIDetailsPage();

			reporter.verifyEqual(this.ROIPage.getPageTitle(),
					PageTitles.add_New_ROI_Page_title,
					("'Add New Region Of Interest (ROI)' Page Opens up "));

			reporter.reportPass("Press Add ROI, ROI details page will be openned");

			this.ROIPage.selectRegionOfInterestType("Gene-based");

			PopUpAddProjectPage popUpAddProject = this.ROIPage
					.clickAddProjectToROI();

			this.ROIPage = popUpAddProject
					.clickOnAdd("PI0000805:Water Optimization TPP Leads");

			PopUpAddOrRemovePhenotypesPage popup = this.ROIPage
					.clickEditPhenotypes();
			String key = "ab";
			popup.enterSearchKeyAndClickOnFind(key);
			List<String> list = popup.getListOfPhenotypes();
			List<String> expected_selected_list = new ArrayList<String>();

			// Select from the list and add to expected results
			popup.AddThisPhenotype(list.get(0));
			expected_selected_list.add(list.get(0));

			this.ROIPage = popup.clickOnClose();

			this.ROIPage.clickAddRegionOfInterest();

			String errorMsg = this.ROIPage.getSourceSpeciesValidationError();
			reporter.verifyEqual(
					errorMsg,
					"This field is required",
					" Error message should be displayed notifying the user that species dropdown should be entered'"
							+ " Expected = 'This field is required.' Actual= '"
							+ errorMsg + "'");

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