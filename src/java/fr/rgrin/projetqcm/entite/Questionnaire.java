package fr.rgrin.projetqcm.entite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;

/**
 * Un QCM.
 */
@Entity
public class Questionnaire implements Serializable {
  @Id
  @GeneratedValue
  private Long id;
  /**
   * Les questions du QCM.
   */
  @ManyToMany
  @OrderColumn(name="numeroQuestion")
  private List<Question> questions = new ArrayList<>();
  /**
   * Le titre du Questionnaire.
   */
  private String titre;
  /**
   * Le thème du questionnaire. Permet de classer les questionnaires.
   */
  private String theme;

  public Questionnaire() {
  }
  
  /**
   * Crée un questionnaire avec un titre.
   * @param titre titre du questionnaire.
   */
  public Questionnaire(String titre) {
    this.titre = titre;
  }
  
  /**
   * Constructeur de copie.
   * Utilisé pour ranger les réponses de l'utilisateur pour faciliter
   * leurs manipulations et pour éviter les accès concurrents.
   * Est-ce intéressant de l'utiliser ?
   * @param questionnaire 
   */
  public Questionnaire(Questionnaire questionnaire) {
    this.titre = questionnaire.titre;
    this.theme = questionnaire.theme;
    ArrayList<Question> listeQuestions = 
            new ArrayList(questionnaire.questions.size());
    for (Question question : questionnaire.questions) {
      // copie en profondeur de la question
      Question copieQuestion = new Question(question);
      listeQuestions.add(copieQuestion);
    }
    this.questions = listeQuestions;
  }
  
  /**
   * Copie un questionnaire en y ajoutant les réponses données par un
   * utilisateur lors d'un test du questionnaire.
   * Utilisé pour ranger les réponses de l'utilisateur pour faciliter
   * leurs manipulations et pour éviter les accès concurrents.
   * Est-ce intéressant de l'utiliser ?
   * @param questionnaire 
   */
  public Questionnaire(Questionnaire questionnaire, 
          Map<Long,ReponseTest> mapReponsesUtilisateur) {
    this.titre = questionnaire.titre;
    this.theme = questionnaire.theme;
    ArrayList<Question> listeQuestions = 
            new ArrayList(questionnaire.questions.size());
    for (Question question : questionnaire.questions) {
      // copie en profondeur de la question
      Question copieQuestion = 
              new Question(question, mapReponsesUtilisateur);
      listeQuestions.add(copieQuestion);
    }
    this.questions = listeQuestions;
  }

  public Long getId() {
    return id;
  }
  
  public String getTitre() {
    return titre;
  }

  public void setTitre(String titre) {
    this.titre = titre;
  }

  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  /**
   * Ajoute une question au questionnaire.
   * Gère les 2 bouts de l'association.
   * @param question question qui est ajoutée.
   */
  public void ajouterQuestion(Question question) {
    this.questions.add(question);
//    question.mettreDansQuestionnaire(this);
  }

  @Override
  public String toString() {
    return "Questionnaire{" + super.toString() 
            + ", questions=" + questions 
            + ", titre=" + titre + ", theme=" + theme + '}';
  }

  public List<Question> getQuestions() {
    return questions;
  }

  public void setQuestions(List<Question> questions) {
    this.questions = questions;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 83 * hash + Objects.hashCode(this.id);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Questionnaire other = (Questionnaire) obj;
    if (this.id != other.id) {
      return false;
    }
    return true;
  }

}
