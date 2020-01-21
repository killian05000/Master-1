/*  Calcule la distance cosine entre 2 vecteurs codes dans 2 fichiers  */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <math.h>

#define SIZE_BUFFER	10000

double *lis_vecteur(char *nom, int size)
{
int i;
char ch[SIZE_BUFFER];
FILE *file;
double *t;
t=(double*)malloc(sizeof(double)*(size+1));
if (!(file=fopen(nom,"rt"))) { fprintf(stderr,"Impossible de lire le fichier: %s\n",nom); exit(0); }
for(i=1;(i<=size)&&(fgets(ch,SIZE_BUFFER,file));i++) if (sscanf(ch,"%lf",&(t[i]))!=1) { fprintf(stderr,"mauvais format: %s\n",ch); exit(0); }
fclose(file);
return t;
}

int main(int argc, char **argv)
{
int i,size;
double *t1,*t2,prod,n1,n2;

if (argc<4) { fprintf(stderr,"Syntaxe: %s <taille lexique> <fichier1> <fichier2>\n",argv[0]); exit(0); }
if (sscanf(argv[1],"%d",&size)!=1) { fprintf(stderr,"Mauvais format: %s\n",argv[1]); exit(0); }

t1=lis_vecteur(argv[2],size);
t2=lis_vecteur(argv[3],size);

for(prod=0,i=1;i<=size;i++) prod+=t1[i]*t2[i];
for(n1=0,i=1;i<=size;i++) n1+=t1[i]*t1[i];
for(n2=0,i=1;i<=size;i++) n2+=t2[i]*t2[i];
printf("%lf\n",(double)1-(prod/(sqrt(n1)*sqrt(n2))));

exit(0);
}
 
