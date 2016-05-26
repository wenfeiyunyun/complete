function alerting(id) {
    alert("i am here!!" + id);
}

function forEach(list,callback) {
  var length = list.length;
  for (var n = 0; n < length; n++) {
    callback.call(list[n]);
  }
}

$( document ).ready(function() {
    $('.addShippmenta').click(function() {
        alert("addShippment is triggered");
        //$(".testingDIV").toggle();
    });

    $('.add').click(function () {
        alert("add is triggered");
        var order;
        var parent = $(this).parent().prop('className');
        var id = parent.split('_')[1];
        if ( $("tr").hasClass(id+"_details") ) {
            //alert("i have the "+id+"_details"+" class, so not add more TR in the table");
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
                content = content +'<td colspan=2>'+this.product_name+"</td><td><span id="+this.id+">0</span>"+" / "+this.quantity+'</td>';
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
            content = content + '<p class="addShippmenta">button here testing </p>';
            content = content + '<div class=testingDIV>This is a paragraph with little content.This is another small paragraph.</div>'

            content = content + '</div></td></tr>';


            content = content + '</table>';
            content = content + '</td></tr>';
            $(content).insertAfter(("."+parent));
//            var object = document.getElementById("temp");
//            object.className = (parent+"_details");
//            object.id = "";

            //////////////////////////////////////////////////////////////////////////////////
//            var button_content = null;
//            button_content =
//            $(button_content).insertAfter(("."+parent));
            /////////////////////////////////////////////////////////////////////////////////
        }});
    });

    $("button").click(function(){
    });
});