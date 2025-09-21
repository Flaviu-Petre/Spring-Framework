package com.SpringBootLogging.app.controller;

import com.SpringBootLogging.app.Entity.Fleet;
import com.SpringBootLogging.app.service.FleetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fleets")
@CrossOrigin(origins = "*")
public class FleetController {

    private static final Logger logger = LogManager.getLogger(FleetController.class);

    @Autowired
    private FleetService fleetService;

    @GetMapping
    public ResponseEntity<List<Fleet>> getAllFleets() {
        logger.info("Request received to get all fleets");
        try {
            List<Fleet> fleets = fleetService.getAllFleets();
            logger.info("Successfully retrieved {} fleets", fleets.size());
            return new ResponseEntity<>(fleets, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while retrieving all fleets: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{fleetId}")
    public ResponseEntity<Fleet> getFleetById(@PathVariable("fleetId") Long fleetId) {
        logger.info("Request received to get fleet with ID: {}", fleetId);
        try {
            Fleet fleet = fleetService.getFleetById(fleetId);
            if (fleet != null) {
                logger.info("Successfully found fleet with ID: {}", fleetId);
                return new ResponseEntity<>(fleet, HttpStatus.OK);
            } else {
                logger.warn("Fleet not found with ID: {}", fleetId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error occurred while retrieving fleet with ID {}: {}", fleetId, e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> createFleet(@RequestBody Fleet fleet) {
        logger.info("Request received to create new fleet: {}", fleet);
        try {
            // Validate required fields
            if (fleet.getOrigin() == null || fleet.getDestination() == null) {
                logger.warn("Fleet creation failed - missing required fields. Origin: {}, Destination: {}",
                        fleet.getOrigin(), fleet.getDestination());
                return new ResponseEntity<>("Origin and destination are required", HttpStatus.BAD_REQUEST);
            }

            logger.debug("Validating fleet data before creation: {}", fleet);
            fleetService.createFleet(fleet);
            logger.info("Successfully created fleet with ID: {}", fleet.getFleetId());
            return new ResponseEntity<>("Fleet created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error occurred while creating fleet: {}", e.getMessage(), e);
            return new ResponseEntity<>("Failed to create fleet: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{fleetId}")
    public ResponseEntity<String> updateFleet(@PathVariable("fleetId") Long fleetId, @RequestBody Fleet fleetDetails) {
        logger.info("Request received to update fleet with ID: {}", fleetId);
        logger.debug("Update data: {}", fleetDetails);
        try {
            Fleet existingFleet = fleetService.getFleetById(fleetId);
            if (existingFleet == null) {
                logger.warn("Update failed - fleet not found with ID: {}", fleetId);
                return new ResponseEntity<>("Fleet not found with id: " + fleetId, HttpStatus.NOT_FOUND);
            }

            logger.debug("Existing fleet data before update: {}", existingFleet);
            fleetService.updateFleet(fleetId, fleetDetails);
            logger.info("Successfully updated fleet with ID: {}", fleetId);
            return new ResponseEntity<>("Fleet updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while updating fleet with ID {}: {}", fleetId, e.getMessage(), e);
            return new ResponseEntity<>("Failed to update fleet: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{fleetId}")
    public ResponseEntity<String> deleteFleet(@PathVariable("fleetId") Long fleetId) {
        logger.info("Request received to delete fleet with ID: {}", fleetId);
        try {
            Fleet existingFleet = fleetService.getFleetById(fleetId);
            if (existingFleet == null) {
                logger.warn("Delete failed - fleet not found with ID: {}", fleetId);
                return new ResponseEntity<>("Fleet not found with id: " + fleetId, HttpStatus.NOT_FOUND);
            }

            logger.debug("Deleting fleet: {}", existingFleet);
            fleetService.deleteFleet(fleetId);
            logger.info("Successfully deleted fleet with ID: {}", fleetId);
            return new ResponseEntity<>("Fleet deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while deleting fleet with ID {}: {}", fleetId, e.getMessage(), e);
            return new ResponseEntity<>("Failed to delete fleet: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Fleet>> searchFleets(
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination) {
        logger.info("Request received to search fleets with origin: '{}' and destination: '{}'", origin, destination);
        try {
            List<Fleet> allFleets = fleetService.getAllFleets();
            logger.debug("Total fleets before filtering: {}", allFleets.size());

            List<Fleet> filteredFleets = allFleets.stream()
                    .filter(fleet -> origin == null || fleet.getOrigin().toLowerCase().contains(origin.toLowerCase()))
                    .filter(fleet -> destination == null || fleet.getDestination().toLowerCase().contains(destination.toLowerCase()))
                    .toList();

            logger.info("Search completed. Found {} fleets matching criteria", filteredFleets.size());
            return new ResponseEntity<>(filteredFleets, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred during fleet search: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getFleetCount() {
        logger.info("Request received to get fleet count");
        try {
            List<Fleet> fleets = fleetService.getAllFleets();
            int count = fleets.size();
            logger.info("Fleet count retrieved: {}", count);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while getting fleet count: {}", e.getMessage(), e);
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}