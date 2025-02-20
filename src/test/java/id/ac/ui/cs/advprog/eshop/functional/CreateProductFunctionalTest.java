package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void userCanCreateProduct(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));


        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));


        nameInput.sendKeys("Shampoo Cap Bambang");
        quantityInput.sendKeys("50");
        submitButton.click();

        wait.until(ExpectedConditions.urlContains("/product/list"));

        WebElement productTable = driver.findElement(By.tagName("table"));

        List<WebElement> rows = productTable.findElements(By.tagName("tr"));
        boolean productFound = rows.stream().anyMatch(row -> row.getText().contains("Shampoo Cap Bambang") && row.getText().contains("50"));

        assertTrue(productFound, "New product should be displayed in the product list.");
    }

    @Test
    void userCannotCreateProductWithInvalidInput(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        nameInput.sendKeys("");
        quantityInput.sendKeys("0");
        submitButton.click();

        WebElement nameError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameError")));
        WebElement quantityError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("quantityError")));

        assertTrue(nameError.getText().contains("Product name is required"), "Error message for name should appear.");
        assertTrue(quantityError.getText().contains("Quantity must be at least 1"), "Error message for quantity should appear.");
    }

}
