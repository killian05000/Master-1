#!/bin/sh

# paramètrage des expériences

export sizelex=10000

echo "Construit les fichiers train pour les 4 themes : CLT, ECO, INT, SPO"
for theme in CLT ECO INT SPO
do
 cat ./corpus_topic/$theme/train/*.txt > ./corpus_topic/$theme/train/all.txt
done

echo "Produit les fichiers .code et les fichiers .tf (pour term frequency) a partir des fichiers .txt"
for fichier in `find ./corpus_topic/ -name "*.txt" -print` 
do
 cat $fichier | ./tools/tokenize ./corpus_topic/lex/lex$sizelex.id -code > $fichier.code
 cat $fichier.code | ./tools/calc_tf $sizelex > $fichier.tf
done

echo "Calcule le fichier IDF"
find ./corpus_topic -name "*.code" -print | ./tools/calc_idf $sizelex > ./corpus_topic/all.idf

echo 'Evaluation: pour la ponderation .tf'
for theme in CLT ECO INT SPO
do
 for fichier in `find ./corpus_topic/$theme/test/ -name "*.tf" -print`
 do
 ./tools/trouve_plus_proche_theme $sizelex $fichier $theme ./corpus_topic/CLT/train/all.txt.tf CLT ./corpus_topic/ECO/train/all.txt.tf ECO ./corpus_topic/INT/train/all.txt.tf INT ./corpus_topic/SPO/train/all.txt.tf SPO
 done
done
