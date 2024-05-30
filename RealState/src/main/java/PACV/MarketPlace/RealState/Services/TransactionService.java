package PACV.MarketPlace.RealState.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import PACV.MarketPlace.RealState.Models.Transaction;
import PACV.MarketPlace.RealState.Repositories.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return (List<Transaction>) transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public Transaction addTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
        return transactionRepository.findById(transaction.getId()).orElse(null);
    }

    public HttpStatus updateTransaction(Transaction transaction){
        if(transactionRepository.existsById(transaction.getId())){
            Optional<Transaction> oldTransactionOptional = transactionRepository.findById(transaction.getId());
            if(oldTransactionOptional.isPresent()){
                Transaction oldTransaction = oldTransactionOptional.get();
                if(!oldTransaction.toString().equals(transaction.toString())){
                    this.transactionRepository.save(transaction);
                    if(transactionRepository.existsById(transaction.getId())){
                        return HttpStatus.OK;
                    }
                }   
                else{
                    return HttpStatus.NOT_MODIFIED;
                }
            }
        }
        return HttpStatus.NOT_FOUND;
    }

    public HttpStatus deleteTransaction(Long id) {
        if(transactionRepository.existsById(id)){
            Optional<Transaction> oldTransactionOptional = transactionRepository.findById(id);
            if(oldTransactionOptional.isPresent()){
                transactionRepository.deleteById(id);
                if(!transactionRepository.existsById(id)){
                        return HttpStatus.OK;
                }   
                else{
                    return HttpStatus.NOT_MODIFIED;
                }
            }
        }
        return HttpStatus.NOT_FOUND;
    }
}
