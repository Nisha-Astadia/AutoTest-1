package com.syngenta.sylk.main.pages;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.menu_add.pages.ConstructNominationPage;
import com.syngenta.sylk.menu_add.pages.ConstructStatusSearchPage;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.RNAiTriggerDetailsPage;
import com.syngenta.sylk.menu_add.pages.ROIDetailPage;
import com.syngenta.sylk.menu_add.pages.ViewLiteratureEvidenceDetailsPageSequence;
import com.syngenta.sylk.menu_find.pages.RNAiPage;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

public class HomePage extends MenuPage {

	@FindBy(name = "searchText")
	private WebElement searchBox;
	@FindBy(name = "searchPlus")
	private WebElement searchButton;
	@FindBy(name = "clearButton")
	private WebElement clearButton;
	private WebDriver driver;

	public HomePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public void enterInSearchBox(String text) {
		this.searchBox.sendKeys(text);
	}

	public HomePage clickSearchButton() {
		this.searchButton.click();
		this.waitForPageToLoad();
		HomePage page = new HomePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public void clickClearButton() {
		this.clearButton.click();
	}

	public void searchSylk(String text) {
		try {
			this.enterInSearchBox(text);
			this.clickSearchButton();
		} catch (Exception e) {
			throw new SyngentaException(
					"Error when searching on HomePage. Exception : "
							+ new CommonLibrary().getStackTrace(e));
		}
	}

	public GeneticFeaturePage clickNewGeneticFeatureLink(String nameOfGene) {

		GeneticFeaturePage gfPage = null;

		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		@SuppressWarnings("unchecked")
		List<WebElement> elements = (List<WebElement>) js
				.executeScript("return jQuery.find(\"table.tableHover.tablesorter strong a\")");

		for (WebElement element : elements) {
			System.out.println(element.getText());
			if (StringUtils.equalsIgnoreCase(nameOfGene, element.getText())) {
				element.click();
				gfPage = new GeneticFeaturePage(this.driver);
				this.waitForPageToLoad();
				this.waitForAjax();
				PageFactory.initElements(this.driver, gfPage);
				break;
			}
		}

		return gfPage;

	}

	public HomePage deleteThisGeneticFeature(HomePage home, String newGFlink) {

		ConstructNominationPage cPage = null;
		ConstructStatusSearchPage cSearchPage = null;
		// click on this gf link
		GeneticFeaturePage gfPage = home.clickNewGeneticFeatureLink(newGFlink);
		if (gfPage == null) {
			SearchSylkPage searchsylkpage = home.goToGFRNAiTriggerROIpromoter();
			searchsylkpage.enterSylkSearch(newGFlink);
			searchsylkpage.selectType("Genetic Feature");
			searchsylkpage = searchsylkpage.clickSearch();
			BasePage basepage = searchsylkpage.clickAndOpenThisGF(newGFlink);
			if (basepage instanceof GeneticFeaturePage) {
				gfPage = (GeneticFeaturePage) basepage;
			} else {
				home = searchsylkpage.gotoHomePage();
				return home;
			}

		}
		// go to contruct tab
		gfPage = gfPage.clickOnConstructTab();

		// click on the link, if any exists
		int totalConstruct = gfPage.getConstructCountOnTab();
		for (int i = 1; i <= totalConstruct; i++) {
			cPage = gfPage.clickConstructNominationIdLink(i);
			// click delete on the condtruct page, if there is a construct
			cSearchPage = cPage.clickDelete();

			home = cSearchPage.gotoHomePage();

			gfPage = home.clickNewGeneticFeatureLink(newGFlink);

			gfPage.clickOnConstructTab();

		}

		// click on project lead tab
		gfPage = gfPage.clickOnProjectLeadTab();
		int totalProjectLeads = gfPage.getProjectLeadsCountOnTab();
		for (int i = 1; i <= totalProjectLeads; i++) {
			// click on the other project link and expand it
			gfPage = gfPage.clickOnTheProjectLinkInProjectLeadTab(i);
			// click on delete button
			gfPage = gfPage.clickOnProjectLeadDeleteButton();
		}

		// on gf detail page click on lead info tab
		gfPage = gfPage.clickOnLeadInfoTab();
		int totalLeadInfo = gfPage.getLeadInfoCountOnTab();
		// click on delete button, if lead info exists
		if (totalLeadInfo > 0) {
			gfPage = gfPage.clickOnLeadInfoDeleteButton();
		}

		// on gf detail page click on evidence tab
		gfPage = gfPage.clickOnEvidenceSequenceTab();
		// open the evidence, if it exists
		int totalEvidence = gfPage.getEvidenceSequenceCountOnTab();
		if (totalEvidence > 0) {
			ViewLiteratureEvidenceDetailsPageSequence viewPage = gfPage
					.clickviewLiteratureEvidenceSequence(0);
			// click on delete button
			gfPage = viewPage.clickOnDelete();
		}

		// click on delete this genetic feature button
		home = gfPage.clickDeleleThisGeneticFeature();
		// make sure this gf is deleted

		return home;

	}

	public HomePage deleteThisGFWithOutCheckingAllTabs(HomePage home,
			String newGFlink) {
		GeneticFeaturePage gfPage = home.clickNewGeneticFeatureLink(newGFlink);
		// go to contruct tab
		gfPage = gfPage.clickOnConstructTab();
		home = gfPage.clickDeleleThisGeneticFeature();
		return home;
	}

	public void deleteThisRNAi(HomePage homepage, String rnaiTriggerName,
			String user) {
		SearchSylkPage search = homepage.goToGFRNAiTriggerROIpromoter();
		RNAiTriggerDetailsPage rnaiTrigger = null;
		RNAiPage rnaiPage = null;
		search.selectAddedBy(user);
		search.selectView("50");
		search.selectType("RNAi");
		search = search.clickSearch();
		BasePage page = search
				.selectThisTriggerFromSearchResult(rnaiTriggerName);
		if (page instanceof SearchSylkPage) {
			((SearchSylkPage) page).gotoHomePage();
		} else if (page instanceof RNAiTriggerDetailsPage) {
			rnaiTrigger = (RNAiTriggerDetailsPage) page;
			rnaiTrigger = rnaiTrigger.clickOnDetailTab();
			rnaiTrigger = rnaiTrigger.deleteTargetGene();
			rnaiTrigger = rnaiTrigger.clickOnDeleteRNAiTrigger();
			rnaiTrigger.gotoHomePage();
		} else if (page instanceof RNAiPage) {
			rnaiPage = (RNAiPage) page;
			rnaiPage.deleteThisRNAiTrigger();
		}
	}

	public void deleteThisRoi(HomePage homepage, String roiName, String user) {
		SearchSylkPage search = homepage.goToGFRNAiTriggerROIpromoter();
		ROIDetailPage roiDetail = null;
		search.selectAddedBy(user);
		search.selectView("50");
		search.selectType("ROI");
		search = search.clickSearch();
		BasePage page = search.selectThisTriggerFromSearchResult(roiName);
		if (page instanceof SearchSylkPage) {
			((SearchSylkPage) page).gotoHomePage();
		} else if (page instanceof ROIDetailPage) {
			roiDetail = (ROIDetailPage) page;
			roiDetail = roiDetail.clickOnDeleteROIPage();
			roiDetail.gotoHomePage();
		}

	}

}
