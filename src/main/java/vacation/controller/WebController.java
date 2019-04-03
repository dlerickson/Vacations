package vacation.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import vacation.beans.Destination;
import vacation.repository.DestinationRepository;

@Controller
public class WebController {
	@Autowired
	DestinationRepository repo;

	@GetMapping("/viewAll")
	public String seeOurVacations(Model model) {
		model.addAttribute("destinations", repo.findAll());
		return "results";
	}

	@GetMapping("/inputDestination")
	public String addNewDestination(Model model) {
		Destination d = new Destination();
		model.addAttribute("newDestination", d);
		return "input";
	}

	@PostMapping("/inputDestination")
	public String addNewDestination(@ModelAttribute Destination d, Model model) {
		repo.save(d);
		model.addAttribute("destinations", repo.findAll());
		return "results";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Destination d = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

		model.addAttribute("destination", d);

		return "update";
	}

	@PostMapping("/update/{id}")
	public String updateVacation(@PathVariable("id") long id, @Valid Destination d, BindingResult result, Model model) {
		if (result.hasErrors()) {
			d.setId(id);
			return "update";
		}

		repo.save(d);
		model.addAttribute("destinations", repo.findAll());
		return "results";
	}

	@GetMapping("/delete/{id}")
	public String deleteVacation(@PathVariable("id") long id, Model model) {
		Destination d = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Vacation Id:" + id));
		repo.delete(d);
		model.addAttribute("destinations", repo.findAll());
		return "results";
	}

}
