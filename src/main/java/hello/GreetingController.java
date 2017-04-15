package hello;

import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.nashorn.api.scripting.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

@Controller
public class GreetingController {
    Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @Autowired
    OrderJDBCTemplate orderJDBCTemplate;
    @Autowired
    TransactionJDBCTemplate transactionJDBCTemplate;
    @Autowired
    ProductJDBCTemplate productJDBCTemplate;
    @Autowired
    CustomerJDBCTemplate customerJDBCTemplate;
    @Autowired
    ConsumerJDBCTemplate consumerJDBCTemplate;
    @Autowired
    AddressJDBCTemplate addressJDBCTemplate;
    @Autowired
    ShippmentJDBCTemplate shippmentJDBCTemplate;

    @RequestMapping(value="/greetingsURL", method=RequestMethod.GET)
    public String orderListForm(Model model) {
        System.out.println("----Listing All Orders from greetingsURL-----" );
        //List<Order> orders = orderJDBCTemplate.getListOrders();
        List<Order> orders = orderJDBCTemplate.getNumOfOrders("",0);
        Search search = new Search();
        model.addAttribute("search", search);
        model.addAttribute("orders_label", orders);
        return "greetingsPAGE";
    }

    @RequestMapping(value="/RetrieNumOfOrdersURL", method=RequestMethod.GET)
    public String RetrieNumOfOrders(@RequestParam(value="table", required=true) String table,
                                    @RequestParam(value="id",    required=true) int number,
                                    Model model) {
        System.out.println("----Listing All Orders from greetingsURL-----" );
        //List<Order> orders = orderJDBCTemplate.getListOrders();
        Search search = new Search();
        List<Order> orders = orderJDBCTemplate.getNumOfOrders(table, number);
        model.addAttribute("search", search);
        model.addAttribute("orders_label", orders);
        return "greetingsPAGE";
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value="/searchProductFormURL", method=RequestMethod.GET)
    public String searchProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "productFormPAGE";
    }

/*    @RequestMapping(value="/deliveryCalFormURL", method=RequestMethod.POST)
    public String searchProductForm(@RequestParam(value="barcode", required=true) String barcode,
                                    @RequestParam(value="weight",  required=true) String weight,
                                    @ModelAttribute Product product,
                                    Model model) {
        Boolean productInputDisabled = true;
        if(barcode != null && !barcode.isEmpty()) {
            System.out.println("----i am here with ID-----" );
            productJDBCTemplate.updateProduct(product);
        }
        else {
            product = productJDBCTemplate.getProductWithInfo(barcode, product_name);
        }

        model.addAttribute("productInputDisabled", productInputDisabled);
        model.addAttribute("product", product);
        return "deliveryCalPAGE";
    }*/

    @RequestMapping(value="/productFormURL", method=RequestMethod.POST)
    public String searchProductForm(@RequestParam(value="barcode",      required=false) String barcode,
                                    @RequestParam(value="product_name", required=false) String product_name,
                                    @RequestParam(value="id",           required=false) String id,
                                    @ModelAttribute Product product,
                                    Model model) {
        //Product product = new Product();
        Boolean productInputDisabled = true;
        if(id != null && !id.isEmpty()) {
            System.out.println("----i am here with ID-----" );
            productJDBCTemplate.updateProduct(product);
        }
        /*else if(barcode != null && !barcode.isEmpty()) {
            product.setBarcode(barcode);
            product = productJDBCTemplate.getProductWithBarcode(barcode);
            System.out.println("----Searching Product with Barcode from productFormURL-----" );
        }else if(product.getId() == 0 && product_name != null && !product_name.isEmpty()) {
            product.setProduct_name(product_name);
            product = productJDBCTemplate.getProductWithProductName(product_name);
            System.out.println("----Searching Product with Product Name from productFormURL-----" );
        }*/
        else {
            product = productJDBCTemplate.getProductWithInfo(barcode, product_name);
        }

        model.addAttribute("productInputDisabled", productInputDisabled);
        model.addAttribute("product", product);
        return "productFormPAGE";
    }

    @RequestMapping(value="/ShippmentTakenUpdateURL", method=RequestMethod.GET)
    public String DeliveryTakenUpdateForm(Model model) {
        System.out.println("---- Add and Update All Shippments taken by Carrier from ShippmentTakenUpdateURL-----" );
        //List<Order> orders = orderJDBCTemplate.getListOrders();
        //model.addAttribute("orders_label", orders);
        return "ShippmentTakenUpdatePAGE";
    }

    @RequestMapping(value="/orderDetailURL", method=RequestMethod.GET)
    public String greeting(@RequestParam(value="id", required=true) int id, Model model) {
        System.out.println("----Listing Order with ID = "+id+" from orderDetailURL-----" );
        Order order = new Order();
        //order = orderJDBCTemplate.getOrder(id);
        //if(orderJDBCTemplate.isShipOrderExisting(id)) {
          order = orderJDBCTemplate.getShipOrder(id);
        //}else {
        //    int result = orderJDBCTemplate.insertNewShipOrder(orderJDBCTemplate.getOrder(id));
        //    if(result>0) order = orderJDBCTemplate.getShipOrder(id);
        //}

        System.out.println("----Listing Transactions with Order ID = "+id+" from orderDetailURL-----" );
        List<Transaction> trans = transactionJDBCTemplate.getListTransactions(id);
        System.out.println("----Listing Transactions with Order ID = "+trans.get(0).getDeal_price()+"||"+trans.get(0).getProduct_id()+" from orderDetailURL-----" );
        //for(int a=0; a<trans.size(); a++) {
            //String product_name = productJDBCTemplate.getProduct(trans.get(a).getProduct_id()).getProduct_name();
            //String product_name
            //trans.get(a).setName(product_name);
            //System.out.println("----Listing Product Name for each Transaction = "+trans.get(a).getName()+" from orderDetailURL-----" );
        //}
        order.setNumberOfCategories(trans.size());
        model.addAttribute("order_label", order);
        //model.addAttribute("order_label_other", order);
        model.addAttribute("transactions_label", trans);
        //model.addAttribute("trans_label", new Transaction());
        return "orderPAGE";
    }

    @RequestMapping(value="/addConsumerFormURL", method=RequestMethod.GET)
    public String addConsumerForm(@RequestParam(value="customer_id", required=true) String customer_id, Model model) {
        System.out.println("----Adding new consumer from addConsumerFormURL-----" );
        Consumer consumer = new Consumer();
        int customerId = Integer.parseInt(customer_id.split("-")[2]);
        consumer.setCustomer_id(customerId);
        model.addAttribute("consumer", consumer);
        return "consumerFormPAGE";
    }
    @RequestMapping(value="/updateConsumerFormURL", method=RequestMethod.GET)
    public String uppdateConsumerForm(@RequestParam(value="consumer_id", required=true) int consumer_id, Model model) {
        System.out.println("----Updating new consumer from updateConsumerFormURL-----" );
        Consumer consumer = new Consumer();
        consumer = consumerJDBCTemplate.getConsumer(consumer_id);
        model.addAttribute("consumer", consumer);
        return "consumerFormPAGE";
    }

    @RequestMapping(value="/consumerFormURL", method=RequestMethod.POST)
    public String consumerSubmitAndUpdate(@ModelAttribute Consumer consumer, Model model) {
        Boolean consumerInputDisabled = true;
        if(consumer.getId() <= 0)
           consumer.setId(consumerJDBCTemplate.insertNewConsumer(consumer.getName(), consumer.getPhone(), consumer.getPersonal_id(), consumer.getComment(), consumer.getWechat_id(), consumer.getCustomer_id()));
        else consumerJDBCTemplate.updateConsumer(consumer.getId(), consumer.getName(), consumer.getPhone(), consumer.getPersonal_id(), consumer.getComment(), consumer.getWechat_id(), consumer.getCustomer_id());
        model.addAttribute("consumer", consumer);
        model.addAttribute("consumerInputDisabled", consumerInputDisabled);
        return "consumerFormPAGE";
    }

    @RequestMapping(value="/addCustomerFormURL", method=RequestMethod.GET)
    public String addCustomerForm(Model model) {
        System.out.println("----Adding new customer from addCustomerFormURL-----" );
        model.addAttribute("customer", new Customer());
        return "customerFormPAGE";
    }
    @RequestMapping(value="/customerFormURL", method=RequestMethod.POST)
    public String customerSubmitAndUpdate(@ModelAttribute Customer customer, Model model) {
        Boolean customerInputDisabled = true;
        if(customer.getCustomer_id() <= 0)
            customer.setCustomer_id(customerJDBCTemplate.insertNewCustomer(customer.getCustomer_id(), customer.getCustomer_name(), customer.getTelephone(), customer.getPersonal_id(), customer.getComment(), customer.getWechat_id(), customer.getAddress()));
                       else customerJDBCTemplate.updateCustomer  (customer.getCustomer_id(), customer.getCustomer_name(), customer.getTelephone(), customer.getPersonal_id(), customer.getComment(), customer.getWechat_id(), customer.getAddress());
        model.addAttribute("customer", customer);
        model.addAttribute("customerInputDisabled", customerInputDisabled);
        return "customerFormPAGE";
    }

    @RequestMapping(value="/addAddressFormURL", method=RequestMethod.GET)
    public String addAddressForm(@RequestParam(value="consumer_id", required=true) int consumer_id, Model model) {
        System.out.println("----Adding new address from addAddressFormURL-----" );

        Consumer consumer = consumerJDBCTemplate.getConsumer(consumer_id);

        Address address = new Address();
        address.setConsumer_id(consumer_id);
        address.setConsumer_name(consumer.getName());

        model.addAttribute("address", address);
        return "addressFormPAGE";
    }
    @RequestMapping(value="/addressFormURL", method=RequestMethod.POST)
    public String addressSubmitAndUpdate(@ModelAttribute Address address, Model model) {
        Boolean addressInputDisabled = true;
        if(address.getId() <= 0)
            address.setId(addressJDBCTemplate.insertNewAddress(address.getAddress(), address.getConsumer_id()));
        else addressJDBCTemplate.updateAddress(address.getId(), address.getAddress(), address.getConsumer_id());

        model.addAttribute("address", address);
        model.addAttribute("addressInputDisabled", addressInputDisabled);
        return "addressFormPAGE";
    }

    @RequestMapping(value="/cameraURL", method=RequestMethod.GET)
    public String cameraShoot(Model model) {
        System.out.println("----show cameraURL-----" );
        return "cameraPAGE";
    }

    @RequestMapping(value="/shippmentsURL", method=RequestMethod.GET)
    public String shippmentListForm(Model model) {
        System.out.println("----Listing All shippments from shippmentsURL-----" );
        List<Order> orders = orderJDBCTemplate.getListOrders();
        List<Shippment> shippments = shippmentJDBCTemplate.getListShippments();
        model.addAttribute("shippments_label", shippments);
        return "shippmentsPAGE";
    }
    @RequestMapping(value="/shippmentsWithProductTypeURL", method=RequestMethod.GET)
    public String shippmentListFormWithProductType(@RequestParam(value="productType", required=true) String productType, Model model) {
        System.out.println("----Listing All "+productType+" shippments from shippmentsURL-----" );
        List<Order> orders = orderJDBCTemplate.getListOrders();
        List<Shippment> shippments = shippmentJDBCTemplate.getListShippments(productType);
        model.addAttribute("shippments_label", shippments);
        return "shippmentsPAGE";
    }

    @RequestMapping(value="/orderDetailSearchURL", method=RequestMethod.POST)
    public String orderDetailSearch(@RequestParam(value="order_id",         required=false) String id,
                                    @RequestParam(value="total",            required=false) String total,
                                    @RequestParam(value="status",           required=false) String status,
                                    @RequestParam(value="customer_id",      required=false) String customer_id,
                                    @RequestParam(value="modified_time",    required=false) String modified_time,
                                    @RequestParam(value="firstname",        required=false) String firstname,
                                    @RequestParam(value="lastname",         required=false) String lastname,
                                    @RequestParam(value="pay_method",       required=false) String pay_method,
                                    @RequestParam(value="comment",          required=false) String comment,
                                    @RequestParam(value="older_id",         required=false) String older_id,
                                    @RequestParam(value="newer_id",         required=false) String newer_id,
                                    @RequestParam(value="searchString",     required=false) String searchString,
                                    //@ModelAttribute Product product,
                                    Model model) {
        System.out.println("----Listing All Orders from greetingsURL-----" +id+" ===  "+total+" ===  "+searchString);
        List<Order> orders = new ArrayList();
        List<String> searchCiteria = new ArrayList();
        if(!id.isEmpty())searchCiteria.add(id);
        //if(!s.isEmpty())searchCiteria.add(s);
        /* if(!older_id.isEmpty())searchCiteria.add(older_id);
        if(!newer_id.isEmpty())searchCiteria.add(newer_id);*/
        ///////////////////////////////////////////////////////////////////////
        Utilities ult = new Utilities();
        searchCiteria = ult.parseJSON(searchString);

        if(searchCiteria.size() == 0) {
            orders = orderJDBCTemplate.getNumOfOrders("",0);
        }else {
            orders = orderJDBCTemplate.getOrdersSearched(searchCiteria);
        }
        Search search = new Search();
        search.setId(id);
        search.setTotal(total);
        search.setStatus(status);
        search.setCustomer_id(customer_id);
        search.setModified_time(modified_time);
        search.setFirstname(firstname);
        search.setLastname(lastname);
        search.setPay_method(pay_method);
        search.setComment(comment);
        search.setOlder_id(older_id);
        search.setNewer_id(newer_id);
        model.addAttribute("search", search);
        model.addAttribute("orders_label", orders);
        return "greetingsPAGE";
    }
}