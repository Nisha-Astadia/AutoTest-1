package com.syngenta.sylk.region.of.interest.sprint2.test;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.syngenta.sylk.menu_add.pages.PopUpAddProjectPage;

public class Check_ThatOnceTheUserTypeTheListOfProjectsWillBeFilteredAndSortedInAscendingOrder {

	private HomePage homepage;
	private LandingPage lp;

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

	@Test(enabled = true, description = "  Check that once the user type the list of projects will be filtered and sorted in ascending order  ", groups = {
			"Check_ThatOnceTheUserTypeTheListOfProjectsWillBeFilteredAndSortedInAscendingOrder",
			"ROI", "regression"})
	public void checkAlreadyExistinGF_ContainerSourceCode() {

		CommonLibrary common = new CommonLibrary();
		SyngentaReporter reporter = new SyngentaReporter();

		try {

			reporter.reportPass("Login to SyLK");

			AddNewRegionOfInterestROIPage ROIPage = this.homepage
					.goToROIDetailsPage();

			reporter.verifyEqual(ROIPage.getPageTitle(),
					PageTitles.add_New_ROI_Page_title,
					("'Add New Region Of Interest (ROI)' Page Opens up "));

			PopUpAddProjectPage popup = ROIPage.clickAddProjectToROI();

			String prefix = "pI"; // first we will enter this letter
			boolean failure = false;
			List<String> listedProjectNames = popup
					.enterPrefixInProjectNameAndGetListedProjectNames(prefix);
			if (listedProjectNames.size() == 0) {
				reporter.verifyThisAsFail("Entered the characters " + prefix
						+ " and did not get a populated dropdown list.");
			} else {

				reporter.reportPass("Entered the characters "
						+ prefix
						+ " and found the following in the list that dropped down. List = "
						+ listedProjectNames);
			}
			int temp = 0;
			for (String name : listedProjectNames) {
				// get the string before char ':'
				String plAlphaNumber = StringUtils.substringBefore(name, ":");
				String plOnlyNumber = StringUtils.substring(plAlphaNumber, 2);
				try {
					int currNum = Integer.parseInt(plOnlyNumber);
					if (currNum > temp) {
						temp = currNum;
					} else {
						failure = true;
						break;
					}
				} catch (Exception e) {
					continue;
				}
			}

			if (!failure) {
				reporter.reportPass("once the user type the list of projects will be filtered and will be sorted by the PI number concatinated with project name in ascending order.");
			}

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
