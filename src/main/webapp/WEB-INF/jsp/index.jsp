<%@ page import="java.util.List" %>
<%@ page import="fr.bart.gamm.model.Magasin" %>
<html>
	<head>
		<title>Maps</title>
		<script src="https://maps.google.com/maps/api/js?libraries=places&key=AIzaSyC3yymmxg6KgmbSsetDC8feIDZvF-aoXhM" type="text/javascript"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	  
	</head>
	<body>
	
		<nav class="navbar navbar-default">
			<div class="container-fluid">
		    	<!-- Brand and toggle get grouped for better mobile display -->
		    	<div class="navbar-header">
		      	<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
			        <span class="sr-only">Toggle navigation</span>
		        	<span class="icon-bar"></span>
		        	<span class="icon-bar"></span>
		        	<span class="icon-bar"></span>
		      	</button>
		      	<a class="navbar-brand" href="#">Gamm2Vert</a>
		    </div>
		
		    <!-- Collect the nav links, forms, and other content for toggling -->
		    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		      <ul class="nav navbar-nav">
		        <li class="active"><a href="index">Carte<span class="sr-only">(current)</span></a></li>
		        <li><a href="magasin">Magasins</a></li>
		      </ul>
		    </div><!-- /.navbar-collapse -->
		  </div><!-- /.container-fluid -->
		</nav>

		<%
			String adressesUrl = "[";
			if(request.getAttribute("magasins") != null && request.getAttribute("magasins") instanceof List<?>) {
				try {
					for(Magasin magasin : (List<Magasin>)request.getAttribute("magasins")) {
						adressesUrl += "\'" + magasin.getAdresseForURL() + "\',";							
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			adressesUrl += "]";
		%>

		<div class="container">
			<div style="width: 1000px; height: 600px;" id="map_canvas"></div>
			<script type="text/javascript">
	
			    var map;
			    var elevator;
			    var map = new google.maps.Map(document.getElementById('map_canvas'), {
				  zoom: 8,
				  center: new google.maps.LatLng(47.46, -0.55),
				  mapTypeId: google.maps.MapTypeId.ROADMAP
				});
	
			    var addresses = <% out.println(adressesUrl); %>;
			    for (var x = 0; x < addresses.length; x++) {
			        $.getJSON('https://maps.googleapis.com/maps/api/geocode/json?address='+addresses[x]+'&sensor=false&key=AIzaSyC3yymmxg6KgmbSsetDC8feIDZvF-aoXhM', null, function (data) {
			            var p = data.results[0].geometry.location
			            var latlng = new google.maps.LatLng(p.lat, p.lng);
			            new google.maps.Marker({
			                position: latlng,
			                map: map
			            });
	
			        });
			    }
	
		  </script>
		</div>
		

	</body>
</html>