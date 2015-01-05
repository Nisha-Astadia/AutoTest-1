package com.syngenta.sylk.region.of.interest.sprint2.test;

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
import com.syngenta.sylk.menu_add.pages.PopUpAddProjectPage;

public class Check_ThatTheUserCanHaveAMaximumOf5ProjectsAssociatedWithTheROI {
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

	@Test(enabled = true, description = "Check that the user can have a maximum of 5 projects associated with the ROI", groups = {
			"Check_ThatTheUserCanHaveAMaximumOf5ProjectsAssociatedWithTheROI",
			"ROI", "regression"})
	public void checkAddingMaxFiveProjects() {

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

			String prefix = "p"; // first we will enter this letter
			List<String> listedProjectNames = popup
					.enterPrefixInProjectNameAndGetListedProjectNames(prefix);
			int a = 0;
			for (String name : listedProjectNames) {
				a++;
				popup = ROIPage.clickAddProjectToROI();
				popup.clickOnAdd(name);

				if ((a >= 5) && (ROIPage.getAddedProjectNameCount() == 5)) {
					break;
				}
			}

			boolean enabled = ROIPage.isAddProjectEnabled();

			reporter.verifyTrue(!enabled,
					"The user may have a maximum of 5 projects associated with the ROI");

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
