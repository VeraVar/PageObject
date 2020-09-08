package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferTest {
    DashboardPage dashboard;

    @BeforeEach
    void SetUp() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboard = verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferToFirstCard() {
        int amount = 1;
        val cardBalanceFirst = dashboard.getFirstCardBalance();
        val cardBalanceSecond = dashboard.getSecondCardBalance();
        val cardInfo = DataHelper.Card.getSecondCard();
        val transferMoney = dashboard.firstCardDepositClick();
        transferMoney.getTransfer(cardInfo, amount);
        val cardBalanceAfterSendFirst = DataHelper.Card.cardBalanceAfterGetMoney(cardBalanceFirst, amount);
        val cardBalanceAfterSendSecond = DataHelper.Card.cardBalanceAfterSendMoney(cardBalanceSecond, amount);
        assertEquals(cardBalanceAfterSendFirst, dashboard.getFirstCardBalance());
        assertEquals(cardBalanceAfterSendSecond, dashboard.getSecondCardBalance());
    }

    @Test
    void shouldTransferToSecondCard() {
        int amount = 10001;
        val cardBalanceFirst = dashboard.getFirstCardBalance();
        val cardBalanceSecond = dashboard.getSecondCardBalance();
        val cardInfo = DataHelper.Card.getFirstCard();
        val transferMoney = dashboard.secondCardDepositClick();
        transferMoney.getTransfer(cardInfo, amount);
        val cardBalanceAfterSendFirst = DataHelper.Card.cardBalanceAfterSendMoney(cardBalanceFirst, amount);
        val cardBalanceAfterSendSecond = DataHelper.Card.cardBalanceAfterGetMoney(cardBalanceSecond, amount);
        assertEquals(cardBalanceAfterSendFirst, dashboard.getFirstCardBalance());
        assertEquals(cardBalanceAfterSendSecond, dashboard.getSecondCardBalance());
    }

    @Test
    void shouldReturnDashboardPageIfCancel() {
        int amount = 10000;
        val cardInfo = DataHelper.Card.getFirstCard();
        val transferMoney = dashboard.firstCardDepositClick();
        transferMoney.getTransferCancel(cardInfo, amount);
        dashboard.returnDashboardPage();
    }

    @Test
    void shouldReturnErrorIfTransferMoreThanBalance() {
        int amount = 100000;
        val cardInfo = DataHelper.Card.getSecondCard();
        val transferMoney = dashboard.firstCardDepositClick();
        transferMoney.getTransfer(cardInfo, amount);
        transferMoney.getErrorMessage();
    }
}
