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


    if(req.status == 404) {
        alert("Los parámetros buscados no se corresponden con ninguna instancia de la base de datos");
    }
    if(req.status != 200) {
        // La petición ha tenido algún error
        alert("Error desconocido");
    }
    else {
        // La petición no ha tenido errores
        var res = JSON.parse(req.responseText);

        // Ponemos "resultados de la búsqueda"
        $("#cadenaResultados").html("<br><h5>Resultados de la búsqueda</h5>")

        if(opcion == "programa") {
            // Se hizo petición al endpoint de programa
            // Modificamos el contenido del div
            $("#contenido").html(presentacionPrograma(res));
        }
        else {
            // Se hizo petición al endpoint de cinta
            // Borramos el contenido previo
            $("#contenido").html("");

            // Creamos un objeto contenedor de botones
            var element = document.createElement("div");
            element.id = "programas";
            element.className = "btn-group-vertical";
            element.role = "group";

            // Lo añadimos al fichero
            var foo = document.getElementById("contenido");
            foo.appendChild(element);

            // Añadimos los botones (programas)
            for(i in res.programas) {
                element = document.createElement("button");
                element.id = "btn_" + i;
                element.type = "button";
                element.className = "btn btn-secondary";
                element.appendChild(document.createTextNode(res.programas[i])); // Ponemos el nombre como hijo
                element.name = res.programas[i];
                element.onclick = function() {
                    mostrarPrograma(this);
                };

                // Añadimos el botón al grupo de botones
                var foo = document.getElementById("programas");
                foo.appendChild(element);
            }
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
        "<div id=\"infoPrograma\"class=\"col-md-3\">\n" +
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
function mostrarPrograma(boton) {
    // Creamos la petición
    var req = new XMLHttpRequest();
    req.open("GET", "/programa/" + boton.name, false);
    req.send(null);

    // Comprobamos que la petición sea correcta
    if(req.status == 200) {
        // Eliminamos contenido previo
        // P.ej, datos de programa que estaban por click anterior
        $("#infoPrograma").remove();

        // Cogemos el cuerpo de la petición
        var res = JSON.parse(req.responseText);

        // Insertamos el html
        $("#contenido").append(presentacionPrograma(res));
    }
    else {
        alert("error en mostrarPrograma: " + boton.name);
    }
}