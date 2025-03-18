INSERT INTO owner (id, name) VALUES (NEXT VALUE FOR seq_owner, 'husband');
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

INSERT INTO owner_equity_account_initial_value (owner_id, equity_account_id, value) VALUES (1, 1, 10);
INSERT INTO owner_equity_account_initial_value (owner_id, equity_account_id, value) VALUES (1, 2, 150);
INSERT INTO owner_equity_account_initial_value (owner_id, equity_account_id, value) VALUES (2, 1, 50);
INSERT INTO owner_equity_account_initial_value (owner_id, equity_account_id, value) VALUES (2, 3, 100);

INSERT INTO credit_entry (id, in_owner_id, out_owner_id, in_account_id, out_account_id, value, date, note) VALUES (NEXT VALUE FOR seq_credit_entry, 2, 2, 2, 1, 100, NOW(), 'wife savings salary');
INSERT INTO credit_entry (id, in_owner_id, out_owner_id, in_account_id, out_account_id, value, date, note) VALUES (NEXT VALUE FOR seq_credit_entry, 1, 1, 1, 2, 100, NOW(), 'husband wallet internship');

INSERT INTO debit_entry (id, in_owner_id, out_owner_id, in_account_id, out_account_id, value, date, note) VALUES (NEXT VALUE FOR seq_credit_entry, 2, 2, 3, 3, 100, NOW(), 'wife airplane checking');
INSERT INTO debit_entry (id, in_owner_id, out_owner_id, in_account_id, out_account_id, value, date, note) VALUES (NEXT VALUE FOR seq_credit_entry, 1, 1, 1, 1, 100, NOW(), 'husband gasoline wallet');

INSERT INTO transfer_entry (id, in_owner_id, out_owner_id, in_account_id, out_account_id, value, date, note) VALUES (NEXT VALUE FOR seq_credit_entry, 2, 1, 3, 2, 100, NOW(), 'wife husband checking savings');
INSERT INTO transfer_entry (id, in_owner_id, out_owner_id, in_account_id, out_account_id, value, date, note) VALUES (NEXT VALUE FOR seq_credit_entry, 1, 2, 1, 3, 10, NOW(), 'husband wife wallet checking');
