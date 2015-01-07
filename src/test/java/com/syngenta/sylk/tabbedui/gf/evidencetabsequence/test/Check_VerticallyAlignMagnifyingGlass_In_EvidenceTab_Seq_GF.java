package com.syngenta.sylk.tabbedui.gf.evidencetabsequence.test;

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
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.main.pages.BasePage;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

/**
 * @author Nisha Pillai
 * 
 */
public class Check_VerticallyAlignMagnifyingGlass_In_EvidenceTab_Seq_GF {

	private List<Object[]> testData = new ArrayList<Object[]>();

	private LandingPage lp;
	private HomePage homepage;
	private SearchSylkPage searchSylkpage;

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Check_Vertically_Align_Magnifying_Glass_Evidence_GF_Sequence.xlsx");
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

	@DataProvider(name = "checkEvidenceMagnifyingGlass_vertically", parallel = false)
	public Iterator<Object[]> loadTestData() {

		return this.testData.iterator();
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp() {

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}
	@Test(enabled = true, description = "Check vertically align magnifying glass on literature evidence tab in Sequence section details  and trait components", dataProvider = "checkEvidenceMagnifyingGlass_vertically", groups = {
			"Check_VerticallyAlignMagnifyingGlass_In_EvidenceTab_Seq_GF",
			"Genetic Feature", "regression"})
	public void VerticallyAlignMagnifyingGlassOnLiteratureEvidenceTab(
			String testDescription, String row_num,
			HashMap<String, String> columns) {
		CommonLibrary common = new CommonLibrary();
		SyngentaReporter reporter = new SyngentaReporter();
		try {
			reporter.reportPass("Login to SyLK");
			String gfName = "selenium_GF1";
			String user = "Pillai, Nisha";
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

			reporter.reportPass("Open a genetic feature with tabbed view");

			gfPage = gfPage.clickOnEvidenceSequenceTab();
			reporter.reportPass("Click on evidence tab open up the evidence tab.");

			int evidenceCount = gfPage.getEvidenceSequenceCountOnTab();
			if (evidenceCount < 2) {
				gfPage = gfPage.addEvidenceInSequenceSection(gfPage, 2);
				reporter.reportPass("Created a new evidence as the count for this selected Genetic Feature was one to be able to check vartical alignment of columns.");
			}

			boolean allIsGood = gfPage
					.checkIfMagnifyingGlassImageIsVerticallyAlligned_SequenceSection();

			reporter.assertTrue(allIsGood,
					"All evidence view magnifying glasses should be alligned vertically.");
			gfPage.deleteEvidenceSequenceTab();

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
