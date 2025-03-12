/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gov.nih.nlm.chqa.model.experiment;

import gov.nih.nlm.chqa.model.Result;
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
public class ExperimentResults {
    
    private int questionID;
    private String question;
    private String introduction;
    private List<Result> relatedImages;
    private List<Result> relatedVideos;
    private List<Result> answers;
    private List<Result> relatedInfo;
    private List<Result> relatedResearch;    
    
    private List<Result> survey;    
    private List<Result> task;
    
    private int taskType;
    
    private boolean slider;    
    private int state;
    private int showIcon;
    
    
}
