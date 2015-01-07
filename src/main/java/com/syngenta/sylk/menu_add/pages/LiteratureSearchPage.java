package com.syngenta.sylk.menu_add.pages;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.main.pages.BasePage;
import com.syngenta.sylk.main.pages.MenuPage;

/**
 * @author Nisha Pillai
 * 
 */
public class LiteratureSearchPage extends MenuPage {

	@FindBy(css = "div#formsFormBd div div input")
	private WebElement searchBox;

	@FindBy(id = "searchButton")
	private WebElement searchButton;
	public LiteratureSearchPage(WebDriver driver) {
		super(driver);
	}

	private void enterSearch(String text) {
		this.searchBox.sendKeys(text);
	}

	private void clicOnSearchButton() {
		this.searchButton.click();
	}

	public BasePage searchThis(String searchText) {
		String message = null;
		this.enterSearch(searchText);
		this.clicOnSearchButton();
		this.waitForPageToLoad();
		this.waitForAjax();
		String title = this.getPageTitle();
		if (StringUtils
				.equalsIgnoreCase(
						title,
						PageTitles.Create_Literature_Evidence_Details_for_GF_page_title)) {
			CreateLiteratureEvidenceDetailsForGeneticFeaturePage page = new CreateLiteratureEvidenceDetailsForGeneticFeaturePage(
					this.driver);
			PageFactory.initElements(this.driver, page);
			return page;
		} else if (StringUtils
				.equalsIgnoreCase(
						title,
						PageTitles.Create_Literature_Evidence_Details_for_RNAi_page_title)) {
			CreateLiteratureEvidenceDetailsForRNAiPage page = new CreateLiteratureEvidenceDetailsForRNAiPage(
					this.driver);
			PageFactory.initElements(this.driver, page);
			return page;
		} else if (StringUtils.equalsIgnoreCase(title,
				PageTitles.literature_search_page_title)) {
			LiteratureSearchPage page = new LiteratureSearchPage(this.driver);
			PageFactory.initElements(this.driver, page);
			return page;
		} else if (StringUtils
				.equalsIgnoreCase(
						title,
						PageTitles.Create_Literature_Evidence_Details_for_Sequence_page_title)) {
			CreateLiteratureEvidenceDetailsForGeneticFeaturePage page = new CreateLiteratureEvidenceDetailsForGeneticFeaturePage(
					this.driver);
			PageFactory.initElements(this.driver, page);
			return page;
		} else if (StringUtils.containsIgnoreCase(title, "Error")) {
			String errorCode = null;
			WebElement errorDiv = this.driver
					.findElement(By
							.cssSelector("div#main div.cont div.formsItemInputWrapper"));
			if (errorDiv != null) {
				errorCode = errorDiv.getText();
			}

			if (!StringUtils.isNotBlank(errorCode)) {
				message = "Click on search external database button resulted in Error page and no Error Code found";
			} else {
				message = "Click on search external database button resulted in Error page. Error Code = "
						+ errorCode;
			}
			throw new SyngentaException(message);
		}
		return null;
	}
}
