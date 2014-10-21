package com.syngenta.sylk.tabbedui.gf.detailedtab.sequemce.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
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
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.ViewLiteratureEvidenceDetailsPageSequence;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

public class Check_DeleteThisSequenceButton_Enabled_In_DetailTab_Seq_GF {
	private LandingPage lp;
	private HomePage homepage;
	private SearchSylkPage searchSylkpage;

	private List<Object[]> testData = new ArrayList<Object[]>();

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Check_DeleteThisSequenceButton_DetailTab_SeqSec.xlsx");
	}

	@AfterClass(alwaysRun = true)
	public void quitDriverIfLeftOpen() {

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}

	@BeforeMethod(alwaysRun = true)
	public void testSetUp() {
		this.lp = LandingPage.getLandingPage();
		this.homepage = this.lp.goToHomePage();
	}

	@DataProvider(name = "testData", parallel = false)
	public Iterator<Object[]> loadTestData() {

		return this.testData.iterator();
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp() {

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}
	@Test(enabled = true, description = "Check Delete This Sequence Button enabled in Detail Tab of Sequence section in tabbed view GF ", dataProvider = "testData", groups = {
			"Check_DeleteThisSequenceButton_Enabled_In_DetailTab_SequenceSec_GF",
			"tabbedui", "Genetic Feature", "regression"})
	public void gfDeleteButtonEnabledinTabbedView(String testDescription,
			String row_num, HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();
		CommonLibrary common = new CommonLibrary();
		// step1
		try {
			reporter.reportPass("Login to SyLK");

			String gfName = "Selenium_GF2";
			String user = "Pillai, Nisha";

			GeneticFeaturePage gfPage = null;
			BasePage page = common.searchAndSelectThisGF(this.homepage, user,
					gfName);
			if (page instanceof GeneticFeaturePage) {
				gfPage = (GeneticFeaturePage) page;
			}
			if (gfPage == null) {
				this.homepage = common.addANewGeneticFeature(this.homepage,
						columns, columns.get("gfName"));
				gfPage = this.homepage.clickNewGeneticFeatureLink(columns
						.get("gfName"));
			}
			String step1 = "Go to Find -> GF/RNAi Triggers/ROI/Promoter";
			String step2 = "1- Select your username from \"Added By\" drop down list."
					+ "2- Select \"Genetic Feature\" from \"Type\" drop down list."
					+ "3- Click on \"Search\" button.";
			reporter.reportPass(step1);
			reporter.reportPass(step2);

			reporter.verifyEqual(
					gfPage.getPageTitle(),
					PageTitles.genetic_feature_page_title,
					" click on one of the displayed GF on search result page opens 'Genetic Feature Page' Tabbed view .");

			int evdCount = gfPage.getEvidenceSequenceCountOnTab();
			if (evdCount != 0) {
				gfPage = gfPage.clickOnEvidenceSequenceTab();
				ViewLiteratureEvidenceDetailsPageSequence viewLit = gfPage
						.clickviewLiteratureEvidenceSequence(0);
				gfPage = viewLit.clickOnDelete();

			}
			// click on detail tab
			gfPage.clickDetailSequenceTab();

			reporter.reportPass("Check that the GF has no evidence associated to it, if there is any evidence, delete all associated evidence");

			boolean enabled = gfPage.isDeleteThisSequenceEnabled();
			reporter.verifyTrue(
					enabled,
					"In Detail tab, check the existence of the  'Delete This Sequence' button and that it is enabled");

			gfPage.clickDeleteThisSequence();

			reporter.reportPass("Click on delete button in confirm delete message and the Sequence is deleted");
			gfPage.clickDeleleThisGeneticFeature();

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
