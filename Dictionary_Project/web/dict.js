/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

"use strict";

(function () {

    function search() {
     
        if($("#inpText").val()===""){
          $("#word").html("<div class='pleaseEnter'>Please enter the word</div>");
        $("#meaning").empty();
        }
        else{
            
            $.ajax("dictServlet",{
                data: {"find": $("#inpText").val()},
                type: "post"
            }).done(success)
              .fail(error);
        }

    }
    function error(){
        
        $("#word").html("<div class='pleaseEnter'>Sorry!!!Could not find this word</div>");
        $("#meaning").empty();
    }

    function success(json) {
        $("#word").empty();
        $("#meaning").empty();
        if (json.result.length === 0) {
            $("#word").html("\" " + $("#inpText").val() + " \" was not found!");
            $("#meaning").empty();
        } else {

            var total = json.result.length;

            $("#word").html(json.result[0].word);
            $("#meaning").empty();

            var orderedlist = $("<ol>");
            $("#meaning").append(orderedlist);

            var index = 0;
            for (index = 0; index < total; index++) {
                if (json.result[index].type === "") {
                    $("#meaning").find("ol").append("<li>" + json.result[index].type + "</li>");
                } else {
                    $("#meaning").find("ol").append("<li><strong>("
                            + json.result[index].type + ")</strong> "
                            + json.result[index].definition + "</li>");
                }
            }
        }
    }
    
    $(document).ready(function () {
        $(function () {
        $('#load').hide();
        $(document).ajaxStart(function () {
            $('#load').show();
        }).ajaxStop(function () {
            $('#load').hide();
        });
    });

        $("#buttonClick").click(search);

        // Call search when <Enter> is pressed
        $("#inpText").keypress(function (event) {
            if (event.which === 13) {
                search();
                $(this).select();
            }
        });

    });
})();

