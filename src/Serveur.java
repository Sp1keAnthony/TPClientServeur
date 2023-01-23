import java.io.*;
import java.net.*;
import java.util.*;

public class Serveur {
    private static final int PORT = 1234; // numéro de port à utiliser pour la connexion
    private static HashMap<String, PrintWriter> clients = new HashMap<>(); // liste des clients inscrits
    private static ArrayList<String> nomsSeries = new ArrayList<>(); // liste des séries disponibles

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Le serveur est à l'écoute sur le port " + PORT);
            while (true) {
                new ThreadClient(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("Impossible d'écouter sur le port " + PORT);
            System.exit(-1);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Impossible de fermer le port " + PORT);
                System.exit(-1);
            }
        }
    }

    private static class ThreadClient extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String pseudo;

        public ThreadClient(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message = in.readLine();
                while (message != null && !message.equals("fin")) {
                    if (message.startsWith("inscription ")) {
                        inscription(message.substring(12));
                    } else if (message.startsWith("desinscription")) {
                        desinscription();
                    } else if (message.startsWith("effacement")) {
                        effacement();
                    } else if (message.startsWith("liste")) {
                        liste();
                    } else {
                        out.println("Commande inconnue");
                    }
                    message = in.readLine();
                }
            } catch (IOException e) {
                System.err.println("Le client " + pseudo + " s'est déconnecté");
            } finally {
                desinscription();
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Impossible de fermer la socket");
                }
            }
        }
        private void inscription(String pseudo) {
            if (clients.containsKey(pseudo)) {
                out.println("Pseudo déjà utilisé");
            } else {
                this.pseudo = pseudo;
                clients.put(pseudo, out);
                System.out.println("Le client " + pseudo + " s'est inscrit");
                out.println("Inscription réussie");
// Envoyer la liste des séries disponibles au nouveau client
                liste();
            }
        }
        private void desinscription() {
            if (pseudo == null) {
                out.println("Vous n'êtes pas inscrit");
            } else {
                clients.remove(pseudo);
                System.out.println("Le client " + pseudo + " s'est désinscrit");
                out.println("Désinscription réussie");
                pseudo = null;
            }
        }

        private void effacement() {
            if (pseudo == null) {
                out.println("Vous n'êtes pas inscrit");
            } else {
                // Code pour effacer les données du client
                System.out.println("Les données du client " + pseudo + " ont été effacées");
                out.println("Effacement réussi");
            }
        }

        private void liste() {
            if (nomsSeries.isEmpty()) {
                out.println("Aucune série disponible");
            } else {
                out.println("Liste des séries disponibles :");
                for (String nom : nomsSeries) {
                    out.println(nom);
                }
            }
        }
    }
}