package com.Getting_Started_With_SpringBoot.demo.controller;

import com.Getting_Started_With_SpringBoot.demo.Entity.Fleet;
import com.Getting_Started_With_SpringBoot.demo.service.FleetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fleets")
@CrossOrigin(origins = "*")
public class FleetController {

    @Autowired
    private FleetService fleetService;

    @GetMapping
    public ResponseEntity<List<Fleet>> getAllFleets() {
        try {
            List<Fleet> fleets = fleetService.getAllFleets();
            return new ResponseEntity<>(fleets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{fleetId}")
    public ResponseEntity<Fleet> getFleetById(@PathVariable("fleetId") Long fleetId) {
        try {
            Fleet fleet = fleetService.getFleetById(fleetId);
            if (fleet != null) {
                return new ResponseEntity<>(fleet, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> createFleet(@RequestBody Fleet fleet) {
        try {
            // Validate required fields
            if (fleet.getOrigin() == null || fleet.getDestination() == null) {
                return new ResponseEntity<>("Origin and destination are required", HttpStatus.BAD_REQUEST);
            }

            fleetService.createFleet(fleet);
            return new ResponseEntity<>("Fleet created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create fleet: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{fleetId}")
    public ResponseEntity<String> updateFleet(@PathVariable("fleetId") Long fleetId, @RequestBody Fleet fleetDetails) {
        try {
            Fleet existingFleet = fleetService.getFleetById(fleetId);
            if (existingFleet == null) {
                return new ResponseEntity<>("Fleet not found with id: " + fleetId, HttpStatus.NOT_FOUND);
            }

            fleetService.updateFleet(fleetId, fleetDetails);
            return new ResponseEntity<>("Fleet updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update fleet: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{fleetId}")
    public ResponseEntity<String> deleteFleet(@PathVariable("fleetId") Long fleetId) {
        try {
            Fleet existingFleet = fleetService.getFleetById(fleetId);
            if (existingFleet == null) {
                return new ResponseEntity<>("Fleet not found with id: " + fleetId, HttpStatus.NOT_FOUND);
            }

            fleetService.deleteFleet(fleetId);
            return new ResponseEntity<>("Fleet deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete fleet: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Fleet>> searchFleets(
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination) {
        try {
            List<Fleet> allFleets = fleetService.getAllFleets();

            List<Fleet> filteredFleets = allFleets.stream()
                    .filter(fleet -> origin == null || fleet.getOrigin().toLowerCase().contains(origin.toLowerCase()))
                    .filter(fleet -> destination == null || fleet.getDestination().toLowerCase().contains(destination.toLowerCase()))
                    .toList();

            return new ResponseEntity<>(filteredFleets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getFleetCount() {
        try {
            List<Fleet> fleets = fleetService.getAllFleets();
            return new ResponseEntity<>(fleets.size(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}