/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rgrin.projetqcm.entite;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author richard
 */
@Entity
public class Resultat1Question {
  @Id
  @GeneratedValue
  private long id;
  private Question question;
  
  
}
