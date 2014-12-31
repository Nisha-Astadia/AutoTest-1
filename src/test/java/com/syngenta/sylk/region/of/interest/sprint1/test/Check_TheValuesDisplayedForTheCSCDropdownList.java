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

public class Check_TheValuesDisplayedForTheCSCDropdownList {

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

	@Test(enabled = true, description = " Check the values displayed for the Chromosome / Scaffold / Contig dropdown list  ", groups = {
			"Check_TheValuesDisplayedForTheCSCDropdownList", "ROI",
			"regression"})
	public void checkTheValuesDisplayedForTheCSCDropdownList() {

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

			this.ROIPage.selectSourceSpecies("Zea mays");

			reporter.reportPass("Selected a value 'Zea mays' from the source species");

			this.ROIPage.selectReferenceGenomeVersion("corn_v3");

			reporter.reportPass("Selected a value 'corn_v3' from 'Reference Genome & Version ' drop down.");

			List<String> a = this.ROIPage
					.getListOfChromosomeScaffoldContig("ch");
			System.out.println(a);

			reporter.reportPass("Entered value 'ch' in the Chromosome/Scaffold/Contig field to populate the 'map names :'  "
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
