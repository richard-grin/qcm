package fr.rgrin.login.entite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author richard
 */
@NamedQueries({
  @NamedQuery(name = "Login.findByEmail",
          query = "SELECT l FROM Login l where l.email = :email"),
  @NamedQuery(name = "Login.getAll", query = "select l from Login l")
})
@Entity
public class Login implements Serializable {

  @Id
  private String login;
  @Column(name = "MOT_DE_PASSE")
  private String motDePasse;
  private String email;
  @ManyToMany
  private List<Groupe> groupes = new ArrayList<>();

  public Login(String login, String motDePasse) {
    this.login = login;
    this.motDePasse = motDePasse;
  }

  public Login() {
  }

  public String getLogin() {
    return login;
  }

  public void addGroupe(Groupe groupe) {
    this.groupes.add(groupe);
    groupe.addUtilisateur(this);
  }
}
