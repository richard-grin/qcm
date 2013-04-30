package fr.rgrin.projetqcm.jsf;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author richard
 */
@Named
@RequestScoped
public class Connexion implements Serializable {
  
  public String deconnect() {
    System.out.println("********DECONNEXION !!!!");
    try {
      ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).logout();
    } catch (ServletException ex) {
      Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "/index";
  }
  
}
