// -*- coding: utf-8 -*-

import java.util.ArrayList;
import static java.lang.Thread.sleep;

public class SeptNains {
    public static void main(String[] args) {
        int nbNains = 7;
        String nom [] = {"Simplet", "Dormeur",  "Atchoum", "Joyeux", "Grincheux", "Prof", "Timide"};
        Nain nain [] = new Nain [nbNains];
        for(int i = 0; i < nbNains; i++) nain[i] = new Nain(nom[i]);
        for(int i = 0; i < nbNains; i++) nain[i].start();

        try {sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}

        System.out.println("------------------------------------------------------------");

        for(int i = 0; i < nbNains; i++)
        {
          System.out.println(nain[i].getName() + " divorce et part rejoindre la sorcière");
          nain[i].interrupt();
        }
        for(int i = 0; i < nbNains; i++) {
            try { nain[i].join(); } catch (InterruptedException e) {e.printStackTrace();}
        }
        System.out.println("C'est fini.");
    }
}

class BlancheNeige {
    private volatile boolean libre = true;        // Initialement, Blanche-Neige est libre.
    public ArrayList<String> waitingQueue = new ArrayList<String>();
    public synchronized void requérir () {
        System.out.println("\t" + Thread.currentThread().getName()
                           + " veut un accès exclusif à la ressource");

        waitingQueue.add(Thread.currentThread().getName());
    }

    public synchronized void accéder () {
        while(!waitingQueue.get(0).equals(Thread.currentThread().getName())) //The dwarf fell asleep
        {
          try { wait(); } catch (InterruptedException e) { this.burnOut(); }
        }

        System.out.println("\t\t" + Thread.currentThread().getName()
                           + " va accéder à la ressource.");
    }

    public synchronized void relâcher () {
        System.out.println("\t\t\t" + Thread.currentThread().getName()
                           + " relâche la ressource.");

        waitingQueue.remove(Thread.currentThread().getName());
        notifyAll();
    }
}

class Nain extends Thread {
    private static final BlancheNeige bn = new BlancheNeige();
    public Nain(String nom) {
        this.setName(nom);
    }
    public void run() {
        while(true) {
            bn.requérir();
            bn.accéder();
            System.out.println("\t\t" + getName() + " possède le privilège d'accès à Blanche-Neige.");
            try {sleep(1000);} catch (InterruptedException e) { burnOut(); }
            bn.relâcher();
        }
        // System.out.println(getName() + " a terminé!");
    }

    public void burnOut()
    {
      System.out.println(Thread.currentThread().getName() + "divorce et part rejoindre la sorcière");
      return;
    }
}

/*
  $ make
  $ $ java SeptNains
	Simplet veut un accès exclusif à la ressource
		Simplet va accéder à la ressource.
	Grincheux veut un accès exclusif à la ressource
	Timide veut un accès exclusif à la ressource
	Joyeux veut un accès exclusif à la ressource
	Atchoum veut un accès exclusif à la ressource
	Dormeur veut un accès exclusif à la ressource
	Prof veut un accès exclusif à la ressource
		Simplet possède le privilège d'accès à Blanche-Neige.
			Simplet relâche la ressource.
	Simplet veut un accès exclusif à la ressource
		Simplet va accéder à la ressource.
		Simplet possède le privilège d'accès à Blanche-Neige.
			Simplet relâche la ressource.
	Simplet veut un accès exclusif à la ressource
		Simplet va accéder à la ressource.
		Simplet possède le privilège d'accès à Blanche-Neige.
			Simplet relâche la ressource.
	Simplet veut un accès exclusif à la ressource
		Simplet va accéder à la ressource.
		Simplet possède le privilège d'accès à Blanche-Neige.
			Simplet relâche la ressource.
	Simplet veut un accès exclusif à la ressource
		Simplet va accéder à la ressource.
		Simplet possède le privilège d'accès à Blanche-Neige.
			Simplet relâche la ressource.
	Simplet veut un accès exclusif à la ressource
		Simplet va accéder à la ressource.
		Simplet possède le privilège d'accès à Blanche-Neige.
			Simplet relâche la ressource.
	Simplet veut un accès exclusif à la ressource
		Simplet va accéder à la ressource.
		Simplet possède le privilège d'accès à Blanche-Neige.
			Simplet relâche la ressource.
	Simplet veut un accès exclusif à la ressource
		Simplet va accéder à la ressource.
		Simplet possède le privilège d'accès à Blanche-Neige.
			Simplet relâche la ressource.
	Simplet veut un accès exclusif à la ressource
		Simplet va accéder à la ressource.
		Simplet possède le privilège d'accès à Blanche-Neige.
        ...
*/
