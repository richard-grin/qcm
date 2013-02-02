/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rgrin.projetqcm.entite;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Reponse d'un utilisateur à une question pendant un test d'un questionnnaire.
 * @author richard
 */
@Entity
public class ReponseTest {
  @Id
  @GeneratedValue
  private long id;
  /**
   * Réponse de l'utilisateur (coche du choix, "ok" ou "pas ok").
   */
  private boolean valeurReponse;
  
  /**
   * Le choix que l'utilisateur a coché ou non.
   */
  @ManyToOne
  private Reponse reponse;
  
  public ReponseTest() { }
  
  
}
