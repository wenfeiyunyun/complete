    //////////////////////////////////////////////////////////////////////
    //Below are Global Variables to  js functions in this app_customer.js
    //////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////
    function alerting() {
        alert("testing i am here");
    }

    function stopSumbitOnEter() {
        var chCode = ('charCode' in event) ? event.charCode : event.keyCode;
         if ( chCode == 13 ) {
            event.preventDefault();
            //alert("you can press ENTER");
            return false;
         }
    }

    function addinToShippmentTakenTable() {
        //alert("i am here now!!");
        shipNo = $("input#shippmentInput").val();
        var ajaxShippmentTakenURL = "/ajaxGetShippmentURL?shipNo="+shipNo;
        $.ajax({url: ajaxShippmentTakenURL, success: function(ajaxShippmentTakenURL_result){
            var Shippment = ajaxShippmentTakenURL_result;
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



            alert("Shippment: "+shipNo+ "- - - " +  products);


/*            if(weight == 0 || weight == null) weightContent = "weight: <input type=text size=3 value='"+this.weight+"'/>";
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
*/
        }});
        $("#shippmentInput").val("");
        $("#shippmentInput").focus();
    }

    $(document).ready(function() {
        $("#shippmentInput").focus();
    });
