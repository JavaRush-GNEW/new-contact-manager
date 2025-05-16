package ua.com.javarush.gnew.contactm.entity;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private int id;

    @Column(name = "first_name")
    @Expose
    private String firstName;

    @Column(name = "last_name")
    @Expose
    private String lastName;

    @Column(name = "app_user_name")
    @Expose
    private String userName;

    @Column(name = "password")
    @Expose
    private String password;

    @Column(name = "email")
    @Expose
    private String email;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date")
    private Date modifyDate;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Expose
    private List<ContactBook> contactBooks;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return getId() == appUser.getId() && Objects.equals(getFirstName(), appUser.getFirstName()) && Objects.equals(getLastName(), appUser.getLastName()) && Objects.equals(getUserName(), appUser.getUserName()) && Objects.equals(getPassword(), appUser.getPassword()) && Objects.equals(getEmail(), appUser.getEmail()) && Objects.equals(getCreateDate(), appUser.getCreateDate()) && Objects.equals(getModifyDate(), appUser.getModifyDate()) && Objects.equals(getContactBooks(), appUser.getContactBooks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getUserName(), getPassword(), getEmail(), getCreateDate(), getModifyDate(), getContactBooks());
    }
}
