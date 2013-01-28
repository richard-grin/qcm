package fr.rgrin.projetqcm.jsf.questionnaire;

import fr.rgrin.projetqcm.ejb.QuestionnaireFacade;
import fr.rgrin.projetqcm.entite.Question;
import fr.rgrin.projetqcm.entite.Questionnaire;
import fr.rgrin.projetqcm.jsf.question.ListeQuestions;
import fr.rgrin.projetqcm.jsf.question.QuestionDataModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Backing bean pour le choix des questions dans un questionnaire :
 * <ol>
 * <li> Choix des questions.</li>
 * <li> Ordre des questions.</li>
 * </ol>
 * @author richard
 */
@Named
@ConversationScoped
public class ChoixQuestions implements Serializable {
  @EJB
  private QuestionnaireFacade questionnaireFacade;
  @Inject
  Conversation conversation;
  @Inject
  ListeQuestions listeQuestions;
  
  /**
   * Questionnaire dont on choisit les questions.
   */
  private Questionnaire questionnaireEnCours;
          
  private Question[] questionsChoisies;
  
  public QuestionDataModel getQuestions() {
    // Sans doute plus simple d'utiliser directement QuestionFacade mais
    // je veux tester l'injection d'un autre backing bean.
    List<Question> questions = listeQuestions.getQuestions();
    return new QuestionDataModel(questions);
  }

  public Questionnaire getQuestionnaireEnCours() {
    return questionnaireEnCours;
  }

  public void setQuestionnaireEnCours(Questionnaire questionnaireEnCours) {
    this.questionnaireEnCours = questionnaireEnCours;
  }

  public void setQuestionsChoisies(Question[] questionsChoisies) {
    this.questionsChoisies = questionsChoisies;
  }

  public Question[] getQuestionsChoisies() {
    return questionsChoisies;
  }
  
  public void initQuestionnaire() {
    System.out.println("===ChoixQuestions.initQuestionnaire; Questionnaire en cours : " + questionnaireEnCours);
    questionsChoisies = questionnaireEnCours.getQuestions().toArray(new Question[0]);
    if (conversation.isTransient()) {
      conversation.begin();
      System.out.println("=======ChoixQuestions.initQuestionnaire; Démarrage de la conversation");
    }
  }
  
  public void enregistrer() {
    System.out.println("+*=*=*=Questionnaire en cours=" + questionnaireEnCours);
    questionnaireEnCours.setQuestions(Arrays.asList(questionsChoisies));
    questionnaireFacade.edit(questionnaireEnCours);
    if (! conversation.isTransient()) {
      conversation.end();
      System.out.println("=======ChoixQuestions.enregistrer; Fin de la conversation");
    }
  }
}
