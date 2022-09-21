package com.Beymen.Tests;

import com.Beymen.Pages.BasePage;
import com.Beymen.Pages.CartPage;
import com.Beymen.Pages.ProductDetailPage;
import com.Beymen.Pages.ProductListPage;
import org.junit.Test;

import java.io.IOException;

public class BeymenTest extends BaseTest {

    @Test
    public void beymenTest() throws IOException {
        logger = report.createTest("Beymen Selenium Web Automation");

        BasePage basePage = new BasePage();
        ProductListPage productListPage = new ProductListPage();
        ProductDetailPage productDetailPage = new ProductDetailPage();
        CartPage cartPage = new CartPage();

        logInfo("www.beymen.com sitesi açılır.");
        basePage.openPage();

        logInfo("Ana sayfanın açıldığı kontrol edilir.");
        basePage.checkPage();

        logInfo("Arama kutucuğuna “şort” kelimesi girilir.");
        basePage.searchInInput(0, "şort");

        logInfo("Arama kutucuğuna girilen “şort” kelimesi silinir.");
        basePage.clearSearch();

        logInfo("Arama kutucuğuna “gömlek” kelimesi girilir.");
        basePage.searchInInput(1, "gömlek");

        logInfo("Klavye üzerinden “enter” tuşuna bastırılır.");
        basePage.pressEnter();

        logInfo("Sonuca göre sergilenen ürünlerden rastgele bir ürün seçilir.");
        productListPage.getRandomProduct();

        logInfo("Seçilen ürünün ürün bilgisi ve tutar bilgisi txt dosyasına yazılır.");
        productDetailPage.writeProductInformation();

        logInfo("Seçilen ürün sepete eklenir.");
        productDetailPage.addToBasket();

        logInfo("Ürün sayfasındaki fiyat ile sepette yer alan ürün fiyatının doğruluğu karşılaştırılır.");
        productDetailPage.checkPriceInBasket();

        logInfo("Adet arttırılarak ürün adedinin 2 olduğu doğrulanır.");
        cartPage.increaseQuantity("2");

        logInfo("Ürün sepetten silinerek sepetin boş olduğu kontrol edilir.");
        cartPage.deleteFromBasket();

    }

    public void logInfo(String text) {
        logger.info(text);
        log4j.info(text);
    }

}