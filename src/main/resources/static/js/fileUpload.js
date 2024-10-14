$(document).ready(function(){
    var title = $(".order-last h3").text();

    var params = getParameterByName("id");
    $("#exchangeCode").val(params);
    
    /*
    $('.exCode').on('click',function(e){ 
        e.preventDefault();
        $("#up-form").show();
        $("#up-data").hide();
        $(".order-last h3").html("Upload the CSV File");
        console.log(params);
    });
    */
    //console.log(params);

    //console.log(title);
    /*
    $('.exCode').on('click',function(e){ 
        e.preventDefault();
        $("#up-form").show();
        $("#up-data").hide();
        $(".order-last h3").html("Upload the CSV File");
        var url = $(this).attr('href'); 
        //var params = url.replace(/#/g, "");
        var params = getParameterByName("id");
        console.log(params);
        $("#exName").val(params);
        console.log(url); 
    });
    */

    $('form').on('submit',function(e){
        e.preventDefault();
        var data = new FormData($(this)[0]);
        $.ajax({
            url: "/utils/upload",
            data: data,
            type: 'post',
            contentType: false,
            processData: false,
        }).done(function(resp){
            //console.log(resp);
            //title = $(".order-last h3").text();
            
            $("#up-form").hide();
            $("#up-data").show();
            $("#up-data").html(resp);
            $(".card-header").html("");
            $(".order-last h3").html("Uploaded File Statistics");
            view_data(resp);
        }).fail(function(){
            alert("Error Getting from server");
        });
   
    });

    function view_data(resp){
        var tbl = "#error_data_tbl";
        if($(tbl).length){
            var uid = $('#id').val();
            var reportColumns = $("#reportColumns").val();
            var cols = JSON.parse(reportColumns);
            var url = "/errorReport?id=" + uid;
            get_dynamic_dataTable(tbl, url, cols);
        }
    }
});