CREATE TABLE IF NOT EXISTS user_activity (
    uuid uuid DEFAULT gen_random_uuid() NOT NULL,
    createActivity date not null,
    activity bigint NOT NULL,
    PRIMARY KEY (uuid),
    unique(createActivity, activity)
);