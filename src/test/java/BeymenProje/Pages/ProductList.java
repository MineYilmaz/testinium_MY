package BeymenProje.Pages;

import BeymenProje.BeymenLocater.Product;
import BeymenProje.BeymenLocater.PList;
import BeymenProje.Tests.BaseTest;
import BeymenProje.Utilities.ElementHelper;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.Random;

public class ProductListPage {

    WebDriver driver;
    WebDriverWait wait;
    ElementHelper elementHelper;
    ExtentTest logger;

    public ProductListPage() {
        this.driver = BaseTest.driver;
        this.wait = new WebDriverWait(driver, 10);
        this.elementHelper = new ElementHelper(driver);
        this.logger = BaseTest.logger;
    }

    public void getRandomProduct() throws IOException {
        try {
            Random random = new Random();
            int productSize = elementHelper.findElements(PList.productList).size();
            int count = random.nextInt(productSize);
            WebElement product = elementHelper.findElements(PList.productList).get(count);
            elementHelper.scrollToElement(product);
            product.click();
            elementHelper.checkElementVisible(Product.productDescription);
            elementHelper.testCasePassed();
        } catch (Exception e) {
            elementHelper.testCaseFailed();
            throw new RuntimeException(e);
        }

    }

}