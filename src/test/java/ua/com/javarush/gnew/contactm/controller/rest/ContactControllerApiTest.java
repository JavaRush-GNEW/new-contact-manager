package ua.com.javarush.gnew.contactm.controller.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.com.javarush.gnew.contactm.DTOs.ContactDTO;
import ua.com.javarush.gnew.contactm.entity.Contact;
import ua.com.javarush.gnew.contactm.mapper.ContactMapper;
import ua.com.javarush.gnew.contactm.services.ContactService;

@WebMvcTest(
    value = ContactControllerApi.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
class ContactControllerApiTest {

  @Autowired private MockMvc mvc;

  @MockitoBean private ContactService contactService;

  @MockitoBean private ContactMapper contactMapper;

  @Test
  void getContact_ShouldReturnContactDTOAndStatus200WhenContactExists() throws Exception {
    // Arrange
    long id = 1L;
    String name = "name";

    Contact contact = Contact.builder().id(id).name(name).build();

    ContactDTO contactDTO = ContactDTO.builder().id(id).name(name).build();

    when(contactService.findById(id)).thenReturn(Optional.ofNullable(contact));
    when(contactMapper.toDto(contact)).thenReturn(contactDTO);

    // Act & Assert
    String path = "/api/v1/contact";

    mvc.perform(get(path).param("id", "1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name));
  }

  @Test
  void getContact_ShouldReturnStatus404WhenContactNotExists() throws Exception {
    // Arrange
    long id = 1L;

    when(contactService.findById(id)).thenReturn(Optional.empty());

    // Act & Assert
    String path = "/api/v1/contact";

    mvc.perform(get(path).param("id", "1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}
