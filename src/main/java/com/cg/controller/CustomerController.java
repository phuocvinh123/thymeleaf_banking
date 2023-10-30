package com.cg.controller;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraw;
import com.cg.service.customer.ICustomerService;
import com.cg.service.deposit.IDepositService;
import com.cg.service.transfer.ITransferService;
import com.cg.service.withdraw.IWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;


@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IDepositService depositService;
    @Autowired
    private ITransferService transferService;
    @Autowired

    private IWithdrawService withdrawService;


    @GetMapping
    public String showListPage(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);

        return "customer/list";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("customer", new Customer());

        return "customer/create";
    }

    @GetMapping("/delete/{customerId}")
    public String showCreatePage(@PathVariable Long customerId, Model model) {
        Customer customerOptional = customerService.findById(customerId);
        model.addAttribute("customer", customerOptional);
        return "customer/delete";
    }

    @GetMapping("/deposit/{customerId}")
    public String showDepositPage(@PathVariable Long customerId, Model model) {
        Customer customerOptional = customerService.findById(customerId);
        Deposit deposit = new Deposit();
        deposit.setCustomer(customerOptional);

        model.addAttribute("deposit", deposit);
        return "customer/deposit";
    }

    @GetMapping("/withdraw/{customerId}")
    public String showWithdrawPage(@PathVariable Long customerId, Model model) {

        Customer customerOptional = customerService.findById(customerId);
        Withdraw withdraw = new Withdraw();
        withdraw.setCustomer(customerOptional);

        model.addAttribute("withdraw", withdraw);

        return "customer/withdraw";
    }

    @GetMapping("/transfer/{senderId}")
    public String showTransferPage(@PathVariable Long senderId, Model model) {
        Customer customerOptional = customerService.findById(senderId);
        List<Customer> recipients = customerService.findAllByIdNot(senderId);


            Transfer transfer = new Transfer();
            transfer.setSender(customerOptional);
            model.addAttribute("transfer", transfer);
            model.addAttribute("recipients", recipients);

        return "customer/transfer";
    }

    @GetMapping("/update/{id}")
    public String ShowUpdate(@PathVariable long id, Model model) {
        Customer customerOptional = customerService.findById(id);
            model.addAttribute("customer", customerOptional);

        return "customer/update";
    }

        @GetMapping("/transfer_histories")
    public String ShowTransferHistories(Model model){
        List<Transfer> transfers = transferService.findAll();
        model.addAttribute("transfers", transfers);
        return "customer/transfer_histories";
    }
    @PostMapping("/create")
    public String createCustomer(@ModelAttribute Customer customer, Model model) {
        customerService.save(customer);
        model.addAttribute("customer", new Customer());
        model.addAttribute("success", true);
        model.addAttribute("message", "Created successfully");
        return "customer/create";
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable long id, @ModelAttribute Customer customer, Model model) {
        customer.setId(id);
        customerService.save(customer);
        model.addAttribute("success", true);
        model.addAttribute("message", "Updated successfully");
        return "customer/update";
    }

    @PostMapping("/delete/{customerId}")
    public String deleteCustomer(@PathVariable Long customerId, RedirectAttributes redirectAttributes) {

        customerService.deleteById(customerId);

        redirectAttributes.addFlashAttribute("success", true);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully");

        return "redirect:/customers";
    }

    @PostMapping("/deposit/{customerId}")
    public String deposit(@PathVariable Long customerId, @ModelAttribute Deposit deposit, Model model) {

        Customer customer = customerService.findById(customerId);
        deposit.setCustomer(customer);

        customerService.deposit(deposit);

        deposit.setTransactionAmount(null);
        model.addAttribute("deposit", deposit);
        model.addAttribute("success", true);
        model.addAttribute("message", "Deposit successfully");

        return "customer/deposit";
    }

    @PostMapping("/withdraw/{customerId}")
    public String Withdraw(@PathVariable Long customerId, @ModelAttribute Withdraw withdraw, Model model) {
        Customer customer = customerService.findById(customerId);
        withdraw.setCustomer(customer);

        if (withdrawService.isValidWithdrawal(withdraw)) {
            customerService.withdraw(withdraw);

            model.addAttribute("success", true);
            model.addAttribute("message", "Withdrawal successful");
        } else {
            model.addAttribute("success", false);
            model.addAttribute("message", "Invalid withdrawal");
        }

        withdraw.setTransactionAmount(null);
        model.addAttribute("withdraw", withdraw);

        return "customer/withdraw";
    }

    @PostMapping("/transfer/{senderId}")
    public String Transfer(@PathVariable Long senderId, @ModelAttribute Transfer transfer, @RequestParam Long recipientId, Model model) {
        Customer sender = customerService.findById(senderId);
        Customer recipient = customerService.findById(recipientId);
        transfer.setSender(sender);
        transfer.setRecipient(recipient);

        if (transferService.isValidTransfer(transfer)) {
            customerService.transfer(transfer);
            transferService.save(transfer);

            model.addAttribute("success", true);
            model.addAttribute("message", "Transfer successful");
        } else {
            model.addAttribute("success", false);
            model.addAttribute("message", "Invalid transfer");
        }

        List<Customer> recipients = customerService.findAllByIdNot(senderId);
        model.addAttribute("transfer", transfer);
        model.addAttribute("recipients", recipients);

        return "customer/transfer";
    }

}
