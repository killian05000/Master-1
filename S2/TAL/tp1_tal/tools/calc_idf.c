/*  Lit une liste de fichiers documents sous forme de liste de code et construit un vecteur termes 'IDF'  */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <math.h>

#define SIZE_BUFFER	10000

int main(int argc, char **argv)
{
int i,size,nbdoc,*tfreq,*tfreqdoc;
char ch[SIZE_BUFFER],*pt;
FILE *file;

if (argc<=1) { fprintf(stderr,"Syntaxe: %s <taille lexique>\n",argv[0]); exit(0); }
if (sscanf(argv[1],"%d",&size)!=1) { fprintf(stderr,"Mauvais format: %s\n",argv[1]); exit(0); }

tfreq=(int*)malloc(sizeof(int)*(size+1));
tfreqdoc=(int*)malloc(sizeof(int)*(size+1));
for(i=0;i<=size;i++) tfreqdoc[i]=1;

for(nbdoc=0;fgets(ch,SIZE_BUFFER,stdin);nbdoc++)
 {
 strtok(ch," \t\n\r");
 if (!(file=fopen(ch,"rt"))) { fprintf(stderr,"Impossible de lire le fichier: %s\n",ch); exit(0); }
 for(i=0;i<=size;i++) tfreq[i]=0;
 while (fgets(ch,SIZE_BUFFER,file)) for(pt=strtok(ch," \t\n\r");pt;pt=strtok(NULL," \t\n\r"))
  if ((sscanf(pt,"%d",&i)==1)&&(i>=0)&&(i<=size)) tfreq[i]++;
 for(i=1;i<=size;i++) if (tfreq[i]) tfreqdoc[i]++;
 fclose(file);
 }

for(i=1;i<=size;i++) printf("%lf\n",log10((double)1+(double)nbdoc/(double)tfreqdoc[i]));
exit(0);
}
