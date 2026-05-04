SET REFERENTIAL_INTEGRITY FALSE;

DELETE FROM credit_entry;

DELETE FROM debit_entry;

DELETE FROM transfer_entry;

DELETE FROM owner_equity_account_initial_value;

DELETE FROM credit_account;

DELETE FROM debit_account;

DELETE FROM equity_account;

DELETE FROM owner;

DELETE FROM credit_category;

DELETE FROM debit_category;

DELETE FROM equity_category;

ALTER SEQUENCE seq_credit_account RESTART WITH 1;

ALTER SEQUENCE seq_credit_category RESTART WITH 1;

ALTER SEQUENCE seq_credit_entry RESTART WITH 1;

ALTER SEQUENCE seq_debit_account RESTART WITH 1;

ALTER SEQUENCE seq_debit_category RESTART WITH 1;

ALTER SEQUENCE seq_debit_entry RESTART WITH 1;

ALTER SEQUENCE seq_equity_account RESTART WITH 1;

ALTER SEQUENCE seq_equity_category RESTART WITH 1;

ALTER SEQUENCE seq_owner RESTART WITH 1;

ALTER SEQUENCE seq_transfer_entry RESTART WITH 1;

SET REFERENTIAL_INTEGRITY TRUE;