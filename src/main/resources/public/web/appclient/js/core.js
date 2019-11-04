function loadHTML(zona, obj, callback) {
    $.ajax({
        url: zona,
        success: function (html) {
            $(obj).html(html);
            var contentHtml = $(html);
            try {
                callback(contentHtml);
            } catch (e) {
                console.log(e);
            }
        },
        error: function (e, err) {
            alert("Error al cargar el html "+zona);
        }
    });
}

function getParameterByName(param) {
    param = param.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + param + "=([^&#]*)"),
            results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

function renderView() {
    loadHTML("template/master.html", $("body"), function (data) {
        //*******
        loadHTML("template/menu.html", $("#menu"), null);
        loadHTML("template/header.html", $("#cabecera"), null);
        loadHTML("template/footer.html", $("#pie_pagina"), null);

        var subpagina = "home.html";
        if (getParameterByName("p") !== "") {
            subpagina = getParameterByName("p") + ".html";
        }
        loadHTML("views/" + subpagina, $("#contenido_principal"), null);
        //*******
    });
}

function httpConnect(route, data, method, success = null, error = null, beforeSend = null, complete = null) {
    $.ajax({
        type: method,
        url: route,
        //context: data,
        data: data,
        dataType: "json",
        beforeSend: function () {
            console.log("Metodo:" + method);
            console.log(route, data);
            if (beforeSend !== null) {
                beforeSend();
            }
        },
        complete: function () {
            if (complete !== null) {
                complete();
            }
        },
        success: function (retorno) {
            console.log(retorno);
            if (success !== null) {
                success(retorno);
            }
        },
        error: function (e, err) {
            var status = e.status;
            var title = e.statusText;
            var body = "<br>" + route + "<br>" + e.responseText;
            console.error("httpError:c1:", e);
            console.error("httpError:c2:", err);
            var r = {
                "status": status,
                "title": title,
                "message": body
            };
            //createMessage(r);
            if (error !== null) {
                error(e.responseText);
            }
        }
    });
}
