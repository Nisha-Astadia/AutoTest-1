package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.MenuPage;

public class ROIDetailPage extends MenuPage {

	public ROIDetailPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public ROIDetailPage clickOnDeleteROIPage() {

		List<WebElement> buttons = this.driver.findElements(By
				.cssSelector("input.btn"));

		for (WebElement button : buttons) {
			if (StringUtils.equalsIgnoreCase(button.getAttribute("value"),
					"Delete This Region Of Interest")) {
				button.click();
				this.driver.switchTo().alert().accept();
				this.waitForPageToLoad();
				this.waitForAjax();
				break;
			}
		}

		ROIDetailPage page = new ROIDetailPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

}
