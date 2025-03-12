/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nih.nlm.chqa.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author gayens
 */

@Getter
@Setter
public class Search
{
    private String conceptID;
    
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
    
    private List<String> terms;
    private List<String> concepts;
    private List<Result> results;
   
    private List<List<Result>> multiCoreResults;
    private String width;

    
    private String action;
   
    private int start;
    private int end;
    
    private int totalResults;
    
    private String[] collections;
    private String[] selectedCollections;
    
    
    
    private String eval_0;
    private String eval_1;
    private String eval_2;
    private String eval_3;
    private String eval_4;
    private String eval_5;
    private String eval_6;
    private String eval_7;
    private String eval_8;
    private String eval_9;
    
    private String questionID_0;
    private String questionID_1;
    private String questionID_2;
    private String questionID_3;
    private String questionID_4;
    private String questionID_5;
    private String questionID_6;
    private String questionID_7;
    private String questionID_8;
    private String questionID_9;

    
    public Search()
    {
        this.start = 1;
        this.end = 50; 
        String[] collections = {
                "Consumer",
            "CORD-19"
//            "CancerGov",            
//            "CDC",
//            "DailyMed",
//            "GARD",
//            "GHR",
//            "GHR_Gene",
//            "MayoClinicDiseases",
//            "MPlusHealthTopics",
//            "NHLBI",
//            "NIAID",
//            "WomensHealth",
//            "WHO"
        };
        this.selectedCollections = collections;
        this.collections = collections;
    }
    
}
