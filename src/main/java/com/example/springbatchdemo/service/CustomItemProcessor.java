package com.example.springbatchdemo.service;

import com.example.springbatchdemo.model.FlightTicket;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<FlightTicket, FlightTicket> {

    @Override
    public FlightTicket process(FlightTicket flightTicket) throws Exception {

        // Process the flight ticket
        System.out.printf("Processing %s...%n", flightTicket);
        flightTicket.setTicketPrice(flightTicket.getTicketPrice() * 1.1);
        flightTicket.setRoute(flightTicket.getRoute() + " (Processed)");

        return flightTicket;
    }
}