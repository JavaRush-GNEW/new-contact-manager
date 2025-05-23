package ua.com.javarush.gnew.contactm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.javarush.gnew.contactm.entity.SocialNetwork;


public interface SocialNetworkRepository extends JpaRepository<SocialNetwork, Integer> {
}
