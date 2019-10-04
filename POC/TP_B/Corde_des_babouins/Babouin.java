// -*- coding: utf-8 -*-

enum Cote { EST, OUEST }                       // Le canyon possède un côté EST et un côté OUEST

class Babouin extends Thread{
    private static int numéroSuivant = 0;      // Compteur partagé par tous les babouins
    private final int numéro;                  // Numéro du babouin
    private Corde corde;                       // Corde utilisée par le babouin
    private Cote origine;                      // Côté du canyon où apparaît le babouin: EST ou OUEST

    Babouin(Corde corde, Cote origine){        // Constructeur de la classe Babouin
        this.corde = corde;                    // Chaque babouin peut utiliser la corde
        this.origine = origine;                // Chaque babouin apparaît d'un côté précis du canyon
        numéro = ++numéroSuivant;              // Chaque babouin possède un numéro distinct
    }

    public void run(){
        System.out.println("Le babouin " + numéro + " arrive sur le côté " + origine + " du canyon.");
        corde.saisir(origine);                 // Pour traverser, le babouin saisit la corde
        System.out.println("Le babouin " + numéro +
                           " commence à traverser sur la corde en partant de l'" + origine + ".");
        try { sleep(5000); } catch(InterruptedException e){} // La traversée ne dure que 5 secondes
        corde.lâcher(origine);                 // Arrivé de l'autre côté, le babouin lâche la corde
        System.out.println("Le babouin " + numéro + " a lâché la corde et s'en va.");
    }
    
    public static void main(String[] args){ 
        Corde corde = new Corde();                        // La corde relie les deux côtés du canyon
        for (int i = 1; i < 20; i++){
            try { Thread.sleep(500); } catch(InterruptedException ignorée){}		    
            if (Math.random() >= 0.5){
                new Babouin(corde, Cote.EST).start();     // Création d'un babouin à l'est du canyon
            } else {
                new Babouin(corde, Cote.OUEST).start(); // Création d'un babouin à l'ouest du canyon
            }
        } // Une vingtaine de babouins sont répartis sur les deux côtés du canyon
    }
}

class Corde {

    public synchronized void saisir(Cote origine){
    }
    
    public synchronized void lâcher(Cote origine){
    }
}
