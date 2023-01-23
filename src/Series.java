Fichier Series.java :
        ```java
import java.util.*;

public class Series {
    private static ArrayList<String> nomsSeries = new ArrayList<>();
    private static HashMap<String, ArrayList<Double>> valeursSeries = new HashMap<>();

    public static void ajouterSerie(String nom) {
        nomsSeries.add(nom);
        valeursSeries.put(nom, new ArrayList<Double>());
    }

    public static void supprimerSerie(String nom) {
        nomsSeries.remove(nom);
        valeursSeries.remove(nom);
    }

    public static void ajouterValeur(String nom, double valeur) {
        if (valeursSeries.containsKey(nom)) {
            valeursSeries.get(nom).add(valeur);
        } else {
            System.out.println("La série n'existe pas");
        }
    }

    public static void envoyerValeurs(String nom, PrintWriter out) {
        if (valeursSeries.containsKey(nom)) {
            for (double valeur : valeursSeries.get(nom)) {
                out.println(valeur);
            }
        } else {
            out.println("La série n'existe pas");
        }
    }
}
