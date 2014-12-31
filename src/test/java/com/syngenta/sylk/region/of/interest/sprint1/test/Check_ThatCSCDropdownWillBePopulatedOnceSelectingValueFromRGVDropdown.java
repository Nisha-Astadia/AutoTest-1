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

public class Check_ThatCSCDropdownWillBePopulatedOnceSelectingValueFromRGVDropdown {

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

	@Test(enabled = true, description = "  Check that Chromosome / Scaffold / Contig dropdown will be populated once selecting value from Referenece Genome & Version dropdown ", groups = {
			"Check_ThatCSCDropdownWillBePopulatedOnceSelectingValueFromRGVDropdown",
			"ROI", "regression"})
	public void checkROITypeDropdownListIsMandatory() {

		CommonLibrary common = new CommonLibrary();
		SyngentaReporter reporter = new SyngentaReporter();

		try {

			reporter.reportPass("Login to SyLK");

			this.ROIPage = this.homepage.goToROIDetailsPage();

			reporter.verifyEqual(this.ROIPage.getPageTitle(),
					PageTitles.ROI_Detail_page_title,
					("'Region Of Interest (ROI) Details' Page Opens up "));

			this.ROIPage.selectRegionOfInterestType("Gene-based");

			reporter.reportPass("Selected a value 'Gene-based' from Region Of Interest Type");

			this.ROIPage.selectSourceSpecies("Allium porrum");

			reporter.reportPass("Selected a value 'Allium-porrum' from the source species");

			this.ROIPage.selectReferenceGenomeVersion("leek_addendum");

			reporter.reportPass("Selected a value 'leek_addendum' from the Reference Genome Version");

			boolean cscEnabled = this.ROIPage
					.isChromosomeScaffoldContigEnabled();

			reporter.verifyTrue(
					cscEnabled,
					"Chromosome / Scaffold / Contig will be populated with values once selecting any value from Referenece Genome & Version dropdown");

			List<String> a = this.ROIPage
					.getListOfChromosomeScaffoldContig("lp");
			System.out.println(a);

			reporter.reportPass("Verify That the 'Chromosome / Scaffold / Contig will be populated with the dropdown list :'  "
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
