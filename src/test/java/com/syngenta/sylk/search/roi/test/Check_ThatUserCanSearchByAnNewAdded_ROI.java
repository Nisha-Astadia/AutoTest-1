package com.syngenta.sylk.search.roi.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.main.pages.BasePage;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.AddNewRegionOfInterestROIPage;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.PopUpAddOrRemovePhenotypesPage;
import com.syngenta.sylk.menu_add.pages.PopUpAddProjectPage;
import com.syngenta.sylk.menu_add.pages.ROIDetailPage;

public class Check_ThatUserCanSearchByAnNewAdded_ROI {
	private List<Object[]> testData = new ArrayList<Object[]>();
	private LandingPage lp;
	private HomePage homepage;
	private AddNewRegionOfInterestROIPage ROIPage;
	private String symbol;
	private String existingroi;
	private GeneticFeaturePage gfpage;
	private ROIDetailPage roidetailpage;

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Check_ThatUserCanSearchByAnAlreadyExist_ROI_Symbol.xlsx");
	}

	@BeforeMethod(alwaysRun = true)
	public void testSetUp() {
		this.lp = LandingPage.getLandingPage();
		this.homepage = this.lp.goToHomePage();
	}

	@DataProvider(name = "TestData", parallel = false)
	public Iterator<Object[]> loadTestData() {
		return this.testData.iterator();
	}

	@AfterClass(alwaysRun = true)
	public void cleanUp() {

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}

	@Test(enabled = true, description = "check that user can search by an new added ROI  ", dataProvider = "TestData", groups = {
			"Check_ThatUserCanSearchByAnNewAdded_ROI", "ROI", "Search SyLK",
			"regression"})
	public void check_ThatUserCanSearchByAnNewAdded_ROI(String testDescription,
			String row_num, HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();
		CommonLibrary common = new CommonLibrary();

		try {
			reporter.reportPass("Login to SyLK");

			this.ROIPage = this.homepage.goToROIDetailsPage();

			reporter.verifyEqual(this.ROIPage.getPageTitle(),
					PageTitles.add_New_ROI_Page_title,
					("'Add New Region Of Interest (ROI)' Page Opens up "));

			reporter.reportPass("Click Add ROI, ROI details page will be openned");

			this.ROIPage.selectRegionOfInterestType("Gene-based");

			this.ROIPage.selectSourceSpecies("Zea mays");

			this.ROIPage.selectReferenceGenomeVersion("corn_v3");

			this.ROIPage.enterChromosomeScaffoldContigAndReportIfError("chr0");

			this.ROIPage.enterStart("10");
			this.ROIPage.enterEnd1("15");

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

			BasePage basepage = this.ROIPage.clickAddRegionOfInterest();

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
