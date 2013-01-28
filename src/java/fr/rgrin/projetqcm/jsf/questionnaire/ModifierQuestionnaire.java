package fr.rgrin.projetqcm.jsf.questionnaire;

import fr.rgrin.projetqcm.ejb.QuestionnaireFacade;
import fr.rgrin.projetqcm.entite.Question;
import fr.rgrin.projetqcm.entite.Questionnaire;
import fr.rgrin.projetqcm.jsf.question.ListeQuestions;
import fr.rgrin.projetqcm.jsf.question.QuestionDataModel;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author richard
 */
@Named
//@RequestScoped
@ConversationScoped
public class ModifierQuestionnaire implements Serializable {

  @Inject
  Conversation conversation;
  @Inject
  ListeQuestions listeQuestions;
  @EJB
  private QuestionnaireFacade questionnaireFacade;
  private Questionnaire questionnaireEnCours;
  private Question[] questionsChoisies;

  public ModifierQuestionnaire() {
    System.out.println("Création nouveau backing bean " + this);
  }

  public Questionnaire getQuestionnaireEnCours() {
    return questionnaireEnCours;
  }

  public void setQuestionnaireEnCours(Questionnaire questionnaireEnCours) {
    System.out.println("+++++++++++++setQuestionnaireEnCours ; questionnaireEnCours=" + questionnaireEnCours);
    this.questionnaireEnCours = questionnaireEnCours;
  }
  
   public void setQuestionsChoisies(Question[] questionsChoisies) {
    this.questionsChoisies = questionsChoisies;
  }

  public Question[] getQuestionsChoisies() {
    return questionsChoisies;
  }
  
  public QuestionDataModel getQuestions() {
    // Sans doute plus simple d'utiliser directement QuestionFacade mais
    // je veux tester l'injection d'un autre backing bean.
    List<Question> questions = listeQuestions.getQuestions();
    return new QuestionDataModel(questions);
  }

  /**
   *
   */
  public void initQuestionnaire() {
    questionsChoisies = questionnaireEnCours.getQuestions().toArray(new Question[0]);
    System.out.println("=*=*= initQuestionnaire; questionsChoisies=" + Arrays.toString(questionsChoisies));
    if (conversation.isTransient()) {
      System.out.println("=======ModifierQuestionnaire.initQuestionnaire; Démarrage conversation " + conversation);
      conversation.begin();
    }
  }

  /**
   * Enregistre le questionnaire en cours dans la base.
   */
  public String enregistrer(boolean resterSurLaPage) {
    questionnaireFacade.create(questionnaireEnCours);
    // Ajout message de confirmation
    int tailleTitre = questionnaireEnCours.getTitre().length();
    int taille = 50;
    if (tailleTitre < taille) {
      taille = tailleTitre;
    }
    addFlashMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
            "Questionnaire \""
            + questionnaireEnCours.getTitre().substring(0, taille)
            + (tailleTitre > taille ? "..." : "")
            + "\" enregistrée", null));
    if (resterSurLaPage) {
      String retour = "ajouterQuestionnaire?faces-redirect=true&amp;idQuestion=" + questionnaireEnCours.getId();
      conversation.end();
      // Pour afficher une nouvelle page vierge
      // (car le bean est de portée View).
      return retour;
    } else {
      conversation.end();
      return "listeQuestionnaires?faces-redirect=true";
    }
  }
  
  public String enregistrer() {
    questionnaireEnCours.setQuestions(Arrays.asList(questionsChoisies));
    questionnaireFacade.edit(questionnaireEnCours);
    String retour = "listeQuestionnaires?faces-redirect=true";
//    String retour = "choixQuestions?faces-redirect=true&amp;idQuestionnaire=" + questionnaireEnCours.getId();
    conversation.end();
    System.out.println("=======ModifierQuestionnaire.enregistrerEtChoixQuestions; fin conversation " + conversation.getId());
    return retour;
  }

  public String enregistrerEtChoixQuestions() {
    questionnaireFacade.create(questionnaireEnCours);
    String retour = "choixQuestions?faces-redirect=true&amp;idQuestionnaire=" + questionnaireEnCours.getId();
    conversation.end();
    System.out.println("=======ModifierQuestionnaire.enregistrerEtChoixQuestions; fin conversation " + conversation.getId());
    return retour;
  }

  private void addFlashMessage(FacesMessage message) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    Flash flash = facesContext.getExternalContext().getFlash();
    flash.setKeepMessages(true);
    flash.setRedirect(true);
    facesContext.addMessage(null, message);
  }
}
