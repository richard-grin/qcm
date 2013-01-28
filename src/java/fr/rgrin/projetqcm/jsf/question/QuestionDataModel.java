package fr.rgrin.projetqcm.jsf.question;

import fr.rgrin.projetqcm.ejb.QuestionFacade;
import fr.rgrin.projetqcm.entite.Question;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.ListDataModel;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author richard
 */
public class QuestionDataModel extends ListDataModel<Question> implements SelectableDataModel<Question> {
  private QuestionFacade questionFacade = lookupQuestionFacadeBean();
  
  public QuestionDataModel() {
  }
  
  public QuestionDataModel(List<Question> questions) {
    super(questions);
  }

  @Override
  public Object getRowKey(Question question) {
    return question.getId();
  }

  @Override
  public Question getRowData(String ids) {
    Long id = Long.parseLong(ids);
    return questionFacade.find(id);
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
