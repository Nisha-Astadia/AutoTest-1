package com.syngenta.sylk.region.of.interest.sprint2.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.AddNewRegionOfInterestROIPage;

public class Check_EnterCorrectStartValue {

	private HomePage homepage;
	private LandingPage lp;

	@BeforeMethod(alwaysRun = true)
	public void testSetUp() {
		this.lp = LandingPage.getLandingPage();
		this.homepage = this.lp.goToHomePage();
	}

	@AfterClass(alwaysRun = true)
	public void cleanUp() {

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}

	@Test(enabled = true, description = "Check that species is required ", groups = {
			"Check_CheckThatSpeciesIsRequired", "ROI", "regression"})
	public void checkAlreadyExistinGF_ContainerSourceCode() {

		CommonLibrary common = new CommonLibrary();
		SyngentaReporter reporter = new SyngentaReporter();

		try {

			reporter.reportPass("Login to SyLK");

			AddNewRegionOfInterestROIPage ROIPage = this.homepage
					.goToROIDetailsPage();

			reporter.verifyEqual(ROIPage.getPageTitle(),
					PageTitles.add_New_ROI_Page_title,
					("'Add New Region Of Interest (ROI)' Page Opens up "));
			reporter.reportPass("Press Add ROI, ROI details page will be openned");
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("regionofinteresttype", "Gene-based");
			map.put("sourcespecies", "Capsicum annuum");
			map.put("referencegenomeandversion", "pepper_v3");
			map.put("chromosomescaffoldcontig", "123");
			map.put("start", "1");
			HashMap<String, String> returnMap = ROIPage.enterTheseValues(map);

			if (returnMap.size() != 0) {
				Iterator<Entry<String, String>> entries = returnMap.entrySet()
						.iterator();
				while (entries.hasNext()) {
					Entry<String, String> entry = entries.next();
					String key = entry.getKey();
					String value = entry.getValue();
					reporter.verifyThisAsFail(value);

				}
			} else {
				reporter.reportPass("Enter "
						+ map.get("start")
						+ " in the start field same number exactly as in the start_pos col.");
			}

			boolean enabled = ROIPage.isEndEnabled();

			reporter.verifyTrue(
					enabled,
					"As the number "
							+ map.get("start")
							+ " in start passes the checks, then the next field \"End\" is enabled");

			map = new HashMap<String, String>();
			map.put("start", "10");
			returnMap = ROIPage.enterTheseValues(map);

			if (returnMap.size() != 0) {
				Iterator<Entry<String, String>> entries = returnMap.entrySet()
						.iterator();
				while (entries.hasNext()) {
					Entry<String, String> entry = entries.next();
					String key = entry.getKey();
					String value = entry.getValue();
					reporter.verifyThisAsFail(value);
				}
			} else {
				reporter.reportPass("Enter "
						+ map.get("start")
						+ " in the start field any integer number between the start_pos and end_pos.");
			}

			enabled = ROIPage.isEndEnabled();

			reporter.verifyTrue(
					enabled,
					"As the number "
							+ map.get("start")
							+ " in start passes the checks, then the next field \"End\" is enabled");

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
