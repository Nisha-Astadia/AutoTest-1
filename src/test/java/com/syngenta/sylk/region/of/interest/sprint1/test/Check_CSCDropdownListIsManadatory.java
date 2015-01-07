package com.syngenta.sylk.region.of.interest.sprint1.test;

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
 * 
 */
public class Check_CSCDropdownListIsManadatory {

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

	@Test(enabled = true, description = " Chromosome / Scaffold / Contig dropdown list is manadatory", groups = {
			"Check_CSCDropdownListIsManadatory", "ROI", "regression"})
	public void checkCSCDropdownListIsManadatory() {

		CommonLibrary common = new CommonLibrary();
		SyngentaReporter reporter = new SyngentaReporter();

		try {

			reporter.reportPass("Login to SyLK");

			this.ROIPage = this.homepage.goToROIDetailsPage();

			reporter.verifyEqual(this.ROIPage.getPageTitle(),
					PageTitles.ROI_Detail_page_title,
					("'Region Of Interest (ROI) Details' Page Opens up "));

			this.ROIPage.selectRegionOfInterestType("Gene-based");

			reporter.reportPass("Selected the value 'Gene-based' from the 'Region Of Interest Type' dropdown");

			this.ROIPage.selectSourceSpecies("Allium porrum");

			reporter.reportPass("Selected a value 'Allium-porrum' from the source species");

			this.ROIPage.selectReferenceGenomeVersion("leek_addendum");

			reporter.reportPass("Selected a value 'leek_addendum' from 'Reference Genome & Version ' drop down.");

			this.ROIPage.clickAddRegionOfInterest();

			String errorMsg = null;
			errorMsg = this.ROIPage.getCSCValidationError();
			reporter.verifyEqual(
					errorMsg,
					"This field is required",
					"Actual validation error message does not match expected. Expected= 'This field is required.', Actual= '"
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
