package com.syngenta.sylk.menu_add.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.main.pages.BasePage;

/**
 * @author Nisha Pillai
 * 
 */
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
		this.project.clear();
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

	public List<String> enterPrefixInProjectNameAndGetListedProjectNames(
			String string) {
		this.project.clear();
		this.project.sendKeys(string);
		List<String> projName = new ArrayList<String>();
		int counter = 0;
		List<WebElement> uls = null;
		WebElement ul = null;
		List<WebElement> lis = null;
		while (true) {
			uls = this.driver
					.findElements(By
							.cssSelector("ul.ui-autocomplete.ui-menu.ui-widget.ui-widget-content.ui-corner-all"));
			ul = uls.get(1);
			lis = ul.findElements(By.tagName("li"));

			// keep checking if the list is populated
			// try 10 times waiting for 500 milliseconds (1/2 second)
			if (counter != 10) {

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				counter++;
			} else {
				// if the list is not populated after 10 tries. Throw an
				// exception
				throw new SyngentaException(
						"Project Name list/dropdown did not populate with suggestion project names when '"
								+ string + "' was entered.");
			}

			if (lis.size() == 0) {
				continue;
			} else {
				break;// list is populated, break out of the loop.
			}

		}

		for (WebElement li : lis) {
			System.out.println(li.getText());
			if (StringUtils.isNotBlank(li.getText())) {
				projName.add(li.getText());
			}
		}

		System.out.println("projName:::" + projName);
		return projName;
	}
	public void clickOnClose() {
		WebElement close = this.driver.findElement(By
				.cssSelector("a.ui-dialog-titlebar-close.ui-corner-all"));

		close.click();
	}

	public AddNewRegionOfInterestROIPage clickOnAdd(String data) {
		this.project.clear();
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
