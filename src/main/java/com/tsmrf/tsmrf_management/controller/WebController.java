
package com.tsmrf.tsmrf_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tsmrf.tsmrf_management.service.AccountService;
import com.tsmrf.tsmrf_management.service.AnalyticAccountService;
import com.tsmrf.tsmrf_management.service.BudgetService;
import com.tsmrf.tsmrf_management.service.ContactService;
import com.tsmrf.tsmrf_management.service.InvoiceService;
import com.tsmrf.tsmrf_management.service.JournalEntryService;
import com.tsmrf.tsmrf_management.service.JournalService;
import com.tsmrf.tsmrf_management.service.OpticalSorterService;
import com.tsmrf.tsmrf_management.service.PaymentService;
import com.tsmrf.tsmrf_management.service.ProductService;
import com.tsmrf.tsmrf_management.service.ProductionService;
import com.tsmrf.tsmrf_management.service.PurchaseOrderService;
import com.tsmrf.tsmrf_management.service.ReportService;
import com.tsmrf.tsmrf_management.service.SalesOrderService;
import com.tsmrf.tsmrf_management.service.VendorBillService;

@Controller
public class WebController {

    private final ContactService contactService;
    private final ProductService productService;
    private final OpticalSorterService sorterService;
    private final ProductionService productionService;
    private final PurchaseOrderService purchaseOrderService;
    private final VendorBillService vendorBillService;
    private final SalesOrderService salesOrderService;
    private final InvoiceService invoiceService;
    private final PaymentService paymentService;
    private final AccountService accountService;
    private final AnalyticAccountService analyticAccountService;
    private final BudgetService budgetService;
    private final JournalService journalService;
    private final JournalEntryService journalEntryService;
    private final ReportService reportService;

    public WebController(
            ContactService contactService,
            ProductService productService,
            OpticalSorterService sorterService,
            ProductionService productionService,
            PurchaseOrderService purchaseOrderService,
            VendorBillService vendorBillService,
            SalesOrderService salesOrderService,
            InvoiceService invoiceService,
            PaymentService paymentService,
            AccountService accountService,
            AnalyticAccountService analyticAccountService,
            BudgetService budgetService,
            JournalService journalService,
            JournalEntryService journalEntryService,
            ReportService reportService) {

        this.contactService = contactService;
        this.productService = productService;
        this.sorterService = sorterService;
        this.productionService = productionService;
        this.purchaseOrderService = purchaseOrderService;
        this.vendorBillService = vendorBillService;
        this.salesOrderService = salesOrderService;
        this.invoiceService = invoiceService;
        this.paymentService = paymentService;
        this.accountService = accountService;
        this.analyticAccountService = analyticAccountService;
        this.budgetService = budgetService;
        this.journalService = journalService;
        this.journalEntryService = journalEntryService;
        this.reportService = reportService;
    }

    // Dashboard
    @GetMapping({"/", "/web", "/web/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("contactCount",
                contactService.getAllContacts().size());
        model.addAttribute("productCount",
                productService.getAllProducts().size());
        model.addAttribute("sorterCount",
                sorterService.getAllSorters().size());
        model.addAttribute("productionCount",
                productionService.getAllProductions().size());
        model.addAttribute("purchaseOrderCount",
                purchaseOrderService.getAll().size());
        model.addAttribute("salesOrderCount",
                salesOrderService.getAll().size());
        model.addAttribute("invoiceCount",
                invoiceService.getAll().size());
        model.addAttribute("paymentCount",
                paymentService.getAll().size());

        return "dashboard";
    }

    // Master Data
    @GetMapping("/web/contacts")
    public String contacts(Model model) {
        model.addAttribute("contacts",
                contactService.getAllContacts());
        return "contacts/list";
    }

    @GetMapping("/web/products")
    public String products(Model model) {
        model.addAttribute("products",
                productService.getAllProducts());
        return "products/list";
    }

    @GetMapping("/web/sorters")
    public String sorters(Model model) {
        model.addAttribute("sorters",
                sorterService.getAllSorters());
        return "sorters/list";
    }

    @GetMapping("/web/accounts")
    public String accounts(Model model) {
        model.addAttribute("accounts",
                accountService.getAll());
        return "accounts/list";
    }

    @GetMapping("/web/analytic-accounts")
    public String analyticAccounts(Model model) {
        model.addAttribute("accounts",
                analyticAccountService.getAll());
        return "analytic-accounts/list";
    }

    // Production and Inventory
    @GetMapping("/web/production")
    public String production(Model model) {
        model.addAttribute("productions",
                productionService.getAllProductions());
        return "production/list";
    }

    // Purchasing
    @GetMapping("/web/purchase-orders")
    public String purchaseOrders(Model model) {
        model.addAttribute("purchaseOrders",
                purchaseOrderService.getAll());
        return "purchase-orders/list";
    }

    @GetMapping("/web/vendor-bills")
    public String vendorBills(Model model) {
        model.addAttribute("vendorBills",
                vendorBillService.getAll());
        return "vendor-bills/list";
    }

    // Sales and Invoicing
    @GetMapping("/web/sales-orders")
    public String salesOrders(Model model) {
        model.addAttribute("salesOrders",
                salesOrderService.getAll());
        return "sales-orders/list";
    }

    @GetMapping("/web/invoices")
    public String invoices(Model model) {
        model.addAttribute("invoices",
                invoiceService.getAll());
        return "invoices/list";
    }

    @GetMapping("/web/payments")
    public String payments(Model model) {
        model.addAttribute("payments",
                paymentService.getAll());
        return "payments/list";
    }

    // Accounting
    @GetMapping("/web/journals")
    public String journals(Model model) {
        model.addAttribute("journals",
                journalService.getAll());
        return "journals/list";
    }

    @GetMapping("/web/ledger")
    public String ledger(Model model) {
        model.addAttribute("entries",
                journalEntryService.getAll());
        return "ledger/list";
    }

    // Financial Reports
    @GetMapping("/web/reports/balance-sheet")
    public String balanceSheet(Model model) {
        model.addAttribute("report",
                reportService.getBalanceSheet());
        return "reports/balance-sheet";
    }

    @GetMapping("/web/reports/profit-loss")
    public String profitLoss(Model model) {
        model.addAttribute("report",
                reportService.getProfitLoss());
        return "reports/profit-loss";
    }

    @GetMapping("/web/reports/budget-variance")
    public String budgetVariance(Model model) {
        model.addAttribute("report",
                reportService.getBudgetVariance());
        return "reports/budget-variance";
    }

    // Budgets
    @GetMapping("/web/budgets")
    public String budgets(Model model) {
        model.addAttribute("budgets",
                budgetService.getAll());
        return "budgets/list";
    }
}