package com.syngenta.sylk.tabbedui.gf.detailedtab.test;

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

/**
 * @author Nisha Pillai
 * 
 */
public class Check_GenomicDetail_In_NewGFTab_GF {

	private List<Object[]> testData = new ArrayList<Object[]>();
	private LandingPage lp;
	private HomePage homepage;

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Detailed_Tabbed_View_GenericFile_GF.xlsx");
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

	@AfterClass(alwaysRun = true)
	public void cleanUp() {

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}

	@Test(enabled = true, description = "Check Genomic Detail in new GF tab view", dataProvider = "testData", groups = {
			"Check_GenomicDetail_In_NewGFTab_GF", "GF", "regression"})
	public void geomicDetailInNewGFTab(String testDescription, String row_num,
			HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();
		CommonLibrary common = new CommonLibrary();
		try {
			reporter.reportPass("Login to SyLK");
			String gfName = "selenium_GF1";
			String user = "Pillai, Nisha";
			String expected_chromosome = "test_chromosome";
			String expected_ecotype = "test_ecotype";
			// step 6
			// check has to be made to see if its navigated to the
			// "Add New Genetic Feature page"
			GeneticFeaturePage gfPage = null;
			BasePage page = common.searchAndSelectThisGF(this.homepage, user,
					gfName);
			if (page instanceof GeneticFeaturePage) {
				gfPage = (GeneticFeaturePage) page;
			}
			if (gfPage == null) {
				gfName = "selenium_GF1";
				this.homepage = common.addANewGeneticFeatureProtein(
						this.homepage, columns, gfName);
				gfPage = this.homepage.clickNewGeneticFeatureLink(gfName);
			}

			String step1 = "Go to Find -> GF/RNAi Triggers/ROI/Promoter";
			String step2 = "1- Select your username from \"Added By\" drop down list."
					+ "2- Select \"Genetic Feature\" from \"Type\" drop down list."
					+ "3- Click on \"Search\" button.";
			reporter.reportPass(step1);
			reporter.reportPass(step2);
			gfPage = gfPage.clickDetailTab();
			String step3 = "Select the \"Detail\" Tab";
			reporter.reportPass(step3);
			String ecotype = gfPage.getEcotypeDetailTab();

			reporter.verifyEqual(ecotype, expected_ecotype,

			"In detailed tab of this genetic feature Ecotype should be "
					+ expected_ecotype + " but found to be " + ecotype,
					"In detailed tab of this genetic feature Ecotype should be "
							+ expected_ecotype);

			String chromosome = gfPage.getChromosomeDetailTab();
			reporter.verifyEqual(chromosome, expected_chromosome,

			"In detailed tab of this genetic feature chromosome should be "
					+ expected_chromosome + " but found to be " + chromosome,
					"In detailed tab of this genetic feature chromosome should be "
							+ expected_chromosome);

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
