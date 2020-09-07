package ru.netology.data;

import lombok.Value;
import lombok.val;
import ru.netology.page.DashboardPage;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class Card {
        private String number;
        private int balance;


        public static Card getFirstCard() {
            val dashboard = new DashboardPage();
            return new Card("5559 0000 0000 0001", dashboard.getCardBalance("01"));
        }

        public static Card getSecondCard() {
            val dashboard = new DashboardPage();
            return new Card("5559 0000 0000 0002", dashboard.getCardBalance("02"));
        }

        public static int cardBalanceAfterSendMoney(int balance, int amount) {
            int total = balance - amount;
            return total;
        }

        public static int cardBalanceAfterGetMoney(int balance, int amount) {
            int total = balance + amount;
            return total;
        }
    }
}
