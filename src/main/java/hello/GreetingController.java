package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {
    Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @Autowired
    OrderJDBCTemplate orderJDBCTemplate;

    @Autowired
    TransactionJDBCTemplate transactionJDBCTemplate;

    @Autowired
    ProductJDBCTemplate productJDBCTemplate;

    @RequestMapping(value="/greetingsURL", method=RequestMethod.GET)
    public String orderListForm(Model model) {
        System.out.println("----Listing All Orders from greetingsURL-----" );
        List<Order> orders = orderJDBCTemplate.getListOrders();
        model.addAttribute("orders_label", orders);
        return "greetingsPAGE";
    }

    @RequestMapping(value="/orderDetailURL", method=RequestMethod.GET)
    public String greeting(@RequestParam(value="id", required=true) int id, Model model) {
        System.out.println("----Listing Order with ID = "+id+" from orderDetailURL-----" );
        Order order = orderJDBCTemplate.getOrder(id);

        System.out.println("----Listing Transactions with Order ID = "+id+" from orderDetailURL-----" );
        List<Transaction> trans = transactionJDBCTemplate.getListTransactions(id);
        System.out.println("----Listing Transactions with Order ID = "+trans.get(0).getDeal_price()+"||"+trans.get(0).getProduct_id()+" from orderDetailURL-----" );
        for(int a=0; a<trans.size(); a++) {
            String product_name = productJDBCTemplate.getProduct(trans.get(a).getProduct_id()).getProduct_name();
            trans.get(a).setProduct_name(product_name);
            System.out.println("----Listing Product Name for each Transaction = "+trans.get(a).getProduct_name()+" from orderDetailURL-----" );
        }
        order.setNumberOfCategories(trans.size());
        model.addAttribute("order_label", order);
        model.addAttribute("order_label_other", order);
        model.addAttribute("transactions_label", trans);
        model.addAttribute("transactions_label", trans);
        model.addAttribute("trans_label", new Transaction());
        return "orderPAGE";
    }

    @RequestMapping(value="/resultURL", method=RequestMethod.POST)
    public String greetingSubmit(@ModelAttribute Order order_label, Order order_label_other, Model model) {
        System.out.println("----Show result page from resultURL-----" );
        model.addAttribute("order_label", order_label);
        model.addAttribute("order_label_other", order_label_other);
        return "resultPAGE";
    }
}
