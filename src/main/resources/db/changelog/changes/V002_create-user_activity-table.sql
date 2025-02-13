CREATE TABLE IF NOT EXISTS user_activity (
    id uuid DEFAULT gen_random_uuid NOT NULL,
    user_uuid uuid references user_credentials(id),
    createActivity date not null,
    activity bigint NOT NULL,
    PRIMARY KEY (id),
    unique(user_uuid, createActivity, activity)
);