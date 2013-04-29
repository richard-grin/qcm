package fr.rgrin.projetqcm.jsf.testQuestionnaire;

import fr.rgrin.projetqcm.ejb.QuestionnaireFacade;
import fr.rgrin.projetqcm.ejb.TestQcmFacade;
import fr.rgrin.projetqcm.entite.Question;
import fr.rgrin.projetqcm.entite.Questionnaire;
import fr.rgrin.projetqcm.entite.Reponse;
import fr.rgrin.projetqcm.entite.ReponseTest;
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
 * Backing bean pour faire passer un test. La conversation commence au démarrage
 * du test du questionnaire et se termine après l'affichage de la note et de la
 * comparaison des réponses de l'utilisateur avec les bonnes réponses qu'il
 * fallait choisir. Remarque : les réponses aux questions conservent les
 * réponses de l'utilisateur (elles sont transient pour ne pas les enregistrer
 * dans la table des réponses sous cette forme car les réponses des utilisateurs
 * sont enregistrées dans la table REPONSETEST et pas dans la table REPONSE).
 *
 * @author richard
 */
@Named
@ConversationScoped
public class TestQuestionnaire implements Serializable {

  @Inject
  private Conversation conversation;
  @EJB
  private TestQcmFacade testQcmFacade;
  @EJB
  private QuestionnaireFacade questionnaireFacade;
  /**
   * L'entité utilisée pour conserver les réponses aux questions et les
   * informations sur le test en cours.
   */
  private TestQcm testQcm;

  public TestQcm getTestQcm() {
    return this.testQcm;
  }

//  public void setTestQcm(TestQcm testQcm) {
//    this.testQcm = testQcm;
//  }
  /**
   * Questionnaire qui est testé. Il contient aussi les réponses de
   * l'utilisateur car l'entité Reponse les contient.
   */
//  private Questionnaire questionnaire;
//  private Question questionEnCours;
//  private int numeroQuestion = 0;
//  public int getNumeroQuestion() {
//    return ++numeroQuestion;
//  }
  // Comme raccourci ??? Les questions du questionnaire.
//  private List<Question> questions;
  /**
   * True si l'utilisateur a répondu à la question en cours.
   */
//  private boolean questionRepondue;
//  // TODO: Mettre plutôt un tableau ou une liste de booléens de la taille de la liste des questions
//  private boolean[] questionsRepondues;
  public TestQuestionnaire() {
  }

//  public Question getQuestionEnCours() {
//    return questionEnCours;
//  }
//
//  public void setQuestionEnCours(Question questionEnCours) {
//    this.questionEnCours = questionEnCours;
//  }
//
//  public Questionnaire getQuestionnaire() {
//    return questionnaire;
//  }
//
//  public void setQuestionnaire(Questionnaire questionnaire) {
//    this.questionnaire = questionnaire;
//  }
//
//  public boolean isBoutonSuivantUtilisable() {
//    return numeroQuestion < questionnaire.getQuestions().size() - 1
//            && questionRepondue;
//  }
//
//  public boolean isBoutonPrecedentUtilisable() {
//    return numeroQuestion < questionnaire.getQuestions().size() - 1;
//  }
//
//  public boolean isBoutonFinUtilisable() {
//    return numeroQuestion == questionnaire.getQuestions().size() - 1;
//  }
  /**
   * Exécuté au démarrage du test.
   */
  public void demarrerQcm() {
//    System.out.println("Backing bean " + this);
//    System.out.println("=====Questionnaire =" + questionnaire);
    long idQuestionnaire = Long.parseLong(
            FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idQuestionnaire"));
    Questionnaire questionnaire = questionnaireFacade.find(idQuestionnaire);
//    System.out.println("=====Dans demarrerQcm - Questionnaire =" + questionnaire);
//    System.out.println("backing bean =" + this);
//    System.out.println("=+=+=+ FIN du questionnaire");
////    this.questionnaire = questionnaire;
//    System.out.println("fin de demarrerQcm, création de TestQcm");
    this.testQcm = new TestQcm(questionnaire);
//    System.out.println("this.testQcm=" + this.testQcm);
//    System.out.println("!!!conversation transient ? " + conversation.isTransient());
    if (conversation.isTransient()) {
      conversation.begin();
    }
//    return "/testQuestionnaire/test"; // TODO: A COMPLETER !!
  }

  public String terminerQcm() {
//    System.out.println("Backing bean " + this);
    Questionnaire questionnaire = this.testQcm.getQuestionnaire();
    // ============= Pour debug !!
    // Affiche les réponses utilisateur des questions
//    List<Question> questions = questionnaire.getQuestions();
////    System.out.println("============= Début de terminerQcm - Réponses utilisateur :");
//    for (Question question : questions) {
//      List<Reponse> reponses = question.getReponses();
//      for (Reponse reponse : reponses) {
////        System.out.println("++++Réponse " + reponse);
////        System.out.println("==Réponse de l'utilisateur id " + reponse.getId() + "=" + reponse.getReponseUtilisateur());
//      }
//    }
    // ============ FIN pour debug

//    PAS LE BON QUESTIONNAIRE !!!!!!
//    System.out.println("backing bean =" + this);
//    System.out.println("=====Dans terminerQcm - Questionnaire =" + questionnaire);
//    System.out.println("=+=+=+ FIN du questionnaire");
//    System.out.println("Question 1=" + questionnaire.getQuestions().get(0));

    // Calcul de la note
    EvaluateurNoteQcm evaluateur = new EvaluateurSimple();
    double note = evaluateur.calculNote(questionnaire);
    System.out.println("++++++++Note =" + note);
//    String retour = "voirReponse?redirect=true&amp;note=" + note;
//    System.out.println("+=+=+retour = " + retour);
//    TestQcm testQcm = new TestQcm(questionnaire);
    testQcm.setNote(note);
    // Cas où on veut enregistrer les réponses de l'utilisateur
    enregistrerReponses(testQcm);

    boolean voirBonnesReponses = true;
    String retour;
    if (voirBonnesReponses) {
      // Pas besoin de donner l'id du test car on garde la même conversation
      // et le même backing bean.
      // On terminera la conversation après avoir affiché les bonnes réponses (comment ??).
      // Un workflow de JSF 2.2 conviendrait sans doute bien ici. A VOIR !!
      retour = "voirBonnesReponses?faces-redirect=true";
    } else {
      retour = "voirReponse?faces-redirect=true&amp;note=" + note;
      if (!conversation.isTransient()) {
        conversation.end();
      }
    }

    // fait afficher la page qui donne les réponses de l'utilisateur et 
    // les bonnes réponses.
    return retour;
  }

  /**
   * Enregistrer le déroulement du questionnaire : - la note obtenue, - les
   * réponses au questionnaire.
   *
   * @param questionnaire
   */
  private void enregistrerReponses(TestQcm testQcm) {
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
    System.out.println("Enregistrement du test " + testQcm);
    Questionnaire questionnaire = testQcm.getQuestionnaire();
    List<Question> questions = questionnaire.getQuestions();
    for (Question question : questions) {
      List<Reponse> reponses = question.getReponses();
      for (Reponse reponse : reponses) {
        ReponseTest reponseTest =
                new ReponseTest(testQcm, reponse, reponse.getReponseUtilisateur());
      }
    }
    testQcmFacade.create(testQcm);
  }

  public String classeCss(Reponse reponse) {
    System.out.println("============================= classeCss!!!!!!!!!!!");
    if (reponse.getReponseUtilisateur() != reponse.isOk()) {
      System.out.println("REPONSE FAUSSE");
      return "reponse-fausse";
    } else {
      System.out.println("REPONSE BONNE");
      return "reponse-bonne";
    }
  }

  /**
   * Passer à la question suivante
   *
   * @return
   */
//  public String suivant() {
//    enregistrerReponse();
//    numeroQuestion++;
//    questionEnCours = questionnaire.getQuestions().get(numeroQuestion);
//    return null; // reste sur la même page
//  }
  /**
   * Enregistrer la réponse à la question en cours.
   */
//  private void enregistrerReponse() {
//    if (conversation.isTransient()) {
//      conversation.end();
//    }
//    // Calcul de la note finale
//
//  }
  /**
   *
   */
//  public void initQuestionnaire() {
//    if (conversation.isTransient()) {
//      conversation.begin();
//    }
//  }
  public int nbLignes(String texte, int nbMaxCaracteres) {
    return Divers.nbLignes(texte, nbMaxCaracteres);
  }
//  private void addFlashMessage(FacesMessage message) {
//    FacesContext facesContext = FacesContext.getCurrentInstance();
//    Flash flash = facesContext.getExternalContext().getFlash();
//    flash.setKeepMessages(true);
//    flash.setRedirect(true);
//    facesContext.addMessage(null, message);
//  }
}
