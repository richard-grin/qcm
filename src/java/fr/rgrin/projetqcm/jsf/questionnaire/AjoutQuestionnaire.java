package fr.rgrin.projetqcm.jsf.questionnaire;

import fr.rgrin.projetqcm.ejb.QuestionnaireFacade;
import fr.rgrin.projetqcm.entite.Questionnaire;
import java.io.Serializable;
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
public class AjoutQuestionnaire implements Serializable {

  @Inject
  Conversation conversation;
  @EJB
  private QuestionnaireFacade questionnaireFacade;
  private Questionnaire questionnaireEnCours;

  public AjoutQuestionnaire() {
    System.out.println("Création nouveau backing bean " + this);
  }

  public Questionnaire getQuestionnaireEnCours() {
    return questionnaireEnCours;
  }

  public void setQuestionnaireEnCours(Questionnaire questionnaireEnCours) {
    System.out.println("+++++++++++++setQuestionnaireEnCours ; questionnaireEnCours=" + questionnaireEnCours);
    this.questionnaireEnCours = questionnaireEnCours;
  }

  /**
   *
   */
  public void initQuestionnaire() {
    // Le if pour permettre d’ajouter une question
    // en tapant directement l’URL de la page,
    // et aussi pour utiliser la même page pour
    // ajouter une nouvelle question et pour en modifier une.
//    System.out.println("*******initQuestion ; questionEnCours=" + questionEnCours
//            + "; nombre de réponses=" + questionEnCours.getReponses().size());
    // Si on vient de la liste des questions, questionEnCours a été initialisé
    // par un paramètre de vue.
    if (questionnaireEnCours == null) {
      questionnaireEnCours = new Questionnaire();
    }
    if (conversation.isTransient()) {
      conversation.begin();
    }
  }

  /**
   * Enregistre la question en cours dans la base.
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

  public String enregistrerEtChoixQuestions() {
    questionnaireFacade.create(questionnaireEnCours);
    String retour = "choixQuestions?faces-redirect=true&amp;idQuestionnaire=" + questionnaireEnCours.getId();
    conversation.end();
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
