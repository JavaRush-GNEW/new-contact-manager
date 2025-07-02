package ua.com.javarush.gnew.contactm.DTOs;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactCacheDto {
  private long id;
  private String name;
  private String lastName;
  private String imageUrl;
  private Date createDate;
  private Date modifyDate;
  private List<String> emailAddresses;
  private List<String> phoneNumbers;
  private List<String> socialNetworks;
  private String contactBookName;
}
