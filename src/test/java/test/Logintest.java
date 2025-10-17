package test;

import org.testng.annotations.Test;

import pages.LoginPage;

import base.Projectspecificationmethod;


public class Logintest extends Projectspecificationmethod  {

    @Test
    public void verifyValidLogin() throws Exception {
        testName = "Verify Valid Login";
        testDescription = "Validate login functionality with valid credentials";
        testCategory = "Smoke";
        testAuthor = "Prarthana";

        // IMPORTANT: Projectspecificationmethod @BeforeMethod must already have launched browser
        LoginPage login = new LoginPage();
        login.loginAs("standard_user", "secret_sauce");
    }
}


	

