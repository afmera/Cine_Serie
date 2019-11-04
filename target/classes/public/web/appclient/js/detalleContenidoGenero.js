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
 * Metodo para cargar los datos de la entidad designada.
 * @returns {undefined} false.
 */
function cargarContenido() {
    var id = getParameterByName("id");
    alert(id);
    httpConnect("/contenidogenero/" + id, null, "GET", function (r) {
        if (r.status !== 200) {
            alert(r.message);
            window.location.replace("?p=listarContenidoGenero");
        }
        var html = "<select id='contenido' name='contenido' class='form-control' required>";
//        html += "<option value=''>[SELECCIONAR OPCION]</option>";
        if (r.data !== null)
        {
            html += "<option value='" + r.data.fkContenido.conId + "'>" + r.data.fkContenido.conTitulo + "</option>";
        }
        html += "</select>";
        $("#contentContenido").html(html);
    });
    return id;
}

/**
 * Metodo para cargar los datos de la entidad designada.
 * @returns {undefined} false.
 */
function cargarGenero() {
    httpConnect("/genero", null, "GET", function (r) {
        var html = "<select id='genero' name='genero' class='form-control' required>";
        html += "<option value=''>[SELECCIONAR OPCION]</option>";
        for (var i = 0; i < r.data.length; i++) {
            var object = r.data[i];
            html += "<option value='" + object.genId + "'>" + object.genNombre + "</option>";
        }
        html += "</select>";
        $("#contentGenero").html(html);
    });
}

/**
 * Metodo principal para gestionar la tupla seleccionada.
 * @returns {undefined} false.
 */
$(function () {
    var id = cargarContenido();
    cargarGenero();
    $("#frmUpdate").submit(function () {
        var entity = new Object();
        entity.fkContenido = {
            "conId": $("#contenido").val()
        };
        entity.fkGenero = {
            "genId": $("#genero").val()
        };
        //************
        var jEntity = JSON.stringify(entity);
        httpConnect("/contenidogenero/" + id, jEntity, "PUT", function (r) {
            alert(r.message);
            window.location.replace("?p=listarContenidoGenero");
        });
        return false;
    });
});