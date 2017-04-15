function alerting(id) {
    alert("i am here!!" + id);
}

function forEach(list,callback) {
  var length = list.length;
  for (var n = 0; n < length; n++) {
    callback.call(list[n]);
  }
}

function createJSON() {
    var jsonObj = {};
    var jsonStr = '{"items":[]}';
    var jsonObj = JSON.parse(jsonStr);
    $("input[class=searchCiteria]").each(function() {
        jsonObj = JSON.parse(jsonStr);
        var field = $(this).attr("name");
        var value = $(this).val();
        if(value != null && value != "") {
            item = {};
            item [field] = value;
            jsonObj.items.push(item);
            jsonStr = JSON.stringify(jsonObj);
        }
    });
    console.log(jsonObj);
    jsonString = JSON.stringify(jsonObj);
    console.log(jsonString);
    return jsonString;
}

$( document ).ready(function() {
    $("#orderTableContent").tablesorter();
    $('.addShippmenta').click(function() {
        alert("addShippment isz triggered");
    });

    $('.add').click(function () {
        //alert("add is triggered");
        var order;
        var parent = $(this).parent().prop('className');
        var id = parent.split('_')[1];
        if ( $("tr").hasClass(id+"_details") ) {
            //alert("i have the "+id+"_details"+" class, so not add more TR in the table");
            $('.'+id+'_details').remove();
            return 0;
        }

        //Below: Ajax call to retrieve Order Transactions List to display under current selected order.
            var testURL    = "/ajaxTransListURL?id="+id;
            $.ajax({url: testURL, success: function(result){
            trans = result;

            var content = null;
            var counter = 0;
            var rows    = 3;
            var left    = rows - (counter/rows);
            content = '<tr class='+id+"_details"+'><td colspan=9><table class="table table-striped table-hover"><tr><th colspan=2>Production</th><th>QTY</th><th colspan=2>Production</th><th>QTY</th><th colspan=2>Production</th><th>QTY</th></tr>';

            forEach(trans, function(){
                if(counter==0) {
                    content = content + '<tr>';
                }else if(counter!=0 && (counter%rows)==0){
                    content = content + '</tr><tr>';
                }
                content = content +'<td colspan=2>'+this.name+"</td><td><span id="+this.id+">0</span>"+" / "+this.quantity+'</td>';
                if(counter == trans.length-1) {
                    for (x = 0; x < left; x++) {
                        content = content + "<td></td><td></td>";
                    }
                    content = content + '</tr>';
                }
                counter++;
            });

            content = content + '<tr><td colspan=9><div>'; //<td class="add"> ADD </td>
//          content = content + '<button type="button" class="addShippment">button here testing </button>';
/*
            content = content + '<p class="addShippmenta">button here testing </p>';
            content = content + '<div class=testingDIV>This is a paragraph with little content.This is another small paragraph.</div>'
*/
            content = content + '</div></td></tr>';
            content = content + '</table>';
            content = content + '</td></tr>';
            $(content).insertAfter(("."+parent));
        }});
    });

    $("#clear").click(function(){

    });

    $("#newer").click(function(){
        var id = "";//$($("#orderTableContent tr:first-child")[1]).attr("class").split("_")[1];
        var largestNo = 0;
        var lengthTR = $("#orderTableContent tr").length;
        for(var i=1; i<lengthTR; i++){
            var temp = $($("#orderTableContent tr")[i]).attr("class").split("_")[1];
            if(largestNo < temp){
                largestNo = temp;
            }
        }
        id = largestNo;
        $("#newer_id").val(id);
        $("#older_id").val("");
        $("#searchString").val(createJSON());
        $( "#orderSearchForm" ).submit();
    });
    $("#older").click(function(){
        var id = $($("#orderTableContent tr:last-child")[1]).attr("class").split("_")[1];
        $("#older_id").val(id);
        $("#newer_id").val("");
        $("#searchString").val(createJSON());
        $( "#orderSearchForm" ).submit();
    });

    $("#search").click(function() {
       $("#newer_id").val("");
       $("#older_id").val("");
       $("#searchString").val(createJSON());
       $( "#orderSearchForm" ).submit();
    });

    $("#latest").click(function(){
        var showLatestOrdersURL = "/greetingsURL";
        window.open(showLatestOrdersURL,"_self");
    });

    $("#refund").click(function(){
        var order_id = "1000055";
        var fee = 0.02;
        var ajaxRefundURL = "/ajaxRefundURL?order_id="+order_id+"&fee="+fee;
        $.ajax({url: ajaxRefundURL, success: function(ajaxRefund_result){
            ajaxRefund = ajaxRefund_result;
        }});
    });

    $("#refundInfo").click(function(){
        var refund_id = "11000055_1491047865675";
        var ajaxRefundInfoURL = "/ajaxRefundInfoURL?refund_id="+refund_id;
        $.ajax({url: ajaxRefundInfoURL, success: function(ajaxRefundInfo_result){
            ajaxRefundInfo = ajaxRefundInfo_result;
        }});
    });
})