package fr.rgrin.login.entite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author richard
 */
@Entity
public class Groupe implements Serializable {
  @Id
  @GeneratedValue
  @Column(name="ID_GROUPE")
  private Long idGroupe;
  @Column(name="NOM_GROUPE")
  private String nomGroupe;
  @ManyToMany(mappedBy="groupes")
  private List<Login> logins = new ArrayList<>();
  
  public Groupe() {
  }
  
  public Groupe(String nom) {
    this.nomGroupe = nom;
  }

  public Long getIdGroupe() {
    return idGroupe;
  }
  
  void addUtilisateur(Login utilisateur) {
    this.logins.add(utilisateur);
  }

}
