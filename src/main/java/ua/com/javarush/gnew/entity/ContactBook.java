package ua.com.javarush.gnew.entity;


import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "contact_book")
public class ContactBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private int id;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser owner;

    @OneToMany(mappedBy = "contactBook", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Expose
    private List<Contact> contacts;

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
        if (o == null || getClass() != o.getClass()) return false;
        ContactBook that = (ContactBook) o;
        return getId() == that.getId() && Objects.equals(getOwner(), that.getOwner()) && Objects.equals(getContacts(), that.getContacts()) && Objects.equals(getCreateDate(), that.getCreateDate()) && Objects.equals(getModifyDate(), that.getModifyDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOwner(), getContacts(), getCreateDate(), getModifyDate());
    }
}
