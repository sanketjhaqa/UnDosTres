package com.UnDosTres.test.stepDefinitions;

import com.UnDosTres.test.pageObjects.PaymentDetailPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;

public class PaymentDetailPageSteps {

    public PaymentDetailPageSteps(PaymentDetailPage paymentDetailPage) {
        this.paymentDetailPage = paymentDetailPage;
    }

    private PaymentDetailPage paymentDetailPage;

    @Then("^I should be on Payment Screen where url containing (.*?)$")
    public void validating_Page_Url(String fraction) {
        Assert.assertTrue(paymentDetailPage.urlContains(fraction));
    }

    @And("^I should see on Payment Page text (.*?)$")
    public void validate_Text_On_Payment_Detail_Page_Screen(String text) {
        Assert.assertTrue(paymentDetailPage.getSummaryMessage().contains(text));
    }

    @Then("^I select payment option as (.*?)$")
    public void i_Click_Tarjeta(String option) {
        Assert.assertTrue(paymentDetailPage.selectOptionTarjeta(option));
    }

    @And("^I select radio button \"(.*?)\"$")
    public void select_Radio_Button(String button) {
        Assert.assertTrue(paymentDetailPage.selectRadioButtonHavingText(button));
    }

    @And("^I provide Card number (.*?)$")
    public void provide_Card_Number(String cardNumber){
        Assert.assertTrue(paymentDetailPage.enterCardNumber(cardNumber));
    }

    @And("^I provide card expiry month as (.*?)$")
    public void enter_CardExpiry_Month(String month){
        Assert.assertTrue(paymentDetailPage.enterExpiryMonth(month));
    }

    @And("^I provide card expiry year as (.*?)$")
    public void enter_CardExpiry_Year(String year){
        Assert.assertTrue(paymentDetailPage.enterExpiryYear(year));
    }

    @And("^I provide card cvv as (.*?)$")
    public void enter_Card_Cvv_Number(String cvv){
        Assert.assertTrue(paymentDetailPage.enterCCVNumber(cvv));
    }

    @And("^I provide email id \"(.*?)\" under correo electronico field$")
    public void enter_Email_Id_For_CardPayment(String emailId){
        Assert.assertTrue(paymentDetailPage.enterEmailId(emailId));
    }

    @When("^I click on Pagar con Tarjeta button$")
    public void click_On_PagarConTarjeta_Button(){
        Assert.assertTrue(paymentDetailPage.clickpagarConTarjetaButton());
    }

    @Then("^I should see a pop up having text \"(.*?)\"$")
    public void validate_Text_On_PopUp(String expected){
        Assert.assertEquals(paymentDetailPage.getTextFromPopUpHeader(),expected);
    }

    @When("^I provide email id as \"(.*?)\" and password as (.*?) in pop up$")
    public void enter_EmailId_And_Password_In_PopUp(String email,String password){
        Assert.assertTrue(paymentDetailPage.fillPopUpEmailIdAndPassword(email,password));
    }
    @And("^I select check box \"I'm not a robot\" and click on \"Acceso\" button$")
    public void select_CheckBox_IamNotRobot(){
        Assert.assertTrue(paymentDetailPage.clickRobotCheckBoxAndClickSubmit());
    }

    @Then("^I should be able to see \"(.*?)\"$")
    public void validate_Recharge_Success(String expected){
        Assert.assertEquals(paymentDetailPage.getRechargeSuccessMessage(),expected);
    }
}
