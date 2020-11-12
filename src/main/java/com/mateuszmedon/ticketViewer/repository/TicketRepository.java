package com.mateuszmedon.ticketViewer.repository;


import com.mateuszmedon.ticketViewer.entiti.Ticket;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long> {
}
