<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html lang="en">
<head>
<!-- Required meta tags always come first -->
<title>Cognitive Maintenance Scheduler App</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/app.css">
<script type="text/javascript" src="js/mustache.js"></script>
</head>
<body>
	<div class="container">
		<jsp:include page="mainmenu.jsp"></jsp:include>
		<!-- Main component for a primary marketing message or call to action -->
		<div class="container">
			<form class="form-horizontal">
					<!-- Form Name -->
					<legend>Equipment Maintenance Scheduler</legend>
					<!-- Select Basic -->
					<div class="form-group">
						<label class="col-sm-2 control-label" for="equipment">Select
							Equipment</label>
						<div class="col-sm-10">
							<select id="equipment" name="equipment" class="form-control" >
								<c:forEach items="${equipmentList}" var="equipment">
								<option value="${equipment.equipmentNumber}">
								<c:out value="${equipment.description}"/>-<c:out value="${equipment.equipmentNumber}" />
								</option>
								</c:forEach>	
							</select>
						</div>
					</div>
					
					<!-- Button -->
					
					<div class="form-group">
						<div class="offset-sm-2 col-sm-10 pt-1">
							<input type="button"  name="planBtn" id="planBtn"
								class="btn btn-primary" value="Get Cognitive Plan"/>
						</div>
					</div>
			</form>
		</div>
		<div class="row pt-1" id="planResult">
			<div class="col-sm-12">
		    <div class="card card-block">
		      <h5 class="card-title">Cognitive Plan</h5>
		      <div class="card-text pb-1" id="detailResult" >
		      		<ul class="list-group">
  					   <li class="list-group-item">
					    <span class="tag tag-success tag-pill float-xs-right" id="lastMaintenceDate"></span>
					    Last maintenance date
					  </li>
					   <li class="list-group-item">
					    <span class="tag tag-success tag-pill float-xs-right" id="oemMaintenanceDate"></span>
					    OEM proposed maintenance date
					  </li>
  					   <li class="list-group-item">
					    <span class="tag tag-pill float-xs-right" id="plannedDate"></span>
					    Proposed cognitive planned date
					  </li>
					  <li class="list-group-item">
					    <span class="tag tag-success tag-pill float-xs-right" id="shift"></span>
					    Shift
					  </li>
					  
  					</ul>
  					<h5>Weather forecast</h5>
  						<ul class="list-group" id="weather">
  							<li class="list-group-item">
							    <span class="tag tag-pill float-xs-right" id="weatherDate"></span>
							    Date
							 </li>
							 <li class="list-group-item">
							    <span class="tag tag-pill float-xs-right" id="rain"></span>
							    Forecast
							 </li>
							 <li class="list-group-item">
							    <span class="tag tag-pill float-xs-right" id="visibility"></span>
							    Visibility
							 </li>
  						</ul>	
  					<div id="resourceList">	
  					<h5>Resource details</h5>
  					<ul class="list-group" id="resources">
  					</ul>
  					</div>
  					<h5>Planning details</h5>
  					<ul class="list-group" id="trace">
  					</ul>
			  </div>
		      <a href="#" class="btn btn-primary">Confirm</a>
		    </div>
		  </div>
		</div>
		<!--  div id="planResult" -->
	</div>
	<!-- /container -->
	

	<!-- jQuery first, then Tether, then Bootstrap JS. -->
	<script src="js/jquery-3.1.1.min.js"></script>
	<script src="js/tether.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script id="traceTemplate" type="x-tmpl-mustache">
		{{#items}}
  		<li class="list-group-item">{{.}}</li>
		{{/items}}
	</script>
	<script id="resourceTemplate" type="x-tmpl-mustache">
		{{#resources}}
  		<li class="list-group-item">{{resouceId}} <span class="tag tag-pill tag-primary">{{date}}</span>
			<span class="tag tag-pill tag-info">{{shift}}</span>&nbsp;<span class="tag tag-pill tag-success">Available</span>	
		</li>
		{{/resources}}
	</script>
	
	<script>
	$( document ).ready(function() {
  		console.log("Page loaded..");
  		$("#planResult").hide();
  		$("#planBtn").click(function(){
  			console.log("Plan btn clicked");
  			getPlan();
  		});
	});
	function getPlan()
	{
		$("#planResult").hide();
		console.log("Equiment Selected :"+ $("#equipment").val());
		$.ajax({
         type: 'POST',
         url:  'getPlan.wss',
         data: { 'equipmentId': $("#equipment").val() }
      })
         .done( function (responseText) {
            // Triggered if response status code is 200 (OK)
            console.log('Response received:' + responseText);
            generateResult(responseText);
            //$("#detailResult").text(responseText);
            
         })
         .fail( function (jqXHR, status, error) {
            // Triggered if response status code is NOT 200 (OK)
            //alert(jqXHR.responseText);
            $("#detailResult").text(jqXHR.responseText);
         })
         .always( function() {
            // Always run after .done() or .fail()
            console.log("Nothing to cover up");
            $("#planResult").show();
         });
	}
	function generateResult(responseTxt)
	{
		var resp = eval("("+responseTxt+")");
		console.log("Response object "+ resp);
		var plannedDate = resp.result.plannedDate;
		if(plannedDate!=null)
		{
			$("#plannedDate").text(plannedDate);
			$("#plannedDate").removeClass('tag-warning');
			$("#plannedDate").addClass('tag-success');
			$("#weatherDate").text(plannedDate);
			$("#weatherDate").removeClass('tag-warning');
			$("#weatherDate").addClass('tag-success');
			
		}
		else
		{
			$("#plannedDate").text('Not found');
			$("#plannedDate").removeClass('tag-success');
			$("#plannedDate").addClass('tag-warning');
			$("#weatherDate").text('Not applicable');
			$("#weatherDate").removeClass('tag-success');
			$("#weatherDate").addClass('tag-warning');
			
		}
		//If weather is available then update 
		if(resp.result.forecast!=null)
		{
			var forecast = resp.result.forecast;
			if(forecast.precpProb!=-1)
			{
				$("#rain").removeClass('tag-warning');
				$("#rain").html('<img src="css/sunny.png"/>');
				$("#visibility").removeClass('tag-warning');
				$("#visibility").addClass('tag-success');
				$("#visibility").text(forecast.visibility+" meters");
			}
			else
			{
				$("#rain").addClass('tag-warning');
				$("#rain").text('No weather data');
				$("#visibility").addClass('tag-warning');
				$("#visibility").text('No weather data');
			}
		}
		else
		{
				$("#rain").addClass('tag-warning');
				$("#rain").text('Not applicable');
				$("#visibility").addClass('tag-warning');
				$("#visibility").text('Not applicable');
				
		}
		$("#oemMaintenanceDate").text(resp.result.oemMaintenanceDate);
		$("#shift").text(resp.result.shift);
		$("#lastMaintenceDate").text(resp.result.lastMaintenceDate);
		var template = $('#traceTemplate').html();
		Mustache.parse(template);   // optional, speeds up future uses
		var rendered = Mustache.render(template, {items: resp.result.planningTace});
		$('#trace').html(rendered);
		if(resp.result.schedule!=null)
		{
			$("#resourceList").show();
			var resourceTemplate = $("#resourceTemplate").html();
			Mustache.parse(resourceTemplate);
			var resourceInfo = Mustache.render(resourceTemplate, {resources: resp.result.schedule});
			$("#resources").html(resourceInfo);
		}
		else
		{
			$("#resourceList").hide();
		}
		
	}
	</script>
	
</body>
</html>