/**
 * 
 */
package br.com.rpires.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Rodrigo Pires
 *
 */
@Controller
public class CorridasController {
    
    @RequestMapping("/Corridas")
    public String index(ModelMap model) {
    	String nome = "Rodrigao";
    	model.addAttribute("nome", nome);
        return "index";
    }
    
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String showLoginPage(ModelMap model){
        return "index";
    }
    
}