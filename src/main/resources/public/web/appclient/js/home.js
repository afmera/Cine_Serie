/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Metodo para cargar los datos en la tabla inicializada en el html.
 * @returns {undefined} false.
 */
function cargarDatos() {
    httpConnect("/contenido", null, "GET", function (r) {
        var html = "";
        html += "<div class='jumbotron'>";
        html += "<div class='container'>";
        html += "<div class='row'>";
        for (var i = 0; i < r.data.length; i++) {
            var entity = r.data[i];
            html += "<div class='col-md-4'>";
            html += "<h5>Titulo del Contenido</h5>";
            html += "<p>" + entity.conTitulo + "</p>";
            html += "<h5>Año de Lanzamiento</h5>";
            html += "<p>" + entity.conAnoLanzamiento + "</p>";
            html += "<h5>Tiempo de Duracion (Minutos)</h5>";
            html += "<p>" + entity.conLongitudMinutos + "</p>";
            html += "<h5>Sinopsis</h5>";
            html += "<p>" + entity.conSinopsis + "</p>";
            html += "<h5>Tipo de Contenido</h5>";
            html += "<p>" + entity.conTipo + "</p>";
            html += "<h5>Cantidad</h5>";
            html += "<p>" + entity.conCantidad + "</p>";
            html += "</div>";
        }
        html += "</div>";
        html += "</div>";
        html += "</div>";
        $("#contenido").html(html);
    });
}

/**
 * Metodo ´rincipal para cargar los datos.
 * @returns {undefined}  */
$(function () {
    cargarDatos();
    $("#frmConsultar").submit(function () {
        var entidad = new Object();
        entidad.conTitulo = $("#titulo").val();
        var jEntidad = JSON.stringify(entidad);

        httpConnect("/byContenidoTitulo" + entidad, jEntidad, "GET", function (r) {
            $("button[type=reset]").click();
            if (r.status !== 200) {
                alert(r.message);
                window.location.replace("?p=index");
            }
            var html = "";
            html += "<div class='jumbotron'>";
            html += "<div class='container'>";
            html += "<div class='row'>";
            for (var i = 0; i < r.data.length; i++) {
                var entity = r.data[i];
                html += "<div class='col-md-4'>";
                html += "<h5>Titulo del Contenido</h5>";
                html += "<p>" + entity.conTitulo + "</p>";
                html += "<h5>Año de Lanzamiento</h5>";
                html += "<p>" + entity.conAnoLanzamiento + "</p>";
                html += "<h5>Tiempo de Duracion (Minutos)</h5>";
                html += "<p>" + entity.conLongitudMinutos + "</p>";
                html += "<h5>Sinopsis</h5>";
                html += "<p>" + entity.conSinopsis + "</p>";
                html += "<h5>Tipo de Contenido</h5>";
                html += "<p>" + entity.conTipo + "</p>";
                html += "<h5>Cantidad</h5>";
                html += "<p>" + entity.conCantidad + "</p>";
                html += "</div>";
            }
            html += "</div>";
            html += "</div>";
            html += "</div>";
            $("#contenido").html(html);

        });
        return false;
    });
});
