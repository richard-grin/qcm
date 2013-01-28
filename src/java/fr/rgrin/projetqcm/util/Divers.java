/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rgrin.projetqcm.util;

/**
 * Classe qui contient des méthodes utilitaires.
 * @author richard
 */
public class Divers {
  
  /**
   * Compte le nombre de ligne d'un texte dont les lignes ont un nombre
   * de caractères limités.
   * Les lignes sont délimitées par '\n'.
   * @param texte
   * @return 
   */
  public static int nbLignes(String texte, int nbMaxCaracteres) {
    String[] lignes = texte.split("\\n");
    int nbLignes = lignes.length;
    for (String ligne : lignes) {
      nbLignes += ligne.length() / nbMaxCaracteres;
    }
    return nbLignes;
  }
  
}
