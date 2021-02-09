package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class Transfer {
    private SelenideElement heading = $(byText("Пополнение карты"));
    private SelenideElement amount = $("[data-test-id = amount] input");
    private SelenideElement from = $("[data-test-id = from] input");
    private SelenideElement transferButton = $("[data-test-id = action-transfer]");
    private SelenideElement error = $("[data-test-id = error-notification]");

    public Transfer() {
        heading.shouldBe(visible);
    }

    public Dashboard validMoneyTransfer(DataHelper.TransferInfo info) {
        amount.doubleClick().sendKeys(Keys.DELETE);
        amount.setValue(info.getAmount());
        from.doubleClick().sendKeys(Keys.DELETE);
        from.setValue(info.getCard());
        transferButton.click();
        return new Dashboard();
    }

    public void invalidMoneyTransfer(DataHelper.TransferInfo info) {
        amount.doubleClick().sendKeys(Keys.DELETE);
        amount.setValue(info.getAmount());
        from.doubleClick().sendKeys(Keys.DELETE);
        from.setValue(info.getCard());
        transferButton.click();
        error.shouldBe(enabled);
    }
}