package Schedule.controller;

import Schedule.model.CompanyModel;
import Schedule.util.mapper.CompanyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import Schedule.service.CompanyService;

@RestController
@RequestMapping("/work_schedule/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService service;

    @PostMapping("/add")
    private String create(@ModelAttribute("company") CompanyModel companyModel) {
        service.create(CompanyMapper.modelToDto(companyModel));
        return "redirect:/work_schedule/company";
    }

    @GetMapping("/add")
    private String createForm(Model model) {
        model.addAttribute("company", new CompanyModel());
        return "company-form";
    }

    @DeleteMapping("/delete/{id}")
    private String deleteById(Long id) {
        service.deleteById(id);
        return "redirect:/work_schedule/company";
    }

    @DeleteMapping("/delete_all")
    private String deleteAll() {
        service.deleteAll();
        return "redirect:/work_schedule/company";
    }

    @GetMapping("/employee/{employeeId}")
    private String getByEmployeeId(@PathVariable Long employeeId, Model model) {
        model.addAttribute("company", service.getByEmployeeId(employeeId));
        return "company-get-by-employee";
    }

    @GetMapping("/{id}")
    private String getById(@PathVariable Long id, Model model) {
        model.addAttribute("company", service.getById(id));
        return "company-get-by-id";
    }

    @GetMapping()
    private String getAll(Model model) {
        model.addAttribute("all_company", service.getAll());
        return "company-list";
    }

    @PostMapping("/update/{id}")
    private String update(@PathVariable Long id, @ModelAttribute("company") CompanyModel companyModel) {
        service.update(id, CompanyMapper.modelToDto(companyModel));
        return "redirect:/work_schedule/company";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        CompanyModel companyModel = CompanyMapper.dtoToModel(service.getById(id));
        if (companyModel == null) return "redirect:/contacts";

        model.addAttribute("company", companyModel);
        return "company-form";
    }
}
