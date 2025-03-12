/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gov.nih.nlm.chqa.controller;

import gov.nih.nlm.chqa.model.Result;
import gov.nih.nlm.chqa.model.Search;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 *
 * @author gayens
 */

@Controller
public class FrontController 
{
    static final Logger LOG = LoggerFactory.getLogger( FrontController.class );

    @Value("${eval.path}")
    private String evalPath;
   
    @GetMapping("/")
    public String home( Model model)
    {
        Search search = new Search();
        model.addAttribute("home",search);                              
        return "home";
    }
    
    @GetMapping("/home")
    public String search( Model model)
    {
        Search search = new Search();
        model.addAttribute("search",search);                              
        return "search";
    }
    
    @RequestMapping( value = "/submiteval", method = RequestMethod.POST)
    public String search( HttpServletRequest request ) throws Exception
    {
        String eval0 = (String)request.getParameter("eval_0");
        String eval1 = (String)request.getParameter("eval_1");
        String eval2 = (String)request.getParameter("eval_2");
        String eval3 = (String)request.getParameter("eval_3");
        String eval4 = (String)request.getParameter("eval_4");
        String eval5 = (String)request.getParameter("eval_5");
        String eval6 = (String)request.getParameter("eval_6");
        String eval7 = (String)request.getParameter("eval_7");
        String eval8 = (String)request.getParameter("eval_8");
        String eval9 = (String)request.getParameter("eval_9");
        String eval10 = (String)request.getParameter("eval_10");
        String eval11 = (String)request.getParameter("eval_11");
        String eval12 = (String)request.getParameter("eval_12");
        String eval13 = (String)request.getParameter("eval_13");
        String eval14 = (String)request.getParameter("eval_14");
        String eval15 = (String)request.getParameter("eval_15");
        String eval16 = (String)request.getParameter("eval_16");
        String eval17 = (String)request.getParameter("eval_17");
        String eval18 = (String)request.getParameter("eval_18");
        String eval19 = (String)request.getParameter("eval_19");
        
        
        String answer0 = (String)request.getParameter("answer_0");
        String answer1 = (String)request.getParameter("answer_1");
        String answer2 = (String)request.getParameter("answer_2");
        String answer3 = (String)request.getParameter("answer_3");
        String answer4 = (String)request.getParameter("answer_4");
        String answer5 = (String)request.getParameter("answer_5");
        String answer6 = (String)request.getParameter("answer_6");
        String answer7 = (String)request.getParameter("answer_7");
        String answer8 = (String)request.getParameter("answer_8");
        String answer9 = (String)request.getParameter("answer_9");
        String answer10 = (String)request.getParameter("answer_10");
        String answer11 = (String)request.getParameter("answer_11");
        String answer12 = (String)request.getParameter("answer_12");
        String answer13 = (String)request.getParameter("answer_13");
        String answer14 = (String)request.getParameter("answer_14");
        String answer15 = (String)request.getParameter("answer_15");
        String answer16 = (String)request.getParameter("answer_16");
        String answer17 = (String)request.getParameter("answer_17");
        String answer18 = (String)request.getParameter("answer_18");
        String answer19 = (String)request.getParameter("answer_19");
        
        

        
        String question = (String)request.getParameter("question");
        
        String text = (String)request.getParameter("text");
        String textWeight = (String)request.getParameter("textWeight");
        String title = (String)request.getParameter("title");
        String titleWeight = (String)request.getParameter("titleWeight");
        String concept = (String)request.getParameter("concept");
        String conceptWeight = (String)request.getParameter("conceptWeight");
        String textType = (String)request.getParameter("textType");
        String textTypeWeight = (String)request.getParameter("textTypeWeight");
        String titleType = (String)request.getParameter("titleType");
        String titleTypeWeight = (String)request.getParameter("titleTypeWeight");
        String conceptType = (String)request.getParameter("conceptType");
        String conceptTypeWeight = (String)request.getParameter("conceptTypeWeight");
        String conceptID = (String)request.getParameter("conceptID");
                
        StringBuilder sb = new StringBuilder();
        
        sb.append("{\"question\":\"").append( question ).append("\",");
        sb.append("\"conceptID\":\"").append( conceptID ).append("\",");
        
        sb.append("\"evalauation\":[");
        
        sb.append("{\"eval\":\"").append( eval0 ).append("\",");
        sb.append("\"id\":\"").append( answer0 ).append("\"},");
        
        sb.append("{\"eval\":\"").append( eval1 ).append("\",");
        sb.append("\"id\":\"").append( answer1 ).append("\"},");
        
        sb.append("{\"eval\":\"").append( eval2 ).append("\",");
        sb.append("\"id\":\"").append( answer2 ).append("\"},");
        
        sb.append("{\"eval\":\"").append( eval3 ).append("\",");
        sb.append("\"id\":\"").append( answer3 ).append("\"},");
        
        sb.append("{\"eval\":\"").append( eval4 ).append("\",");
        sb.append("\"id\":\"").append( answer4 ).append("\"},");
        
        sb.append("{\"eval\":\"").append( eval5 ).append("\",");
        sb.append("\"id\":\"").append( answer5 ).append("\"},");
        
        sb.append("{\"eval\":\"").append( eval6 ).append("\",");
        sb.append("\"id\":\"").append( answer6 ).append("\"},");
        
        sb.append("{\"eval\":\"").append( eval7 ).append("\",");
        sb.append("\"id\":\"").append( answer7 ).append("\"},");
        
        sb.append("{\"eval\":\"").append( eval8 ).append("\",");
        sb.append("\"id\":\"").append( answer8 ).append("\"},");
        
        sb.append("{\"eval\":\"").append( eval9 ).append("\",");
        sb.append("\"id\":\"").append( answer9 ).append("\"},");
        
        
        
        
        
        //////
        sb.append("{\"eval\":\"").append( eval10 ).append("\",");
        sb.append("\"id\":\"").append( answer10 ).append("\"},");
        
        sb.append("{\"eval\":\"").append( eval11 ).append("\",");
        sb.append("\"id\":\"").append( answer11 ).append("\"},");
        
        sb.append("{\"eval\":\"").append( eval12 ).append("\",");
        sb.append("\"id\":\"").append( answer12 ).append("\"},");
        
        sb.append("{\"eval\":\"").append( eval13 ).append("\",");
        sb.append("\"id\":\"").append( answer13 ).append("\"},");
        
        sb.append("{\"eval\":\"").append( eval14 ).append("\",");
        sb.append("\"id\":\"").append( answer14 ).append("\"},");
        
        sb.append("{\"eval\":\"").append( eval15 ).append("\",");
        sb.append("\"id\":\"").append( answer15 ).append("\"},");
        
        sb.append("{\"eval\":\"").append( eval16 ).append("\",");
        sb.append("\"id\":\"").append( answer16 ).append("\"},");
        
        sb.append("{\"eval\":\"").append( eval17 ).append("\",");
        sb.append("\"id\":\"").append( answer17 ).append("\"},");
        
        sb.append("{\"eval\":\"").append( eval18 ).append("\",");
        sb.append("\"id\":\"").append( answer18 ).append("\"},");
        
        sb.append("{\"eval\":\"").append( eval19 ).append("\",");
        sb.append("\"id\":\"").append( answer19 ).append("\"}");
        
        
        
        ////
        
        sb.append("],");
        
        sb.append("\"text\":\"").append( text ).append("\",");
        sb.append("\"textWeight\":\"").append( textWeight ).append("\",");
        sb.append("\"title\":\"").append( title ).append("\",");
        sb.append("\"titleWeight\":\"").append( titleWeight ).append("\",");
        sb.append("\"concept\":\"").append( concept ).append("\",");
        sb.append("\"conceptWeight\":\"").append( conceptWeight ).append("\",");
        sb.append("\"textType\":\"").append( textType ).append("\",");
        sb.append("\"textTypeWeight\":\"").append( textTypeWeight ).append("\",");
        sb.append("\"titleType\":\"").append( titleType ).append("\",");
        sb.append("\"titleTypeWeight\":\"").append( titleTypeWeight ).append("\",");
        sb.append("\"conceptType\":\"").append( conceptType ).append("\",");
        sb.append("\"conceptTypeWeight\":\"").append( conceptTypeWeight ).append("\",");
        
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        sb.append("\"ts\":\"").append( sdf.format(timestamp) ).append("\"");
        
        sb.append("}\n");
        
        LOG.info(".... Saving the string to path : " + evalPath + "\n" + sb.toString());
        
        try 
        {
            FileWriter fw = new FileWriter(evalPath, true);
            fw.write(sb.toString());
            fw.close();
        }
        catch(IOException e) 
        {
            LOG.error("Error in saving file" , e );
        }
        
        return "redirect:home";
    }
            
}
