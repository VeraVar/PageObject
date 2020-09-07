package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amountField = $("[data-test-id='amount'] input");
    private SelenideElement fromCardField = $("[data-test-id='from'] input");
    private SelenideElement toCardField = $("[data-test-id='to'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement cancelButton = $("[data-test-id='action-cancel']");

    public void getTransfer(DataHelper.Card card, int amount) {
        amountField.setValue(String.valueOf(amount));
        fromCardField.setValue(card.getNumber());
        transferButton.click();
    }

    public void getTransferCancel(DataHelper.Card card, int amount) {
        amountField.setValue(String.valueOf(amount));
        fromCardField.setValue(card.getNumber());
        cancelButton.click();
    }

    public SelenideElement getErrorMessage() {
        return $(withText("У вас недостаточно средств для перевода такой суммы")).shouldBe(Condition.visible);
    }
}
