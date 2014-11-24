package com.syngenta.sylk.tabbedui.gf.detailedtab.test;

import org.apache.commons.lang.StringUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.main.pages.AdminAssignRolePage;
import com.syngenta.sylk.main.pages.BasePage;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

public class Check_Edit_Button_Disabled_In_DetailTab_GF {

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

	@Test(enabled = true, description = "Check Edit Button disabled in tabbed view GF (GF created by another user, and in the same time user does not have a curator role)")
	public void check_Edit_Button_Disabled() {
		SyngentaReporter reporter = new SyngentaReporter();
		LandingPage lp = LandingPage.getLandingPage();
		HomePage homepage = lp.goToHomePage();
		String gfName = "GTest1";
		try {

			String step1 = "Go to Find -> GF/RNAi Triggers/ROI/Promoter Search SyLK Page appears";
			SearchSylkPage search = homepage.goToGFRNAiTriggerROIpromoter();
			reporter.reportPass(step1);
			String step2 = "1- Select a user's username different from yours from \"Added By\" drop down list. "
					+ "2- Select \"Genetic Feature\" from \"Type\" drop down list. "
					+ "3- Click on \"Search\" button. Search SyLK Page appears with search results";
			String user = "Elkomy, Mohamed";
			search.selectAddedBy(user);
			search.selectType("genetic feature");
			search.enterSylkSearch(gfName);
			search.clickSearch();
			reporter.reportPass(step2);
			GeneticFeaturePage gfPage = null;
			BasePage page = search.clickAndOpenThisGF(gfName);
			String step3 = "Click on one of the genetic features displayed in the search results which no evidence associated to it, and should not be nominated as a DB Lead.";
			reporter.reportPass(step3);

			if (page instanceof GeneticFeaturePage) {
				gfPage = (GeneticFeaturePage) page;
				String step = "check that an edit button exists in Tab view in \"Detail\" Tab, and that it is disabled. Edit Button is found disabled.";
				gfPage = gfPage.clickDetailTab();
				boolean enabled = gfPage.isEditButtonUnderDetailTabEnabled();
				reporter.verifyTrue(!enabled, step);
				String toolTip = gfPage.getEditButtonToolTipDetailedTab();
				String stepHoover = "Hover over the edit button. a tool tip with \"GF was created by another user tXXXXXX\" message appears";
				boolean hooverPass = false;
				if (StringUtils.containsIgnoreCase(toolTip,
						"Genetic Feature was created by another user")) {
					hooverPass = true;
				}
				reporter.verifyTrue(hooverPass, stepHoover);
			}
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