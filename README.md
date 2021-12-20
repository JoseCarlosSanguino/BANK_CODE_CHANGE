# BANK_CODE_CHANGE

I have made the database in memory

POST --> http://localhost:8080/api/transactions/       -----  TO REGISTER A TRANSACTION

{
    "reference":"aaaa",
    "amount":25.38,
    "fee":5.38,
    "date":"2021-11-19",
    "account_iban":"SSSSSS"
}

GET ---> http://localhost:8080/api/transactions/        ---list get transaction


GET ---> http://localhost:8080/api/transactions/status        

{
   "reference" : "aaa",
   "channel" : "CLLIENT"
}


GET ---> http://localhost:8080/api/transactions/searchTransactions/{account_iban}/{order}")   ORDER(ascending/descending)
