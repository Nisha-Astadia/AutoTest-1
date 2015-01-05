package com.syngenta.sylk.menu_add.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.main.pages.MenuPage;

public class AddNewRegionOfInterestROIPage extends MenuPage {

	private String hidden_chromosome_uuid;
	private String hidden_chromosome_length;
	private String selectedChromoScafoldConfig;
	private List<WebElement> lis;
	public AddNewRegionOfInterestROIPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "roiType.typeCode-clone")
	private WebElement ROITypeExpand;

	@FindBy(id = "roiType.typeCode")
	private WebElement RegionOfInterestType;

	@FindBy(css = "#roiTypeHelp_anchor img")
	private WebElement roiHelp;

	@FindBy(id = "organism.uuid-clone")
	private WebElement SourceSpecies;

	@FindBy(id = "referenceGenome.uuid")
	private WebElement ReferenceGenomeAndVersion;

	@FindBy(id = "chromosome.name")
	private WebElement ChromosomeScaffoldContig;

	@FindBy(id = "chromosome.start")
	private WebElement start;

	@FindBy(id = "chromosome.end")
	private WebElement end;

	@FindBy(css = "a#roiTypeHelp_anchor img")
	private WebElement ROIhelp;

	@FindBy(id = "typeComment")
	private WebElement ROITypeComment;

	@FindBy(id = "addROIButton")
	private WebElement AddRegionOFInterest;

	@FindBy(id = "addProjects")
	private WebElement project;

	@FindBy(id = "editPhenotypesButton")
	private WebElement Phenotype;

	// Validation errors:

	@FindBy(id = "chromosomeName_error")
	private WebElement chromosomeNameError;

	@FindBy(id = "type_error")
	private WebElement regionOfInterestTypeError;

	@FindBy(id = "organism_error")
	private WebElement sourceSpeciesError;

	@FindBy(id = "referenceGenome_error")
	private WebElement ReferenceGenomeVersionError;

	@FindBy(id = "start_error")
	private WebElement startError;

	@FindBy(id = "end_error")
	private WebElement endError;

	private String errorMsg = "This field is required";

	public AddNewRegionOfInterestROIPage selectRegionOfInterestType(
			String selection) {
		AddNewRegionOfInterestROIPage page = null;
		Select select = new Select(this.ROITypeExpand);
		List<WebElement> options = select.getOptions();
		for (WebElement option : options) {
			if (StringUtils.equalsIgnoreCase(option.getText(), selection)) {
				select.selectByVisibleText(option.getText());
				option.click();
				this.waitForPageToLoad();
				this.waitForAjax();
				page = new AddNewRegionOfInterestROIPage(this.driver);
				PageFactory.initElements(this.driver, page);

				break;
			}
		}
		return page;

	}

	public List<String> getListOfRegionOfInterestTypeDropdownValues() {
		List<String> values = new ArrayList<String>();
		this.roiHelp.click();
		List<WebElement> list = this.RegionOfInterestType.findElements(By
				.tagName("option"));
		for (WebElement li : list) {
			values.add(li.getText());
		}
		return values;
	}

	public List<String> getTextOfHelp() {
		List<String> values = new ArrayList<String>();
		this.RegionOfInterestType.click();
		List<WebElement> list = this.RegionOfInterestType.findElements(By
				.tagName("option"));
		for (WebElement li : list) {
			values.add(li.getText());
		}
		return values;
	}

	public AddNewRegionOfInterestROIPage selectSourceSpecies(String Selection) {
		AddNewRegionOfInterestROIPage page = null;
		Select select = new Select(this.SourceSpecies);
		List<WebElement> options = select.getOptions();
		for (WebElement option : options) {
			if (StringUtils.equalsIgnoreCase(option.getText(), Selection)) {
				select.selectByVisibleText(option.getText());
				option.click();
				this.waitForPageToLoad();
				this.waitForAjax();
				page = new AddNewRegionOfInterestROIPage(this.driver);
				PageFactory.initElements(this.driver, page);
				break;

			}

		}
		return page;

	}

	// Returns the list of elements in the drop

	public List<String> getListOfSourceSpeciesInDropDown() {
		List<String> values = new ArrayList<String>();
		// this.SourceSpecies.click();
		List<WebElement> list = this.SourceSpecies.findElements(By
				.tagName("option"));
		for (WebElement li : list) {
			values.add(li.getText());
		}
		return values;
	}

	public AddNewRegionOfInterestROIPage selectReferenceGenomeVersion(
			String selection) {

		AddNewRegionOfInterestROIPage page = null;
		Select select = new Select(this.ReferenceGenomeAndVersion);
		List<WebElement> options = select.getOptions();
		for (WebElement option : options) {
			if (StringUtils.equalsIgnoreCase(option.getText(), selection)) {
				select.selectByVisibleText(option.getText());
				option.click();
				this.waitForPageToLoad();
				this.waitForAjax();
				page = new AddNewRegionOfInterestROIPage(this.driver);
				PageFactory.initElements(this.driver, page);

				break;
			}
		}
		return page;

	}

	public List<String> getListOfReferenceGenomeVersion() {
		List<String> values = new ArrayList<String>();

		List<WebElement> list = this.ReferenceGenomeAndVersion.findElements(By
				.tagName("option"));
		for (WebElement li : list) {
			values.add(li.getText());
		}
		return values;
	}

	public AddNewRegionOfInterestROIPage enterChromosomeScaffoldContig(
			String data) {

		while (true) {
			if (this.ChromosomeScaffoldContig.isEnabled()) {
				break;
			}

		}

		this.ChromosomeScaffoldContig.sendKeys(data);

		AddNewRegionOfInterestROIPage page = new AddNewRegionOfInterestROIPage(
				this.driver);
		PageFactory.initElements(this.driver, page);

		return page;

	}

	public boolean enterChromosomeScaffoldContigAndReportIfError(String data) {
		boolean flag = false;
		// this.ChromosomeScaffoldContig.sendKeys(data);
		Actions action = new Actions(this.driver);
		action.sendKeys(this.ChromosomeScaffoldContig, data);
		action.perform();
		try {
			WebDriverWait wait = new WebDriverWait(this.driver, 10);
			wait.until(ExpectedConditions.alertIsPresent());
			this.driver.switchTo().alert().accept();
			this.waitForPageToLoad();
			this.waitForAjax();
			flag = true;
		} catch (Exception e) {
			/*
			 * it just means that there was no alert... which is a good thing
			 * and hopefully the select box is now populated with values.
			 */
			this.waitForAjax();
		}

		// if there is no alert we will select a value from dropdown
		if (!flag) {
			this.ChromosomeScaffoldContig = this.driver.findElement(By
					.id("chromosome.name"));
			WebElement ul = this.driver
					.findElement(By
							.cssSelector(".ui-autocomplete.ui-menu.ui-widget.ui-widget-content.ui-corner-all"));
			this.lis = ul.findElements(By.tagName("li"));
			this.lis.get(0).click();
			new CommonLibrary().slowDown();
			this.ChromosomeScaffoldContig = this.driver.findElement(By
					.id("chromosome.name"));
			this.selectedChromoScafoldConfig = this.ChromosomeScaffoldContig
					.getAttribute("value");
		}

		return flag;
	}

	public List<String> getListOfChromosomeScaffoldContig(String data) {

		List<String> values = new ArrayList<String>();
		// this.ChromosomeScaffoldContig.sendKeys(data);
		Actions action = new Actions(this.driver);
		action.sendKeys(this.ChromosomeScaffoldContig, data);
		action.perform();
		try {
			WebDriverWait wait = new WebDriverWait(this.driver, 10);
			wait.until(ExpectedConditions.alertIsPresent());
			this.driver.switchTo().alert().accept();
			this.waitForPageToLoad();
			this.waitForAjax();

		} catch (Exception e) {
			/*
			 * it just means that there was no alert... which is a good thing
			 * and hopefully the select box is now populated with values.
			 */
			this.waitForAjax();

			this.ChromosomeScaffoldContig = this.driver.findElement(By
					.id("chromosome.name"));
			WebElement ul = this.driver
					.findElement(By
							.cssSelector(".ui-autocomplete.ui-menu.ui-widget.ui-widget-content.ui-corner-all"));
			this.lis = ul.findElements(By.tagName("li"));

			for (WebElement li : this.lis) {
				values.add(li.getText());
			}

		}
		return values;

	}

	public String getRegionOfInterestTypeValidationError() {

		String error = null;
		try {
			this.regionOfInterestTypeError = this.driver.findElement(By
					.id("type_error"));
			error = this.regionOfInterestTypeError.getText();
		} catch (Exception e) {

		}

		return error;
	}

	public String getCSCValidationError() {
		String error = null;
		try {
			this.chromosomeNameError = this.driver.findElement(By
					.id("chromosomeName_error"));
			error = this.chromosomeNameError.getText();
		} catch (Exception e) {

		}

		return error;
	}

	public String getSourceSpeciesValidationError() {
		String error = null;
		try {
			this.SourceSpecies = this.driver.findElement(By
					.id("organism_error"));
			error = this.SourceSpecies.getText();
		} catch (Exception e) {

		}

		return error;
	}

	public String getReferenceGenomeAndVersionValidationError() {
		String error = null;
		try {
			this.ReferenceGenomeVersionError = this.driver.findElement(By
					.id("referenceGenome_error"));
			error = this.ReferenceGenomeVersionError.getText();
		} catch (Exception e) {

		}

		return error;
	}

	public String getStartValidationError() {
		String error = null;
		try {
			this.startError = this.driver.findElement(By.id("start_error"));
			error = this.startError.getText();
		} catch (Exception e) {

		}

		return error;
	}

	public String getendValidationError() {
		String error = null;
		try {
			this.end = this.driver.findElement(By.id("end_error"));
			error = this.end.getText();
		} catch (Exception e) {

		}

		return error;
	}

	public PopUpAddProjectPage clickAddProjectToROI() {
		this.project.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		PopUpAddProjectPage page = new PopUpAddProjectPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public boolean isAddProjectEnabled() {
		return this.project.isEnabled();
	}

	public int getAddedProjectNameCount() {

		WebElement span = this.driver.findElement(By.id("projectNameList"));
		List<WebElement> projects = span.findElements(By.tagName("span"));
		if (projects != null) {
			return projects.size();
		} else {
			return 0;
		}
	}

	public List<String> getAddedPhenoTypes() {
		List<String> addedPhenos = new ArrayList<String>();
		WebElement addedMainDiv = this.driver.findElement(By
				.id("addedPhenotypes"));
		List<WebElement> divs = addedMainDiv.findElements(By.tagName("div"));
		for (WebElement div : divs) {
			if (StringUtils.startsWithIgnoreCase(div.getAttribute("class"),
					"rowbg")) {
				continue;
			} else {
				addedPhenos.add(div.getText());
			}
		}

		return addedPhenos;
	}
	public PopUpAddOrRemovePhenotypesPage clickEditPhenotypes() {
		this.Phenotype.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		PopUpAddOrRemovePhenotypesPage page = new PopUpAddOrRemovePhenotypesPage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public AddNewRegionOfInterestROIPage clickAddRegionOfInterest() {

		this.AddRegionOFInterest.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		AddNewRegionOfInterestROIPage page = new AddNewRegionOfInterestROIPage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public boolean isReferenceGenomeAndVersionEnabled() {
		CommonLibrary common = new CommonLibrary();
		boolean rgv;
		try {
			rgv = this.ReferenceGenomeAndVersion.isEnabled();
		} catch (Exception e) {
			throw new SyngentaException(
					" Got the exception while trying to locate the element RGV "
							+ common.getStackTrace(e));
		}

		return rgv;
	}

	public boolean isStartEnabled() {
		CommonLibrary common = new CommonLibrary();
		boolean flag = false;
		try {
			flag = this.start.isEnabled();
		} catch (Exception e) {
			throw new SyngentaException(
					" Start field should be available for use "
							+ common.getStackTrace(e));
		}

		return flag;

	}

	public boolean isChromosomeScaffoldContigEnabled() {
		CommonLibrary common = new CommonLibrary();
		boolean csc;
		try {
			csc = this.ChromosomeScaffoldContig.isEnabled();
		} catch (Exception e) {
			throw new SyngentaException(
					" Got the exception while trying to locate the element csc "
							+ common.getStackTrace(e));
		}

		return csc;
	}

	public HashMap<String, String> enterTheseValues(HashMap<String, String> map) {

		HashMap<String, String> failureMap = new HashMap<String, String>();

		// if region of interest type has a value in the passed Map
		if (map.containsKey("regionofinteresttype")) {
			this.selectRegionOfInterestType(map.get("regionofinteresttype"));
		}
		// if source species has a value in the passed Map
		if (map.containsKey("sourcespecies")) {
			this.selectSourceSpecies(map.get("sourcespecies"));
		}
		// if Reference Genome & Version has a value in the passed Map
		if (map.containsKey("referencegenomeandversion")) {
			boolean error = false;
			boolean enabled = false;
			String returnMessage = null;

			enabled = this.isReferenceGenomeAndVersionEnabled();

			if (!enabled)// if disabled
			{
				error = true;
				returnMessage = "Reference Genome & Version is disabled.";

			} else {
				this.selectReferenceGenomeVersion(map
						.get("referencegenomeandversion"));
			}
			if (error) {
				failureMap.put("referencegenomeandversion", returnMessage);
			}
		}

		// if Chromosome / Scaffold / Contig has a value in the passed Map
		if (map.containsKey("chromosomescaffoldcontig")) {
			// We need to reset the values as this method is expected to set and
			// new value
			this.hidden_chromosome_length = null;
			this.hidden_chromosome_uuid = null;
			this.selectedChromoScafoldConfig = null;
			boolean error = false;
			boolean enabled = false;
			String returnMessage = null;

			enabled = this.isChromosomeScaffoldContigEnabled();

			if (!enabled)// if disabled
			{
				error = true;
				returnMessage = "Chromosome / Scaffold / Contig is disabled.";

			} else { // only if enabled
				error = this.enterChromosomeScaffoldContigAndReportIfError(map
						.get("chromosomescaffoldcontig"));
				if (error) {
					returnMessage = "Alert when entering value="
							+ map.get("chromosomescaffoldcontig")
							+ " in Chromosome / Scaffold / Contig";
				} else {// if there was no alert

					WebElement hidden_chromose_uuid = this.driver
							.findElement(By.id("chromosome.uuid"));
					this.hidden_chromosome_uuid = hidden_chromose_uuid
							.getAttribute("value");
					WebElement hidden_chromose_length = this.driver
							.findElement(By.id("chromosome.length"));
					this.hidden_chromosome_length = hidden_chromose_length
							.getAttribute("value");

				}
			}
			if (error) {
				failureMap.put("chromosomescaffoldcontig", returnMessage);
			}
		}
		// if Start has a value in the passed Map
		if (map.containsKey("start")) {
			boolean error = false;
			boolean enabled = false;
			String returnMessage = null;

			enabled = this.isStartEnabled();
			if (!enabled)// if disabled
			{
				error = true;
				returnMessage = "Start is disabled.";

			} else {
				this.enterStart(map.get("start"));
				String startError = this.isThereAnErroOnStartField();
				if (StringUtils.isNotBlank(startError)) {
					error = true;
					returnMessage = "Start field has the follwoing error. Error = "
							+ startError;
				}
			}
			if (error) {
				failureMap.put("start", returnMessage);
			}
		}

		// if End has a value in the passed Map
		if (map.containsKey("end")) {
			boolean error = false;
			boolean enabled = false;
			String returnMessage = null;

			enabled = this.isEndEnabled();
			if (!enabled)// if disabled
			{
				error = true;
				returnMessage = "End is disabled.";

			} else {
				this.enterEnd(map.get("end"));
				String endError = this.isThereAnErroOnEndField();
				if (StringUtils.isNotBlank(endError)) {
					error = true;
					returnMessage = "End field has the follwoing error. Error = "
							+ endError;
				}
			}
			if (error) {
				failureMap.put("end", returnMessage);
			}
		}

		// if End has a value in the passed Map
		if (map.containsKey("end")) {

		}
		return failureMap;
	}

	private void enterEnd(String string) {
		WebElement end = this.driver.findElement(By.id("chromosome.end"));
		Actions action = new Actions(this.driver);
		end.sendKeys("");
		action.sendKeys(end, string);
		action.sendKeys(end, Keys.TAB);
		action.perform();

	}

	public void enterStart(String string) {
		WebElement start = this.driver.findElement(By.id("chromosome.start"));
		Actions action = new Actions(this.driver);
		start.sendKeys("");
		action.sendKeys(start, string);
		action.perform();
		WebElement end = this.driver.findElement(By.id("chromosome.end"));
		end.click();
	}

	public String getStartValue() {
		WebElement start = this.driver.findElement(By.id("chromosome.start"));
		return start.getAttribute("value");
	}
	public String isThereAnErroOnStartField() {
		WebElement startError = this.driver.findElement(By.id("start_error"));
		String errorMsg = startError.getText();
		return errorMsg;
	}
	public String isThereAnErroOnEndField() {
		WebElement endError = this.driver.findElement(By.id("end_error"));
		String errorMsg = endError.getText();
		return errorMsg;
	}
	public String getHiddenChromosomeLength() {
		return this.hidden_chromosome_length;
	}

	public String getHiddenChromosomeUUID() {
		return this.hidden_chromosome_uuid;
	}

	public String getSelectedChromoScafoldConfig() {
		return this.selectedChromoScafoldConfig;
	}

	public boolean isEndEnabled() {
		WebElement end = this.driver.findElement(By.id("chromosome.end"));
		return end.isEnabled();
	}
	public List<WebElement> getListOFChromoScafoldConfig() {

		return this.lis;
	}

}
