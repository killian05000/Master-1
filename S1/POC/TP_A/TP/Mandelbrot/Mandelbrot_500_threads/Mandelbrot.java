// -*- coding: utf-8 -*-

import java.awt.Color;
import java.util.*;

public class Mandelbrot extends Thread {
    final static int size = 500 ;   // nombre de pixels par ligne et par colonne
    final static Picture image = new Picture(size, size) ;
    // Il y a donc taille*taille pixels blancs ou gris à déterminer
    final static int max = 88_000 ;
    // C'est le nombre maximum d'itérations pour déterminer la couleur d'un pixel
    int lignID;

    public static void main(String[] args) throws InterruptedException  {
        final long début = System.nanoTime() ;

        Mandelbrot[] T = new Mandelbrot[size]; //size = 500
        for(int i=0; i<size; i++)
        {
          T[i] = new Mandelbrot(i);
          T[i].start();
        }

        for(int x=0; x<T.length; x++)
        {
          T[x].join();
        }

        final long durée = (System.nanoTime() - début) / 1_000_000 ;
        System.out.println("Program final execution time = " + (double) durée / 1000 + " s.") ;
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
        double a = xc - region/2 + region*i/size ;
        double b = yc - region/2 + region*j/size ;
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

    public Mandelbrot(int lignID)
    {
      this.lignID = lignID;
    }

    public void run()
    {
      final long début = System.nanoTime() ;

      for (int x = 0; x < size; x++)
      {
          colorierPixel(x,this.lignID);
      }
      synchronized(image){image.show();}         // Pour visualiser l'évolution de l'image

      final long durée = (System.nanoTime() - début) / 1_000_000 ;
      //System.out.println("Line "+ this.lignID + " execution time = " + (double) durée / 1000 + " s." );
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
   $ java -jar Mandelbrot.jar
   Durée = 35.851 s.
   ^C
*/
