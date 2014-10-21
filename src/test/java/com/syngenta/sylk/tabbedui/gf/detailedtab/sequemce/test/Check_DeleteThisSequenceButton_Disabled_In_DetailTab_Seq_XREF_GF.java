package com.syngenta.sylk.tabbedui.gf.detailedtab.sequemce.test;

import java.util.HashMap;

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

public class Check_DeleteThisSequenceButton_Disabled_In_DetailTab_Seq_XREF_GF {

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

	@Test(enabled = true, description = "Check 'Delete This Sequence' Button disabled in Detail Tab of Sequence section in tabbed view GF(GF has an XREF created by another user) ", dataProvider = "testData", groups = {
			"Check_DeleteThisSequenceButton_Disabled_In_DetailTab_Seq_XREF_GFF",
			"tabbedui", "Genetic Feature", "regression"})
	public void gfDeleteButtonDisabledinTabbedView(String testDescription,
			String row_num, HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();
		LandingPage lp = LandingPage.getLandingPage();
		HomePage homepage = lp.goToHomePage();
		String gfName = "selenium_XREF";
		try {

			String step1 = "Go to Find -> GF/RNAi Triggers/ROI/Promoter Search SyLK Page appears";
			SearchSylkPage search = homepage.goToGFRNAiTriggerROIpromoter();
			reporter.reportPass(step1);
			String step2 = "1- Select your username from \"Added By\" drop down list. "
					+ "2- Select \"Genetic Feature\" from \"Type\" drop down list. "
					+ "3- Click on \"Search\" button. Search SyLK Page appears with search results";
			String user = "Pillai, Nisha";
			search.selectAddedBy(user);
			search.selectType("genetic feature");
			search.enterSylkSearch(gfName);
			search.clickSearch();
			reporter.reportPass(step2);
			GeneticFeaturePage gfPage = null;
			BasePage page = search.clickAndOpenThisGF(gfName);
			String step3 = "Click on one of the genetic features displayed in the search results which has an XREF created by another user.";
			reporter.reportPass(step3);

			if (page instanceof GeneticFeaturePage) {
				gfPage = (GeneticFeaturePage) page;
				String step = "In Details tab, check the existence of the  \"Delete This Sequence\" button and that it is Disabled.";
				gfPage = gfPage.clickDetailTab();
				boolean enabled = gfPage.isDeleteThisSequenceEnabled();
				reporter.verifyTrue(!enabled, step);
				String toolTip = gfPage.getDeleteThisSequenceButtonToolTip();
				String stepHoover = "Hover over the \"Delete This Sequence\" button. Tool tip appearing showing \"There are some xrefs created by other user\" ";
				boolean hooverPass = false;
				if (StringUtils.containsIgnoreCase(toolTip,
						"There are some xrefs created by other user")) {
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
