package fr.rgrin.projetqcm.jsf.question;

import fr.rgrin.projetqcm.ejb.QuestionFacade;
import fr.rgrin.projetqcm.entite.Question;
import fr.rgrin.projetqcm.entite.Reponse;
import fr.rgrin.projetqcm.util.Divers;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author richard
 */
// TODO: A passer en Viewscoped CDI dès que possible
@ManagedBean
@ViewScoped
public class AjoutQuestion implements Serializable {

  @EJB
  private QuestionFacade questionFacade;
  private Question questionEnCours;
  private List<Reponse> reponses;

  public List<Reponse> getReponses() {
    return reponses;
  }

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
   * Cette méthode est appelée par le listener preRenderView de la page pour
   * ajouter une nouvelle question. A VOIR !!
   */
  public void initQuestion() {
    // Le if pour permettre d’ajouter une question
    // en tapant directement l’URL de la page,
    // et aussi pour utiliser la même page pour
    // ajouter une nouvelle question et pour en modifier une.
//    System.out.println("*******initQuestion ; questionEnCours=" + questionEnCours
//            + "; nombre de réponses=" + questionEnCours.getReponses().size());
    // Si on vient de la liste des questions, questionEnCours a été initialisé
    // par un paramètre de vue.
    System.out.println("*************initQuestion !!!******");
    if (questionEnCours == null) {
      questionEnCours = new Question();
    }
    FacesContext fc = FacesContext.getCurrentInstance();
    boolean ajaxRequest = fc.isPostback();
    // Si ça n'est pas une requête Ajax
    System.out.println("pppppppppppppp isAjaxRequest=" + fc.getPartialViewContext().isAjaxRequest());
    System.out.println("ppppppppppppppppp isPostback = " + fc.isPostback());
    if (! fc.getPartialViewContext().isAjaxRequest()) {
      reponses = questionEnCours.getReponses();
    }
  }

  public void reset() {
//    questionEnCours = new Question();
  }

  public void enregistrerAjax() {
    System.out.println("enregistrerAjax");
    questionEnCours.setReponses(reponses);
    questionFacade.edit(questionEnCours);
    int tailleEnonce = questionEnCours.getEnonce().length();
    int taille = 10;
    if (tailleEnonce < taille) {
      taille = tailleEnonce;
    }
    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
            "Question \""
            + questionEnCours.getEnonce().substring(0, taille)
            + (tailleEnonce > taille ? "..." : "")
            + "\" enregistrée", null);
    FacesContext.getCurrentInstance().addMessage(null, message);
  }

  /**
   * Enregistre la question en cours dans la base.
   */
  public String enregistrer(boolean resterSurLaPage) {
    questionEnCours.setReponses(reponses);
    System.out.println("**enregistrer: taille liste des réponses=" + questionEnCours.getReponses().size());
    System.out.println("****la liste des réponses :" + questionEnCours.getReponses());
    System.out.println("pour question " + questionEnCours);
    if (questionEnCours.getId() == null) {
      questionFacade.create(questionEnCours);
    } else {
      questionFacade.edit(questionEnCours);
    }
//    System.out.println("====id de la question après le edit :" + questionEnCours.getId());
//    questionFacade.flush();
//    System.out.println("====id de la question après le flush et un refresh :" + questionEnCours.getId());
    // Ajout message de confirmation
    int tailleEnonce = questionEnCours.getEnonce().length();
    int taille = 50;
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
      // à tester : reconstituer l'URL de la page avec ExternalContext.
      // J'ai eu un problème : comment faire une redirection vers la même page
      // tout en restant dans la même portée vue (pour ne pas avoir à passer à 
      // nouveau le id).
      return "modifierQuestion?faces-redirect=true&amp;idQuestion=" + questionEnCours.getId();
    } else {
      return "listeQuestions?faces-redirect=true";
    }
  }

  /**
   * Ajouter une réponse vide.
   */
  public void ajouterReponse() {
//    questionEnCours.ajouterReponse(new Reponse());
    reponses.add(new Reponse());
    System.out.println("**ajouterReponse: taille liste des réponses=" + questionEnCours.getReponses().size());
    System.out.println("Les réponses : " + reponses);
//    questionEnCours.setReponses(reponses);
  }

  public void supprimerReponse(Reponse reponse) {
    System.out.println("supprimerReponse: reponse à supprimer=" + reponse + " de la question en cours " + questionEnCours);
    System.out.println("La réponse est dans la liste ?" + reponses.contains(reponse));
//    questionEnCours.getReponses().remove(reponse);
    reponses.remove(reponse);
    System.out.println("****supprimerReponse: la liste des réponses après suppression :"
            + reponses);
//    questionEnCours.setReponses(reponses);
  }
  
  public void testListener(AjaxBehaviorEvent event) {
    System.out.println("***testListener***");
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
