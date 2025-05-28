package main;

import model.*;

public class MainCentraleElettrica
{
    public static void main(String[] args)
    {
        CentraleElettrica ce = new CentraleElettrica(100);

        while(true)
        {
            Cliente c = new Cliente(ce);
            c.start();

            try
            {
                Thread.sleep(2000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }

            Tecnico t = new Tecnico(ce);
            t.start();
        }
    }
}
