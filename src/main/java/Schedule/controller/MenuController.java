package Schedule.controller;

import Schedule.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/work_schedule")
@RequiredArgsConstructor
public class MenuController {

    private final SearchService searchService;

    @GetMapping
    private String getMenu(Model model) {
        return "menu/menu";
    }

    //реализовать поиск
    private String search(String value) {

        return "";
    }

    private String searchForm(Model model) {
        return "";
    }
}
