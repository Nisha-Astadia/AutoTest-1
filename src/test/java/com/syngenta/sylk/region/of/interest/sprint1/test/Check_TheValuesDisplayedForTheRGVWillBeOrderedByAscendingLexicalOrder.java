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

public class Check_TheValuesDisplayedForTheRGVWillBeOrderedByAscendingLexicalOrder {
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

	@Test(enabled = true, description = " Check the values displayed for the Referenece Genome & Version will be ordered by ascending lexical order ", groups = {
			"Check_TheValuesDisplayedForTheRGVWillBeOrderedByAscendingLexicalOrder",
			"ROI", "regression"})
	public void checkTheValuesDisplayedForTheRGVWillBeOrderedByAscendingLexicalOrder() {

		CommonLibrary common = new CommonLibrary();
		SyngentaReporter reporter = new SyngentaReporter();

		try {

			reporter.reportPass("Login to SyLK");

			this.ROIPage = this.homepage.goToROIDetailsPage();

			reporter.verifyEqual(this.ROIPage.getPageTitle(),
					PageTitles.ROI_Detail_page_title,
					("'Region Of Interest (ROI) Details' Page Opens up "));

			this.ROIPage.selectRegionOfInterestType("Gene-based");

			this.ROIPage.selectSourceSpecies("Capsicum annuum");

			reporter.reportPass("Selected a value 'Capsicum annuum' from the source species");

			List<String> list = this.ROIPage.getListOfReferenceGenomeVersion();

			System.out.println(list);

			boolean didsort = common.isSorted(list);

			reporter.verifyTrue(didsort,
					"The list is ordered by ascending lexical order.");

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
