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
    httpConnect("/contenido", null, "GET", function (r) {
        var html = "<select id='contenido' name='contenido' class='form-control' required>";
        html += "<option value=''>[SELECCIONAR OPCION]</option>";
        for (var i = 0; i < r.data.length; i++) {
            var o = r.data[i];
            html += "<option value='" + o.conId + "'>" + o.conTitulo + "</option>";
        }
        html += "</select>";
        $("#contentContenido").html(html);
    });
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
 * Metodo principal para la gestion de la tupla selecionada.
 * @returns {undefined} false.
 */
$(function () {
    cargarContenido();
    cargarGenero();
    $("#frmCrear").submit(function () {
        var entity = new Object();
        entity.fkContenido = {
            "conId": $("#contenido").val()
        }
        entity.fkGenero = {
            "genId": $("#genero").val()
        }
        //************
        var jEntity = JSON.stringify(entity);
        //************
        httpConnect("/contenidogenero", jEntity, "POST", function (r) {
            alert(r.message + "-" + r.data.fkContenido.conTitulo + " - " + r.data.fkGenero.genNombre);
            $("button[type=reset]").click();
        });
        //************
        return false;
    });
});