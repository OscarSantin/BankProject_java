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
    private final ArrayList<Investiment> investimentsHistory=new ArrayList<>();


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

    public void setClient(double saldo, double debito, double portafoglio,ArrayList<Investiment> investiments){
        this.debito=debito;
        this.saldo=saldo;
        this.portafoglio=portafoglio;
        this.investimenti=investiments;
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
        System.out.println("Deposito andato a buon fine!");

        addInfo();
    }

    public void preleva(double importo) {
        if (importo > saldo) {
            System.out.println("Fondi insufficienti nel conto!");
            return;
        }
        saldo -= importo;
        portafoglio += importo;
        System.out.println("Prelievo andato a buon fine!");

        addInfo();
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
        addInfo();
    }

    @Override
    public String toString() {
        return "Client{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", password='" + "**" + '\'' +
                ", saldo=" + saldo +
                ", debito=" + debito +
                ", portafoglio=" + portafoglio +
                ", investimenti attivi=" + formatInvestiments() +
                ", investimentsHistory=" + formatInvestimentsHistory("") +
                '}';
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
        investimentsHistory.add(inv);
        addInfo();
        return true;
    }

    public ArrayList<Investiment> getInvestimenti() { return investimenti; }

    public final void addInfo(){
        ArrayList<String> lines = new ArrayList<>();
        try (InputStream is = Bank.class.getClassLoader().getResourceAsStream("Clients.csv");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            String newLine;
            while ((line = br.readLine()) != null) {
                lines.add(line);
                String name=line.substring(0,line.indexOf(";"));
                String password;
                line=line.substring(line.indexOf(";")+1);
                password=line.substring(0,line.indexOf(";"));
                String investimentsHistory="";
                line=line.substring(line.indexOf(";")+1);
                line=line.substring(line.indexOf(";")+1);
                line=line.substring(line.indexOf(";")+1);
                line=line.substring(line.indexOf(";")+1);
                line=line.substring(line.indexOf(";")+1);
                if(line.contains(";")){
                    investimentsHistory=line.substring(0,line.indexOf(";"));
                }
                if(name.equals(this.getNome()+this.getCognome()) && password.equals(this.getPassword())){
                    lines.removeLast();
                    newLine=this.getNome()+this.getCognome()+';'+this.password+';'+saldo+';'+debito+';'+portafoglio+';'+formatInvestiments()+';'+formatInvestimentsHistory(investimentsHistory)+';';
                    newLine=newLine.replaceAll(";+", ";");
                    lines.add(newLine);
                }
            }
        } catch (Exception e) {
            System.out.println("Errore: file non trovato!");
        }
        String filePath = Bank.class.getClassLoader().getResource("").getPath() + "Clients.csv";
        File file = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for(int i=0;i<lines.size();i++){
                writer.write(lines.get(i)+'\n');
            }
        } catch (IOException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
    public String formatInvestiments(){
        String totalInv="";
        if(investimenti.isEmpty()){
            return "";
        }
        for(int c=0;c< investimenti.size();c++){
            totalInv+=investimenti.get(c)+",";
        }
        totalInv=totalInv.substring(0,totalInv.length()-1);
        return totalInv;
    }

    public String formatInvestimentsHistory(String previousones){
        String totalInv= "";
        if(previousones.isEmpty()){
            totalInv=previousones;
        }else{
            totalInv+=previousones+",";
        }
        if(investimentsHistory.isEmpty()){
            return previousones;
        }
        for(int c=0;c< investimentsHistory.size();c++){
            if(!previousones.contains(investimentsHistory.get(c).toString())){
                totalInv+=investimentsHistory.get(c)+",";
            }
            else{
                totalInv+=",";
            }
        }
        totalInv=totalInv.replaceAll(",,",",");
        totalInv=totalInv.substring(0,totalInv.length()-1);
        return totalInv;
    }
    public final double getSaldo()  { return saldo; }
    public final double getDebito()  { return debito; }
    public final String getNome()  { return nome; }
    public final String getCognome()  { return cognome; }
    protected final String getPassword(){return password;}
    protected final double getPortafoglio(){return portafoglio;}
}

