package com.syngenta.sylk.tabbedui.roitab.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.AfterClass;
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
import com.syngenta.sylk.menu_add.pages.AddNewGeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.AddNewROIPage;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.ROIDetailPage;
import com.syngenta.sylk.menu_add.pages.SearchTargetGenePage;
import com.syngenta.sylk.menu_find.pages.RNAiPage;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

public class Check_ROI_Tab_In_GF {

	private AddNewGeneticFeaturePage addNewGFPage;
	private LandingPage lp;
	private HomePage homepage;
	private GeneticFeaturePage gfPage;
	private String proteinSymbol;
	private SearchSylkPage searchSylkpage;
	private RNAiPage rnai;

	private List<Object[]> testData = new ArrayList<Object[]>();

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("CheckROITabInGF.xlsx");
	}

	@BeforeMethod(alwaysRun = true)
	public void testSetUp() {
		this.lp = LandingPage.getLandingPage();
		this.homepage = this.lp.goToHomePage();
	}

	@DataProvider(name = "checkrnaiingf", parallel = false)
	public Iterator<Object[]> loadTestData() {
		return this.testData.iterator();
	}

	@AfterClass(alwaysRun = true)
	public void cleanUp() {

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}

	@Test(enabled = true, description = "Check ROI Tab in Genetic Feature", dataProvider = "checkrnaiingf", groups = {
			"CheckROITabInGFTest", "ROI", "regression"})
	public void addNewGeneticFeatureProtein(String testDescription,
			String row_num, HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();
		CommonLibrary common = new CommonLibrary();
		try {

			reporter.reportPass("Login to SyLK");
			String gfName = "selenium_GF1";
			String user = "Pillai, Nisha";
			// step 6
			// check has to be made to see if its navigated to the
			// "Add New Genetic Feature page"
			BasePage page = common.searchAndSelectThisGF(this.homepage, user,
					gfName);
			if (page instanceof GeneticFeaturePage) {
				this.gfPage = (GeneticFeaturePage) page;
			}
			if (this.gfPage == null) {
				gfName = "selenium_GF1";
				this.homepage = common.addANewGeneticFeatureProtein(
						this.homepage, columns, gfName);
				this.gfPage = this.homepage.clickNewGeneticFeatureLink(gfName);
			}
			reporter.reportPass("Create a genetic feature");

			String roiName = "Acanthus ebracteatus_test_line_lotus_v1_xyz_10_20";

			this.homepage
					.deleteThisRoi(this.homepage, roiName, "Pillai, Nisha");

			page = null;
			AddNewROIPage addnewROIPage = this.homepage.goToAddNewROIPage();
			reporter.verifyEqual(addnewROIPage.getPageTitle(),
					PageTitles.add_New_ROI_Page_title,
					"Select menu item 'Region Of Interest' and open page.");

			// Add ROI
			SearchTargetGenePage searchTargetgenePage = addnewROIPage
					.clickAddGeneToROI();

			searchTargetgenePage.selectAddedBy(columns.get("addedBy"));

			searchTargetgenePage = searchTargetgenePage.clickSearch();

			addnewROIPage = searchTargetgenePage.clickAddGeneForROI();

			addnewROIPage.enterSourceSpecies(columns.get("source_species"));
			addnewROIPage.enterLine(columns.get("line"));
			addnewROIPage
					.selectReferenceGenome(columns.get("reference_genome"));
			addnewROIPage.enterChromosome(columns.get("Chromosome"));
			addnewROIPage.enterFrom(columns.get("Chromosome_from"));
			addnewROIPage.enterTo(columns.get("Chromosome_to"));

			addnewROIPage.selectRegionOfInterestType(columns
					.get("Region_Of_Interest_Type"));
			addnewROIPage.enterSNPs(columns.get("snps"));

			ROIDetailPage roiDetailspage = addnewROIPage
					.ClickAddRegionOfInterest();
			reporter.reportPass("ROI is added with a added  gene");

			reporter.verifyEqual(roiDetailspage.getPageTitle(),
					PageTitles.roi_detail_page, "ROI is created successfully.");

			// step 5
			this.searchSylkpage = this.homepage.goToGFRNAiTriggerROIpromoter();

			reporter.verifyEqual(this.searchSylkpage.getPageTitle(),
					PageTitles.search_sylk_page_title,
					"Select menu item 'GF/RNAi Triggers/ROI/Promoter' and open Search page.");

			this.searchSylkpage.selectAddedBy("Pillai, Nisha");

			this.searchSylkpage.selectType("Genetic Feature");

			this.searchSylkpage = this.searchSylkpage.clickSearch();
			this.gfPage = (GeneticFeaturePage) this.searchSylkpage
					.clickAndOpenThisGF(gfName);

			this.gfPage = this.gfPage.clickOnROITab();

			reporter.verifyEqual(
					this.gfPage.getPageTitle(),
					PageTitles.genetic_feature_page_title,
					"Open the added genetic feature and click on ROI tab and ROI tab becomes active");
			HashMap<String, String> headers = this.gfPage
					.getAllColumnHeadersInROITab();
			System.out.println("column headers:" + headers);
			reporter.verifyEqual("" + headers.size(), "7",
					"ROI tab has seven column headers");
			reporter.reportPass("The following headers are displayes : "
					+ headers.toString());
			// this.homepage = gfPage.gotoHomePage();
			// this.homepage
			// .deleteThisRoi(this.homepage, roiName, "Pillai, Nisha");

		} catch (Exception e) {
			e.printStackTrace();
			throw new SyngentaException("Test Failed:"
					+ new CommonLibrary().getStackTrace(e));
		} finally {
			reporter.assertAll();
		}
	}

}
