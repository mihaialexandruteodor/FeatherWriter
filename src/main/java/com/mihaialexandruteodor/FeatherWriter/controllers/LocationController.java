package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.model.FWCharacter;
import com.mihaialexandruteodor.FeatherWriter.model.Location;
import com.mihaialexandruteodor.FeatherWriter.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LocationController {
    @Autowired
    private LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/newLocation")
    public String viewLocationPage(Model model) {

        Location location = new Location();
        model.addAttribute("location",location);
        return "location_creation";
    }

    @GetMapping("/updateLocation/{id}")
    public String updateLocation(@Valid @PathVariable( value = "id") int id, Model model) {

        Location location = locationService.getLocationById(id);
        model.addAttribute("location",location);
        return "edit_location";
    }

    @PostMapping("/saveLocation")
    public String saveLocation(@Valid @ModelAttribute("location") Location location) {
        locationService.saveLocation(location);
        return "redirect:/locationsPage";
    }

    @GetMapping("/deleteLocationProfile/{id}")
    public String deleteLocationProfile(@Valid @PathVariable (value = "id") int id) {
        locationService.deleteLocationById(id);
        return "redirect:/locationsPage";
    }
}
