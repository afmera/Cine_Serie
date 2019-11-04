/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ANDRÉS FELIPE MERA TRÓCHEZ
 */

/**
 * Metodo para redireccionar la pagina y enviar un parametro por la url.
 * @param {type} target
 * @returns {undefined}
 */
function detalle(target) {
    var id = $(target).data("id");
    window.location.replace("?p=detalleContenido&id=" + id);
}
/**
 * Metodo para eliminar la tupla sleccionada.
 * @param {type} target
 * @returns {undefined}
 */
function eliminar(target) {
    var id = $(target).data("id");
    httpConnect("/contenido/" + id, null, "DELETE",
            function (r) {
                alert(r.message);
                cargarDatos();
            });
}
/**
 * Metodo para cargar los datos en la tabla inicializada en el html.
 * @returns {undefined} false.
 */
function cargarDatos() {
    httpConnect("/contenido", null, "GET", function (r) {
        var html = "";
        for (var i = 0; i < r.data.length; i++) {
            var entity = r.data[i];
            html += "<tr>";
            html += "<td>" + entity.conTitulo + "</td>";
            html += "<td>" + entity.conAnoLanzamiento + "</td>";
            html += "<td>" + entity.conLongitudMinutos + "</td>";
            html += "<td>" + entity.conSinopsis + "</td>";
            html += "<td>" + entity.conTipo + "</td>";
            html += "<td>" + entity.conCantidad + "</td>";
            html += "<td>";
            html += "<div data-id='" + entity.conId + "' class='material-icons delete' style='color:red'>delete</div>";
            html += "<div data-id='" + entity.conId + "' class='material-icons edit' style='color:green'>edit</div>";
            html += "</td>";
            html += "</tr>";
        }
        $("tbody").html(html);

        $(".delete").click(function () {
            if (confirm("Desea eliminar el recurso?")) {
                eliminar(this);
            }
        });
        $(".edit").click(function () {
            detalle(this);
        });

    });
}
/**
 * Metodo ´rincipal para cargar los datos.
 * @returns {undefined}
 */
$(function () {
    cargarDatos();
});