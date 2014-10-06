package com.syngenta.sylk.tabbedui.test;

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
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;

public class Check_Edit_Button_Enabled_DeatilTab_View_GF {

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

	@Test(enabled = true, description = "Check created by name and time stamp in tabbed view GF Created By and Created Date", dataProvider = "checkrnaiingf", groups = {
			"checkCreatedByAndTimeStampInTabbedGF", "GF", "regression"})
	public void checkCreatedByAndTimeStampGF_CreatedByAndDate(
			String testDescription, String row_num,
			HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();
		CommonLibrary common = new CommonLibrary();
		try {
			reporter.reportPass("Login to SyLK");
			String gfName = "selenium_GF1";
			String user = "Pillai, Nisha";
			// step 6
			// check has to be made to see if its navigated to the
			// "Add New Genetic Feature page"
			GeneticFeaturePage gfPage = common.searchAndSelectThisGF(
					this.homepage, user, gfName);
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

			gfPage = gfPage.clickOnEditDetailTab();
			reporter.reportPass("Click on the edit button");
			gfPage.enterSymbolDetailTab("");
			gfPage = gfPage.clickOnSaveDetailTab();
			String errorMessage = gfPage.getSymbolError();
			reporter.verifyEqual(
					errorMessage,
					"Field is required",
					"Clear the value of \"Symbol\", and click Save an error message \"Field is required\" appears under symbol field ");
			gfPage = gfPage.clickOnCancelDetailTab();

			gfPage = gfPage.clickOnEditDetailTab();
			String symbolUpdated = "updated_value";
			String nameUpdated = "updated_name";
			String ncbiidUpdated = "12345";
			String descUpdated = "updated_description";
			String synonymsUpdated = "updated_synonyms";
			String ncbitaxonidUpdated = "1";
			String syngentancbiupdated = "1";
			String varietyUpdated = "updated_variety";
			String lineUpdated = "updated_line";
			String ecotypeUpdated = "updated_ecotype";

			HashMap<String, String> existingValues = gfPage
					.getAllFieldValuesDetailTab();
			gfPage.enterSymbolDetailTab(symbolUpdated);
			gfPage.enterNameDetailTab(nameUpdated);
			gfPage.enterNCBIIDDetailTab(ncbiidUpdated);
			gfPage.enterDescriptionDetailTab(descUpdated);
			gfPage.enterSynonymsDetailTab(synonymsUpdated);
			gfPage.enterNDBITaxonIdDetailTab(ncbitaxonidUpdated);
			gfPage.enterSyngentaNCBIIdDetailTab(syngentancbiupdated);
			gfPage.enterVarietyDetailTab(varietyUpdated);
			gfPage.enterLineDetailTab(lineUpdated);
			gfPage.enterEcotype(ecotypeUpdated);

			gfPage = gfPage.clickOnSaveDetailTab();

			HashMap<String, String> updatedValues = gfPage
					.getAllFieldValuesDetailTab();

			reporter.verifyEqual(updatedValues.get("symbol"), symbolUpdated,
					"Symbol updated value not saved after edit. Saved value="
							+ updatedValues.get("symbol") + " expected value ="
							+ symbolUpdated,
					"Edit symbol and click on save, new symbol value saved successfully");
			reporter.verifyEqual(updatedValues.get("name"), nameUpdated,
					"Name updated value not saved after edit. Saved value="
							+ updatedValues.get("name") + " expected value ="
							+ nameUpdated,
					"Edit name and click on save, new name value saved successfully");
			reporter.verifyEqual(updatedValues.get("ncbiid"), ncbiidUpdated,
					"NCBI ID updated value not saved after edit. Saved value="
							+ updatedValues.get("ncbiid") + " expected value ="
							+ ncbiidUpdated,
					"Edit NCBI ID and click on save, new NCBI ID value saved successfully");

			reporter.verifyEqual(updatedValues.get("description"), descUpdated,
					"Description updated value not saved after edit. Saved value="
							+ updatedValues.get("description")
							+ " expected value =" + descUpdated,
					"Edit Description and click on save, new Description value saved successfully");

			reporter.verifyEqual(updatedValues.get("synonyms"),
					synonymsUpdated,
					"Synonyms updated value not saved after edit. Saved value="
							+ updatedValues.get("synonyms")
							+ " expected value =" + synonymsUpdated,
					"Edit Synonyms and click on save, new Synonyms value saved successfully");

			reporter.verifyEqual(
					updatedValues.get("taxonid"),
					ncbitaxonidUpdated,
					"NCBI Taxon Id updated value not saved after edit. Saved value="
							+ updatedValues.get("taxonid")
							+ " expected value =" + ncbitaxonidUpdated,
					"Edit NCBI Taxon Id and click on save, new NCBI Taxon Id value saved successfully");

			reporter.verifyEqual(
					updatedValues.get("syngentataxonid"),
					syngentancbiupdated,
					"Syngenta Taxon Id updated value not saved after edit. Saved value="
							+ updatedValues.get("syngentataxonid")
							+ " expected value =" + syngentancbiupdated,
					"Edit Syngenta Taxon Id and click on save, new Syngenta Taxon Id value saved successfully");

			reporter.verifyEqual(updatedValues.get("variety"), varietyUpdated,
					"Variety updated value not saved after edit. Saved value="
							+ updatedValues.get("variety")
							+ " expected value =" + varietyUpdated,
					"Edit Variety and click on save, new Variety value saved successfully");

			reporter.verifyEqual(updatedValues.get("line"), lineUpdated,
					"Line updated value not saved after edit. Saved value="
							+ updatedValues.get("line") + " expected value ="
							+ lineUpdated,
					"Edit Line and click on save, new Variety value saved successfully");

			reporter.verifyEqual(updatedValues.get("ecotype"), ecotypeUpdated,
					"Ecotype updated value not saved after edit. Saved value="
							+ updatedValues.get("ecotype")
							+ " expected value =" + ecotypeUpdated,
					"Edit Ecotype and click on save, new Ecotype value saved successfully");

			/*
			 * get the data back to its original shape
			 */
			gfPage = gfPage.clickOnEditDetailTab();
			gfPage.enterSymbolDetailTab(existingValues.get("symbol"));
			gfPage.enterNameDetailTab(existingValues.get("name"));
			gfPage.enterNCBIIDDetailTab(existingValues.get("ncbiid"));
			gfPage.enterDescriptionDetailTab(existingValues.get("description"));
			gfPage.enterSynonymsDetailTab(existingValues.get("synonyms"));
			gfPage.enterNDBITaxonIdDetailTab(existingValues.get("taxonid"));
			gfPage.enterSyngentaNCBIIdDetailTab(existingValues
					.get("syngentataxonid"));
			gfPage.enterVarietyDetailTab(existingValues.get("variety"));
			gfPage.enterLineDetailTab(existingValues.get("line"));
			gfPage.enterEcotype(existingValues.get("ecotype"));
			gfPage = gfPage.clickOnSaveDetailTab();
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
