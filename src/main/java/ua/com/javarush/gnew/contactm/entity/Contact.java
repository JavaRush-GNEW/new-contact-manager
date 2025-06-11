package ua.com.javarush.gnew.contactm.entity;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@NamedQueries({@NamedQuery(name = "Contact.findByName", query = "from Contact where name= :name")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "contact")
public class Contact {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Expose
  private long id;

  @ManyToOne
  @JoinColumn(name = "contact_book_id")
  private ContactBook contactBook;

  @Column(name = "name")
  @Expose
  private String name;

  // Consider switching to LAZY loading if appropriate.
  @OneToMany(mappedBy = "contact", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @Expose
  private List<Email> emails = new ArrayList<>();

    @Column(name = "last_name")
    @Expose
    private String lastName;

  @OneToMany(mappedBy = "contact", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @Expose
  private List<Phone> phones = new ArrayList<>();

  @OneToMany(mappedBy = "contact", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @Expose
  private List<SocialNetwork> networks = new ArrayList<>();

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "create_date")
  private Date createDate;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "modify_date")
  private Date modifyDate;

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Contact contact)) return false;
    return getId() == contact.getId()
        && Objects.equals(getName(), contact.getName())
        && Objects.equals(getLastName(), contact.getLastName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getLastName());
  }
}
