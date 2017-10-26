<%@page import="fr.bart.gamm.model.Magasin"%>
<%@page import="java.util.List"%>
<%@page import="fr.bart.gamm.util.Action"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
	<head>
	  	<title>Gamm2Vert</title>
	  	<meta charset="utf-8">
	  	<meta name="viewport" content="width=device-width, initial-scale=1">
	  	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	  	
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
		        		<li><a href="index">Carte</a></li>
		        		<li><a class="active" href="magasin">Magasins</a></li>
		     		 </ul>
		    	</div><!-- /.navbar-collapse -->
		  	</div><!-- /.container-fluid -->
		</nav>

		<div class="container">
			
			<form action="magasin">
			  <div class="form-group">
			    <label for="numero">Numéro</label>
			    <input type="number" class="form-control" id="numero" name="numero" placeholder="Numéro">
			  </div>
			  <div class="form-group">
			    <label for="rue">Rue</label>
			    <input type="text" class="form-control" id="rue" name="rue" placeholder="Rue">
			  </div>
			  <div class="form-group">
			    <label for="codePostal">Code postal</label>
			    <input type="number" class="form-control" id="codePostal" name="codePostal" placeholder="Code Postal">
			  </div>
			  <div class="form-group">
			    <label for="ville">Ville</label>
			    <input required="true" type="text" class="form-control" id="ville" name="ville" placeholder="Ville">
			  </div>
			  <input type="hidden" name="action" value="<% out.print(Action.CREATE.getLabel());%>" />
			  <button type="submit" class="btn btn-primary">Submit</button>
			</form>
		  	
		</div>
		
	</body>
</html>
