package hello;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicLong;

import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import java.util.zip.Deflater;

import java.util.Base64.Decoder;
//import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.ByteArrayOutputStream;

@RestController
public class AJAXController {
    String seperator = "_";

    Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @Autowired
    OrderJDBCTemplate orderJDBCTemplate;
    @Autowired
    TransactionJDBCTemplate transactionJDBCTemplate;
    @Autowired
    ProductJDBCTemplate productJDBCTemplate;
    @Autowired
    ShippmentJDBCTemplate shippmentJDBCTemplate;
    @Autowired
    CustomerJDBCTemplate customerJDBCTemplate;
    @Autowired
    ConsumerJDBCTemplate consumerJDBCTemplate;
    @Autowired
    AddressJDBCTemplate addressJDBCTemplate;
    @Autowired
    WechatRefundJDBCTemplate wechatRefundJDBCTemplate;
    /*
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
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
    @RequestMapping(value="/ajaxRefundURL", method=RequestMethod.GET)
    public String consumerSubmitAndUpdate(@RequestParam(value="order_id", required=true) String order_id,
                                          @RequestParam(value="fee",      required=true) double fee) {
        Utilities utils = new Utilities();
        WechatRefund wechatRefund = utils.wechatPayRefundPutRequest(order_id, fee);
        if(wechatRefund != null) {
            int result = wechatRefundJDBCTemplate.insertNewWechatRefund(
                    wechatRefund.getAmount(), wechatRefund.getProductID(), wechatRefund.getProductQTY(), wechatRefund.getRefund_id(), wechatRefund.getPartner_refund_id(), wechatRefund.getModified_by(), wechatRefund.getModified_time()
            );
        }
        return "consumerFormPAGE";
    }

    @RequestMapping(value="/ajaxRefundInfoURL", method=RequestMethod.GET)
    public String consumerSubmitAndUpdate(@RequestParam(value="refund_id", required=true) String refund_id) {
        Utilities utils = new Utilities();
        utils.wechatPayGetRequest(refund_id);
        return "consumerFormPAGE";
    }

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
            //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            //String product_name = productJDBCTemplate.getProduct(ajaxTransList.get(a).getProduct_id()).getProduct_name();
            //ajaxTransList.get(a).setProduct_name(product_name);
            System.out.println("----Listing Product Name for each Transaction = "+ajaxTransList.get(a).getName()+" from ajaxTransListURL-----" );
        }
        return ajaxTransList;
    }

    @RequestMapping(value="/ajaxGetShippmentURL") //, method=RequestMethod.GET
    public Shippment ajaxGetShippment(@RequestParam(value="shipNo", required=true) String shipNo) {
        System.out.println("----Listing ONE Shippment with shipNo = "+shipNo+" from ajaxGetShippmentURL-----" );
        Shippment ajaxShippment = shippmentJDBCTemplate.getShippment(shipNo);
        ajaxShippment.setCustomer_name(customerJDBCTemplate.getCustomer(ajaxShippment.getCustomer_id()).getCustomer_name());
        ajaxShippment.setCustomer_phone(customerJDBCTemplate.getCustomer(ajaxShippment.getCustomer_id()).getTelephone());

        int comsumer_id = Integer.valueOf(ajaxShippment.getConsumer_id().split("\\|")[0]);
        Consumer consumer = consumerJDBCTemplate.getConsumer(comsumer_id);
        ajaxShippment.setConsumer_name(consumer.getName());
        ajaxShippment.setConsumer_phone(consumer.getPhone());
        ajaxShippment.setConsumer_identity(consumer.getPersonal_id());

        String address_id = ajaxShippment.getAddress_id().split("\\|")[0];
        ajaxShippment.setConsumer_address(addressJDBCTemplate.getAddress(address_id).getAddress());
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
    @RequestMapping(value="/ajaxInsertNewShippmentURL") //, method=RequestMethod.GET
    public int ajaxUpdateShippment(@RequestParam(value="", required=true) String shippmentDetails) {
        String[]    details  = shippmentDetails.split(seperator);

        String      shipNo   = details[0];
        int         order_id = Integer.parseInt(details[3]);
        String      weight   = details[2];
        int         enabled  = Integer.parseInt(details[4]);

        String      products         = details[1];
        String      consumer_details = details[7]; //var option = this.id+"|"+this.name+"|"+this.wechat_id+"|"+this.customer_id;
        String      address_details  = details[8]; //var option = this.id+"|"+this.address;
        int         printTimes       = 0;
        String      productType      = details[5];

        String      comments         = details[6];

        String[] productList        = products.split("\\|\\|");

        String      carrierTaken     = productList[productList.length-1].split("\\.")[1];
        //productType = "NF";
        //shipNo +seperator+ products +seperator+ weight +seperator+ order_id +seperator+ enabled;
        // 0                  1                     2                     3                4
        /*the following variables not get value from frontend page*/
        //String[] productlist = products.split("\\|\\|");
        //String weight        = (productlist[productlist.length-1].split("\\*"))[0];

        String consumer_id = consumer_details;
        String customer_id = consumer_details.split("\\|")[3];

        String address_id  = address_details;

        String modifiedBy = new String();
        Date ModifiedTime = new Date();
/*        Date today = Calendar.getInstance().getTime();

        if(shipNo.isEmpty()) {
            SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmmss");
            String strTime= formatter.format(today);
            shipNo = "2176" + customer_id.split("\\|")[0] + strTime;
        }*/

        System.out.println("----Update Shippment Details with Order ID = " + order_id + " from ajaxUpdateShippmentURL-----");
        int result = shippmentJDBCTemplate.insertNewShippment(shipNo, weight, products, consumer_id, customer_id, address_id, modifiedBy, ModifiedTime, enabled, order_id, printTimes, productType, comments, carrierTaken);

        return result;
    }
    @RequestMapping(value="/updateOrderOwnerURL") //, method=RequestMethod.GET
    public int updtepOrderOwnerShip(@RequestParam(value="customer_name", required=true) String customer_name,
                                    @RequestParam(value="customer_id",   required=true) int customer_id,
                                    @RequestParam(value="order_id",      required=true) int order_id) {
        int result = 0;
        result = orderJDBCTemplate.updtepOrderOwnerShip(customer_id, customer_name, order_id);
        return result;
    }

    @RequestMapping(value="/ajaxCustomerAutoSearchURL_backup") //, method=RequestMethod.GET
    public List ajaxCustomerList(@RequestParam(value="keyword", required=true) String keyword) {
        System.out.println("----Listing Customer with keyword for Cusotmer Name= " + keyword + " from ajaxCustomerAutoSearchURL-----");
        List<Customer> ajaxCustomersList = customerJDBCTemplate.getListCustomers();
        return ajaxCustomersList;
    }

    @RequestMapping(value = "/ajaxCustomerAutoSearchURL") //, method = RequestMethod.GET ajaxCustomerAutoSearchURL?=
    public List<Tag> ajaxCustomerAutoSearch(@RequestParam(value="keyword", required=true) String keyword) {
        return simulateSearchResult(keyword); //Method below
    }

    @RequestMapping(value = "/ajaxConsumerOptionsURL") //, method = RequestMethod.GET ajaxCustomerAutoSearchURL?=
    public List ajaxConsumerOptions(@RequestParam(value="customer_id", required=true) String customer_id) {
            List<Consumer> ajaxConsumerOptions = consumerJDBCTemplate.getListConsumers(customer_id);
        return ajaxConsumerOptions; //Method below
    }
    @RequestMapping(value="/ajaxLoadConsumerURL") //, method=RequestMethod.GET
    public Consumer ajaxLoadConsumer(@RequestParam(value="consumer_id", required=true) int consumer_id) {
        System.out.println("----Listing ONE Consumer with consumer_id = "+consumer_id+" from ajaxLoadConsumerURL-----" );
        Consumer consumer = consumerJDBCTemplate.getConsumer(consumer_id);
        return consumer;
    }

    @RequestMapping(value = "/ajaxAddressOptionsURL") //, method = RequestMethod.GET ajaxCustomerAutoSearchURL?=
    public List ajaxAddressOptions(@RequestParam(value="consumer_id", required=true) String consumer_id) {
        List<Address> ajaxAddressOptions = addressJDBCTemplate.getListAddresses(consumer_id);
        return ajaxAddressOptions; //Method below
    }
    @RequestMapping(value = "/ajaxGetAddressURL") //
    public Address ajaxGetAddress(@RequestParam(value="address_id", required=true) String address_id) {
        Address ajaxAddress = addressJDBCTemplate.getAddress(address_id);
        return ajaxAddress;
    }

    @RequestMapping(value = "/ajaxPrintTimesURL") //
    public int ajaxPrintTimes(@RequestParam(value="shipmentNo", required=true) String shipmentNo) {
         int result = shippmentJDBCTemplate.updatePrintTimes(shipmentNo);
        System.out.println("----Shippment Oder Print for Shippment Order ID = " + shipmentNo + " from ajaxPrintTimesURL| update result is"+ result +"-----");
        return result;
    }

    @RequestMapping(value = "/ajaxDeleteShippmentURL") //
    public int ajaxPrintTimes(@RequestParam(value="ship_id", required=true) int ship_id) {
        int result = shippmentJDBCTemplate.deleteShippment(ship_id);
        System.out.println("----Delete ONE shippment with ship_id= "+ship_id+" from ajaxLoadConsumerURL-----" );
        return result;
    }

    @RequestMapping(value = "/ajaxSaveCanvasURL") //
    public void ajaxSaveCanvas(@RequestParam(value="imageData", required=true) String imageData) {
        InputStream in = null;
        FileOutputStream fos = null;
        try {
            imageData = imageData.substring("data:image/jpeg;base64,".length());
            byte[] contentData = imageData.getBytes();
            byte[] decodedData = Base64.getDecoder().decode(contentData);
            //decodedData = compress_bytes(decodedData);

            String imgName = String.valueOf(System.currentTimeMillis()) + ".jpg";
            fos = new FileOutputStream(imgName);
            fos.write(decodedData);
            System.out.println("----iamge:"+imgName+" saved!!!-----" );
            if (in != null) {
                in.close();
            }
            if (fos != null) {
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            String loggerMessage = "Save image failed !!";
        } finally {

        }

        //System.out.println("----imageData is: data:image/png;base64,"+imageData+"-----" );
    }

    private List<Tag> simulateSearchResult(String keyword) {
        List<Tag> data = new ArrayList<Tag>();
        List<Customer> ajaxCustomersList = new ArrayList<Customer>();
        if(keyword.trim().length() != 0) ajaxCustomersList = customerJDBCTemplate.getListCustomers(keyword);
        for(int a=0; a<ajaxCustomersList.size(); a++) {
            data.add(new Tag(ajaxCustomersList.get(a).getCustomer_id(), ajaxCustomersList.get(a).getCustomer_name(), ajaxCustomersList.get(a).getWechat_id()));
        }

        List<Tag> result = new ArrayList<Tag>();
        // iterate a list and filter by keyword
        for (Tag tag : data) {
            if (tag.getTagName().contains(keyword)) {
                result.add(tag);
            }
        }

        if(result.size() == 0) {
            Tag noneTag = new Tag(99999, "None", "None");
            result.add(noneTag);
        }

        return result;
    }

    /////////////////////////////////////////////////////////////////////
    public static byte[] compress_bytes(byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated code... index
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        System.out.println("Original: " + data.length / 1024 + " Kb");
        System.out.println("Compressed: " + output.length / 1024 + " Kb");
        return output;
    }

    public static byte[] compress(byte[] input) {
/*        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            OutputStream out = new DeflaterOutputStream(baos);
            out.write(text.getBytes("UTF-8"));
            out.close();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        return baos.toByteArray();*/
        //byte[] input = text.getBytes();
        // Compressor with highest level of compression
        Deflater compressor = new Deflater();
        compressor.setLevel(Deflater.BEST_COMPRESSION);
        // Give the compressor the data to compress
        compressor.setInput(input);
        compressor.finish();
        // Create an expandable byte array to hold the compressed data.
        // It is not necessary that the compressed data will be smaller than
        // the uncompressed data.
        ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
        // Compress the data
        byte[] buf = new byte[2056];
        while (!compressor.finished()) {
            int count = compressor.deflate(buf);
            bos.write(buf, 0, count);
        }
        try {
            bos.close();
        } catch (IOException e) {
        }
        // Get the compressed data
        byte[] compressedData = bos.toByteArray();
        return compressedData;
    }

    public static String decompress(byte[] bytes) {
        InputStream in = new InflaterInputStream(new ByteArrayInputStream(bytes));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[8192];
            int len;
            while((len = in.read(buffer))>0)
                baos.write(buffer, 0, len);
            return new String(baos.toByteArray(), "UTF-8");
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
    /////////////////////////////////////////////////////////////////////
}