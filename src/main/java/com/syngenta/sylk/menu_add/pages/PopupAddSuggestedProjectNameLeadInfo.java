package com.syngenta.sylk.menu_add.pages;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.main.pages.BasePage;

/**
 * @author Nisha Pillai
 * 
 */
public class PopupAddSuggestedProjectNameLeadInfo extends BasePage {

	@FindBy(id = "projectName8")
	private WebElement suggestedProjectName;

	@FindBy(css = "input[class='btn'][value='Add']")
	private WebElement addButton;
	protected PopupAddSuggestedProjectNameLeadInfo(WebDriver driver) {
		super(driver);
	}

	public void enterProjectName(String string) {
		this.suggestedProjectName.clear();
		this.suggestedProjectName.sendKeys(string);
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
		}
		Actions action = new Actions(this.driver);
		action.sendKeys(this.suggestedProjectName, Keys.ARROW_DOWN).sendKeys(
				Keys.ENTER);
		action.perform();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

	public void clickOnAdd() {
		this.addButton.click();
	}

	public BasePage addProjectName(String text) {
		this.enterProjectName(text);
		this.clickOnAdd();
		this.waitForPageToLoad();
		BasePage page = null;
		String title = this.getPageTitle();
		if (StringUtils.equalsIgnoreCase(title,
				PageTitles.genetic_feature_page_title)) {
			page = new GeneticFeaturePage(this.driver);
			PageFactory.initElements(this.driver, page);
		}

		return page;
	}
}
