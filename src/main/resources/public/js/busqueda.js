function hacerBusqueda() {
    // Recuperamos los valores del formulario
    var opcion = document.forms["formulario"]["opcion"].value;
    var termino = document.forms["formulario"]["texto"].value;
    document.forms["formulario"]["texto"].clear;

    // La petición será a un endpoint distinto en función del formulario
    var url = "";
    if(opcion == "programa") {
        url = "/programa/" + termino;
    }
    else {
        url = "/cinta/" + termino;
    }

    // Creamos la petición
    var req = new XMLHttpRequest();
    req.open("GET", url, false);
    req.send(null);

    if(req.status != 200) {
        // La petición ha tenido algún error
        alert("error");
    }
    else {
        // La petición no ha tenido errores
        var res = JSON.parse(req.responseText);

        if(opcion == "programa") {
            // Se hizo petición al endpoint de programa
            // Guardamos en un string las cintas en las que está el programa
            var cintas = res.cinta[0];
            for(i=1; i<res.cinta.length; i++) {
                cintas += "-" + res.cinta[i];
            }

            // Creamos el div para mostrarlo
            var texto =
                "<br>\n" +
                "<h5>Resultados de la búsqueda</h5>\n" +
                "<div class=\"col-md-3\">\n" +
                "        <ul class=\"list-group ticketView\">\n" +
                "            <li class=\"list-group-item ticketView\">\n" +
                "                <span class=\"badge pull-left\" style=\"width:50%;\">Nº Registro</span>\n" +
                "                " + res.numRegistro + "\n" +
                "            </li>\n" +
                "            <li class=\"list-group-item ticketView\">\n" +
                "                <span class=\"badge pull-left\" style=\"width:50%;\">Nombre</span>\n" +
                "                " + res.nombre + "\n" +
                "            </li>\n" +
                "            <li class=\"list-group-item ticketView\">\n" +
                "                <span class=\"badge pull-left\" style=\"width:50%;\">Tipo</span>\n" +
                "                " + res.tipo + "\n" +
                "            </li>\n" +
                "            <li class=\"list-group-item ticketView\">\n" +
                "                <span class=\"badge pull-left\" style=\"width:50%;\">Cinta(s)</span>\n" +
                "                " + cintas + "\n" +
                "            </li>\n" +
                "        </ul>\n" +
                "    </div>";

            // Modificamos el contenido del div
            document.getElementById('contenido').innerHTML = texto;
        }
        else {
            // Se hizo petición al endpoint de cinta
            alert("Endpoint cinta");
        }
    }
    // Si devolvemos false no reenviará el formulario
    return false;
}