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
        Client cliente=new Client();
        int scelta = -1;

        Scanner my_scan=new Scanner(System.in);

        System.out.println("Benvenuto nell'app della Banca!");

        while (scelta != 0) {
            if(cliente.getNome()!=null){
                System.out.println("\nMenu principale:");
                System.out.println("1. Log in/Registrazione");
                System.out.println("2. Deposita denaro");
                System.out.println("3. Preleva denaro");
                System.out.println("4. Effettua un investimento");
                System.out.println("5. Avanza nel tempo");
                System.out.println("6. Mostra lo stato di tutti i clienti");
                System.out.println("0. Esci");
                System.out.println("Scegli un'opzione: ");
                scelta= newpositiveInt();
            }
            else{
                System.out.println("1. Log in/Registrazione");
                System.out.println("6. Mostra lo stato di tutti i clienti");
                System.out.println("0. Esci");
                System.out.println("Per selezionare il resto delle operazioni effettuare il login ");
                System.out.println("Scegli un'opzione: ");
                scelta= newpositiveInt();
            }
            switch (scelta) {
                case 1: {
                    //my_scan.nextLine();
                    String nome, cognome, password;
                    System.out.println("Inserisci il nome del cliente: ");
                    nome=newStr();
                    System.out.println("Inserisci il cognome del cliente: ");
                    cognome=newStr();
                    System.out.println("Inserisci la password del cliente: ");
                    password=newStr();
                    cliente=new Client(nome, cognome,password);
                    if(!banca.findClient(cliente)){
                        System.out.println("Utente non trovato, creazione nuovo utente");
                        banca.aggiungiCliente(cliente);
                        System.out.println("Cliente aggiunto con successo!");
                    }
                    break;
                }
                case 2: {
                    double importo;
                    System.out.println("Inserisci l'importo da depositare: ");
                    importo=newpositiveDouble();
                    cliente.deposita(importo);
                    break;
                }
                case 3: {
                    double importo;
                    System.out.println("Inserisci l'importo da prelevare: ");
                    importo=newpositiveDouble();
                    cliente.preleva(importo);
                    break;
                }
                case 4: {
                    double valore;
                    int durata;
                    String tipo;
                    System.out.println("Inserisci il tipo di investimento (basso/medio/alto): ");
                    tipo=newStr();
                    System.out.println("Inserisci il valore dell'investimento: ");
                    valore=my_scan.nextInt();
                    System.out.println("Inserisci la durata in mesi: ");
                    durata=my_scan.nextInt();
                    Investiment nuovoInvestimento=new Investiment(tipo, valore, durata);
                    if (cliente.aggiungiInvestimento(nuovoInvestimento)) {
                        System.out.println("Investimento aggiunto con successo!");
                    }
                    break;
                }

                case 5: {
                    int mesi;
                    System.out.println("Inserisci il numero di mesi da avanzare: ");
                    mesi=my_scan.nextInt();
                    banca.avanzareTempo(cliente,mesi);

                    break;
                }

                case 6: {
                    System.out.println("\nStato di tutti i clienti:\n");
                    banca.mostraStatiClienti();
                    break;
                }
                case 0:
                    System.out.println("Uscita dall'app. Arrivederci!");
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova.\n");
            }
        }
    }
    public static String newStr(){
        Scanner scan=new Scanner(System.in);
        String s=scan.nextLine();
        while(s.isBlank()){
            System.out.println("La stringa non può essere vuota o essere composta solo da spazi!");
            s=scan.nextLine().trim();
        }
        return s;
    }
    public static int newpositiveInt(){
        Scanner scan=new Scanner(System.in);
        int i= scan.nextInt();
        while(i<0){
            System.out.println("Errore: inserisici un numero valido e positivo");
            i= scan.nextInt();
        }
        return i;
    }
    public static double newpositiveDouble(){
        Scanner scan=new Scanner(System.in);
        double d=scan.nextDouble();
        while(d<0.0){
            System.out.println("Errore: inserisici un numero valido e positivo");
            d= scan.nextDouble();
        }
        return d;
    }

}
