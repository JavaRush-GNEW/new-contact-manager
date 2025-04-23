package ua.com.javarush.gnew.repository;

import ua.com.javarush.gnew.entity.Email;

public class EmailRepository extends BaseRepository<Email, Integer> {

  public EmailRepository() {
    super(Email.class);
  }
}
