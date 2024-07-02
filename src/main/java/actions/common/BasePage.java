package actions.common;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import interfaces.BaseUI;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    public void openPageUrl(WebDriver driver, String url) {
        driver.get(url);
    }

    public String getPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

    public String getPageUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    public String getPageSourcerCode(WebDriver driver) {
        return driver.getPageSource();
    }

    public void backToPage(WebDriver driver) {
        driver.navigate().back();
    }

    public void forwardToPage(WebDriver driver) {
        driver.navigate().forward();
    }

    public void refreshpage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public Alert waitForAlertPresence(WebDriver driver) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        return explicitWait.until(ExpectedConditions.alertIsPresent());
    }

    public void acceptAlert(WebDriver driver) {
        waitForAlertPresence(driver).accept();
    }

    public void cancelAlert(WebDriver driver) {
        waitForAlertPresence(driver).dismiss();
    }

    public void getAlertText(WebDriver driver) {
        waitForAlertPresence(driver).getText();
    }

    public void sendkeyToAlert(WebDriver driver, String textSenkey) {
        waitForAlertPresence(driver).sendKeys(textSenkey);
    }

    public boolean isElementDisplay(WebDriver driver, String locator) {
        waitForELementVisible(driver, locator);
        return getElement(driver, locator).isDisplayed();
    }

    public void switchToWindowByID(WebDriver driver, String parentID) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String runWindow : allWindows) {
            if (!runWindow.equals(parentID)) {
                driver.switchTo().window(runWindow);
                break;
            }
        }
    }

    public void switchToWindowByTitle(WebDriver driver, String title) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String runWindows : allWindows) {
            driver.switchTo().window(runWindows);
            String currentWin = driver.getTitle();
            if (currentWin.equals(title)) {
                break;
            }
        }
    }

    public void closeAllWindowsWithoutParent(WebDriver driver, String parentID) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String runWindows : allWindows) {
            if (!runWindows.equals(parentID)) {
                driver.switchTo().window(runWindows);
                driver.close();
            }
        }
        driver.switchTo().window(parentID);
    }

    public By getLocator(String locator) {
        By by = null;
        if (locator.startsWith("id=")) {
            by = by.id(locator.substring(3));
        } else if (locator.startsWith("name=")) {
            by = by.name(locator.substring(5));
        } else if (locator.startsWith("class=")) {
            by = by.className(locator.substring(6));
        } else if (locator.startsWith("css=")) {
            by = by.cssSelector(locator.substring(4));
        } else if (locator.startsWith("xpath=")) {
            by = by.xpath(locator.substring(6));

        } else {
            throw new RuntimeException("Locator invalid");
        }
        return by;
    }

    public WebElement getElement(WebDriver driver, String locator) {
        return driver.findElement(getLocator(locator));
    }

    public WebElement getElement(WebDriver driver, String variableLocator, String... dynamicValues) {
        variableLocator = getLocatorDynamic(variableLocator, dynamicValues);
        return driver.findElement(getLocator(variableLocator));
    }

    public String getLocatorDynamic(String variableLocator, String... dynamicValues) {
        if (variableLocator.startsWith("xpath=")) {
            variableLocator = String.format(variableLocator, (Object[]) dynamicValues);
        }
        return variableLocator;
    }

    public String getElementAtribute(WebDriver driver, String locator, String nameAttribute) {
        return getElement(driver, locator).getAttribute(nameAttribute);
    }

    public String getElementValueCss(WebDriver driver, String locator, String propertyName) {
        return getElement(driver, locator).getCssValue(propertyName);
    }

    public String getHexaColorFromRGBA(String rgbaValue) {
        return Color.fromString(rgbaValue).asHex();
    }

    public List<WebElement> getListElement(WebDriver driver, String locator) {
        return driver.findElements(getLocator(locator));
    }

    public List<WebElement> getListElement(WebDriver driver, String variableLocator, String... dynamicValues) {
        return driver.findElements(getLocator(getLocatorDynamic(variableLocator, dynamicValues)));
    }

    public int getElementSize(WebDriver driver, String locator) {
        return getListElement(driver, locator).size();
    }

    private int getElementSize(WebDriver driver, String variableLocator, String... dynamicValues) {
        variableLocator = getLocatorDynamic(variableLocator, dynamicValues);
        return getListElement(driver, variableLocator, dynamicValues).size();
    }

    public void clickToElement(WebDriver driver, String locator) {
        waitForClickToElement(driver, locator);
        getElement(driver, locator).click();
    }

    public void clickToElement(WebDriver driver, String variableLocator, String... dynamicValues) {
        waitForClickToElement(driver, variableLocator, dynamicValues);
        getElement(driver, variableLocator, dynamicValues).click();
    }

    public void senkeyToElement(WebDriver driver, String locator, String textSenkey) {
        waitForELementVisible(driver, locator);
        WebElement element = getElement(driver, locator);
        element.clear();
        getElement(driver, locator).sendKeys(textSenkey);
    }

    public void senkeyToElement(WebDriver driver, String locator, String textSenkey, String... dynamicValues) {
        waitForELementVisible(driver, locator, dynamicValues);
        WebElement element = getElement(driver, locator, dynamicValues);
        element.clear();
        getElement(driver, locator, dynamicValues).sendKeys(textSenkey);
    }

    public String getElementText(WebDriver driver, String locator) {
        return getElement(driver, locator).getText();
    }

    public String getElementText(WebDriver driver, String variableLocator, String... dynamicLocator) {
        variableLocator = getLocatorDynamic(variableLocator, dynamicLocator);
        return getElement(driver, variableLocator).getText().trim();
    }

    public void selectItemInDefaultDropdown(WebDriver driver, String locator, String textItem) {
        Select select = new Select(getElement(driver, locator));
        select.selectByValue(textItem);
    }

    public String getSelectedItemInDefaultDropdown(WebDriver driver, String locator) {
        Select select = new Select(getElement(driver, locator));
        return select.getFirstSelectedOption().getText();

    }

    public boolean isDropdownMultiple(WebDriver driver, String locator) {
        Select select = new Select(getElement(driver, locator));
        return select.isMultiple();
    }

    public void selectItemCustomDropDown(WebDriver driver, String xpathParent, String xpathChild) {
        getElement(driver, xpathParent).click();
        threadSecond(2);
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitWait.until(ExpectedConditions.presenceOfElementLocated(getLocator(xpathChild)));
        List<WebElement> allItem = driver.findElements(getLocator(xpathChild));
        for (WebElement item : allItem) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
            item.click();
            threadSecond(3);
            break;
        }
    }

    public void selectItemCustomDropDown(WebDriver driver, String parentXpath, String childXpath, String expectedText, String... dymParent) {
        getElement(driver, parentXpath, dymParent).click();
        threadSecond(1);
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        List<WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getLocator(childXpath)));

        for (WebElement item : allItems) {
            if (allItems.size()>7) {
                JavascriptExecutor jsExcutor = (JavascriptExecutor) driver;
                jsExcutor.executeScript("arguments[0].scrollIntoView(true);", item);
            }
            if (item.getText().trim().equals(expectedText.trim())) {
                threadSecond(1);
                item.click();
                break;
            }
        }
    }

    public void checkToDefaultCheckBox(WebDriver driver, String locator) {
        WebElement element = getElement(driver, locator);
        if (!element.isSelected()) {
            element.click();
        }
    }
    public void checkToDefaultCheckBox(WebDriver driver, String locator, String ...dymLocator) {
        WebElement element = getElement(driver, locator,dymLocator);
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void unCheckToDefaultCheckBox(WebDriver driver, String locator) {
        WebElement element = getElement(driver, locator);
        if (element.isSelected()) {
            element.click();
        }
    }
    public void unCheckToDefaultCheckBox(WebDriver driver, String locator,String ...dymLocator) {
        WebElement element = getElement(driver, locator,dymLocator);
        if (element.isSelected()) {
            element.click();
        }
    }

    public boolean elementIsSelected(WebDriver driver, String locator) {
        return getElement(driver, locator).isSelected();
    }

    public boolean elementIsDisplayed(WebDriver driver, String locator) {
        return getElement(driver, locator).isDisplayed();
    }

    public boolean elementIsEnable(WebDriver driver, String locator) {
        return getElement(driver, locator).isEnabled();
    }

    public void switchToFrameIframe(WebDriver driver, String locator) {
        driver.switchTo().frame(getElement(driver, locator));
    }

    public void switchToDefaultContent(WebDriver driver, String locator) {
        driver.switchTo().defaultContent();
    }

    public void hoverMouseToElement(WebDriver driver, String locator) {
        Actions action = new Actions(driver);
        action.moveToElement(getElement(driver, locator)).perform();
    }

    public void pressEnter(WebDriver driver) {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ENTER).build().perform();
    }

    public Object executeForBrowser(WebDriver driver, String javaScript) {
        return ((JavascriptExecutor) driver).executeScript(javaScript);
    }

    public String getInnerText(WebDriver driver) {
        return (String) ((JavascriptExecutor) driver).executeScript("return document.documentElement.innerText;");
    }

    public boolean areExpectedTextInInnerText(WebDriver driver, String textExpected) {
        String textActual = (String) ((JavascriptExecutor) driver)
                .executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
        return textActual.equals(textExpected);
    }

    public void scrollToBottomPage(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public void navigateToUrlByJS(WebDriver driver, String url) {
        ((JavascriptExecutor) driver).executeScript("window.location = '" + url + "'");
    }

    public void highlightElement(WebDriver driver, String locator) {
        WebElement element = getElement(driver, locator);
        String originalStyle = element.getAttribute("style");
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element,
                "style", "border: 2px solid red; border-style: dashed;");
        threadSecond(1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element,
                "style", originalStyle);
    }

    public void clickToElementByJS(WebDriver driver, String locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", getElement(driver, locator));
    }

    public void scrollToElement(WebDriver driver, String locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getElement(driver, locator));
    }

    public void sendkeyToElementByJS(WebDriver driver, String locator, String value) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + value + "')",
                getElement(driver, locator));
    }

    public void setAttributeInDom(WebDriver driver, String locator, String attribute, String valueAttribute){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('"+attribute+"', '"+valueAttribute+"');",getElement(driver, locator) );
    }
    public void removeAttributeInDOM(WebDriver driver, String locator, String attributeRemove) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('" + attributeRemove + "');",
                getElement(driver, locator));
    }

    public boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
                        .equals("complete");
            }
        };
        return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
    }

    public boolean isJQueryAndJSLoadedSuccess(WebDriver driver) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return (Boolean) jsExecutor.executeScript("return (window.jQuery != null) && (jQuery.active==0); ");
            }
        };
        return explicitWait.until(jQueryLoad);
    }

    public String getElementValidationMessage(WebDriver driver, String locator) {
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].validationMessage;",
                getElement(driver, locator));
    }

    public boolean isImageLoaded(WebDriver driver, String locator) {
        boolean status = (boolean) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
                getElement(driver, locator));
        if (status) {
            return true;
        } else {
            return false;
        }
    }

    public void waitForELementVisible(WebDriver driver, String locator) {
        WebDriverWait explicitwait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitwait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locator)));
    }

    public void waitForELementVisible(WebDriver driver, String variableLocator, String... dynamicValues) {
        WebDriverWait explicitwait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitwait.until(ExpectedConditions
                .visibilityOfElementLocated(getLocator(getLocatorDynamic(variableLocator, dynamicValues))));
    }

    public void waitForAllELementVisible(WebDriver driver, String locator) {
        WebDriverWait explicitwait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitwait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getLocator(locator)));
    }

    public void waitForELementInVisible(WebDriver driver, String locator) {
        WebDriverWait explicitwait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitwait.until(ExpectedConditions.invisibilityOfElementLocated(getLocator(locator)));
    }

    public void waitForAllELementInVisible(WebDriver driver, String locator) {
        WebDriverWait explicitwait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitwait.until(ExpectedConditions.invisibilityOfAllElements(getListElement(driver, locator)));
    }

    public void waitForClickToElement(WebDriver driver, String locator) {
        WebDriverWait explicitwait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitwait.until(ExpectedConditions.elementToBeClickable(getLocator(locator)));
    }

    public void waitForClickToElement(WebDriver driver, String variableLocator, String... dynamicValues) {
        WebDriverWait explicitwait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitwait.until(ExpectedConditions
                .elementToBeClickable(getLocator(getLocatorDynamic(variableLocator, dynamicValues))));
    }

    public void threadSecond(long second) {
        try {
            Thread.sleep(1000 * second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getDateTimeNow() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime futureTime = currentDateTime.plusMinutes(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formattedDateTime = futureTime.format(formatter);
        return formattedDateTime;
    }
    public String getDateTomorrow() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime futureTime = currentDateTime.plusMinutes(60);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formattedDateTime = futureTime.format(formatter);
        return formattedDateTime;
    }

    public int ranDomNumber(){
        return (int) (Math.random() * 10000000);
    }

    public void loginPromotion(WebDriver driver,String userName, String password) {
        clickToElement(driver, BaseUI.LOGIN_SSO_BTN);
        threadSecond(2);
        senkeyToElement(driver, BaseUI.USERNAME_TXTBOX, userName);
        senkeyToElement(driver, BaseUI.PASSWORD_TXTBOX, password);
        clickToElement(driver, BaseUI.DYNAMIC_BTN, "Đăng nhập");
        threadSecond(3);
    }

    public String[] phanTachDauPhay(String text){
       return  text.split(",");
    }
}
