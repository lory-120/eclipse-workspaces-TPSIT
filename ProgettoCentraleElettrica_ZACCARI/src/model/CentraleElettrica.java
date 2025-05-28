package model;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CentraleElettrica
{
    //Attributi
    private final int Q;
    private int correnteDisponibile;
    private int contaUtenti;
    private ReentrantLock lock;
    private Condition condClientiNormali;
    private Condition condClienteUrgente;
    private Condition condTecnico;
    private boolean manutenzioneInCorso;

    public CentraleElettrica(int q)
    {
        this.Q = q;
        this.correnteDisponibile = q;
        this.contaUtenti = 0;
        lock = new ReentrantLock();
        condClientiNormali = lock.newCondition();
        condClienteUrgente = lock.newCondition();
        condTecnico = lock.newCondition();
        manutenzioneInCorso = false;
    }

    public int getQ() {return Q;}

    public int getCorrenteDisponibile() {return correnteDisponibile;}

    public void setCorrenteDisponibile(int correnteDisponibile) {this.correnteDisponibile = correnteDisponibile;}

    public int getContaUtenti() {return contaUtenti;}

    public void setContaUtenti(int contaUtenti) {this.contaUtenti = contaUtenti;}

    public void utilizzaCorrente(int correnteNecessaria, boolean clienteUrgente) throws InterruptedException
    {
        lock.lock();
        try
        {
            while(correnteDisponibile < correnteNecessaria || manutenzioneInCorso) //correzione della condizione
            {
                System.out.println(Thread.currentThread().getName() + " e' in attesa");
                if(clienteUrgente)
                {
                    condClienteUrgente.await();
                }
                else
                {
                    condClientiNormali.await();
                }
            }

            correnteDisponibile -= correnteNecessaria;
        }
        finally
        {
            lock.unlock();
        }

    }

    public void rilasciaCorrente(int correnteNecessaria)
    {
        try
        {
            lock.lock();
            correnteDisponibile += correnteNecessaria;
            System.out.println(Thread.currentThread().getName() + " ha usato " + correnteNecessaria + " di corrente");
            condTecnico.signalAll();
            condClienteUrgente.signalAll();
            condClientiNormali.signalAll();
        }
        finally
        {
            lock.unlock();
        }
    }

    public void inziaManutenzione() throws InterruptedException
    {
        try
        {
            lock.lock();
            while(correnteDisponibile == Q)
            {
                condTecnico.await();
            }
            manutenzioneInCorso = true;
        }
        finally
        {
            lock.unlock();
        }
    }

    public void fineManutenzione()
    {
        try
        {
            lock.lock();
            manutenzioneInCorso = false;
            System.out.println("Manutenzione eseguita");
        }
        finally
        {
            lock.unlock();
        }
    }
}