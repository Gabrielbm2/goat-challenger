package api.com.transmission.specializations.controllers;

import api.com.transmission.specializations.dtos.CreateServantSpecializationApprovalRequestDto;
import api.com.transmission.specializations.dtos.CreateServantSpecializationRejectionRequestDto;
import api.com.transmission.specializations.services.ServantSpecializationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

public class ServantSpecializationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ServantSpecializationService specializationService;

    @InjectMocks
    private ServantSpecializationController specializationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(specializationController).build();
    }

    @Test
    public void testApproveSpecialization() throws Exception {
        UUID servantId = UUID.randomUUID();
        UUID specializationId = UUID.randomUUID();
        CreateServantSpecializationApprovalRequestDto dto = new CreateServantSpecializationApprovalRequestDto(servantId, specializationId);

        mockMvc.perform(post("/servant-specializations/approve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isCreated());

        verify(specializationService, times(1)).approve(dto);
    }

    @Test
    public void testRejectSpecialization() throws Exception {
        UUID servantId = UUID.randomUUID();
        UUID specializationId = UUID.randomUUID();
        String content = "Rejection reason";
        CreateServantSpecializationRejectionRequestDto dto = new CreateServantSpecializationRejectionRequestDto(servantId, specializationId, content);

        mockMvc.perform(post("/servant-specializations/reject")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isCreated());

        verify(specializationService, times(1)).reject(dto);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
