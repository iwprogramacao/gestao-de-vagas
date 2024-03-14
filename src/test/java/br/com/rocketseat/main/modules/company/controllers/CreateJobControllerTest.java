package br.com.rocketseat.main.modules.company.controllers;

import br.com.rocketseat.main.modules.company.DTOs.CreateJobDTO;
import br.com.rocketseat.main.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

@RunWith(SpringRunner.class) // Define a forma de rodar a aplicação
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Define uma porta randômica para o teste
public class CreateJobControllerTest {

    private MockMvc mvc; // Simulador de servidor rodando
    @Autowired
    private WebApplicationContext context; // Contexto da aplicação para os testes
    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Before // Antes de executar o teste
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                             .apply(SecurityMockMvcConfigurers.springSecurity()) // Mocka o Spring Security para authenticantion
                             .build();
    }

    @Test
    public void shoud_be_able_to_create_a_new_job() throws Exception {
        var createdJobDTO = CreateJobDTO.builder()
                                        .benefits("Benefits test")
                                        .description("Description test")
                                        .level("Level test")
                                        .build();

        var result = mvc.perform(MockMvcRequestBuilders.post("/company/job") // Recebe o tipo de método que vamos utiliza
                                                       .contentType(MediaType.APPLICATION_JSON)
                                                       .content(TestUtils.objectToJSON(createdJobDTO))
                                                       .header("Authorization", TestUtils.generateToken(UUID.fromString("65d3847b-f39e-49a1-8630-3c1f014d9eaf"), jwtSecret)))
                        .andExpect(MockMvcResultMatchers.status()
                                                        .isOk());

        System.out.println(result);
    }

}
