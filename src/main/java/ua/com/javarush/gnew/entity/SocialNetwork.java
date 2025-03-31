package ua.com.javarush.gnew.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "social_network")
public class SocialNetwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "label")
    private String label;

    @Column(name = "account")
    private String account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;


    @Override
    public String toString() {
        return "SocialNetwork{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}
