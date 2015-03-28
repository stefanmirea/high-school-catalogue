package liceu;

import grafic.Fereastra;

public class Main
{
    public static void main(String[] args)
    {
        Centralizator centralizator = Centralizator.getInstance();
        Fereastra fereastra = new Fereastra("Catalog");
    }
}