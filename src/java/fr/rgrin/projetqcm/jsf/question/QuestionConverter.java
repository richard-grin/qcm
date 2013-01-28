package fr.rgrin.projetqcm.jsf.question;

import fr.rgrin.projetqcm.ejb.QuestionFacade;
import fr.rgrin.projetqcm.entite.Question;
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
@FacesConverter(forClass = Question.class)
public class QuestionConverter implements Converter {

  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value) {
    System.out.println("Converter.getAsObject de QuestionConverter");
    QuestionFacade ejb = lookupQuestionFacadeBean();
    return ejb.find(Long.parseLong(value));
  }

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value) {
    Long id = ((Question) value).getId();
    if (id == null) {
      return null;
    } else {
      return id.toString();
    }
  }

  private QuestionFacade lookupQuestionFacadeBean() {
    try {
      Context c = new InitialContext();
      return (QuestionFacade) c.lookup("java:global/qcm/QuestionFacade!fr.rgrin.projetqcm.ejb.QuestionFacade");
    } catch (NamingException ne) {
      Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
      throw new RuntimeException(ne);
    }
  }
}
