package com.mateuszmedon.ticketViewer.service;


import com.mateuszmedon.ticketViewer.entiti.Ticket;
import com.mateuszmedon.ticketViewer.exception.NotFoundExceptionId;
import com.mateuszmedon.ticketViewer.repository.TicketRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Optional;

@Service
public class TicketsViewerService {


//    **** Email.properties = "mateuszmedon1@gmail.com"
//    **** Password.properties = "testpassword"

    private static final String URL = "https://mateuszmedon.zendesk.com/api/v2/tickets.json";
    private static final String EMAIL = "****";
    private static final String PASSWORD = "****";

    private static final String TRIP_NOT_FOUND = "Trip with id %s not found.";

    @Autowired
    TicketRepository ticketRepository;

    private final RestTemplate restTemplate;

    TicketsViewerService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .basicAuthentication(EMAIL, PASSWORD)
                .build();
    }


    public void saveOrUpdate(Ticket ticket) {
        ticketRepository.save(ticket);
    }


    public void runData() {

        String json = this.restTemplate.getForObject(URL, String.class);
        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONArray("tickets");
        for (int i = 0; i < arr.length(); i++) {
            Ticket ticket = new Ticket();

            String post_sub = arr.getJSONObject(i).getString("subject");
            ticket.setSubject(post_sub);

            String post_url = arr.getJSONObject(i).getString("url");
            ticket.setUrl(post_url);

            String post_status = arr.getJSONObject(i).getString("status");
            ticket.setStatus(post_status);

            String post_description = arr.getJSONObject(i).getString("description");
            ticket.setDescription(post_description);

            saveOrUpdate(ticket);
        }
    }

    public Ticket findById(Long id) {

        Optional<Ticket> trip = ticketRepository.findById(id);

        if (!trip.isPresent()) {
            throw new NotFoundExceptionId(
                    String.format(TRIP_NOT_FOUND, id));
        }
        return trip.get();
    }
}