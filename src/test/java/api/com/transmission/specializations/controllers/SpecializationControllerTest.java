package api.com.transmission.specializations.controllers;

import api.com.transmission.specializations.dtos.CreateSpecializationRequestDto;
import api.com.transmission.specializations.dtos.SpecializationResponseDto;
import api.com.transmission.specializations.dtos.UpdateSpecializationDto;
import api.com.transmission.specializations.services.SpecializationService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SpecializationControllerTest {

    @Mock
    private SpecializationService specializationService;

    @InjectMocks
    private SpecializationController specializationController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(specializationController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void listSpecializations() throws Exception {
        SpecializationResponseDto dto = new SpecializationResponseDto();
        dto.setId(UUID.randomUUID());
        List<SpecializationResponseDto> list = List.of(dto);
        when(specializationService.list()).thenReturn(list);

        mockMvc.perform(get("/specializations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").exists());

        verify(specializationService, times(1)).list();
    }

    @Test
    public void testGetSpecializationById() throws Exception {
        UUID id = UUID.randomUUID();
        SpecializationResponseDto dto = new SpecializationResponseDto();
        dto.setId(id);
        when(specializationService.findById(id)).thenReturn(dto);

        mockMvc.perform(get("/specializations/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());

        verify(specializationService, times(1)).findById(id);
    }

    @Test
    public void testCreateSpecialization() throws Exception {
        CreateSpecializationRequestDto requestDto = new CreateSpecializationRequestDto();
        SpecializationResponseDto responseDto = new SpecializationResponseDto();
        responseDto.setId(UUID.randomUUID());
        when(specializationService.save(any(CreateSpecializationRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/specializations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());

        verify(specializationService, times(1)).save(any(CreateSpecializationRequestDto.class));
    }

    @Test
    public void testUpdateSpecialization() throws Exception {
        UUID id = UUID.randomUUID();
        UpdateSpecializationDto updateDto = new UpdateSpecializationDto();
        SpecializationResponseDto responseDto = new SpecializationResponseDto();
        responseDto.setId(id);
        when(specializationService.update(eq(id), any(UpdateSpecializationDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/specializations/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());

        verify(specializationService, times(1)).update(eq(id), any(UpdateSpecializationDto.class));
    }

    @Test
    public void testDeleteSpecialization() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(specializationService).delete(id);

        mockMvc.perform(delete("/specializations/{id}", id))
                .andExpect(status().isNoContent());

        verify(specializationService, times(1)).delete(id);
    }

    @Test
    public void testDeleteSpecializationNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        doThrow(new EntityNotFoundException()).when(specializationService).delete(id);

        mockMvc.perform(delete("/specializations/{id}", id))
                .andExpect(status().isNotFound());

        verify(specializationService, times(1)).delete(id);
    }
}
