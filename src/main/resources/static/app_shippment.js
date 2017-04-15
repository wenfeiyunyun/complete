    //////////////////////////////////////////////////////////////////////
    //Below are Global Variables to  js functions in this app_shippment.js
    //////////////////////////////////////////////////////////////////////
    var shipsheet = "";
    var shipID = "shipment"+id;
    var id=0;   var counter = 0;    var number = 0;
    var seperator = "_"; var customer_seperator="-"; var joint = "||";
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
            //alert("you can press ENTER");
            return false;
         }
    }

    function selectOrderOwner_w() {
        var testURL    = "/ajaxCustomerAutoSearchURL?keyword="+$("#orderOwner").val();
        $('#orderOwner').autocomplete({
            serviceUrl: testURL,
            delimiter: ",",
            transformResult: function(response) {
                return {
                    //must convert json to javascript object before process
                    suggestions: $.map($.parseJSON(response), function(item) {
                        //if(item == null) return { value: null, data: null };
                        return { value: item.tagName, data: item.id };
                    })
                };
            }
        });
    }

    function updateOrderOwner() {
        var order_id = order.id;
        var customer_name = $('#orderOwner').val();
        var customer_id = $('#orderOwnerId').val();

        //alert("testing i am here"+order.id+"||"+customer_name);
        var updateOrderOwnerURL = "/updateOrderOwnerURL?order_id="+order_id+"&customer_name="+customer_name+"&customer_id="+customer_id;
        var orderOwnderShip = 0;
        $.ajax({url: updateOrderOwnerURL, success: function(updateOrderOwner_result){
            orderOwnderShip = updateOrderOwner_result;
            if(orderOwnderShip > 0) {
                var orderOwner = $('input#orderOwner').val();
                $("span#orderOwner_disabled").remove();
                $("<span id='orderOwner_disabled' class='orderOwner'name='orderOwner' ondblclick='changeOwnerShip()'/>").insertAfter("#orderNo");
                $("span#orderOwner_disabled").text(orderOwner);
                $('input#orderOwner').remove();
                $("#orderOwnerShipButton").remove();
                //$("<span>testing</span>").insertBefore("#orderOwnerShipButton");
                //$(this).html('<span>testing!!!</span>');
                location.reload();
            }
        }});
    }

    function selectOrderOwner() {
        var keyword = "";
        setTimeout(function() {
            keyword = $("#orderOwner").val(); //do something special
            if(keyword.trim().length==0) return;
            var ajaxCustomerAutoSearchURL = "/ajaxCustomerAutoSearchURL?keyword="+keyword;
            var orderOwnerList = [];
            $.ajax({url: ajaxCustomerAutoSearchURL, success: function(selectOrderOwners_result){
                forEach(selectOrderOwners_result, function(){
                    //orderOwnerList.push(this.tagName + "||" + this.id);
                    orderOwnerList.push(this.tagName);
                });
                //alert(orderOwnerList[0]+"|"+orderOwnerList[1]+"|"+orderOwnerList[2]+"|"+orderOwnerList[3]+"|"+orderOwnerList[4]+"|"+orderOwnerList[5]);
                $( '#orderOwner').autocomplete({
                  source: orderOwnerList
                });
            }});
         }, 800);
    }

    function calculateAssignedQTY(shipQTY_id) {
        var shipQTY = $($(shipQTY_id).parentsUntil("tbody")[1]).find("input");
        var assignedQTY = 0;
        forEach(shipQTY, function(){
            assignedQTY = assignedQTY + Number(this.value);
        });
        //$($($($(shipQTY_id).parentsUntil("tbody")[1]).find("p"))[4]).text(assignedQTY);
        $($($(shipQTY_id).parentsUntil("tbody")[1]).find(".assinged")).text(assignedQTY);
    }

    function addShipOrderIntoTable(shipQTY_id, shipNumber_id) {

/*        if($("p.assinged").text() == 0 || $("select#weight").val() == 0) {
            alert("请选择物流 以及 输入货物重量！！！");
        }else {*/
            var shipNo = $("."+shipNumber_id).val();
            var enabled = 1;
            var order_id = order.id;
            var delivery_company = $("#weight").val().split("|")[0];
            var delivery_price   = $("#weight").val().split("|")[1];
            var weight           = $($("."+shipQTY_id).last()).val();
            //var productType    = $("#productType:checked").val();
            var productType      = delivery_company.split(".")[0];
            var comments         = $("#shippment_comments").val();
            //var radioValue = $("input[name='gender']:checked"). val()
            var products = "";
            var QTYlist = document.getElementsByClassName(shipQTY_id);

            trans.push({
                product_name: delivery_company,
                quantity    : weight,
                product_id  : "999",
                deal_price  : delivery_price,
                id          : "kg"
            });

            $.each(QTYlist, function( index, qty ) {
                var productName = trans[index].product_name;
                var productQTY  = qty.value;

                if(productQTY != 0 && productName != "Bubble Wrap" && productName != "BOX Fee" && productName != "Bank fee" && productName != "insurace fee" && productName != "dafafei") //产品名不能为 代发， bubble wrap, box fee, insruance etc ..
                    {
                    if(products != "") {
                        products = products +joint+ productQTY+"*"+productName;
                    } else  products = productQTY+"*"+productName;
                }
            });
            var updateShippment =  0;
            var shippmentDetails = shipNo +seperator+ products +seperator+ weight +seperator+ order_id +seperator+ enabled +seperator+ productType +seperator+ comments;
            addNewShippmentIntoHtml_new(shippmentDetails);
        //}
    }

    function saveNewShippment(shippmentDetails) {
        //alert("testing: "+shippmentDetails);
        //Below: Ajax call to add New Shippment.
        var consumer = $( "#consumer" ).val();
        var address  = $( "#address_detais" ).val();
        shippmentDetails = shippmentDetails +seperator+ consumer +seperator+ address;
        ///alert("testing: "+shippmentDetails);
        var ajaxUpdateShippmentURL = "/ajaxInsertNewShippmentURL?shippmentDetails="+shippmentDetails;
        $.ajax({url: ajaxUpdateShippmentURL, success: function(ajaxUpdateShippment_result){
            number++;
            updateShippment = ajaxUpdateShippment_result;
            if(updateShippment != 0) {
                //addNewShippmentIntoHtml(shipNo); //another ajax call to open the new shippment just created
            }
            //loadNewCreatedShippment($().);
            location.reload();
        }});
    }

    function addNewShippmentIntoHtml_new(shippmentDetails) {
        var getShippment = shippmentDetails.split(seperator);
        var shipNo   = getShippment[0];

        var isExistingShippment = false;
        var existingShippments = $(".existingShippment");
        forEach(existingShippments, function(){
            if(shipNo == $(this).text()) {
                isExistingShippment = true;
                return false; //If condition meets, return false to break the loop, and continue on outside of loop.
            }
        });
        if(isExistingShippment == true) return 0;

        var products = getShippment[1];
        var weight = getShippment[2];
        var order_id  = getShippment[3];
        var enabled  = getShippment[4];
        var productType = getShippment[5];
        var comments    = getShippment[6];
        //var weightContent = "weight: <input type=text value='"+weight+"' size=3 class=ontheleft/>";
        if(weight == 0 || weight == null) weightContent = "weight: <input type=text disabled size=3 value='"+weight+"'/>";
            else weightContent = "weight: <input type=text disabled size=3 value='"+weight+"'/>";
        var productsContent = parserShipContent(products);
        var shippment_comments = '<div>注释:  <b>'+comments+"</b></br></div>";

        var shipNoButton = "<button class=button-border toggleModal onclick=popupShipmentDetails('"+shipNo+"')><span class=icon></span>Freight</button>";
        var shipContent = "<p><b>Ship NO: <b class='existingShippment'>"+shipNo+"</b> || "+weightContent+" || "+shipNoButton+"</p><div id=consumer_details><div id=reciever></div><div id=address></div></div>"+productsContent+shippment_comments+"<div>产品种类: <b>"+productType+"</b></div><button onclick=\"saveNewShippment('"+shippmentDetails+"')\">Save</button>";
        var content = "<div class='divSelection divSelectionNew' id='"+shipNo+"'>"+shipContent+"</div>";
        $( "div.orderShippment" ).prepend(content);
        //var customer_id = $("span#orderOwner_disabled").text();
        loadConsumers();
    }

    function loadAddresses(consumer_option) {
        var consumer_id = consumer_option.split("|")[0];
        //alert("iamhere!!! "+ consumer_id);
        var address_option = "<option value=''></option>";
        var address_details = "";
        var ajaxAddressOptionsURL = "/ajaxAddressOptionsURL?consumer_id="+consumer_id;
            $.ajax({url: ajaxAddressOptionsURL, success: function(loadAddresses_result){
                var getAddressOptions = loadAddresses_result;
                forEach(getAddressOptions, function(){
                  //alert("this is: " + this);
                  var option = this.id+"|"+this.address;
                  //var option = this.name+"|"+this.wechat_id+"|"+this.customer_id;
                  address_option = address_option + "<option value='"+option+"'>"+option+"</option>";
                });
               address_details =
                    "<div>地 址：<b><select id='address_detais'>" +
                    address_option +
                    "</select><b></div>";
               $("div#address").html(address_details);
            }});
    }

    function loadConsumerAndDisplay(consumerID) {
        var consumer_id = consumerID.split("|")[0];
        var ajaxLoadConsumerURL = "/ajaxLoadConsumerURL?consumer_id="+consumer_id;
        $.ajax({url: ajaxLoadConsumerURL, success: function(loadConsumer_result){
            var getConsumer = loadConsumer_result;
            //this.id+"|"+this.name+"|"+this.phone+"|"+this.personal_id+"|"+this.wechat_id+"|"+this.comment+"|"+this.customer_id;
            $("input#consumer_id").val(getConsumer.id);
            $("input#consumer_name").val(getConsumer.name);
            //$("input#wechat_id").val(getConsumer.wechat_id);
            //$("input#customer_id").val(getConsumer.customer_id);
            $("input#consumer_phone").val(getConsumer.phone);
            $("input#consumer_identity").val(getConsumer.personal_id);
            //$("input#comment").val(getConsumer.comment);
        }});
    }

    function loadConsumers() {
        var customer_id = $("#orderOwnerId").val();
        //customer_id = customer_id.split("-")[2];
        var consumer_option = "<option value=''></option>";
        var consumer_details = "";
        var ajaxConsumerOptionsURL = "/ajaxConsumerOptionsURL?customer_id="+customer_id;
            $.ajax({url: ajaxConsumerOptionsURL, success: function(loadConsumers_result){
                var getConsumerOptions = loadConsumers_result;
                forEach(getConsumerOptions, function(){
                    //alert("this is: " + this);
                    var option = this.id+"|"+this.name+"|"+this.wechat_id+"|"+this.customer_id;
                    consumer_option = consumer_option + "<option value='"+option+"'>"+option+"</option>";
                });
                var consumer_id = this.id;
                consumer_details =
                    "<div>收件人：</button> <b><select id='consumer' onchange=loadAddresses(this.value)>" +
                    consumer_option +
                    "</select><b></div>";
                $("div#reciever").html(consumer_details);
            }});
    }

    function parserShipContent(shipContent) {
        var productsContent = "";
        var contentList = shipContent.split("||");
        for(key in contentList){
        //for(key=0; key<contentList.length-1; key++) {
            var productsName = contentList[key].split("*")[1];
            if(productsContent != "") //&& //产品不能为零  12*奶粉.澳邮
               //productsName.split(".")[0] != "奶粉" && productsContent.split(".")[0] != "杂物")//產品不能為物流 - 杂物 以及 奶粉
            {
                 productsContent = productsContent + "<tr><td>"+contentList[key].split("*")[1]+"</td><td>-</td><td>-</td><td>("+contentList[key].split("*")[0]+")</td></tr>";
            } else  productsContent = "<tr><td>"+contentList[key].split("*")[1]+"</td><td>-</td><td>-</td><td>("+contentList[key].split("*")[0]+")</td></tr>";
        }
        productsContent ="<table class='innerProductDiv' >"+productsContent+"</table>";
        return productsContent;
    }

    function loadExistingShippments(id) {
        var content = null;
        var weightContent = null;
        var testURL    = "/ajaxShippmentsListURL?id="+id;
        $.ajax({url: testURL, success: function(loadExistingShippments_result){
            var ShippmentsList = loadExistingShippments_result;
            forEach(ShippmentsList, function(){
                var ship_id = this.id;
                var shipNo = this.shipNo;
                var weight= this.weight;
                var products = this.products;
                var enabled = this.enabled;
                var order_id = this.order_id;
                var consumer = this.consumer_id;
                var address = this.address_id;
                var printTimes = this.printTimes;
                var productType = this.productType;
                var comments = this.comments;
                if(weight == 0 || weight == null) weightContent = "weight: <input type=text size=3 value='"+this.weight+"'/>";
                else weightContent = "weight: <input type=text disabled size=3 value='"+this.weight+"'/>";
                var productsContent = parserShipContent(products);
                var shipNoButton = "<button class=button-border toggleModal onclick=popupShipmentDetails('"+shipNo+"')><span class=icon></span>Freight</button>";
                var shipContent = '<p>Ship NO: <b class="existingShippment">'+shipNo+'</b> || '+weightContent+' || '+shipNoButton+'</p>'+
                                  '<div>'+productsContent +'</div>'+
                                  '<div>注释:  <b>'+comments+"</b></div>"+
                                  '<div>收件人:  <b>'+consumer +"</b></div>"+
                                  '<div>地    址:  <b>'+address +'</b></div><br/>'+
                                  "<div>产品种类:  <b>"+productType +"</b>   ||    打印次数:  <b>"+printTimes +"</b> || <button onclick=\"deleteShippment('"+ship_id+"')\">Delete</button></div><br/>";
                content = "<div class='divSelection'>"+shipContent +"</div>";
                $( "div.orderShippment" ).prepend(content);
            });
        }});
    }

    function loadNewCreatedShippment(shipNo) {
        var content = null;
        var weightContent = null;
        var testURL    = "/ajaxGetShippmentURL?shipNo="+shipNo;
        $.ajax({url: testURL, success: function(loadNewCreatedShippment_result){
            var Shippment = loadNewCreatedShippment_result;
            var shipNo = Shippment.shipNo;
            var weight= Shippment.weight;
            var products = Shippment.products;
            var enabled = Shippment.enabled;
            var order_id = Shippment.order_id;
            var consumer = Shippment.consumer_id;
            var address = Shippment.address_id;
            var printTimes = Shippment.printTimes;
            var productType = Shippment.productType;
            var comments = Shippment.comments;
            if(weight == 0 || weight == null) weightContent = "weight: <input type=text size=3 value='"+this.weight+"'/>";
            else weightContent = "weight: <input type=text disabled size=3 value='"+this.weight+"'/>";
            var productsContent = parserShipContent(products);
            var shipNoButton = "<button class=button-border toggleModal onclick=popupShipmentDetails('"+shipNo+"')><span class=icon></span>Freight</button>";
            var shipContent = '<p>Ship NO: <b class="existingShippment">'+shipNo+'</b> || '+weightContent+' || '+shipNoButton+'</p>'+
                              '<div>'+productsContent +'</div>'+
                              '<div>注释:  <b>'+comments+"</b></div>"+
                              '<div>收件人:  <b>'+consumer +"</b></div>"+
                              '<div>地    址:  <b>'+address +'</b></div><br/>'+
                              '<div>产品种类:  <b>'+productType +'</b>   ||    打印次数:  <b>'+printTimes +'</b></div><br/>';
            content = "<div class='divSelection'>"+shipContent +"</div>";
            //$( "div.orderShippment" ).prepend(content);
            $( '"div.'+shipNo+'"' ).prepend(content);
        }});
    }

    function changeOwnerShip(){
        var orderOwner = $("span#orderOwner_disabled");
        var customer = orderOwner.text();
        $("span#orderOwner_disabled").html('<input th:if="${order_label.customer_name} == null" id="orderOwner" type="text" size="20" name="orderOwner" value="'+customer
        +'" onkeyup="selectOrderOwner()" placeholder="Entered Customer ..." /><input id="orderOwnerShipButton" type="button" size="3" value="确认" onclick="updateOrderOwner()" />');
        /*$("#orderOwner").focus();
        $("#orderOwner").select();*/
        //var orderOwner = $("#orderOwner");
        //var strLength = orderOwner.val().length * 2;
        //orderOwner.focus();
        //orderOwner[0].setSelectionRange(strLength, strLength);
    };

    function popupAddConsumer() {
        $('#addNewConsumer').popupWindow({
            type: 'POST',
            centerScreen:1,
            windowURL:'/addConsumerFormURL?customer_id='+$('#orderOwner_disabled').text(),
            windowName:'Add New Customer'
        });
    }
    function popupUpdateConsumer() {
        var consumer = $("#consumer_side :selected").val();
        var consumer_id = consumer.split("|")[0];
        //alert(consumer_id.split("|")[0]);
        if($("#consumer_side :selected").val() != "") {
            $('#updateConsumer').popupWindow({
                type: 'POST',
                centerScreen:1,
                //windowURL:'/addConsumerFormURL?customer_id='+$('#orderOwner_disabled').text(),
                windowURL:'/updateConsumerFormURL?consumer_id='+consumer_id,
                windowName:'Update Consumer'
            });
        }
    }

    function milkpowderSettings(cans) {
        var mpWeight = $("#mpCans").val();
        $($("input.shipQTY_0")[0]).val(cans);
        $($("select#weight")[0]).val("奶粉.澳邮|4.5");
        if(cans == 3) {
            $($(".shipQTY_0")[1]).val(3.5);
            $("p.assinged").text(3.5);
        }else if(cans == 6) {
            $($("input.shipQTY_0")[1]).val(7.2);
            $("p.assinged").text(7.2);
        }else if(cans == 8) {
            $($("input.shipQTY_0")[1]).val(9.6);
            $("p.assinged").text(9.6);
        }else if(cans == 1) {
             $($("input.shipQTY_0")[1]).val(1.2);
             $("p.assinged").text(1.2);
        }else if(cans == 2) {
             $($("input.shipQTY_0")[1]).val(2.4);
             $("p.assinged").text(2.4);
         }
    }

    $( document ).ready(function() {
        loadConsumerList();

        loadExistingShippments(order.id);

        $('.addNewCustomer').popupWindow({
            type: 'POST',
            centerScreen:1,
            windowURL:'/addCustomerFormURL',
            windowName:'swip'
        });

        $('#addNewConsumer').on('click', function(e) {
            popupAddConsumer();
        });
        $('#updateConsumer').on('click', function(e) {
            popupUpdateConsumer();
        });
        $('#consumerSearch').on('click', function(e) {
            alert("testing i na here");
        });
        $('.toggleModal').on('click', function(e) {
            $('.modal').toggleClass('active');
        });

        $('#icol').click(function(){
            //if($('#col').val()){
                $('#mtable tr').append($("<td>"));
                $('#mtable thead tr>td:last').html('<input name=shipNumber class=shipNumber_'+counter+' type=text size=20 onkeypress=stopSumbitOnEter()  onclick=addShipOrderIntoTable("shipQTY_"+'+counter+',"shipNumber_"+'+counter+') />');
                $('#mtable thead tr>td:last input').val($('#col').val());
                $('#col').val("");
                $('#mtable tbody tr').each(function(){
                    $(this).children('td:last').append($('<input name="shipQTY_'+counter+'" class="shipQTY_'+counter+'" type="number" value="0" size="4" onchange=calculateAssignedQTY('+"this"+') onkeypress="stopSumbitOnEter()">'))
                });
                counter++;
                $('#col').prop('disabled', true);
                 $('#icol').attr("disabled", true);
                //.prop('disabled', true);
            //}else{alert('Enter Text');}
        });
        /*$('#irow').click(function(){
            if($('#row').val()){
                $('#mtable tbody').append($("#mtable tbody tr:last").clone());
                //$('#mtable tbody').append($("#mtable tbody tr:last").append("<td><input type='text'/></td>"));
                //$('#mtable tbody tr:last td:first').html(('<input type="text"/>').val($('#row').val()));
                $('#mtable tbody tr:last td:first').html(('<input name="shipQTY" type="text" onkeypress="stopSumbitOnEter()"/>'));
                $('#mtable tbody tr:last td:first input').val($('#row').val());
            }else{alert('Enter Text');}
        });*/

        /*$('.addShippmentButton').click(function() {
            ///////////////////////////////////////////////////////////////////////////////////////
            var trans_dropdown_potions = "";

            forEach(trans, function(){
                trans_dropdown_potions = trans_dropdown_potions + "<option value="+this.product_name+">"+this.product_name+"</option>";
            });
            var trans_dropdown = "<select>" + trans_dropdown_potions + "</select>";
            var trans_content = "<table><tr><td><div>"+ trans_dropdown +"</div></td><td><input type='number' size='4' id='trans_qty' name='trans_qty' min='1' max='10'/></td></tr></table>";
            temp = trans_content;
            //////////////////////////////////////////////////////////////////////////////////////
            var order_content = "<div class='divSelection' id="+shipID+" onlick='addTransactionSelection()'><input type='text' name='shippmentNumnber' onkeypress='stopSumbitOnEter()'/><input type='radio' name='shippmentDiv' checked/></div>";

            $( "div.orderShippment" ).append(order_content);
            id++;
            shipID = "shipment"+id;
        });*/
        $('.addProductButton').click(function() {
            shipsheet = $("input:checked").parent();
            $(shipsheet).append(temp);
        });/**/ //addShippmentButton() and addProductButton() commented and disabled with addTransactionSelection()

        $('.close').click(function() {
            //alert("testing");
            location.reload();
        });
    });

    function addTransactionSelection() {
            var trans_dropdown_potions = "";
            forEach(trans, function(){
                trans_dropdown_potions = trans_dropdown_potions + "<option value="+this.product_name+">"+this.product_name+"</option>";
            });
           var trans_dropdown = "<select>" + trans_dropdown_potions + "</select>";
            var trans_content = "<table><tr><td><div>"+ trans_dropdown +"</div></td></tr></table>";
            var temp = trans_content;
    };

    function generateBarcode(barcodeNumber){
        var value 		= barcodeNumber;
        var btype 		= "code128";
        var renderer 	= "css";

        var settings = {
            output:renderer,
            bgColor	: "#FFFFFF",	//$("#bgColor").val(),
            color		: "#000000",	//$("#color").val(),
            barWidth	: "1",			//$("#barWidth").val(),
            barHeight	: "60"			//$("#barHeight").val()
        };
        $(".barcode").html("").show().barcode(value, btype, settings);
    }

    function delivery(weight_price) {
        var weight_price = $('#weight_price').text(weight_price);
    }

    function getCustomer(customer_id) {
        var ajaxGetCustomerURL = "/ajaxGetCustomerURL?id="+customer_id;
        $.ajax({url: ajaxGetCustomerURL, success: function(customer_result){
            return customer_result;
        }});
    }

    function popupShipmentDetails(shipNo) {
        $('.modal').toggleClass('active');
        $('#shipmentNo').text("运单号码:"+shipNo);
        popupShippment(shipNo);
    }

    function popupShippment(shipNo) {
        var ajaxGetShippmentURL = "/ajaxGetShippmentURL?shipNo="+shipNo;
        $.ajax({url: ajaxGetShippmentURL, success: function(popupShippment_result){
            var getShippment    = popupShippment_result;
            var shipNo          = getShippment.shipNo;
            var weight          = getShippment.weight;
            var products        = getShippment.products;
            /////////////////////////////////////Doggy plus below
            var productList     = products.split("||");
            var newProductList  = "";
            for(i = 0; i < productList.length-1; i++) {
                if(i==0) {
                    newProductList = productList[i];
                }else newProductList = newProductList +"||"+ productList[i] ;
            }
            products = newProductList;
            productList[productList.length-1];
            /////////////////////////////////////
            var enabled         = getShippment.enabled;
            var order_id        = getShippment.order_id;
            var customer_id     = getShippment.customer_id;
            var customer_name   = getShippment.customer_name.split("-")[0];
            if(customer_name == "ART") customer_name = "";
            var comments        = getShippment.comments;


            var datePrint       = "<div style='margin: 0px 0px 0px 10px;'>"+Date()+"</div>";
            weight              = "<div style='margin: 0px 0px 0px 10px;'>"+weight+"公斤"+"</div>";
            order_id            = "<div style='margin: 0px 0px 0px 10px;'>"+order_id+"</div>";

            var consumer_name     = getShippment.consumer_name;
            var consumer_phone    = getShippment.consumer_phone;
            var consumer_identity = getShippment.consumer_identity;
            var consumer_address  = getShippment.consumer_address;

            var customer_details= "<div style='margin: -10px 0px 0px 10px;'>"+customer_name+" "+getShippment.customer_phone+"</div><br/>";
            var consumer_details= "<div style='margin: 0px 0px 0px 10px;'>名字："+consumer_name+"<br/>电话："+consumer_phone+"<br/>地址："+consumer_address+"<br/>身份证："+consumer_identity+"</div><br/>";

            var productsContent = "<div>"+parserShipContent(products)+"</div><br/>";
            var barcode         = "<div class=barcode></div>";

            var productArray    = products.split("||");
            var productLength   = productArray.length;
            //var carrier         = (productArray[productLength-1].split("*")[1]);//.split(".")[1];
            /////////////////////////////////////Doggy plus above
            var carrier         = (productList[productList.length-1].split("*")[1]);//.split(".")[1];
            /////////////////////////////////////
            //carrier             = carrier.split(".")[1];
            var carrierIMG      = '<img class="popup_img" src="imgs/'+carrier+'.jpg" alt="物流LOGO" />';
            //var carrierIMG      = '<font size="6" color="red">EWE</font>';
            //var order_id        = "<div>"+order_id+"</div><br/>";

            //var shipContent = "<div><table><tr><td>"+barcode+"</td><td>"+carrierIMG+"</td></tr></table></div>"
            var shipContent = "<div><table class='popTable'><tr><td>"+barcode+carrierIMG+"</td></tr></table></div>"+
                              "<div><table class='popTable'><tr><td style='border: 1px solid black;' align='center' width='35px'>发</br>件</br>人</td><td>"+customer_details+"</td></tr></table></div>"+
                              "<div><table class='popTable'><tr><td style='border: 1px solid black;' align='center' width='35px'>货</br>物</td><td>"+productsContent+"</td></tr></table></div>"+
                              "<div><table class='popTable'><tr><td style='border: 1px solid black;' align='center' width='35px'>收</br>件</br>人</td><td>"+consumer_details+"</td></tr></table></div>"+
                              "<div><table class='popTable'><tr><td style='border: 1px solid black;' align='center' width='35px'>重</br>量</td><td>"+weight+"</td><td style='border: 1px solid black;' align='center' width='35px'>单</br>号</td><td>"+order_id+"</td></tr></table></div>"+
                              "<div><table class='popTable'><tr><td style='border: 1px solid black;' align='center' width='35px'>注</br>释</td><td>"+comments+"</td></tr></table></div>";
                              /*"<div><table class='popTable'><tr><td style='border: 1px solid black;' align='center' width='35px'>日</br>期</td><td>"+datePrint+"</td></tr></table></div>";*/

            var printContent = "<div class='divSelection_popup'>"+shipContent+"</div>";
            $( "section.popup" ).html(printContent);
            generateBarcode(shipNo);
        }});
    }

    function PopupToPrint() {
        var popup_css =
            "<style>"+
            ".barcode {"+
            "    float:left;"+
            "    margin-top: 10px;"+
            "}"+
            ".divSelection_popup{"+
            "    float: left;"+
            "    width:  385px;"+
            "    height: 550px;"+
            "    margin: 0px 0px 0px -5px;"+
            "    border: 2px solid #73AD21;"+
            "}"+
            ".popup_img {"+
            "   float:right;"+
            "   margin: 20px 10px 0px 0px;"+
            "}"+
            ".popTable{"+
            "   width:  385px;"+
            "   border: 1px solid black;"+
            "}"+
            ""+
            "</style>";
        var shipNo = ($("#shipmentNo").text()).split(":")[1];
        var printTimesURL    = "/ajaxPrintTimesURL?shipmentNo="+shipNo;
        //alert(printTimesURL);
        $.ajax({url: printTimesURL, success: function(printTimes_result){
            ///alert(printTimes_result);
            var mywindow = window.open('', 'Print Shippment Slip', 'height=400,width=600');
            //var printContent = "<div id=''mydiv'>i just print.</div>";
            mywindow.document.write('<html><head>'+popup_css);
            /*optional stylesheet*/ //mywindow.document.write('<link rel="stylesheet" href="main.css" type="text/css" />');
            mywindow.document.write('</head><body >');
            mywindow.document.write($(".popup").html());
            //mywindow.document.write(data);
            mywindow.document.write('</body></html>');

            mywindow.document.close(); // necessary for IE >= 10
            mywindow.focus(); // necessary for IE >= 10

            mywindow.print();
            mywindow.close();
        }});
        return true;
    }

    function loadConsumerList() {
        customerID = $("#orderOwnerId").val();
        if(customerID) {
            var consumer_option = "<option value=''></option>";
            var consumer_details = "";
            customer_id = customerID.split(customer_seperator)[2];
            //alert("i am here now "+ customer_id);
            var ajaxConsumerOptionsURL = "/ajaxConsumerOptionsURL?customer_id="+customer_id;
            $.ajax({url: ajaxConsumerOptionsURL, success: function(ajaxConsumerOptions_result){
                var getConsumerOptions = ajaxConsumerOptions_result;
                forEach(getConsumerOptions, function(){
                    var option = this.id+"|"+this.name+"|"+this.wechat_id+"|"+this.customer_id;
                    //var optionConsumerValue = this.id+"|"+this.name+"|"+this.phone+"|"+this.personal_id+"|"+this.wechat_id+"|"+this.comment+"|"+this.customer_id;
                    consumer_option = consumer_option + "<option value='"+option+"'>"+option+"</option>";
                });
                var consumer_id = this.id;
                consumer_details =
                    "<div>收件人    ： <b><select id='consumer_side' onclick=loadConsumerAndDisplay(this.value); onchange=loadAddress(this.value)>" +
                    consumer_option +
                    "</select><b></div>";
                $("div.consumer_list").html(consumer_details);
            }});
        }
     }

    function loadAddress(consumer_option) {
        var consumer_id = consumer_option.split("|")[0];
        //alert("iamhere!!! "+ consumer_id);
        var address_option = "<option value=''></option>";
        var address_details = "";
        var ajaxAddressOptionsURL = "/ajaxAddressOptionsURL?consumer_id="+consumer_id;
            $.ajax({url: ajaxAddressOptionsURL, success: function(loadAddresses_result){
                var getAddressOptions = loadAddresses_result;
                forEach(getAddressOptions, function(){
                  //alert("this is: " + this);
                  var option = this.id+"|"+this.address;
                  //var option = this.name+"|"+this.wechat_id+"|"+this.customer_id;
                  address_option = address_option + "<option value='"+option+"'>"+option+"</option>";
                });
               address_details =
                    "<div>地 址：<b><select id='address_side' onclick=loadAddressAndDisplay(this.value)>" +
                    address_option +
                    "</select><b></div>";
               $("div.address_list").html(address_details);
            }});
    }

    function loadAddressAndDisplay(consumer_address) {
        $("textarea#consumer_address").html(consumer_address);
    }

    function deleteShippment(ship_id) {
        //alert("ship_id: "+ship_id);
        var confirmed = false;
        confirmed = confirm("Are you sure to delete shipppment id: "+ship_id);
        if(confirmed) {
            var ajaxDeleteShippmentURL = "/ajaxDeleteShippmentURL?ship_id="+ship_id;
            $.ajax({url: ajaxDeleteShippmentURL, success: function(deleteShippment_result){
                deleteRS = deleteShippment_result;
                alert("Shippment: "+ship_id+ " has been deleted!!!");
                location.reload();
            }});
        }
    }

    function myFunction() {
        var txt;
        var r = confirm("Press a button!");
        if (r == true) {
            txt = "You pressed OK!";
        } else {
            txt = "You pressed Cancel!";
        }
        document.getElementById("demo").innerHTML = txt;
    }

