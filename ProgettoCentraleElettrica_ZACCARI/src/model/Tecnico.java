package model;

public class Tecnico extends Thread
{
    private CentraleElettrica centraleElettrica;

    public Tecnico(CentraleElettrica centraleElettrica) {this.centraleElettrica = centraleElettrica;}

    public CentraleElettrica getCentraleElettrica() {return centraleElettrica;}

    public void setCentraleElettrica(CentraleElettrica centraleElettrica) {this.centraleElettrica = centraleElettrica;}

    @Override
    public void run() {
        try
        {
            centraleElettrica.inziaManutenzione();
            Thread.sleep(2000);
            centraleElettrica.fineManutenzione();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
