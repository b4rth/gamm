<%@page import="fr.bart.gamm.util.Action"%>
<%@page import="java.util.Map"%>
<%@ page import="java.util.List" %>
<%@ page import="fr.bart.gamm.model.Magasin" %>
<%@ page import="fr.bart.gamm.util.Couple" %>
<%@ page import="fr.bart.gamm.util.StringUtil" %>
<html>
	<head>
		<title>Maps</title>
		<script src="https://maps.google.com/maps/api/js?libraries=places&key=AIzaSyC3yymmxg6KgmbSsetDC8feIDZvF-aoXhM" type="text/javascript"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		<script type="text/javascript">
			function loadTrajet() {
				
			}
		</script>
	  
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
			String adresseRenseignee = "";
		    String adresseDestination = "";
		    if(request.getAttribute("adresseRenseignee") != null) {
		    	adresseRenseignee = (String)request.getAttribute("adresseRenseignee");
		    	adresseRenseignee = StringUtil.Normalize(adresseRenseignee).replaceAll("\\s+","+");		
		    }
			if(request.getAttribute("magasinsAvecDistance") != null) {
				try {
					Map<Magasin, Couple<Integer, Integer>> magasins = (Map<Magasin, Couple<Integer, Integer>>)request.getAttribute("magasinsAvecDistance");
					Magasin magasinPlusProche = null;
					int distanceMin = Integer.MAX_VALUE;
					for(Map.Entry<Magasin, Couple<Integer, Integer>> e : magasins.entrySet()) {
						if(e.getValue() != null && e.getValue().getElement1() != null && e.getValue().getElement1() < distanceMin) {
							magasinPlusProche = e.getKey();
							distanceMin = e.getValue().getElement1();
						}
					}						
					if(magasinPlusProche != null) {
						adresseDestination = StringUtil.Normalize(magasinPlusProche.getAdresse()).replaceAll("\\s+","+");	
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		%>

		<div class="container">
		
			<form action="index">
				<div class="input-group">
				   <input type="text" class="form-control float-left" id="address" name="address" placeholder="Rentrez une adresse pour trouver les magasins les plus proches">
				   <input type="hidden" name="action" value="<% out.print(Action.SEARCH_NEAR_STORES.getLabel());%>" />
				   <span class="input-group-btn">
				        <button type="submit" class="btn btn-primary float-right">Rechercher</button>
				   </span>
				</div>
			</form>
		
			<div>
				<iframe width="70%"  height="450" frameborder="0" style="border:0" class="float-right"  
					src="https://www.google.com/maps/embed/v1/directions?key=AIzaSyC3yymmxg6KgmbSsetDC8feIDZvF-aoXhM&origin=<% out.println(adresseRenseignee); %>&destination=<% out.println(adresseDestination); %>&avoid=tolls|highways" allowfullscreen>
				</iframe>
				<div class="float-right" style="width:25%;">
					<div class="list-group">
					
						<%
							if(request.getAttribute("magasinsAvecDistance") != null) {
								try {
									Map<Magasin, Couple<Integer, Integer>> magasins = (Map<Magasin, Couple<Integer, Integer>>)request.getAttribute("magasinsAvecDistance");
									
									for(Map.Entry<Magasin, Couple<Integer, Integer>> e : magasins.entrySet()) {
										out.println("<a href=\"#\" onclick=\"loadTrajet()\" class=\"list-group-item list-group-item-action flex-column align-items-start\">");
										out.println("<p class=\"mb-1\"><b>" + e.getKey().getAdresse() + "</b></p>");
										out.println("<p class=\"mb-1\"><i>Distance : </i>" + Integer.valueOf(e.getValue().getElement1())/1000 + "</p>");
										out.println("<p class=\"mb-1\"><i>Temps : </i>" + e.getValue().getElement2() + "</p>");
				  						out.println("</a>");
									}
								} catch(Exception e) {
									e.printStackTrace();
								}
							}
						%> 					
	  					
	  					
					</div>
				</div>
		  	</div>
		  
			
		  
		</div>
		

	</body>
</html>