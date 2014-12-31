package com.syngenta.sylk.menu_add.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.BasePage;

public class PopUpAddProjectPage extends BasePage {

	protected PopUpAddProjectPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "projectName")
	private WebElement project;

	@FindBy(id = "projectAdd")
	private WebElement add;

	public void enterProject(String data) {
		this.project.sendKeys(data);
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
		}
		Actions action = new Actions(this.driver);
		action.sendKeys(this.project, Keys.ARROW_DOWN).sendKeys(Keys.ENTER);
		action.perform();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

	public AddNewRegionOfInterestROIPage clickOnAdd(String data) {
		this.project.sendKeys(data);
		this.add.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		AddNewRegionOfInterestROIPage page = new AddNewRegionOfInterestROIPage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

}
