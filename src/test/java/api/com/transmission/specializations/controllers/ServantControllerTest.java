package api.com.transmission.specializations.controllers;

import api.com.transmission.specializations.dtos.CreateServantRequestDto;
import api.com.transmission.specializations.dtos.ServantResponseDto;
import api.com.transmission.specializations.dtos.UpdateServantDto;
import api.com.transmission.specializations.services.ServantService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ServantControllerTest {

    @Mock
    private ServantService servantService;

    @InjectMocks
    private ServantController servantController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(servantController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void listServants() throws Exception {
        ServantResponseDto dto = new ServantResponseDto();
        dto.setId(UUID.randomUUID());
        List<ServantResponseDto> list = List.of(dto);
        when(servantService.list()).thenReturn(list);

        MvcResult result = mockMvc.perform(get("/servants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").exists())
                .andReturn();

        System.out.println("Status: " + result.getResponse().getStatus());
        System.out.println("Content: " + result.getResponse().getContentAsString());

        verify(servantService, times(1)).list();
    }

    @Test
    public void testGetServantById() throws Exception {
        UUID id = UUID.randomUUID();
        ServantResponseDto dto = new ServantResponseDto();
        dto.setId(id);
        when(servantService.findById(id)).thenReturn(dto);

        mockMvc.perform(get("/servants/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());

        verify(servantService, times(1)).findById(id);
    }

    @Test
    public void testCreateServant() throws Exception {
        CreateServantRequestDto requestDto = new CreateServantRequestDto();
        ServantResponseDto responseDto = new ServantResponseDto();
        responseDto.setId(UUID.randomUUID());
        when(servantService.save(any(CreateServantRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/servants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());

        verify(servantService, times(1)).save(any(CreateServantRequestDto.class));
    }

    @Test
    public void testUpdateServant() throws Exception {
        UUID id = UUID.randomUUID();
        UpdateServantDto updateDto = new UpdateServantDto();
        ServantResponseDto responseDto = new ServantResponseDto();
        responseDto.setId(id);
        when(servantService.update(eq(id), any(UpdateServantDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/servants/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());

        verify(servantService, times(1)).update(eq(id), any(UpdateServantDto.class));
    }

    @Test
    public void testDeleteServant() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(servantService).delete(id);

        mockMvc.perform(delete("/servants/{id}", id))
                .andExpect(status().isNoContent());

        verify(servantService, times(1)).delete(id);
    }

    @Test
    public void testDeleteServantNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        doThrow(new EntityNotFoundException()).when(servantService).delete(id);

        mockMvc.perform(delete("/servants/{id}", id))
                .andExpect(status().isNotFound());

        verify(servantService, times(1)).delete(id);
    }
}
