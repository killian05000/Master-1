bool lireFichier(char *monFichierString, vector<Initial> &initials, vector<Final> &finals)
{
  ifstream monFichier;
  monFichier.open(monFichierString);

  string ligne;
  getline(monFichier,ligne);

  if(ligne == "initials")
  {
    while (getline(monFichier,ligne))
    {
      if(ligne == "finals")
        break;

      string largeur = ligne.substr(0, ligne.find(" "));
      string nombreR = ligne.substr(ligne.find(" ")+1, ligne.length());

      Initial iniTemp;
      iniTemp.largeur = std::stoi(largeur);
      iniTemp.coef = std::stod(nombreR);

      initials.push_back(iniTemp);
    }
    while (getline(monFichier,ligne))
    {
      string largeur = ligne.substr(0, ligne.find(" "));
      string nombreR = ligne.substr(ligne.find(" ")+1, ligne.length());

      Final finTemp;
      finTemp.largeur = std::stoi(largeur);
      finTemp.nbRoul = std::stoi(nombreR);

      finals.push_back(finTemp);
    }
  }

  monFichier.close();

  if(finals.empty())
    return false;

  return true;
}
