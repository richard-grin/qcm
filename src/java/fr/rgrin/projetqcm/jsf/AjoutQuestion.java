package fr.rgrin.projetqcm.jsf;

import fr.rgrin.projetqcm.ejb.QuestionFacade;
import fr.rgrin.projetqcm.entite.Question;
import fr.rgrin.projetqcm.entite.Reponse;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

/**
 *
 * @author richard
 */
@ManagedBean
@ViewScoped
public class AjoutQuestion implements Serializable {

  @EJB
  private QuestionFacade questionFacade;
  private Question questionEnCours;
  
  public AjoutQuestion() {
    System.out.println("Création nouveau backing bean " + this);
  }

  public Question getQuestionEnCours() {
    return questionEnCours;
  }

  public void setQuestionEnCours(Question questionEnCours) {
    System.out.println("+++++++++++++setQuestionEnCours ; questionEnCours=" + questionEnCours);
    this.questionEnCours = questionEnCours;
  }

  /**
   * 
   */
  public void initQuestion() {
    // Le if pour permettre d’ajouter une question
    // en tapant directement l’URL de la page,
    // et aussi pour utiliser la même page pour
    // ajouter une nouvelle question et pour en modifier une.
//    System.out.println("*******initQuestion ; questionEnCours=" + questionEnCours
//            + "; nombre de réponses=" + questionEnCours.getReponses().size());
    if (questionEnCours == null) {
      questionEnCours = new Question();
    }
  }

  public void reset() {
//    questionEnCours = new Question();
  }

  /**
   * Enregistre la question en cours dans la base.
   */
  public String enregistrer(boolean resterSurLaPage) {
    System.out.println("**enregistrer: taille liste des réponses=" + questionEnCours.getReponses().size());
    System.out.println("****la liste des réponses :" + questionEnCours.getReponses());
    System.out.println("pour question " + questionEnCours);
    questionFacade.edit(questionEnCours);
    // Ajout message de confirmation
    int tailleEnonce = questionEnCours.getEnonce().length();
    int taille = 10;
    if (tailleEnonce < taille) {
      taille = tailleEnonce;
    }
    addFlashMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
            "Question \"" 
            + questionEnCours.getEnonce().substring(0, taille)
            + (tailleEnonce > taille ? "..." : "")
            + "\" enregistrée", null));
    if (resterSurLaPage) {
      // Pour afficher une nouvelle page vierge
      // (car le bean est de portée View).
      return "creerQuestion?faces-redirect=true";
    } else {
      return "listeQuestions?faces-redirect=true";
    }
  }
  
  /**
   * Ajouter une réponse vide.
   */
  public void ajouterReponse() {
    questionEnCours.ajouterReponse(new Reponse());
//    System.out.println("**ajouterReponse: taille liste des réponses=" + questionEnCours.getReponses().size());
  }
  
  public void supprimerReponse(Reponse reponse) {
    System.out.println("supprimerReponse: reponse à supprimer=" + reponse);
    questionEnCours.getReponses().remove(reponse);
    System.out.println("****supprimerReponse: la liste des réponses après suppression :" 
            + questionEnCours.getReponses());
  }

  private void addFlashMessage(FacesMessage message) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    Flash flash = facesContext.getExternalContext().getFlash();
    flash.setKeepMessages(true);
    flash.setRedirect(true);
    facesContext.addMessage(null, message);
  }
}
