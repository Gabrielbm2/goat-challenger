package api.com.transmission.specializations.controllers;


import api.com.transmission.specializations.dtos.CreateUserRequestDto;
import api.com.transmission.specializations.dtos.UserResponseDto;
import api.com.transmission.specializations.infra.config.TokenConfig;
import api.com.transmission.specializations.repositories.UserRepository;
import api.com.transmission.specializations.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private List<UserResponseDto> userList;

    @MockBean
    private TokenConfig tokenConfig;
    @Autowired
    private UserController userController;
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @WithMockUser(username = "user1", roles = { "USER" })
    public void listUsers() throws Exception {
        String token = "seu-token-jwt-aqui";
        String bearerToken = "Bearer " + token;

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .header("Authorization", bearerToken)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", roles = { "USER" })
    public void getUserById() throws Exception {
        String token = "seu-token-jwt-aqui";
        String bearerToken = "Bearer " + token;

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/{userId}", "a13faee6-96f1-4d9c-b693-9ce48c71a3ca")
                        .header("Authorization", bearerToken)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void createUser() throws Exception {
        CreateUserRequestDto requestDto = new CreateUserRequestDto();
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(UUID.randomUUID()); // Certifique-se de que o ID está configurado
        when(userService.save(any(CreateUserRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user1", roles = { "USER" })
    public void updateUser() throws Exception {
        String token = "seu-token-jwt-aqui";
        String bearerToken = "Bearer " + token;

        String updatedUserJson = "{\"name\": \"Nome Atualizado\", \"email\": \"atualizado@example.com\"}";

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/a13faee6-96f1-4d9c-b693-9ce48c71a3ca")
                        .header("Authorization", bearerToken)
                        .contentType("application/json")
                        .content(updatedUserJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", roles = { "USER" })
    public void deleteUser() throws Exception {
        String token = "seu-token-jwt-aqui";
        String bearerToken = "Bearer " + token;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/{userId}", "a13faee6-96f1-4d9c-b693-9ce48c71a3ca")
                        .header("Authorization", bearerToken)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
