INSERT INTO warehouse_area(name) VALUES ('area1');
INSERT INTO warehouse_area(name) VALUES ('area2');
INSERT INTO operator(name, assigned_area_id) VALUES ('Operator1', 1);
INSERT INTO operator(name, assigned_area_id) VALUES ('Operator2', 2);
INSERT INTO maintenance_job(status, area_id, assigned_operator_id) VALUES ('OPEN', 1, 1);
INSERT INTO maintenance_job(start_time, status, area_id, assigned_operator_id) VALUES (current_timestamp - interval '3' hour, 'IN_PROGRESS', 2, 2);
INSERT INTO maintenance_job(end_time,start_time, status, area_id, assigned_operator_id) VALUES (current_timestamp - interval '2' hour, current_timestamp - interval '3' hour ,'FINISHED', 1, 1);
INSERT INTO statistic(open, in_progress, finished, diff_open,  diff_in_progress, diff_finished) VALUES (1, 1, 1, 1, 1, 1);
