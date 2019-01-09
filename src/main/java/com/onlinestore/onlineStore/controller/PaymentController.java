package com.onlinestore.onlineStore.controller;

import ch.qos.logback.core.pattern.Converter;
import com.onlinestore.onlineStore.model.User;
import com.onlinestore.onlineStore.model.payment.AddressForCustomer;
import com.onlinestore.onlineStore.model.payment.BalanceOfAddress;
import com.onlinestore.onlineStore.model.payment.MyWallet;
import com.onlinestore.onlineStore.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@RestController
public class PaymentController {

    @Autowired
    UserService userService;

    private static MyWallet myWallet = new MyWallet("32ed97a8-9782-4cca-b50b-ad3e2917143e", "23081997aab");
    private static RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    private static String baseURL = "http://192.168.1.126:3000/";

    private static String ControllerNameForPay="merchant/";
    //API adresa za nekoj poednostavni requesti(proveruvanje na status)
    private static String baseWebAddressSimpleApi = "https://blockchain.info/q/";
    RestTemplate restTemplateSimpleApi = new RestTemplate();
    AddressForCustomer addressForCustomer = null;

    //Client i api za konverzija od bitcoin vo dolari
    private RestTemplate restTemplateForPriceConvert = new RestTemplate();
    private static String URLforPriceConverting = "https://blockchain.info/tobtc?";

    @RequestMapping(value = "/payment/generateAddress", method = RequestMethod.GET)
    public ModelAndView generateAddress(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findByUsername(authentication.getName());
        ModelAndView mav = new ModelAndView("payment/showAddressForPayment");
        String priceInBitcoin = null;
        Integer sum = userService.findByUsername(loggedUser.getUsername()).getProducts().stream()
                .mapToInt(p->p.getPrice())
                .sum();
        String path = myWallet.id + "/new_address?password=" + myWallet.password;
        AddressForCustomer addressForCustomer = getNewAddress(path);
        model.addAttribute("addressForCustomer", addressForCustomer);
        priceInBitcoin = convertToBitcoin(sum,"USD");
        model.addAttribute("priceInBitcoin",priceInBitcoin);
        return mav;
    }

    public String convertToBitcoin(Integer ammount, String currency){
        ResponseEntity<String> entity = restTemplate.getForEntity(URLforPriceConverting + "currency=" + currency + "&value=" + ammount.toString(), String.class);
        String body = entity.getBody();
        MediaType contentType = entity.getHeaders().getContentType();
        HttpStatus statusCode = entity.getStatusCode();
        return body;
    }

    public  AddressForCustomer getNewAddress(String path){

        addressForCustomer = restTemplate.getForObject(baseURL+ControllerNameForPay+path, AddressForCustomer.class);

        return addressForCustomer;
    }

    public String getBalance(){
        ResponseEntity<String> entity =
                restTemplate.getForEntity(baseWebAddressSimpleApi+"addressbalance/" + addressForCustomer.address +
                        "?confirmations=0",String.class);
        String body = entity.getBody();
        MediaType contentType = entity.getHeaders().getContentType();
        HttpStatus statusCode = entity.getStatusCode();
        return body;
    }

    @RequestMapping(value = "/payment/getStatus", method = RequestMethod.GET)
    public ModelAndView getStatusOfTransaction(Model model){
        String balance = getBalance();
        String status = null;
        ModelAndView mav = new ModelAndView("payment/statusOfTransaction");
        model.addAttribute("balance", balance);
        model.addAttribute("addressForCustomer",addressForCustomer);
        return mav;
    }
}