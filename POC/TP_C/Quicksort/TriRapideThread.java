// -*- coding: utf-8 -*-
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random ;
import java.util.Arrays;

public class TriRapideThread {
    static final int taille = 41_000_000 ;                   // Longueur du tableau à trier
    static final int [] tableauSeq = new int[taille] ;         // Le tableau d'entiers à trier
    static final int [] tableauPara = new int[taille] ;         // Le tableau d'entiers à trier
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

      int i = 0;
      while(nbTasks.decrementAndGet() > 0)
      {
        //System.out.println(nbTasks);
        try {
          ecs.take();
          i++;
        } catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }
      System.out.println(i + " tâches effectuées");
      executor.shutdown();
      ecs = null;
    }

    private static void _paralTrierRapidement(int[] t, int début, int fin) {
      if (début >= fin)                             // S'il y a un seul élément, il n'y a rien à faire!
        return;

      //System.out.println(t.length);
      int p = partitionner(t, début, fin) ;
      int partSize = fin - début;

      if(partSize > 1000 && partSize > taille/100) {
        ecs.submit(() -> _paralTrierRapidement(t, début, p-1), null);
        ecs.submit(() -> _paralTrierRapidement(t, p+1, fin), null);
        nbTasks.addAndGet(2);
      }
      else {
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
            int tmp = alea.nextInt(2*borne) - borne ;
            tableauSeq[i] = tmp;
            tableauPara[i] = tmp;
        }

        //System.out.println(tableauSeq == tableauPara);

        System.out.print("Tableau initial : ") ;
        afficher(tableauSeq, 0, taille -1) ;                         // Affiche le tableau à trier

        //---------------Sequentiel--------------//
        System.out.println("\n----------------SEQ----------------");
        System.out.println("Démarrage du tri rapide sequentiel.") ;
        double débutSeq = System.nanoTime();

        seqTrierRapidement(tableauSeq, 0, taille-1) ;                   // Tri du tableau

        double finSeq = System.nanoTime();
        double duréeSeq = (finSeq - débutSeq) / 1_000_000 ;
        System.out.print("Tableau trié : ") ;
        afficher(tableauSeq, 0, taille -1) ;                         // Affiche le tableau obtenu
        System.out.println("obtenu en " + (int)duréeSeq + " millisecondes.") ;

        //---------------Parallèle--------------//
        System.out.println("\n----------------PARA----------------");
        System.out.println("Démarrage du tri rapide parallèle.") ;
        double débutPara= System.nanoTime();

        paralTrierRapidement(tableauPara, 0, taille-1);

        double finPara = System.nanoTime();
        double duréePara = (finPara - débutPara) / 1_000_000 ;
        System.out.print("Tableau trié : ") ;
        afficher(tableauPara, 0, taille -1) ;                         // Affiche le tableau obtenu
        System.out.println("obtenu en " + (int)duréePara + " millisecondes.") ;

        //System.out.println(Arrays.equals(tableauSeq,tableauPara));

        System.out.println("\n---------------RESULTS---------------");
        int errors = 0;
        for (int i=0 ; i<taille ; i++) {
            if(tableauSeq[i] != tableauPara[i])
                errors++;
        }
        //System.out.println("Done !");

        if(errors == 0)
          System.out.println("Les tableaux sont identiques.");
        else
          System.out.println("There is  "+ errors + " sorting errors.");

        // if(tableauSeq == tableauPara)
        // {
        //   System.out.println("Les tableaux sont identiques");
        // }
        // else
        // {
        //   int errors = 0;
        //   //System.out.println("Start testing !");
        //   for (int i=0 ; i<taille ; i++) {                          // Remplissage aléatoire du tableau
        //       if(tableauSeq[i] != tableauPara[i])
        //           errors++;
        //   }
        //   //System.out.println("Done !");
        //   if(errors > 0)
        //     System.out.println("There is  "+ errors + " sorting errors");
        // }

        System.out.println("La version parallèle est " + (double)((int)((duréeSeq/duréePara)*100))/100 + " fois plus rapide");

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
