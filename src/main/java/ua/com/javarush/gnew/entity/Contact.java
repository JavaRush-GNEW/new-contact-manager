package ua.com.javarush.gnew.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NamedQueries({
        @NamedQuery(
                name = "Contact.findByName",
                query = "from Contact where name= :name"
        )
})
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    // Consider switching to LAZY loading if appropriate.
    @OneToMany(mappedBy = "contact", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Email> emails = new ArrayList<>();

    @OneToMany(mappedBy = "contact", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Phone> phones = new ArrayList<>();

    @OneToMany(mappedBy = "contact", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<SocialNetwork> networks = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;
        return id == contact.id && name.equals(contact.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }
}
