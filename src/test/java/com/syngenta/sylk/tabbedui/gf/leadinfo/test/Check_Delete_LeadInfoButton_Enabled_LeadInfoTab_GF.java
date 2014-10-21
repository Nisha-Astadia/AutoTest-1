package com.syngenta.sylk.tabbedui.gf.leadinfo.test;

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
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.main.pages.BasePage;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;

public class Check_Delete_LeadInfoButton_Enabled_LeadInfoTab_GF {

	private List<Object[]> testData = new ArrayList<Object[]>();
	private LandingPage lp;
	private HomePage homepage;
	private GeneticFeaturePage gfPage;

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Check_Delete_LeadInfo_Button_Enabled_LeadInfoTab.xlsx");
	}

	@BeforeMethod(alwaysRun = true)
	public void testSetUp() {
		this.lp = LandingPage.getLandingPage();
		this.homepage = this.lp.goToHomePage();
	}

	@DataProvider(name = "getdata", parallel = false)
	public Iterator<Object[]> loadTestData() {
		return this.testData.iterator();
	}

	@AfterClass(alwaysRun = true)
	public void cleanUp() {

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}

	@Test(enabled = true, description = "Check Delete Lead Info Button Enabled from Lead Info tab (User is creator of the nominated GF to DB Lead)", dataProvider = "getdata")
	public void checkDeleteButtonEnabled_LeadInfo(String testDescription,
			String row_num, HashMap<String, String> columns) {
		SyngentaReporter reporter = new SyngentaReporter();
		CommonLibrary common = new CommonLibrary();
		try {
			reporter.reportPass("Login to SyLK");
			String gfName = "selenium_GF1";
			String user = "Pillai, Nisha";
			BasePage page = common.searchAndSelectThisGF(this.homepage, user,
					gfName);
			if (page instanceof GeneticFeaturePage) {
				this.gfPage = (GeneticFeaturePage) page;
			}
			if (this.gfPage == null) {
				this.homepage = common.addANewGeneticFeatureProtein(
						this.homepage, columns, gfName);
				this.gfPage = this.homepage.clickNewGeneticFeatureLink(gfName);
			}
			String step1 = "Go to Find -> GF/RNAi Triggers/ROI/Promoter";
			String step2 = "1- Select your username from \"Added By\" drop down list."
					+ "2- Select \"Genetic Feature\" from \"Type\" drop down list."
					+ "3- Click on \"Search\" button.";
			String step3 = "Click on one of the genetic features displayed in the search results. Genetic Feature Page Tabbed view appears";
			reporter.reportPass(step1);
			reporter.reportPass(step2);
			reporter.reportPass(step3);

			int evdCount = this.gfPage.getEvidenceSequenceCountOnTab();
			if (evdCount == 0) {
				this.gfPage = this.gfPage.clickOnEvidenceSequenceTab();
				this.gfPage = this.gfPage.addEvidenceInSequenceSection(
						this.gfPage, 1);
			}

			int leadCount = this.gfPage.getLeadInfoCountOnTab();
			if (leadCount == 0) {
				this.gfPage = this.gfPage.addLeadInfoAndNominateDatabaseLead(
						this.gfPage, columns);
			}
			String step4 = "IF GF Sequence does not have evidence associated to it, and if it is not nominated as a DB Lead "
					+ "1- Add Evidence to the sequences and Curate the GF "
					+ "2- Add Lead Info (nominated as a Database Lead) "
					+ "Go to GF Page tabbed view";
			reporter.reportPass(step4);

			String step5 = "Click on the Lead Info tab of one of the sequences. You will see an delete button and it should be enabled";
			this.gfPage = this.gfPage.clickOnLeadInfoTab();
			boolean enabled = this.gfPage.isDeleteButtonInLeadInfoEnabled();
			reporter.verifyTrue(enabled, step5);
			String step6 = "click on the enabled Delete Lead "
					+ "Information button.  a confirmation dialog appears,"
					+ " mentioning that the lead as well as, Regulatory Info, "
					+ "Lead Function Source, Suggested Project Name will be deleted";
			this.gfPage = this.gfPage.clickOnLeadInfoDeleteButton();
			reporter.reportPass(step6);
			this.gfPage.deleteEvidenceSequenceTab();

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
