package org.example;

public class Investiment {

    private String tipo;
    private double valore;
    private double rendimento;
    private double rischio;
    private int durata;
    private int mesiPassati;

    private void impostaParametri(final String tipo) {
        if (tipo.equals("basso") ) {
            rendimento = 0.05;
            rischio = 0.10;
        } else if (tipo.equals("medio")) {
            rendimento = 0.10;
            rischio = 0.20;
        } else if (tipo.equals("alto")) {
            rendimento = 0.15;
            rischio = 0.30;
        } else {
            rendimento = 0.05;
            rischio = 0.10;
            this.tipo = "basso";
        }
    }
    @Override
    public String toString(){
        return tipo+'/'+valore+'/'+durata;
    }

    public Investiment(final String tipo, double valore, int durata)
     {
        this.tipo=tipo;
        this.valore=valore;
        this.durata=durata;
        mesiPassati=0;
        impostaParametri(tipo);
    }

    public void avanza(int mesi) { mesiPassati += mesi; }

    public final double calcolaGuadagno()  {
        if (mesiPassati >= durata) {
            double rendimentoMensile = rendimento / 12.0;
            double guadagno = valore;

            for (int i = 0; i < durata; i++) {guadagno *= (1 + rendimentoMensile);
            }

            guadagno -= valore * rischio;

            return guadagno;
        }
        return 0;
    }

    public final Boolean isTerminato()  { return mesiPassati >= durata; }
    public final String getTipo()  { return tipo; }
    public final double  getValore()  { return valore; }
    public final double getRendimento()  { return rendimento; }
};

