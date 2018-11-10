var req = new XMLHttpRequest();
req.open('GET', '/numregistros', false);
req.send(null);
if (req.status == 200) {
    var res = JSON.parse(req.responseText);
    var texto = "<h5>Actualmente hay " + res.numRegistros + " registros en la base de datos</h5>";
    document.getElementById('numRegistros').innerHTML = texto;
}
else {
    document.getElementById('numRegistros').innerHTML = "<p>ADIOS</p>"
}
