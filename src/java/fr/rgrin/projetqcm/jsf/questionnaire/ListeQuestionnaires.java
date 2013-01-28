package fr.rgrin.projetqcm.jsf.questionnaire;

import fr.rgrin.projetqcm.ejb.QuestionFacade;
import fr.rgrin.projetqcm.ejb.QuestionnaireFacade;
import fr.rgrin.projetqcm.entite.Question;
import fr.rgrin.projetqcm.entite.Questionnaire;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Backing bean pour la liste des questions.
 * @author richard
 */
@Named
@RequestScoped
public class ListeQuestionnaires {
  @EJB
  private QuestionnaireFacade questionnaireFacade;
  private List<Questionnaire> questionnaires;
  
  

  public List<Questionnaire> getQuestionnaires() {
    if (questionnaires == null) {
      questionnaires = questionnaireFacade.findAll();
    }
    return questionnaires;
  }
  
  public String supprimerQuestionnaire(Questionnaire questionnaire) {
    questionnaireFacade.remove(questionnaire);
    return "listeQuestionnaires?faces-redirect=true";
  }
  
  public String supprimerQuestionnaire() {
    // Récupére le paramètre
    String idQuestion = FacesContext.getCurrentInstance().getExternalContext()
            .getRequestParameterMap().get("idQuestionnaire");
    Questionnaire questionnaire = questionnaireFacade.find(Long.parseLong(idQuestion));
    questionnaireFacade.remove(questionnaire);
    return "listeQuestionnaires?faces-redirect=true";
  }
}
