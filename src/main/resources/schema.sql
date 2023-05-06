drop all objects;
create table WAREHOUSE_AREA
(
    id   bigint auto_increment primary key,
    name varchar(255)
);

create table OPERATOR
(
    id               bigint auto_increment primary key,
    name             varchar(255),
    assigned_area_id bigint not null,
        CONSTRAINT FK_operator_area FOREIGN KEY(assigned_area_id)
        REFERENCES WAREHOUSE_AREA(id)
);
create table MAINTENANCE_JOB
(
    id                   bigint auto_increment primary key,
    end_time             timestamp,
    start_time           timestamp,
    status               varchar(255) not null,
    area_id              bigint       not null,
    assigned_operator_id bigint,
        CONSTRAINT FK_maintenance_job_area FOREIGN KEY(area_id)
        REFERENCES WAREHOUSE_AREA(id),
        CONSTRAINT FK_maintenance_job_operator FOREIGN KEY(assigned_operator_id)
        REFERENCES OPERATOR(id)
);

create table STATISTIC
(
    time             timestamp not null default current_timestamp primary key,
    diff_finished    integer,
    diff_in_progress integer,
    diff_open        integer,
    finished         integer,
    in_progress      integer,
    open             integer
);









