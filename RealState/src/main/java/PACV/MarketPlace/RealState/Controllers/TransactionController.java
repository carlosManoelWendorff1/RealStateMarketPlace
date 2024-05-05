package PACV.MarketPlace.RealState.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import PACV.MarketPlace.RealState.Models.Transaction;
import PACV.MarketPlace.RealState.Services.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("id") Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        if (transaction != null) {
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
        Transaction newTransaction = transactionService.addTransaction(transaction);
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public HttpStatus updateTransaction(@PathVariable("id") Long id, @RequestBody Transaction transaction) {
        return transactionService.updateTransaction(transaction);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteTransaction(@PathVariable("id") Long id) {
        return transactionService.deleteTransaction(id);
    }
}
