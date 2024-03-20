package PACV.MarketPlace.RealState.Repositories;

import org.springframework.data.repository.CrudRepository;

import PACV.MarketPlace.RealState.Models.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction,Long>{
    
}
