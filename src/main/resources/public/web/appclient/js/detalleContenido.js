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
 * Metodo para cargar los detalles de de la tupla seleccionada.
 * @returns {undefined} false.
 */
function cargarDetalle() {
    var id = getParameterByName("id");
    httpConnect("/contenido/" + id, null, "GET",function(r){
        if(r.status!==200){
            alert(r.message);
            window.location.replace("?p=listarContenido");
        }
        $("#titulo").val(r.data.conTitulo);
        $("#ano_lazamiento").val(r.data.conAnoLanzamiento);
        $("#duracion").val(r.data.conLongitudMinutos);
        $("#sinopsis").val(r.data.conSinopsis);
        $("#tipo").val(r.data.conTipo);
        $("#cantidad").val(r.data.conCantidad);
        $("#id").val(id);
    },function(e){
        alert(e);
        window.location.replace("?p=listarContenido");
    });
}
/**
 * Metodo principal para gestionar la tupla seleccionada.
 * @returns {undefined} false.
 */
$(function () {
    cargarDetalle();
    $("#frmUpdate").submit(function(){
        var entity = new Object();
        entity.conTitulo = $("#titulo").val();
        entity.conAnoLanzamiento = $("#ano_lazamiento").val();
        entity.conLongitudMinutos = $("#duracion").val();
        entity.conSinopsis = $("#sinopsis").val();
        entity.conTipo = $("#tipo").val();
        entity.conCantidad = $("#cantidad").val();
        var jEntity = JSON.stringify(entity);
        var id=$("#id").val();
        httpConnect("/contenido/"+id,jEntity,"PUT",function(r){
            alert(r.message+" - Titulo del contenido "+r.data.conTitulo);
            window.location.replace("?p=listarContenido");
        });
        return false;
    });
});