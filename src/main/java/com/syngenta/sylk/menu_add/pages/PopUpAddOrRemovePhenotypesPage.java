package com.syngenta.sylk.menu_add.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.syngenta.sylk.libraries.SyngentaException;
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

	private HashMap<String, String> map = new HashMap<String, String>();

	private List<String> list = new ArrayList<String>();

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

	private void enterPhenoType(String string) {
		this.phenotype.sendKeys(string);
	}

	private void clickFind() {
		this.map = new HashMap<String, String>();
		this.find.click();
		String error = this.isThereAnSearchKeyWordError();
		if (StringUtils.isNotBlank(error)) {
			throw new SyngentaException("Error when find is clicked: " + error);
		} else {
			this.waitForWebElement(By.id("phenotypesSearchResult"));
		}
		this.loadClassLevelMapOfPhenotypes();
	}

	public void enterSearchKeyAndClickOnFind(String key) {
		this.enterPhenoType(key);
		this.clickFind();
	}
	public String isThereAnSearchKeyWordError() {
		WebElement error = this.driver.findElement(By
				.id("phenotype_keyword_error"));
		String errorString = error.getText();
		return errorString;
	}

	// internally used by other methods
	// This methods should not be called from ouside and hence private
	private HashMap<String, String> loadClassLevelMapOfPhenotypes() {
		this.map = new HashMap<String, String>();
		this.list = new ArrayList<String>();
		WebElement element = this.driver.findElement(By
				.id("phenotypesSearchResult"));
		Select selectList = new Select(element);
		List<WebElement> options = selectList.getOptions();
		for (WebElement option : options) {
			String s = option.getText();
			this.map.put(s, option.getAttribute("value"));
			this.list.add(s);
		}

		return this.map;
	}

	public List<String> getListOfPhenotypes() {
		return this.list;
	}

	public void AddThisPhenotype(String data) {
		if (this.map.size() != 0) {
			WebElement element = this.driver.findElement(By
					.id("phenotypesSearchResult"));
			Select selectList = new Select(element);
			selectList.deselectAll();
			if (this.map.containsKey(data)) {
				selectList.selectByValue(this.map.get(data));
			}
			if (selectList.getAllSelectedOptions().size() == 0) {
				throw new SyngentaException("Could not make a selection.");
			}
		} else {
			throw new SyngentaException(
					"There is no phenotype listed in the select box to make a selection.");
		}
		WebElement addSelectedPhenotype = this.driver.findElement(By
				.id("addSelectedPhenotypeButton"));
		addSelectedPhenotype.click();
	}

	public void AddThisPhenotype(List<String> data) {
		if (this.map.size() != 0) {
			WebElement element = this.driver.findElement(By
					.id("phenotypesSearchResult"));
			Select selectList = new Select(element);
			selectList.deselectAll();
			for (String s : data) {
				selectList.selectByValue(this.map.get(s));
			}
		} else {
			throw new SyngentaException(
					"There is no phenotype listed in the select box to make a selection.");
		}
		WebElement addSelectedPhenotype = this.driver.findElement(By
				.id("addSelectedPhenotypeButton"));
		addSelectedPhenotype.click();
	}
	public String getNameOfThePhenoTypeSelectBox() {
		try {
			String allText = this.driver.findElement(
					By.id("phenotypesSearchResultDiv")).getText();
			allText = StringUtils.replaceChars(allText, '\n', '$');
			String name = StringUtils.substringBefore(allText, "$");
			return name;
		} catch (Exception e) {
			return null;
		}
	}
	public void searchAddThisPhenotype(String key, String data) {
		this.enterSearchKeyAndClickOnFind(key);
		if (this.map.size() != 0) {
			WebElement element = this.driver.findElement(By
					.id("phenotypesSearchResult"));
			Select selectList = new Select(element);
			selectList.deselectAll();
			if (this.map.containsKey(data)) {
				selectList.selectByValue(this.map.get(data));
			}
			if (selectList.getAllSelectedOptions().size() == 0) {
				throw new SyngentaException("Could not make a selection.");
			}
		} else {
			throw new SyngentaException(
					"There is no phenotype listed in the select box to make a selection.");
		}

	}

	public AddNewRegionOfInterestROIPage clickOnClose() {
		this.close.click();
		AddNewRegionOfInterestROIPage page = new AddNewRegionOfInterestROIPage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}
}
