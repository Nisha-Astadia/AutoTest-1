package com.syngenta.sylk.tabbedui.rnai.detailtab.test;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.main.pages.AdminAssignRolePage;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_find.pages.RNAiPage;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

//Tabbed veiw RNAi
//Check Edit Button disabled in details Tab in tabbed view RNAi (User doesn't have Curator role, and RNAi was created by another user)

public class Check_EditButton_Disabled_In_DetailTab_RNAi {

	@BeforeClass(alwaysRun = true)
	public void setUp() {
		LandingPage lp = LandingPage.getLandingPage();
		HomePage homepage = lp.goToHomePage();
		SyngentaReporter reporter = new SyngentaReporter();
		AdminAssignRolePage admin = null;
		try {
			admin = homepage.gotoAdminAssignRolePage();
			admin = admin.searchUser("Pillai Nisha");
			admin = admin.clickOnThisUserLink("Pillai Nisha");
			admin.removeThisRole("curator");
		} catch (Exception e) {
			e.printStackTrace();
			throw new SyngentaException("Test Failed:"
					+ new CommonLibrary().getStackTrace(e));
		} finally {
			if (lp != null) {
				lp.driverQuit();
			}
			reporter.assertAll();
		}
	}

	@AfterClass(alwaysRun = true)
	public void cleanUp() {
		LandingPage lp = LandingPage.getLandingPage();
		HomePage homepage = lp.goToHomePage();
		SyngentaReporter reporter = new SyngentaReporter();
		AdminAssignRolePage admin = null;
		try {
			admin = homepage.gotoAdminAssignRolePage();
			admin = admin.searchUser("Pillai Nisha");
			admin = admin.clickOnThisUserLink("Pillai Nisha");
			admin.addThisRole("curator");
		} catch (Exception e) {
			e.printStackTrace();
			throw new SyngentaException("Test Failed:"
					+ new CommonLibrary().getStackTrace(e));
		} finally {
			if (lp != null) {
				lp.driverQuit();
			}
			reporter.assertAll();
		}
	}

	@Test(enabled = true, description = "Check Edit Button disabled in details Tab in tabbed view RNAi (User doesn't have Curator role, and RNAi was created by another user) ", groups = {
			"Check_EditButton_Disabled_In_DetailTab_RNAi", "tabbedui", "RNAI",
			"regression"})
	public void rNAiConstructTabInTabbedView() {

		SyngentaReporter reporter = new SyngentaReporter();
		LandingPage lp = LandingPage.getLandingPage();
		try {

			HomePage homepage = lp.goToHomePage();
			String user = "Elkomy, Mohamed";
			// step1

			reporter.reportPass("Login to SyLK");

			// Step 2
			SearchSylkPage searchSylkpage = homepage
					.goToGFRNAiTriggerROIpromoter();

			reporter.verifyEqual(searchSylkpage.getPageTitle(),
					PageTitles.search_sylk_page_title,
					"Select menu item 'GF/RNAi Triggers/ROI/Promoter' and open Search page.");

			// step 3
			searchSylkpage.selectAddedBy(user);

			searchSylkpage.selectType("RNAi");

			searchSylkpage = searchSylkpage.clickSearch();

			int count = searchSylkpage.getTotalResultCount();
			if (count == 0) {
				reporter.assertThisAsFail("When searched for RNAi for this user="
						+ user + " resulted in zero results");
			} else {
				reporter.reportPass("When searched for RNAi for this user="
						+ user + " displays " + count + " results");
			}

			// step 4
			RNAiPage rnaiPage = (RNAiPage) searchSylkpage
					.clickAndOpenRNAiWithOutLiteratureEvidence();
			reporter.verifyEqual(
					rnaiPage.getPageTitle(),
					PageTitles.rnai_page_title_page,
					"From the search result click on RNAi link and open a RNAi which has at least one evidence.");

			// step 5
			rnaiPage = rnaiPage.clickOnDetailTab();

			reporter.reportPass("Click on detail tab open up the detail tab.");

			// step 6

			boolean editenabled = rnaiPage.isEditButtonUnderDetailTabEnabled();

			reporter.verifyTrue(
					!editenabled,
					"Edit Button is disabled in details Tab in tabbed view RNAi created by another user and user does not have curator role");

		} catch (SkipException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SyngentaException("Test Failed:"
					+ new CommonLibrary().getStackTrace(e));
		} finally {
			if (lp != null) {
				lp.driverQuit();
			}
			reporter.assertAll();
		}
	}
}