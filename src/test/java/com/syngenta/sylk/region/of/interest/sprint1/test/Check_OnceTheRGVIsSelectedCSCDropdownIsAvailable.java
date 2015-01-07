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
 * 
 */
public class Check_OnceTheRGVIsSelectedCSCDropdownIsAvailable {

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

	@Test(enabled = true, description = "  Once the Referenece Genome & Version is selected, Chromosome / Scaffold / Contig dropdown is available ", groups = {
			"Check_OnceTheRGVIsSelectedCSCDropdownIsAvailable", "ROI",
			"regression"})
	public void checkOnceTheRGVIsSelectedCSCDropdownIsAvailable() {

		CommonLibrary common = new CommonLibrary();
		SyngentaReporter reporter = new SyngentaReporter();

		try {

			reporter.reportPass("Login to SyLK");

			this.ROIPage = this.homepage.goToROIDetailsPage();

			reporter.verifyEqual(this.ROIPage.getPageTitle(),
					PageTitles.ROI_Detail_page_title,
					("'Region Of Interest (ROI) Details' Page Opens up "));

			this.ROIPage.selectRegionOfInterestType("Haplotype Block");

			reporter.reportPass("Selected the value 'Haplotype Block' from the 'Region Of Interest Type' dropdown");

			this.ROIPage.selectSourceSpecies("Botrytis cinerea");

			reporter.reportPass("Selected a value 'Botrytis cinerea' from the source species");

			boolean rgv = this.ROIPage.isReferenceGenomeAndVersionEnabled();

			reporter.verifyTrue(
					rgv,
					"Once the 'Source species' is selected, the Reference Genome And Version field is Enabled and available for use");

			List<String> a = this.ROIPage.getListOfReferenceGenomeVersion();
			System.out.println(a);

			reporter.reportPass("Once the Referenece Genome & Version is selected, this dropdown list is available.  ");

			reporter.reportPass("The Vaules of dropdown list :"
					+ (a.toString()));

			this.ROIPage.selectReferenceGenomeVersion("b_cinerea_b0510_V2");

			// boolean start = this.ROIPage.isStartEnabled();
			// reporter.verifyTrue(start,
			// "Start field should be available for use");

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
