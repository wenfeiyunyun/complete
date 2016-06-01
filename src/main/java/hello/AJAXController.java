package hello;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AJAXController {

    Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @Autowired
    OrderJDBCTemplate orderJDBCTemplate;

    @Autowired
    TransactionJDBCTemplate transactionJDBCTemplate;

    @Autowired
    ProductJDBCTemplate productJDBCTemplate;

    @Autowired
    ShippmentJDBCTemplate shippmentJDBCTemplate;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /*
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }
    @RequestMapping("/greeting_string")
    public String greetingString(@RequestParam(value="name", defaultValue="World") String name) {
        return name;
    }
    */

    @RequestMapping(value="/ajaxOrderURL") //, method=RequestMethod.GET
    public Order ajaxOrder(@RequestParam(value="id", required=true) int id) {
        System.out.println("----Listing Order with ID = "+id+" from ajaxOrderURL-----" );
        Order ajaxOrder = orderJDBCTemplate.getOrder(id);
        //model.addAttribute("order_label", order);

        return ajaxOrder;
    }

    @RequestMapping(value="/ajaxTransListURL") //, method=RequestMethod.GET
    public List ajaxTransList(@RequestParam(value="id", required=true) int id) {
        System.out.println("----Listing Transactions with Order ID = " + id + " from ajaxTransListURL-----");
        List<Transaction> ajaxTransList = transactionJDBCTemplate.getListTransactions(id);

        for(int a=0; a<ajaxTransList.size(); a++) {
            String product_name = productJDBCTemplate.getProduct(ajaxTransList.get(a).getProduct_id()).getProduct_name();
            ajaxTransList.get(a).setProduct_name(product_name);
            System.out.println("----Listing Product Name for each Transaction = "+ajaxTransList.get(a).getProduct_name()+" from ajaxTransListURL-----" );
        }
        return ajaxTransList;
    }

    @RequestMapping(value="/ajaxGetShippmentURL") //, method=RequestMethod.GET
    public Shippment ajaxGetShippment(@RequestParam(value="shipNo", required=true) String shipNo) {
        System.out.println("----Listing ONE Shippment with shipNo = "+shipNo+" from ajaxGetShippmentURL-----" );
        Shippment ajaxShippment = shippmentJDBCTemplate.getShippment(shipNo);
        //model.addAttribute("order_label", order);

        return ajaxShippment;
    }

    @RequestMapping(value="/ajaxpPrintShippmentURL") //, method=RequestMethod.GET
    public Shippment ajaxPrintShippment(@RequestParam(value="shipNo", required=true) String shipNo) {
        System.out.println("----Listing ONE Shippment with shipNo = "+shipNo+" from ajaxpPrintShippmentURL-----" );
        Shippment ajaxPrintShippment = shippmentJDBCTemplate.getShippment(shipNo);
        //model.addAttribute("order_label", order);

        return ajaxPrintShippment;
    }

    @RequestMapping(value="/ajaxShippmentsListURL") //, method=RequestMethod.GET
    public List ajaxShippmentsList(@RequestParam(value="id", required=true) int id) {
        System.out.println("----Listing Shippment with Order ID = " + id + " from ajaxShippmentsListURL-----");
        List<Shippment> ajaxShippmentsList = shippmentJDBCTemplate.getListShippments(id);
        return ajaxShippmentsList;
    }
    @RequestMapping(value="/insertNewShippmentURL") //, method=RequestMethod.GET
    public int ajaxUpdateShippment(@RequestParam(value="shippmentDetails", required=true) String shippmentDetails) {
        //shippmentDetails = shipNo 0 +"#&#"+ products 1 +"#&#"+ order_id 2 +"#&#"+ is_enabled 3;
        String[] details = shippmentDetails.split("-");
        //shipNo, weight, products, cosumer, client, modifiedBy, ModifiedTime, enabled, order_id
        String shipNo = new String();
        String products = new String();
        int order_id = 0;
        int enabled = 0;

        /*the following variables not get value from frontend page*/
        //Float weight = new Float();
        float weight = 0;
        int cosumer = 0;
        int client = 0;
        String modifiedBy = new String();
        Date ModifiedTime = new Date();

        shipNo      = details[0];
        products    = details[1];
        order_id    = Integer.parseInt(details[2]);
        enabled     = Integer.parseInt(details[3]);
        weight      = 0;

        System.out.println("----Update Shippment Details with Order ID = " + order_id + " from ajaxUpdateShippmentURL-----");
        int result = shippmentJDBCTemplate.insertNewShippment(shipNo, weight, products, cosumer, client, modifiedBy, ModifiedTime, enabled, order_id);

        return result;
    }
}