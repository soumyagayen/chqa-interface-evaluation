/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gov.nih.nlm.chqa.service;

import gov.nih.nlm.chqa.model.experiment.UserBase;
import gov.nih.nlm.chqa.model.experiment.UserBaseRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gayens
 */

@Service
public class H2ExperimentService {
    
    @Autowired
    private UserBaseRepository userBaseRepository;
    
    public UserBase getUserBase( Long id )
    {
        
        Optional<UserBase> users = (Optional<UserBase>)userBaseRepository.findById(id);
        if( users != null && users.isPresent() )
        {
            return users.get();
        }
        
        return null;
    }
    
    public void save( UserBase user )
    {
        userBaseRepository.save( user );
    }
}
