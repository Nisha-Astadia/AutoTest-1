package com.syngenta.sylk.menu_add.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.BasePage;

public class PopUpHelp extends BasePage {

	protected PopUpHelp(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(css = ".ui-icon ui-icon-closethick")
	private WebElement close;

	public AddNewRegionOfInterestROIPage clickClose() {
		this.close.click();
		this.waitForPageToLoad();
		AddNewRegionOfInterestROIPage page = new AddNewRegionOfInterestROIPage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

}
