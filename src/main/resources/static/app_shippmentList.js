    //////////////////////////////////////////////////////////////////////
    //Below are Global Variables to  js functions in this app_shippment.js
    //////////////////////////////////////////////////////////////////////
    var shipsheet = "";
    var shipID = "shipment"+id;
    var id=0;   var counter = 0;    var number = 0;
    var seperator = "_";    var joint = "||";
    //////////////////////////////////////////////////////////////////////
    $( document ).ready(function() {
        $('.toggleModal').on('click', function(e) {
            $('.modal').toggleClass('active');
        });

       $('.close').click(function() {
            //alert("testing");
            location.reload();
        });

        $("#refund").click(function(){
            /* alert("iamhere");
            $.get("https://mpay.royalpay.com.au/api/v1.0/gateway/partners/TOPC/orders", function(data, status){
                alert("Data: " + data + "\nStatus: " + status);
            });*/
            var order_id = "1000048";
            var fee = 0.01;
            var updateOrderOwnerURL = "/ajaxRefundURL?order_id="+order_id+"&fee="+fee;
            $.ajax({url: ajaxRefundURL, success: function(ajaxRefundURL){
                ajaxRefund = ajaxRefund_result;
            }});
        });
    });

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

    function popupShipmentDetails(shipNo) {
        $('.modal').toggleClass('active');
        $('#shipmentNo').text("运单号码:"+shipNo+"");
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