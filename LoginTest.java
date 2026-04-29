
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import pages.LoginPage;
import pages.GmailPage;

public class LoginTest {

    @Test
    public void verifyUserLogin() throws Exception {

        WebDriver driver = new ChromeDriver();

        LoginPage login = new LoginPage(driver);
        GmailPage gmail = new GmailPage();

        driver.manage().window().maximize();

        // navigate to site
        login.navigate();

        // enter username
        login.login("akshaygarg283@gmail.com");

        // wait for OTP email
        Thread.sleep(15000);

        // fetch OTP from Gmail
        String otp = gmail.getOTP(
                "akshaygarg283@gmail.com",
                "axclycuqbsxddbjg"
        );

        // enter OTP and submit
        login.writeOTPandSubmit(otp);

        driver.quit();
    }
}