package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.syngenta.sylk.main.pages.MenuPage;

public class RegionOfInterestROIDetailsPage extends MenuPage {

	public RegionOfInterestROIDetailsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "typeCode-clone")
	private WebElement RegionOfInterestType;

	@FindBy(id = "organism.uuid-clone")
	private WebElement SourceSpecies;

	@FindBy(id = "referenceGenome.uuid")
	private WebElement ReferenceGenomeAndVersion;

	@FindBy(id = "chromosome.name")
	private WebElement ChromosomeScaffoldContig;

	@FindBy(css = "a#roiTypeHelp_anchor img")
	private WebElement ROIhelp;

	@FindBy(id = "typeComment")
	private WebElement ROITypeComment;

	@FindBy(id = "addROIButton")
	private WebElement AddRegionOFInterest;

	// Validation errors:

	@FindBy(id = "chromosomeName_error")
	private WebElement chromosomeNameError;

	@FindBy(id = "type_error")
	private WebElement regionOfInterestTypeError;

	@FindBy(id = "organism_error")
	private WebElement sourceSpeciesError;

	@FindBy(id = "referenceGenome_error")
	private WebElement ReferenceGenomeVersionError;

	private String errorMsg = "This field is required";

	public void selectRegionOfInterestType(String selection) {
		List<WebElement> elements = this.RegionOfInterestType.findElements(By
				.tagName("options"));
		for (WebElement element : elements) {
			if (element.getText().equalsIgnoreCase("selection")) {
				element.click();
			}

		}

	}

	public void selectSourceSpecies(String Selection) {
		List<WebElement> elements = this.SourceSpecies.findElements(By
				.tagName("options"));
		for (WebElement element : elements) {
			if (element.getText().equalsIgnoreCase(Selection)) {
				element.click();
			}
		}

	}
	// Returns the list of elements in the drop
	public String getListOfSourceSpeciesInDropDown() {
		String list = null;

		List<WebElement> elements = this.driver.findElements(By
				.id("organism.uuid-clone"));
		for (WebElement element : elements) {
			list = list + element.getText().toString() + " , ";
		}

		return list;
	}

	public int getSizeOfListOfSourceSpeciesInDropDown() {
		int size1 = 0;
		List<WebElement> elements = this.driver.findElements(By
				.id("organism.uuid-clone"));
		size1 = elements.size();
		return size1;
	}

	public void selectReferenceGenomeVersion(String selection) {
		List<WebElement> elements = this.ReferenceGenomeAndVersion
				.findElements(By.tagName("options"));
		for (WebElement element : elements) {
			if (element.getText().equalsIgnoreCase(selection)) {
				element.click();
			}
		}
	}

	public void enterChromosomeScaffoldContig(String data) {
		this.ChromosomeScaffoldContig.sendKeys(data);

	}

	public String getValidationerror() {
		String error = null;
		try {
			this.chromosomeNameError = this.driver.findElement(By
					.id("chromosomeName_error"));
			error = this.chromosomeNameError.getText();
		} catch (Exception e) {

		}

		return error;
	}
}
