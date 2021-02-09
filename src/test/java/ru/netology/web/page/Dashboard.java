package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class Dashboard {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement increaseButton = $("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"] .button");
    private SelenideElement decreaseButton = $("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"] .button");

    public Dashboard() {
        heading.shouldBe(visible);
    }

    public Transfer increaseTransfer() {
        increaseButton.click();
        return new Transfer();
    }

    public Transfer decreaseTransfer() {
        decreaseButton.click();
        return new Transfer();
    }

    public int getCurrentBalance() {
        String balance = $(".list__item [data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]").getText();
        String[] substring = balance.split(",");
        String sub = substring[substring.length - 1];
        String s = sub.replaceAll("\\D+", "");
        return Integer.parseInt(s);
    }

    public int getCurrentBalanceBack() {
        String balance = $(".list__item [data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"]").getText();
        String[] substring = balance.split(",");
        String sub = substring[substring.length - 1];
        String s = sub.replaceAll("\\D+", "");
        return Integer.parseInt(s);
    }

    public int getExpectedBalance(int transfer) {
        int currentBalance = getCurrentBalance();
        return currentBalance + transfer;
    }

    public int getExpectedBalanceBack(int transfer) {
        int currentBalance = getCurrentBalanceBack();
        return currentBalance + transfer;
    }
}