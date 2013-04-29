/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rgrin.projetqcm.entite;

import javax.persistence.Entity;

/**
 *
 * @author richard
 */
@Entity
public class Enseignant extends Utilisateur {
  private String departement;

  public String getDepartement() {
    return departement;
  }

  public void setDepartement(String departement) {
    this.departement = departement;
  }
  
}
