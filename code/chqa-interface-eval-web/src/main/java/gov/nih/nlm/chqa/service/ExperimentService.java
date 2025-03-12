/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gov.nih.nlm.chqa.service;

import gov.nih.nlm.chqa.model.Result;
import gov.nih.nlm.chqa.model.experiment.ExperimentResults;
import gov.nih.nlm.chqa.model.experiment.HealthLiteracy;
import gov.nih.nlm.chqa.model.experiment.UserBase;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * @author gayens
 */

@Service
public class ExperimentService {
    
    @Value("${experiment.data.path}")
    private String EXPERIMENT_DATA_PATH;
    
    @Value("${experiment.data.image.url}")
    private String EXPERIMENT_DATA_IMAGE_URL;
    
    @Autowired
    H2ExperimentService h2Service;
    
    public static String LATIN_SQUARE[][] = {
                                                {"1,1,1","2,1,1","2,0,2","1,0,1","1,0,2","2,0,1","2,1,2","1,1,2"},
                                                {"2,1,1","1,0,1","1,1,1","2,0,1","2,0,2","1,1,2","1,0,2","2,1,2"},
                                                {"1,0,1","2,0,1","2,1,1","1,1,2","1,1,1","2,1,2","2,0,2","1,0,2"},
                                                {"2,0,1","1,1,2","1,0,1","2,1,2","2,1,1","1,0,2","1,1,1","2,0,2"},
                                                {"1,1,2","2,1,2","2,0,1","1,0,2","1,0,1","2,0,2","2,1,1","1,1,1"},
                                                {"2,1,2","1,0,2","1,1,2","2,0,2","2,0,1","1,1,1","1,0,1","2,1,1"},
                                                {"1,0,2","2,0,2","2,1,2","1,1,1","1,1,2","2,1,1","2,0,1","1,0,1"},
                                                {"2,0,2","1,1,1","1,0,2","2,1,1","2,1,2","1,0,1","1,1,2","2,0,1"}
                                            };
    
    public boolean saveHealthLiteracy( String user, HealthLiteracy healthLiteracy ) throws Exception
    {
        String[] userSplit = user.split(":");
        Long userID  = Long.parseLong(userSplit[0] );
        String userName = "";
        if( userSplit.length == 2 )
        {
            userName = userSplit[1];
        }
        
        StringBuilder healthLiteracyFileText = new StringBuilder();
        healthLiteracyFileText.append( healthLiteracy.getQuestion1()).append("\n");
        healthLiteracyFileText.append( healthLiteracy.getQuestion2()).append("\n");
        healthLiteracyFileText.append( healthLiteracy.getQuestion3()).append("\n");
        healthLiteracyFileText.append( healthLiteracy.getQuestion4()).append("\n");
        healthLiteracyFileText.append( healthLiteracy.getQuestion5()).append("\n");
        healthLiteracyFileText.append( healthLiteracy.getQuestion6()).append("\n");
        healthLiteracyFileText.append( healthLiteracy.getQuestion7()).append("\n");
        healthLiteracyFileText.append( healthLiteracy.getQuestion8()).append("\n");
        healthLiteracyFileText.append( healthLiteracy.getQuestion9()).append("\n");
        healthLiteracyFileText.append( healthLiteracy.getQuestion10()).append("\n");
        healthLiteracyFileText.append( healthLiteracy.getQuestion11()).append("\n");
        healthLiteracyFileText.append( healthLiteracy.getQuestion12()).append("\n");
        healthLiteracyFileText.append( healthLiteracy.getQuestion13()).append("\n");
        healthLiteracyFileText.append( healthLiteracy.getQuestion14()).append("\n");
        healthLiteracyFileText.append( healthLiteracy.getQuestion15()).append("\n");
        healthLiteracyFileText.append( healthLiteracy.getQuestion16()).append("\n");
        healthLiteracyFileText.append( healthLiteracy.getQuestion17()).append("\n");
        healthLiteracyFileText.append( healthLiteracy.getQuestion18()).append("\n");
        healthLiteracyFileText.append( healthLiteracy.getQuestion19()).append("\n");
        healthLiteracyFileText.append( healthLiteracy.getQuestion20()).append("\n");

        String healthLiteracyFilePath = EXPERIMENT_DATA_PATH + File.separator + "HealthLiteracy" + 
              File.separator +  userName + ".txt";
        
        File healthLiteracyFile = new File( healthLiteracyFilePath );
        healthLiteracyFile.getParentFile().mkdirs();
        
        FileUtils.writeByteArrayToFile(healthLiteracyFile, healthLiteracyFileText.toString().getBytes() );
        
        UserBase userBase = getUserBase(user);
        userBase.setStatus(0);
        h2Service.save(userBase);
        
        return true;
    }
    
    public boolean updateEvent( String user, String eventData) throws Exception
    {
        String[] userSplit = user.split(":");
        Long userID  = Long.parseLong(userSplit[0] );
        String userName = "";
        if( userSplit.length == 2 )
        {
            userName = userSplit[1];
        }
        
        UserBase userBase = h2Service.getUserBase(userID);
        
        String eventJson = EXPERIMENT_DATA_PATH + File.separator + "event" + 
              File.separator +  userName + ".json";
        
        File eventFile = new File( eventJson );
        /*
        if( eventFile.exists() )
        {
            for( String line : FileUtils.readLines(eventFile, "UTF-8") ) 
            {
                String[] parts = line.split(":");
                        
            }
            
        
        }
        */
        
        
        eventData += ":" + userBase.getQestionID() + ":" + userBase.getStatus() + "\n";
        FileUtils.write(eventFile, eventData, "UTF-8", true);
        
        
        
        return true;
        
    }
    
    public UserBase getUserBase( String user )
    {
        String[] userSplit = user.split(":");
        Long userID  = Long.parseLong(userSplit[0] );
        String userName = "";
        if( userSplit.length == 2 )
        {
            userName = userSplit[1];
        }
        
        UserBase userBase = h2Service.getUserBase(userID);
        if( userBase == null )
        {
            userBase = new UserBase();
            userBase.setUserID(userID);
            userBase.setQestionID( 1 );
            userBase.setStatus(-1);
            userBase.setUserName(userName);
            
            
            h2Service.save(userBase);
            
        }
        
        return userBase;
    }
    
    public ExperimentResults getNextQuestion(String user ) throws Exception
    {
        
        UserBase userBase =  getUserBase( user );
        ExperimentResults experimentResults = load( userBase.getQestionID() );        
        experimentResults.setState( userBase.getStatus() );        
        return experimentResults;
    }
    
    @Cacheable("getImageBytes")
    public byte[] getImage(String questionID, String imageName) throws Exception
    {
        String questionFolder = EXPERIMENT_DATA_PATH + File.separator + 
                "questions" + File.separator + 
                questionID + File.separator +
                "image" + File.separator + imageName;
        
        
        File file = new File( questionFolder );
        
        if( file.exists() )
        {
            return FileUtils.readFileToByteArray(file);
        }
        else
        {
            return null;
        }
        
        
    }
    
    /*
    State 1 : Seen the question
    State 2: Submitted the evalaution
    State 3: Submitted the survey, upto next question.
    */
    public void updateState(String user, String state, String jsonData  ) throws Exception
    {
        
        UserBase userBase =  getUserBase( user );                
        userBase.setStatus( Integer.parseInt(state) );
        
        String resultFolder = EXPERIMENT_DATA_PATH + File.separator + 
                "results" + File.separator + 
                userBase.getQestionID();                
        
        if( state.equals("2") )
        {
            String taskResultFilePath = resultFolder + File.separator +
                "task" + File.separator + userBase.getUserName() + ".json";
            
            File taskResultFile = new File( taskResultFilePath );
            taskResultFile.getParentFile().mkdirs();
            
            FileUtils.writeByteArrayToFile(taskResultFile, jsonData.getBytes() );
            
            ExperimentResults experimentResults = load( userBase.getQestionID() );
            if( experimentResults.getSurvey() == null || experimentResults.getSurvey().size() == 0 )
            {
                userBase.setQestionID( userBase.getQestionID() + 1 );
                userBase.setStatus( 0 );
            }

        }
        else if( state.equals("3") )
        {
            String surveyResultFilePath = resultFolder + File.separator +
                "survey" + File.separator + userBase.getUserName() + ".json";
            
            File surveyResultFile = new File( surveyResultFilePath );
            surveyResultFile.getParentFile().mkdirs();
            
            FileUtils.writeByteArrayToFile(surveyResultFile, jsonData.getBytes() );
            
            userBase.setQestionID( userBase.getQestionID() + 1 );
            userBase.setStatus( 0 );
        }
        
        
        h2Service.save(userBase);
        
    }
    
    @Cacheable("getQuestionFocus")
    public ExperimentResults load(int questionID ) throws Exception
    {
        String questionFolder = EXPERIMENT_DATA_PATH + File.separator + "questions" + File.separator + questionID;
        String questionJsonFile = questionFolder + File.separator + "data.json";
        String imageURLPrefix = EXPERIMENT_DATA_IMAGE_URL + "/" + questionID;
           
        
        JSONParser parser = new JSONParser();
        JSONObject questionJsonObject = null;
        
        try
        {
            questionJsonObject = (JSONObject)parser.parse( new FileReader( questionJsonFile ) );         
        }
        catch( FileNotFoundException e )
        {
            return null;
        }
        
        JSONArray answerJsonArray = (JSONArray)questionJsonObject.get("answers");
        
        List<Result> answers = new ArrayList<>();
        for( Object object : answerJsonArray.toArray() )
        {
            JSONObject answerJSONObject = (JSONObject)object;
            JSONArray refJSONArray = (JSONArray)answerJSONObject.get("ref");
            
            Result answer = new Result();
            answer.setText( (String)answerJSONObject.get("text") );
            
            List<Result> consumerReferenceList = new ArrayList<>();
            for( Object refObject : refJSONArray.toArray() )
            {
                JSONObject refJSONObject = (JSONObject)refObject;
                if(refJSONObject != null )
                {
                    Result consumerReference = new Result();
                    consumerReference.setTitle( (String)refJSONObject.get("title") );
                    consumerReference.setText( (String)refJSONObject.get("text") );
                    consumerReference.setSource( (String)refJSONObject.get("corpus") );
                    consumerReference.setUrl( (String)refJSONObject.get("url") );
                    consumerReferenceList.add(consumerReference); 
                }
            }
            answer.setRefrences( consumerReferenceList );
            
            answers.add(answer);

        }
        
        JSONArray imageJsonArray = (JSONArray)questionJsonObject.get("images");
        List<Result> images = new ArrayList<>();
        for( Object object : imageJsonArray.toArray() )
        {
            JSONObject imageJSONObject = (JSONObject)object;
            
            Result image = new Result();
            image.setTitle( (String)imageJSONObject.get("title") );
            image.setText( (String)imageURLPrefix + "/" + imageJSONObject.get("name") + ".jpg" );
            image.setUrl( (String)imageJSONObject.get("url") );
            
            images.add(image);
        }
        
        
        JSONArray relatedInfoJsonArray = (JSONArray)questionJsonObject.get("related-info");
        List<Result> consumerReferenceList = new ArrayList<>();
        for( Object object : relatedInfoJsonArray.toArray() )
        {
            JSONObject refJSONObject = (JSONObject) object;
            if (refJSONObject != null) 
            {
                Result consumerReference = new Result();
                consumerReference.setTitle((String) refJSONObject.get("title"));
                consumerReference.setText((String) refJSONObject.get("text"));
                consumerReference.setSource((String) refJSONObject.get("corpus"));
                consumerReference.setUrl((String) refJSONObject.get("url"));
                consumerReferenceList.add(consumerReference);
            }
        }
        
        
        JSONArray clinicalEvidenceJsonArray = (JSONArray)questionJsonObject.get("clinical-evidence");
        List<Result> clinicalEvidenceList = new ArrayList<>();
        for( Object object : clinicalEvidenceJsonArray.toArray() )
        {
            JSONObject clinicalEvidenceJSONObject = (JSONObject)object;
            
            Result clinicalEvidence = new Result();
            clinicalEvidence.setTitle( (String)clinicalEvidenceJSONObject.get("title") );
            clinicalEvidence.setText((String)clinicalEvidenceJSONObject.get("text") );
            clinicalEvidence.setUrl( (String)clinicalEvidenceJSONObject.get("url") );
            
            clinicalEvidenceList.add( clinicalEvidence );
        }
        
        JSONArray tasksJsonArray = (JSONArray)questionJsonObject.get("task");
        List<Result> tasksList = new ArrayList<>();
        for( Object object : tasksJsonArray.toArray() )
        {
            JSONObject tasksJSONObject = (JSONObject)object;
            
            Result task = new Result();
            task.setTitle( (String)tasksJSONObject.get("question") );
            JSONArray taskOptionsJsonArray = (JSONArray)tasksJSONObject.get("options");
            List<String> options = new ArrayList();
            for( Object optionsObj : taskOptionsJsonArray.toArray() )
            {
                options.add((String)optionsObj);                
            }
            task.setOptions(options);
            
            tasksList.add( task );
        }
        
        JSONArray surverysJsonArray = (JSONArray)questionJsonObject.get("survey");
        List<Result> surveysList = null;
        if( surverysJsonArray !=  null )
        {
            surveysList = new ArrayList<>();
            for( Object object : surverysJsonArray.toArray() )
            {
                JSONObject surveysJSONObject = (JSONObject)object;

                Result survey = new Result();
                survey.setTitle( (String)surveysJSONObject.get("question") );
                JSONArray surveyOptionsJsonArray = (JSONArray)surveysJSONObject.get("options");
                List<String> options = new ArrayList();
                for( Object optionsObj : surveyOptionsJsonArray.toArray() )
                {
                    options.add((String)optionsObj);                
                }
                survey.setOptions(options);

                surveysList.add( survey );
            }
        }
        
        
                
        ExperimentResults result= new ExperimentResults();  
        result.setIntroduction((String)questionJsonObject.get("introduction") );
        result.setQuestion( (String)questionJsonObject.get("question") );     
        result.setAnswers(answers);
        result.setRelatedInfo( consumerReferenceList );
        result.setRelatedImages(images);
        result.setRelatedResearch( clinicalEvidenceList );
        result.setTask( tasksList );
        result.setSurvey(surveysList);
        result.setQuestionID(questionID);
        
        
        return result;
    }
}
