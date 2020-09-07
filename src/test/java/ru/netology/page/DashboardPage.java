package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement dashboardField = $("[data-test-id='dashboard']");
    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private ElementsCollection depositButton = $$("[data-test-id='action-deposit']");

    public DashboardPage() {
        dashboardField.shouldHave(Condition.visible, text("Личный кабинет"));
    }

    public int getCardBalance(String id) {
        val text = cards.findBy(text(id)).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public int getFirstCardBalance() {
        return getCardBalance("01");
    }

    public int getSecondCardBalance() {
        return getCardBalance("02");
    }

    public TransferPage firstCardDepositClick() {
        depositButton.first().click();
        return new TransferPage();
    }

    public TransferPage secondCardDepositClick() {
        depositButton.last().click();
        return new TransferPage();
    }

    public String returnDashboardPage() {
        val text = $("[data-test-id='dashboard']").text();
        return text;
    }
}