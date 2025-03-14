package org.example;

import java.util.ArrayList;
import java.io.*;
public class Bank {
    private ArrayList<Client> clienti=new ArrayList<>();

    public void aggiungiCliente(final Client cliente) {
        String filePath = Bank.class.getClassLoader().getResource("").getPath() + "Clients.csv";
        File file = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))) {
            writer.write(cliente.getNome()+cliente.getCognome()+';'+cliente.getPassword()+';'+cliente.getSaldo()+';'
                   +cliente.getDebito()+ ';'+cliente.getPortafoglio()+';'+cliente.getInvestimenti().toString()+';'+ cliente.formatInvestiments()+'\n');
        } catch (IOException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        clienti.add(cliente);
    }

    public boolean findClient(final Client cliente){
        try (InputStream is = Bank.class.getClassLoader().getResourceAsStream("Clients.csv");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
             String line;
             while ((line = br.readLine()) != null) {
                 String name=line.substring(0,line.indexOf(";"));
                 String password;
                 line=line.substring(line.indexOf(";")+1);
                 password=line.substring(0,line.indexOf(";"));
                 String saldo;
                  line=line.substring(line.indexOf(";")+1);
                 saldo=line.substring(0,line.indexOf(";"));
                 String debito;
                 line=line.substring(line.indexOf(";")+1);
                 debito=line.substring(0,line.indexOf(";"));
                 String portafoglio;
                 line=line.substring(line.indexOf(";")+1);
                 portafoglio=line.substring(0,line.indexOf(";"));
                 if(name.equals(cliente.getNome()+cliente.getCognome()) && password.equals(cliente.getPassword())){
                    cliente.setClient(Double.parseDouble(saldo),Double.parseDouble(debito),Double.parseDouble(portafoglio),cliente.getInvestimenti());
                    System.out.println("Utente trovato!");
                    return true;
                 }
             }
        } catch (Exception e) {
            System.out.println("Errore: file non trovato!");
        }
        return false;
    }

    public void avanzareTempo(Client cliente,int mesi) {
       // for (Client cliente : clienti) {
            cliente.avanzaTempo(mesi);
       // }
    }

    public final void mostraStatoDopoAvanzamento()  {
        for (final Client cliente : clienti) {
            System.out.println("\nStato aggiornato del cliente " + cliente.getNome() + " "
                    + cliente.getCognome() + ":\n");
            cliente.visualizzaStato();
        }
    }

    public final void mostraStatiClienti()  {
        try (InputStream is = Bank.class.getClassLoader().getResourceAsStream("Clients.csv");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.equals("")){
                    break;
                }
                String name=line.substring(0,line.indexOf(";"));
                line=line.substring(line.indexOf(";")+1);
                String saldo;
                line=line.substring(line.indexOf(";")+1);
                saldo=line.substring(0,line.indexOf(";"));
                String debito;
                line=line.substring(line.indexOf(";")+1);
                debito=line.substring(0,line.indexOf(";"));
                String portafoglio;
                line=line.substring(line.indexOf(";")+1);
                portafoglio=line.substring(0,line.indexOf(";"));
                line=line.substring(line.indexOf(";")+1);
                String investimentiAttivi=line.substring(0,line.indexOf(";"));
                line=line.substring(line.indexOf(";")+1);
                String investimentiTot=line.substring(0,line.indexOf(";"));
                System.out.println("Name: "+name+" Saldo: "+saldo+"\nDebito: "+debito+" Portafoglio: "+ portafoglio+"\nInvestimenti attivi=" +investimentiAttivi+ "\nInvestimentsHistory=" + investimentiTot + "\n");
            }
        } catch (Exception e) {
            e.getMessage();
            System.out.println("Errore: file non trovato!");
        }
    }

    public Client getCliente(int indice) {
        if (indice < 0 || indice >= clienti.size()) {
            throw new IndexOutOfBoundsException("Indice cliente non valido!");
        }
        return clienti.get(indice);
    }

    public final int numeroClienti()  { return clienti.size(); }
};

