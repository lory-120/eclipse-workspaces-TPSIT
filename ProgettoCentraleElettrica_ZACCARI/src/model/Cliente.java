package model;

import java.util.Random;

public class Cliente extends Thread
{
    private Random r = new Random();
    private CentraleElettrica centraleElettrica;
    private int correnteNecessaria;
    private boolean clienteUrgente;

    public Cliente(CentraleElettrica centraleElettrica)
    {
        this.centraleElettrica = centraleElettrica;
        this.correnteNecessaria = r.nextInt(0, 50) + 1;
        this.clienteUrgente = r.nextBoolean();
    }

    public Random getR() {
        return r;
    }

    public void setR(Random r) {
        this.r = r;
    }

    public int getCorrenteNecessaria() {
        return correnteNecessaria;
    }

    public void setCorrenteNecessaria(int correnteNecessaria) {
        this.correnteNecessaria = correnteNecessaria;
    }

    public CentraleElettrica getCentraleElettrica() {
        return centraleElettrica;
    }

    public void setCentraleElettrica(CentraleElettrica centraleElettrica) {
        this.centraleElettrica = centraleElettrica;
    }

    public boolean isClienteUrgente() {
        return clienteUrgente;
    }

    public void setClienteUrgente(boolean clienteUrgente) {
        this.clienteUrgente = clienteUrgente;
    }

    @Override
    public void run() {
        try
        {
            centraleElettrica.utilizzaCorrente(correnteNecessaria, clienteUrgente);

            Thread.sleep(2000);

            centraleElettrica.rilasciaCorrente(correnteNecessaria);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
