package org.example;
import java.io.*;
import java.util.ArrayList;
public class Client {
    private String nome;
    private String cognome;
    private String password;
    private double saldo;
    private double debito;
    private double portafoglio;
    private ArrayList<Investiment> investimenti=new ArrayList<>();


    public Client(final String nome, final String cognome,final String password)
     {
         this.nome=nome;
         this.cognome=cognome;
         this.password=password;
         this.saldo=(0.0);
         this.debito=(0.0);
         this.portafoglio=(100.0);
     }

    public Client() {}

    public void setClient(double saldo, double debito, double portafoglio){
        this.debito=debito;
        this.saldo=saldo;
        this.portafoglio=portafoglio;
     }

    public void deposita(double importo) {
        if (importo > portafoglio) {
            System.out.println("Fondi insufficienti nel portafoglio!");
            return;
        }

        portafoglio -= importo;
        saldo += importo;

        if (debito > 0) {
            double ripagato = Math.min(debito, saldo);
            saldo -= ripagato;
            debito -= ripagato;
        }
        ArrayList<String> lines = new ArrayList<>();
        try (InputStream is = Bank.class.getClassLoader().getResourceAsStream("Clients.csv");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            String newLine;
            while ((line = br.readLine()) != null) {
                lines.add(line);
                String name=line.substring(0,line.indexOf(";"));
                String password;
                line=line.substring(line.indexOf(";")+1,line.length()-1);
                password=line.substring(0,line.indexOf(";"));
                if(name.equals(this.getNome()+this.getCognome()) && password.equals(this.getPassword())){
                    lines.removeLast();
                    newLine=this.getNome()+';'+this.getCognome()+';'+this.password+';'+saldo+';'+debito+';'+portafoglio+';';
                    lines.add(newLine);
                }
            }
        } catch (Exception e) {
            System.out.println("Errore: file non trovato!");
        }
        String filePath = Bank.class.getClassLoader().getResource("").getPath() + "Clients.csv";
        File file = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(this.getNome()+this.getCognome()+';'+this.getPassword()+';'+this.getSaldo()+';'
                    +this.getDebito()+ ';'+this.getPortafoglio()+';'+this.getInvestimenti().toString()+';'+'\n');
        } catch (IOException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    public void preleva(double importo) {
        if (importo > saldo) {
            System.out.println("Fondi insufficienti nel conto!");
            return;
        }
        saldo -= importo;
        portafoglio += importo;
        ArrayList<String> lines = new ArrayList<>();
        try (InputStream is = Bank.class.getClassLoader().getResourceAsStream("Clients.csv");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            String newLine;
            while ((line = br.readLine()) != null) {
                lines.add(line);
                String name=line.substring(0,line.indexOf(";"));
                String password;
                line=line.substring(line.indexOf(";")+1,line.length()-1);
                password=line.substring(0,line.indexOf(";"));
                if(name.equals(this.getNome()+this.getCognome()) && password.equals(this.getPassword())){
                    lines.removeLast();
                    newLine=this.getNome()+';'+this.getCognome()+';'+this.password+';'+saldo+';'+debito+';'+portafoglio+';';
                    lines.add(newLine);
                }
            }
        } catch (Exception e) {
            System.out.println("Errore: file non trovato!");
        }
        String filePath = Bank.class.getClassLoader().getResource("").getPath() + "Clients.csv";
        File file = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(this.getNome()+this.getCognome()+';'+this.getPassword()+';'+this.getSaldo()+';'
                    +this.getDebito()+ ';'+this.getPortafoglio()+';'+this.getInvestimenti().toString()+';'+'\n');
        } catch (IOException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
    public void avanzaTempo(int mesi) {
        portafoglio += 100.0 * mesi;
        for (int i = 0; i < investimenti.size(); ) {
            Investiment it = investimenti.get(i);
            it.avanza(mesi);
            if (it.isTerminato()) {
                double guadagno = it.calcolaGuadagno();
                saldo += it.getValore() + guadagno;
                if (guadagno < 0) {
                    System.out.println("Investimento completato: Perdita di " + (-guadagno) +
                            " € e capitale di " + it.getValore() + " € restituito.");
                } else {
                    System.out.println("Investimento completato: Guadagno di " + guadagno +
                            " € e capitale di " + it.getValore() + " € restituito.");
                }
                investimenti.remove(i);
            } else {
                i++;
            }
        }

        if (saldo < 0) {
            System.out.println("Attenzione: Il saldo è negativo (" + saldo +
                    " €). Non è possibile effettuare nuovi investimenti finché il debito non è ripagato.");
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", password='" + password + '\'' +
                ", saldo=" + saldo +
                ", debito=" + debito +
                ", portafoglio=" + portafoglio +
                '}';
    }

    public final void visualizzaStato()  {             // TESTA
        System.out.println(this.toString());
        for (final Investiment investimento : investimenti) {
            if (investimento.isTerminato()) {
                System.out.println("Investimento completato: " + investimento.getTipo()
                        + " - Guadagno: " + investimento.calcolaGuadagno() + " �");
            } else {
                System.out.println("Investimento in corso: " + investimento.getTipo());
            }
        }
        return;
    }

    public boolean aggiungiInvestimento(final Investiment inv) {
        if (saldo < inv.getValore()) {
            System.out.println("Fondi insufficienti per effettuare l'investimento!");
            return false;
        }
        if (saldo < 0) {
            System.out.println("Impossibile fare un nuovo investimento, il saldo � negativo!");
            return false;
        }
        saldo -= inv.getValore();
        investimenti.add(inv);
        return true;
    }

    public void aggiungiDebito(double importo) { debito += importo; }

    public ArrayList<Investiment> getInvestimenti() { return investimenti; }

    public final double getSaldo()  { return saldo; }
    public final double getDebito()  { return debito; }
    public final String getNome()  { return nome; }
    public final String getCognome()  { return cognome; }
    protected final String getPassword(){return password;}
    protected final double getPortafoglio(){return portafoglio;}
};

