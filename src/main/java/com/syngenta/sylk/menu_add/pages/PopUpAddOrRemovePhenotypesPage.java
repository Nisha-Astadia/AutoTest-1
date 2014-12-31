package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.syngenta.sylk.main.pages.BasePage;

public class PopUpAddOrRemovePhenotypesPage extends BasePage {

	protected PopUpAddOrRemovePhenotypesPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "Phenotype")
	private WebElement Phenotype;

	@FindBy(id = "findPhenotypeButton")
	private WebElement find;

	@FindBy(id = "phenotypesSearchResult")
	private WebElement phenotypesSearchResult;

	@FindBy(id = "phenotypeSearchKeyword")
	private WebElement phenotype;

	@FindBy(id = "addSelectedPhenotypeButton")
	private WebElement addSelectedPhenotypes;

	@FindBy(id = "closePhenotypeButton")
	private WebElement close;

	public AddNewRegionOfInterestROIPage findPhenotype(String data) {

		AddNewRegionOfInterestROIPage page = null;
		// PopUpAddOrRemovePhenotypesPage page = null;
		this.phenotype.sendKeys(data);
		this.find.click();

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement elements = this.waitForWebElement(By
				.id("phenotypesSearchResult"));
		Select select = new Select(elements);
		List<WebElement> options = select.getOptions();
		for (WebElement option : options) {
			if (StringUtils.equalsIgnoreCase(option.getText(), data)) {
				select.selectByVisibleText(option.getText());
				option.click();
				this.addSelectedPhenotypes.click();
				this.waitForPageToLoad();
				this.waitForAjax();

				this.waitForPageToLoad();
				this.close.click();
				new AddNewRegionOfInterestROIPage(this.driver);
				PageFactory.initElements(this.driver, page);

			}
		}

		return page;
	}
}
