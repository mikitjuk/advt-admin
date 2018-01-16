
CREATE TYPE app_type AS ENUM ('IOS', 'ANDROID', 'WEBSITE');

CREATE TYPE content_type AS ENUM ('VIDEO', 'IMAGE', 'HTML');

CREATE TYPE user_role AS ENUM ('ADMIN', 'ADOPS', 'PUBLISHER');

CREATE TABLE users
(
  id SERIAL PRIMARY KEY,
  name VARCHAR(250),
  email VARCHAR(200),
  role user_role
);

CREATE TABLE apps
(
  id SERIAL PRIMARY KEY,
  name VARCHAR(250),
  type app_type,
  content_types content_type ARRAY,
  user_id INTEGER,
  CONSTRAINT apps_user_id_fk FOREIGN KEY (user_id)
  REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE app_content
(
  app_id INTEGER,
  content CONTENT_TYPE,
  CONSTRAINT app_content_app_id_content_pk PRIMARY KEY (app_id, content),
  CONSTRAINT app_content_apps_id_fk FOREIGN KEY (app_id)
      REFERENCES apps (id) ON DELETE CASCADE ON UPDATE CASCADE
);