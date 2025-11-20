package Schedule.controller;

import Schedule.dto.CompanyDto;
import Schedule.model.CompanyModel;
import Schedule.util.mapper.CompanyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import Schedule.service.CompanyService;

@Controller
@RequestMapping("/work_schedule/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService service;

    @PostMapping("/add")
    private String create(@ModelAttribute("company") CompanyModel companyModel) {
        service.create(CompanyMapper.modelToDto(companyModel));
        return "redirect:/work_schedule/company/list";
    }

    @GetMapping("/add")
    private String createForm(Model model) {
        model.addAttribute("company", new CompanyModel());
        return "company/company-form";
    }

    @PostMapping("/delete/{id}")
    private String deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/work_schedule/company/list";
    }

    @PostMapping("/delete_all")
    private String deleteAll() {
        service.deleteAll();
        return "redirect:/work_schedule/company/list";
    }

    @GetMapping("/employee/{employeeId}")
    private String getByEmployeeId(@PathVariable Long employeeId, Model model) {
        CompanyDto companyDto = service.getByEmployeeId(employeeId);
        model.addAttribute("companyId", companyDto.getId());
        model.addAttribute("company", companyDto);
        return "company/company-get-by";
    }

    @GetMapping("/{id}")
    private String getById(@PathVariable Long id, Model model) {
        model.addAttribute("companyId", id);
        model.addAttribute("company", service.getById(id));
        return "company/company-get-by";
    }

    @GetMapping("/list")
    private String getAll(Model model) {
        model.addAttribute("all_company", service.getAll());
        return "company/company-list";
    }

    @PostMapping("/update/{id}")
    private String update(@PathVariable Long id, @ModelAttribute("company") CompanyModel companyModel) {
        service.update(id, CompanyMapper.modelToDto(companyModel));
        return "redirect:/work_schedule/company/list";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        CompanyModel companyModel = CompanyMapper.dtoToModel(service.getById(id));
        if (companyModel == null) return "redirect:/work_schedule/company/list";

        model.addAttribute("company", companyModel);
        return "company/company-form";
    }
}
