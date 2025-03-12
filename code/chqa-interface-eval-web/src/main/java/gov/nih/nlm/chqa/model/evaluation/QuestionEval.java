/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gov.nih.nlm.chqa.model.evaluation;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author gayens
 */

@Getter
@Setter
public class QuestionEval {
    
    private String question;
    
    private String title;
    private String text;
    private String concept;
    
    private String titleWeight;
    private String textWeight;
    private String conceptWeight;
    
    
    private String titleType;
    private String textType;
    private String conceptType;
    
    private String titleTypeWeight;
    private String textTypeWeight;
    private String conceptTypeWeight;
    
    private String answerTitle;
            
    private String answerText;  
    
    private String eval;
}
