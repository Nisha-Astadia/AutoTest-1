package com.syngenta.sylk.region.of.interest.sprint1.test;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.AddNewRegionOfInterestROIPage;
import com.syngenta.sylk.menu_add.pages.PopUpAddOrRemovePhenotypesPage;
import com.syngenta.sylk.menu_add.pages.PopUpAddProjectPage;

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

		PopUpAddOrRemovePhenotypesPage popUpAddOrRemovePh = this.ROIPage
				.clickEditPhenotypes();
		this.ROIPage = popUpAddOrRemovePh
				.findPhenotype("DNA binding [en;XX;1]");

		// this.ROIPage = popUpAddOrRemovePh
		// .selectPhenotypeToAssignToROI("DNA binding [en;XX;1]");
		// this.ROIPage = this.ROIPage.clickAddRegionOfInterest();
		//
		// }
	}
}