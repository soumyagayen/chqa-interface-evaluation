/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nih.nlm.chqa.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author gayens
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Result
{

    private String id;
    private String title;
    private String text;
    private String url;
    private String pageTitle;
    private String source;
    private String parentText;
    private String topicText;
    private String ingredientText;
    private String textPieces;
    private String score;    
    private String evaluation;    
    private List<String> options;    
    private List<Result> refrences;

    
}
