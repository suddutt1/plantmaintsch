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
					<legend>Plant Resource Availability</legend>
					<!-- Select Basic -->
					<div class="form-group">
						<label class="col-sm-2 control-label" for="resourceId">Select
							Resource</label>
						<div class="col-sm-10">
							<select id="resourceId" name="resourceId" class="form-control" >
								<c:forEach items="${resources}" var="resource">
								<option value="${resource.id}">
								<c:out value="${resource.description}"/> 
								</option>
								</c:forEach>	
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 pt-1 control-label" for="year">Select
							Year</label>
						<div class="col-sm-4 pt-1">
							<select id="year" name="year" class="form-control" >
								<option value="2017">
								2017 
								</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 pt-1 control-label" for="month">Select
							Month</label>
						<div class="col-sm-4 pt-1">
							<select id="month" name="month" class="form-control" >
								<option value="1">January</option>
								<option value="2">February</option>
								<option value="3">March</option>
								<option value="4">April</option>
								<option value="5">May</option>
								<option value="6">June</option>
								<option value="7">July</option>
								<option value="8">August</option>
								<option value="9">September</option>
								<option value="10">October</option>
								<option value="11">November</option>
								<option value="12">December</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="offset-sm-2 col-sm-10 pt-1">
							<input type="button"  name="planBtn" id="planBtn"
								class="btn btn-primary" value="Get Resource Availability"/>
						</div>
					</div>
			</form>
		</div>
		<div class="row pt-1" id="planResult">
			<div class="col-sm-12" id="detailResult">
		    </div>
		</div>
		<!--  div id="planResult" -->
	</div>
	<!-- /container -->
	<!-- jQuery first, then Tether, then Bootstrap JS. -->
	<script src="js/jquery-3.1.1.min.js"></script>
	<script src="js/tether.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script id="dataTableTemplate" type="x-tmpl-mustache">
		<table class="table table-striped col-sm-12">
		<thead class="thead-inverse">
			<tr>
				<th>Date</th><th>Shift 1</th><th>Shift 2</th><th>Shift 3</th>
			</tr>
		</thead>
		<tbody>
			{{#rows}}
				<tr><td>{{planDate}}</td><td>{{{shift1}}}</td><td>{{{shift2}}}</td><td>{{{shift3}}}</td></tr>
			{{/rows}}
		</tbody>
		</table>	
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
		var postData = {};
		postData["resourceId"]=$("#resourceId").val();
		postData["month"]=$("#month").val();
		postData["year"]=$("#year").val();
		
		$.ajax({
         type: 'POST',
         url:  'getMonthlySchedule.wss',
         data: postData
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
		//console.log("Response object "+ resp);
		var data = resp.result.availablity;
		var tableTemplate = $("#dataTableTemplate").html();
		Mustache.parse(tableTemplate);
		var rows = new Array();
		for(var date in data)
		{
			//console.log(date+"|"+ data[date]);
			var availble = data[date];
			rows.push({	planDate: date,
				shift1:genString(availble[0]),
				shift2:genString(availble[1]),
				shift3:genString(availble[2])
			}); 
		}
		var tableContent = Mustache.render(tableTemplate,{'rows':rows});
		$("#detailResult").html(tableContent);
		console.log(tableContent);
	}
	function genString(value)
	{
		if(value==true)
		{
			return '<span class="tag tag-pill tag-success">Avilable</span>';
		}
		else
		{
			return '<span class="tag tag-pill tag-danger">Not avilable</span>';
		}
	}
	</script>
	
</body>
</html>