#define SIZE_BUFFER	10000
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

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

double calc_cosine(int size, double *t1, double *t2)
{
int i;
double prod,n1,n2;
for(prod=0,i=1;i<=size;i++) prod+=t1[i]*t2[i];
for(n1=0,i=1;i<=size;i++) n1+=t1[i]*t1[i];
for(n2=0,i=1;i<=size;i++) n2+=t2[i]*t2[i];
return (double)1-(prod/(sqrt(n1)*sqrt(n2)));
}

int main(int argc, char **argv)
{
int nb,i,size,ibest,iref;
double *t1,*t2,bestcosine,dcos;

if (argc<4) { fprintf(stderr,"Syntaxe: %s <taille lexique> <test> <label-test>  <train1> <label1> <train2> <label2> ....\n",argv[0]); exit(0); }
if (sscanf(argv[1],"%d",&size)!=1) { fprintf(stderr,"Mauvais format: %s\n",argv[1]); exit(0); }

printf("%s - theme reference=%s\n",argv[2],argv[3]);
t1=lis_vecteur(argv[2],size);
iref=3;
for(bestcosine=100,nb=4;nb<argc;nb+=2)
 {
 t2=lis_vecteur(argv[nb],size);
 dcos=calc_cosine(size,t1,t2);
 if (dcos<bestcosine) { bestcosine=dcos; ibest=nb+1; }
 printf("\t- %s : %lf\n",argv[nb+1],dcos);
 free(t2);
 }
printf("Topic choisi: %s => %s\n",argv[ibest],!strcmp(argv[iref],argv[ibest])?"succes":"echec");
exit(0);
}
