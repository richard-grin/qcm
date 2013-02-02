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
  private Questionnaire questionnaire;
  
  public int calculNote(Questionnaire questionnaire) {
    System.out.println("=============EvaluateurSimple.calcuNote avec " + questionnaire);
    List<Question> questions = questionnaire.getQuestions();
    // Total des points obtenus
    int somme = 0;
    for (Question question : questions) {
      System.out.println("==== Question " + question);
      somme += pointPourQuestion(question);
    }
    return somme;
  }
  
  private int pointPourQuestion(Question question) {
    List<Reponse> reponses = question.getReponses();
    for (Reponse reponse : reponses) {
      System.out.println("=Reponse " + reponse);
      if (reponse.getReponseUtilisateur() != reponse.isOk()) {
        return 0;
      }
    }
    return 1;
  }
  
}
