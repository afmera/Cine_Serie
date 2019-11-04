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
 * Metodo principal para la gestion de la tupla selecionada.
 * @returns {undefined} false.
 */
$(function () {
    $("#frmCrear").submit(function () {
        var entity = new Object();
        entity.genNombre = $("#nombre").val();
        //************
        var jEntity = JSON.stringify(entity);
        //************
        httpConnect("/genero", jEntity, "POST", function (r) {
            alert(r.message + "-" + r.data.genNombre);
            $("button[type=reset]").click();
        });
        //************
        return false;
    });
});