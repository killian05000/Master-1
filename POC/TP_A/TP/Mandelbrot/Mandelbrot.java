// -*- coding: utf-8 -*-

import java.awt.Color;

public class Mandelbrot extends Thread {
    final static int taille = 500 ;   // nombre de pixels par ligne et par colonne
    final static Picture image = new Picture(taille, taille) ;
    // Il y a donc taille*taille pixels blancs ou gris à déterminer
    final static int max = 30_000 ;
    // C'est le nombre maximum d'itérations pour déterminer la couleur d'un pixel
    int start_i;
    int start_j;
    int end_i;
    int end_j;

    public static void main(String[] args) throws InterruptedException  {
        final long début = System.nanoTime() ;

        Mandelbrot[] T = new Mandelbrot[4];
        int i = 0;
        int j = 0;
        for(int x=0; x<4; x++)
        {
          T[x] = new Mandelbrot(i, j, i+taille/2, j+taille/2);
          System.out.println("Thred launched on " + i + "," + j + " / " + (i+taille/2) + "," + (j+taille/2));
          T[x].start();
          i+=taille/2;
          if(i == taille)
          {
            i=0;
            j+=taille/2;
          }
        }

        for(int x=0; x<4; x++)
        {
          T[x].join();
          image.show();
        }

        // for (int i = 0; i < taille; i++) {
        //     for (int j = 0; j < taille; j++) {
        //         colorierPixel(i,j) ;
        //     }
        //     image.show();         // Pour visualiser l'évolution de l'image
        // }

        final long fin = System.nanoTime() ;
        final long durée = (fin - début) / 1_000_000 ;
        System.out.println("Durée = " + (double) durée / 1000 + " s.") ;
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

    public Mandelbrot(int start_i, int start_j, int end_i, int end_j)
    {
      this.start_i = start_i;
      this.start_j = start_j;
      this.end_i = end_i;
      this.end_j = end_j;
    }

    public void run()
    {
      for (int i = start_i; i < end_i; i++) {
          for (int j = start_j; j < end_j; j++) {
              colorierPixel(i,j) ;
          }
          //image.show();         // Pour visualiser l'évolution de l'image
      }
      System.out.println("Thread "+ start_i + "," + start_j + " / " + (start_i+taille/2) + "," + (start_j+taille/2) + " done");
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
