package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.syngenta.sylk.main.pages.MenuPage;

/**
 * @author Nisha Pillai
 * 
 */
public class NominateConstructPage extends MenuPage {

	public NominateConstructPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "nominationId")
	private WebElement nominationId;
	@FindBy(id = "rationale")
	private WebElement reationale;
	@FindBy(id = "description")
	private WebElement description;
	@FindBy(id = "comment")
	private WebElement comment;
	@FindBy(id = "additionalInfo")
	private WebElement additionalInfo;
	@FindBy(id = "rnaiSequence")
	private WebElement rnaiSequence;
	@FindBy(id = "pipeline")
	private WebElement pipelineLocation;
	@FindBy(id = "vectorQCNumber")
	private WebElement qcNumber;
	@FindBy(className = "floatright btn")
	private WebElement addTargetSpecies;
	@FindBy(id = "dialogSpecies")
	private WebElement targetSpeciesForConstrut;
	@FindBy(id = "dialogAdd")
	private WebElement add;

	public void enterNominationId(String id) {
		this.nominationId.sendKeys("id");
	}

	public void enterConstructNominationRationale(String data) {
		this.reationale.sendKeys("data");
	}

	public void enterVectorFunctionDescription(String data) {
		this.description.sendKeys("data");
	}

	public void enterProjectMemberComment(String data) {
		this.comment.sendKeys("data");
	}

	public void enterAdditionalInfoForCloning(String data) {
		this.additionalInfo.sendKeys("data");
	}

	public void enterRnaiRegionSequence(String data) {
		this.rnaiSequence.sendKeys("data");
	}

	public void checkboxPipeLineLocation(String checkbox) {
		List<WebElement> elements = this.pipelineLocation.findElements(By
				.tagName("input"));
		for (WebElement e : elements) {
			if (e.getText().equalsIgnoreCase("checkbox")) {
				e.click();
				this.add.click();
			}
		}
	}

	public void enterQcNumber(String data) {
		this.qcNumber.sendKeys("data");

	}

	public void clickAddTargetSpecies(String data) {
		this.addTargetSpecies.click();
		this.targetSpeciesForConstrut.sendKeys("data");

	}

}
