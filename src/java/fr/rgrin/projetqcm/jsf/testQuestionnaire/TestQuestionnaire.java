package fr.rgrin.projetqcm.jsf.testQuestionnaire;

import fr.rgrin.projetqcm.ejb.QuestionnaireFacade;
import fr.rgrin.projetqcm.entite.Question;
import fr.rgrin.projetqcm.entite.Questionnaire;
import fr.rgrin.projetqcm.entite.TestQcm;
import fr.rgrin.projetqcm.evaluation.EvaluateurNoteQcm;
import fr.rgrin.projetqcm.evaluation.EvaluateurSimple;
import fr.rgrin.projetqcm.util.Divers;
import java.io.Serializable;
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
@ConversationScoped
public class TestQuestionnaire implements Serializable {

  @Inject
  private Conversation conversation;
  @EJB // Utile ???
  private QuestionnaireFacade questionnaireFacade;
  
  /**
   * L'entité utilisée pour conserver les réponses aux questions
   * et les informations sur le test en cours.
   */
  private TestQcm testQcm;
  
  /**
   * Questionnaire qui est testé.
   * Il contient aussi les réponses de l'utilisateur car l'entité
   * Reponse les contient.
   */
  private Questionnaire questionnaire;
  private Question questionEnCours;
  private int numeroQuestion = 0;

  public int getNumeroQuestion() {
    return ++numeroQuestion;
  }
  
  // Comme raccourci ??? Les questions du questionnaire.
  private List<Question> questions;
  
  /**
   * True si l'utilisateur a répondu à la question en cours.
   */
  private boolean questionRepondue;
  // TODO: Mettre plutôt un tableau ou une liste de booléens de la taille de la liste des questions
  private boolean[] questionsRepondues;

  public TestQuestionnaire() {
  }

  public Question getQuestionEnCours() {
    return questionEnCours;
  }

  public void setQuestionEnCours(Question questionEnCours) {
    this.questionEnCours = questionEnCours;
  }

  public Questionnaire getQuestionnaire() {
    return questionnaire;
  }

  public void setQuestionnaire(Questionnaire questionnaire) {
    this.questionnaire = questionnaire;
  }
  
  public boolean isBoutonSuivantUtilisable() {
    return numeroQuestion < questionnaire.getQuestions().size() - 1
            && questionRepondue;
  }
  
  public boolean isBoutonPrecedentUtilisable() {
    return numeroQuestion < questionnaire.getQuestions().size() - 1;
  }
  
  public boolean isBoutonFinUtilisable() {
    return numeroQuestion == questionnaire.getQuestions().size() - 1;
  }
  
  public String demarrerQcm(Questionnaire questionnaire) {
    
    System.out.println("=====Questionnaire =" + questionnaire);
    long idQuestionnaire = Long.parseLong(
    FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idQuestionnaire"));
    questionnaire = questionnaireFacade.find(idQuestionnaire);
    System.out.println("=====Questionnaire =" + questionnaire);
    this.questionnaire = questionnaire;
    TestQcm testQcm = new TestQcm(questionnaire);
    conversation.begin();
    return "/testQuestionnaire/test"; // TODO: A COMPLETER !!
  }
  
  public String terminerQcm() {
    if (conversation.isTransient()) {
      conversation.end();
    }
    // Calcul de la note
    EvaluateurNoteQcm evaluateur = new EvaluateurSimple();
    int note = evaluateur.calculNote(questionnaire);
    System.out.println("++++++++Note =" + note);
    String retour = "voirReponse?redirect=true&amp;note=" + note;
    System.out.println("+=+=+retour = " + retour);
    // Cas où on veut enregistrer les réponses de l'utilisateur
    enregistrerReponses(questionnaire);
    return "voirReponse?faces-redirect=true&amp;note=" + note; // TODO: A COMPLETER !!
  }
 
  /**
   * Enregistrer les réponses au questionnaire.
   * @param questionnaire 
   */
  private void enregistrerReponses(Questionnaire questionnaire) {
    /* TODO: utiliser entité TestQuestionnaire qui contient
     * id (id non signicatif)
     * idQuestionnaire (id de l'entité Questionnaire)
     * utilisateur
     * date
     * note (?)
     * Les réponses sont enregistrées avec une entité ReponseTest
     * qui contient la valeur booléenne de la réponse de l'utilisateur,
     * l'id du TestQuestionnaire qui correspond à ce test,
     * et une association n:1 vers l'entité Reponse qui correspond à cette
     * réponse.
     * Pour récupérer les réponses à un test de questionnaire dans la base, 
     * il suffit de parcourir les questions du questionnaire (celui-ci est 
     * récupéré grâce à idQuestionnaire) puis de chercher dans la base 
     * les ReponseTest qui ont l'id des réponses des questions et qui sont
     * associées à ce test de questionnaire.
     */
  }
  
  /**
   * Passer à la question suivante
   * @return 
   */
  public String suivant() {
    enregistrerReponse();
    numeroQuestion++;
    questionEnCours = questionnaire.getQuestions().get(numeroQuestion);
    return null; // reste sur la même page
  }
  
  /**
   * Enregistrer la réponse à la question en cours.
   */
  private void enregistrerReponse() {
    if (conversation.isTransient()) {
      conversation.end();
    }
    // Calcul de la note finale
    
  }

  /**
   *
   */
  public void initQuestionnaire() {
    if (conversation.isTransient()) {
      conversation.begin();
    }
  }
  
  public int nbLignes(String texte, int nbMaxCaracteres) {
    return Divers.nbLignes(texte, nbMaxCaracteres);
  }

  private void addFlashMessage(FacesMessage message) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    Flash flash = facesContext.getExternalContext().getFlash();
    flash.setKeepMessages(true);
    flash.setRedirect(true);
    facesContext.addMessage(null, message);
  }
}
