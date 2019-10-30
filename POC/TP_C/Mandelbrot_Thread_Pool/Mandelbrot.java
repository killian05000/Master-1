// -*- coding: utf-8 -*-
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.*;

import java.awt.Color;

public class Mandelbrot {

    public static class DrawLine implements Runnable {
      int lignID;

      public DrawLine(int lignID)
      {
        this.lignID = lignID;
      }

      public void run()
      {
        for (int x = 0; x < 500; x++)
        {
            colorierPixel(x,this.lignID);
        }
      }
    }

    final static int taille = 500 ;   // nombre de pixels par ligne et par colonne
    final static Picture image = new Picture(taille, taille) ;
    // Il y a donc taille*taille pixels blancs ou gris à déterminer
    final static int max = 88_000 ;
    // C'est le nombre maximum d'itérations pour déterminer la couleur d'un pixel

    public static void main(String[] args)  {
        final long start = System.nanoTime() ;

        // Création du réservoir formé de nbThreads esclaves
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (int i = 0; i < taille; i++)
          executor.execute(new DrawLine(i));

        executor.shutdown(); // There is no longer tasks to submit
        System.out.println("All tasks have been submitted");

        // we await the end of the calculations
        try {
            while (! executor.awaitTermination(1, TimeUnit.SECONDS))
                System.out.print("#");
        } catch (InterruptedException e) {e.printStackTrace();}
        System.out.println() ;
        System.out.println("All tasks have been carried out");

        final long end = System.nanoTime() ;
        final long duration = (end - start) / 1_000_000 ;
        System.out.println("Duration = " + (double) duration / 1000 + " s.") ;
        image.show() ;
    }

    // La fonction colorierPixel(i,j) colorie le pixel (i,j) de l'image en gris ou blanc
    public static void colorierPixel(int i, int j) {
        final Color gris = new Color(90, 90, 90) ;
        final Color blanc = new Color(255, 255, 255) ;
        final double xc = -.5 ;
        final double yc = 0 ; // Le point (xc,yc) est le centre de l'image
        final double region = 2 ;
        /*
          La région du plan considérée est un carré de côté égal à 2.
          Elle s'étend donc du point (xc - 1, yc - 1) au point (xc + 1, yc + 1)
          c'est-à-dire du point (-1.5, -1) en bas à gauche au point (0.5, 1) en haut
          à droite
        */
        double a = xc - region/2 + region*i/taille ;
        double b = yc - region/2 + region*j/taille ;
        // Le pixel (i,j) correspond au point (a,b)
        if (mandelbrot(a, b, max)) image.set(i, j, gris) ;
        else image.set(i, j, blanc) ;
    }

    // La fonction mandelbrot(a, b, max) détermine si le point (a,b) est gris
    public static boolean mandelbrot(double a, double b, int max) {
        double x = 0 ;
        double y = 0 ;
        for (int t = 0; t < max; t++) {
            if (x*x + y*y > 4.0) return false ; // Le point (a,b) est blanc
            double nx = x*x - y*y + a ;
            double ny = 2*x*y + b ;
            x = nx ;
            y = ny ;
        }
        return true ; // Le point (a,b) est gris
    }
}


/*
   $ make
   javac *.java
   jar cvmf MANIFEST.MF Mandelbrot.jar *.class
   manifeste ajouté
   ajout : Mandelbrot.class(entrée = 1697) (sortie = 1066)(compression : 37 %)
   ajout : Picture.class(entrée = 5689) (sortie = 3039)(compression : 46 %)
   rm *.class
   $ java -version
   java version "11.0.3" 2019-04-16 LTS
   Java(TM) SE Runtime Environment 18.9 (build 11.0.3+12-LTS)
   Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.3+12-LTS, mixed mode)
   $ java -jar Mandelbrot.jar
   Durée = 35.851 s.
   ^C
*/
