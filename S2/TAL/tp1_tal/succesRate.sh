#!/bin/bash

succes=$(cat output.txt | grep -o 'succes' | wc -l)
size=$(cat output.txt | grep -o 'theme reference' | wc -l)
succesRate=$(echo "$succes/$size" | bc -l)

echo "Taux de bonne classification : $succes / $size : $succesRate"
