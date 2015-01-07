package com.syngenta.sylk.region.of.interest.sprint2.test;

import java.util.ArrayList;
import java.util.Collections;
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
import com.syngenta.sylk.menu_add.pages.PopUpAddOrRemovePhenotypesPage;

/**
 * @author Nisha Pillai
 * 
 */
public class Check_ThatSelectedPhenotypesWillBeDisplayedInRoiDetailsPage {

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

	@Test(enabled = true, description = "Check that selected phenotypes will be displayed in roi details page ", groups = {
			"Check_ThatSelectedPhenotypesWillBeDisplayedInRoiDetailsPage",
			"ROI", "regression"})
	public void checkPhenotypeSearchGivesMatchingResults() {

		CommonLibrary common = new CommonLibrary();
		SyngentaReporter reporter = new SyngentaReporter();

		try {

			reporter.reportPass("Login to SyLK");

			AddNewRegionOfInterestROIPage ROIPage = this.homepage
					.goToROIDetailsPage();

			reporter.verifyEqual(ROIPage.getPageTitle(),
					PageTitles.add_New_ROI_Page_title,
					("'Add New Region Of Interest (ROI)' Page Opens up "));

			PopUpAddOrRemovePhenotypesPage popup = ROIPage
					.clickEditPhenotypes();
			String key = "ab";
			popup.enterSearchKeyAndClickOnFind(key);
			List<String> list = popup.getListOfPhenotypes();
			List<String> expected_selected_list = new ArrayList<String>();

			// Select from the list and add to expected results
			popup.AddThisPhenotype(list.get(0));
			expected_selected_list.add(list.get(0));

			popup.AddThisPhenotype(list.get(1));
			expected_selected_list.add(list.get(1));

			popup.AddThisPhenotype(list.get(2));
			expected_selected_list.add(list.get(2));

			popup.AddThisPhenotype(list.get(3));
			expected_selected_list.add(list.get(3));

			ROIPage = popup.clickOnClose();

			reporter.reportPass("Select four entry from the list and press 'Add Selected Phenotypes' button.");
			List<String> actual_selected_list = ROIPage.getAddedPhenoTypes();

			// before we compare we should make sure that they are sorted the
			// same way
			Collections.sort(expected_selected_list);
			Collections.sort(actual_selected_list);

			if (expected_selected_list.equals(actual_selected_list)) {
				reporter.reportPass("The selected phenotypes will be displayed normally in the roi details page");
			} else {
				reporter.verifyThisAsFail("The selected phenotypes will be displayed normally in the roi details page");
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
