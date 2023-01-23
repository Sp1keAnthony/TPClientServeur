
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private String pseudo;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String pseudo) {
        this.pseudo = pseudo;
    }

    public void seConnecter() {
        try {
            socket = new Socket("localhost", Serveur.PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Hôte inconnu");
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("Impossible de se connecter au serveur");
            System.exit(-1);
        }
    }

    public void envoyerCommande(String commande) {
        out.println(commande);
    }

    public String recevoirReponse() {
        try {
            return in.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public void seDeconnecter() {
        try {
            out.println("fin");
            socket.close();
        } catch (IOException e) {
            System.err.println("Impossible de se déconnecter");
        }
    }
    public void inscription() {
        envoyerCommande("inscription " + pseudo);
        String reponse = recevoirReponse();
        if(reponse.equals("Inscription réussie"))
            System.out.println("Inscription réussie");
        else
            System.out.println("Echec de l'inscription");
    }
    public void desinscription() {
        envoyerCommande("desinscription");
        String reponse = recevoirReponse();
        if(reponse.equals("Désinscription réussie"))
            System.out.println("Désinscription réussie");
        else
            System.out.println("Echec de la désinscription");
    }
    public void effacement() {
        envoyerCommande("effacement");
        String reponse = recevoirReponse();
        if(reponse.equals("Effacement réussi"))
            System.out.println("Effacement réussi");
        else
            System.out.println("Echec de l'effacement");
    }
    public void liste() {
        envoyerCommande("liste");
        String reponse = recevoirReponse();
        if(reponse.equals("Aucune série disponible"))
            System.out.println("Aucune série disponible");
        else
            System.out.println("Liste des séries disponibles :");
        while((reponse = recevoirReponse()) != null)
            System.out.println(reponse);
    }
}
