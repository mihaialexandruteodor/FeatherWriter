package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.model.Location;
import com.mihaialexandruteodor.FeatherWriter.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LocationProfileController {

    @Autowired
    private LocationService locationService;

    @Autowired
    public LocationProfileController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locationProfile/{id}")
    public ModelAndView locationProfile(@Valid @PathVariable(value = "id") int id) {
        Location location = locationService.getLocationById(id);
        ModelAndView mv = new ModelAndView("location_profile");
        mv.addObject("location", location);
        return mv;
    }
}
