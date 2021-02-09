package ru.netology.web;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.page.Dashboard;
import ru.netology.web.page.Login;
import ru.netology.web.page.Transfer;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoney() {
        val amount = 500;
        val Login = new Login();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = Login.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        val Dashboard = new Dashboard();
        val expectedBalance = Dashboard.getExpectedBalance(amount);
        Dashboard.increaseTransfer();

        val Transfer = new Transfer();
        val transferInfo = DataHelper.getTransferInfo(String.valueOf(amount));
        Transfer.validMoneyTransfer(transferInfo);
        val finalBalance = Dashboard.getCurrentBalance();
        assertEquals(expectedBalance, finalBalance);
    }

    @Test
    void shouldBeErrorWhenCardFieldEmpty() {
        val Login = new Login();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = Login.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        val Dashboard = new Dashboard();
        Dashboard.increaseTransfer();

        val Transfer = new Transfer();
        val transferInfo = new DataHelper.TransferInfo("500", "");
        Transfer.invalidMoneyTransfer(transferInfo);
    }

    @Test
    void shouldBeErrorWhenAmountEmpty() {
        val Login = new Login();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = Login.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        val Dashboard = new Dashboard();
        Dashboard.increaseTransfer();

        val Transfer = new Transfer();
        val transferInfo = new DataHelper.TransferInfo("", "5559000000000002");
        Transfer.invalidMoneyTransfer(transferInfo);
    }

    @Test
    void shouldBeErrorWhenInvalidCard() {
        val Login = new Login();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = Login.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        val Dashboard = new Dashboard();
        Dashboard.increaseTransfer();

        val Transfer = new Transfer();
        val transferInfo = new DataHelper.TransferInfo("500", "5559000000000000");
        Transfer.invalidMoneyTransfer(transferInfo);
    }

    @Test
    void shouldBeErrorWhenNotEnoughMoneyToTransfer() {
        val Login = new Login();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = Login.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        val Dashboard = new Dashboard();
        val currentAmount = Dashboard.getCurrentBalanceBack();
        val amount = currentAmount + 1;
        Dashboard.increaseTransfer();

        val Transfer = new Transfer();
        val transferInfo = DataHelper.getTransferInfo(String.valueOf(amount));
        Transfer.invalidMoneyTransfer(transferInfo);
    }

    @Test
    void shouldTransferZeroWhenAmountZero() {
        val amount = 0;
        val Login = new Login();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = Login.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        val Dashboard = new Dashboard();
        val expectedBalance = Dashboard.getExpectedBalance(amount);
        Dashboard.increaseTransfer();

        val Transfer = new Transfer();
        val transferInfo = DataHelper.getTransferInfo(String.valueOf(amount));
        Transfer.validMoneyTransfer(transferInfo);
        val finalBalance = Dashboard.getCurrentBalance();
        assertEquals(expectedBalance,finalBalance);
    }

    @Test
    void shouldTransferAllMoney() {
        val Login = new Login();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = Login.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        val Dashboard = new Dashboard();
        val amount = Dashboard.getCurrentBalanceBack();
        val expectedBalance = Dashboard.getExpectedBalance(amount);
        Dashboard.increaseTransfer();

        val Transfer = new Transfer();
        val transferInfo = DataHelper.getTransferInfo(String.valueOf(amount));
        Transfer.validMoneyTransfer(transferInfo);
        val finalBalance = Dashboard.getCurrentBalance();
        assertEquals(expectedBalance,finalBalance);
    }
}