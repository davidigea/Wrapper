// Función que se ejecuta cuando el usuario clicka el botón de buscar
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

            // Creamos el div y lo mostramos
            var texto =
                "<br>\n" +
                "<h5>Resultados de la búsqueda</h5>\n" +
                presentacionPrograma(res);

            // Modificamos el contenido del div
            $("#contenido").html(texto);
        }
        else {
            // Se hizo petición al endpoint de cinta
            // Generamos el html
            var texto = "<div id=\"programas\" name=\"programas\" class=\"btn-group-vertical\" role=\"group\">\n";
            for(i=0; i<res.programas.length; i++) {
                // Variable auxiliar
                var id_bt = "btn_" + i;
                
                // Generamos un botón
                texto += "<button id=\""+ id_bt +"\" type=\"button\" class=\"btn btn-secondary\" onclick=\"mostrarPrograma(" + res.programas[i] + ")\">"+ res.programas[i] + "</button>";

                // Hay que registrar cada botón creado dinámicamente
                $("div").on('click', "#" + id_bt, function(){
                    console.log(i);
                });
            }
            texto +=
                "</div>\n" +
                "<div id=\"infoPrograma\" name=\"infoPrograma\"></div>";

            // Modificamos el contenido del div
            $("#contenido").html(texto);
        }
    }
    // Si devolvemos false no reenviará el formulario
    // Si se devuelve true envia el formulario y se hace un refresh
    return false;
}

// Función auxiliar que genera la presentación de un programa
// Devuelve un string con el código html, no lo inserta
function presentacionPrograma(res) {
    // Guardamos en un string las cintas en las que está el programa
    var cintas = res.cinta[0];
    for(i=1; i<res.cinta.length; i++) {
        cintas += "-" + res.cinta[i];
    }

    // Creamos el resto del string
    var texto =
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
    return texto;
}

// Función que se ejecuta cuando el usuario hace click sobre un programa
// Muestra el programa en pantalla
function mostrarPrograma(nombre) {
    // Creamos la petición
    var req = new XMLHttpRequest();
    req.open("GET", "/programa/" + nombre, false);
    req.send(null);

    // Comprobamos que la petición sea correcta
    if(req.status == 200) {
        // Cogemos el cuerpo de la petición
        var res = JSON.parse(req.responseText);

        // Insertamos el html
        document.getElementById('infoPrograma').innerHTML = presentacionPrograma(res);
    }
    else {
        alert("error");
    }
}