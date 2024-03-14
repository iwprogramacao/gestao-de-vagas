package br.com.rocketseat.main.modules.company.controllers;

import br.com.rocketseat.main.modules.company.DTOs.CreateJobDTO;
import br.com.rocketseat.main.modules.company.entities.CompanyEntity;
import br.com.rocketseat.main.modules.company.repositories.CompanyRepository;
import br.com.rocketseat.main.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

@RunWith(SpringRunner.class) // Define a forma de rodar a aplicação
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Define uma porta randômica para o teste
@ActiveProfiles("test") // Chama as "application-test.properties"
public class CreateJobControllerTest {

    private MockMvc mvc; // Simulador de servidor rodando
    @Autowired
    private WebApplicationContext context; // Contexto da aplicação para os testes
    @Autowired
    private CompanyRepository companyRepository;
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
        var company = CompanyEntity.builder()
                                   .description("COMPANY_DESCRIPTION2")
                                   .email("email2@company.com")
                                   .password("123123")
                                   .username("COMPANY_USERNAME2")
                                   .name("COMPANY_NAME2")
                                   .build();
        // Estratégia para salvar imediatamente a informação antes de executar o código posterior
        company = companyRepository.saveAndFlush(company);

        var createdJobDTO = CreateJobDTO.builder()
                                        .benefits("Benefits test")
                                        .description("Description test")
                                        .level("Level test")
                                        .build();

        var result = mvc.perform(MockMvcRequestBuilders.post("/company/job") // Recebe o tipo de método que vamos utiliza
                                                       .contentType(MediaType.APPLICATION_JSON)
                                                       .content(TestUtils.objectToJSON(createdJobDTO))
                                                       .header("Authorization", TestUtils.generateToken(UUID.fromString(company.getId()
                                                                                                                               .toString()), jwtSecret)))
                        .andExpect(MockMvcResultMatchers.status()
                                                        .isOk());

        System.out.println(result);
    }

}
