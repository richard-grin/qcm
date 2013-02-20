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
  /**
   * Le test pendant lequel la réponse a été choisie par l'utilisateur.
   */
  @ManyToOne
  private TestQcm testQcm;
  
  public ReponseTest() { }
  
  public ReponseTest(TestQcm testQcm, Reponse reponse, boolean valeurReponse) {
    this.valeurReponse = valeurReponse;
    this.reponse = reponse;
    this.testQcm = testQcm;
    // L'autre bout de l'association
    this.testQcm.ajouterReponseTest(this);
  }
 
}
