package fr.rgrin.projetqcm.jsf.questionnaire;

import fr.rgrin.projetqcm.ejb.QuestionFacade;
import fr.rgrin.projetqcm.ejb.QuestionnaireFacade;
import fr.rgrin.projetqcm.entite.Question;
import fr.rgrin.projetqcm.entite.Questionnaire;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Convertisseur pour une question.
 *
 * @author richard
 */
@FacesConverter(forClass = Questionnaire.class)
public class QuestionnaireConverter implements Converter {

  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value) {
    System.out.println("QuestionnaireConverter.getAsObject");
    QuestionnaireFacade ejb = lookupQuestionnaireFacadeBean();
    return ejb.find(Long.parseLong(value));
  }

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value) {
    System.out.println("QuestionnaireConverter.getAsString");
    Long id = ((Questionnaire) value).getId();
    if (id == null) {
      return null;
    } else {
      return id.toString();
    }
  }

  private QuestionnaireFacade lookupQuestionnaireFacadeBean() {
    try {
      Context c = new InitialContext();
      return (QuestionnaireFacade) c.lookup("java:global/qcm/QuestionnaireFacade!fr.rgrin.projetqcm.ejb.QuestionnaireFacade");
    } catch (NamingException ne) {
      Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
      throw new RuntimeException(ne);
    }
  }
}
