var API_BASE_URL = "http://147.83.7.205:8089";
var USERNAME = "";
var PASSWORD = "";

$.ajaxSetup({
    headers: { 'Authorization': "Basic "+ btoa(USERNAME+':'+PASSWORD) }
});

/*oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo*/
//Create new user

$("#button_to_create_user").click(function(e) {
	e.preventDefault();
	if($("#createname").val() == "" || $("#createpassword").val() == "")
	{
			$("#create_result").text("Write all your data!");
		
	}
	else
	{
		var user = new Object();
		user.name = $("#createname").val();
		user.password = $("#createpassword").val();
		createuser(user);
	}
});

function createuser(user)
{
	
	var url = API_BASE_URL + '/users';

    $.ajax({
		url : url, 
		type : 'POST',
		crossDomain : true,

		data : {name:user.name,password:user.password},
        
	}).done(function(data, status, jqxhr) {
        $("#create_result").text("User created!");
        login(user);

  	}).fail(function() {
	$("#create_result").text("Error! This user ID is already taken");
	});
}
/*oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo*/

//Login an existing user

$("#button_to_log").click(function(e) {
	e.preventDefault();
	if($("#name").val() == "" || $("#password").val() == "")
	{
			$("#login_result").text("Write all your data!");
		
	}
	else
	{
		var user = new Object();
		user.name = $("#name").val();
		user.password = $("#password").val();
		login(user);
	}
});

function login(user)
{
	
	var url = API_BASE_URL + '/login';
    $.ajax({
		url : url,
		type : 'POST',
		crossDomain : true,
		data :  {name:user.name,password:user.password},
	}).done(function(data, status, jqxhr) {
	
					var authtoken =data.token;			
                    sessionStorage.token= JSON.stringify(authtoken);
				 sessionStorage.username= user.name;
				
     $("#login_result").text("Succesful login!");
					window.location = "index.html"
					

  	}).fail(function() {
		$("#login_result").text("Failed attempt to login,check your username and password");
	});
}            
    
  /*oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo*/
//Create an exam
$("#button_to_create_exam").click(function(e) {
	e.preventDefault();
    var comprove = sessionStorage.getItem("username");
    if(comprove == null || comprove == "" ){$("#create_result").text("You need to login to post!");}
    else{
	if($("#repository_description_to_create").val() == "" || $("#subject_to_create").val() == "" || $("#files").val() == "")
	{
			$("#create_result").text("Write all your data!");
		
	}
	else
	{
		var exam = new Object();
		exam.subject = $("#subject_to_create").val();
		exam.text = $("#repository_description_to_create").val();
        var fileInput = document.getElementById('files');
var fileex = fileInput.files[0];
        exam.image=fileex;
        createxam(exam);
	}}
});
  
function createxam(exam)
{
	
	var url = API_BASE_URL + '/exam';
	
   var tokenauts = sessionStorage.getItem("token");
    var tokenaut=tokenauts.slice(1,-1);
    
    
    var formData = new FormData();
    formData.append('subject',exam.subject);
    formData.append('text',exam.text); 
    formData.append('image',exam.image);
    
    $.ajax({
		url : url, 
		type : 'POST',
		crossDomain : true,
        headers: {'X-Auth-Token' : tokenaut },
        processData: false,
        contentType : false,
        cache: false,    
	    data : formData,
         statusCode: {
    500: function() {
     $("#create_result").text("Select a picture, png or jpg!");
    }}
        
	}).done(function(data, status, jqxhr) {
        $("#create_result").text("Exam created!");

  	}).fail(function() {
	$("#create_result").text("Error! Fill the 3 fields!");
	});
}    
    
/*oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo*/
//Get exam by ID
$("#butsearch").click(function(e) {
	e.preventDefault();
    var id= $("#search-field").val();
    var ifadmin=sessionStorage.getItem("username");
	if($("#search-field").val() == "")
	{
			$("#searcherror").text("Write all your data!");
		
	}
    
	else{
        
        document.getElementById("newcorrection2").style.visibility = "visible";
        document.getElementById("newcomment").style.visibility = "visible";
       if(ifadmin == "admin" && list.length>0) { document.getElementById("deleteifadmin").style.visibility = "visible";}
         GetExam(id);} 
	
});

function GetExam(exam_id) {
	var url = API_BASE_URL + '/exam/' + exam_id;
        $("#searchsubject").text('');
        $("#searchdescription").text('');
        $("#searchimage").text('');
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
        var exam = data;
        sessionStorage.currentexamid= exam.id;
        
         var dcc =new Date(exam.created_at);
            var _mes=dcc.getMonth()+1;
                var _dia=dcc.getDate();
                var _anyo=dcc.getFullYear();
                var _hora = dcc.getHours();
                var _minuto = dcc.getMinutes();
                var _segundo = dcc.getSeconds(); 
        var index3=index2 +1;
           document.getElementById("newcorrection2").style.visibility = "visible";
        document.getElementById("newcomment").style.visibility = "visible";
        
         $('<strong> Exam: '+index3+'of '+ list.length +' </strong>  <br>').appendTo($('#searchsubject'));
         $('<strong> Creator: </strong> ' + exam.creator + '<br>').appendTo($('#searchsubject'));
         $('<strong> Creation date: </strong> ' + _dia+"-"+_mes+"-"+_anyo +" at "+_hora+":"+_minuto+":"+_segundo + '<br>').appendTo($('#searchsubject'));
        $('<strong> Subject: </strong> ' + exam.subject + '<br>').appendTo($('#searchsubject'));
        $('<strong> Description: </strong> ' + exam.text + '<br>').appendTo($('#searchdescription'));
      
         $("#image").attr("src",exam.image );
        $('#image').width(700); 
        $('#image').height(700);
        $("#image").click(function() {
   $(this).attr('width', '1000');
    $(this).attr('height', '1400');
});
        GetComments(exam_id);
        GetCorrections(exam_id);
    }).fail(function(){
    $('<strong>No results found</strong><br>').appendTo($('#searchsubject'));
    }); }

/*oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo*/
//Get examlist

  var list=[] ;



function GetExams() {
	var url = API_BASE_URL + '/exam';
        $("#searchsubject").text('');
        $("#searchdescription").text('');
        $("#searchimage").text('');
  var ifadmin=sessionStorage.getItem("username");
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
        
	}).done(function(data, status, jqxhr) {
           if(ifadmin == "admin") { document.getElementById("deleteifadmin").style.visibility = "visible";}
  
        
        var bigdata = data.exams;
        $.each(bigdata, function(i, h) {
					var exam = h;
            
        list.push(exam.id);
      
        
            }   );
        if(list.length > 1){document.getElementById("next2").style.visibility = "visible";}
        
        GetExam(list[0]);
    }).fail(function(){
    $('<strong>No results found</strong><br>').appendTo($('#searchsubject'));
    }); }

/*oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo*/
//Get examlist related to a certain subject


$("#butsearchsub").click(function(e) {
	e.preventDefault();
    document.getElementById("prev").style.visibility = "hidden";
    var subject_name= $("#search-field").val();
	if($("#search-field").val() == "")
	{
			$("#searcherror").text("Write all your data!");
		
	}
    
	else{
        index2=0;
        $("#searcherror").text('');
       while(list.length > 0) {
    list.pop();
}
         GetExamSubj(subject_name);} 
	
});

function GetExamSubj(subject_name) {
    
	var url = API_BASE_URL + '/exam/subject/'+subject_name;
        $("#searchsubject").text('');
        $("#searchdescription").text('');
        $("#searchimage").text('');
    
   var ifadmin=sessionStorage.getItem("username");
  
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
      
        
	}).done(function(data, status, jqxhr) {
       // document.getElementById("newcorrection2").style.visibility = "visible";
        //document.getElementById("newcomment").style.visibility = "visible";
       if(ifadmin == "admin") { document.getElementById("deleteifadmin").style.visibility = "visible";}
        var bigdata = data.exams;
        $.each(bigdata, function(i, h) {
					var exam = h;
            
        list.push(exam.id); } 
              
              
              
              ); 
        
       // $('#numberofresults').text( "\n"+' Number of results :  ' + list.length + "\n");
       
        if(list.length > 1){document.getElementById("next").style.visibility = "visible";}
        
        GetExam(list[0]);
        
    }).fail(function(){
      $('#searchsubject').text("\n"+"No results found");  

    }); }



/*oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo*/
//Get the comments associated with a certain exam

function GetComments(exam_id) {
	var url = API_BASE_URL + '/exam/'+exam_id+'/comment';
        $("#comment").text('');
      
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
        
        //$('<h2><strong> Comments: </h2> <br>').appendTo($('#comment'));
        
        var comdata = data.comments;
        
        $.each(comdata, function(i, h) {
					var comment = h;
            
            var dcc =new Date(comment.created_at);
            var _mes=dcc.getMonth()+1;
                var _dia=dcc.getDate();
                var _anyo=dcc.getFullYear();
                var _hora = dcc.getHours();
                var _minuto = dcc.getMinutes();
                var _segundo = dcc.getSeconds(); 
            
        
        $( '<div class="well"> <div class="row"><div class="col-md-12">Created by: '+comment.creator+'<span class="pull-right">Date of creation:'+ _dia+"-"+_mes+"-"+_anyo +" at "+_hora+":"+_minuto+":"+_segundo+'</span><p>'+comment.text+'</p></div></div>').appendTo($('#comment'));
        
        
        
            }); $('<input type="text" class="form-control" id="commentbox" placeholder="Type your comment...">').appendTo($('#comment')); 
    }).fail(function(){
    $('<strong>No comments yet</strong><br>').appendTo($('#comment'));
    }); }

/*oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo*/
//Get the corrections associated with a certain exam
function GetCorrections(exam_id) {
	var url = API_BASE_URL + '/exam/'+exam_id+'/correction';
        $("#correction").text('');
       
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
        
	}).done(function(data, status, jqxhr) {
       // $('<br><strong> Correction: </strong> <br>').appendTo($('#correction'));
        var corrdata = data.corrections;
        $.each(corrdata, function(i, h) {
					var correction = h;
              var dcc =new Date(correction.created_at);
            var _mes=dcc.getMonth()+1;
                var _dia=dcc.getDate();
                var _anyo=dcc.getFullYear();
                var _hora = dcc.getHours();
                var _minuto = dcc.getMinutes();
                var _segundo = dcc.getSeconds();
        
           
      $('<br><div class="well"><div class="row"> <div class="cold-md-12"> <h3>Correction created by: '+correction.creator+'. Cretion date:'+_dia+"-"+_mes+"-"+_anyo +" at:  "+_hora+":"+_minuto+":"+_segundo +' </h3><br> <p>'+correction.text+'</p><img src="'+correction.image_correction+'" height="700" width="500"></div><br><h3>This corrections is rated with: '+correction.rating+' likes!</h3><br><h3>Rate the correction! Only one rating per user!</h3><br><button onclick="votePositive(\''+correction.id+'\');" class="btn btn-succes" type="button"   style="background-color:green;color:white" id="button_to_vote_pos">Useful correction! :)</button> <button class="btn btn-succes" type="button" onclick="voteNegative(\''+correction.id+'\');"   style="background-color:red;color:white">Negative rating :(</button></div><br><button class="btn btn-succes" type="button" onclick="deletecorr(\''+correction.id+'\');"   style="background-color:red;color:white">Delete your correction</button></div>').appendTo($('#correction'));
            
           
            } 
                  
   
              ); $('<hr><input type="file" name="file" id="correction_link_to_create" required /><br><input type="text" class="form-control" id="correctionbox" placeholder="Type your correction text...">').appendTo($('#correction'));   
    }).fail(function(){
    $('<strong>No results found</strong><br>').appendTo($('#correction'));
    }); }

/*oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo*/
//Create a correction associated with an existing exam

$("#newcorrection2").click(function(e) {
	e.preventDefault();
    var comprove = sessionStorage.getItem("username");
    if(comprove == null || comprove == "" ){$("#correctionresult").text("You need to login to post!");}
    else{
	if($("#correctionbox").val() == "" || $("#correction_link_to_create").val() == "")
	{
			$("#correctionresult").text("Write all your data!");
		
	}
	else
	{
		var correction = new Object();
		
		correction.text = $("#correctionbox").val();
        var fileInput = document.getElementById('correction_link_to_create');
var filecor = fileInput.files[0];
        correction.image=filecor;
        createcorrection(correction);
	}}
});


function createcorrection(correction)
{
	
    var exam_id_correct = sessionStorage.getItem("currentexamid");
	var url = API_BASE_URL + '/exam/'+exam_id_correct+'/correction';
	
   var tokenauts = sessionStorage.getItem("token");
    var tokenaut=tokenauts.slice(1,-1);
    
    
    var formData = new FormData(correction);
   
    formData.append('text',correction.text); 
    formData.append('image',correction.image);
    
    $.ajax({
		url : url, 
		type : 'POST',
		crossDomain : true,
        headers: {'X-Auth-Token' : tokenaut },
        processData: false,
        contentType : false,
        cache: false,    
	    data : formData,
        statusCode: {
    500: function() {
     $("#correctionresult").text("Select a picture, png or jpg!");
    }}
        
	}).done(function(data, status, jqxhr) {
        $("#correctionresult").text("Correction created!");

  	}).fail(function() {
	$("#correctionresult").text("Error! Fill the 2 fields!");
	});
}    
    

/*oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo*/


/*oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo*/
//Create cooments
$("#newcomment").click(function(e) {
	e.preventDefault();
    var comprove = sessionStorage.getItem("username");
    if(comprove == null || comprove == "" ){$("#commentresult").text("You need to login to post!");}
    else{
	if($("#commentbox").val() == "" )
	{
			$("#commentresult").text("Write your comment!");
		
	}
	else
	{
		var comment = new Object();
		
		comment.text = $("#commentbox").val();
        createcomment(comment);
	}}
});

function createcomment(comment)
{
var exam_id_comment = sessionStorage.getItem("currentexamid");
	var url = API_BASE_URL + '/exam/'+exam_id_comment+'/comment';
      var tokenauts = sessionStorage.getItem("token");
    var tokenaut=tokenauts.slice(1,-1);

    $.ajax({
		url : url, 
		type : 'POST',
		crossDomain : true,
        headers: {'X-Auth-Token' : tokenaut },
		data : {text: comment.text},
        
	}).done(function(data, status, jqxhr) {
        $("#commentresult").text("Comment posted!");
       

  	}).fail(function() {
	$("#commentresult").text("Error! ");
	});
}


/*oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo*/
// tries to control the next and prev buttons
var index2=0;
$("#next").click(function(e) {
    e.preventDefault();
     document.getElementById("newcorrection2").style.visibility = "visible";
        document.getElementById("newcomment").style.visibility = "visible";

    index2++;
        if(index2 < (list.length-1)){document.getElementById("next").style.visibility = "visible";}
    else {document.getElementById("next").style.visibility = "hidden";}
    if(index2 != 0 ){document.getElementById("prev").style.visibility = "visible";}
    else {document.getElementById("prev").style.visibility = "hidden";}
    console.log(index2);
    list_id=list[index2];
	GetExam(list_id);
    
});

$("#prev").click(function(e) {
    e.preventDefault();
     document.getElementById("newcorrection2").style.visibility = "visible";
        document.getElementById("newcomment").style.visibility = "visible";
    
    index2--;
    if(index2 < list.length){document.getElementById("next").style.visibility = "visible";}
    else {document.getElementById("next").style.visibility = "hidden";}
    if(index2 != 0 ){document.getElementById("prev").style.visibility = "visible";}
    else {document.getElementById("prev").style.visibility = "hidden";}
    console.log(index2);
    list_id=list[index2];
	GetExam(list_id);
   
});



$("#next2").click(function(e) {
    e.preventDefault();
     document.getElementById("newcorrection2").style.visibility = "visible";
        document.getElementById("newcomment").style.visibility = "visible";

    index2++;
        if(index2 < (list.length-1)){document.getElementById("next2").style.visibility = "visible";}
    else {document.getElementById("next2").style.visibility = "hidden";}
    if(index2 != 0 ){document.getElementById("prev2").style.visibility = "visible";}
    else {document.getElementById("prev2").style.visibility = "hidden";}
    console.log(index2);
    list_id=list[index2];
	GetExam(list_id);
    
});

$("#prev2").click(function(e) {
    e.preventDefault();
     document.getElementById("newcorrection2").style.visibility = "visible";
        document.getElementById("newcomment").style.visibility = "visible";
    
    index2--;
    if(index2 < list.length){document.getElementById("next2").style.visibility = "visible";}
    else {document.getElementById("next2").style.visibility = "hidden";}
    if(index2 != 0 ){document.getElementById("prev2").style.visibility = "visible";}
    else {document.getElementById("prev2").style.visibility = "hidden";}
    console.log(index2);
    list_id=list[index2];
	GetExam(list_id);
   
});
/*oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo*/
//Functions that increase or decrease the ratings of an exam

$("#button_to_vote_pos").click(function(e) {
	e.preventDefault();	
	votePositive($("#like_id").val());
});

function votePositive(like_id)
{
 
	var url = API_BASE_URL + '/like/correction';
      var tokenauts = sessionStorage.getItem("token");
    var tokenaut=tokenauts.slice(1,-1);

    $.ajax({
		url : url, 
		type : 'POST',
		crossDomain : true,
        headers: {'X-Auth-Token' : tokenaut },
		data : {liked_id: like_id},
       
        
	}).done(function(data, status, jqxhr) {
         alert("Like accepted! :)");
	}).fail(function() {
	 alert("Only one like per logged user :)");
	});
}

function voteNegative(like_id)
{

	var url = API_BASE_URL + '/like/correction/'+like_id;
      var tokenauts = sessionStorage.getItem("token");
    var tokenaut=tokenauts.slice(1,-1);

    $.ajax({
		url : url, 
		type : 'DELETE',
		crossDomain : true,
        headers: {'X-Auth-Token' : tokenaut },
		data : {liked_id: like_id},
        
	}).done(function(data, status, jqxhr) {
           alert("Dislike accepted! :)");
	}).fail(function() {
	 alert("Only one like/dislike per user :)");
	});
}

/*oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo*/
//Delete option ony for admin
$("#deleteifadmin").click(function(e) {
	e.preventDefault();
	
		var delete_id= sessionStorage.getItem("currentexamid");
        deleteExam(delete_id);
	
});
  
function deleteExam(delete_id)
{
	
	var url = API_BASE_URL + '/exam/'+delete_id;
	
   var tokenauts = sessionStorage.getItem("token");
    var tokenaut=tokenauts.slice(1,-1);
    
    
   
    
    $.ajax({
		url : url, 
		type : 'DELETE',
		crossDomain : true,
        headers: {'X-Auth-Token' : tokenaut },
        processData: false,
        contentType : false,
        cache: false,    
	    
        
	}).done(function(data, status, jqxhr) {
        alert("Exam deleted");

  	}).fail(function() {
	alert("Error deleting!");
	});
}    


/*oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo*/
//Per fer logout i tal
$("#button_to_log_out").click(function(e) {
	e.preventDefault();
    var comprove = sessionStorage.getItem("username");
	if(comprove == null || comprove == "" )
	{
			$("#logout_result").text("Yor are not even logged!");
		
	}
	else
	{
		sessionStorage.token="";
        sessionStorage.username="";
        $("#logout_result").text("Logout succesfull!");
	}
});

/*oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo*/
//Delete corrections for creator users
function deletecorr(delete_id)
{
	 var examid = sessionStorage.getItem("currentexamid");
	var url = API_BASE_URL + '/exam/'+examid+'/correction/'+delete_id;
	
   var tokenauts = sessionStorage.getItem("token");
    var tokenaut=tokenauts.slice(1,-1);
    
    
   
    
    $.ajax({
		url : url, 
		type : 'DELETE',
		crossDomain : true,
        headers: {'X-Auth-Token' : tokenaut },
        processData: false,
        contentType : false,
        cache: false,    
	    
        
	}).done(function(data, status, jqxhr) {
        alert("Correction deleted");

  	}).fail(function() {
	alert("Login as  creator to delete the correction,if you are not the creator you can't delete!");
	});
}    

/*oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo*/
//to load the index list



$(document).ready(function() {
    var nametoshow = sessionStorage.getItem("username");
    if(nametoshow != null || nametoshow != '')
    
   GetExams(); 
  
});