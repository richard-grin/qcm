package fr.rgrin.projetqcm.entite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Test d'un QCM passé par un utilisateur.
 * TODO: ajouter l'utilisateur qui a passé le test quand le login
 * aura été implanté.
 * @author richard
 */
@Entity
@NamedQuery(name="testQcm.findToutQuestionnaireById",
        query="select q, r, t, rt "
        + "from ReponseTest rt join rt.testQcm t join t.questionnaire.questions q join q.reponses r "
        + "where r.id = rt.reponse.id and rt.testQcm.id = :idTest "
        + "  and t.id = :idTest "
        + "order by index(q), index(r)")
public class TestQcm implements Serializable {
  @Id
  @GeneratedValue
  private long id;
  
  /**
   * La note obtenue, avec 2 décimales.
   */
  @Column(scale=2)
  private double note;
  /**
   * Le questionnaire testé.
   */
  @ManyToOne
  private Questionnaire questionnaire;

  public Questionnaire getQuestionnaire() {
    return this.questionnaire;
  }

  public void setQuestionnaire(Questionnaire questionnaire) {
    System.out.println("***** TestQcm.setQuestionnaire - met ce questionnaire dans testQcm :" + questionnaire);
    this.questionnaire = questionnaire;
    System.out.println("****** Fin de TestQcm.setQuestionnaire - testQcm =" + this);
  }
  
  @OneToMany(mappedBy="testQcm", cascade=CascadeType.ALL, orphanRemoval = true)
  private List<ReponseTest> reponsesTests = new ArrayList<>();
  
  /**
   * Date du test
   */
  @Temporal(TemporalType.DATE)
  private Date date;
  
  @Temporal(TemporalType.TIME)
  private Date heure;
  
  public TestQcm() { }
  
  public TestQcm(Questionnaire questionnaire) {
    this.questionnaire = questionnaire;
    this.date = new Date();
    this.heure = date;
    // POUR DEBUG =======
//    List<Question> questions = questionnaire.getQuestions();
//    System.out.println("============= Constructeur de TestQcm - Réponses utilisateur :");
//    for (Question question : questions) {
//      List<Reponse> reponses = question.getReponses();
//      for (Reponse reponse : reponses) {
////        System.out.println("++++Réponse " + reponse);
//        System.out.println("==Réponse de l'utilisateur id " + reponse.getId() + "=" + reponse.getReponseUtilisateur());
//      }
//    }
    // FIN POUR DEBUG ============
  }
  
  public void ajouterReponseTest(ReponseTest reponseTest) {
    this.reponsesTests.add(reponseTest);
  }

  public void setNote(double note) {
    this.note = note;
  }
  
  public double getNote() {
    return note;
  }

  public long getId() {
    return id;
  }

  public Date getDate() {
    return date;
  }

  public Date getHeure() {
    return heure;
  }

  public List<ReponseTest> getReponsesTest() {
    return reponsesTests;
  }
  
  /**
   * @return retourne une copie du questionnaire associé à ce test,
   * avec les réponses de l'utilisateur mises
   * au bon endroit (dans la réponse associée).
   */
  public Questionnaire getQuestionnaireAvecReponsesUtilisateur() {
    Questionnaire questionnaireSansReponseUtilisateur = this.questionnaire;
    // On met les réponses de l'utilisateur dans une map avec les id
    // des Reponse comme clé.
    List<ReponseTest> listeReponses = this.getReponsesTest();
    Map<Long,ReponseTest> mapReponsesUtilisateur = new HashMap<>();
    for (ReponseTest reponseUtilisateur : listeReponses) {
      mapReponsesUtilisateur.put(reponseUtilisateur.getReponse().getId(), reponseUtilisateur);
    }
    System.out.println("******Les réponses de l'utilisateur : " + listeReponses);
    // Faire une copie du questionnaire avec les réponses au bon endroit
    Questionnaire questionnaireAvecReponsesUtilisateur = 
            new Questionnaire(questionnaireSansReponseUtilisateur, mapReponsesUtilisateur);
    System.out.println("questionnaireAvecReponsesUtilisateur = " + questionnaireAvecReponsesUtilisateur);
    return questionnaireAvecReponsesUtilisateur;
  }

  @Override
  public String toString() {
    return "TestQcm{" + "id=" + id + ", note=" + note + ", id questionnaire=" + questionnaire.getId()
            + ", reponsesTests=" + reponsesTests + ", date=" + date + '}';
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 53 * hash + (int) (this.id ^ (this.id >>> 32));
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
    final TestQcm other = (TestQcm) obj;
    if (this.id != other.id) {
      return false;
    }
    return true;
  }
  
  
  
}
