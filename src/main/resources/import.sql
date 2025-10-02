insert into TEMPLATE_PARAMETER (id, name, description)
    VALUES (nextval('TEMPLATE_PARAMETER_SEQ'), 'registration_id', 'The user registration ID');
insert into TEMPLATE_PARAMETER (id, name, description)
    VALUES (nextval('TEMPLATE_PARAMETER_SEQ'), 'university_name', 'The name of the university');
insert into TEMPLATE_PARAMETER (id, name, description)
    VALUES (nextval('TEMPLATE_PARAMETER_SEQ'), 'faculty_name', 'The name of the faculty');
insert into TEMPLATE_PARAMETER (id, name, description)
    VALUES (nextval('TEMPLATE_PARAMETER_SEQ'), 'session_id', 'The unique session, or example 2025/26');


insert into TEMPLATE_DESCRIPTOR (id, name, description)
    VALUES (nextval('TEMPLATE_DESCRIPTOR_SEQ'), 'confirm_registration', 'Template to confirm user registration');
insert into TEMPLATE_DESCRIPTOR (id, name, description)
    VALUES (nextval('TEMPLATE_DESCRIPTOR_SEQ'), 'payment_confirmation', 'Template to confirm payment of semester fee');
insert into TEMPLATE_DESCRIPTOR (id, name, description)
    VALUES (nextval('TEMPLATE_DESCRIPTOR_SEQ'), 'payment_notification', 'Template to notify user about upcoming payment of semester fee');
insert into TEMPLATE_DESCRIPTOR (id, name, description)
    VALUES (nextval('TEMPLATE_DESCRIPTOR_SEQ'), 'payment_reminder', 'Template to remind user about overdue payment of semester fee');
