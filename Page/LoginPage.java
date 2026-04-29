package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    By userName = By.xpath("//input[@placeholder='Please enter email address']");
    By nextButton = By.xpath("//input[@name='login']"); 
    By loginButton = By.xpath("//button[@id='login_with_otp']"); 

    public void navigate(){
        driver.get("https://uae.sharafdg.com/my-account/");
    }

    public void login(String username){
        driver.findElement(userName).sendKeys(username);
        driver.findElement(nextButton).click();
    }

   public void writeOTPandSubmit(String otp) {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    for (int i = 0; i < otp.length(); i++) {
        String fieldId = "digit" + (i + 1);
        wait.until(ExpectedConditions.elementToBeClickable(By.id(fieldId)))
                .sendKeys(String.valueOf(otp.charAt(i)));
    }
}
}
