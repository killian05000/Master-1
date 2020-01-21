TP1 - Tokenisation, sacs de mots, TF-IDF, similarité cosinus
  M1 - Introduction au Traitement Automatique des Langues
         Frederic Bechet & Abdellah Fourtassi
                   ----------------

Vous trouverez dans ce répertoire les fichiers suivants :

- corpus_topic : répertoire contenant les documents textuels classés par thèmes

- corpus_topic/CLT : répertoire des données du thème CULTURE (CLT)
- corpus_topic/CLT/test : répertoire des données de test du thème CULTURE (CLT)
- corpus_topic/CLT/test/document.CLT.1.txt : exemple de document de test du thème CULTURE (CLT)
- corpus_topic/CLT/train : répertoire des données d'apprentissage du thème CULTURE (CLT)
- corpus_topic/CLT/train/document.CLT.100.txt : exemple de document d'apprentissage du thème CULTURE (CLT)

- corpus_topic/ECO : répertoire des données du thème ECONOMIE (ECO)
- corpus_topic/ECO/test : répertoire des données de test du thème ECONOMIE (ECO)
- corpus_topic/ECO/test/document.ECO.1.txt : exemple de document de test du thème ECONOMIE (ECO)
- corpus_topic/ECO/train : répertoire des données d'apprentissage du thème ECONOMIE (ECO)
- corpus_topic/ECO/train/document.ECO.100.txt : exemple de document d'apprentissage du thème ECONOMIE (ECO)

- corpus_topic/INT : répertoire des données du thème INTERNATIONAL (INT)
- corpus_topic/INT/test : répertoire des données de test du thème INTERNATIONAL (INT)
- corpus_topic/INT/test/document.INT.1.txt : exemple de document de test du thème INTERNATIONAL (INT)
- corpus_topic/INT/train : répertoire des données d'apprentissage du thème INTERNATIONAL (INT)
- corpus_topic/INT/train/document.INT.100.txt : exemple de document d'apprentissage du thème INTERNATIONAL (INT)

- corpus_topic/SPO : répertoire des données du thème SPORT (SPO)
- corpus_topic/SPO/test : répertoire des données de test du thème SPORT (SPO)
- corpus_topic/SPO/test/document.SPO.1.txt : exemple de document de test du thème SPORT (SPO)
- corpus_topic/SPO/train : répertoire des données d'apprentissage du thème SPORT (SPO)
- corpus_topic/SPO/train/document.SPO.100.txt : exemple de document d'apprentissage du thème SPORT (SPO)

- corpus_topic/lex : répertoire des lexiques avec code (identifiant des tokens)
- corpus_topic/lex/lex100.id : lexique contenant les 100 tokens les plus fréquents
- corpus_topic/lex/lex1000.id : lexique contenant les 1000 tokens les plus fréquents
- corpus_topic/lex/lex10000.id : lexique contenant les 10000 tokens les plus fréquents

- tools : répertoire contenant des programme (en C) pour vous aider à faire le TP
- tools/tokenize.c : permet de prendre un texte en entrée et de tokeniser par rapport à un lexique donné en paramètre,
                     en traduisant éventuellement en code
- tools/code2mot.c : permet de passer d'une séquence de code à une séquence de mots
                     (par rapport à un lexique donné en paramètre)
- tools/calc_cosine.c : calcul la distance cosinus entre 2 vecteurs représentés par des fichiers textes
                        contenant 1 valeur (double) par ligne; la taille du vecteur est en paramètre
- tools/Makefile : permet de compiler les programmes précédents
- exemple/vecteur_d1.tfidf : exemple de vecteur document avec une valeur de pondération par ligne pour une taille de 1000 mots

----------------------------------------------------------------

Exemples d'utilisation :

Tokenisation d'un fichier :
cat corpus_topic/CLT/test/document.CLT.1.txt | ./tools/tokenize ./corpus_topic/lex/lex1000.id

Tokenisation et traduction en code d'un fichier : 
cat corpus_topic/CLT/test/document.CLT.1.txt | ./tools/tokenize ./corpus_topic/lex/lex1000.id -code

Production d'un corpus d'apprentissage tokenisé et traduit en code pour un thème :
cat corpus_topic/CLT/train/*.txt | ./tools/tokenize ./corpus_topic/lex/lex1000.id -code > corpus_topic/CLT/train/corpus_apprentissage.CLT.code

Exemple de calcul de la distance cosine entre 2 vecteurs de pondération TF-IDF de taille 1000 :
./tools/calc_cosine 1000 exemple/vecteur_d1.tfidf exemple/vecteur_d2.tfidf 
  
