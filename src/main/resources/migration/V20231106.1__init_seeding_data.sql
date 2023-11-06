INSERT INTO "public"."product" ("product_id", "product_name", "price", "product_detail", "created_at", "created_by", "updated_at", "updated_by") VALUES
('fitness-01', 'Overweight and Obesity Package', 200000.00, '{"schedule":"senin/rabu/jumat","durationMenu":"[5,10,15]","trainingList":"[\"cardio\",\"pushup\",\"situp\"]"}', '2023-10-30 17:54:25.905759', 'system', '1900-01-01 00:00:00', ''),
('fitness-02', 'Fitness Package', 100000.00, '{"schedule":"selasa/rabu/kamis","durationMenu":"[5,10,15,20]","trainingList":"[\"pushup\",\"benchpress\",\"plank\"]"}', '2023-10-30 17:54:25.905759', 'system', '1900-01-01 00:00:00', '');

INSERT INTO "public"."payment_data" ("payment_data_id", "token_data", "card_number_masking", "card_holder_name", "created_at", "created_by", "updated_at", "updated_by") VALUES
('538e75e7-2270-478c-a540-17eff668944c', 'eyJjYXJkTnVtYmVyVG9rZW4iOiJOREF3TURBd01EQXdNREF3TVRBNU1RPT0iLCJjYXJkSWRlbnRpZnlUb2tlbiI6Ik1USXpjM056YzNOemN3PT0iLCJjYXJkRXhwaXJlZERhdGUiOiIwNS8yNSJ9', '400000******1091', 'wowo', '2023-10-28 12:41:17.393', 'system', '2023-10-30 22:52:56.472', 'customer');

INSERT INTO "public"."user" ("user_id", "name", "phone_number", "dob", "member_status", "payment_data_id", "created_at", "created_by", "updated_at", "updated_by") VALUES
('0cf5ae48-9014-4ccc-adfe-0d96dfe38771', 'john doe', '6281212341234', '', 'registered', '538e75e7-2270-478c-a540-17eff668944c', '2023-10-30 22:52:56.473', 'system', '1900-01-01 00:00:00', 'system'),
('50253feb-5f6e-42cb-957c-fefb4dbba5eb', 'john doeloe', '6281212341234', '08-08-2008', 'registered', '538e75e7-2270-478c-a540-17eff668944c', '2023-10-28 12:41:17.408', 'system', '2023-10-29 23:38:44', 'customer');

INSERT INTO "public"."user_login" ("user_login_id", "user_id", "email", "password", "is_active", "created_at", "created_by", "updated_at", "updated_by") VALUES
('b9dc6643-64d8-4843-9f66-1b12756a3993', '50253feb-5f6e-42cb-957c-fefb4dbba5eb', 'johndoe1@gmail.com', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 't', '2023-10-30 22:52:56.493', 'system', '1900-01-01 00:00:00', 'system');

INSERT INTO "public"."user_subscription" ("user_id", "product_id", "status_subscription", "training_remaining", "training_duration", "subscribe_at", "created_at", "created_by", "updated_at", "updated_by") VALUES
('50253feb-5f6e-42cb-957c-fefb4dbba5eb', 'fitness-01', 'subscribed', 10, 10, '1900-01-01 00:00:00', '2023-11-02 23:46:14.316', 'system', '2023-11-03 01:30:08.807', 'customer');

INSERT INTO "public"."invoice_request" ("invoice_id", "invoice_status", "user_id", "product_id", "amount", "duration", "invoice_at", "created_at", "created_by", "updated_at", "updated_by") VALUES
('6296fc20-4e2e-493b-8638-d7c342243723', 'paid', '50253feb-5f6e-42cb-957c-fefb4dbba5eb', 'fitness-01', 2000000.00, 10, '2023-11-02 23:45:45.352', '2023-11-02 23:45:45.352', 'system', '2023-11-03 01:30:08.807', 'customer');

INSERT INTO "public"."payment_request" ("payment_id", "payment_status", "invoice_id", "payment_at", "payment_data_id", "payment_amount", "payment_external_id", "created_at", "created_by", "updated_at", "updated_by") VALUES
('40b5350b-f035-4927-b4a2-6c9af6eca964', 'success', '6296fc20-4e2e-493b-8638-d7c342243723', '2023-11-03 01:30:08.104', '538e75e7-2270-478c-a540-17eff668944c', 2000000.00, '09323147631675939094', '2023-11-03 00:56:12.047', 'customer', '2023-11-03 01:30:08.807', 'customer');