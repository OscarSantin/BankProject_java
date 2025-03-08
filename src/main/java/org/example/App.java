package org.example;
import java.util.Scanner;
/**
 * Hello world!
 *
 */
/*
*
*/
public class App 
{
    public static void main( String[] args )
    {
        Bank banca=new Bank();
        int scelta = -1;

        Scanner my_scan=new Scanner(System.in);

        System.out.println("Benvenuto nell'app della Banca!");

        while (scelta != 0) {
            System.out.println("\nMenu principale:");
            System.out.println("1. Log in/Registrazione");
            System.out.println("2. Deposita denaro");
            System.out.println("3. Preleva denaro");
            System.out.println("4. Effettua un investimento");
            System.out.println("5. Avanza nel tempo");
            System.out.println("6. Mostra lo stato di tutti i clienti");
            System.out.println("0. Esci");
            System.out.println("Scegli un'opzione: ");
            scelta= my_scan.nextInt();

            switch (scelta) {
                case 1: {
                    my_scan.nextLine();
                    String nome, cognome, password;
                    System.out.println("Inserisci il nome del cliente: ");
                    nome=my_scan.nextLine();
                    System.out.println("Inserisci il cognome del cliente: ");
                    cognome=my_scan.nextLine();
                    System.out.println("Inserisci la password del cliente: ");
                    password=my_scan.nextLine();
                    Client cliente=new Client(nome, cognome,password)
                    if(banca.findClient(cliente)==false){
                        System.out.println("Utente non trovato, creazione nuovo utente");
                        banca.aggiungiCliente(cliente);
                        System.out.println("Cliente aggiunto con successo!");
                    }
                    break;
                }
                case 2: {
                    my_scan.nextLine();
                    int indice;
                    double importo;
                    System.out.println("Seleziona il cliente (0 per il primo, 1 per il secondo, ecc.): ");
                    indice=my_scan.nextInt();
                    if (indice < 0 || indice >= banca.numeroClienti()) {
                        System.out.println("Cliente non valido.");
                        break;
                    }
                    System.out.println("Inserisci l'importo da depositare: ");
                    importo=my_scan.nextInt();
                    banca.getCliente(indice).deposita(importo);
                    break;
                }
                case 3: {
                    my_scan.nextLine();
                    int indice;
                    double importo;
                    System.out.println("Seleziona il cliente (0 per il primo, 1 per il secondo,ecc.): ");
                    indice=my_scan.nextInt();
                    if (indice < 0 || indice >= banca.numeroClienti()) {
                        System.out.println("Cliente non valido.");
                        break;
                    }
                    System.out.println("Inserisci l'importo da prelevare: ");
                    importo=my_scan.nextInt();
                    banca.getCliente(indice).preleva(importo);
                    break;
                }
                case 4: {
                    my_scan.nextLine();
                    int indice;
                    double valore;
                    int durata;
                    String tipo;

                    System.out.println("Seleziona il cliente (0 per il primo, 1 per il secondo,ecc.): ");
                    indice=my_scan.nextInt();
                    if (indice < 0 || indice >= banca.numeroClienti()) {
                        System.out.println("Cliente non valido.");
                        break;
                    }
                    my_scan.nextLine();

                    System.out.println("Inserisci il tipo di investimento (basso/medio/alto): ");
                    tipo=my_scan.nextLine();
                    System.out.println("Inserisci il valore dell'investimento: ");
                    valore=my_scan.nextInt();
                    System.out.println("Inserisci la durata in mesi: ");
                    durata=my_scan.nextInt();
                    Investiment nuovoInvestimento=new Investiment(tipo, valore, durata);
                    if (banca.getCliente(indice).aggiungiInvestimento(nuovoInvestimento)) {
                        System.out.println("Investimento aggiunto con successo!");
                    } else {
                        System.out.println("Fondi insufficienti per l'investimento.");
                    }
                    break;
                }

                case 5: {
                    my_scan.nextLine();
                    int mesi;
                    System.out.println("Inserisci il numero di mesi da avanzare: ");
                    mesi=my_scan.nextInt();

                    banca.avanzareTempo(mesi);


                    System.out.println("\nStato aggiornato dei clienti dopo l'avanzamento del tempo:");
                    banca.mostraStatoDopoAvanzamento();

                    break;
                }

                case 6: {
                    my_scan.nextLine();
                    System.out.println("\nStato di tutti i clienti:\n");
                    banca.mostraStatiClienti();
                    break;
                }
                case 0:
                    my_scan.nextLine();
                    System.out.println("Uscita dall'app. Arrivederci!");
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova.\n");
            }
        }

        return ;
    }
}
