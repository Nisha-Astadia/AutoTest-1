package com.syngenta.sylk.region.of.interest.sprint1.test;

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

/**
 * @author Nisha Pillai
 */
public class Check_SpeciesDropdownListExistsInROIDetails {

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

	@Test(enabled = true, description = "Region of Interest Type dropdownlist values ", groups = {
			"Check_ROITypeDropdownListValues", "ROI", "regression"})
	public void checkROITypeDropdownListValues() {

		CommonLibrary common = new CommonLibrary();
		SyngentaReporter reporter = new SyngentaReporter();

		try {

			reporter.reportPass("Login to SyLK");

			this.ROIPage = this.homepage.goToROIDetailsPage();

			reporter.verifyEqual(this.ROIPage.getPageTitle(),
					PageTitles.ROI_Detail_page_title,
					("'Region Of Interest (ROI) Details' Page Opens up "));

			List<String> a = this.ROIPage.getListOfSourceSpeciesInDropDown();
			System.out.println(a);

			reporter.reportPass("Verify That the source species has changed  from a type-in text field to a dropdown list below the region of interest type dropdown list.  ");

			reporter.reportPass("The Vaules of dropdown list :"
					+ (a.toString()));

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
