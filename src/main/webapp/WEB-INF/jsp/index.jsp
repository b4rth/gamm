<%@page import="fr.bart.gamm.model.Magasin"%>
<html>
	<head>
	  <title>Bootstrap Example</title>
	  <meta charset="utf-8">
	  <meta name="viewport" content="width=device-width, initial-scale=1">
	  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	  <script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>

	  
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
		      	<a class="navbar-brand" href="#">Brand</a>
		    </div>
		
		    <!-- Collect the nav links, forms, and other content for toggling -->
		    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		      <ul class="nav navbar-nav">
		        <li class="active"><a href="index">Calcul de distance<span class="sr-only">(current)</span></a></li>
		        <li><a href="magasin">Gestion des magasins</a></li>
		      </ul>
		    </div><!-- /.navbar-collapse -->
		  </div><!-- /.container-fluid -->
		</nav>
		
		<div style="width: 500px; height: 500px;" id="map_canvas"></div>
		
		<script type="text/javascript">

		    var map;
		    var elevator;
		    var map = new google.maps.Map(document.getElementById('map_canvas'), {
			  zoom: 1,
			  center: new google.maps.LatLng(-33.92, 151.25),
			  mapTypeId: google.maps.MapTypeId.ROADMAP
			});

		    var addresses = ['Norway', 'Africa', 'Asia'];
		    for (var x = 0; x < addresses.length; x++) {
		        $.getJSON('https://maps.googleapis.com/maps/api/geocode/json?address='+addresses[x]+'&sensor=false&key=AIzaSyD_IDpHIZ0G1t8Y_gWDi5FIfF4I8oFG_n4', null, function (data) {
		            var p = data.results[0].geometry.location
		            var latlng = new google.maps.LatLng(p.lat, p.lng);
		            new google.maps.Marker({
		                position: latlng,
		                map: map
		            });

		        });
		    }

	  </script>
	</body>
</html>
