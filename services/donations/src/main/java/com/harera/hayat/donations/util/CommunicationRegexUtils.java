package com.harera.hayat.donations.util;

public class CommunicationRegexUtils {

    public static boolean isValidTelegramLink(String link) {
        return link.matches("^(https://t.me/)([a-zA-Z0-9_]{5,32})$");
    }

    public static boolean isValidWhatsappLink(String link) {
        return link.matches("^(https://chat.whatsapp.com/)([a-zA-Z0-9_]{22})$")
                        || link.matches("^(https://wa.me/)([0-9]{11})$");
    }
}
