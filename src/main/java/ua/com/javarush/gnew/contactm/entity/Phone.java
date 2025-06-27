package ua.com.javarush.gnew.contactm.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "phone")
public class Phone {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private long id;

  @Column(name = "label", length = 45)
  @Expose
  private String label;

  @Column(name = "phone", nullable = false, length = 45)
  @Expose
  private String phone;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "contact_id", nullable = true)
  @JsonBackReference
  private Contact contact;

  @Override
  public String toString() {
    return "Phone{" + "id=" + id + ", label='" + label + '\'' + ", phone='" + phone + '\'' + '}';
  }
}

/*
       CLI   SERVLET
        -      -
       END     Implemet
      EXTEND   Implemet
*/
