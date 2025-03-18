INSERT INTO owner (id, name) VALUES (NEXT VALUE FOR seq_owner, 'hustand');
INSERT INTO owner (id, name) VALUES (NEXT VALUE FOR seq_owner, 'wife');

INSERT INTO credit_category (id, description) VALUES (NEXT VALUE FOR seq_credit_category, 'salary');
INSERT INTO credit_category (id, description) VALUES (NEXT VALUE FOR seq_credit_category, 'interim');

INSERT INTO debit_category (id, description) VALUES (NEXT VALUE FOR seq_debit_category, 'tax');
INSERT INTO debit_category (id, description) VALUES (NEXT VALUE FOR seq_debit_category, 'transport');
INSERT INTO debit_category (id, description) VALUES (NEXT VALUE FOR seq_debit_category, 'housing');

INSERT INTO equity_category (id, description) VALUES (NEXT VALUE FOR seq_equity_category, 'cash');
INSERT INTO equity_category (id, description) VALUES (NEXT VALUE FOR seq_equity_category, 'bank');

INSERT INTO credit_account (id, description, category_id) VALUES (NEXT VALUE FOR seq_credit_account, 'work', 1);
INSERT INTO credit_account (id, description, category_id) VALUES (NEXT VALUE FOR seq_credit_account, 'internship', 2);

INSERT INTO debit_account (id, description, category_id) VALUES (NEXT VALUE FOR seq_debit_account, 'gasoline', 2);
INSERT INTO debit_account (id, description, category_id) VALUES (NEXT VALUE FOR seq_debit_account, 'bus', 2);
INSERT INTO debit_account (id, description, category_id) VALUES (NEXT VALUE FOR seq_debit_account, 'airplane', 2);

INSERT INTO equity_account (id, description, category_id) VALUES (NEXT VALUE FOR seq_equity_account, 'wallet', 1);
INSERT INTO equity_account (id, description, category_id) VALUES (NEXT VALUE FOR seq_equity_account, 'savings', 2);
INSERT INTO equity_account (id, description, category_id) VALUES (NEXT VALUE FOR seq_equity_account, 'checking', 2);