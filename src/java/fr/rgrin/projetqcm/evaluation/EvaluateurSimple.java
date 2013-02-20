package fr.rgrin.projetqcm.evaluation;

import fr.rgrin.projetqcm.entite.Question;
import fr.rgrin.projetqcm.entite.Questionnaire;
import fr.rgrin.projetqcm.entite.Reponse;
import java.util.List;

/**
 * Evaluateur simple de la note relative à un test de questionnaire.
 * 1 point par question si toutes les réponses sont bonnes.
 * La note à un questionnaire est la somme des notes obtenues aux questions.
 * @author richard
 */
public class EvaluateurSimple  implements EvaluateurNoteQcm {
  /**
   * Le questionnaire testé.
   * Il contient aussi les réponses de l'utilisateur car l'entité
   * Reponse en garde les traces.
   */
//  private Questionnaire questionnaire;
  
  /**
   * Retourne la note du questionnaire sur 20.
   * Chaque question peut rapporter 1 point sur 1 si toutes les réponses
   * sont bien cochées. Sinon, elle rapporte 0 point.
   * @param questionnaire
   * @return 
   */
  @Override
  public double calculNote(Questionnaire questionnaire) {
//    System.out.println("=============EvaluateurSimple.calcuNote avec " + questionnaire);
    List<Question> questions = questionnaire.getQuestions();
//    System.out.println("=============2222questions=" + questions);
    // Total des points obtenus
    int somme = 0;
    for (Question question : questions) {
//      System.out.println("==== Dans calculNote - pour question=" + question);
      somme += pointPourQuestion(question);
    }
    return (double)somme / questions.size() * 20;
  }
  
  private int pointPourQuestion(Question question) {
    List<Reponse> reponses = question.getReponses();
    for (Reponse reponse : reponses) {
//      System.out.println("++++Réponse " + reponse);
//      System.out.println("==Réponse de l'utilisateur=" + reponse.getReponseUtilisateur());
//      System.out.println("==Réponse bonnne ?=" + reponse.isOk());
//      System.out.println("----FIN réponse");
//      System.out.println("=Reponse " + reponse);
      if (reponse.getReponseUtilisateur() != reponse.isOk()) {
        return 0;
      }
    }
    return 1;
  }
  
}
