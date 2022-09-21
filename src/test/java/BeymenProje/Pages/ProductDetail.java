package BeymenProje.Pages;

import BeymenProje.BeymenLocater.Cart;
import BeymenProje.BeymenLocater.Product;
import BeymenProje.Tests.BaseTest;
import BeymenProje.Utilities.ElementHelper;

import com.aventstack.extentreports.ExtentTest;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.Random;

public class ProductDetailPage {

    WebDriver driver;
    WebDriverWait wait;
    ElementHelper elementHelper;
    ExtentTest logger;

    private String detailPrice;

    public ProductDetailPage() {
        this.driver = BaseTest.driver;
        this.wait = new WebDriverWait(driver, 10);
        this.elementHelper = new ElementHelper(driver);
        this.logger = BaseTest.logger;
    }

    public void writeProductInformation() throws IOException {
        try {
            elementHelper.writeToTxt(Product.productDescription, Product.productColor, Product.productPrice);
            elementHelper.testCasePassed();
        } catch (Exception e) {
            elementHelper.testCaseFailed();
            throw new RuntimeException(e);
        }
    }

    public void addToBasket() throws IOException {
        try {
            detailPrice = elementHelper.getText(Product.productPrice);
            int bodySizeCount = elementHelper.findElements(Product.productSizes).size();
            while (true) {
                Random random = new Random();
                int count = random.nextInt(bodySizeCount);
                WebElement selectedBody = elementHelper.findElements(Product.productSizes).get(count);
                if (!selectedBody.getAttribute("class").contains("-disabled")) {
                    selectedBody.click();
                    break;
                }
            }
            elementHelper.click(Product.addBasketBtn);
            elementHelper.checkElementVisible(Product.addedToCartNotifTitle);
            String actualText = elementHelper.getText(Product.addedToCartNotifTitle);
            String expectedText = "Sepete Eklendi";
            if (expectedText.equals(actualText)) {
                Assert.assertEquals(expectedText, actualText);
                elementHelper.testCasePassed();
            } else {
                elementHelper.testCaseFailed();
            }
        } catch (Exception e) {
            elementHelper.testCaseFailed();
            throw new RuntimeException(e);
        }
    }

    public void checkPriceInBasket() throws IOException {
        try {
            elementHelper.click(Product.addedToCartNotifBtn);
            elementHelper.checkElementVisible(Cart.orderSummaryTitle);
            String salePrice = elementHelper.getText(Cart.productSalePrice);
            if (detailPrice.equals(salePrice)) {
                Assert.assertEquals(detailPrice, salePrice);
                elementHelper.testCasePassed();
            } else {
                elementHelper.testCaseFailed();
            }
        } catch (Exception e) {
            elementHelper.testCaseFailed();
            throw new RuntimeException(e);
        }
    }

}