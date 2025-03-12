/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gov.nih.nlm.chqa.controller;


import gov.nih.nlm.chqa.model.experiment.HealthLiteracy;
import gov.nih.nlm.chqa.service.ExperimentService;
import gov.nih.nlm.chqa.service.H2ExperimentService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author gayens
 */

@Controller
public class ExperimentController 
{    
    @Autowired
    private H2ExperimentService h2ExperimentService;
    
    @Autowired
    private ExperimentService experimentService;        
    
    static final Logger LOG = LoggerFactory.getLogger( ExperimentController.class );        

    @GetMapping("/experiment/userselect")
    public String load( Model model)
    {
        return "experiment.userselect";
    }
    
    @PostMapping("/experiment/userselect")
    public String loadPost( Model model, HttpServletRequest request )
    {
        String user = (String)request.getParameter("user");        
        request.getSession().setAttribute("user", user);
        
        int userID  = Integer.parseInt( user.split(":")[0] );
        
        if( h2ExperimentService.getUserBase( Long.parseLong( userID+"" ) ) == null )
        {
            return "redirect:/experiment/healthliteracy";
        }
        else
        {
            return "redirect:/experiment/load";
        }
    }
    
    
    @GetMapping("/experiment/load")
    public String userselect( Model model, HttpServletRequest request)
    {
        if( request.getSession().getAttribute("user") == null )
        {
           return "redirect:/experiment/userselect";
        }
         
        String user = (String)request.getSession().getAttribute("user");
                
        LOG.info("loadPost  /experiment/load : " + user );                

        return "experiment.load";
    }
        
    @RequestMapping(value = "/experiment/healthliteracy", method = RequestMethod.GET)
    public String showHealthLiteracy( Model model, HttpServletRequest request )
    { 
        String user = (String)request.getSession().getAttribute("user");        
        if( user == null )
        {
            return "redirect:/experiment/userselect";
        }
        
        model.addAttribute("healthLiteracy", new HealthLiteracy());        
        return "experiment.healthliteracy";
    }
    
    @RequestMapping(value = "/experiment/healthliteracy", method = RequestMethod.POST )
    public String submitHealthLiteracy( Model model,
            @Valid HealthLiteracy healthLiteracy, 
            BindingResult bindingResult,
            HttpServletRequest request) throws Exception
    {  
        
        String user = (String)request.getSession().getAttribute("user");        
        if( user == null )
        {
            return "redirect:/experiment/userselect";
        }
        
        if (bindingResult.hasErrors())
        {
            healthLiteracy.setError("Please answer all questions");
            model.addAttribute("healthLiteracy", healthLiteracy );        
            return "experiment.healthliteracy";
        }
        else
        {
            
            if( experimentService.saveHealthLiteracy(user, healthLiteracy) )
            {
                LOG.info("");
                return "redirect:/experiment/load";
            }
            else
            {
                return "experiment.healthliteracy";
            }
            
        }                
        
    }
}
