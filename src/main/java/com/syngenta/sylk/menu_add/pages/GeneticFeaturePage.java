package com.syngenta.sylk.menu_add.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.main.pages.BasePage;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.MenuPage;

public class GeneticFeaturePage extends MenuPage {

	private static final List<WebElement> WebElment = null;

	public GeneticFeaturePage(WebDriver driver) {
		super(driver);
		this.waitForConstructSequenceToLoad();
		this.waitForLeadInfoSequenceToLoad();
	}

	@FindBy(css = "a@href")
	private WebElement gDNA;

	@FindBy(id = "deleteGeneticFeatureButton")
	private WebElement deleteThisGeneticFeature;

	@FindBy(id = "exportButton")
	private WebElement export;

	@FindBy(id = "geneticFeature_geneId")
	private WebElement nCBIid;

	@FindBy(className = "ui-state-default ui-corner-top")
	private WebElement evidenceTabTop;

	@FindBy(id = "geneticFeature_tabPanel")
	private WebElement tabDiv;

	// @FindBy(css = "div#ui-tabs-2")
	// private WebElement viewEvidence;

	@FindBy(css = "div[id='ui-tabs-7 span.view']")
	private WebElement viewEvidenceSequence;

	@FindBy(css = "select[onchange='addGeneticFeatureEvidence(this.value)']")
	private WebElement evidencedropdown;

	@FindBy(css = "a[target='_blank']")
	private WebElement titleEvidenceStatementLink;

	@FindBy(css = "a[href='javascript:addSequence6('cds');']")
	private WebElement cDNA;

	@FindBy(css = "span[class='field']")
	private WebElement protein;

	@FindBy(css = "ul#geneticFeature_tabs li:nth-child(2)")
	private WebElement clickEvidence;

	@FindBy(css = "ul#geneticFeature_tabs li:nth-child(3)")
	private WebElement clickOrtholog;

	@FindBy(css = "ul#sequence_0_tabs li:nth-child(2)")
	private WebElement clickEvidenceGF;

	// @FindBy(css = "ul#sequence_0_tabs li:nth-child(2)")
	// private WebElement clickEvidenceGF;
	//

	@FindBy(css = "ul#sequence_0_tabs li:nth-child(2)")
	private WebElement clickEvidenceSequence;

	@FindBy(css = "ul#sequence_0_tabs li:nth-child(3)")
	private WebElement clickLeadInfo;

	@FindBy(css = "ul#sequence_0_tabs li:nth-child(5)")
	private WebElement clickProjectLeads;

	@FindBy(css = "ul#sequence_0_tabs li:nth-child(6)")
	private WebElement clickOnConstructTab;

	@FindBy(css = "ul#sequence_0_tabs li:nth-child(1)")
	private WebElement clickDetail;

	@FindBy(css = "div#ui-tabs-2 div select")
	private WebElement selectEvidence;

	@FindBy(css = "ul#sequence_0_tabs li:nth-child(3)")
	private WebElement leadInfo;

	@FindBy(css = "div#ui-tabs-6 table table tr:nth-child(1) td:nth-child(3)")
	private WebElement addNewXref;

	@FindBy(css = "div#ui-tabs-11")
	private WebElement constructDiv;

	@FindBy(css = "input[id='edit1']")
	private WebElement edit;

	@FindBy(id = "ro_synonyms")
	private WebElement synonyms;

	@FindBy(id = "synonyms")
	private WebElement synonymsedit;

	@FindBy(css = "input[class='formBtn btn deleteSequenceButton6'][value='Delete This Sequence']")
	private WebElement deleteThisSequence;

	private void waitForConstructSequenceToLoad() {
		try {
			CommonLibrary common = new CommonLibrary();
			WebElement construct = this.driver.findElement(By
					.cssSelector("ul#sequence_0_tabs li:nth-child(6)"));
			if (StringUtils.containsIgnoreCase(construct.getAttribute("class"),
					"ui-state-active")) {
				int count = this.getConstructCountOnTab();
				if (count == 0) {
					while (true) {
						try {
							this.driver.findElement(By
									.cssSelector("div#ui-tabs-11 div.info"));
							break;
						} catch (Exception e) {
							common.slowDown();
						}
					}
				} else {
					this.waitForWebElement(By
							.cssSelector("div#ui-tabs-11 table.spaced"));
				}
			}
		} catch (Exception e) {

		}
	}

	private void waitForLeadInfoSequenceToLoad() {
		try {
			CommonLibrary common = new CommonLibrary();
			WebElement leadInfo = this.driver.findElement(By
					.cssSelector("ul#sequence_0_tabs li:nth-child(3)"));
			if (StringUtils.containsIgnoreCase(leadInfo.getAttribute("class"),
					"ui-state-active")) {
				int count = this.getLeadInfoCountOnTab();
				if (count == 0) {
					while (true) {
						try {
							this.driver.findElement(By
									.cssSelector("div#ui-tabs-8 div.info"));
							break;
						} catch (Exception e) {
							common.slowDown();
						}
					}
				}
			}
		} catch (Exception e) {

		}
	}

	private void waitForEvidenceSequenceToLoad() {
		try {
			if (this.getEvidenceSequenceCountOnTab() > 0) {
				this.waitForWebElement(By.cssSelector("div#ui-tabs- div.clip"));
			} else {
				new CommonLibrary().slowDown();
			}
		} catch (Exception e) {

		}
	}

	private void waitForEvidenceToLoad() {
		try {
			if (this.getEvidenceCountOnTab() > 0) {
				this.waitForWebElement(By.cssSelector("div#ui-tabs-2 div.clip"));
			} else {
				new CommonLibrary().slowDown();
			}
		} catch (Exception e) {

		}
	}

	private void waitForOrthologToLoad() {
		try {
			if (this.getOrthologCountOnTab() > 0) {
				this.waitForWebElement(By.cssSelector("input#addOrthologs"));
			} else {
				new CommonLibrary().slowDown();
			}
		} catch (Exception e) {

		}
	}

	public String getGDNALabel() {
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		WebElement element = (WebElement) js
				.executeScript("return jQuery.find(\"[href='javascript:addSequence6('gdna');']\")[0]");
		return element.getText();
	}

	// this method should accept the parameter and return the pop for the
	// associated seq.
	public PopUpgDNASequenceDataPage clickOngDNA() {
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		WebElement element = (WebElement) js
				.executeScript("return jQuery.find(\"[href='javascript:addSequence6('gdna');']\")[0]");
		if (element != null) {
			element.click();
		} else {
			element = (WebElement) js
					.executeScript("return jQuery.find(\"[href='javascript:viewSequence6('gdna');']\")[0]");
		}
		this.waitForPopUpToLoad();
		PopUpgDNASequenceDataPage page = new PopUpgDNASequenceDataPage(
				this.driver);
		PageFactory.initElements(this.driver, page);

		return page;
	}

	public PopUpcDNASequenceDataPage clickOncDNA() {
		String linkText = this.driver.findElement(
				By.cssSelector("div#sequence_0_container span")).getText();
		WebElement element = null;
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		if (StringUtils.containsIgnoreCase(linkText, "-cdna")) {
			WebElement div = this.driver.findElement(By.id("ui-tabs-6"));
			List<WebElement> trs = div.findElements(By.tagName("tr"));
			for (WebElement tr : trs) {
				WebElement td1;
				try {
					td1 = tr.findElement(By.cssSelector("td.label"));
				} catch (Exception e) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(
						StringUtils.deleteWhitespace(td1.getText()), "cDNA:")) {
					element = tr.findElement(By.tagName("span"));
					break;
				}
			}
			if (element != null) {
				element.click();
			}
		} else {
			element = (WebElement) js
					.executeScript("return jQuery.find(\"[href='javascript:addSequence6('cdna');']\")[0]");
			if (element != null) {
				element.click();
			} else {
				element = (WebElement) js
						.executeScript("return jQuery.find(\"[href='javascript:viewSequence6('cdna');']\")[0]");
			}
		}
		element.click();
		this.waitForPopUpToLoad();
		PopUpcDNASequenceDataPage page = new PopUpcDNASequenceDataPage(
				this.driver);
		PageFactory.initElements(this.driver, page);

		return page;
	}
	public PopUpCDSSequenceDataPage clickOnCDS() {
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		WebElement element = (WebElement) js
				.executeScript("return jQuery.find(\"[href='javascript:addSequence6('cds');']\")[0]");
		element.click();

		this.waitForPopUpToLoad();
		PopUpCDSSequenceDataPage page = new PopUpCDSSequenceDataPage(
				this.driver);
		PageFactory.initElements(this.driver, page);

		return page;
	}

	public PopUpProteinSequenceDataPage clickOnProtein() {
		this.protein.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		PopUpProteinSequenceDataPage page = new PopUpProteinSequenceDataPage(
				this.driver);
		PageFactory.initElements(this.driver, page);

		return page;
	}

	public HomePage clickDeleleThisGeneticFeature() {
		this.deleteThisGeneticFeature.click();

		WebDriverWait wait = new WebDriverWait(this.driver, 10);
		wait.until(ExpectedConditions.alertIsPresent());
		this.driver.switchTo().alert().accept();
		this.waitForPageToLoad();
		this.waitForAjax();
		HomePage page = new HomePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public ConstructNominationPage clickConstructNominationIdLink(int a) {
		if (a == 0) {
			a = 1;
		}
		WebElement link = this.constructDiv.findElement(By
				.cssSelector("tr:nth-child(" + (a + 1) + ") td:nth-child(1)"));
		WebElement atag = link.findElement(By.tagName("a"));
		atag.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		ConstructNominationPage page = new ConstructNominationPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public PopUpFileDownLoad clickOnExport() {
		this.export.click();
		// write the implementation for the browser alert for
		// file download.
		this.waitForPopUpToLoad();
		PopUpFileDownLoad page = new PopUpFileDownLoad(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public NCBIPage expandTheSeqInPreferredSequenceSection() {
		this.nCBIid.click();
		this.waitForPopUpToLoad();
		this.waitForAjax();
		NCBIPage page = new NCBIPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public String getTextFromCDNA() {
		String linkText = this.driver.findElement(
				By.cssSelector("div#sequence_0_container span")).getText();
		WebElement element = null;
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		if (StringUtils.containsIgnoreCase(linkText, "-cdna")) {
			WebElement div = this.driver.findElement(By.id("ui-tabs-6"));
			List<WebElement> trs = div.findElements(By.tagName("tr"));
			for (WebElement tr : trs) {
				WebElement td1;
				try {
					td1 = tr.findElement(By.cssSelector("td.label"));
				} catch (Exception e) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(
						StringUtils.deleteWhitespace(td1.getText()), "cDNA:")) {
					element = tr.findElement(By.tagName("span"));
					break;
				}
			}

		} else {
			element = (WebElement) js
					.executeScript("return jQuery.find(\"[href='javascript:addSequence6('cdna');']\")[0]");
			if (element == null) {
				element = (WebElement) js
						.executeScript("return jQuery.find(\"[href='javascript:viewSequence6('cdna');']\")[0]");
			}
		}
		if (element != null) {
			return element.getText();
		}

		return null;
	}

	public void clickEvidenceTab() {
		WebElement evdTab = this.driver.findElement(By
				.cssSelector("ul#geneticFeature_tabs li:nth-child(2)"));
		if (!StringUtils.containsIgnoreCase(evdTab.getAttribute("class"),
				"ui-state-active")) {
			WebElement evi = this.clickEvidence.findElement(By.tagName("a"));
			evi.click();
			this.waitForPageToLoad();
		}
	}

	public int getConstructCountOnTab() {

		WebElement evi = this.driver.findElement(By
				.partialLinkText("Constructs"));
		String text = evi.getText();
		text = StringUtils.substringBetween(text, "(", ")");
		if (StringUtils.isBlank(text)) {
			return 0;
		} else {
			return Integer.parseInt(text);
		}

	}

	public int getProjectLeadsCountOnTab() {

		WebElement evi = this.clickProjectLeads.findElement(By.tagName("a"));
		String text = evi.getText();
		text = StringUtils.substringBetween(text, "(", ")");
		if (StringUtils.isBlank(text)) {
			return 0;
		} else {
			return Integer.parseInt(text);
		}

	}

	public int getLeadInfoCountOnTab() {

		WebElement infoLi = this.driver.findElement(By
				.cssSelector("ul#sequence_0_tabs li:nth-child(3)"));
		WebElement evi = infoLi.findElement(By.tagName("a"));
		String text = evi.getText();
		text = StringUtils.substringBetween(text, "(", ")");
		if (StringUtils.isBlank(text)) {
			return 0;
		} else {
			return Integer.parseInt(text);
		}

	}

	public GeneticFeaturePage clickOnEvidenceSequenceTab() {
		WebElement evdTab = this.driver.findElement(By
				.cssSelector("ul#sequence_0_tabs li:nth-child(2)"));
		if (!StringUtils.containsIgnoreCase(evdTab.getAttribute("class"),
				"ui-state-active")) {
			WebElement evi = evdTab.findElement(By.tagName("a"));
			evi.click();
			this.waitForPageToLoad();
			this.waitForAjax();
			this.waitForEvidenceSequenceToLoad();
		}
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public GeneticFeaturePage clickOnEvidenceTab() {
		WebElement evdTab = this.driver.findElement(By
				.cssSelector("ul#geneticFeature_tabs li:nth-child(2)"));
		if (!StringUtils.containsIgnoreCase(evdTab.getAttribute("class"),
				"ui-state-active")) {
			WebElement evi = evdTab.findElement(By.tagName("a"));
			evi.click();
			this.waitForPageToLoad();
			this.waitForAjax();
			this.waitForEvidenceToLoad();
		}
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public GeneticFeaturePage clickOnOrthologTab() {

		WebElement orthoTab = this.driver.findElement(By
				.cssSelector("ul#geneticFeature_tabs li:nth-child(3)"));
		if (!StringUtils.containsIgnoreCase(orthoTab.getAttribute("class"),
				"ui-state-active")) {
			WebElement ortho = orthoTab.findElement(By.tagName("a"));
			ortho.click();
			this.waitForPageToLoad();
			this.waitForAjax();
			this.waitForOrthologToLoad();
		}
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}
	public GeneticFeaturePage clickDetailTab() {
		if (!StringUtils.containsIgnoreCase(
				this.clickDetail.getAttribute("class"), "ui-state-active")) {
			WebElement evi = this.clickDetail.findElement(By.tagName("a"));
			evi.click();
			this.waitForPageToLoad();
			this.waitForAjax();
			this.waitForWebElement(By
					.cssSelector("div#ui-tabs-6 table table tr:nth-child(1) td:nth-child(3)"));
		}
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}
	public GeneticFeaturePage clickOnLeadInfoTab() {
		if (!StringUtils.containsIgnoreCase(
				this.clickLeadInfo.getAttribute("class"), "ui-state-active")) {
			WebElement evi = this.clickLeadInfo.findElement(By.tagName("a"));
			evi.click();
			this.waitForPageToLoad();
			this.waitForAjax();
			this.waitForLeadInfoSequenceToLoad();
		}
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public GeneticFeaturePage clickOnProjectLeadTab() {
		if (!StringUtils
				.containsIgnoreCase(
						this.clickProjectLeads.getAttribute("class"),
						"ui-state-active")) {
			WebElement evi = this.clickProjectLeads
					.findElement(By.tagName("a"));
			evi.click();
			this.waitForPageToLoad();
			this.waitForAjax();
		}
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public GeneticFeaturePage clickOnConstructTab() {
		if (!StringUtils.containsIgnoreCase(
				this.clickOnConstructTab.getAttribute("class"),
				"ui-state-active")) {
			WebElement element = this.clickOnConstructTab.findElement(By
					.tagName("a"));
			element.click();
			this.waitForPageToLoad();
			this.waitForAjax();
			this.waitForConstructSequenceToLoad();
		}

		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public boolean promoteNewProjectButtonExists() {
		boolean flag = false;
		try {
			WebElement button = this.driver.findElement(By
					.cssSelector("div#ui-tabs-10 input.formBtn"));
			if (StringUtils.equalsIgnoreCase(button.getAttribute("value"),
					"Promote To New Project")) {
				flag = true;
			}
		} catch (Exception e) {
		}

		return flag;

	}
	public ProjectLeadPage clickOnPromoteToNewProjectButton() {
		WebElement button = this.driver.findElement(By
				.cssSelector("div#ui-tabs-10 input.formBtn"));
		button.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		ProjectLeadPage page = new ProjectLeadPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public LeadNominationPage clickOnNominateButton() {
		WebElement form = this.driver.findElement(By
				.cssSelector("div#ui-tabs-8 form#submitForm"));
		List<WebElement> buttons = form.findElements(By
				.cssSelector("input.btn"));
		for (WebElement nominate : buttons) {
			if (StringUtils.equalsIgnoreCase(nominate.getAttribute("value"),
					"Nominate")) {
				nominate.click();
				break;
			}
		}
		this.waitForPageToLoad();
		this.waitForAjax();
		LeadNominationPage page = new LeadNominationPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}
	public int getEvidenceSequenceCountOnTab() {

		WebElement evi = this.clickEvidenceSequence
				.findElement(By.tagName("a"));
		String text = evi.getText();
		text = StringUtils.substringBetween(text, "(", ")");
		if (StringUtils.isBlank(text)) {
			return 0;
		} else {
			return Integer.parseInt(text);
		}
	}

	public int getEvidenceCountOnTab() {

		WebElement evi = this.clickEvidence.findElement(By.tagName("a"));
		String text = evi.getText();
		text = StringUtils.substringBetween(text, "(", ")");
		if (StringUtils.isBlank(text)) {
			return 0;
		} else {
			return Integer.parseInt(text);
		}

	}

	private int getOrthologCountOnTab() {
		WebElement evi = this.clickOrtholog.findElement(By.tagName("a"));
		String text = evi.getText();
		text = StringUtils.substringBetween(text, "(", ")");
		if (StringUtils.isBlank(text)) {
			return 0;
		} else {
			return Integer.parseInt(text);
		}
	}

	public int getRegulatoryCheckCountOnTab() {
		WebElement reg = this.driver.findElement(By
				.cssSelector("ul#sequence_0_tabs li:nth-child(4)"));
		WebElement regATag = reg.findElement(By.tagName("a"));
		String text = regATag.getText();
		text = StringUtils.substringBetween(text, "(", ")");
		if (StringUtils.isBlank(text)) {
			return 0;
		} else {
			return Integer.parseInt(text);
		}
	}
	public RelatedLiteraturePage selectEvidence(String selection) {
		// this.selectEvidence.click();
		List<WebElement> elements = this.selectEvidence.findElements(By
				.tagName("option"));
		for (WebElement element : elements) {
			if (element.getText().equalsIgnoreCase(selection)) {
				element.click();
			}

		}
		this.waitForPageToLoad();
		this.waitForAjax();
		RelatedLiteraturePage page = new RelatedLiteraturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public BasePage selectAddEvidence(String selection) {

		BasePage page = null;
		if (StringUtils.containsIgnoreCase(selection, "literature")) {
			selection = "literature";
		} else if (StringUtils.containsIgnoreCase(selection, "other")) {
			selection = "other";
		}
		WebElement div = this.driver.findElement(By.id("ui-tabs-2"));
		List<WebElement> elements = div.findElements(By.tagName("option"));
		for (WebElement element : elements) {
			String value = StringUtils.lowerCase(element.getAttribute("value"));
			if (StringUtils.equals(StringUtils.deleteWhitespace(value),
					selection)) {
				element.click();
				break;
			}
		}
		this.waitForPageToLoad();
		this.waitForAjax();
		String title = this.driver.getTitle();
		if (StringUtils.equalsIgnoreCase(title,
				PageTitles.related_literature_page_title)) {
			page = new RelatedLiteraturePage(this.driver);
			PageFactory.initElements(this.driver, page);
		} else if (StringUtils.equalsIgnoreCase(title,
				PageTitles.literature_search_page_title)) {
			page = new LiteratureSearchPage(this.driver);
			PageFactory.initElements(this.driver, page);
		}
		return page;

	}

	public LiteratureSearchPage selectAddEvidenceSequence(String selection) {

		if (StringUtils.containsIgnoreCase(selection, "literature")) {
			selection = "literature";
		} else if (StringUtils.containsIgnoreCase(selection, "other")) {
			selection = "other";
		}
		WebElement div = this.driver.findElement(By.id("ui-tabs-7"));
		List<WebElement> elements = div.findElements(By.tagName("option"));
		for (WebElement element : elements) {
			String value = StringUtils.lowerCase(element.getAttribute("value"));
			if (StringUtils.equals(StringUtils.deleteWhitespace(value),
					selection)) {
				element.click();
				break;
			}
		}
		this.waitForPageToLoad();
		this.waitForAjax();
		LiteratureSearchPage page = new LiteratureSearchPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}
	public RelatedLiteraturePage clickEvidenceAndMakeAselection(String selection) {
		WebElement evi = this.evidenceTabTop.findElement(By.tagName("a"));
		evi.click();
		this.waitForPageToLoad();
		List<WebElement> elements = this.evidencedropdown.findElements(By
				.tagName("option"));
		for (WebElement element : elements) {
			if (element.getText().equalsIgnoreCase(selection)) {
				element.click();
			}
			this.waitForPageToLoad();
			this.waitForAjax();

		}
		RelatedLiteraturePage page = new RelatedLiteraturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public ViewLiteratureEvidenceDetailsGF clickviewLiteratureEvidence(int a) {

		List<WebElement> evidences = this.driver.findElements(By
				.cssSelector("div#ui-tabs-2 div.clip table.table span.view"));
		WebElement span = evidences.get(a);
		return this.clickviewLiteratureEvidence(span);
	}
	// click open and view the literature evidence.
	public ViewLiteratureEvidenceDetailsGF clickviewLiteratureEvidence(
			WebElement span) {

		span.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		ViewLiteratureEvidenceDetailsGF page = new ViewLiteratureEvidenceDetailsGF(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public ViewLiteratureEvidenceDetailsPageSequence clickviewLiteratureEvidenceSequence(
			WebElement element) {
		element.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		ViewLiteratureEvidenceDetailsPageSequence page = new ViewLiteratureEvidenceDetailsPageSequence(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public NCBIPage clickOnEvidenceStatementLink() {
		this.titleEvidenceStatementLink.click();
		String currentHandle = this.driver.getWindowHandle();

		this.waitForPopUpToLoad();
		Set<String> handles = this.driver.getWindowHandles();
		Iterator<String> i = handles.iterator();
		while (i.hasNext()) {
			String handle = i.next();
			if (!StringUtils.equalsIgnoreCase(currentHandle, handle)) {
				this.driver.switchTo().window(handle);
				System.out.println(":::::::::::::::::::::::::"
						+ this.driver.getTitle());
				break;
			}
		}
		this.waitForPopUpToLoad();
		NCBIPage page = new NCBIPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	// Add new Xref
	public PopUpXrefPage clickAddNewXref() {
		WebElement element = this.addNewXref.findElement(By.tagName("a"));
		element.click();
		this.waitForPopUpToLoad();
		PopUpXrefPage page = new PopUpXrefPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public GeneticFeaturePage clickOnTheProjectLinkInProjectLeadTab(int a) {
		// when the test send 1 it means the first link needs to be clicked
		a = a - 1;
		WebElement projectLink = this.driver.findElement(By.id("summary_10_"
				+ a + ""));
		projectLink.click();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public ConstructNominationPage clickOnNominateConstructButton() {

		List<WebElement> buttons = this.driver.findElements(By
				.cssSelector("#submitForm > input.floatleft.btn"));
		for (WebElement button : buttons) {
			if (StringUtils.equalsIgnoreCase(button.getAttribute("value"),
					"Nominate as New Construct")) {
				button.click();
				break;
			}
		}
		this.waitForPageToLoad();
		this.waitForAjax();
		ConstructNominationPage page = new ConstructNominationPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public GeneticFeaturePage clickOnProjectLeadDeleteButton() {

		List<WebElement> buttons = this.driver.findElements(By
				.cssSelector("#submitForm > input.floatleft.btn"));
		for (WebElement button : buttons) {
			if (StringUtils.equalsIgnoreCase(button.getAttribute("value"),
					"Delete")) {
				button.click();
				this.driver.switchTo().alert().accept();
				break;
			}
		}

		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public GeneticFeaturePage clickOnLeadInfoDeleteButton() {
		WebElement button = this.driver.findElement(By
				.id("deleteLeadNominationDetailsButton8"));

		button.click();
		this.driver.switchTo().alert().accept();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public List<String> getConstructColumns() {
		List<String> columns = new ArrayList<String>();
		List<WebElement> allHeaders = this.driver.findElements(By
				.cssSelector("div#ui-tabs-11 td.label"));
		for (WebElement header : allHeaders) {
			columns.add(header.getText());
		}
		return columns;
	}

	public GeneticFeaturePage clickOnRnaiTab() {
		WebElement evdTab = this.driver.findElement(By
				.cssSelector("ul#geneticFeature_tabs li:nth-child(4)"));
		if (!StringUtils.containsIgnoreCase(evdTab.getAttribute("class"),
				"ui-state-active")) {
			WebElement evi = evdTab.findElement(By.tagName("a"));
			evi.click();
			this.waitForPageToLoad();
			this.waitForAjax();
			this.waitForEvidenceSequenceToLoad();
		}
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public GeneticFeaturePage clickOnROITab() {
		WebElement evdTab = this.driver.findElement(By
				.cssSelector("ul#geneticFeature_tabs li:nth-child(5)"));
		if (!StringUtils.containsIgnoreCase(evdTab.getAttribute("class"),
				"ui-state-active")) {
			WebElement evi = evdTab.findElement(By.tagName("a"));
			evi.click();
			this.waitForPageToLoad();
			this.waitForAjax();
			this.waitForEvidenceSequenceToLoad();
		}
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public HashMap<String, String> getAllColumnHeadersInRNAITab() {
		HashMap<String, String> columns = new HashMap<String, String>();
		WebElement mainDiv = this.driver.findElement(By
				.cssSelector("div#ui-tabs-4"));
		List<WebElement> tds = mainDiv.findElements(By
				.cssSelector("tr:nth-child(1) td"));
		int a = 1;
		for (WebElement td : tds) {
			columns.put("" + a, td.getText());
			a++;
		}

		return columns;
	}

	public HashMap<String, String> getAllColumnHeadersInROITab() {
		HashMap<String, String> columns = new HashMap<String, String>();
		WebElement mainDiv = this.driver.findElement(By
				.cssSelector("div#ui-tabs-5"));
		List<WebElement> tds = mainDiv.findElements(By
				.cssSelector("tr:nth-child(1) td"));
		int a = 1;
		for (WebElement td : tds) {
			columns.put("" + a, td.getText());
			a++;
		}

		return columns;
	}
	public GeneticFeaturePage clickDetailSequenceTab() {
		WebElement evdTab = this.driver.findElement(By
				.cssSelector("ul#sequence_0_tabs li:nth-child(1)"));
		if (!StringUtils.containsIgnoreCase(evdTab.getAttribute("class"),
				"ui-state-active")) {
			WebElement evi = evdTab.findElement(By.tagName("a"));
			evi.click();
			this.waitForPageToLoad();
			this.waitForAjax();
			this.waitForEvidenceSequenceToLoad();
		}
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public GeneticFeaturePage clickOnRegulatoryCheckTab() {
		WebElement regTab = this.driver.findElement(By
				.cssSelector("ul#sequence_0_tabs li:nth-child(4)"));
		if (!StringUtils.containsIgnoreCase(regTab.getAttribute("class"),
				"ui-state-active")) {
			WebElement evi = regTab.findElement(By.tagName("a"));
			evi.click();
			this.waitForPageToLoad();
			this.waitForAjax();
			this.waitForRegulatoryCheckToLoad();
		}

		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	private void waitForRegulatoryCheckToLoad() {
		try {
			if (this.getRegulatoryCheckCountOnTab() > 0) {
				this.waitForWebElement(By.cssSelector("form#submitForm"));
			} else {
				new CommonLibrary().slowDown();
			}
		} catch (Exception e) {
		}
	}

	public String getRegulatoryCheckTabMessage() {
		WebElement form = this.driver.findElement(By
				.cssSelector("div#ui-tabs-9 form#submitForm"));
		String message = null;
		try {
			WebElement msgDiv = form.findElement(By
					.cssSelector("table div.info"));
			message = msgDiv.getText();
		} catch (Exception e) {
		}

		return message;
	}

	public boolean isCheckBoxRecommendedRegulatoryChecked() {
		WebElement span = this.driver.findElement(By
				.cssSelector("span#ro_recommend_regulatory_check"));
		WebElement input = span.findElement(By.tagName("input"));
		String value = input.getAttribute("value");
		if (StringUtils.equalsIgnoreCase(value, "on")) {
			return true;
		} else {
			return false;
		}
	}

	public String getAllergenSimilarityInRegulatory() {
		WebElement span = this.driver.findElement(By
				.cssSelector("span#ro_allergen_similarity"));
		String value = span.getText();
		return value;
	}

	public String getConsensesSiteInRegulatory() {
		WebElement span = this.driver.findElement(By
				.cssSelector("span#ro_glycosylation_consensus_sites"));
		String value = span.getText();
		return value;
	}

	public String getAllergenSearchDateInRegulatory() {
		WebElement span = this.driver.findElement(By
				.cssSelector("span#ro_allergen_search_date"));
		String value = span.getText();
		return value;
	}

	public int getEvidenceCountOnTabForGF() {

		WebElement evi = this.clickEvidenceGF.findElement(By.tagName("a"));
		String text = evi.getText();
		text = StringUtils.substringBetween(text, "(", ")");
		if (StringUtils.isBlank(text)) {
			return 0;
		} else {
			return Integer.parseInt(text);
		}

	}

	public GeneticFeaturePage addEvidenceInUpperSection(
			GeneticFeaturePage gfPage, int num) {

		gfPage.clickEvidenceTab();
		LiteratureSearchPage litSearchPage = (LiteratureSearchPage) gfPage
				.selectAddEvidence("literature");
		CreateLiteratureEvidenceDetailsForGeneticFeaturePage createLitPage = (CreateLiteratureEvidenceDetailsForGeneticFeaturePage) litSearchPage
				.searchThis("Bharitkar S, Mendel");
		createLitPage.enterObservation("test observation");
		createLitPage.enterRationale("test");
		PopUpAddTraitComponent popup = createLitPage.clickAddTraitComponent();
		createLitPage = (CreateLiteratureEvidenceDetailsForGeneticFeaturePage) popup
				.addTrait("biomass yield [en;XX;1]");
		if (!createLitPage.isThereOneGeneticFeatureWhenAddingEvidence()) {
			PopUpAddGeneSymbol popupSeq = createLitPage
					.clickAddGeneticFeatures();
			popupSeq.enterText("AAP55168");
			createLitPage = popupSeq.clickAdd();
		}
		gfPage = createLitPage.clickSave();
		if (num > 1) {
			gfPage.clickEvidenceTab();
			litSearchPage = (LiteratureSearchPage) gfPage
					.selectAddEvidence("literature");
			createLitPage = (CreateLiteratureEvidenceDetailsForGeneticFeaturePage) litSearchPage
					.searchThis("Rhodes HE, Chenevert L, Munsell M");
			createLitPage.enterObservation("test observation");
			createLitPage.enterRationale("test");
			popup = createLitPage.clickAddTraitComponent();
			createLitPage = (CreateLiteratureEvidenceDetailsForGeneticFeaturePage) popup
					.addTrait("biomass yield [en;XX;1]");
			if (!createLitPage.isThereOneGeneticFeatureWhenAddingEvidence()) {
				PopUpAddGeneSymbol popupSeq = createLitPage
						.clickAddGeneticFeatures();
				popupSeq.enterText("BI306443");
				createLitPage = popupSeq.clickAdd();
			}
			gfPage = createLitPage.clickSave();
		}
		return gfPage;

	}

	public GeneticFeaturePage addEvidenceInSequenceSection(
			GeneticFeaturePage gfPage, int num) {

		gfPage.clickEvidenceSequenceTab();
		LiteratureSearchPage litSearchPage = gfPage
				.selectAddEvidenceSequence("literature");
		CreateLiteratureEvidenceDetailsForGeneticFeaturePage createLitPage = (CreateLiteratureEvidenceDetailsForGeneticFeaturePage) litSearchPage
				.searchThis("Rhodes HE, Chenevert L, Munsell M");
		createLitPage.enterObservation("test observation");
		createLitPage.enterRationale("test");
		PopUpAddTraitComponent popup = createLitPage.clickAddTraitComponent();
		createLitPage = (CreateLiteratureEvidenceDetailsForGeneticFeaturePage) popup
				.addTrait("biomass yield [en;XX;1]");
		if (!createLitPage.isThereOneSequenceWhenAddingEvidence()) {
			PopUpAddSequenceAccession popupSeq = createLitPage
					.clickOnAddSequence();
			createLitPage = popupSeq.addSequenceName("CA119664-CDNA//");
		}
		gfPage = createLitPage.clickSave();

		if (num > 1) {
			// SYLK001607-CDNA/SYLKPROT
			gfPage.clickEvidenceSequenceTab();
			litSearchPage = gfPage.selectAddEvidenceSequence("literature");
			createLitPage = (CreateLiteratureEvidenceDetailsForGeneticFeaturePage) litSearchPage
					.searchThis("Bharitkar S, Mendel");
			createLitPage.enterObservation("test observation");
			createLitPage.enterRationale("test");
			popup = createLitPage.clickAddTraitComponent();
			createLitPage = (CreateLiteratureEvidenceDetailsForGeneticFeaturePage) popup
					.addTrait("biomass yield [en;XX;1]");
			if (!createLitPage.isThereOneSequenceWhenAddingEvidence()) {
				PopUpAddSequenceAccession popupSeq = createLitPage
						.clickOnAddSequence();
				createLitPage = popupSeq
						.addSequenceName("SYLK001607-CDNA/SYLKPROT");
			}
			gfPage = createLitPage.clickSave();
		}
		return gfPage;
	}

	private void clickEvidenceSequenceTab() {
		WebElement evdTab = this.driver.findElement(By
				.cssSelector("ul#sequence_0_tabs li:nth-child(2)"));
		if (!StringUtils.containsIgnoreCase(evdTab.getAttribute("class"),
				"ui-state-active")) {
			WebElement evi = this.clickEvidenceSequence.findElement(By
					.tagName("a"));
			evi.click();
			this.waitForPageToLoad();
		}
	}

	public boolean isDeleteThisGFButtonEnabled() {
		WebElement deleteButton = this.driver.findElement(By
				.id("deleteGeneticFeatureButton"));
		return deleteButton.isEnabled();
	}

	// Return true if the button 'Delete this sequence' in detail tab of GF is
	// enabled.
	public boolean isDeleteThisSequenceEnabled() {
		WebElement deleteButton = this.driver.findElement(By
				.cssSelector("input.formBtn.btn.deleteSequenceButton6"));
		return deleteButton.isEnabled();
	}

	public String getDeleteButtonToolTip() {
		WebElement deleteButton = this.driver.findElement(By
				.id("deleteGeneticFeatureButton"));
		return deleteButton.getAttribute("title");
	}

	public String getDeleteThisButtonToolTip() {
		String tooltip = this.deleteThisSequence.getAttribute("title");
		return tooltip;
	}
	public boolean checkIfMagnifyingGlassImageIsVerticallyAlligned_SequenceSection() {
		WebElement mainDiv = this.driver.findElement(By.id("ui-tabs-7"));
		List<WebElement> tables = mainDiv.findElements(By.tagName("table"));

		boolean flag = true;
		for (WebElement table : tables) {
			List<WebElement> trs = table.findElements(By.tagName("tr"));
			int a = 0;
			for (WebElement tr : trs) {
				a++;
				if (a == 1) {
					continue;
				}

				try {
					WebElement view = tr.findElement(By
							.cssSelector("td:nth-child(1) span.view"));
				} catch (Exception e) {
					flag = false;
					break;
				}

			}

			if (!flag) {
				break;
			}
		}

		return flag;
	}

	public boolean checkIfMagnifyingGlassImageIsVerticallyAlligned() {
		WebElement mainDiv = this.driver.findElement(By.id("ui-tabs-2"));
		List<WebElement> tables = mainDiv.findElements(By.tagName("table"));

		boolean flag = true;
		for (WebElement table : tables) {
			List<WebElement> trs = table.findElements(By.tagName("tr"));
			int a = 0;
			for (WebElement tr : trs) {
				a++;
				if (a == 1) {
					continue;
				}

				try {
					WebElement view = tr.findElement(By
							.cssSelector("td:nth-child(1) span.view"));
				} catch (Exception e) {
					flag = false;
					break;
				}

			}

			if (!flag) {
				break;
			}
		}

		return flag;
	}

	public boolean isEditButtonUnderDetailTabEnabled() {
		return this.edit.isEnabled();

	}

	public String getEditButtonToolTipDetailedTab() {
		return this.edit.getAttribute("title");
	}
	public String getCreatedByInDetailTab() {
		this.clickDetailTab();
		List<WebElement> table = this.driver.findElements(By
				.cssSelector("div#ui-tabs-1 form#submitForm table.table"));
		WebElement tr = table.get(1).findElement(
				By.cssSelector("tr:nth-child(7)"));
		String value = tr.findElement(By.cssSelector("td.field")).getText();

		return value;

	}

	public String getCreatedDateInDetailTab() {
		this.clickDetailTab();
		List<WebElement> table = this.driver.findElements(By
				.cssSelector("div#ui-tabs-1 form#submitForm table.table"));
		WebElement tr = table.get(1).findElement(
				By.cssSelector("tr:nth-child(8)"));
		String value = tr.findElement(By.cssSelector("td.field")).getText();

		return value;
	}

	public String getChromosomeDetailTab() {
		this.clickDetailTab();
		List<WebElement> table = this.driver.findElements(By
				.cssSelector("div#ui-tabs-1 form#submitForm table.table"));
		WebElement tr = table.get(1).findElement(
				By.cssSelector("tr:nth-child(6)"));

		String value = tr.findElement(By.cssSelector("td.field")).getText();

		return value;
	}

	public String getEcotypeDetailTab() {
		this.clickDetailTab();
		List<WebElement> table = this.driver.findElements(By
				.cssSelector("div#ui-tabs-1 form#submitForm table.table"));
		WebElement tr = table.get(1).findElement(
				By.cssSelector("tr:nth-child(5)"));
		String value = tr.findElement(By.cssSelector("td.field")).getText();

		return value;
	}

	public GeneticFeaturePage clickOnEditDetailTab() {
		this.clickDetailTab();
		WebElement edit = this.driver.findElement(By.id("edit1"));
		edit.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public void enterSymbolDetailTab(String str) {
		WebElement symbol = this.driver.findElement(By.id("symbol"));
		symbol.clear();
		symbol.sendKeys(str);
	}

	public GeneticFeaturePage clickOnSaveDetailTab() {
		WebElement edit = this.driver.findElement(By.id("save1"));
		edit.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public String getSymbolError() {
		WebElement symbolError = null;
		try {
			symbolError = this.driver.findElement(By.id("symbol_error"));
		} catch (Exception e) {
		}

		if (symbolError == null) {
			return null;
		} else {
			return symbolError.getText();
		}
	}

	public GeneticFeaturePage clickOnCancelDetailTab() {
		WebElement edit = this.driver.findElement(By.id("cancel1"));
		edit.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public HashMap<String, String> getAllFieldValuesDetailTab() {
		HashMap<String, String> vals = new HashMap<String, String>();
		// symbol
		WebElement symbol = this.driver.findElement(By.id("symbol"));
		vals.put("symbol", symbol.getAttribute("value"));
		// name
		WebElement name = this.driver
				.findElement(By
						.cssSelector("form#submitForm td.field input[name='locusTag']"));
		vals.put("name", name.getAttribute("value"));
		// NCBI ID
		WebElement ncbiid = this.driver.findElement(By
				.cssSelector("form#submitForm td.field input[name='geneID']"));
		vals.put("ncbiid", ncbiid.getAttribute("value"));
		// description
		WebElement desc = this.driver.findElement(By.id("description"));
		vals.put("description", desc.getAttribute("value"));
		// Synonyms
		WebElement synonyms = this.driver.findElement(By.id("synonyms"));
		vals.put("synonyms", synonyms.getAttribute("value"));
		// NCBI Taxon Id
		WebElement taxonId = this.driver.findElement(By.id("taxonId"));
		vals.put("taxonid", taxonId.getAttribute("value"));
		// Syngenta Taxon Id
		WebElement syngentataxonid = this.driver
				.findElement(By
						.cssSelector("form#submitForm td.field input[name='syngentaTaxonId']"));
		vals.put("syngentataxonid", syngentataxonid.getAttribute("value"));

		// Variety
		WebElement variety = this.driver.findElement(By
				.cssSelector("form#submitForm td.field input[name='variety']"));
		vals.put("variety", variety.getAttribute("value"));

		// Line
		WebElement line = this.driver.findElement(By
				.cssSelector("form#submitForm td.field input[name='line']"));
		vals.put("line", line.getAttribute("value"));

		// Ecotype
		WebElement ecotype = this.driver.findElement(By
				.cssSelector("form#submitForm td.field input[name='ecotype']"));
		vals.put("ecotype", ecotype.getAttribute("value"));

		return vals;

	}

	public void enterNameDetailTab(String string) {
		WebElement name = this.driver
				.findElement(By
						.cssSelector("form#submitForm td.field input[name='locusTag']"));
		name.clear();
		name.sendKeys(string);
	}

	public void enterNCBIIDDetailTab(String string) {
		WebElement ncbiid = this.driver.findElement(By
				.cssSelector("form#submitForm td.field input[name='geneID']"));
		ncbiid.clear();
		ncbiid.sendKeys(string);
	}

	public void enterDescriptionDetailTab(String string) {
		WebElement desc = this.driver.findElement(By.id("description"));
		desc.clear();
		desc.sendKeys(string);
	}

	public void enterSynonymsDetailTab(String string) {
		WebElement synonyms = this.driver.findElement(By.id("synonyms"));
		synonyms.clear();
		synonyms.sendKeys(string);
	}

	public void enterNDBITaxonIdDetailTab(String string) {
		WebElement taxonId = this.driver.findElement(By.id("taxonId"));
		taxonId.clear();
		taxonId.sendKeys(string);
	}

	public void enterSyngentaNCBIIdDetailTab(String string) {
		WebElement syngentataxonid = this.driver
				.findElement(By
						.cssSelector("form#submitForm td.field input[name='syngentaTaxonId']"));
		syngentataxonid.clear();
		syngentataxonid.sendKeys(string);
	}

	public void enterVarietyDetailTab(String string) {
		WebElement variety = this.driver.findElement(By
				.cssSelector("form#submitForm td.field input[name='variety']"));
		variety.clear();
		variety.sendKeys(string);
	}

	public void enterLineDetailTab(String string) {
		WebElement line = this.driver.findElement(By
				.cssSelector("form#submitForm td.field input[name='line']"));
		line.clear();
		line.sendKeys(string);
	}

	public void enterEcotype(String string) {
		WebElement ecotype = this.driver.findElement(By
				.cssSelector("form#submitForm td.field input[name='ecotype']"));
		ecotype.clear();
		ecotype.sendKeys(string);
	}

	// click the 'Delete this sequence button' .
	public GeneticFeaturePage clickDeleteThisSequence() {

		WebElement deleteThisSeq = this.driver.findElement(By
				.cssSelector("input.formBtn.btn.deleteSequenceButton6"));
		deleteThisSeq.click();
		// this.deleteThisSequence.click();
		WebDriverWait wait = new WebDriverWait(this.driver, 10);
		wait.until(ExpectedConditions.alertIsPresent());
		this.driver.switchTo().alert().accept();
		this.waitForPageToLoad();
		this.waitForAjax();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public GeneticFeaturePage addLeadInfoAndNominateDatabaseLead(
			GeneticFeaturePage gfPage, HashMap<String, String> columns) {
		gfPage = gfPage.clickOnLeadInfoTab();
		LeadNominationPage leadNominationpage = gfPage.clickOnNominateButton();

		PopUpAddSuggestedProjectNamePage popUpAddSuggProNamepage = leadNominationpage
				.clickOnAddSuggestedProjectName();

		popUpAddSuggProNamepage.enterSuggestedProjectName(columns
				.get("suggestedProjectName"));
		leadNominationpage = popUpAddSuggProNamepage.clickAdd();
		leadNominationpage.selectLeadSource(columns.get("lead_source"));
		leadNominationpage.selectLeadType(columns.get("lead_type"));
		leadNominationpage.enterLeadFunction(columns.get("lead_function"));
		PopUpAddSourceOfLeadFunctionInfo leadInfoPage = leadNominationpage
				.clickOnAddLeadFunctionInfo();
		leadNominationpage = leadInfoPage
				.addLeadFunctionInformationAndClickAdd(columns
						.get("source_lead_function_info"));
		leadNominationpage.enterRationale("test");
		gfPage = leadNominationpage.clickOnAddLeadNomination();

		return gfPage;
	}

	public HashMap<String, String> getAllFieldValuesLeadInfoTab(
			GeneticFeaturePage gfPage) {
		HashMap<String, String> data = new HashMap<String, String>();
		gfPage = gfPage.clickOnLeadInfoTab();
		String leadtype = gfPage.getLeadTypeLeadInfo();
		String leadname = gfPage.getLeadNameLeadInfoTab();
		String leadsource = gfPage.getLeadSourceLeadInfoTab();
		String countryoforigin = gfPage.getCountryOfOriginLeadInfo();
		String leadfunction = gfPage.getLeadFunctionLeadInfoTab();
		data.put("leadtype", leadtype);
		data.put("leadname", leadname);
		data.put("leadsource", leadsource);
		data.put("countryoforigin", countryoforigin);
		data.put("leadfunction", leadfunction);
		return data;
	}

	private String getLeadFunctionLeadInfoTab() {
		WebElement element = this.driver.findElement(By.id("lead_functions"));
		return element.getAttribute("value");
	}

	private String getCountryOfOriginLeadInfo() {
		WebElement element = this.driver.findElement(By.id("country_name8"));
		Select select = new Select(element);
		WebElement option = select.getFirstSelectedOption();
		return option.getText();
	}

	private String getLeadTypeLeadInfo() {
		WebElement element = this.driver.findElement(By.id("lead_type"));
		Select select = new Select(element);
		WebElement option = select.getFirstSelectedOption();
		return option.getText();
	}

	private String getLeadSourceLeadInfoTab() {
		WebElement element = this.driver.findElement(By.id("lead_source"));
		Select select = new Select(element);
		WebElement option = select.getFirstSelectedOption();
		return option.getText();
	}

	private String getLeadNameLeadInfoTab() {
		WebElement element = this.driver.findElement(By.id("lead_name"));
		return element.getAttribute("value");
	}

	public GeneticFeaturePage clickOnEditLeadInfo() {
		this.clickOnLeadInfoTab();
		WebElement edit = this.driver.findElement(By.id("edit8"));
		edit.click();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public void setAllFieldValuesLeadInfoTab(GeneticFeaturePage gfPage,
			HashMap<String, String> newVals) {
		gfPage = gfPage.clickOnLeadInfoTab();
		this.setLeadTypeLeadInfoTab(newVals.get("leadtype"));
		this.setLeadFunctionLeadInfoTab(newVals.get("leadfunction"));
		this.setCountryOfOriginLeadInfo(newVals.get("countryoforigin"));
		this.setLeadNameLeadInfoTab(newVals.get("leadname"));
		this.setLeadSourceLeadInfoTab(newVals.get("leadsource"));
	}

	private void setLeadTypeLeadInfoTab(String string) {
		if (StringUtils.isBlank(string)) {
			string = "Select Value";
		}
		WebElement element = this.driver.findElement(By.id("lead_type"));
		Select select = new Select(element);
		select.selectByVisibleText(string);

	}

	private void setLeadFunctionLeadInfoTab(String string) {
		WebElement element = this.driver.findElement(By.id("lead_functions"));
		element.clear();
		element.sendKeys(string);
	}

	private void setCountryOfOriginLeadInfo(String string) {
		if (StringUtils.isBlank(string)) {
			string = "Select Value";
		}
		WebElement element = this.driver.findElement(By.id("country_name8"));
		Select select = new Select(element);
		select.selectByVisibleText(string);

	}

	private void setLeadSourceLeadInfoTab(String string) {
		if (StringUtils.isBlank(string)) {
			string = "Select Value";
		}
		WebElement element = this.driver.findElement(By.id("lead_source"));
		Select select = new Select(element);
		select.selectByVisibleText(string);
	}

	private void setLeadNameLeadInfoTab(String string) {
		WebElement element = this.driver.findElement(By.id("lead_name"));
		element.clear();
		element.sendKeys(string);
	}

	public void clickOnCancelLeadInfoTab() {
		WebElement cancel = this.driver.findElement(By.id("cancel8"));
		cancel.click();
	}

	public GeneticFeaturePage clickOnDeleteLeadInfoTab() {
		WebElement delete = this.driver.findElement(By
				.id("deleteLeadNominationDetailsButton8"));
		delete.click();

		WebDriverWait wait = new WebDriverWait(this.driver, 10);
		wait.until(ExpectedConditions.alertIsPresent());
		this.driver.switchTo().alert().accept();
		this.waitForPageToLoad();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}
	public void clickOnSaveLeadInfoTab() {
		WebElement save = this.driver.findElement(By.id("save8"));
		save.click();
		// WebDriverWait wait = new WebDriverWait(this.driver, 10);
		// wait.until(ExpectedConditions.alertIsPresent());
		// this.driver.switchTo().alert().accept();
	}

	public String getLeadNameErrorLeadInfoTab() {
		WebElement element = null;
		try {
			element = this.driver.findElement(By.id("lead_name_error"));
		} catch (Exception e) {
		}
		String error = element.getText();
		return error;
	}

	public String getLeadSourceErrorLeadInfoTab() {
		WebElement element = this.driver
				.findElement(By.id("lead_source_error"));
		String error = element.getText();
		return error;
	}

	public String getLeadTypeErrorLeadInfoTab() {
		WebElement element = this.driver.findElement(By.id("lead_type_error"));
		String error = element.getText();
		return error;
	}

	public String getLeadFunctionErrorLeadInfoTab() {
		WebElement element = this.driver.findElement(By
				.id("lead_functions_error"));
		String error = element.getText();
		return error;
	}

	public GeneticFeaturePage addSuggestedProjectNameLeadInfo(String string) {
		WebElement button = this.driver.findElement(By.id("addProjects8"));
		button.click();
		PopupAddSuggestedProjectNameLeadInfo popup = new PopupAddSuggestedProjectNameLeadInfo(
				this.driver);
		PageFactory.initElements(this.driver, popup);
		GeneticFeaturePage page = (GeneticFeaturePage) popup
				.addProjectName(string);

		return page;
	}

	public List<String> getAllAddedProjectNames() {
		List<String> names = new ArrayList<String>();
		WebElement mainSpan = this.driver
				.findElement(By.id("projectNameList8"));
		List<WebElement> innerSpans = mainSpan.findElements(By.tagName("span"));
		for (WebElement element : innerSpans) {
			WebElement aTag = null;
			try {
				aTag = element.findElement(By.tagName("a"));
				if (StringUtils.equalsIgnoreCase(aTag.getAttribute("class"),
						"pointer")) {
					String name = element.getText();
					if (StringUtils.isNoneBlank(name)) {
						names.add(name.trim());
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
		return names;
	}

	public GeneticFeaturePage deleteThisProjectNameLeadInfo(String string) {
		string = StringUtils.deleteWhitespace(string);

		WebElement mainSpan = this.driver
				.findElement(By.id("projectNameList8"));
		List<WebElement> innerSpans = mainSpan.findElements(By.tagName("span"));
		for (WebElement element : innerSpans) {
			WebElement aTag = null;
			try {
				aTag = element.findElement(By.tagName("a"));
				if (StringUtils.equalsIgnoreCase(aTag.getAttribute("class"),
						"pointer")) {
					String name = element.getText();
					if (StringUtils.equalsIgnoreCase(
							StringUtils.deleteWhitespace(name), string)) {
						WebElement delete = aTag.findElement(By.tagName("img"));
						delete.click();
						break;
					}
				}
			} catch (Exception e) {
				continue;
			}
		}

		this.clickOnSaveLeadInfoTab();

		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public boolean isThereASaveButtonInLeadInfo() {
		WebElement save = null;
		try {
			save = this.driver.findElement(By.id("save8"));
		} catch (Exception e) {
			// do nothing
		}

		if (save != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isThereAnEditButtonInLeadInfo() {
		WebElement edit = null;
		try {
			edit = this.driver.findElement(By.id("edit8"));
		} catch (Exception e) {
			// do nothing
		}

		if (edit != null) {
			return true;
		} else {
			return false;
		}
	}

	// public void deleteEvidenceSequenceTab() {
	// this.clickOnEvidenceSequenceTab();
	// ViewLiteratureEvidenceDetailsPageSequence lit = this
	// .clickviewLiteratureEvidenceSequence();
	// lit.clickOnDelete();
	// }

	public void deleteEvidenceUpperTab() {
		while (true) {
			this.clickOnEvidenceTab();
			List<WebElement> evidences = this.driver
					.findElements(By
							.cssSelector("div#ui-tabs-2 div.clip table.table span.view"));
			if (evidences.size() != 0) {
				WebElement element = evidences.get(0);
				ViewLiteratureEvidenceDetailsGF lit = this
						.clickviewLiteratureEvidence(element);
				lit.clickDeleteToDeleteThisLiteratureEvidence();
			} else {
				break;
			}
		}
	}

	public void deleteEvidenceSequenceTab() {
		while (true) {
			this.clickOnEvidenceSequenceTab();
			List<WebElement> evidences = this.driver
					.findElements(By
							.cssSelector("div#ui-tabs-7 div.clip table.table span.view"));
			if (evidences.size() != 0) {
				WebElement element = evidences.get(0);
				ViewLiteratureEvidenceDetailsPageSequence lit = this
						.clickviewLiteratureEvidenceSequence(element);
				lit.clickOnDelete();
			} else {
				break;
			}
		}
	}

	public ViewLiteratureEvidenceDetailsPageSequence clickviewLiteratureEvidenceSequence(
			int a) {

		List<WebElement> evidences = this.driver.findElements(By
				.cssSelector("div#ui-tabs-7 div.clip table.table span.view"));
		WebElement span = evidences.get(a);
		return this.clickviewLiteratureEvidenceSequence(span);
	}

	public boolean isEditButtonInLeadInfoEnabled() {
		WebElement edit = this.driver.findElement(By.id("edit8"));
		boolean flag = edit.isEnabled();
		return flag;
	}

	public String getTextOfSynonyms() {
		String synony = this.synonyms.getText();
		return synony;
	}

	public boolean isDeleteButtonInLeadInfoEnabled() {
		WebElement edit = this.driver.findElement(By
				.id("deleteLeadNominationDetailsButton8"));
		boolean flag = edit.isEnabled();
		return flag;
	}
	public String getLeadInfoEditButtonToolTip() {
		WebElement edit = this.driver.findElement(By.id("edit8"));
		String toolTip = edit.getAttribute("title");
		return toolTip;
	}
	public String getLeadInfoDeleteButtonToolTip() {
		WebElement edit = this.driver.findElement(By
				.id("deleteLeadNominationDetailsButton8"));
		String toolTip = edit.getAttribute("title");
		return toolTip;
	}

	public String getDeleteThisSequenceButtonToolTip() {
		WebElement deleteThisSeq = this.driver.findElement(By
				.cssSelector("input.formBtn.btn.deleteSequenceButton6"));
		return deleteThisSeq.getAttribute("title");
	}

	public String getEditedSynonyms() {
		String synonymsEdit = this.synonymsedit.getText();
		// TODO Auto-generated method stub
		return synonymsEdit;
	}

}