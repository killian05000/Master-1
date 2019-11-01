// -*- coding: utf-8 -*-
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random ;

public class TriRapideThread {
    static final int taille = 41_000_000 ;                   // Longueur du tableau à trier
    static final int [] tableau = new int[taille] ;         // Le tableau d'entiers à trier
    static final int borne = 10 * taille ;                  // Valeur maximale dans le tableau
    static ExecutorService executor;
    static CompletionService<Void> ecs;
    static AtomicInteger nbTasks = new AtomicInteger(0);

    private static void echangerElements(int[] t, int m, int n) {
        int temp = t[m] ;
        t[m] = t[n] ;
        t[n] = temp ;
    }

    private static int partitionner(int[] t, int début, int fin) {
        int v = t[fin] ;                               // Choix (arbitraire) du pivot : t[fin]
        int place = début ;                            // Place du pivot, à droite des éléments déplacés
        for (int i = début ; i<fin ; i++) {            // Parcours du *reste* du tableau
            if (t[i] < v) {                            // Cette valeur t[i] doit être à droite du pivot
                echangerElements(t, i, place) ;        // On le place à sa place
                place++ ;                              // On met à jour la place du pivot
            }
        }
        echangerElements(t, place, fin) ;              // Placement définitif du pivot
        return place ;
    }

    private static void seqTrierRapidement(int[] t, int début, int fin) {
        if (début < fin) {                             // S'il y a un seul élément, il n'y a rien à faire!
            //System.out.println(t.length);

            int p = partitionner(t, début, fin) ;
            seqTrierRapidement(t, début, p-1) ;
            seqTrierRapidement(t, p+1, fin) ;
        }
    }

    private static void paralTrierRapidement(int[] t, int début, int fin) {
      executor = Executors.newFixedThreadPool(4);
      ecs = new ExecutorCompletionService<Void>(executor);
      nbTasks.set(0);
      _paralTrierRapidement(t, début, fin);

      while(nbTasks.decrementAndGet() > 0)
      {
        //System.out.println(nbTasks);
        try {
          ecs.take();
        } catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }

      executor.shutdown();
      ecs = null;
    }

    private static void _paralTrierRapidement(int[] t, int début, int fin) {
      if (début >= fin)                             // S'il y a un seul élément, il n'y a rien à faire!
        return;

      //System.out.println(t.length);
      int p = partitionner(t, début, fin) ;

      if(t.length > 1000 && t.length > taille/100) {
        ecs.submit(() -> _paralTrierRapidement(t, début, p-1), null);
        ecs.submit(() -> _paralTrierRapidement(t, p+1, fin), null);
        nbTasks.addAndGet(2);
      }
      else {
        System.out.print("#");
        seqTrierRapidement(t, début, p-1) ;
        seqTrierRapidement(t, p+1, fin) ;
      }


    }

    private static void afficher(int[] t, int début, int fin) {
        for (int i = début ; i <= début+3 ; i++) {
            System.out.print(" " + t[i]) ;
        }
        System.out.print("...") ;
        for (int i = fin-3 ; i <= fin ; i++) {
            System.out.print(" " + t[i]) ;
        }
        System.out.print("\n") ;
    }

    public static void main(String[] args) {
        Random alea = new Random() ;
        for (int i=0 ; i<taille ; i++) {                          // Remplissage aléatoire du tableau
            tableau[i] = alea.nextInt(2*borne) - borne ;
        }
        System.out.print("Tableau initial : ") ;
        afficher(tableau, 0, taille -1) ;                         // Affiche le tableau à trier

        System.out.println("Démarrage du tri rapide.") ;
        long débutDuTri = System.nanoTime();

        //seqTrierRapidement(tableau, 0, taille-1) ;                   // Tri du tableau
        paralTrierRapidement(tableau, 0, taille-1);

        long finDuTri = System.nanoTime();
        long duréeDuTri = (finDuTri - débutDuTri) / 1_000_000 ;
        System.out.print("Tableau trié : ") ;
        afficher(tableau, 0, taille -1) ;                         // Affiche le tableau obtenu
        System.out.println("obtenu en " + duréeDuTri + " millisecondes.") ;
    }
}


/*
  $ make
  javac *.java
  $ java TriRapide
  Tableau initial :  4967518 -8221265 -951337 4043143... -4807623 -1976577 -2776352 -6800164
  Démarrage du tri rapide.
  Tableau trié :  -9999981 -9999967 -9999957 -9999910... 9999903 9999914 9999947 9999964
  obtenu en 85 millisecondes.
*/
