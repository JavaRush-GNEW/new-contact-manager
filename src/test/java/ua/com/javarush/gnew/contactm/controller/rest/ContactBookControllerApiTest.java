package ua.com.javarush.gnew.contactm.controller.rest;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.com.javarush.gnew.contactm.DTOs.ContactBookDTO;
import ua.com.javarush.gnew.contactm.entity.ContactBook;
import ua.com.javarush.gnew.contactm.mapper.ContactBookMapper;
import ua.com.javarush.gnew.contactm.repository.ContactBookRepository;

@WebMvcTest(ContactBookControllerApi.class)
@WithMockUser
class ContactBookControllerApiTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private ContactBookRepository contactBookRepository;

  @MockitoBean private ContactBookMapper contactBookMapper;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void testGetContactBookFound() throws Exception {
    ContactBook contactBook = new ContactBook();
    contactBook.setId(1L);
    contactBook.setName("Test Book");

    ContactBookDTO contactBookDTO = new ContactBookDTO();
    contactBookDTO.setId(1L);
    contactBookDTO.setName("Test Book");

    when(contactBookRepository.findById(1L)).thenReturn(Optional.of(contactBook));
    when(contactBookMapper.toDto(contactBook)).thenReturn(contactBookDTO);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/api/v1/contact-book").param("id", "1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("Test Book"));
  }

  @Test
  void testGetContactBookNotFound() throws Exception {
    when(contactBookRepository.findById(1L)).thenReturn(Optional.empty());

    mockMvc
        .perform(MockMvcRequestBuilders.get("/api/v1/contact-book").param("id", "1"))
        .andExpect(status().isNotFound());
  }

  @Test
  void testSaveContactBook() throws Exception {
    ContactBookDTO requestDTO = new ContactBookDTO();
    requestDTO.setName("New Book");

    ContactBook entity = new ContactBook();
    entity.setName("New Book");

    ContactBookDTO responseDTO = new ContactBookDTO();
    responseDTO.setId(2L);
    responseDTO.setName("New Book");

    ContactBook savedEntity = new ContactBook();
    savedEntity.setId(2L);
    savedEntity.setName("New Book");

    when(contactBookMapper.toEntity(any(ContactBookDTO.class))).thenReturn(entity);
    when(contactBookRepository.save(entity)).thenReturn(savedEntity);
    when(contactBookMapper.toDto(savedEntity)).thenReturn(responseDTO);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/v1/contact-book")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(2L))
        .andExpect(jsonPath("$.name").value("New Book"));
  }

  @Test
  void testSaveContactBookWhenDtoIsInvalid() throws Exception {
    String invalidJson = "{}";
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/v1/contact-book")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testUpdateContactBookFound() throws Exception {
    ContactBook existing = new ContactBook();
    existing.setId(1L);
    existing.setName("Old Book");

    ContactBookDTO requestDTO = new ContactBookDTO();
    requestDTO.setName("Updated Book");

    ContactBook updated = new ContactBook();
    updated.setId(1L);
    updated.setName("Updated Book");

    ContactBookDTO responseDTO = new ContactBookDTO();
    responseDTO.setId(1L);
    responseDTO.setName("Updated Book");

    when(contactBookRepository.findById(1L)).thenReturn(Optional.of(existing));
    when(contactBookMapper.toEntity(any(ContactBookDTO.class))).thenReturn(updated);
    when(contactBookRepository.save(updated)).thenReturn(updated);
    when(contactBookMapper.toDto(updated)).thenReturn(responseDTO);

    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/api/v1/contact-book")
                .with(csrf())
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("Updated Book"));
  }

  @Test
  void testUpdateContactBookNotFound() throws Exception {
    ContactBookDTO requestDTO = new ContactBookDTO();
    requestDTO.setName("Updated Book");

    when(contactBookRepository.findById(1L)).thenReturn(Optional.empty());

    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/api/v1/contact-book")
                .with(csrf())
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
        .andExpect(status().isNotFound());
  }

  @Test
  void testDeleteContactBookFound() throws Exception {
    when(contactBookRepository.existsById(1L)).thenReturn(true);
    doNothing().when(contactBookRepository).deleteById(1L);

    mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/api/v1/contact-book").with(csrf()).param("id", "1"))
        .andExpect(status().isNoContent());
  }

  @Test
  void testDeleteContactBookNotFound() throws Exception {
    when(contactBookRepository.existsById(1L)).thenReturn(false);

    mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/api/v1/contact-book").with(csrf()).param("id", "1"))
        .andExpect(status().isNotFound());
  }
}
