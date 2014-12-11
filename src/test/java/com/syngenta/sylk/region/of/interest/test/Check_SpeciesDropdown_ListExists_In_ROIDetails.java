package com.syngenta.sylk.region.of.interest.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.RegionOfInterestROIDetailsPage;

public class Check_SpeciesDropdown_ListExists_In_ROIDetails {

	private List<Object[]> testData = new ArrayList<Object[]>();
	private LandingPage lp;
	private HomePage homepage;
	private RegionOfInterestROIDetailsPage ROIPage;

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Check_That_UserCanSearchForANewAdded_GF_Chromosome.xlsx");
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
	@Test(enabled = true, description = "Species dropdown list exists in ROI details page ", dataProvider = "TestData", groups = {
			"Check_Species_Dropdown_List_Exists_In_ROI_Details_Page", "ROI",
			"Search SyLK", "regression"})
	public void check_That_UserCanSearchForANewAdded_GF_Chromosome(
			String testDescription, String row_num,
			HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();

		reporter.reportPass("Login to SyLK");

		this.ROIPage = this.homepage.goToROIDetailsPage();

		reporter.verifyEqual(this.ROIPage.getPageTitle(),
				PageTitles.ROI_Detail_page_title,
				("'Region Of Interest (ROI) Details' Page Opens up "));

		int value = this.ROIPage.getSizeOfListOfSourceSpeciesInDropDown();
		reporter.reportPass("There are altogether " + value
				+ "values in the dropdown of  'source species'  ");

		String actualList = this.ROIPage.getListOfSourceSpeciesInDropDown();
		reporter.reportPass("Species dropdown list exists in ROI details page and they are as follows     :"
				+ actualList + " , ");

	}
}
