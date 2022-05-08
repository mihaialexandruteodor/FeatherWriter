package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.model.FWCharacter;
import com.mihaialexandruteodor.FeatherWriter.model.Location;
import com.mihaialexandruteodor.FeatherWriter.services.LocationService;
import com.mihaialexandruteodor.FeatherWriter.utlis.DataSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

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
        location.setUsername(DataSingleton.getInstance().getCurrentUser());
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

    @GetMapping("/locationProfile/{id}")
    public ModelAndView locationProfile(@Valid @PathVariable(value = "id") int id) {
        Location location = locationService.getLocationById(id);
        ModelAndView mv = new ModelAndView("location_profile");
        mv.addObject("location", location);
        return mv;
    }

    @GetMapping("/locationsPage")
    public ModelAndView locationsPage() {
        return loadLocationsPageData();
    }

    public ModelAndView loadLocationsPageData() {
        return findPaginated(1, "name", "asc");
    }

    @GetMapping("/locationsPage/page/{pageNo}")
    public ModelAndView findPaginated(@Valid @PathVariable(value = "pageNo") int pageNo,
                                      @Valid @RequestParam("sortField") String sortField,
                                      @Valid @RequestParam("sortDir") String sortDir) {

        ModelAndView model = new ModelAndView("locations_page");
        int pageSize = 12;

        Page<Location> page = locationService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Location> locationList = page.getContent();

        model.addObject("currentPage", pageNo);
        model.addObject("totalPages", page.getTotalPages());
        model.addObject("totalItems", page.getTotalElements());

        model.addObject("sortField", sortField);
        model.addObject("sortDir", sortDir);
        model.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addObject("locationList", locationList);

        return model;
    }
}
