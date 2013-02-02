/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rgrin.projetqcm.entite;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Test qu'un QCM passé par un utilisateur.
 * @author richard
 */
@Entity
public class TestQcm {
  @Id
  @GeneratedValue
  private long id;
  /**
   * Le questionnaire testé.
   */
  @ManyToOne
  private Questionnaire questionnaire;
  
  /**
   * Date du test
   */
  @Temporal(TemporalType.DATE)
  private Date date;
  
  public TestQcm() { }
  
  public TestQcm(Questionnaire questionnaire) {
    this.questionnaire = questionnaire;
    this.date = new Date();
  }
  
}
