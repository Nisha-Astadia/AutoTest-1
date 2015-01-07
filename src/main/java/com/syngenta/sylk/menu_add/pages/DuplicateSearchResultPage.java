package com.syngenta.sylk.menu_add.pages;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.main.pages.BasePage;

/**
 * @author Nisha Pillai
 * 
 */
public class DuplicateSearchResultPage extends BasePage {

	protected DuplicateSearchResultPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css = "input[value='Modify Search']")
	private WebElement modifyButton;

	@FindBy(css = "input[value='Search External Databases']")
	private WebElement searchExternalButton;

	@FindBy(css = "input[value='Manual Entry']")
	private WebElement manualButton;

	public AddNewGeneticFeaturePage clickModify() {
		this.modifyButton.click();
		this.waitForPageToLoad();
		AddNewGeneticFeaturePage page = new AddNewGeneticFeaturePage(
				this.driver);
		PageFactory.initElements(this.driver, page);

		return page;
	}

	public BasePage clickExternalDatabase() {
		BasePage page = null;
		this.searchExternalButton.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		String message = null;
		String title = this.getPageTitle();
		if (StringUtils.containsIgnoreCase(title,
				PageTitles.duplicate_search_result_page_title)) {
			page = new DuplicateSearchResultPage(this.driver);
		} else if (StringUtils.containsIgnoreCase(title,
				PageTitles.genetic_feature_search_result_page_title)) {
			page = new GeneticFeatureSearchResultsPage(this.driver);
		} else if (StringUtils.containsIgnoreCase(title,
				PageTitles.import_genetic_feature_page_title)) {
			page = new ImportGeneticFeaturePage(this.driver);
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

		PageFactory.initElements(this.driver, page);

		return page;
	}
	public void clickManual() {
		this.manualButton.click();
		this.waitForPageToLoad();
	}
}
