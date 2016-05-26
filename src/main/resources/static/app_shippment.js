    //////////////////////////////////////////////////////////////////////
    //Below are Global Variables to  js functions in this app_shippment.js
    //////////////////////////////////////////////////////////////////////
    var shipsheet = "";
    var id=0;
    var shipID = "shipment"+id;
    var counter = 0;
    var number = 0;
    //////////////////////////////////////////////////////////////////////
    function alertingMessage() {
        alert("i am here for you!!");
    }
    function getNumberOfCategories() {
       var noCat = $('.numberOfCategories').text();
       return (noCat);
    }
    function forEach(list,callback) {
      var length = list.length;
      for (var n = 0; n < length; n++) {
        callback.call(list[n]);
      }
    }
    function stopSumbitOnEter() {
        var chCode = ('charCode' in event) ? event.charCode : event.keyCode;
         if ( chCode == 13 ) {
            event.preventDefault();
            alert("testsing");
            return false;
         }
    }

    function calculateAssignedQTY(shipQTY_id) {
        //alert("i am here: " + shipQTY_id.parentNode.parentNode.id);
        //alert("i am here: " + $(shipQTY_id).parentsUntil("tbody")[1].id);
        var shipQTY = $($(shipQTY_id).parentsUntil("tbody")[1]).find("input");
        var assignedQTY = 0;
        forEach(shipQTY, function(){
            assignedQTY = assignedQTY + Number(this.value);
            //alert("i am here : "+assignedQTY+" || " + $(this).val()); //var product_id = $("."+shipQTY_id).val(); alert($(this).parent().attr('class'));
        });
        $($($($(shipQTY_id).parentsUntil("tbody")[1]).find("p"))[4]).text(assignedQTY);

    }

    function addShippmentOrder(shipQTY_id, shipNumber_id) {
        var shipNo = $("."+shipNumber_id).val();
        var enabled = 1;
        var order_id = order.id;

        var products = "";
        var QTYlist = document.getElementsByClassName(shipQTY_id);

        $.each(QTYlist, function( index, qty ) {
            var productName = trans[index].product_name;
            var productQTY  = qty.value;
            if(products != "") {
                products = products + "||" + productName+"*"+productQTY;
            } else  products = productName+"*"+productQTY;
        });

        //alert(products);
        //prepareShip
        /*
            id int NOT NULL AUTO_INCREMENT,
            shipNo varchar(30) NOT NULL,
            products varchar(255) NOT NULL,
            enabled tinyint,
            order_id int NOT NULL,
            //////////////////////////////////
            weight float NOT NULL,
            consumer_id INT,
            customer_id INT,
            modifiedBy varchar(30),
            modifiedTime DATETIME,
            PRIMARY KEY (id)
        */
        var updateShippment =  0;
        var seperator = "-";
        var shippmentDetails = shipNo +seperator+ products +seperator+ order_id +seperator+ enabled;
        //Below: Ajax call to update Shippment.
        var ajaxUpdateShippmentURL = "/insertNewShippmentURL?shippmentDetails="+shippmentDetails;
        $.ajax({url: ajaxUpdateShippmentURL, success: function(ajaxUpdateShippment_result){
            number++;
            updateShippment = ajaxUpdateShippment_result;
            //alert("insertNewShippmentURL - number: "+number +"|| result:"+ajaxUpdateShippment_result+"||updateShippment:"+updateShippment);
            if(updateShippment != 0) {
                //var ajaxGetShippmentURL = "/ajaxGetShippmentURL?="+shipNo;
                 //alert("ajaxGetShippmentURL - number: "+number +"|| updateShippment:"+updateShippment);
                 addNewShippmentIntoHtml(shipNo);
            }
        }});

    }

    function addNewShippmentIntoHtml(shipNo) {
        var ajaxGetShippmentURL = "/ajaxGetShippmentURL?shipNo="+shipNo;
        $.ajax({url: ajaxGetShippmentURL, success: function(addNewShippmentIntoHtml_result){
            var getShippment = addNewShippmentIntoHtml_result;
            var shipNo = getShippment.shipNo;
            var weight= getShippment.weight;
            var products = getShippment.products;
            var enabled = getShippment.enabled;
            var order_id = getShippment.order_id;
            var shipContent = '<p>'+shipNo+'</p>'+'<p>'+order_id+'</p>'+'<p >'+weight+'</p>'+'<p>'+enabled+'</p>'+'<p>'+products+'</p><br/>';
            var content = "<div class='divSelection divSelectionNew'>"+shipContent+"</div>";
            //alert("add into div list");
            $( "div.orderShippment" ).prepend(content);
        }});
    }
    function loadExistingShippments(id) {
        //var order_content = "<div class='divSelection' id="+shipID+" onlick='addTransactionSelection()'><input type='text' name='shippmentNumnber' onkeypress='stopSumbitOnEter()'/><input type='radio' name='shippmentDiv' checked/></div>";
        //alert("order.id:" + id);
        var content = null;
        var testURL    = "/ajaxShippmentsListURL?id="+id;
        $.ajax({url: testURL, success: function(loadExistingShippments_result){
            var ShippmentsList = loadExistingShippments_result;
            //alert(ShippmentsList);
            forEach(ShippmentsList, function(){
                var shipNo = this.shipNo;
                var weight= this.weight;
                var products = this.products;
                var enabled = this.enabled;
                var order_id = this.order_id;
                var shipContent = '<p>'+shipNo+'</p>'+'<p>'+order_id+'</p>'+'<p >'+weight+'</p>'+'<p>'+enabled+'</p>'+'<p>'+products+'</p><br/>';
                content = "<div class='divSelection'>"+shipContent+"</div>";
                $( "div.orderShippment" ).append(content);
            });
        }});
    }

    $( document ).ready(function() {
        loadExistingShippments(order.id);

        $('#icol').click(function(){
            //if($('#col').val()){
                $('#mtable tr').append($("<td>"));
                $('#mtable thead tr>td:last').html('<input name=shipNumber class=shipNumber_'+counter+' type=text size=20 onkeypress=stopSumbitOnEter()  onclick=addShippmentOrder("shipQTY_"+'+counter+',"shipNumber_"+'+counter+') />');
                $('#mtable thead tr>td:last input').val($('#col').val());
                $('#col').val("");

                //$('#mtable thead tr>td:last').html(('<input type="text"/>');
                //$('#mtable tbody tr:last td:first input').val($('#row').val());
                $('#mtable tbody tr').each(function(){
                    $(this).children('td:last').append($('<input name="shipQTY_'+counter+'" class="shipQTY_'+counter+'" type="number" value="0" size="4" onchange=calculateAssignedQTY('+"this"+') onkeypress="stopSumbitOnEter()">'))
                });
                counter++;
            //}else{alert('Enter Text');}
        });
        $('#irow').click(function(){
            if($('#row').val()){
                $('#mtable tbody').append($("#mtable tbody tr:last").clone());
                //$('#mtable tbody').append($("#mtable tbody tr:last").append("<td><input type='text'/></td>"));
                //$('#mtable tbody tr:last td:first').html(('<input type="text"/>').val($('#row').val()));
                $('#mtable tbody tr:last td:first').html(('<input name="shipQTY" type="text" onkeypress="stopSumbitOnEter()"/>'));
                $('#mtable tbody tr:last td:first input').val($('#row').val());
            }else{alert('Enter Text');}
        });

        $('.addShippmentButton').click(function() {
            ///////////////////////////////////////////////////////////////////////////////////////
            var trans_dropdown_potions = "";

            forEach(trans, function(){
                trans_dropdown_potions = trans_dropdown_potions + "<option value="+this.product_name+">"+this.product_name+"</option>";
            });
            var trans_dropdown = "<select>" + trans_dropdown_potions + "</select>";

            //        forEach(trans, function(){
            //            trans_content = trans_content + trans_dropdown + "</br>";
            //        });
            var trans_content = "<table><tr><td><div>"+ trans_dropdown +"</div></td><td><input type='number' size='4' id='trans_qty' name='trans_qty' min='1' max='10'/></td></tr></table>";
            temp = trans_content;
            //////////////////////////////////////////////////////////////////////////////////////
            var order_content = "<div class='divSelection' id="+shipID+" onlick='addTransactionSelection()'><input type='text' name='shippmentNumnber' onkeypress='stopSumbitOnEter()'/><input type='radio' name='shippmentDiv' checked/></div>";

            $( "div.orderShippment" ).append(order_content);

            id++;
            shipID = "shipment"+id;
        });
        $('.addProductButton').click(function() {
            shipsheet = $("input:checked").parent();
            $(shipsheet).append(temp);
        });/**/ //addShippmentButton() and addProductButton() commented and disabled with addTransactionSelection()
    });

    function addTransactionSelection() {
            //alert("message: " + message+"||"+order.total_price+"||"+order.id);
            //alert("transactions: "+trans.length+"||");

            var trans_dropdown_potions = "";

            forEach(trans, function(){
                trans_dropdown_potions = trans_dropdown_potions + "<option value="+this.product_name+">"+this.product_name+"</option>";
            });
           var trans_dropdown = "<select>" + trans_dropdown_potions + "</select>";

    //        forEach(trans, function(){
    //            trans_content = trans_content + trans_dropdown + "</br>";
    //        });
            var trans_content = "<table><tr><td><div>"+ trans_dropdown +"</div></td></tr></table>";
            var temp = trans_content;
    };
    /**/