package model;

import lombok.extern.log4j.Log4j2;

import java.util.Arrays;

@Log4j2
public class Langs {
    public final static String[] FR_LANGUAGE = {"Utilisateur", "Mot de passe", "Connexion"};
    public final static String[] DE_LANGUAGE = {"Benutzer", "Passwort", "Login"};
    public final static String[] EN_LANGUAGE = {"User", "Password", "Login"};


    public static boolean compareTranslatedLoginLabels(String lang, String[] translatedLabels) {
        log.info("Comparing translated login labels");
        boolean translatedLabelsCorrect = false;
        switch (lang) {
            case "FR":
                translatedLabelsCorrect = Arrays.equals(FR_LANGUAGE, translatedLabels);
                break;
            case "DE":
                translatedLabelsCorrect = Arrays.equals(DE_LANGUAGE, translatedLabels);
                break;
            case "EN":
                translatedLabelsCorrect = Arrays.equals(EN_LANGUAGE, translatedLabels);
                break;
            default:
                System.out.println("Library for selected language doesn't exist!");
        }
        return translatedLabelsCorrect;
    }
}
