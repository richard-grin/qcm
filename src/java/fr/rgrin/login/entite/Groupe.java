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
  private Long id;
  @Column(length=30)
  private String nom;
  @ManyToMany(mappedBy="groupes")
  private List<Login> logins = new ArrayList<>();
  
  public Groupe() {
  }
  
  public Groupe(String nom) {
    this.nom = nom;
  }

  public Long getId() {
    return id;
  }
  
  void addUtilisateur(Login utilisateur) {
    this.logins.add(utilisateur);
  }

}
