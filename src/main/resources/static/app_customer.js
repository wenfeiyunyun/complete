    //////////////////////////////////////////////////////////////////////
    //Below are Global Variables to  js functions in this app_customer.js
    //////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////
    function alerting() {
        alert("testing i am here");
    }

    $('#addNewAddress').popupWindow({
        type: 'POST',
        centerScreen:1,
        windowURL:'/addAddressFormURL?consumer_id='+$('#consumer_id').text(),
        windowName:'Add New Address'
    });

    $(document).ready(function() {
        $("#locked").change(function() {
            var ischecked= $(this).is(':checked');
            if(ischecked) $(":input").prop("disabled", true);
            else $(":input").prop("disabled", false);
            $("#locked").prop("disabled", false);
        });

    });
