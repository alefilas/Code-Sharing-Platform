package platform.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import platform.entity.Code;
import platform.service.CodeService;

import java.util.List;
import java.util.Map;


@RestController
public class PlatformController {

    @Autowired
    private CodeService codeService;

    @GetMapping(path = "/api/code/{id}")
    private ResponseEntity<Code> getApiCodeById(@PathVariable String id) {
        Code code = codeService.getCodeSnippet(id);
        return new ResponseEntity<>(code, HttpStatus.OK);
    }

    @GetMapping(path = "/api/code/latest")
    private ResponseEntity<List<Code>> getApiAllCodes() {
        List<Code> codes = codeService.getAllCodes();
        return new ResponseEntity<>(codes, HttpStatus.OK);
    }

    @GetMapping(path = "/code/{id}")
    private ModelAndView getHtmlCode(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("code", codeService.getCodeSnippet(id));
        modelAndView.setViewName("code");
        return modelAndView;
    }

    @GetMapping(path = "/code/new")
    private ModelAndView addNewHtmlCode() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new");
        return modelAndView;
    }

    @GetMapping(path = "/code/latest")
    private ModelAndView getHtmlAllCodes() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("codes", codeService.getAllCodes());
        modelAndView.setViewName("latest");
        return modelAndView;
    }

    @PostMapping(path = "api/code/new")
    private ResponseEntity<Map<String, String>> addJsonCode(@RequestBody Code code) {
        Map<String, String> map = codeService.addNewCode(code);
        return new ResponseEntity<>(map,  HttpStatus.OK);
    }
}
