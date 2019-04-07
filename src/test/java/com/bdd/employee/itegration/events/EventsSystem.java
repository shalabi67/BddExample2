package com.bdd.employee.itegration.events;

import com.bdd.employee.configurations.SecurityConfiguration;
import com.bdd.employee.employees.EmployeeController;
import com.bdd.employee.events.EmployeeEvent;
import com.bdd.employee.events.EventController;
import com.bdd.employee.employees.JsonMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Base64Utils;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventsSystem implements com.bdd.employee.facade.EventsSystem {
    private MockMvc mockMvc;

    public EventsSystem(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Override
    public List<EmployeeEvent> getEvents(long employeeId) {
        JsonMapper<EmployeeEvent> jsonMapper = new JsonMapper<>();
        List<EmployeeEvent> employeeEvents = null;
        MvcResult mvcResult = null;
        try {
            mvcResult = this.mockMvc
                    .perform(
                            get(EmployeeController.URL + "/" + employeeId + EventController.EVENTS_URL)
                                    .header(HttpHeaders.AUTHORIZATION,
                                            "Basic " + Base64Utils.encodeToString((SecurityConfiguration.ADMIN_USER + ":" + SecurityConfiguration.ADMIN_PASSWORD).getBytes()))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andReturn();
            String content = mvcResult.getResponse().getContentAsString();
            employeeEvents = jsonMapper.toObjectList(content, new TypeReference<List<EmployeeEvent>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return employeeEvents;
    }
}
