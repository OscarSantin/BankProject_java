package org.example;
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

     public void setClient(double saldo,double debito,double portafoglio){
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
    }

    public void preleva(double importo) {
        if (importo > saldo) {
            System.out.println("Fondi insufficienti nel conto!");
            return;
        }
        saldo -= importo;
        portafoglio += importo;

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

