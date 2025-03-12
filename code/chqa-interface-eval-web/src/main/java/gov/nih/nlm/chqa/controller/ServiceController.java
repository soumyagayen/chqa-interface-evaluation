/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gov.nih.nlm.chqa.controller;

import gov.nih.nlm.chqa.model.experiment.ExperimentResults;
import gov.nih.nlm.chqa.service.ExperimentService;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author gayens
 */

@RestController
public class ServiceController 
{
 
    @Autowired
    private ExperimentService experimentService;
  
    static final Logger LOG = LoggerFactory.getLogger(ServiceController.class);

    @GetMapping(value="/experiment/info", produces = MediaType.APPLICATION_JSON_VALUE )
    public ExperimentResults getExperimentInfo(  
            @RequestParam(required = false, defaultValue = "-1" ) int slider, 
            @RequestParam(required = false, defaultValue = "-1" ) int type, 
            @RequestParam(required = false, defaultValue = "-1" ) int showIcon, 
            HttpServletRequest request )
    {
        String user = (String)request.getSession().getAttribute("user");
        int userID  = Integer.parseInt( user.split(":")[0] );
        try
        {
            ExperimentResults result = experimentService.getNextQuestion(user);
            
            String view = ExperimentService.LATIN_SQUARE[userID-1][result.getQuestionID()-1];
            String[] viewParts = view.split(",");
            
            if( type == -1 )
            {
                result.setTaskType( Integer.parseInt( viewParts[0]) );
            }
            else
            {
                result.setTaskType( type );
            }
            
            if( slider == -1)
            {
                slider = Integer.parseInt( viewParts[1]);
            }
            if( slider == 0)
            {
                result.setSlider(false);
            }
            else if( slider == 1)
            {
                result.setSlider(true);
            }
            
            if( showIcon == -1 )
            {
                showIcon = Integer.parseInt( viewParts[2]);
            }
            result.setShowIcon( showIcon );                        
            
            return result;
        }
        catch( Exception e )
        {
            LOG.error("ExperimentResults for user : "+user , e );
            return null;
        }
    }
    
    @RequestMapping(value = "/image/{questionid}/{imageid}",method= RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType
        (@PathVariable String questionid, @PathVariable String imageid) throws Exception 
    {
        InputStream in = new ByteArrayInputStream( experimentService.getImage( questionid, imageid ) );
        return IOUtils.toByteArray(in);
    }
        
    @PostMapping(value="/experiment/update", produces = MediaType.APPLICATION_JSON_VALUE )
    public boolean updateExperimentInfo( @RequestParam String state, 
            @RequestParam String jsonData,  HttpServletRequest request ) throws Exception
    {
        
        String user = (String)request.getSession().getAttribute("user");
        try
        {
            experimentService.updateState(user, state, jsonData);
        }
        catch( Exception e )
        {
            LOG.error("Error in updating state", e);
            return false;
        }
        return true;
    }
    
    @PostMapping(value="/experiment/event", produces = MediaType.APPLICATION_JSON_VALUE )
    public boolean updateExperimentEvent( @RequestParam String jsonData,  HttpServletRequest request ) throws Exception
    {
        String user = (String)request.getSession().getAttribute("user");        
        experimentService.updateEvent(user, jsonData);        
        return true;
    }

    
}
