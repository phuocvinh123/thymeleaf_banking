package com.cg.controller;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraw;
import com.cg.service.customer.CustomerServiceImpl;
import com.cg.service.customer.ICustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private ICustomerService customerService = new CustomerServiceImpl();

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
        Customer customer = customerService.findById(customerId);
        model.addAttribute("customer", customer);

        return "customer/delete";
    }

    @GetMapping("/deposit/{customerId}")
    public String showDepositPage(@PathVariable Long customerId, Model model) {

        Customer customer = customerService.findById(customerId);
        Deposit deposit = new Deposit();
        deposit.setCustomer(customer);

        model.addAttribute("deposit", deposit);

        return "customer/deposit";
    }

    @GetMapping("/withdraw/{customerId}")
    public String showWithdrawPage(@PathVariable Long customerId, Model model) {

        Customer customer = customerService.findById(customerId);
        Withdraw withdraw = new Withdraw();
        withdraw.setCustomer(customer);

        model.addAttribute("withdraw", withdraw);

        return "customer/withdraw";
    }

    @GetMapping("/transfer/{senderId}")
    public String showTransferPage(@PathVariable Long senderId, Model model) {

        Customer sender = customerService.findById(senderId);

        List<Customer> recipients = customerService.findAllWithoutId(senderId);

        Transfer transfer = new Transfer();
        transfer.setSender(sender);

        model.addAttribute("transfer", transfer);
        model.addAttribute("recipients", recipients);

        return "customer/transfer";
    }

    @GetMapping("/update/{id}")
    public String ShowUpdate(@PathVariable long id, Model model) {
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);
        return "customer/update";
    }

    @PostMapping("/create")
    public String createCustomer(@ModelAttribute Customer customer, Model model) {

        if (customer.getFullName().length() == 0) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Created unsuccessful");
        } else {
            customerService.create(customer);

            model.addAttribute("customer", new Customer());

            model.addAttribute("success", true);
            model.addAttribute("message", "Created successfully");
        }

        return "customer/create";
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable long id, @ModelAttribute Customer customer, Model model) {
        model.addAttribute("customer", customer);
        customerService.update(id, customer);
        model.addAttribute("success", true);
        model.addAttribute("message", "Updated successfully");
        return "customer/update";
    }

    @PostMapping("/delete/{customerId}")
    public String deleteCustomer(@PathVariable Long customerId, RedirectAttributes redirectAttributes) {

        customerService.removeById(customerId);

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
    public String withdraw(@PathVariable Long customerId, @ModelAttribute Withdraw withdraw, Model model) {
        Customer customer = customerService.findById(customerId);
        BigDecimal withdrawalAmount = withdraw.getTransactionAmount();
        if (withdrawalAmount == null || withdrawalAmount.compareTo(BigDecimal.ZERO) <= 0 || withdrawalAmount.compareTo(customer.getBalance()) > 0) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Invalid withdrawal amount");
        } else {
            BigDecimal updatedBalance = customer.getBalance().subtract(withdrawalAmount);
            customer.setBalance(updatedBalance);
            customerService.update(customerId, customer);
            model.addAttribute("success", true);
            model.addAttribute("message", "Withdrawal successful");
        }
        withdraw.setTransactionAmount(null);
        withdraw.setCustomer(customer);
        model.addAttribute("withdraw", withdraw);
        return "customer/withdraw";
    }

    @PostMapping("/transfer/{senderId}")
    public String transfer(@PathVariable Long senderId, @ModelAttribute Transfer transfer, @RequestParam Long recipientId, Model model) {
        Customer sender = customerService.findById(senderId);
        Customer recipient = customerService.findById(recipientId);
        BigDecimal transferAmount = transfer.getTransferAmount();
        BigDecimal transferFee = transferAmount.multiply(BigDecimal.valueOf(0.1));

        if (transferAmount.compareTo(BigDecimal.ZERO) <= 0 || transferAmount.compareTo(sender.getBalance()) > 0) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Invalid transfer amount");
        } else {
            BigDecimal totalAmount = transferAmount.add(transferFee);
            if (totalAmount.compareTo(sender.getBalance()) > 0) {
                model.addAttribute("success", false);
                model.addAttribute("message", "Insufficient balance for transfer");
            } else {
                BigDecimal updatedSenderBalance = sender.getBalance().subtract(totalAmount);
                BigDecimal updatedRecipientBalance = recipient.getBalance().add(transferAmount);

                sender.setBalance(updatedSenderBalance);
                recipient.setBalance(updatedRecipientBalance);

                customerService.update(senderId, sender);
                customerService.update(recipient.getId(), recipient);

                model.addAttribute("success", true);
                model.addAttribute("message", "Transfer successful");
            }
        }

        List<Customer> recipients = customerService.findAllWithoutId(senderId);
        transfer.setSender(sender);
        transfer.setTransferAmount(null);
        model.addAttribute("transfer", transfer);
        model.addAttribute("recipients", recipients);

        return "customer/transfer";
    }
}
