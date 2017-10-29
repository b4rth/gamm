<%@page import="fr.bart.gamm.util.Action"%>
<%@page import="fr.bart.gamm.util.Error"%>
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
		
			<%
				if(request.getAttribute("error") != null) {
					out.println("<div class=\"alert alert-danger\">" + ((Error)request.getAttribute("error")).getMessage() + "</div>");
				}
			%>		
		
			<form action="index">
				<div class="input-group">
				   <input type="text" class="form-control float-left" id="address" name="address" placeholder="Rentrez une adresse pour trouver les magasins les plus proches">
				   <input type="hidden" name="action" value="<% out.print(Action.SEARCH_NEAR_STORES.getLabel());%>" />
				   <span class="input-group-btn">
				        <button type="submit" class="btn btn-primary float-right">Rechercher</button>
				   </span>
				</div>
			</form>
		
			<div style="width: 100%; height: 600px;" id="map_canvas"></div>
		  
		  
			<script type="text/javascript">
				var locations = [				                 
					<% 
						if(request.getAttribute("magasins") != null && request.getAttribute("magasins") instanceof List<?>) {
							try {
								String tab = "";
								int i = 1; 
								for(Magasin magasin : (List<Magasin>)request.getAttribute("magasins")) {
									if(magasin.getLatitude() != null && magasin.getLongitude() != null) {
										tab += "['" + magasin.getAdresse() + "', " + magasin.getLatitude() + ", " + magasin.getLongitude() + ", " + i + "],";
										i++;									
									}
								}
								if (tab != null && tab.length() > 0) {
									tab = tab.substring(0, tab.length() - 1);
							    }
								out.println(tab);
							} catch(Exception e) {
								e.printStackTrace();
							}
						}
					%>
				];
				
				var map = new google.maps.Map(document.getElementById('map_canvas'), {
					zoom: 8,
					center: new google.maps.LatLng(47.46, -0.55),
					mapTypeId: google.maps.MapTypeId.ROADMAP
				});

				var infowindow = new google.maps.InfoWindow();

				var marker, i;		
				for (i = 0; i < locations.length; i++) {  
  					marker = new google.maps.Marker({
    					position: new google.maps.LatLng(locations[i][1], locations[i][2]),
    					map: map
  					});

					google.maps.event.addListener(marker, 'click', (function(marker, i) {
	    				return function() {
      						infowindow.setContent(locations[i][0]);
      						infowindow.open(map, marker);
    					}
  					})(marker, i));
				}
			</script>
		  
		</div>
		

	</body>
</html>