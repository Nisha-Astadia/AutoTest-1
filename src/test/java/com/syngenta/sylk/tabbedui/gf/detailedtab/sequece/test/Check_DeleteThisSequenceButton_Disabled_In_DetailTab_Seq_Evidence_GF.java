package com.syngenta.sylk.tabbedui.gf.detailedtab.sequece.test;

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
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

/**
 * @author Nisha Pillai
 * 
 */
public class Check_DeleteThisSequenceButton_Disabled_In_DetailTab_Seq_Evidence_GF {

	private LandingPage lp;
	private HomePage homepage;
	private SearchSylkPage searchSylkpage;
	private GeneticFeaturePage gfPage;

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
	@Test(enabled = true, description = "Check 'Delete This Sequence' Button disabled in Detail Tab of Sequence section in tabbed view GF(Sequence has associated Evidences) ", dataProvider = "testData", groups = {
			"Check_DeleteThisSequenceButton_Disabled_In_DetailTab_SequenceSec_GF",
			"tabbedui", "Genetic Feature", "regression"})
	public void gfDeleteButtonDisabledinTabbedView(String testDescription,
			String row_num, HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();
		String user = columns.get("user");
		try {

			// step1

			reporter.reportPass("Login to SyLK");

			// Step 2
			this.searchSylkpage = this.homepage.goToGFRNAiTriggerROIpromoter();

			reporter.verifyEqual(this.searchSylkpage.getPageTitle(),
					PageTitles.search_sylk_page_title,
					"Select menu item 'GF/RNAi Triggers/ROI/Promoter' and open Search page.");
			// step 3
			this.searchSylkpage.selectAddedBy(user);

			this.searchSylkpage.selectType(columns.get("selectType"));

			this.searchSylkpage = this.searchSylkpage.clickSearch();

			int count = this.searchSylkpage.getTotalResultCount();
			if (count == 0) {
				reporter.assertThisAsFail("When searched for GF for this user="
						+ user + " resulted in zero results");
			} else {
				reporter.reportPass("When searched for GF for this user="
						+ user + " displays " + count + " results");
			}

			GeneticFeaturePage gfPage = (GeneticFeaturePage) this.searchSylkpage
					.clickAndOpenGFWithLiteratureEvidenceSequence();

			reporter.verifyEqual(
					gfPage.getPageTitle(),
					PageTitles.genetic_feature_page_title,
					"Genetic Feature Tabbed Page appears when clicked on one of the genetic feature displayed in the search results.");

			gfPage.clickDetailSequenceTab();

			boolean enabled = gfPage.isDeleteThisSequenceEnabled();
			reporter.verifyTrue(
					!enabled,
					"In Detail tab , check the existence of the  'Delete This Sequence' button and that it is disabled");

			String toolTip = gfPage.getDeleteButtonToolTip();

			reporter.verifyEqual(
					toolTip,
					"some sequences have associated evidences.",
					"a pop-up with \"some sequences have associated evidences\" message appears. Actual message="
							+ toolTip);

			// gfPage = gfPage.clickOnEvidenceSequenceTab();
			// ViewLiteratureEvidenceDetailsPageSequence viewLit = gfPage
			// .clickviewLiteratureEvidenceSequence(0);
			// gfPage = viewLit.clickOnDelete();

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
