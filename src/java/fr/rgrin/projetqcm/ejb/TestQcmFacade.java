/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rgrin.projetqcm.ejb;

import com.sun.xml.ws.api.tx.at.Transactional;
import fr.rgrin.projetqcm.entite.Question;
import fr.rgrin.projetqcm.entite.Questionnaire;
import fr.rgrin.projetqcm.entite.Reponse;
import fr.rgrin.projetqcm.entite.ReponseTest;
import fr.rgrin.projetqcm.entite.TestQcm;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * EJB pour gérer les tests de QCM dans la base de données
 *
 * @author richard
 */
@Stateless
public class TestQcmFacade extends AbstractFacade<TestQcm> {

  @PersistenceContext(unitName = "qcmPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public TestQcmFacade() {
    super(TestQcm.class);
  }
  
  /**
   * Retrouve un test de questionnaire avec les réponses de l'utilisateur
   * et aussi les infos sur le questionnaire et les questions (par exemple
   * le titre du questionnaire et les textes des questions et des
   * réponses).
   * Récupère le questionnaire et va ajouter les réponses de l'utilisateur.
   * Suspend la transaction car la méthode modifie le questionnaire avec
   * des instances de Question qui ne sont pas persistantes et on ne veut
   * pas de commit à la fin de la transaction.
   * TODO: Solution avec programmation Java. Chercher aussi une solution qui 
   * utilise une requête JPQL et moins de programmation Java.
   * @param idTestQcm
   * @return 
   */
  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  public TestQcm findQuestionnaireAvecReponsesUtilisateur(Long idTestQcm) {
    TestQcm testQcm = find(idTestQcm);
    // Ajoute les réponses de l'utilisateur dans le questionnaire
    Questionnaire questionnaire = 
            testQcm.getQuestionnaireAvecReponsesUtilisateur();
    System.out.println("++++TestQcmfacade.findQuestionnaireAvecReponsesUtilisateur - le questionnaire retourné par getQuestionnaireAvecReponsesUtilisateur=" + questionnaire);
    System.out.println("++++TestQcmfacade.findQuestionnaireAvecReponsesUtilisateur - testQcm juste avant testQcm.setQuestionnaire(questionnaire) : " + testQcm);
    testQcm.setQuestionnaire(questionnaire);
    System.out.println("++++TestQcmfacade.findQuestionnaireAvecReponsesUtilisateur - testQcm juste après testQcm.setQuestionnaire(questionnaire) : " + testQcm);
    System.out.println("****** testQcm à la fin de findQuestionnaireAvecReponsesUtilisateur=" + testQcm);
    return testQcm;
  }

  /**
   * Test de la requête JPQL pour retrouver un test dans la base de données.
   *
   * @param idQuestionnaire
   */
  public void test(Long idQuestionnaire) {
    Query q = em.createNamedQuery("testQcm.findToutQuestionnaireById");
    q.setParameter("idTest", idQuestionnaire);
    List<Object[]> liste = q.getResultList();
    // La liste est ordonnée par question et, pour chaque question, par
    // réponse.
    // Un élément de la liste contient question, réponse, test, réponse au test
    TestQcm testQcm = (TestQcm) liste.get(0)[2];
    Questionnaire qu = ((TestQcm) liste.get(0)[2]).getQuestionnaire();
    Questionnaire questionnaire = new Questionnaire(qu.getTitre());
    Long idQuestionEnCours = null;
    for (Object[] infos : liste) {
      Question question = (Question) infos[0];
      Reponse reponse = (Reponse) infos[1];
      ReponseTest reponseTest = (ReponseTest) infos[3];
      if (question.getId() != idQuestionEnCours) {
        idQuestionEnCours = question.getId();

      }
    }
  }
}
