package com.example.findmyproject;

import com.example.findmyproject.model.Project;
import com.example.findmyproject.model.Researcher;
import com.example.findmyproject.repository.ProjectJpaRepository;
import com.example.findmyproject.repository.ResearcherJpaRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.*;
import javax.transaction.Transactional;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = { "/schema.sql", "/data.sql" })
public class FindMyProjectControllerTests {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ResearcherJpaRepository researcherJpaRepository;

        @Autowired
        private ProjectJpaRepository projectJpaRepository;

        @Autowired
        private JdbcTemplate jdbcTemplate;

        private HashMap<Integer, Object[]> researchersHashMap = new HashMap<>(); // Researcher
        {
                researchersHashMap.put(1, new Object[] { "Marie Curie", "Radioactivity", new Integer[] { 1, 2 } });
                researchersHashMap.put(2, new Object[] { "Albert Einstein", "Relativity", new Integer[] { 2 } });
                researchersHashMap.put(3,
                                new Object[] { "Isaac Newton", "Classical Mechanics", new Integer[] { 3, 4 } });
                researchersHashMap.put(4, new Object[] { "Niels Bohr", "Quantum Mechanics", new Integer[] { 4 } });
                researchersHashMap.put(5,
                                new Object[] { "Rosalind Franklin", "Molecular Biology", new Integer[] { 4 } }); // POST
                researchersHashMap.put(6, new Object[] { "Richard Feynman", "Quantum Physics", new Integer[] { 5 } }); // PUT
        }

        private HashMap<Integer, Object[]> projectsHashMap = new HashMap<>(); // Project
        {
                projectsHashMap.put(1, new Object[] { "Project Alpha", 50000.00, new Integer[] { 1 } });
                projectsHashMap.put(2, new Object[] { "Project Beta", 100000.00, new Integer[] { 1, 2 } });
                projectsHashMap.put(3, new Object[] { "Project Gamma", 150000.00, new Integer[] { 3 } });
                projectsHashMap.put(4, new Object[] { "Project Delta", 75000.00, new Integer[] { 3, 4 } });
                projectsHashMap.put(5, new Object[] { "DNA Structure Study", 120000.00, new Integer[] { 4, 5 } }); // POST
                projectsHashMap.put(6,
                                new Object[] { "Quantum Electrodynamics Study", 200000.00, new Integer[] { 5 } }); // PUT
        }

        @Test
        @Order(1)
        public void testGetResearchers() throws Exception {
                mockMvc.perform(get("/researchers")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(4)))

                                .andExpect(jsonPath("$[0].researcherId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].researcherName",
                                                Matchers.equalTo(researchersHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].specialization",
                                                Matchers.equalTo(researchersHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$[0].projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(1)[2])[1])))

                                .andExpect(jsonPath("$[1].researcherId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].researcherName",
                                                Matchers.equalTo(researchersHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].specialization",
                                                Matchers.equalTo(researchersHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(2)[2])[0])))

                                .andExpect(jsonPath("$[2].researcherId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].researcherName",
                                                Matchers.equalTo(researchersHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].specialization",
                                                Matchers.equalTo(researchersHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$[2].projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(3)[2])[1])))

                                .andExpect(jsonPath("$[3].researcherId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].researcherName",
                                                Matchers.equalTo(researchersHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].specialization",
                                                Matchers.equalTo(researchersHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(4)[2])[0])));
        }

        @Test
        @Order(2)
        public void testGetProjects() throws Exception {
                mockMvc.perform(get("/researchers/projects")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(4)))

                                .andExpect(jsonPath("$[0].projectId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].projectName", Matchers.equalTo(projectsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].budget", Matchers.equalTo(projectsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(1)[2])[0])))

                                .andExpect(jsonPath("$[1].projectId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].projectName", Matchers.equalTo(projectsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].budget", Matchers.equalTo(projectsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(2)[2])[0])))
                                .andExpect(jsonPath("$[1].researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(2)[2])[1])))

                                .andExpect(jsonPath("$[2].projectId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].projectName", Matchers.equalTo(projectsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].budget", Matchers.equalTo(projectsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(3)[2])[0])))

                                .andExpect(jsonPath("$[3].projectId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].projectName", Matchers.equalTo(projectsHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].budget", Matchers.equalTo(projectsHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(4)[2])[0])))
                                .andExpect(jsonPath("$[3].researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(4)[2])[1])));
        }

        @Test
        @Order(3)
        public void testGetResearcherNotFound() throws Exception {
                mockMvc.perform(get("/researchers/48")).andExpect(status().isNotFound());
        }

        @Test
        @Order(4)
        public void testGetResearcherById() throws Exception {
                mockMvc.perform(get("/researchers/1")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.researcherId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.researcherName", Matchers.equalTo(researchersHashMap.get(1)[0])))
                                .andExpect(jsonPath("$.specialization", Matchers.equalTo(researchersHashMap.get(1)[1])))
                                .andExpect(jsonPath("$.projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$.projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(1)[2])[1])));

                mockMvc.perform(get("/researchers/2")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.researcherId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.researcherName", Matchers.equalTo(researchersHashMap.get(2)[0])))
                                .andExpect(jsonPath("$.specialization", Matchers.equalTo(researchersHashMap.get(2)[1])))
                                .andExpect(jsonPath("$.projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(2)[2])[0])));

                mockMvc.perform(get("/researchers/3")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.researcherId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.researcherName", Matchers.equalTo(researchersHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.specialization", Matchers.equalTo(researchersHashMap.get(3)[1])))
                                .andExpect(jsonPath("$.projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$.projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(3)[2])[1])));

                mockMvc.perform(get("/researchers/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.researcherId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.researcherName", Matchers.equalTo(researchersHashMap.get(4)[0])))
                                .andExpect(jsonPath("$.specialization", Matchers.equalTo(researchersHashMap.get(4)[1])))
                                .andExpect(jsonPath("$.projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(4)[2])[0])));
        }

        @Test
        @Order(5)
        public void testGetProjectNotFound() throws Exception {
                mockMvc.perform(get("/researchers/projects/48")).andExpect(status().isNotFound());
        }

        @Test
        @Order(6)
        public void testGetProjectById() throws Exception {
                mockMvc.perform(get("/researchers/projects/1")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.projectId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.projectName", Matchers.equalTo(projectsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$.budget", Matchers.equalTo(projectsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$.researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(1)[2])[0])));

                mockMvc.perform(get("/researchers/projects/2")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.projectId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.projectName", Matchers.equalTo(projectsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$.budget", Matchers.equalTo(projectsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$.researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(2)[2])[0])))
                                .andExpect(jsonPath("$.researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(2)[2])[1])));

                mockMvc.perform(get("/researchers/projects/3")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.projectId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.projectName", Matchers.equalTo(projectsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.budget", Matchers.equalTo(projectsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$.researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(3)[2])[0])));

                mockMvc.perform(get("/researchers/projects/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.projectId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.projectName", Matchers.equalTo(projectsHashMap.get(4)[0])))
                                .andExpect(jsonPath("$.budget", Matchers.equalTo(projectsHashMap.get(4)[1])))
                                .andExpect(jsonPath("$.researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(4)[2])[0])))
                                .andExpect(jsonPath("$.researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(4)[2])[1])));
        }

        @Test
        @Order(7)
        public void testPostResearcher() throws Exception {
                String content = "{\n    \"researcherName\": \"" + researchersHashMap.get(5)[0]
                                + "\",\n    \"specialization\": \""
                                + researchersHashMap.get(5)[1]
                                + "\",\n    \"projects\": [\n        {\n            \"projectId\": "
                                + ((Integer[]) researchersHashMap.get(5)[2])[0]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/researchers")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.researcherId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.researcherName", Matchers.equalTo(researchersHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.specialization", Matchers.equalTo(researchersHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(5)[2])[0])));
        }

        @Test
        @Order(8)
        public void testAfterPostResearcher() throws Exception {
                mockMvc.perform(get("/researchers/5")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.researcherId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.researcherName", Matchers.equalTo(researchersHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.specialization", Matchers.equalTo(researchersHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(5)[2])[0])));
        }

        @Test
        @Order(9)
        @Transactional
        public void testDbAfterPostResearcher() throws Exception {
                Researcher researcher = researcherJpaRepository.findById(5).get();

                assertEquals(researcher.getResearcherId(), 5);
                assertEquals(researcher.getResearcherName(), researchersHashMap.get(5)[0]);
                assertEquals(researcher.getSpecialization(), researchersHashMap.get(5)[1]);
                assertEquals(researcher.getProjects().get(0).getProjectId(),
                                ((Integer[]) researchersHashMap.get(5)[2])[0]);

                Project project = projectJpaRepository.findById(((Integer[]) researchersHashMap.get(5)[2])[0]).get();

                int i;
                for (i = 0; i < project.getResearchers().size(); i++) {
                        if (project.getResearchers().get(i).getResearcherId() == 5) {
                                break;
                        }
                }
                if (i == project.getResearchers().size()) {
                        throw new AssertionError("Assertion Error: Project " + project.getProjectId()
                                        + " has no researcher with researcherId 5");
                }
        }

        @Test
        @Order(10)
        public void testPostProjectBadRequest() throws Exception {
                String content = "{\n    \"projectName\": \"" + projectsHashMap.get(5)[0] + "\",\n    \"budget\": \""
                                + projectsHashMap.get(5)[1]
                                + "\",\n    \"researchers\": [\n        {\n            \"researcherId\": "
                                + ((Integer[]) projectsHashMap.get(5)[2])[0]
                                + "\n        },\n        {\n            \"researcherId\": " + 48
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/researchers/projects")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
        }

        @Test
        @Order(11)
        public void testPostProject() throws Exception {
                String content = "{\n    \"projectName\": \"" + projectsHashMap.get(5)[0] + "\",\n    \"budget\": \""
                                + projectsHashMap.get(5)[1]
                                + "\",\n    \"researchers\": [\n        {\n            \"researcherId\": "
                                + ((Integer[]) projectsHashMap.get(5)[2])[0]
                                + "\n        },\n        {\n            \"researcherId\": "
                                + ((Integer[]) projectsHashMap.get(5)[2])[1]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/researchers/projects")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.projectId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.projectName", Matchers.equalTo(projectsHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.budget", Matchers.equalTo(projectsHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(5)[2])[0])))
                                .andExpect(jsonPath("$.researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(5)[2])[1])));
        }

        @Test
        @Order(12)
        public void testAfterPostProject() throws Exception {
                mockMvc.perform(get("/researchers/projects/5")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.projectId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.projectName", Matchers.equalTo(projectsHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.budget", Matchers.equalTo(projectsHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(5)[2])[0])))
                                .andExpect(jsonPath("$.researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(5)[2])[1])));
        }

        @Test
        @Order(13)
        @Transactional
        public void testDbAfterPostProject() throws Exception {
                Project project = projectJpaRepository.findById(5).get();

                assertEquals(project.getProjectId(), 5);
                assertEquals(project.getProjectName(), projectsHashMap.get(5)[0]);
                assertEquals(project.getBudget(), projectsHashMap.get(5)[1]);
                assertEquals(project.getResearchers().get(0).getResearcherId(),
                                ((Integer[]) projectsHashMap.get(5)[2])[0]);
                assertEquals(project.getResearchers().get(1).getResearcherId(),
                                ((Integer[]) projectsHashMap.get(5)[2])[1]);

                Researcher researcher = researcherJpaRepository.findById(((Integer[]) projectsHashMap.get(5)[2])[0])
                                .get();

                int i;
                for (i = 0; i < researcher.getProjects().size(); i++) {
                        if (researcher.getProjects().get(i).getProjectId() == 5) {
                                break;
                        }
                }
                if (i == researcher.getProjects().size()) {
                        throw new AssertionError("Assertion Error: Researcher " + researcher.getResearcherId()
                                        + " has no project with projectId 5");
                }

                researcher = researcherJpaRepository.findById(((Integer[]) projectsHashMap.get(5)[2])[1]).get();
                for (i = 0; i < researcher.getProjects().size(); i++) {
                        if (researcher.getProjects().get(i).getProjectId() == 5) {
                                break;
                        }
                }
                if (i == researcher.getProjects().size()) {
                        throw new AssertionError("Assertion Error: Researcher " + researcher.getResearcherId()
                                        + " has no project with projectId 5");
                }
        }

        @Test
        @Order(14)
        public void testPutResearcherNotFound() throws Exception {
                String content = "{\n    \"researcherName\": \"" + researchersHashMap.get(6)[0]
                                + "\",\n    \"specialization\": \""
                                + researchersHashMap.get(6)[1]
                                + "\",\n    \"projects\": [\n        {\n            \"projectId\": "
                                + ((Integer[]) researchersHashMap.get(6)[2])[0]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/researchers/48")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(15)
        public void testPutResearcher() throws Exception {
                String content = "{\n    \"researcherName\": \"" + researchersHashMap.get(6)[0]
                                + "\",\n    \"specialization\": \""
                                + researchersHashMap.get(6)[1]
                                + "\",\n    \"projects\": [\n        {\n            \"projectId\": "
                                + ((Integer[]) researchersHashMap.get(6)[2])[0]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/researchers/5")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.researcherId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.researcherName", Matchers.equalTo(researchersHashMap.get(6)[0])))
                                .andExpect(jsonPath("$.specialization", Matchers.equalTo(researchersHashMap.get(6)[1])))
                                .andExpect(jsonPath("$.projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(6)[2])[0])));
        }

        @Test
        @Order(16)
        public void testAfterPutResearcher() throws Exception {
                mockMvc.perform(get("/researchers/5")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.researcherId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.researcherName", Matchers.equalTo(researchersHashMap.get(6)[0])))
                                .andExpect(jsonPath("$.specialization", Matchers.equalTo(researchersHashMap.get(6)[1])))
                                .andExpect(jsonPath("$.projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(6)[2])[0])));
        }

        @Test
        @Order(17)
        @Transactional
        public void testDbAfterPutResearcher() throws Exception {
                Researcher researcher = researcherJpaRepository.findById(5).get();

                assertEquals(researcher.getResearcherId(), 5);
                assertEquals(researcher.getResearcherName(), researchersHashMap.get(6)[0]);
                assertEquals(researcher.getSpecialization(), researchersHashMap.get(6)[1]);
                assertEquals(researcher.getProjects().get(0).getProjectId(),
                                ((Integer[]) researchersHashMap.get(6)[2])[0]);

                Project project = projectJpaRepository.findById(((Integer[]) researchersHashMap.get(6)[2])[0]).get();

                int i;
                for (i = 0; i < project.getResearchers().size(); i++) {
                        if (project.getResearchers().get(i).getResearcherId() == 5) {
                                break;
                        }
                }
                if (i == project.getResearchers().size()) {
                        throw new AssertionError("Assertion Error: Project " + project.getProjectId()
                                        + " has no researcher with researcherId 5");
                }
        }

        @Test
        @Order(18)
        public void testPutProjectNotFound() throws Exception {
                String content = "{\n    \"projectName\": \"" + projectsHashMap.get(6)[0] + "\",\n    \"budget\": \""
                                + projectsHashMap.get(6)[1]
                                + "\",\n    \"researchers\": [\n        {\n            \"researcherId\": "
                                + ((Integer[]) projectsHashMap.get(6)[2])[0]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/researchers/projects/48")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(19)
        public void testPutProjectBadRequest() throws Exception {
                String content = "{\n    \"projectName\": \"" + projectsHashMap.get(6)[0] + "\",\n    \"budget\": \""
                                + projectsHashMap.get(6)[1]
                                + "\",\n    \"researchers\": [\n        {\n            \"researcherId\": "
                                + 48 + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/researchers/projects/5")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
        }

        @Test
        @Order(20)
        public void testPutProject() throws Exception {
                String content = "{\n    \"projectName\": \"" + projectsHashMap.get(6)[0] + "\",\n    \"budget\": \""
                                + projectsHashMap.get(6)[1]
                                + "\",\n    \"researchers\": [\n        {\n            \"researcherId\": "
                                + ((Integer[]) projectsHashMap.get(6)[2])[0]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/researchers/projects/5")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.projectId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.projectName", Matchers.equalTo(projectsHashMap.get(6)[0])))
                                .andExpect(jsonPath("$.budget", Matchers.equalTo(projectsHashMap.get(6)[1])))
                                .andExpect(jsonPath("$.researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(6)[2])[0])));
        }

        @Test
        @Order(21)
        public void testAfterPutProject() throws Exception {

                mockMvc.perform(get("/researchers/projects/5")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.projectId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.projectName", Matchers.equalTo(projectsHashMap.get(6)[0])))
                                .andExpect(jsonPath("$.budget", Matchers.equalTo(projectsHashMap.get(6)[1])))
                                .andExpect(jsonPath("$.researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(6)[2])[0])));
        }

        @Test
        @Order(22)
        @Transactional
        public void testDbAfterPutProject() throws Exception {
                Project project = projectJpaRepository.findById(5).get();

                assertEquals(project.getProjectId(), 5);
                assertEquals(project.getProjectName(), projectsHashMap.get(6)[0]);
                assertEquals(project.getBudget(), projectsHashMap.get(6)[1]);
                assertEquals(project.getResearchers().get(0).getResearcherId(),
                                ((Integer[]) projectsHashMap.get(6)[2])[0]);

                Researcher researcher = researcherJpaRepository.findById(((Integer[]) projectsHashMap.get(6)[2])[0])
                                .get();

                int i;
                for (i = 0; i < researcher.getProjects().size(); i++) {
                        if (researcher.getProjects().get(i).getProjectId() == 5) {
                                break;
                        }
                }
                if (i == researcher.getProjects().size()) {
                        throw new AssertionError("Assertion Error: Researcher " + researcher.getResearcherId()
                                        + " has no project with projectId 5");
                }
        }

        @Test
        @Order(23)
        public void testDeleteProjectNotFound() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/researchers/projects/148");
                mockMvc.perform(mockRequest).andExpect(status().isNotFound());

        }

        @Test
        @Order(24)
        public void testDeleteProject() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/researchers/projects/5");
                mockMvc.perform(mockRequest).andExpect(status().isNoContent());
        }

        @Test
        @Order(25)
        @Transactional
        @Rollback(false)
        public void testAfterDeleteProject() throws Exception {
                mockMvc.perform(get("/researchers/projects")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(4)))

                                .andExpect(jsonPath("$[0].projectId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].projectName", Matchers.equalTo(projectsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].budget", Matchers.equalTo(projectsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(1)[2])[0])))

                                .andExpect(jsonPath("$[1].projectId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].projectName", Matchers.equalTo(projectsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].budget", Matchers.equalTo(projectsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(2)[2])[0])))
                                .andExpect(jsonPath("$[1].researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(2)[2])[1])))

                                .andExpect(jsonPath("$[2].projectId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].projectName", Matchers.equalTo(projectsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].budget", Matchers.equalTo(projectsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(3)[2])[0])))

                                .andExpect(jsonPath("$[3].projectId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].projectName", Matchers.equalTo(projectsHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].budget", Matchers.equalTo(projectsHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(4)[2])[0])))
                                .andExpect(jsonPath("$[3].researchers[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(4)[2])[1])));

                Researcher researcher = researcherJpaRepository.findById(((Integer[]) projectsHashMap.get(6)[2])[0])
                        .get();

                for (Project project : researcher.getProjects()) {
                        if (project.getProjectId() == 5) {
                                throw new AssertionError("Assertion Error: Project " + project.getProjectId()
                                        + " and Researcher 5 are still linked");
                        }
                }
        }

        @Test
        @Order(26)
        public void testDeleteResearcherNotFound() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/researchers/148");
                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(27)
        public void testDeleteResearcher() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/researchers/5");
                mockMvc.perform(mockRequest).andExpect(status().isNoContent());
        }

        @Test
        @Order(28)
        public void testAfterDeleteResearcher() throws Exception {
                mockMvc.perform(get("/researchers")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(4)))

                                .andExpect(jsonPath("$[0].researcherId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].researcherName",
                                                Matchers.equalTo(researchersHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].specialization",
                                                Matchers.equalTo(researchersHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$[0].projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(1)[2])[1])))

                                .andExpect(jsonPath("$[1].researcherId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].researcherName",
                                                Matchers.equalTo(researchersHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].specialization",
                                                Matchers.equalTo(researchersHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(2)[2])[0])))

                                .andExpect(jsonPath("$[2].researcherId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].researcherName",
                                                Matchers.equalTo(researchersHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].specialization",
                                                Matchers.equalTo(researchersHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$[2].projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(3)[2])[1])))

                                .andExpect(jsonPath("$[3].researcherId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].researcherName",
                                                Matchers.equalTo(researchersHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].specialization",
                                                Matchers.equalTo(researchersHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].projects[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(4)[2])[0])));
        }

        @Test
        @Order(29)
        public void testGetResearcherByProjectId() throws Exception {
                mockMvc.perform(get("/projects/1/researchers")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(1)[2])[0])));

                mockMvc.perform(get("/projects/2/researchers")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(2)[2])[0])))
                                .andExpect(jsonPath("$[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(2)[2])[1])));

                mockMvc.perform(get("/projects/3/researchers")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(3)[2])[0])));

                mockMvc.perform(get("/projects/4/researchers")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(4)[2])[0])))
                                .andExpect(jsonPath("$[*].researcherId",
                                                hasItem(((Integer[]) projectsHashMap.get(4)[2])[1])));
        }

        @Test
        @Order(30)
        public void testGetProjectByResearcherId() throws Exception {
                mockMvc.perform(get("/researchers/1/projects")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(1)[2])[1])));

                mockMvc.perform(get("/researchers/2/projects")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(2)[2])[0])));

                mockMvc.perform(get("/researchers/3/projects")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(3)[2])[1])));

                mockMvc.perform(get("/researchers/4/projects")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].projectId",
                                                hasItem(((Integer[]) researchersHashMap.get(4)[2])[0])));
        }

        @AfterAll
        public void cleanup() {
                jdbcTemplate.execute("drop table researcher_project");
                jdbcTemplate.execute("drop table project");
                jdbcTemplate.execute("drop table researcher");
        }

}