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
///**
// * Metodo para verificar cula fu la calificacion del usuario.
// * @returns {String} de tipo String.
// */
//function verrifiCarcalidicacion()
//{
//    var resultado = "";
//    var valor5 = $("#estrellas").val();
////    var valor4 = $("#estrellas").val().checked;
////    var valor3 = $("#estrellas").val().checked;
////    var valor2 = $("#estrellas").val().checked;
////    var valor1 = $("#estrellas").val().checked;
//    if (valor5 === true)
//    {
//        resultado = "5";
//    } 
//    alert(valor5.length);
////    else if (valor4 === true)
////    {
////        resultado = "4";
////    } 
////    else if (valor3 === true)
////    {
////        resultado = "3";
////    } 
////    else if (valor2 === true)
////    {
////        resultado = "2";
////    } 
////    else if (valor1 === true)
////    {
////        resultado = "1";
////    } 
////    else 
////    {
////        resultado = "0";
////    }
//    return resultado;
//}

/**
 * Metodo principal para la gestion de la tupla selecionada.
 * @returns {undefined} false.
 */
$(function () {
    cargarContenido();
    $("#frmCrear").submit(function () {
        var entity = new Object();
//        entity.favCalificacion = verrifiCarcalidicacion();//llamo el metodo.
var valor5 = $("#estrellas").val();
        alert(valor5);
        entity.favCalificacion = $("#estrellas").val();
        entity.favComentario = $("#comentario").val();
        entity.fkContenido = {
            "conId": $("#contenido").val()
        };
        //************
        var jEntity = JSON.stringify(entity);
        //************
        httpConnect("/favorita", jEntity, "POST", function (r) {
            alert(r.message + " - Calificacion: " + r.data.favCalificacion);
            $("button[type=reset]").click();
        });
        //************
        return false;
    });
});