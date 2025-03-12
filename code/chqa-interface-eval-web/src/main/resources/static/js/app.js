
var relatedInfoTemplte = $(".related-info .template").get(0).outerHTML;
$(".related-info .template").get(0).remove();

$("#submit-button").on("click", function()
{
    $(".related-info .item, .related-research .item").each( function(){
            $(this).remove();
        });
        
    /*
     * Resetting the View
     */
    var html = "<div class='spinner-border' role='status'><span class='visually-hidden'>Loading...</span></div>";    
    $(".answer>.content").html( html );
    
    $(".image-carousel-button").click();
    
    /*
     * End
     */
        
    var question = $("#search-query").val();
    
    if( question.length > 0 )
    {
        $(".results .question .content").text( question );

        question = question.trim();
        /*
         * Related Images
         */
        $.ajax({
            type : "POST",
            url : "/chqa/relatedimages",
            data: {question : question},
            success: function( response )
            {
                console.log( "relatedvideos" + response.length );
                $(".image-carousel .carousel-inner").empty();
                
                for( var cnt in response )
                {
                    var image = response[cnt];
                    var imageURL = image.text;
                    var url = image.url;
                    
                    var active = cnt === "0" ? "active" : "";
                        var itemDiv = "<div class='carousel-item "+ active +
                                        "'>"+
                                        "<div class='col-md-2'>"+
                                        "<div class='card'><div class='card-img'><a href='"+url+"'><img src='"+imageURL+"'></img></a></div></div>"+
                                        "</div>"+
                                        "</div>";

                    $(".image-carousel .carousel-inner").append( itemDiv );        
                                       
                }
                
                initcarousel();
            }                        
        });
        
        /*
         * Related Videos
         */
        $.ajax({
            type : "POST",
            url : "/chqa/relatedvideos",
            data: {question : question},
            success: function( response )
            {
                console.log( "relatedvideos" + response.length );
            }                        
        });
        
        
        $.ajax({
            type : "POST",
            url : "/chqa/answer",
            data: {question : question},
            success: function( response )
            {
                
                var html = "";                
                if( response.text)
                {
                    html += response.text; 
                }
                
                $(".answer>.content").html( html );
            }                        
        });
        
        $.ajax({
            type : "POST",
            url : "/chqa/relatedresearch",
            data: {question : question},
            success: function( response )
            {
                console.log( "relatedresearch" + response.length );
                for( var cnt in response )
                {
                    var newTemplateHTML = relatedInfoTemplte;
                    
                    newTemplateHTML = newTemplateHTML.replace("template-text", response[cnt].text);
                    newTemplateHTML = newTemplateHTML.replace("template-url", response[cnt].url);
                    newTemplateHTML = newTemplateHTML.replace("template-title", response[cnt].title);

                    $(".related-research").append( newTemplateHTML );
                }
            }                        
        });
        
        
        
        $.ajax({
            type : "POST",
            url : "/chqa/relatedinfo",
            data: {question : question},
            success: function( response )
            {
                console.log( "relatedinfo" + response.length );
                
                for( var cnt in response )
                {
                    var newTemplateHTML = relatedInfoTemplte;
                    
                    newTemplateHTML = newTemplateHTML.replace("template-text", response[cnt].text);
                    newTemplateHTML = newTemplateHTML.replace("template-url", response[cnt].url);
                    newTemplateHTML = newTemplateHTML.replace("template-title", response[cnt].title);

                    $(".related-info").append( newTemplateHTML );
                }
                
            }                        
        });
    }
});

$(document).on("click",".results .more", function(e){
    var btn = $(e.target);
    btn.hide();
    btn.next().show();
    
    console.log(btn.parent().html());
    
    btn.parent().prev().prev().removeClass("crop-text-3");
    btn.parent().prev().show();
});

$(document).on("click",".results .less", function(e){
    var btn = $(e.target);
    btn.hide();
    btn.prev().show();
    
    console.log(btn.parent().html());

    
    btn.parent().prev().prev().addClass("crop-text-3");
    btn.parent().prev().hide();
});

$(".related-image-video .icon").on("click", function( e )
{
    var btn = $(e.target);
    if( btn.hasClass("selected") )
    {
        return;
    }
    else
    {
        btn.addClass("selected");
    }

    if( btn.hasClass("image-carousel-button") )
    {
        $(".video-carousel-button").removeClass("selected");
        
        $(".related-image-video .image-carousel").show();
        $(".related-image-video .video-carousel").hide();
        
        console.log("showing image carousel");
    }
    else if( btn.hasClass("video-carousel-button") )
    {
        $(".image-carousel-button").removeClass("selected");
        
        $(".related-image-video .image-carousel").hide();
        $(".related-image-video .video-carousel").show();
        
        console.log("showing video carousel");
    }
    
    initcarousel();

        
});

