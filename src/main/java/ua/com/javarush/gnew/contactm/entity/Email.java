package ua.com.javarush.gnew.contactm.entity;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "email")
public class Email {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "label", length = 45)
  @Expose
  private String label;

  @Column(name = "email", nullable = false)
  @Expose
  private String email;

  @ManyToOne(fetch = FetchType.EAGER, optional = true)
  @JoinColumn(name = "contact_id", nullable = true)
  private Contact contact;

  @Override
  public String toString() {
    return "Email{" + "id=" + id + ", label='" + label + '\'' + ", email='" + email + '\'' + '}';
  }
}
