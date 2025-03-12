
var answerSentenceTemplate_sentence = $(".answer-sentence-with-pt-info .sentence").get(0).outerHTML;
var answerSentenceTemplate_patientInfo = $(".answer-sentence-with-pt-info .patient-info").get(0).outerHTML;
var answerSentenceOnlyTemplate = $(".answer-sentence-only").get(0).outerHTML;
var clinicalEvidenceTemplate = $(".clinical-evidence").get(0).outerHTML;
var relatedInfoTemplate = $(".related-info").get(0).outerHTML;
var relatedImageTemplate = $(".media").get(0).outerHTML;
var relatedInfoModal = $(".related-info-modal").get(0).outerHTML;
var result = {};
result.event = [];
var startTime;
var loadResponse;

window.onclick = function(e) 
{
    var dateTime = new Date().toLocaleString();
    
    var timeStamp = Date.now();
    var timePassed = parseInt( (Date.now() / 1000) - startTime );
    
    var clickID = $(e.target).attr("click-id");
    var href = $(e.target).attr("href");
    
    
    
    if( href === undefined || !href.length )
    {
        href = $(e.target).parent().attr("href");                        
    }
    
    var event;
    if( href !== undefined && href.length )
    {
        event = timeStamp + ":" + dateTime + ":" + href;
    }    
    else if(  clickID !== undefined && clickID.length )
    {
        event = timeStamp + ":" + dateTime + ":" + clickID;
    }
    else
    {
        event = timeStamp + ":" + dateTime + ": UNKNOW CLICK";
    }
    result.event.push( event );

    $.ajax({
        type : "POST",
        url : "/chqa/experiment/event",
        data: {"jsonData": event },
        success: function( response )
        {
            console.log("Submitted the event : " + event );           
        }

    }); 
    
};



var loadAnswer = function( answers, taskType, showIcon )
{
    if( taskType === 1 )
    {
        $(".answer-list").hide();
        var answerText = "";
        for( var index in answers )
        {
            var className = "relatedinfo-click";           
            
           
            var answer = answers[index];
            var relatedInfoHTML = "";
            for (var refIndex in answer.refrences)
            {
                var relatedInfo = answer.refrences[refIndex];                
                var ptInfoHTML = answerSentenceTemplate_patientInfo;                
                ptInfoHTML = ptInfoHTML.replace("{{title}}", relatedInfo.title);
                if (relatedInfo.source.length > 0 && showIcon === 2)
                {
                    ptInfoHTML = ptInfoHTML.replace("{{img-src}}", "/chqa/images/source/" + relatedInfo.source + ".png");
                    ptInfoHTML = ptInfoHTML.replace("{{corpus}}", relatedInfo.source );

                }
                
                ptInfoHTML = ptInfoHTML.replace("{{url}}", relatedInfo.url);
                relatedInfoHTML += ptInfoHTML;
            }
            var modalHtml = relatedInfoModal;
                    modalHtml = modalHtml.replace("{{related-info-list}}", relatedInfoHTML ).
                    replace("{{related-info-modal-id}}", "related-info-modal-" + index );
            $(".results").append(modalHtml);
            
            answerText += "<span class=\""+ className +"\" relatedInfoModalId=\"related-info-modal-" + index + "\" showIcon=\""+ showIcon+"\" click-id=\"para-answer-sentence\" >"+answer.text+"</span>&nbsp;";
            
        }
        
        $(".answer .content").html( answerText );
        
        $(".relatedinfo-click").on("click",function( e ){
             
            var relatedInfoModalId = $(this).attr("relatedInfoModalId"); 
            let myModal = new bootstrap.Modal(
              document.getElementById(relatedInfoModalId),
              {}
            );
            myModal.show();
            
            
            var showIcon = $(this).attr("showIcon"); 
            if( showIcon === "1" )
            {
                $(".patient-info .icon").hide();
            }
            
        });
        
        
        $(".showRelatedInfo").on("mouseover",function( e ){
            
        });
            
        $(".showRelatedInfo").on("mouseleave",function( e ){
           
        });
    }
    else if( taskType === 2 )
    {
        //$(".related-info-list").hide();  
        //$(".related-info-heading").hide();
        
        $(".answer").hide();
        var allHTML = "";
        for( var index in answers )
        {
            var answerLineHTML = "";
            var answer = answers[index];
            var answerText = answer.text;
            
            var sentenceHTML = answerSentenceTemplate_sentence.replace("{{sentence}}", answerText );
            answerLineHTML += sentenceHTML+"<div>"; 
            for (var refIndex in answer.refrences)
            {
                var relatedInfo = answer.refrences[refIndex];                
                var ptInfoHTML = answerSentenceTemplate_patientInfo;                
                ptInfoHTML = ptInfoHTML.replace("{{title}}", relatedInfo.title);
                if (relatedInfo.source.length > 0 && showIcon === 2)
                {
                    ptInfoHTML = ptInfoHTML.replace("{{img-src}}", "/chqa/images/source/" + relatedInfo.source + ".png");
                    ptInfoHTML = ptInfoHTML.replace("{{corpus}}", relatedInfo.source);
                }
                ptInfoHTML = ptInfoHTML.replace("{{url}}", relatedInfo.url);
                answerLineHTML += ptInfoHTML;
            }
            answerLineHTML += "</div>";
            allHTML += "<div class=\"answer-sentence  answer-sentence-with-pt-info\">" + answerLineHTML + "</div>";
        }

        $(".answer-list").html( allHTML );
        
        if( showIcon === 1 )
        {
            $(".patient-info .icon").hide();
        }
    }
    
    
};

var loadImages = function( images )
{
    var allHTML = "";
    for( var index in images )
    {
        var image = images[index];
        var html =  relatedImageTemplate;
        html = html.replace("{{link}}", image.url );
        html = html.replace("{{link}}", image.url );
        html = html.replace("{{img-src}}", image.text );
        html = html.replace("{{corpus}}", image.text );
        html = html.replace("{{title}}", image.title );

        allHTML += html;
    }
    
    $(".imageAndVideo").html( allHTML );
};

var loadVideos = function( videos )
{
    
};

var loadRelatedInfo = function ( relatedInfoList )
{
    var allHTML = "";
    for (var index in relatedInfoList)
    {
        var html = relatedInfoTemplate;
        var relatedInfo = relatedInfoList[index];

        html = html.replace("{{title}}", relatedInfo.title);
        if (relatedInfo.source.length > 0)
        {
            html = html.replace("{{img-src}}", "/chqa/images/source/" + relatedInfo.source + ".png");
            html = html.replace("{{corpus}}", relatedInfo.source);
        }
        html = html.replace("{{url}}", relatedInfo.url);

        allHTML += html;
    }
    $(".related-info-list").html(allHTML);
};

var loadRelatedResesrch = function( relatedResearch )
{
    var allHTML = "";

    for( var index in relatedResearch )
    {
       var clinicalEvidence = relatedResearch[index];
       var html = clinicalEvidenceTemplate;
       html = html.replace("{{img-src}}", "/chqa/images/source/pubmed.png" );
       html = html.replace("{{corpus}}", "pubmed" );
       html = html.replace("{{url}}", clinicalEvidence.url );
       html = html.replace("{{title}}", clinicalEvidence.title );
       html = html.replace("{{text}}", clinicalEvidence.text );
       
       allHTML += html;
    }
    
    $(".clinical-evidence-list").html( allHTML );
};

var loadTask = function( dataArray, introduction )
{
    var html = "";
    for( var cnt in dataArray )
    {
        var data = dataArray[cnt];
        html += "<div class=\"question\" >";
        html += "</br><p>"+ data.title +"</p>";                
        for( var optionCnt in data.options )
        {
            var option = data.options[optionCnt];
            html += "<div class=\"option\" >";
            html += "<input type=\"radio\" id=\"" + 
                    optionCnt +"\" name=\"" +
                    cnt +"\" value=\"" +
                    option +"\" />" ;
            
            html += "<label for=\""+ optionCnt + "\" >" + option + "</label>";
            html += "</div>";
        }        
        html += "</div>";
    }
    
    $(".task .questions").html( html );    
    $(".task .introduction").html( introduction );
    
};

var loadSurvey = function( dataArray )
{
    var html = "";
    for( var cnt in dataArray )
    {
        var data = dataArray[cnt];
        html += "<div class=\"question\" >";
        html += "</br><p>"+ data.title +"</p>";                
        for( var optionCnt in data.options )
        {
            var option = data.options[optionCnt];
            html += "<div class=\"option\" >";
            html += "<input type=\"radio\" id=\"" + 
                    optionCnt +"\" name=\"" +
                    cnt +"\" value=\"" +
                    option +"\" />" ;
            
            html += "<label for=\""+ optionCnt + "\" >" + option + "</label>";
            html += "</div>";
        }        
        html += "</div>";
    }
    
    $(".survey .questions").html( html );
    
};

const list1 = document.querySelector(".type3 .imageAndVideo");
const list2 = document.querySelector(".type4 .imageAndVideo");

const item = document.querySelector(".media");
const itemWidth = item.offsetWidth;

var handleClick = function(direction) {
        
    console.log(" Handel logs :" + direction ); 
    
    // Based on the direction we call `scrollBy` with the item width we got earlier
    if(direction === "previous") {
      list1.scrollBy({ top: -itemWidth, behavior: "smooth" });
      list2.scrollBy({ top: -itemWidth, behavior: "smooth" });

    } else {
      list1.scrollBy({ top: itemWidth, behavior: "smooth" });
      list2.scrollBy({ top: itemWidth, behavior: "smooth" });

    }
  };

var loadData = function(  )
{
    const queryString = window.location.search;
    console.log(queryString);
    const urlParams = new URLSearchParams(queryString);

    
    var type = urlParams.get('type');
    var slider = urlParams.get('slider');
    var showIcon = urlParams.get('showIcon');
    $.ajax({
            type : "GET",
            url : "/chqa/experiment/info",
            data: {"type":type, "slider": slider, "showIcon": showIcon },
            success: function( response )
            {
                if( response === "" )
                {                    
                    $(".results").hide();
                    $(".task").hide();
                    $(".survey").hide();

                    window.setTimeout(function(){
                        alert("No more quesitons left. Thank you for your participation.");                    
                    }, 600);

                    return;
                }
                
                loadResponse = response;
                $(".type1-type2-type4").show();
                $(".type1-type3").show();
                if( !response.slider )
                {
                    if( response.taskType === 1  )
                    {
                        $(".type1").show();
                        $(".type2").hide();
                        $(".type3").hide();
                        $(".type4").hide();
                    }
                    else
                    {
                        $(".type1").hide();
                        $(".type2").show();
                        $(".type3").hide();
                        $(".type4").hide();
                        $(".type1-type3").hide();
                    }
                    $(".type1-type2").show();
                    $(".type3-type4").hide();
                }
                else
                {
                    $(".type1-type2").hide();
                    $(".type3-type4").show();

                    if( response.taskType === 1  )
                    {
                        $(".type1").hide();
                        $(".type2").hide();
                        $(".type3").show();
                        $(".type4").hide();
                        $(".type1-type2-type4").hide();
                        
                    }
                    else
                    {
                        $(".type1").hide();
                        $(".type2").hide();
                        $(".type3").hide();
                        $(".type4").show();
                        $(".type1-type3").hide();
                    }                                       
                }
                                
                
                loadAnswer( response.answers, response.taskType, response.showIcon );
                loadImages( response.relatedImages );
                loadVideos( response.relatedVideos );
                loadRelatedInfo( response.relatedInfo );
                loadRelatedResesrch( response.relatedResearch );
                loadTask( response.task, response.introduction );
                if( response.survey !== undefined )
                {
                    loadSurvey( response.survey );
                }
                
                if( response.showIcon === 1)
                {
                    $(".patient-info .icon").hide();
                    $(".related-info .icon").hide();
                    $(".clinical-evidence .icon").hide();
                }
                
                $(".introduction .content").html( response.introduction );                
                $(".question .content").html( response.question );
                
                if( response.state === -1 )
                {
                    window.location = "/chqa/experiment/healthliteracy";
                }
                else if( response.state === 0 )
                {
                    $(".results").show();
                    $(".survey").hide();
                    $(".task").hide();
                }
                else if( response.state === 1 )
                {
                    $(".results").hide();
                    $(".survey").hide();
                    $(".task").show();
                    initSubmitTask( response.survey !== undefined );
                }
                else if( response.state === 2 && response.survey !== undefined )
                {
                    $(".results").hide();
                    $(".survey").show();
                    $(".task").hide();
                    initSubmitSurvey();
                }
                else
                {
                    $(".results").hide();
                    $(".survey").hide();
                    $(".task").hide();
                    alert("Error in display");
                }
                                
                $("#evalauate").click(function( )
                {
                    console.log("Clicked the evaluation button");
                    $(".results").hide();
                    $(".survey").hide();
                    $(".task").show();
                    
                    
                    $.ajax({
                        type : "POST",
                        url : "/chqa/experiment/update",
                        data: {"state":1, "jsonData":""},
                        success: function( response )
                        {
                            initSubmitTask();
                        }
                        
                    });    
                    
                });
                
                startTime = Date.now() / 1000;
//                
//                tippy('#question-title', {
//                content: 'This is title tool tip',
//                trigger: 'click'
//                });
                
                
                
            }
            
           
                        
        });
        
//        let myModal = new bootstrap.Modal(
//          document.getElementById("{{related-info-modal-id}}"),
//          {}
//        );
//        // https://getbootstrap.com/docs/5.0/components/modal/#show
//        myModal.show();
      
};

var initSubmitTask = function( hasSurvey )
{
    $("#submit-task").click(function( )
    {
        result.task = [];
        var taskCount = parseInt( $(".task .question").length );                                                
        for( var counter = 0; counter < taskCount; counter++ )
        {
            result.task[counter] = {};
            result.task[counter].question = loadResponse.task[counter].title;

            if( !$('.task .question input[name="'+ counter +'"]:checked').length )
            {                            
               alert("Please answer all questions");
               return;
            }

            result.task[counter].answer = $('.task .question input[name="'+ counter +'"]:checked').val();

        }
        
        $.ajax({
            type : "POST",
            url : "/chqa/experiment/update",
            data: {"state":2, "jsonData": JSON.stringify( result.task ) },
            success: function( response )
            {
                console.log("Clicked the submit-task buton : " + $(".task .question").length );
                $(".survey").show();
                $(".task").hide();

                if( hasSurvey )
                {
                    initSubmitSurvey();
                }
                else
                {
                    location.reload();
                }
            }

        }); 
                      
    });
};

var initSubmitSurvey = function()
{
    $("#submit-survey").click(function( )
    {
        result.survey = [];
        var surveyCount = parseInt( $(".survey .question").length );
        for( var counter = 0; counter < surveyCount; counter++ )
        {
            result.survey[counter] = {};
            result.survey[counter].question = loadResponse.survey[counter].title;
            if( !$('.survey .question input[name="'+ counter +'"]:checked').length )
            {                                                                    
                alert("Please answer all questions");
                return;  
            }

            result.survey[counter].answer = $('.survey .question input[name="'+ counter +'"]:checked').val();
        }
        
        $.ajax({
            type : "POST",
            url : "/chqa/experiment/update",
            data: {"state":3, "jsonData": JSON.stringify( result.survey ) },
            success: function( response )
            {                
                console.log("Clicked the submit-survey buton : " + $(".survey .question").length);                            
                location.reload();
            }

        });

    });
};

loadData();

