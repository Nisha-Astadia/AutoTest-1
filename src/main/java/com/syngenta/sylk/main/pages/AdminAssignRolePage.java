package com.syngenta.sylk.main.pages;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AdminAssignRolePage extends MenuPage {

	@FindBy(id = "term")
	private WebElement searchBox;
	@FindBy(id = "findUser")
	private WebElement findUserButton;
	@FindBy(css = "input[class='btn'][value='Clear']")
	private WebElement clearButton;
	@FindBy(css = "div#users table")
	private WebElement userTable;

	@FindBy(css = "div#users")
	private WebElement roleMainDiv;

	@FindBy(id = "roles")
	private WebElement roleSelect;
	@FindBy(css = "input[class='btn'][value='Assign Selected Roles to user']")
	private WebElement addRoleButton;
	protected AdminAssignRolePage(WebDriver driver) {
		super(driver);
	}

	public AdminAssignRolePage searchUser(String string) {
		this.searchBox.sendKeys(string);
		this.findUserButton.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		AdminAssignRolePage page = new AdminAssignRolePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public AdminAssignRolePage removeThisRole(String string) {
		List<WebElement> roleDivs = this.roleMainDiv.findElements(By
				.cssSelector("div.rowbg"));
		for (WebElement div : roleDivs) {
			WebElement innerDiv = null;
			try {
				innerDiv = div.findElement(By.tagName("div"));
			} catch (Exception e) {
				continue;
			}
			if (StringUtils.equalsIgnoreCase(innerDiv.getText(), string)) {
				WebElement img = div.findElement(By
						.cssSelector("a.pointer img"));
				img.click();
				this.waitForPageToLoad();
				this.waitForAjax();
				break;
			}
		}

		AdminAssignRolePage page = new AdminAssignRolePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}
	public AdminAssignRolePage clickOnThisUserLink(String string) {
		AdminAssignRolePage page = null;
		string = StringUtils.deleteWhitespace(string);
		List<WebElement> trs = this.userTable.findElements(By.tagName("tr"));
		for (WebElement tr : trs) {
			WebElement user = null;
			try {
				user = tr.findElement(By.tagName("td"));
				String name = StringUtils.remove(
						StringUtils.deleteWhitespace(user.getText()), ",");
				if (StringUtils.equalsIgnoreCase(name, string)) {
					WebElement aTag = user.findElement(By.tagName("a"));
					aTag.click();
					this.waitForPageToLoad();
					this.waitForAjax();
					page = new AdminAssignRolePage(this.driver);
					PageFactory.initElements(this.driver, page);
					break;
				}
			} catch (Exception e) {
			}
		}

		return page;
	}

	public AdminAssignRolePage addThisRole(String string) {

		Select select = new Select(this.roleSelect);
		List<WebElement> options = select.getOptions();
		for (WebElement option : options) {
			if (StringUtils.equalsIgnoreCase(option.getText(), string)) {
				select.selectByVisibleText(option.getText());
				this.addRoleButton.click();
				this.waitForPageToLoad();
				this.waitForAjax();
				break;
			}
		}

		AdminAssignRolePage page = new AdminAssignRolePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}
}
