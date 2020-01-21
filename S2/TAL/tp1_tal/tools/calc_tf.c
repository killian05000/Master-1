/*  Lit un texte sous forme de liste de code et construit un vecteur 'bag-of-word' avec les frequences des termes 'TF'  */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>

#define SIZE_BUFFER	10000

int main(int argc, char **argv)
{
int i,size,nb=0,*tfreq;
char ch[SIZE_BUFFER],*pt;

if (argc<=1) { fprintf(stderr,"Syntaxe: %s <taille lexique>\n",argv[0]); exit(0); }
if (sscanf(argv[1],"%d",&size)!=1) { fprintf(stderr,"Mauvais format: %s\n",argv[1]); exit(0); }
tfreq=(int*)malloc(sizeof(int)*(size+1));
for(i=0;i<=size;i++) tfreq[i]=0;
while (fgets(ch,SIZE_BUFFER,stdin)) for(pt=strtok(ch," \t\n\r");pt;pt=strtok(NULL," \t\n\r"))
 if ((sscanf(pt,"%d",&i)==1)&&(i>=0)&&(i<=size)) { tfreq[i]++; nb++; }
for(i=1;i<=size;i++) printf("%lf\n",(double)tfreq[i]/(double)nb);
exit(0);
}
