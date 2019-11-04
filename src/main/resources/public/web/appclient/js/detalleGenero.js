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
    httpConnect("/genero/" + id, null, "GET",function(r){
        if(r.status!==200){
            alert(r.message);
            window.location.replace("?p=listarGenero");
        }
        $("#nombre").val(r.data.genNombre);
        $("#id").val(id);
    },function(e){
        alert(e);
        window.location.replace("?p=listarGenero");
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
        entity.genNombre = $("#nombre").val();
        var jEntity = JSON.stringify(entity);
        var id=$("#id").val();
        httpConnect("/genero/"+id,jEntity,"PUT",function(r){
            alert(r.message+" - Nombre "+r.data.genNombre);
            window.location.replace("?p=listarGenero");
        });
        return false;
    });
});
