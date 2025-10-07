insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'account_last_digits', 'The last few digits of the customer’s account number, used for identification in messages.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'account_number', 'The unique number identifying the customer’s bank account.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'activation_deadline_date', 'The final date by which the customer must complete account activation.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'activation_link', 'The secure link for completing account activation.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'advisor_email', 'The contact email of the customer’s assigned financial advisor.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'bank_name', 'The official name of the bank or financial institution.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'branch_id', 'The internal identification number of the bank branch handling the customer’s account.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'branch_name', 'The name of the bank branch where the customer’s account is managed.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'customer_id', 'The unique identifier assigned to the customer in the bank’s system.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'customer_name', 'The full name of the customer.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'disbursement_timeframe', 'The number of business days within which an approved loan will be disbursed.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'fraud_reporting_link', 'The link to report fraudulent or suspicious transactions.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'loan_amount', 'The amount of money requested or approved in a loan application.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'loan_portal_link', 'The URL to access the loan management or application tracking portal.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'loan_reference_id', 'The unique reference ID assigned to a specific loan application.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'message_creation_date', 'The date on which the message or notification was generated.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'online_bank_link', 'The link to access the bank’s online banking portal.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'original_due_date', 'The original payment due date before any extensions or delays.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'overdue_amount', 'The total outstanding amount that is past its due date.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'payment_amount', 'The total amount that needs to be paid by the customer.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'payment_due_date', 'The scheduled date when a payment is due.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'payment_portal_link', 'The link to access the secure online payment portal.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'review_duration_days', 'The estimated number of business days required to review a loan application.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'security_contact_number', 'The phone number for the bank’s security or fraud prevention department.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'security_portal_link', 'The secure portal link for reviewing or verifying suspicious activity.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'statement_end_date', 'The end date of the billing or account statement period.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'statement_portal_link', 'The link where the customer can view or download their account statement.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'support_email', 'The contact email address for the bank’s customer support team.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'transaction_amount', 'The monetary amount of the processed transaction.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'transaction_datetime', 'The date and time when the transaction occurred.');

insert into TEMPLATE_PARAMETER (id, name, description)
values (nextval('TEMPLATE_PARAMETER_SEQ'), 'transaction_type', 'The type of transaction performed, such as deposit, withdrawal, or transfer.');


insert into TEMPLATE_DESCRIPTOR (id, name, description)
values (nextval('TEMPLATE_DESCRIPTOR_SEQ'), 'account_activation_reminder', 'Template to remind the customer to activate their newly created bank account.');

insert into TEMPLATE_DESCRIPTOR (id, name, description)
values (nextval('TEMPLATE_DESCRIPTOR_SEQ'), 'account_opening_confirmation', 'Template to confirm that a new bank account has been successfully opened.');

insert into TEMPLATE_DESCRIPTOR (id, name, description)
values (nextval('TEMPLATE_DESCRIPTOR_SEQ'), 'account_statement_available', 'Template to notify the customer that their periodic account statement is available for review or download.');

insert into TEMPLATE_DESCRIPTOR (id, name, description)
values (nextval('TEMPLATE_DESCRIPTOR_SEQ'), 'loan_application_received', 'Template to confirm receipt of a loan application and inform the customer about the review process.');

insert into TEMPLATE_DESCRIPTOR (id, name, description)
values (nextval('TEMPLATE_DESCRIPTOR_SEQ'), 'loan_approval_notification', 'Template to notify the customer that their loan application has been approved and includes the loan details.');

insert into TEMPLATE_DESCRIPTOR (id, name, description)
values (nextval('TEMPLATE_DESCRIPTOR_SEQ'), 'loan_rejection_notice', 'Template to inform the customer that their loan application was not approved and provide contact options for further clarification.');

insert into TEMPLATE_DESCRIPTOR (id, name, description)
values (nextval('TEMPLATE_DESCRIPTOR_SEQ'), 'overdue_payment_notice', 'Template to inform the customer that a scheduled payment is overdue and provide payment instructions.');

insert into TEMPLATE_DESCRIPTOR (id, name, description)
values (nextval('TEMPLATE_DESCRIPTOR_SEQ'), 'payment_due_reminder', 'Template to remind the customer about an upcoming payment and its due date.');

insert into TEMPLATE_DESCRIPTOR (id, name, description)
values (nextval('TEMPLATE_DESCRIPTOR_SEQ'), 'security_alert', 'Template to notify the customer about suspicious or potentially fraudulent activity detected on their account.');

insert into TEMPLATE_DESCRIPTOR (id, name, description)
values (nextval('TEMPLATE_DESCRIPTOR_SEQ'), 'transaction_alert', 'Template to inform the customer of a recent transaction performed on their account.');
