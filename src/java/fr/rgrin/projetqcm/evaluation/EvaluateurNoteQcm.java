/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rgrin.projetqcm.evaluation;

import fr.rgrin.projetqcm.entite.Questionnaire;

/**
 *
 * @author richard
 */
public interface EvaluateurNoteQcm {
  /**
   * Calcule la note pour un QCM
   * @param questionnaire le questionnaire qui contient les questions et 
   * les réponses de l'utilisateur.
   * @return la note
   */
  public double calculNote(Questionnaire questionnaire);
}
