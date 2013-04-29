package fr.rgrin.projetqcm.jsf.testQuestionnaire;

import fr.rgrin.projetqcm.ejb.TestQcmFacade;
import fr.rgrin.projetqcm.entite.TestQcm;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 * Backing bean pour voir un test déjà passé, d'après ce qui a été
 * enregistré dans la base de données.
 *
 * @author richard
 */
@Model
public class VoirTest {
  /**
   * Le test affiché.
   */
  private TestQcm testQcm;
  
  @Inject
  private TestQcmFacade testQcmFacade;

  public TestQcm getTestQcm() {
    return testQcm;
  }

  public void setTestQcm(TestQcm testQcm) {
    this.testQcm = testQcm;
  }
  
  public void setIdTestQcm(String id) {
    System.out.println("*******Début de VoirTest.setIdTestQcm - id=" + id);
    Long idTest = Long.parseLong(id);
    this.testQcm = testQcmFacade.findQuestionnaireAvecReponsesUtilisateur(idTest);
    System.out.println("*******Fin de VoirTest.setIdTestQcm - testQcm=" + testQcm);
  }
  
  public String getIdTestQcm() {
    return null;
  }
  
}
