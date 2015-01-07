package com.syngenta.sylk.region.of.interest.sprint2.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
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

/**
 * @author Nisha Pillai
 * 
 */
public class Check_ThatTheNumberEnteredInStartFieldMustBeLessThanTotalLengthOfTheCSCfield {

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

	@Test(enabled = true, description = " Check that the number entered in start field must be less than the total length of the chromosome / contig / scaffold field  ", groups = {
			"Check_ThatTheNumberEnteredInStartFieldMustBeLessThanTotalLengthOfTheCSCfield",
			"ROI", "regression"})
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
			map.put("start", "1204261");

			HashMap<String, String> returnMap = ROIPage.enterTheseValues(map);
			int actual_chromosome_length = NumberUtils.toInt(ROIPage
					.getHiddenChromosomeLength());
			int testdata_start = NumberUtils.toInt(map.get("start"));

			if (returnMap.size() != 0) {
				Iterator<Entry<String, String>> entries = returnMap.entrySet()
						.iterator();
				while (entries.hasNext()) {
					Entry<String, String> entry = entries.next();
					String key = entry.getKey();
					String value = entry.getValue();
					if (!StringUtils.equalsIgnoreCase(key, "start")) {
						reporter.verifyThisAsFail(value);
					} else {
						// we don't expect start field to be disabled and hence
						// we report it as a failure
						if (StringUtils
								.containsIgnoreCase(value, "is disabled")) {
							reporter.verifyThisAsFail(value);
						} else {
							reporter.reportPass("If the number entered in start is greater than the chromosome length a validation error should be displayed. Actual Validation error seen on screen:"
									+ value);
							reporter.reportPass(value);
						}
					}
				}
			} else {
				if (testdata_start > actual_chromosome_length) {
					reporter.verifyThisAsFail("The number entered in start field must be less than the total length of the chromosome / contig / scaffold else an alert error must ne displayed.");
				}
			}

			boolean enabled = ROIPage.isEndEnabled();

			reporter.verifyTrue(
					!enabled,
					"As the number not passes the checks, then the next field \"End\" is not enabled");

			String startValue = ROIPage.getStartValue();
			if (StringUtils.isNotBlank(startValue)) {
				reporter.verifyThisAsFail("The start field is cleared");
			}

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
