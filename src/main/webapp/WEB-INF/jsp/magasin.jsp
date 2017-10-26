<%@page import="fr.bart.gamm.model.Magasin"%>
<%@page import="fr.bart.gamm.util.Action"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
	<head>
	  	<title>Bootstrap Example</title>
	  	<meta charset="utf-8">
	  	<meta name="viewport" content="width=device-width, initial-scale=1">
	  	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	  	<script>
			function tableFilter() {
			  // Declare variables 
			  	var input, filter, table, tr, td, i;
			  	input = document.getElementById("searchInput");
			  	filter = input.value.toUpperCase();			  	
			  	table = document.getElementById("tableMagasin");
			  	tr = table.getElementsByTagName("tr");
				
			  	// Loop through all table rows, and hide those who don't match the search query
			  	for (i = 0; i < tr.length; i++) {
				    td0 = tr[i].getElementsByTagName("td")[0];
				    td1 = tr[i].getElementsByTagName("td")[1];
				    td2 = tr[i].getElementsByTagName("td")[2];
				    td3 = tr[i].getElementsByTagName("td")[3];
				    td4 = tr[i].getElementsByTagName("td")[4];

		    		if(td0 || td1 || td2 || td3 || td4) {
			      		if ((td0 != null && td0.innerHTML.toUpperCase().indexOf(filter) > -1) || 
			      				(td1 != null && td1.innerHTML.toUpperCase().indexOf(filter) > -1) || 
			      				(td2 != null && td2.innerHTML.toUpperCase().indexOf(filter) > -1) || 
			      				(td3 != null && td3.innerHTML.toUpperCase().indexOf(filter) > -1) || 
			      				(td4 != null && td4.innerHTML.toUpperCase().indexOf(filter) > -1)) {
			        		tr[i].style.display = "";
			      		} else {
			        		tr[i].style.display = "none";
				    	} 		    			
		    		}
			  	}
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
		        		<li><a href="index">Carte</a></li>
		        		<li class="active"><a href="magasin">Magasins</a></li>
		     		 </ul>
		    	</div><!-- /.navbar-collapse -->
		  	</div><!-- /.container-fluid -->
		</nav>

		<div class="container">
			<a style="margin:0 0 20px 0;" class="btn btn-primary btn-lg btn-block" href="creationMagasin">Ajouter un nouveau magasin</a>
			<div style="margin:0 0 20px 0;" id="custom-search-input">
                <div class="input-group col-md-12">
                    <input onkeyup="tableFilter()" id="searchInput" type="text" class="form-control input-lg" placeholder="Recherche" />
                </div>
            </div>     
             
             
			<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		        <div class="modal-dialog">
		            <div class="modal-content">
		            
		                <div class="modal-header">
		                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		                    <h4 class="modal-title" id="myModalLabel">Confirmer la suppression</h4>
		                </div>
		            
		                <div class="modal-body">
		                    <p>Vous êtes sur le point de supprimer ce magasin.</p>
		                    <p>Confirmez-vous cette action ?</p>
		                </div>
		                
		                <div class="modal-footer">
		                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
		                    <a class="btn btn-danger btn-ok">Confirmer</a>
		                </div>
		            </div>
		        </div>
		    </div>
            
		  	<table id="tableMagasin" class="table" cellspacing="0" width="100%">
		    	<thead>
		      		<tr>
		        		<th>Numéro</th>
		        		<th>Rue</th>
		        		<th>Code postal</th>
		        		<th>Ville</th>
		        		<th>Type</th>
		        		<th></th>
		      		</tr>
		    	</thead>
		    	<tbody>
			    	<% 	
			    		List<Magasin> magasinList = (List<Magasin>)request.getAttribute("magasins");
			    		if(magasinList != null) {
			    			for(Magasin magasin : magasinList) {
			    				out.println("<tr>");
		    					out.println("<td>" + (magasin.getNumero() != null ? magasin.getNumero() : "-") + "</td>");
		    					out.println("<td>" + (magasin.getRue() != null ? magasin.getRue() : "-") + "</td>");
		    					out.println("<td>" + (magasin.getCodePostal() != null ? magasin.getCodePostal() : "-") + "</td>");
		    					out.println("<td>" + (magasin.getVille() != null ? magasin.getVille() : "-") + "</td>");

			    				if(magasin.getType() != null && magasin.getType().getLabel() != null) {
			    					out.println("<td>" + magasin.getType().getLabel() + "</td>");		    					
			    				} else {
			    					out.println("<td></td>");
			    				}
			    				
			    				out.println("<td><button class=\"btn btn-default\" data-href=\"magasin?action=" + Action.DELETE.getLabel() + "&id=" + magasin.getId() + "\" data-toggle=\"modal\" data-target=\"#confirm-delete\">Supprimer</button><td>");
			    				
			    				out.println("</tr>");
			    			}		
			    		}
			    	%>
		    	</tbody>
		  	</table>
		  	<script>
		        $('#confirm-delete').on('show.bs.modal', function(e) {
		            $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));		
		            $('.debug-url').html('Delete URL: <strong>' + $(this).find('.btn-ok').attr('href') + '</strong>');
		        });
    		</script>
		</div>
		
	</body>
</html>
